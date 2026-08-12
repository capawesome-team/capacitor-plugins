package io.capawesome.capacitorjs.plugins.screenbrightness;

import android.app.Activity;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.screenbrightness.classes.options.SetBrightnessOptions;
import io.capawesome.capacitorjs.plugins.screenbrightness.classes.results.GetBrightnessResult;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.screenbrightness.interfaces.NonEmptyResultCallback;

public class ScreenBrightness {

    private static final int MAXIMUM_SYSTEM_BRIGHTNESS = 255;

    @NonNull
    private final ScreenBrightnessPlugin plugin;

    public ScreenBrightness(@NonNull ScreenBrightnessPlugin plugin) {
        this.plugin = plugin;
    }

    public void getBrightness(@NonNull NonEmptyResultCallback<GetBrightnessResult> callback) {
        runOnUiThread(() -> {
            float brightness = getWindow().getAttributes().screenBrightness;
            if (brightness < 0) {
                brightness = getSystemBrightness();
            }
            callback.success(new GetBrightnessResult(brightness));
        });
    }

    public void resetBrightness(@NonNull EmptyCallback callback) {
        runOnUiThread(() -> {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
            window.setAttributes(attributes);
            callback.success();
        });
    }

    public void setBrightness(@NonNull SetBrightnessOptions options, @NonNull EmptyCallback callback) {
        float brightness = options.getBrightness();
        runOnUiThread(() -> {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = brightness;
            window.setAttributes(attributes);
            callback.success();
        });
    }

    private float getSystemBrightness() {
        int brightness = Settings.System.getInt(plugin.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 0);
        return (float) brightness / MAXIMUM_SYSTEM_BRIGHTNESS;
    }

    @NonNull
    private Window getWindow() {
        return plugin.getActivity().getWindow();
    }

    private void runOnUiThread(@NonNull Runnable runnable) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(runnable);
    }
}
