package io.capawesome.capacitorjs.plugins.inappbrowser.classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.OpenInWebViewOptions;
import io.capawesome.capacitorjs.plugins.inappbrowser.classes.options.WebViewToolbarOptions;
import java.util.ArrayList;
import java.util.List;

public class WebViewDialog extends Dialog {

    public interface Listener {
        void onClosed(@NonNull WebViewDialog dialog);
        void onMessageReceived(@NonNull String data);
        void onPageLoaded();
        void onPageNavigationCompleted(@NonNull String url);
    }

    private static final String BRIDGE_JAVASCRIPT =
        "window.CapacitorInAppBrowser = window.CapacitorInAppBrowser || { postMessage: function (data) { CapacitorInAppBrowserAndroid.postMessage(JSON.stringify(data)); } };";
    private static final String BRIDGE_NAME = "CapacitorInAppBrowserAndroid";
    private static final int TOOLBAR_HEIGHT_IN_DP = 56;

    @Nullable
    private TextView backButton;

    @Nullable
    private TextView forwardButton;

    private boolean initialLoadNotified = false;

    @NonNull
    private final Listener listener;

    @NonNull
    private final OpenInWebViewOptions options;

    @Nullable
    private TextView titleView;

    @Nullable
    private WebView webView;

    public WebViewDialog(@NonNull Context context, @NonNull OpenInWebViewOptions options, @NonNull Listener listener) {
        super(context, android.R.style.Theme_Material_Light_NoActionBar);
        this.options = options;
        this.listener = listener;
    }

    public void executeScript(@NonNull String script, @NonNull ValueCallback<String> resultCallback) {
        WebView webView = this.webView;
        if (webView != null) {
            webView.evaluateJavascript(script, resultCallback);
        }
    }

    public void handleOnPause() {
        WebView webView = this.webView;
        if (options.getPauseMediaWhenHidden() && webView != null) {
            webView.onPause();
        }
    }

    public void handleOnResume() {
        WebView webView = this.webView;
        if (options.getPauseMediaWhenHidden() && webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onBackPressed() {
        WebView webView = this.webView;
        if (options.getHardwareBackButton() && webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            dismiss();
        }
    }

    public void postMessage(@NonNull String data) {
        WebView webView = this.webView;
        if (webView != null) {
            webView.evaluateJavascript(
                "window.dispatchEvent(new CustomEvent('capacitorInAppBrowserMessage', { detail: " + data + " }));",
                null
            );
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = createWebView();
        this.webView = webView;
        setContentView(createContentView(webView));
        setOnDismissListener(dialog -> handleDismiss());
        webView.loadUrl(options.getUri().toString(), options.getHeaders());
    }

    @NonNull
    private LinearLayout createContentView(@NonNull WebView webView) {
        LinearLayout contentView = new LinearLayout(getContext());
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setFitsSystemWindows(true);
        contentView.setBackgroundColor(Color.WHITE);
        if (options.getToolbar().getVisible()) {
            contentView.addView(
                createToolbar(),
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(TOOLBAR_HEIGHT_IN_DP))
            );
        }
        contentView.addView(webView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        return contentView;
    }

    @NonNull
    private LinearLayout createToolbar() {
        WebViewToolbarOptions toolbarOptions = options.getToolbar();
        int textColor = toolbarOptions.getColor() == null ? Color.BLACK : toolbarOptions.getColor();

        LinearLayout toolbar = new LinearLayout(getContext());
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setBackgroundColor(toolbarOptions.getBackgroundColor() == null ? Color.WHITE : toolbarOptions.getBackgroundColor());
        toolbar.setPadding(dpToPx(8), 0, dpToPx(8), 0);

        TextView closeButton = createToolbarButton(toolbarOptions.getCloseButtonText(), textColor);
        closeButton.setOnClickListener(view -> dismiss());
        toolbar.addView(closeButton);

        TextView titleView = new TextView(getContext());
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        titleView.setTypeface(null, Typeface.BOLD);
        titleView.setTextColor(textColor);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        titleView.setPadding(dpToPx(8), 0, dpToPx(8), 0);
        toolbar.addView(titleView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        this.titleView = titleView;

        if (toolbarOptions.getShowNavigationButtons()) {
            TextView backButton = createToolbarButton("‹", textColor);
            backButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            backButton.setOnClickListener(view -> {
                WebView webView = this.webView;
                if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                }
            });
            toolbar.addView(backButton);
            this.backButton = backButton;

            TextView forwardButton = createToolbarButton("›", textColor);
            forwardButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            forwardButton.setOnClickListener(view -> {
                WebView webView = this.webView;
                if (webView != null && webView.canGoForward()) {
                    webView.goForward();
                }
            });
            toolbar.addView(forwardButton);
            this.forwardButton = forwardButton;

            updateNavigationButtons();
        }

        return toolbar;
    }

    @NonNull
    private TextView createToolbarButton(@NonNull String text, int textColor) {
        TextView button = new TextView(getContext());
        button.setText(text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        button.setTextColor(textColor);
        button.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        button.setClickable(true);
        return button;
    }

    @NonNull
    @SuppressLint("SetJavaScriptEnabled")
    private WebView createWebView() {
        WebView webView = new WebView(getContext());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(options.getMediaPlaybackRequiresUserAction());
        settings.setSupportZoom(options.getAllowZoom());
        settings.setBuiltInZoomControls(options.getAllowZoom());
        settings.setDisplayZoomControls(false);
        String userAgent = options.getUserAgent();
        if (userAgent != null) {
            settings.setUserAgentString(userAgent);
        }
        webView.addJavascriptInterface(new Bridge(), BRIDGE_NAME);
        webView.setWebViewClient(
            new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    injectBridgeJavaScript();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    injectBridgeJavaScript();
                    updateNavigationButtons();
                    updateTitle();
                    if (!initialLoadNotified) {
                        initialLoadNotified = true;
                        listener.onPageLoaded();
                    }
                    if (url != null) {
                        listener.onPageNavigationCompleted(url);
                    }
                }

                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                    updateNavigationButtons();
                    updateTitle();
                }
            }
        );
        webView.setWebChromeClient(
            new WebChromeClient() {
                @Override
                public void onPermissionRequest(PermissionRequest request) {
                    handlePermissionRequest(request);
                }

                @Override
                public void onReceivedTitle(WebView view, String title) {
                    updateTitle();
                }
            }
        );
        return webView;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getContext().getResources().getDisplayMetrics().density);
    }

