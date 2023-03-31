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

    public void lock(String orientationType) {
        switch (orientationType) {
            case ScreenOrientationType.LANDSCAPE:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                break;
            case ScreenOrientationType.LANDSCAPE_PRIMARY:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case ScreenOrientationType.LANDSCAPE_SECONDARY:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                break;
            case ScreenOrientationType.PORTRAIT:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                break;
            case ScreenOrientationType.PORTRAIT_PRIMARY:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case ScreenOrientationType.PORTRAIT_SECONDARY:
                bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                break;
        }
    }

    public void unlock() {
        bridge.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

    public String getCurrentOrientationType() {
        int rotation = bridge.getActivity().getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_90:
                return ScreenOrientationType.LANDSCAPE_PRIMARY;
            case Surface.ROTATION_180:
                return ScreenOrientationType.PORTRAIT_SECONDARY;
            case Surface.ROTATION_270:
                return ScreenOrientationType.LANDSCAPE_SECONDARY;
            default:
                return ScreenOrientationType.PORTRAIT_PRIMARY;
        }
    }
}
