package io.capawesome.capacitorjs.plugins.cameramulticapture;

import android.util.Size;
import android.view.Surface;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import com.getcapacitor.JSObject;
import android.view.ViewGroup;

public class CameraConfig {
    public int lensFacing;
    public int captureMode;
    public Size resolution;
    public float zoomRatio;
    public int jpegQuality;
    public boolean autoFocus;
    public int targetRotation;
    public int previewWidth;
    public int previewHeight;
    public int previewX;
    public int previewY;


    public CameraConfig() {
        this.lensFacing = CameraSelector.LENS_FACING_BACK;
        this.captureMode = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY;
        this.resolution = new Size(1280, 720);
        this.zoomRatio = 1.0f;
        this.jpegQuality = 85;
        this.autoFocus = true;
        this.targetRotation = Surface.ROTATION_0;
        this.previewWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        this.previewHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        this.previewX = 0;
        this.previewY = 0;
    }
}