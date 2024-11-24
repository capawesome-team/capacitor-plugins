package io.capawesome.capacitorjs.plugins.torch;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraControl;
import java.lang.reflect.Method;

public class Torch {

    private final TorchPlugin plugin;

    private boolean isTorchEnabled = false;

    public Torch(TorchPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() throws CameraAccessException {
        setTorchMode(true);
        isTorchEnabled = true;
    }

    public void disable() throws CameraAccessException {
        setTorchMode(false);
        isTorchEnabled = false;
    }

    public boolean isAvailable() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }
        return plugin.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public boolean isEnabled() {
        return isTorchEnabled;
    }

    public void toggle() throws CameraAccessException {
        if (isTorchEnabled) {
            disable();
        } else {
            enable();
        }
    }

    private void setTorchMode(boolean enabled) throws CameraAccessException {
        CameraControl cameraControl = getCameraControl();
        if (cameraControl == null) {
            CameraManager cameraManager = getCameraManager();
            setTorchMode(cameraManager, enabled);
        } else {
            setTorchMode(cameraControl, enabled);
        }
    }

    private void setTorchMode(@NonNull CameraManager cameraManager, boolean enabled) throws CameraAccessException {
        String cameraId = cameraManager.getCameraIdList()[0];
        if (cameraId == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId, enabled);
        }
    }

    private void setTorchMode(@NonNull CameraControl cameraControl, boolean enabled) {
        cameraControl.enableTorch(enabled);
    }

    @Nullable
    private CameraControl getCameraControl() {
        try {
            Class<?> cls = Class.forName("io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.BarcodeScanner");
            Method method = cls.getMethod("getCameraControl");
            return (CameraControl) method.invoke(null);
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    private CameraManager getCameraManager() {
        return (CameraManager) plugin.getContext().getSystemService(plugin.getContext().CAMERA_SERVICE);
    }
}
