package io.capawesome.capacitorjs.plugins.crisp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomException;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.MessageReceivedEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.MessageSentEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.events.SessionLoadedEvent;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.ConfigureOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.HandlePushNotificationOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.IsCrispPushNotificationOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.OpenHelpdeskArticleOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.PushSessionEventOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetCompanyOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetNotificationsEnabledOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionBoolOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionIntOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionSegmentOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetSessionStringOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetTokenIdOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.options.SetUserOptions;
import io.capawesome.capacitorjs.plugins.crisp.classes.results.IsCrispPushNotificationResult;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.crisp.interfaces.Result;

@CapacitorPlugin(name = "Crisp")
public class CrispPlugin extends Plugin {

    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_CHAT_CLOSED = "chatClosed";
    public static final String EVENT_CHAT_OPENED = "chatOpened";
    public static final String EVENT_MESSAGE_RECEIVED = "messageReceived";
    public static final String EVENT_MESSAGE_SENT = "messageSent";
    public static final String EVENT_SESSION_LOADED = "sessionLoaded";
    public static final String TAG = "CrispPlugin";

    private Crisp implementation;

    @Override
    public void load() {
        super.load();
        this.implementation = new Crisp(this);
    }

    @PluginMethod
    public void configure(PluginCall call) {
        try {
            ConfigureOptions options = new ConfigureOptions(call);
            implementation.configure(options, createEmptyCallback(call));
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
    public void isCrispPushNotification(PluginCall call) {
        try {
            IsCrispPushNotificationOptions options = new IsCrispPushNotificationOptions(call);
            NonEmptyResultCallback<IsCrispPushNotificationResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsCrispPushNotificationResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            implementation.isCrispPushNotification(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    public void notifyChatClosedListeners() {
        notifyListeners(EVENT_CHAT_CLOSED, new JSObject());
    }

    public void notifyChatOpenedListeners() {
        notifyListeners(EVENT_CHAT_OPENED, new JSObject());
    }

    public void notifyMessageReceivedListeners(@NonNull MessageReceivedEvent event) {
        notifyListeners(EVENT_MESSAGE_RECEIVED, event.toJSObject());
    }

    public void notifyMessageSentListeners(@NonNull MessageSentEvent event) {
        notifyListeners(EVENT_MESSAGE_SENT, event.toJSObject());
    }

    public void notifySessionLoadedListeners(@NonNull SessionLoadedEvent event) {
        notifyListeners(EVENT_SESSION_LOADED, event.toJSObject());
    }

    @PluginMethod
    public void openChat(PluginCall call) {
        try {
            implementation.openChat(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openHelpdeskArticle(PluginCall call) {
        try {
            OpenHelpdeskArticleOptions options = new OpenHelpdeskArticleOptions(call);
            implementation.openHelpdeskArticle(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void pushSessionEvent(PluginCall call) {
        try {
            PushSessionEventOptions options = new PushSessionEventOptions(call);
            implementation.pushSessionEvent(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void resetSession(PluginCall call) {
        try {
            implementation.resetSession(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void searchHelpdesk(PluginCall call) {
        try {
            implementation.searchHelpdesk(createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCompany(PluginCall call) {
        try {
            SetCompanyOptions options = new SetCompanyOptions(call);
            implementation.setCompany(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNotificationsEnabled(PluginCall call) {
        try {
            SetNotificationsEnabledOptions options = new SetNotificationsEnabledOptions(call);
            implementation.setNotificationsEnabled(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setSessionBool(PluginCall call) {
        try {
            SetSessionBoolOptions options = new SetSessionBoolOptions(call);
            implementation.setSessionBool(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setSessionInt(PluginCall call) {
        try {
            SetSessionIntOptions options = new SetSessionIntOptions(call);
            implementation.setSessionInt(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setSessionSegment(PluginCall call) {
        try {
            SetSessionSegmentOptions options = new SetSessionSegmentOptions(call);
            implementation.setSessionSegment(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setSessionString(PluginCall call) {
        try {
            SetSessionStringOptions options = new SetSessionStringOptions(call);
            implementation.setSessionString(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setShouldPromptForNotificationPermission(PluginCall call) {
        rejectCallAsUnimplemented(call);
    }

    @PluginMethod
    public void setTokenId(PluginCall call) {
        try {
            SetTokenIdOptions options = new SetTokenIdOptions(call);
            implementation.setTokenId(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setUser(PluginCall call) {
        try {
            SetUserOptions options = new SetUserOptions(call);
            implementation.setUser(options, createEmptyCallback(call));
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        if (implementation != null) {
            implementation.unregisterCallback();
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
        call.unimplemented("This method is not available on Android.");
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
