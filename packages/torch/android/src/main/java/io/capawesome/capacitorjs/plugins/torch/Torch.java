package io.capawesome.capacitorjs.plugins.torch;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class Torch {

    private final TorchPlugin plugin;

    private boolean isTorchEnabled = false;

    public Torch(TorchPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() throws CameraAccessException {
        CameraManager camera = (CameraManager) plugin.getContext().getSystemService(plugin.getContext().CAMERA_SERVICE);
        String cameraId = camera.getCameraIdList()[0];
        if (cameraId == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            camera.setTorchMode(cameraId, true);
        }
        isTorchEnabled = true;
    }

    public void disable() throws CameraAccessException {
        CameraManager camera = (CameraManager) plugin.getContext().getSystemService(plugin.getContext().CAMERA_SERVICE);
        String cameraId = camera.getCameraIdList()[0];
        if (cameraId == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            camera.setTorchMode(cameraId, false);
        }
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
}
