package io.capawesome.capacitorjs.plugins.ferrostarnavigation

import android.speech.tts.TextToSpeech
import com.stadiamaps.ferrostar.core.AndroidTtsObserver
import com.stadiamaps.ferrostar.core.AndroidTtsStatusListener
import com.stadiamaps.ferrostar.core.CorrectiveAction
import com.stadiamaps.ferrostar.core.CustomRouteProvider
import com.stadiamaps.ferrostar.core.FerrostarCore
import com.stadiamaps.ferrostar.core.NavigationState
import com.stadiamaps.ferrostar.core.RouteDeviationHandler
import com.stadiamaps.ferrostar.core.http.HttpClientProvider
import com.stadiamaps.ferrostar.core.http.IResponse
import com.stadiamaps.ferrostar.core.location.AndroidLocationProvider
import com.stadiamaps.ferrostar.core.location.NavigationLocationProviding
import com.stadiamaps.ferrostar.core.location.SimulatedLocationProvider
import com.stadiamaps.ferrostar.core.service.FerrostarForegroundServiceManager
import com.stadiamaps.ferrostar.core.service.ForegroundServiceManager
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.CustomExceptions
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.Instruction
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.Location
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.NotificationBuilder
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events.NavigationStateChangedEvent
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events.RouteDeviationDetectedEvent
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options.ReplaceRouteOptions
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.options.StartNavigationOptions
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.EmptyCallback
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.launch
import uniffi.ferrostar.DeviationKind
import uniffi.ferrostar.NavigationControllerConfig
import uniffi.ferrostar.Route
import uniffi.ferrostar.RouteDeviation
import uniffi.ferrostar.RouteDeviationTracking
import uniffi.ferrostar.RouteRequest
import uniffi.ferrostar.TripState
import uniffi.ferrostar.UserLocation
import uniffi.ferrostar.VisualInstruction
import uniffi.ferrostar.WaypointAdvanceMode
import uniffi.ferrostar.createOsrmResponseParser
import uniffi.ferrostar.stepAdvanceDistanceEntryAndExit
import uniffi.ferrostar.stepAdvanceDistanceToEndOfStep

class FerrostarNavigation(private val plugin: FerrostarNavigationPlugin) {

