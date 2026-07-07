package io.capawesome.capacitorjs.plugins.inappbrowser;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.WebStorage;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.WebViewDialog;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.events.BrowserMessageReceivedEvent;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.events.BrowserNavigationCompletedEvent;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.events.BrowserUrlChangedEvent;
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
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONTokener;

public class InAppBrowser {

    @NonNull
    private final InAppBrowserPlugin plugin;

    private boolean customTabOpened = false;

    @Nullable
    private WebViewDialog webViewDialog;

    public InAppBrowser(@NonNull InAppBrowserPlugin plugin) {
        this.plugin = plugin;
    }

    public void clearCache(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebView webView = new WebView(plugin.getContext());
                webView.clearCache(true);
                webView.destroy();
                callback.success();
            });
    }

    public void clearSessionData(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookies(value -> {
                    cookieManager.flush();
                    WebStorage.getInstance().deleteAllData();
                    callback.success();
                });
            });
    }

    public void close(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebViewDialog webViewDialog = this.webViewDialog;
                if (webViewDialog != null) {
                    webViewDialog.dismiss();
                    callback.success();
                    return;
                }
                if (customTabOpened) {
                    Intent intent = new Intent(plugin.getContext(), plugin.getActivity().getClass());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    plugin.getActivity().startActivity(intent);
                    callback.success();
                    return;
                }
                callback.error(CustomExceptions.NO_BROWSER_OPEN);
            });
    }

    public void executeScript(@NonNull ExecuteScriptOptions options, @NonNull NonEmptyResultCallback<ExecuteScriptResult> callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebViewDialog webViewDialog = this.webViewDialog;
                if (webViewDialog == null) {
                    callback.error(CustomExceptions.NO_BROWSER_OPEN);
                    return;
                }
                webViewDialog.executeScript(options.getScript(), value -> {
                    String result = value == null || value.equals("null") ? null : value;
                    callback.success(new ExecuteScriptResult(result));
                });
            });
    }

    public void getCookies(@NonNull GetCookiesOptions options, @NonNull NonEmptyResultCallback<GetCookiesResult> callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                Map<String, String> cookies = new HashMap<>();
                String cookieString = CookieManager.getInstance().getCookie(options.getUrl());
                if (cookieString != null) {
                    for (String cookie : cookieString.split("; ")) {
                        int separatorIndex = cookie.indexOf('=');
                        if (separatorIndex < 0) {
                            continue;
                        }
                        String name = cookie.substring(0, separatorIndex);
                        String value = cookie.substring(separatorIndex + 1);
                        cookies.put(name, value);
                    }
                }
                callback.success(new GetCookiesResult(cookies));
            });
    }

    public void handleOnPause() {
        WebViewDialog webViewDialog = this.webViewDialog;
        if (webViewDialog != null) {
            webViewDialog.handleOnPause();
        }
    }

    public void handleOnResume() {
        WebViewDialog webViewDialog = this.webViewDialog;
        if (webViewDialog != null) {
            webViewDialog.handleOnResume();
        }
        if (customTabOpened) {
            customTabOpened = false;
            plugin.notifyBrowserClosedListeners();
        }
    }

    public void openInExternalBrowser(@NonNull OpenInExternalBrowserOptions options, @NonNull EmptyCallback callback) {
        Intent intent = new Intent(Intent.ACTION_VIEW, options.getUri());
        try {
            plugin.getActivity().startActivity(intent);
            callback.success();
        } catch (ActivityNotFoundException exception) {
            callback.error(CustomExceptions.BROWSER_NOT_FOUND);
        }
    }

    public void openInSystemBrowser(@NonNull OpenInSystemBrowserOptions options, @NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                Integer toolbarColor = options.getToolbarColor();
                if (toolbarColor != null) {
                    builder.setDefaultColorSchemeParams(new CustomTabColorSchemeParams.Builder().setToolbarColor(toolbarColor).build());
                }
                builder.setShowTitle(options.getShowTitle());
                builder.setUrlBarHidingEnabled(options.getHideToolbarOnScroll());
                CustomTabsIntent customTabsIntent = builder.build();
                try {
                    customTabsIntent.launchUrl(plugin.getActivity(), options.getUri());
                    customTabOpened = true;
                    callback.success();
                } catch (ActivityNotFoundException exception) {
                    callback.error(CustomExceptions.BROWSER_NOT_FOUND);
                }
            });
    }

    public void openInWebView(@NonNull OpenInWebViewOptions options, @NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebViewDialog existingWebViewDialog = this.webViewDialog;
                if (existingWebViewDialog != null) {
                    existingWebViewDialog.dismiss();
                }
                WebViewDialog webViewDialog = new WebViewDialog(
                    plugin.getActivity(),
                    options,
                    new WebViewDialog.Listener() {
                        @Override
                        public void onClosed(@NonNull WebViewDialog dialog) {
                            if (InAppBrowser.this.webViewDialog == dialog) {
                                InAppBrowser.this.webViewDialog = null;
                            }
                            plugin.notifyBrowserClosedListeners();
                        }

                        @Override
                        public void onMessageReceived(@NonNull String data) {
                            handleMessageReceived(data);
                        }

                        @Override
                        public void onNavigationCompleted(@NonNull String url) {
                            plugin.notifyBrowserNavigationCompletedListeners(new BrowserNavigationCompletedEvent(url));
                        }

                        @Override
                        public void onPageLoaded() {
                            plugin.notifyBrowserPageLoadedListeners();
                        }

                        @Override
                        public void onUrlChanged(@NonNull String url) {
                            plugin.notifyBrowserUrlChangedListeners(new BrowserUrlChangedEvent(url));
                        }
                    }
                );
                this.webViewDialog = webViewDialog;
                webViewDialog.show();
                callback.success();
            });
    }

    public void postMessage(@NonNull PostMessageOptions options, @NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebViewDialog webViewDialog = this.webViewDialog;
                if (webViewDialog == null) {
                    callback.error(CustomExceptions.NO_BROWSER_OPEN);
                    return;
                }
                webViewDialog.postMessage(options.getData().toString());
                callback.success();
            });
    }

    public void show(@NonNull EmptyCallback callback) {
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                WebViewDialog webViewDialog = this.webViewDialog;
                if (webViewDialog == null) {
                    callback.error(CustomExceptions.NO_BROWSER_OPEN);
                    return;
                }
                webViewDialog.showWebView();
                callback.success();
            });
    }

    private void handleMessageReceived(@NonNull String data) {
        Object value;
        try {
            value = new JSONTokener(data).nextValue();
        } catch (JSONException exception) {
            value = data;
        }
        plugin.notifyBrowserMessageReceivedListeners(new BrowserMessageReceivedEvent(value));
    }
}
