package io.capawesome.capacitorjs.plugins.privacyscreen;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import io.capawesome.capacitorjs.plugins.privacyscreen.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.privacyscreen.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.privacyscreen.interfaces.NonEmptyResultCallback;

public class PrivacyScreen {

    @NonNull
    private final PrivacyScreenPlugin plugin;

    private boolean isObserving = false;

    @Nullable
    private Object screenCaptureCallback;

    public PrivacyScreen(@NonNull PrivacyScreenPlugin plugin) {
        this.plugin = plugin;
    }

    public void disable(@NonNull EmptyCallback callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            callback.success();
        });
    }

    public void enable(@NonNull EmptyCallback callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
            callback.success();
        });
    }

    public void isEnabled(@NonNull NonEmptyResultCallback<IsEnabledResult> callback) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(() -> {
            int flags = activity.getWindow().getAttributes().flags;
            boolean enabled = (flags & WindowManager.LayoutParams.FLAG_SECURE) != 0;
            callback.success(new IsEnabledResult(enabled));
        });
    }

    public void startObserving() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return;
        }
        if (isObserving) {
            return;
        }
        registerScreenCaptureCallback();
        isObserving = true;
    }

    public void stopObserving() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return;
        }
        if (!isObserving) {
            return;
        }
        unregisterScreenCaptureCallback();
        isObserving = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void registerScreenCaptureCallback() {
        Activity activity = plugin.getActivity();
        if (activity == null) {
            return;
        }
        Activity.ScreenCaptureCallback callback = () -> plugin.notifyScreenshotTakenListeners();
        screenCaptureCallback = callback;
        activity.registerScreenCaptureCallback(activity.getMainExecutor(), callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void unregisterScreenCaptureCallback() {
        Activity activity = plugin.getActivity();
        if (activity == null || screenCaptureCallback == null) {
            return;
        }
        activity.unregisterScreenCaptureCallback((Activity.ScreenCaptureCallback) screenCaptureCallback);
        screenCaptureCallback = null;
    }
}
