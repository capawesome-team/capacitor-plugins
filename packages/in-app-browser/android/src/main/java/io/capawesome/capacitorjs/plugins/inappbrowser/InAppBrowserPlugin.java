package io.capawesome.capacitorjs.plugins.inappbrowser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomException;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.events.BrowserPageNavigationCompletedEvent;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.events.MessageReceivedEvent;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.ExecuteScriptOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.GetCookiesOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.OpenInExternalBrowserOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.OpenInSystemBrowserOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.OpenInWebViewOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.PostMessageOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.results.ExecuteScriptResult;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.results.GetCookiesResult;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.NonEmptyResultCallback;
import io.capawesome.capacitorjs.plugins.inappbrowser.interfaces.Result;

@CapacitorPlugin(name = "InAppBrowser")
public class InAppBrowserPlugin extends Plugin {

    public static final String EVENT_BROWSER_CLOSED = "browserClosed";
    public static final String EVENT_BROWSER_PAGE_LOADED = "browserPageLoaded";
    public static final String EVENT_BROWSER_PAGE_NAVIGATION_COMPLETED = "browserPageNavigationCompleted";
    public static final String EVENT_MESSAGE_RECEIVED = "messageReceived";
    public static final String TAG = "InAppBrowserPlugin";

    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private InAppBrowser implementation;

    @PluginMethod
    public void clearCache(PluginCall call) {
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

            implementation.clearCache(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void clearSessionData(PluginCall call) {
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

            implementation.clearSessionData(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void close(PluginCall call) {
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

            implementation.close(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void executeScript(PluginCall call) {
        try {
            ExecuteScriptOptions options = new ExecuteScriptOptions(call);
            NonEmptyResultCallback<ExecuteScriptResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull ExecuteScriptResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.executeScript(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCookies(PluginCall call) {
        try {
            GetCookiesOptions options = new GetCookiesOptions(call);
            NonEmptyResultCallback<GetCookiesResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetCookiesResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCookies(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    public void load() {
        implementation = new InAppBrowser(this);
    }

    public void notifyBrowserClosedListeners() {
        notifyListeners(EVENT_BROWSER_CLOSED, new JSObject());
    }

    public void notifyBrowserPageLoadedListeners() {
        notifyListeners(EVENT_BROWSER_PAGE_LOADED, new JSObject());
    }

    public void notifyBrowserPageNavigationCompletedListeners(@NonNull BrowserPageNavigationCompletedEvent event) {
        notifyListeners(EVENT_BROWSER_PAGE_NAVIGATION_COMPLETED, event.toJSObject());
    }

    public void notifyMessageReceivedListeners(@NonNull MessageReceivedEvent event) {
        notifyListeners(EVENT_MESSAGE_RECEIVED, event.toJSObject());
    }

    @PluginMethod
    public void openInExternalBrowser(PluginCall call) {
        try {
            OpenInExternalBrowserOptions options = new OpenInExternalBrowserOptions(call);
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

            implementation.openInExternalBrowser(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openInSystemBrowser(PluginCall call) {
        try {
            OpenInSystemBrowserOptions options = new OpenInSystemBrowserOptions(call);
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

            implementation.openInSystemBrowser(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openInWebView(PluginCall call) {
        try {
            OpenInWebViewOptions options = new OpenInWebViewOptions(call);
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

            implementation.openInWebView(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void postMessage(PluginCall call) {
        try {
            PostMessageOptions options = new PostMessageOptions(call);
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

            implementation.postMessage(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void show(PluginCall call) {
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

            implementation.show(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    protected void handleOnPause() {
        implementation.handleOnPause();
    }

    @Override
    protected void handleOnResume() {
        implementation.handleOnResume();
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
