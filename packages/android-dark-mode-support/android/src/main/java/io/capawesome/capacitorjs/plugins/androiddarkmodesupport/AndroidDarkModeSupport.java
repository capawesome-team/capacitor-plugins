package io.capawesome.capacitorjs.plugins.androiddarkmodesupport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.webkit.WebSettings;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;
import com.getcapacitor.Bridge;

public class AndroidDarkModeSupport {

    private Context context;
    private Bridge bridge;

    AndroidDarkModeSupport(Context context, Bridge bridge) {
        this.context = context;
        this.bridge = bridge;
    }

    @SuppressLint("RestrictedApi")
    public void syncDarkMode() {
        WebSettings settings = bridge.getWebView().getSettings();

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            int nightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            boolean isNightModeEnabled = nightMode == Configuration.UI_MODE_NIGHT_YES;
            int forceDarkMode = (isNightModeEnabled ? WebSettingsCompat.FORCE_DARK_ON : WebSettingsCompat.FORCE_DARK_OFF);
            WebSettingsCompat.setForceDark(settings, forceDarkMode);
        }

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)) {
            WebSettingsCompat.setForceDarkStrategy(settings, WebSettingsCompat.DARK_STRATEGY_WEB_THEME_DARKENING_ONLY);
        }
    }
}
