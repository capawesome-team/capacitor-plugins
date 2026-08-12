package io.capawesome.capacitorjs.plugins.intune;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.intune.classes.CustomException;
import io.capawesome.capacitorjs.plugins.intune.classes.events.AppConfigChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.EnrollmentChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.PolicyChangeEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.events.WipeRequestedEvent;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.AcquireTokenSilentOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetAppConfigOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.GetPolicyOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.RegisterAndEnrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.options.UnenrollAccountOptions;
import io.capawesome.capacitorjs.plugins.intune.classes.results.AcquireTokenResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetAppConfigResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetEnrolledAccountResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetPolicyResult;
import io.capawesome.capacitorjs.plugins.intune.classes.results.GetSdkVersionResult;
import io.capawesome.capacitorjs.plugins.intune.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.intune.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.intune.interfaces.Result;

@CapacitorPlugin(name = "Intune")
public class IntunePlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_APP_CONFIG_CHANGE = "appConfigChange";
    public static final String EVENT_ENROLLMENT_CHANGE = "enrollmentChange";
    public static final String EVENT_POLICY_CHANGE = "policyChange";
    public static final String EVENT_WIPE_REQUESTED = "wipeRequested";
    public static final String TAG = "IntunePlugin";

    private Intune implementation;

    @Override
    public void load() {
        super.load();
        Intune.initialize(getContext());
        this.implementation = new Intune(this);
    }

    @PluginMethod
    public void acquireToken(PluginCall call) {
        try {
            AcquireTokenOptions options = new AcquireTokenOptions(call);
            NonEmptyResultCallback<AcquireTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull AcquireTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.acquireToken(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void acquireTokenSilent(PluginCall call) {
        try {
            AcquireTokenSilentOptions options = new AcquireTokenSilentOptions(call);
            NonEmptyResultCallback<AcquireTokenResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull AcquireTokenResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.acquireTokenSilent(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAppConfig(PluginCall call) {
        try {
            GetAppConfigOptions options = new GetAppConfigOptions(call);
            NonEmptyResultCallback<GetAppConfigResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAppConfigResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getAppConfig(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getEnrolledAccount(PluginCall call) {
        try {
            NonEmptyResultCallback<GetEnrolledAccountResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetEnrolledAccountResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getEnrolledAccount(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getPolicy(PluginCall call) {
        try {
            GetPolicyOptions options = new GetPolicyOptions(call);
            NonEmptyResultCallback<GetPolicyResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetPolicyResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getPolicy(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getSdkVersion(PluginCall call) {
        try {
            NonEmptyResultCallback<GetSdkVersionResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetSdkVersionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getSdkVersion(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public boolean hasWipeRequestedListeners() {
        return hasListeners(EVENT_WIPE_REQUESTED);
    }

    @PluginMethod
    public void loginAndEnrollAccount(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    public void notifyAppConfigChangeListeners(@NonNull AppConfigChangeEvent event) {
        notifyListeners(EVENT_APP_CONFIG_CHANGE, event.toJSObject());
    }

    public void notifyEnrollmentChangeListeners(@NonNull EnrollmentChangeEvent event) {
        notifyListeners(EVENT_ENROLLMENT_CHANGE, event.toJSObject(), true);
    }

    public void notifyPolicyChangeListeners(@NonNull PolicyChangeEvent event) {
        notifyListeners(EVENT_POLICY_CHANGE, event.toJSObject());
    }

    public void notifyWipeRequestedListeners(@NonNull WipeRequestedEvent event) {
        notifyListeners(EVENT_WIPE_REQUESTED, event.toJSObject(), true);
    }

    @PluginMethod
    public void registerAndEnrollAccount(PluginCall call) {
        try {
            RegisterAndEnrollAccountOptions options = new RegisterAndEnrollAccountOptions(call);
            implementation.registerAndEnrollAccount(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void showDiagnosticConsole(PluginCall call) {
        try {
            implementation.showDiagnosticConsole(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void unenrollAccount(PluginCall call) {
        try {
            UnenrollAccountOptions options = new UnenrollAccountOptions(call);
            implementation.unenrollAccount(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @NonNull
    private EmptyCallback createEmptyCallback(@NonNull PluginCall call) {
        return new EmptyCallback() {
            @Override
            public void success() {
                resolveCall(call);
            }

            @Override
            public void error(Exception exception) {
                rejectCall(call, exception);
            }
        };
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        String code = null;
        if (exception instanceof CustomException) {
            code = ((CustomException) exception).getCode();
        }
        Logger.error(TAG, message, exception);
        call.reject(message, code);
    }

    private void rejectCallAsUnimplemented(@NonNull PluginCall call) {
        call.unimplemented("This method is not available on this platform.");
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
}
