package io.capawesome.capacitorjs.plugins.screenbrightness.classes.options;

import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.screenbrightness.classes.CustomExceptions;

public class SetBrightnessOptions {

    private final float brightness;

    public SetBrightnessOptions(PluginCall call) throws Exception {
        this.brightness = SetBrightnessOptions.getBrightnessFromCall(call);
    }

    public float getBrightness() {
        return brightness;
    }

    private static float getBrightnessFromCall(PluginCall call) throws Exception {
        Double brightness = call.getDouble("brightness");
        if (brightness == null) {
            throw CustomExceptions.BRIGHTNESS_MISSING;
        }
        if (brightness < 0.0 || brightness > 1.0) {
            throw CustomExceptions.BRIGHTNESS_INVALID;
        }
        return brightness.floatValue();
    }
}
