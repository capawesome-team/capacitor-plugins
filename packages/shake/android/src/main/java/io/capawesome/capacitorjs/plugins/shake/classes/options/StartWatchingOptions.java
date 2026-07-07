package io.capawesome.capacitorjs.plugins.shake.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.shake.classes.CustomExceptions;

public class StartWatchingOptions {

    public static final String SENSITIVITY_HARD = "hard";
    public static final String SENSITIVITY_LIGHT = "light";
    public static final String SENSITIVITY_MEDIUM = "medium";

    private static final float SHAKE_THRESHOLD_HARD = 3.4f;
    private static final float SHAKE_THRESHOLD_LIGHT = 2.3f;
    private static final float SHAKE_THRESHOLD_MEDIUM = 2.7f;

    private final float shakeThreshold;

    public StartWatchingOptions(@NonNull PluginCall call) throws Exception {
        String sensitivity = call.getString("sensitivity", SENSITIVITY_MEDIUM);
        this.shakeThreshold = StartWatchingOptions.getShakeThresholdForSensitivity(sensitivity);
    }

    public float getShakeThreshold() {
        return shakeThreshold;
    }

    private static float getShakeThresholdForSensitivity(@NonNull String sensitivity) throws Exception {
        switch (sensitivity) {
            case SENSITIVITY_HARD:
                return SHAKE_THRESHOLD_HARD;
            case SENSITIVITY_LIGHT:
                return SHAKE_THRESHOLD_LIGHT;
            case SENSITIVITY_MEDIUM:
                return SHAKE_THRESHOLD_MEDIUM;
            default:
                throw CustomExceptions.INVALID_SENSITIVITY;
        }
    }
}
