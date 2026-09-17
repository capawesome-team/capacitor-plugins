package io.capawesome.capacitorjs.plugins.intercom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomException;
import io.capawesome.capacitorjs.plugins.intercom.classes.events.UnreadConversationCountChangeEvent;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.HandlePushNotificationOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.IsIntercomPushNotificationOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.LogEventOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.LoginUserOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentContentOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentMessageComposerOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.PresentOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SendPushTokenToIntercomOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetBottomPaddingOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetInAppMessagesVisibleOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetLauncherVisibleOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetUserHashOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.SetUserJwtOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.options.UpdateUserOptions;
import io.capawesome.capacitorjs.plugins.intercom.classes.results.GetUnreadConversationCountResult;
import io.capawesome.capacitorjs.plugins.intercom.classes.results.IsIntercomPushNotificationResult;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.intercom.interfaces.Result;

@CapacitorPlugin(name = "Intercom")
public class IntercomPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_UNREAD_CONVERSATION_COUNT_CHANGE = "unreadConversationCountChange";
    public static final String TAG = "IntercomPlugin";

    private Intercom implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Intercom(this);
    }

    @PluginMethod
    public void getUnreadConversationCount(PluginCall call) {
        try {
            NonEmptyResultCallback<GetUnreadConversationCountResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetUnreadConversationCountResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.getUnreadConversationCount(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void handlePushNotification(PluginCall call) {
        try {
            HandlePushNotificationOptions options = new HandlePushNotificationOptions(call);
            implementation.handlePushNotification(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void hide(PluginCall call) {
        try {
            implementation.hide(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
            implementation.initialize(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isIntercomPushNotification(PluginCall call) {
        try {
            IsIntercomPushNotificationOptions options = new IsIntercomPushNotificationOptions(call);
            NonEmptyResultCallback<IsIntercomPushNotificationResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsIntercomPushNotificationResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isIntercomPushNotification(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void logEvent(PluginCall call) {
        try {
            LogEventOptions options = new LogEventOptions(call);
            implementation.logEvent(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void loginUnidentifiedUser(PluginCall call) {
        try {
            implementation.loginUnidentifiedUser(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void loginUser(PluginCall call) {
        try {
            LoginUserOptions options = new LoginUserOptions(call);
            implementation.loginUser(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void logout(PluginCall call) {
        try {
            implementation.logout(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyUnreadConversationCountChangeListeners(@NonNull UnreadConversationCountChangeEvent event) {
        notifyListeners(EVENT_UNREAD_CONVERSATION_COUNT_CHANGE, event.toJSObject());
    }

    @PluginMethod
    public void present(PluginCall call) {
        try {
            PresentOptions options = new PresentOptions(call);
            implementation.present(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void presentContent(PluginCall call) {
        try {
            PresentContentOptions options = new PresentContentOptions(call);
            implementation.presentContent(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void presentMessageComposer(PluginCall call) {
        try {
            PresentMessageComposerOptions options = new PresentMessageComposerOptions(call);
            implementation.presentMessageComposer(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sendPushTokenToIntercom(PluginCall call) {
        try {
            SendPushTokenToIntercomOptions options = new SendPushTokenToIntercomOptions(call);
            implementation.sendPushTokenToIntercom(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setBottomPadding(PluginCall call) {
        try {
            SetBottomPaddingOptions options = new SetBottomPaddingOptions(call);
            implementation.setBottomPadding(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setInAppMessagesVisible(PluginCall call) {
        try {
            SetInAppMessagesVisibleOptions options = new SetInAppMessagesVisibleOptions(call);
            implementation.setInAppMessagesVisible(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setLauncherVisible(PluginCall call) {
        try {
            SetLauncherVisibleOptions options = new SetLauncherVisibleOptions(call);
            implementation.setLauncherVisible(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUserHash(PluginCall call) {
        try {
            SetUserHashOptions options = new SetUserHashOptions(call);
            implementation.setUserHash(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUserJwt(PluginCall call) {
        try {
            SetUserJwtOptions options = new SetUserJwtOptions(call);
            implementation.setUserJwt(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void updateUser(PluginCall call) {
        try {
            UpdateUserOptions options = new UpdateUserOptions(call);
            implementation.updateUser(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        if (implementation != null) {
            implementation.unregisterUnreadConversationCountListener();
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