    private void handleDismiss() {
        WebView webView = this.webView;
        if (webView != null) {
            webView.destroy();
            this.webView = null;
        }
        listener.onClosed(this);
    }

    private void handlePermissionRequest(@NonNull PermissionRequest request) {
        List<String> grantedResources = new ArrayList<>();
        for (String resource : request.getResources()) {
            if (PermissionRequest.RESOURCE_VIDEO_CAPTURE.equals(resource) && hasPermission(Manifest.permission.CAMERA)) {
                grantedResources.add(resource);
            } else if (PermissionRequest.RESOURCE_AUDIO_CAPTURE.equals(resource) && hasPermission(Manifest.permission.RECORD_AUDIO)) {
                grantedResources.add(resource);
            } else if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID.equals(resource)) {
                grantedResources.add(resource);
            }
        }
        if (grantedResources.isEmpty()) {
            request.deny();
        } else {
            request.grant(grantedResources.toArray(new String[0]));
        }
    }

    private boolean hasPermission(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void injectBridgeJavaScript() {
        WebView webView = this.webView;
        if (webView != null) {
            webView.evaluateJavascript(BRIDGE_JAVASCRIPT, null);
        }
    }

    private void updateNavigationButtons() {
        WebView webView = this.webView;
        TextView backButton = this.backButton;
        TextView forwardButton = this.forwardButton;
        if (webView == null || backButton == null || forwardButton == null) {
            return;
        }
        backButton.setAlpha(webView.canGoBack() ? 1.0f : 0.3f);
        forwardButton.setAlpha(webView.canGoForward() ? 1.0f : 0.3f);
    }

    private void updateTitle() {
        TextView titleView = this.titleView;
        WebView webView = this.webView;
        if (titleView == null || webView == null) {
            return;
        }
        WebViewToolbarOptions toolbarOptions = options.getToolbar();
        if (toolbarOptions.getTitle() != null) {
            titleView.setText(toolbarOptions.getTitle());
        } else if (toolbarOptions.getShowUrl()) {
            titleView.setText(webView.getUrl());
        } else {
            titleView.setText(webView.getTitle());
        }
    }

    private class Bridge {

        @JavascriptInterface
        public void postMessage(@Nullable String data) {
            if (data != null) {
                listener.onMessageReceived(data);
            }
        }
    }
}