    companion object {
        private const val MINIMUM_HORIZONTAL_ACCURACY_METERS: UShort = 32u
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var core: FerrostarCore? = null
    private var spokenInstructionObserver: AndroidTtsObserver? = null
    private var wasComplete = false
    private var wasOffRoute = false

    fun muteVoice(callback: EmptyCallback) {
        spokenInstructionObserver?.setMuted(true)
        callback.success()
    }

    @Throws(Exception::class)
    fun replaceRoute(options: ReplaceRouteOptions, callback: EmptyCallback) {
        val core = core ?: throw CustomExceptions.NOT_NAVIGATING
        val route = parseRoute(options.osrmResponseJson, options.polylinePrecision)
        (core.locationProvider as? SimulatedLocationProvider)?.setRoute(route)
        wasOffRoute = false
        core.replaceRoute(route)
        callback.success()
    }

    @Throws(Exception::class)
    fun startNavigation(options: StartNavigationOptions, callback: EmptyCallback) {
        stopNavigation()
        val route = parseRoute(options.osrmResponseJson, options.polylinePrecision)
        val core = FerrostarCore(
            customRouteProvider = CustomRouteProvider { _, _ -> emptyList() },
            httpClient = UnusedHttpClientProvider,
            locationProvider = createLocationProvider(options, route),
            navigationControllerConfig = createNavigationControllerConfig(options),
            foregroundServiceManager = createForegroundServiceManager(options),
        )
        core.deviationHandler = RouteDeviationHandler { _, _, _ -> CorrectiveAction.DoNothing }
        this.core = core
        if (!options.speakInstructions) {
            startNavigation(core, route, callback)
            return
        }
        // The engine starts asynchronously and drops instructions until it is ready, so the navigation starts after it.
        val spokenInstructionObserver = AndroidTtsObserver(
            plugin.context,
            statusObserver = createTtsStatusListener(options.voiceLanguage) {
                if (this.core === core) {
                    startNavigation(core, route, callback)
                } else {
                    callback.success()
                }
            },
        )
        spokenInstructionObserver.setMuted(options.muteVoice)
        core.spokenInstructionObserver = spokenInstructionObserver
        this.spokenInstructionObserver = spokenInstructionObserver
        spokenInstructionObserver.start()
    }

    fun stopNavigation(callback: EmptyCallback) {
        stopNavigation()
        callback.success()
    }

    fun unmuteVoice(callback: EmptyCallback) {
        spokenInstructionObserver?.setMuted(false)
        callback.success()
    }

    private fun createForegroundServiceManager(options: StartNavigationOptions): ForegroundServiceManager? {
        if (!options.continueInBackground) {
            return null
        }
        val context = plugin.context.applicationContext
        return FerrostarForegroundServiceManager(context, NotificationBuilder(context, options.androidNotification))
    }

    private fun createInstruction(visualInstruction: VisualInstruction?): Instruction? {
        val primaryContent = visualInstruction?.primaryContent ?: return null
        return Instruction(
            primaryContent.text,
            visualInstruction.secondaryContent?.text,
            primaryContent.maneuverType?.name,
            primaryContent.maneuverModifier?.name,
        )
    }

    private fun createLocation(userLocation: UserLocation): Location {
        return Location(
            userLocation.coordinates.lat,
            userLocation.coordinates.lng,
            userLocation.courseOverGround?.degrees?.toInt(),
            userLocation.horizontalAccuracy,
        )
    }

    private fun createLocationProvider(options: StartNavigationOptions, route: Route): NavigationLocationProviding {
        if (!options.simulateLocation) {
            return AndroidLocationProvider(plugin.context)
        }
        val locationProvider = SimulatedLocationProvider(warpFactor = options.simulationSpeedMultiplier.toUInt())
        locationProvider.setRoute(route)
        return locationProvider
    }

    private fun createNavigationControllerConfig(options: StartNavigationOptions): NavigationControllerConfig {
        val routeDeviationTracking = if (options.routeDeviationThresholdMeters == 0.0) {
            RouteDeviationTracking.None
        } else {
            RouteDeviationTracking.StaticThreshold(MINIMUM_HORIZONTAL_ACCURACY_METERS, options.routeDeviationThresholdMeters)
        }
        return NavigationControllerConfig(
            WaypointAdvanceMode.WaypointWithinRange(options.waypointAdvanceDistanceMeters),
            stepAdvanceDistanceEntryAndExit(
                options.stepAdvanceEntryDistanceMeters.toUShort(),
                options.stepAdvanceExitDistanceMeters.toUShort(),
                MINIMUM_HORIZONTAL_ACCURACY_METERS,
            ),
            stepAdvanceDistanceToEndOfStep(options.arrivalStepAdvanceDistanceMeters.toUShort(), MINIMUM_HORIZONTAL_ACCURACY_METERS),
            routeDeviationTracking,
            options.courseFiltering,
        )
    }

    private fun createNavigationStateChangedEvent(tripState: TripState): NavigationStateChangedEvent {
        return when (tripState) {
            is TripState.Idle -> NavigationStateChangedEvent(
                "IDLE",
                null,
                null,
                null,
                null,
                null,
                tripState.userLocation?.let { createLocation(it) },
                false,
                null,
            )
            is TripState.Navigating -> NavigationStateChangedEvent(
                "NAVIGATING",
                createInstruction(tripState.visualInstruction),
                tripState.progress.distanceToNextManeuver,
                tripState.progress.distanceRemaining,
                tripState.progress.durationRemaining,
                tripState.summary.snappedDistanceTraveled,
                createLocation(tripState.snappedUserLocation),
                getCompletelyOffRouteDeviation(tripState) != null,
                tripState.spokenInstruction?.text,
            )
            is TripState.Complete -> NavigationStateChangedEvent(
                "COMPLETE",
                null,
                null,
                null,
                null,
                tripState.summary.snappedDistanceTraveled,
                createLocation(tripState.userLocation),
                false,
                null,
            )
        }
    }

    private fun createTtsStatusListener(voiceLanguage: String?, onInitialized: () -> Unit): AndroidTtsStatusListener {
        return object : AndroidTtsStatusListener {
            override fun onTtsInitialized(tts: TextToSpeech?, status: Int) {
                if (voiceLanguage != null) {
                    tts?.language = Locale.forLanguageTag(voiceLanguage)
                }
                onInitialized()
            }

            override fun onTtsShutdownAndRelease() {}

            override fun onTtsSpeakError(utteranceId: String, status: Int) {}
        }
    }

    private fun getCompletelyOffRouteDeviation(tripState: TripState.Navigating): DeviationKind.CompletelyOffRoute? {
        val deviation = tripState.deviation as? RouteDeviation.Deviation ?: return null
        return deviation.kind as? DeviationKind.CompletelyOffRoute
    }

    private fun handleNavigationStateChanged(state: NavigationState) {
        val tripState = state.tripState
        plugin.notifyNavigationStateChangedListeners(createNavigationStateChangedEvent(tripState))
        if (tripState is TripState.Navigating) {
            notifyRouteDeviationDetectedListenersIfOffRoute(tripState)
        }
        val isComplete = tripState is TripState.Complete
        if (isComplete && !wasComplete) {
            plugin.notifyNavigationCompletedListeners()
        }
        wasComplete = isComplete
    }

    private fun notifyRouteDeviationDetectedListenersIfOffRoute(tripState: TripState.Navigating) {
        val deviation = getCompletelyOffRouteDeviation(tripState)
        if (deviation != null && !wasOffRoute) {
            plugin.notifyRouteDeviationDetectedListeners(
                RouteDeviationDetectedEvent(deviation.deviationFromRouteLine, createLocation(tripState.userLocation)),
            )
        }
        wasOffRoute = deviation != null
    }

    /**
     * Collects the state until the session is stopped, so that the final idle state is still delivered.
     */
    private fun observeState(core: FerrostarCore) {
        coroutineScope.launch {
            core.state
                .transformWhile { state ->
                    emit(state)
                    state.tripState !is TripState.Idle
                }
                .collect { state -> handleNavigationStateChanged(state) }
        }
    }

    @Throws(Exception::class)
    private fun parseRoute(osrmResponseJson: String, polylinePrecision: Int): Route {
        val routes = createOsrmResponseParser(polylinePrecision.toUInt()).parseResponse(osrmResponseJson.toByteArray())
        return routes.firstOrNull() ?: throw CustomExceptions.ROUTE_MISSING
    }

    private fun startNavigation(core: FerrostarCore, route: Route, callback: EmptyCallback) {
        wasComplete = false
        wasOffRoute = false
        core.startNavigation(route)
        observeState(core)
        callback.success()
    }

    private fun stopNavigation() {
        core?.stopNavigation()
        core = null
        spokenInstructionObserver?.shutdown()
        spokenInstructionObserver = null
    }

    /**
     * The core only calls the HTTP client for route adapters, but the plugin uses a custom route provider.
     */
    private object UnusedHttpClientProvider : HttpClientProvider {
        override suspend fun call(request: RouteRequest): IResponse {
            throw UnsupportedOperationException("The plugin does not fetch routes.")
        }
    }
}
