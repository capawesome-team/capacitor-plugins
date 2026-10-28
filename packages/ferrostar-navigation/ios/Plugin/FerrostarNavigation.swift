import Combine
import CoreLocation
import FerrostarCore
import FerrostarCoreFFI
import Foundation

@objc public class FerrostarNavigation: NSObject {
    private static let minimumHorizontalAccuracyMeters: UInt16 = 32

    private var core: FerrostarCore?
    private var isOffRoute = false
    private var locationProvider: LocationProviding?
    private weak var plugin: FerrostarNavigationPlugin?
    private var stateCancellable: AnyCancellable?
    private var status: NavigationStatus?

    init(plugin: FerrostarNavigationPlugin) {
        self.plugin = plugin
    }

    @objc public func muteVoice(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.setVoiceMuted(true)
            completion(nil)
        }
    }

    @objc public func replaceRoute(_ options: ReplaceRouteOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                try self.replaceSessionRoute(options)
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    @objc public func startNavigation(_ options: StartNavigationOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                try self.startSession(options)
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    @objc public func stopNavigation(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.stopSession()
            completion(nil)
        }
    }

    @objc public func unmuteVoice(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.setVoiceMuted(false)
            completion(nil)
        }
    }

    private func createLocationProvider(_ options: StartNavigationOptions, route: Route) throws -> LocationProviding {
        guard options.simulateLocation else {
            return CoreLocationProvider(activityType: .otherNavigation, allowBackgroundLocationUpdates: options.continueInBackground)
        }
        let provider = SimulatedLocationProvider()
        provider.warpFactor = options.simulationSpeedMultiplier
        try provider.setSimulatedRoute(route)
        return provider
    }

    private func createNavigationControllerConfig(_ options: StartNavigationOptions) -> SwiftNavigationControllerConfig {
        let accuracy = Self.minimumHorizontalAccuracyMeters
        let routeDeviationTracking: SwiftRouteDeviationTracking = options.routeDeviationThresholdMeters == 0
            ? .none
            : .staticThreshold(minimumHorizontalAccuracy: accuracy, maxAcceptableDeviation: options.routeDeviationThresholdMeters)
        return SwiftNavigationControllerConfig(
            waypointAdvance: .waypointWithinRange(options.waypointAdvanceDistanceMeters),
            stepAdvanceCondition: stepAdvanceDistanceEntryAndExit(
                distanceToEndOfStep: options.stepAdvanceEntryDistanceMeters,
                distanceAfterEndOfStep: options.stepAdvanceExitDistanceMeters,
                minimumHorizontalAccuracy: accuracy
            ),
            arrivalStepAdvanceCondition: stepAdvanceDistanceToEndOfStep(
                distance: options.arrivalStepAdvanceDistanceMeters,
                minimumHorizontalAccuracy: accuracy
            ),
            routeDeviationTracking: routeDeviationTracking,
            snappedLocationCourseFiltering: options.courseFiltering
        )
    }

    private func createSpokenInstructionObserver(_ options: StartNavigationOptions) -> SpokenInstructionObserver {
        guard options.speakInstructions else {
            return SpokenInstructionObserver(synthesizer: SilentSpeechSynthesizer(), isMuted: true, managesAudioSession: false)
        }
        return SpokenInstructionObserver(
            synthesizer: DeviceSpeechSynthesizer(voiceLanguage: options.voiceLanguage),
            isMuted: options.muteVoice
        )
    }

    private func handleStateChange(_ state: NavigationState?) {
        let event = NavigationStateChangedEvent(tripState: state?.tripState)
        plugin?.notifyNavigationStateChangedListeners(event)
        notifyRouteDeviationDetectedListenersIfNeeded(event)
        notifyNavigationCompletedListenersIfNeeded(event)
    }

    private func notifyNavigationCompletedListenersIfNeeded(_ event: NavigationStateChangedEvent) {
        let previousStatus = status
        status = event.status
        guard event.status == .complete, previousStatus != .complete else {
            return
        }
        plugin?.notifyNavigationCompletedListeners()
    }

    private func notifyRouteDeviationDetectedListenersIfNeeded(_ event: NavigationStateChangedEvent) {
        let wasOffRoute = isOffRoute
        isOffRoute = event.isOffRoute
        guard !wasOffRoute,
              let deviationMeters = FerrostarNavigationHelper.getOffRouteDeviationMeters(event.tripState),
              case let .navigating(_, userLocation, _, _, _, _, _, _, _, _, _) = event.tripState else {
            return
        }
        plugin?.notifyRouteDeviationDetectedListeners(RouteDeviationDetectedEvent(deviationMeters: deviationMeters, location: userLocation))
    }

    private func parseRoute(_ osrmResponseJson: String, polylinePrecision: UInt32) throws -> Route {
        let parser = createOsrmResponseParser(polylinePrecision: polylinePrecision)
        guard let route = try parser.parseResponse(response: Data(osrmResponseJson.utf8)).first else {
            throw CustomError.routeMissing
        }
        return route
    }

    private func replaceSessionRoute(_ options: ReplaceRouteOptions) throws {
        guard let core else {
            throw CustomError.notNavigating
        }
        let route = try parseRoute(options.osrmResponseJson, polylinePrecision: options.polylinePrecision)
        if let simulatedLocationProvider = locationProvider as? SimulatedLocationProvider {
            // Stop the simulation first so that the old session does not receive the start of the new route.
            simulatedLocationProvider.stopUpdating()
            try simulatedLocationProvider.setSimulatedRoute(route)
        }
        // Ferrostar only clears the speech queue on stop, not when a route starts on a running session.
        core.spokenInstructionObserver.stopAndClearQueue()
        resetEventTracking()
        try core.startNavigation(route: route)
    }

    private func resetEventTracking() {
        isOffRoute = false
        status = nil
    }

    private func setVoiceMuted(_ isMuted: Bool) {
        guard let observer = core?.spokenInstructionObserver, observer.isMuted != isMuted else {
            return
        }
        observer.toggleMute()
    }

    private func startSession(_ options: StartNavigationOptions) throws {
        stopSession()
        let route = try parseRoute(options.osrmResponseJson, polylinePrecision: options.polylinePrecision)
        let locationProvider = try createLocationProvider(options, route: route)
        let core = FerrostarCore(
            customRouteProvider: EmptyRouteProvider(),
            locationProvider: locationProvider,
            navigationControllerConfig: createNavigationControllerConfig(options),
            networkSession: URLSession.shared,
            annotation: nil,
            spokenInstructionObserver: createSpokenInstructionObserver(options)
        )
        core.delegate = self
        resetEventTracking()
        do {
            try core.startNavigation(route: route)
        } catch {
            locationProvider.delegate = nil
            throw error
        }
        self.core = core
        self.locationProvider = locationProvider
        stateCancellable = core.$state.sink { [weak self] state in
            self?.handleStateChange(state)
        }
    }

    private func stopSession() {
        guard let core else {
            return
        }
        core.stopNavigation()
        // The provider holds the core strongly as its delegate, so the cycle must be broken to release the session.
        locationProvider?.delegate = nil
        self.core = nil
        locationProvider = nil
        stateCancellable = nil
    }
}

extension FerrostarNavigation: FerrostarCoreDelegate {
    public func core(_ core: FerrostarCore, correctiveActionForDeviation deviation: DeviationKind, remainingWaypoints waypoints: [Waypoint]) -> CorrectiveAction {
        return .doNothing
    }

    public func core(_ core: FerrostarCore, didStartWith route: Route) {}

    public func core(_ core: FerrostarCore, loadedAlternateRoutes routes: [Route]) {}
}
