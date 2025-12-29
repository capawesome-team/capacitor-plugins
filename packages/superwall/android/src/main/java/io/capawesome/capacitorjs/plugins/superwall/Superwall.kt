package io.capawesome.capacitorjs.plugins.superwall

import android.app.Application
import android.net.Uri
import com.getcapacitor.JSObject
import org.json.JSONObject
import com.superwall.sdk.Superwall as SuperwallSDK
import com.superwall.sdk.config.options.SuperwallOptions
import com.superwall.sdk.config.options.PaywallOptions
import com.superwall.sdk.identity.IdentityOptions
import com.superwall.sdk.identity.identify
import com.superwall.sdk.identity.setUserAttributes
import com.superwall.sdk.delegate.SuperwallDelegate
import com.superwall.sdk.analytics.superwall.SuperwallEventInfo
import com.superwall.sdk.paywall.presentation.PaywallInfo
import com.superwall.sdk.paywall.presentation.PaywallPresentationHandler
import com.superwall.sdk.paywall.presentation.internal.state.PaywallResult
import com.superwall.sdk.paywall.presentation.result.PresentationResult
import com.superwall.sdk.paywall.presentation.register
import com.superwall.sdk.paywall.presentation.get_presentation_result.getPresentationResultSync
import com.superwall.sdk.models.entitlements.SubscriptionStatus
import com.superwall.sdk.logger.LogLevel
import com.superwall.sdk.logger.LogScope
import io.capawesome.capacitorjs.plugins.superwall.classes.CustomExceptions
import io.capawesome.capacitorjs.plugins.superwall.classes.events.*
import io.capawesome.capacitorjs.plugins.superwall.classes.options.*
import io.capawesome.capacitorjs.plugins.superwall.classes.results.*
import io.capawesome.capacitorjs.plugins.superwall.interfaces.EmptyCallback
import io.capawesome.capacitorjs.plugins.superwall.interfaces.NonEmptyResultCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.EnumSet

class Superwall(private val plugin: SuperwallPlugin) : SuperwallDelegate {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isConfigured = false

    fun configure(options: ConfigureOptions, callback: EmptyCallback) {
        val application = plugin.context.applicationContext as? Application
        if (application == null) {
            callback.error(CustomExceptions.FAILED_TO_GET_APPLICATION_CONTEXT)
            return
        }

        // Configure Superwall SDK
        SuperwallSDK.configure(
            applicationContext = application,
            apiKey = options.getApiKey(),
            purchaseController = null,
            options = options.getSuperwallOptions(),
            activityProvider = null
        )

        isConfigured = true
        // Set delegate to receive events
        SuperwallSDK.instance.delegate = this
        callback.success()
    }

    fun register(options: RegisterOptions, callback: NonEmptyResultCallback<RegisterResult>) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val placement = options.getPlacement()
        val params = options.getParams()

        // Create a handler to capture the result
        val handler = PaywallPresentationHandler()
        handler.onDismiss { _, result ->
            val resultString = when (result) {
                is PaywallResult.Purchased -> "PURCHASED"
                is PaywallResult.Restored -> "RESTORED"
                is PaywallResult.Declined -> "CANCELLED"
            }
            callback.success(RegisterResult(resultString))
        }
        handler.onError { error ->
            val message = error.message ?: CustomExceptions.UNKNOWN_ERROR.message
            callback.error(Exception(message, error))
        }
        handler.onSkip { _ ->
            // If skipped, treat as cancelled
            callback.success(RegisterResult("CANCELLED"))
        }

