package io.capawesome.capacitorjs.plugins.accessibilitypreferences.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.accessibilitypreferences.interfaces.Result;
import org.json.JSONObject;

public class GetPreferencesResult implements Result {

    private final float fontScale;

    @Nullable
    private final Boolean isBoldTextEnabled;

    @Nullable
    private final Boolean isHighContrastEnabled;

    @Nullable
    private final Boolean isInvertColorsEnabled;

    private final boolean isReduceMotionEnabled;

    @Nullable
    private final Boolean isReduceTransparencyEnabled;

    public GetPreferencesResult(
        float fontScale,
        boolean isReduceMotionEnabled,
        @Nullable Boolean isBoldTextEnabled,
        @Nullable Boolean isInvertColorsEnabled,
        @Nullable Boolean isReduceTransparencyEnabled,
        @Nullable Boolean isHighContrastEnabled
    ) {
        this.fontScale = fontScale;
        this.isReduceMotionEnabled = isReduceMotionEnabled;
        this.isBoldTextEnabled = isBoldTextEnabled;
        this.isInvertColorsEnabled = isInvertColorsEnabled;
        this.isReduceTransparencyEnabled = isReduceTransparencyEnabled;
        this.isHighContrastEnabled = isHighContrastEnabled;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("fontScale", fontScale);
        result.put("isReduceMotionEnabled", isReduceMotionEnabled);
        result.put("isBoldTextEnabled", isBoldTextEnabled == null ? JSONObject.NULL : isBoldTextEnabled);
        result.put("isInvertColorsEnabled", isInvertColorsEnabled == null ? JSONObject.NULL : isInvertColorsEnabled);
        result.put("isReduceTransparencyEnabled", isReduceTransparencyEnabled == null ? JSONObject.NULL : isReduceTransparencyEnabled);
        result.put("isHighContrastEnabled", isHighContrastEnabled == null ? JSONObject.NULL : isHighContrastEnabled);
        return result;
    }
}
