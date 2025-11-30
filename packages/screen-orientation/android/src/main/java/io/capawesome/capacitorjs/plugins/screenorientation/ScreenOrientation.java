package io.capawesome.capacitorjs.plugins.screenorientation;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import androidx.annotation.Nullable;
import com.getcapacitor.Bridge;

public class ScreenOrientation {

    interface ScreenOrientationChangeListener {
        void onScreenOrientationChanged();
    }

    @Nullable
    private ScreenOrientationChangeListener orientationChangeListener;

    @Nullable
    private int lastOrientationConfiguration;

    private Bridge bridge;

    ScreenOrientation(Bridge bridge) {
        this.bridge = bridge;
    }

    public void handleOnConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == lastOrientationConfiguration) {
            return;
        }
        this.lastOrientationConfiguration = newConfig.orientation;
        if (this.orientationChangeListener == null) {
            return;
        }
        this.orientationChangeListener.onScreenOrientationChanged();
    }

    public void setOrientationChangeListener(@Nullable ScreenOrientationChangeListener listener) {
        this.orientationChangeListener = listener;
    }

    @Nullable
    public ScreenOrientationChangeListener getOrientationChangeListener() {
        return orientationChangeListener;
    }

    public void lock(@Nullable String orientationType) throws Exception {
        final String key = (orientationType == null) ? getCurrentOrientationTypeKey() : orientationType;
        int value = ScreenOrientationType.toInt(key);
        bridge.getActivity().setRequestedOrientation(value);
    }

    public void unlock() {
        bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

    public String getCurrentOrientationTypeKey() {
        int rotation = bridge.getActivity().getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_90:
                return ScreenOrientationType.LANDSCAPE_PRIMARY.key();
            case Surface.ROTATION_180:
                return ScreenOrientationType.PORTRAIT_SECONDARY.key();
            case Surface.ROTATION_270:
                return ScreenOrientationType.LANDSCAPE_SECONDARY.key();
            default:
                return ScreenOrientationType.PORTRAIT_PRIMARY.key();
        }
    }
}
