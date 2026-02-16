package io.capawesome.capacitorjs.plugins.applesignin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import org.json.JSONObject;

public class AppleSignInActivity extends Activity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_REDIRECT_URL = "redirectUrl";
    public static final String EXTRA_AUTHORIZATION_CODE = "authorizationCode";
    public static final String EXTRA_IDENTITY_TOKEN = "identityToken";
    public static final String EXTRA_USER = "user";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_GIVEN_NAME = "givenName";
    public static final String EXTRA_FAMILY_NAME = "familyName";
    public static final String EXTRA_STATE = "state";

    private String redirectUrl;
    private TextView domainTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(EXTRA_URL);
        redirectUrl = getIntent().getStringExtra(EXTRA_REDIRECT_URL);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Toolbar
        RelativeLayout toolbar = new RelativeLayout(this);
        toolbar.setBackgroundColor(0xFFF8F8F8);
        LinearLayout.LayoutParams toolbarParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(56)
        );
        toolbar.setLayoutParams(toolbarParams);

        ImageButton closeButton = new ImageButton(this);
        closeButton.setImageResource(R.drawable.ic_close);
        closeButton.setBackgroundColor(0x00000000);
        int closePadding = dpToPx(12);
        closeButton.setPadding(closePadding, closePadding, closePadding, closePadding);
        closeButton.setContentDescription("Close");
        closeButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(dpToPx(48), dpToPx(48));
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        closeParams.addRule(RelativeLayout.CENTER_VERTICAL);
        closeParams.setMarginStart(dpToPx(4));

        domainTextView = new TextView(this);
        domainTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        domainTextView.setTextColor(0xFF5F6368);
        domainTextView.setSingleLine(true);
        domainTextView.setEllipsize(TextUtils.TruncateAt.END);
        domainTextView.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams domainParams = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        domainParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        int domainHorizontalMargin = dpToPx(56);
        domainParams.setMarginStart(domainHorizontalMargin);
        domainParams.setMarginEnd(domainHorizontalMargin);

        toolbar.addView(closeButton, closeParams);
        toolbar.addView(domainTextView, domainParams);

        // Progress bar
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.getProgressDrawable().setColorFilter(0xFF007AFF, PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.GONE);
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(3)
        );

        // WebView
        WebView webView = new WebView(this);
        LinearLayout.LayoutParams webViewParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        );

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.addJavascriptInterface(new AppleSignInBridge(), "AppleSignInBridge");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(newProgress < 100 ? View.VISIBLE : View.GONE);
            }
        });
        webView.setWebViewClient(
            new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    updateDomainText(url);
                    String js =
                        "(function() {" +
                        "  var origSubmit = HTMLFormElement.prototype.submit;" +
                        "  HTMLFormElement.prototype.submit = function() {" +
                        "    if (this.action && this.action.indexOf('" +
                        escapeJsString(redirectUrl) +
                        "') !== -1) {" +
                        "      var data = {};" +
                        "      for (var i = 0; i < this.elements.length; i++) {" +
                        "        if (this.elements[i].name) {" +
                        "          data[this.elements[i].name] = this.elements[i].value;" +
                        "        }" +
                        "      }" +
                        "      window.AppleSignInBridge.onFormData(JSON.stringify(data));" +
                        "      return;" +
                        "    }" +
                        "    origSubmit.call(this);" +
                        "  };" +
                        "})();";
                    view.evaluateJavascript(js, null);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String requestUrl = request.getUrl().toString();
                    if (requestUrl.startsWith(redirectUrl)) {
                        handleRedirectUrl(requestUrl);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }
            }
        );

        rootLayout.addView(toolbar);
        rootLayout.addView(progressBar, progressParams);
        rootLayout.addView(webView, webViewParams);
        setContentView(rootLayout);
        setupStatusBar();

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            v.setPadding(0, insets.top, 0, 0);
            return WindowInsetsCompat.CONSUMED;
        });

        webView.loadUrl(url);
    }

    private void handleRedirectUrl(@NonNull String url) {
        Uri uri = Uri.parse(url);
        String code = uri.getQueryParameter("code");
        String idToken = uri.getQueryParameter("id_token");
        String state = uri.getQueryParameter("state");

        if (code != null && idToken != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_AUTHORIZATION_CODE, code);
            resultIntent.putExtra(EXTRA_IDENTITY_TOKEN, idToken);
            resultIntent.putExtra(EXTRA_STATE, state);
            setResult(RESULT_OK, resultIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()
        );
    }

    @NonNull
    private static String escapeJsString(@NonNull String input) {
        return input.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
    }

    @SuppressWarnings("deprecation")
    private void setupStatusBar() {
        Window window = getWindow();
        window.setStatusBarColor(0xFFF8F8F8);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            android.view.WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                controller.setSystemBarsAppearance(
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                );
            }
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void updateDomainText(@NonNull String url) {
        try {
            String host = Uri.parse(url).getHost();
            if (host != null) {
                domainTextView.setText(host);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private class AppleSignInBridge {

        @JavascriptInterface
        public void onFormData(String jsonString) {
            try {
                JSONObject data = new JSONObject(jsonString);

                String code = data.optString("code", null);
                String idToken = data.optString("id_token", null);
                String state = data.optString("state", null);
                String userJson = data.optString("user", null);

                String email = null;
                String givenName = null;
                String familyName = null;

                if (userJson != null && !userJson.isEmpty()) {
                    try {
                        JSONObject userObj = new JSONObject(userJson);
                        email = userObj.optString("email", null);
                        JSONObject nameObj = userObj.optJSONObject("name");
                        if (nameObj != null) {
                            givenName = nameObj.optString("firstName", null);
                            familyName = nameObj.optString("lastName", null);
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_AUTHORIZATION_CODE, code);
                resultIntent.putExtra(EXTRA_IDENTITY_TOKEN, idToken);
                resultIntent.putExtra(EXTRA_STATE, state);
                resultIntent.putExtra(EXTRA_EMAIL, email);
                resultIntent.putExtra(EXTRA_GIVEN_NAME, givenName);
                resultIntent.putExtra(EXTRA_FAMILY_NAME, familyName);

                setResult(RESULT_OK, resultIntent);
                finish();
            } catch (Exception e) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}
