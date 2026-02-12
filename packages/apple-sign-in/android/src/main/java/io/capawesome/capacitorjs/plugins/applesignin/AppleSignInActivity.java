package io.capawesome.capacitorjs.plugins.applesignin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(EXTRA_URL);
        redirectUrl = getIntent().getStringExtra(EXTRA_REDIRECT_URL);

        RelativeLayout rootLayout = new RelativeLayout(this);
        rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ImageButton closeButton = new ImageButton(this);
        closeButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        closeButton.setBackgroundColor(0x00000000);
        closeButton.setPadding(24, 24, 24, 24);
        closeButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        int closeButtonId = 1001;
        closeButton.setId(closeButtonId);

        WebView webView = new WebView(this);
        RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        );
        webViewParams.addRule(RelativeLayout.BELOW, closeButtonId);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.addJavascriptInterface(new AppleSignInBridge(), "AppleSignInBridge");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(
            new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
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

        rootLayout.addView(closeButton, closeParams);
        rootLayout.addView(webView, webViewParams);
        setContentView(rootLayout);

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

    @NonNull
    private static String escapeJsString(@NonNull String input) {
        return input.replace("\\", "\\\\").replace("'", "\\'").replace("\"", "\\\"");
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
