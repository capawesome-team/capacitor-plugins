package io.capawesome.capacitorjs.plugins.accessibilitypreferences;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.classes.results.GetPreferencesResult;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.interfaces.NonEmptyResultCallback;

public class AccessibilityPreferences {

    private static final String SETTING_HIGH_TEXT_CONTRAST_ENABLED = "high_text_contrast_enabled";
    private static final String SETTING_INVERSION_ENABLED = "accessibility_display_inversion_enabled";

    @NonNull
    private final Context context;

    public AccessibilityPreferences(@NonNull Context context) {
        this.context = context;
    }

    public void getPreferences(@NonNull NonEmptyResultCallback<GetPreferencesResult> callback) {
        GetPreferencesResult result = new GetPreferencesResult(
            getFontScale(),
            isReduceMotionEnabled(),
            isBoldTextEnabled(),
            isInvertColorsEnabled(),
            null,
            isHighContrastEnabled()
        );
        callback.success(result);
    }

    private float getFontScale() {
        return context.getResources().getConfiguration().fontScale;
    }

    @Nullable
    private Boolean isBoldTextEnabled() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return null;
        }
        int fontWeightAdjustment = context.getResources().getConfiguration().fontWeightAdjustment;
        if (fontWeightAdjustment == Configuration.FONT_WEIGHT_ADJUSTMENT_UNDEFINED) {
            return false;
        }
        return fontWeightAdjustment > 0;
    }

    @Nullable
    private Boolean isHighContrastEnabled() {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), SETTING_HIGH_TEXT_CONTRAST_ENABLED, 0) == 1;
        } catch (Exception exception) {
            return null;
        }
    }

    private boolean isInvertColorsEnabled() {
        return Settings.Secure.getInt(context.getContentResolver(), SETTING_INVERSION_ENABLED, 0) == 1;
    }

    private boolean isReduceMotionEnabled() {
        ContentResolver resolver = context.getContentResolver();
        return Settings.Global.getFloat(resolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) == 0f;
    }
}
