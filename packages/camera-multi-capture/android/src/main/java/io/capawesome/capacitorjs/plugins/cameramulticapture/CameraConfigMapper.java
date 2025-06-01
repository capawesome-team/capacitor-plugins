package io.capawesome.capacitorjs.plugins.cameramulticapture;

import android.util.Size;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import com.getcapacitor.JSObject;
import android.view.ViewGroup;

public class CameraConfigMapper {
    public static CameraConfig fromJSObject(JSObject data) {
        CameraConfig config = new CameraConfig();

        String direction = data.getString("direction", "back");
        config.lensFacing = "front".equals(direction)
                ? CameraSelector.LENS_FACING_FRONT
                : CameraSelector.LENS_FACING_BACK;

        String captureMode = data.getString("captureMode", "minimizeLatency");
        config.captureMode = "maxQuality".equals(captureMode)
                ? ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
                : ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY;

        JSObject resolution = data.getJSObject("resolution");
        if (resolution != null) {
            int width = resolution.getInteger("width", 1280);
            int height = resolution.getInteger("height", 720);
            config.resolution = new Size(width, height);
        }

        try {
            config.zoomRatio = data.has("zoom") ? (float) data.getDouble("zoom") : 1.0f;
        } catch (Exception e) {
            config.zoomRatio = 1.0f;
        }
        config.jpegQuality = data.has("quality") ? data.getInteger("quality") : 85;
        config.autoFocus = data.getBoolean("autoFocus", true);

        JSObject previewRect = data.getJSObject("previewRect");
        if (previewRect != null) {
            if (previewRect.has("width")) {
                config.previewWidth = previewRect.getInteger("width", ViewGroup.LayoutParams.MATCH_PARENT);
            }
            if (previewRect.has("height")) {
                config.previewHeight = previewRect.getInteger("height", ViewGroup.LayoutParams.MATCH_PARENT);
            }
            if (previewRect.has("x")) {
                config.previewX = previewRect.getInteger("x", 0);
            }
            if (previewRect.has("y")) {
                config.previewY = previewRect.getInteger("y", 0);
            }
        }

        return config;
    }
}