        // Register placement
        SuperwallSDK.instance.register(
            placement = placement,
            params = params,
            handler = handler
        )
    }

    fun getPresentationResult(
        options: GetPresentationResultOptions,
        callback: NonEmptyResultCallback<GetPresentationResultResult>
    ) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val placement = options.getPlacement()
        val params = options.getParams()

        coroutineScope.launch {
            val result = SuperwallSDK.instance.getPresentationResultSync(
                placement = placement,
                params = params
            )

            result.onSuccess { presentationResult ->
                val typeString = when (presentationResult) {
                    is PresentationResult.PlacementNotFound -> "PLACEMENT_NOT_FOUND"
                    is PresentationResult.NoAudienceMatch -> "NO_AUDIENCE_MATCH"
                    is PresentationResult.Paywall -> "PAYWALL"
                    is PresentationResult.Holdout -> "HOLDOUT"
                    is PresentationResult.PaywallNotAvailable -> "PAYWALL_NOT_AVAILABLE"
                }

                val experiment = when (presentationResult) {
                    is PresentationResult.Paywall -> presentationResult.experiment
                    is PresentationResult.Holdout -> presentationResult.experiment
                    else -> null
                }

                callback.success(GetPresentationResultResult(typeString, experiment))
            }.onFailure { error ->
                val message = error.message ?: CustomExceptions.FAILED_TO_GET_PRESENTATION_RESULT.message
                callback.error(Exception(message, error))
            }
        }
    }

    fun identify(options: IdentifyOptions, callback: EmptyCallback) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val userId = options.getUserId()
        val restorePaywallAssignments = options.getRestorePaywallAssignments()

        val identityOptions = if (restorePaywallAssignments) {
            IdentityOptions(restorePaywallAssignments = true)
        } else {
            null
        }

        SuperwallSDK.instance.identify(userId, identityOptions)
        callback.success()
    }

    fun reset(callback: EmptyCallback) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        SuperwallSDK.instance.reset()
        callback.success()
    }

    fun getUserId(callback: NonEmptyResultCallback<GetUserIdResult>) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val userId = SuperwallSDK.instance.userId
        callback.success(GetUserIdResult(userId))
    }

    fun getIsLoggedIn(callback: NonEmptyResultCallback<GetIsLoggedInResult>) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val isLoggedIn = SuperwallSDK.instance.isLoggedIn
        callback.success(GetIsLoggedInResult(isLoggedIn))
    }

    fun setUserAttributes(options: SetUserAttributesOptions, callback: EmptyCallback) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val attributes = options.getAttributes()
        SuperwallSDK.instance.setUserAttributes(attributes)
        callback.success()
    }

    fun handleDeepLink(options: HandleDeepLinkOptions, callback: EmptyCallback) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val urlString = options.getUrl()
        val uri = Uri.parse(urlString)
        SuperwallSDK.instance.handleDeepLink(uri)
        callback.success()
    }

    fun getSubscriptionStatus(callback: NonEmptyResultCallback<GetSubscriptionStatusResult>) {
        if (!isConfigured) {
            callback.error(CustomExceptions.NOT_CONFIGURED)
            return
        }

        val status = SuperwallSDK.instance.subscriptionStatus.value
        val statusString = when (status) {
            is SubscriptionStatus.Unknown -> "UNKNOWN"
            is SubscriptionStatus.Active -> "ACTIVE"
            is SubscriptionStatus.Inactive -> "INACTIVE"
        }
        callback.success(GetSubscriptionStatusResult(statusString))
    }

    // SuperwallDelegate methods
    override fun handleSuperwallEvent(eventInfo: SuperwallEventInfo) {
        val event = SuperwallEvent(eventInfo)
        plugin.notifySuperwallEventListeners(event)
    }

    override fun subscriptionStatusDidChange(from: SubscriptionStatus, to: SubscriptionStatus) {
        val statusString = when (to) {
            is SubscriptionStatus.Unknown -> "UNKNOWN"
            is SubscriptionStatus.Active -> "ACTIVE"
            is SubscriptionStatus.Inactive -> "INACTIVE"
        }
        val event = SubscriptionStatusDidChangeEvent(statusString)
        plugin.notifySubscriptionStatusDidChangeListeners(event)
    }

    override fun didPresentPaywall(withInfo: PaywallInfo) {
        val event = PaywallPresentedEvent(withInfo)
        plugin.notifyPaywallPresentedListeners(event)
    }

    override fun willDismissPaywall(withInfo: PaywallInfo) {
        val event = PaywallWillDismissEvent(withInfo)
        plugin.notifyPaywallWillDismissListeners(event)
    }

    override fun didDismissPaywall(withInfo: PaywallInfo) {
        val event = PaywallDismissedEvent(withInfo)
        plugin.notifyPaywallDismissedListeners(event)
    }

    override fun handleCustomPaywallAction(withName: String) {
        val event = CustomPaywallActionEvent(withName)
        plugin.notifyCustomPaywallActionListeners(event)
    }

}
