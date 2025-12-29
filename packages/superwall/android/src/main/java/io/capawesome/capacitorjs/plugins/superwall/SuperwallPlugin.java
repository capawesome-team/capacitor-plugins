package io.capawesome.capacitorjs.plugins.superwall;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.superwall.classes.events.*;
import io.capawesome.capacitorjs.plugins.superwall.classes.options.*;
import io.capawesome.capacitorjs.plugins.superwall.classes.results.*;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;

@CapacitorPlugin(name = "Superwall")
public class SuperwallPlugin extends Plugin {

    public static final String TAG = "Superwall";
    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String EVENT_SUPERWALL_EVENT = "superwallEvent";
    public static final String EVENT_SUBSCRIPTION_STATUS_DID_CHANGE = "subscriptionStatusDidChange";
    public static final String EVENT_PAYWALL_PRESENTED = "paywallPresented";
    public static final String EVENT_PAYWALL_WILL_DISMISS = "paywallWillDismiss";
    public static final String EVENT_PAYWALL_DISMISSED = "paywallDismissed";
    public static final String EVENT_CUSTOM_PAYWALL_ACTION = "customPaywallAction";

    private Superwall implementation;

    @Override
    public void load() {
        implementation = new Superwall(this);
    }

    @PluginMethod
    public void configure(PluginCall call) {
        try {
            ConfigureOptions options = new ConfigureOptions(call);

            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.configure(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void register(PluginCall call) {
        try {
            RegisterOptions options = new RegisterOptions(call);

            NonEmptyResultCallback<RegisterResult> callback = new NonEmptyResultCallback<RegisterResult>() {
                @Override
                public void success(@NonNull RegisterResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.register(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getPresentationResult(PluginCall call) {
        try {
            GetPresentationResultOptions options = new GetPresentationResultOptions(call);

            NonEmptyResultCallback<GetPresentationResultResult> callback = new NonEmptyResultCallback<GetPresentationResultResult>() {
                @Override
                public void success(@NonNull GetPresentationResultResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getPresentationResult(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void identify(PluginCall call) {
        try {
            IdentifyOptions options = new IdentifyOptions(call);

            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.identify(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void reset(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.reset(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getUserId(PluginCall call) {
        try {
            NonEmptyResultCallback<GetUserIdResult> callback = new NonEmptyResultCallback<GetUserIdResult>() {
                @Override
                public void success(@NonNull GetUserIdResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getUserId(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getIsLoggedIn(PluginCall call) {
        try {
            NonEmptyResultCallback<GetIsLoggedInResult> callback = new NonEmptyResultCallback<GetIsLoggedInResult>() {
                @Override
                public void success(@NonNull GetIsLoggedInResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getIsLoggedIn(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUserAttributes(PluginCall call) {
        try {
            SetUserAttributesOptions options = new SetUserAttributesOptions(call);

            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.setUserAttributes(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void handleDeepLink(PluginCall call) {
        try {
            HandleDeepLinkOptions options = new HandleDeepLinkOptions(call);

            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.handleDeepLink(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getSubscriptionStatus(PluginCall call) {
        try {
            NonEmptyResultCallback<GetSubscriptionStatusResult> callback = new NonEmptyResultCallback<GetSubscriptionStatusResult>() {
                @Override
                public void success(@NonNull GetSubscriptionStatusResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getSubscriptionStatus(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Log.e(TAG, message, exception);
        call.reject(message);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("Not implemented on this platform.");
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unavailable("This method is not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }

    public void notifySuperwallEventListeners(@NonNull SuperwallEvent event) {
        notifyListeners(EVENT_SUPERWALL_EVENT, event.toJSObject());
    }

    public void notifySubscriptionStatusDidChangeListeners(@NonNull SubscriptionStatusDidChangeEvent event) {
        notifyListeners(EVENT_SUBSCRIPTION_STATUS_DID_CHANGE, event.toJSObject());
    }

    public void notifyPaywallPresentedListeners(@NonNull PaywallPresentedEvent event) {
        notifyListeners(EVENT_PAYWALL_PRESENTED, event.toJSObject());
    }

    public void notifyPaywallWillDismissListeners(@NonNull PaywallWillDismissEvent event) {
        notifyListeners(EVENT_PAYWALL_WILL_DISMISS, event.toJSObject());
    }

    public void notifyPaywallDismissedListeners(@NonNull PaywallDismissedEvent event) {
        notifyListeners(EVENT_PAYWALL_DISMISSED, event.toJSObject());
    }

    public void notifyCustomPaywallActionListeners(@NonNull CustomPaywallActionEvent event) {
        notifyListeners(EVENT_CUSTOM_PAYWALL_ACTION, event.toJSObject());
    }
}
