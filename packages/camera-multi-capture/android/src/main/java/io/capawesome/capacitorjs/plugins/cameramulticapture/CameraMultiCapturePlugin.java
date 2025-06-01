package io.capawesome.capacitorjs.plugins.cameramulticapture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.PermissionState;
import com.getcapacitor.JSObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.concurrent.Executor;

@CapacitorPlugin(name = "CameraMultiCapture")
public class CameraMultiCapturePlugin extends Plugin {

    private PreviewView previewView;
    private ImageCapture imageCapture;
    private Camera camera;
    private ProcessCameraProvider cameraProvider;
    private CameraConfig currentConfig = new CameraConfig();

    private void ensurePreviewView() {
        if (previewView != null) return;

        previewView = new PreviewView(getContext());
        
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        
        FrameLayout.LayoutParams params;
        if (currentConfig.previewWidth == ViewGroup.LayoutParams.MATCH_PARENT && 
            currentConfig.previewHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
            params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        } else {
            int widthInPixels = (int) (currentConfig.previewWidth * density);
            int heightInPixels = (int) (currentConfig.previewHeight * density);
            
            params = new FrameLayout.LayoutParams(widthInPixels, heightInPixels);
            
            params.leftMargin = (int) (currentConfig.previewX * density);
            params.topMargin = (int) (currentConfig.previewY * density);
        }
        
        previewView.setLayoutParams(params);
        previewView.setScaleType(PreviewView.ScaleType.FILL_CENTER);

        if (previewView.getParent() != null) {
            ((ViewGroup) previewView.getParent()).removeView(previewView);
        }

        ViewGroup rootView = getActivity().findViewById(android.R.id.content);
        rootView.addView(previewView, 0);

        if (bridge != null && bridge.getWebView() != null) {
            bridge.getWebView().setBackgroundColor(Color.TRANSPARENT);
            bridge.getWebView().setAlpha(1.0f);
            bridge.getWebView().bringToFront();
        }

        rootView.requestLayout();
        rootView.invalidate();
    }

    private void bindCameraSession() {
        if (cameraProvider == null || previewView == null) {
            Log.e("CameraMultiCapture", "Camera provider or previewView is null");
            return;
        }

        cameraProvider.unbindAll();

        Preview preview = new Preview.Builder().build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        imageCapture = new ImageCapture.Builder()
        .setCaptureMode(currentConfig.captureMode)
        .setTargetRotation(currentConfig.targetRotation)
        .setTargetResolution(currentConfig.resolution)
        .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
            .requireLensFacing(currentConfig.lensFacing)
            .build();

        camera = cameraProvider.bindToLifecycle(
            getActivity(),
            cameraSelector,
            preview,
            imageCapture
        );

        camera.getCameraControl().setZoomRatio(currentConfig.zoomRatio);
        previewView.setKeepScreenOn(true);
        Log.i("CameraMultiCapture", "Camera session bound successfully");
    }

    @PluginMethod
    public void start(PluginCall call) {
        Log.i("CameraMultiCapture", "start");
        currentConfig = CameraConfigMapper.fromJSObject(call.getData());

        getActivity().runOnUiThread(() -> {
            ensurePreviewView();
            ProcessCameraProvider.getInstance(getContext()).addListener(() -> {
                try {
                    cameraProvider = ProcessCameraProvider.getInstance(getContext()).get();
                    bindCameraSession();
                    call.resolve();
                } catch (Exception e) {
                    call.reject("Failed to bind camera session: " + e.getMessage(), e);
                }
            }, ContextCompat.getMainExecutor(getContext()));
        });
    }
 
    @PluginMethod
    public void stop(PluginCall call) {
        getActivity().runOnUiThread(() -> {
            try {
                if (cameraProvider != null) {
                    cameraProvider.unbindAll();
                    cameraProvider = null;
                }
                if (previewView != null) {
                    ViewGroup parent = (ViewGroup) previewView.getParent();
                    if (parent != null) {
                        parent.removeView(previewView);
                    }
                    previewView = null;
                }
                call.resolve();
            } catch (Exception e) {
                call.reject("Failed to stop camera: " + e.getMessage(), e);
            }
        });
    }

    @PluginMethod
    public void capture(PluginCall call) {
        if (imageCapture == null) {
            call.reject("ImageCapture not initialized");
            return;
        }

        int quality = call.getInt("quality", currentConfig.jpegQuality);

        try {
            File photoFile = new File(getContext().getCacheDir(), "photo_" + System.currentTimeMillis() + ".jpg");
            ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(getContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        JSObject result = new JSObject();
                        JSObject imageData = new JSObject();
                        
                        try {
                            Uri uri = Uri.fromFile(photoFile);
                            imageData.put("uri", uri.toString());
                            
                            byte[] bytes = java.nio.file.Files.readAllBytes(photoFile.toPath());
                            String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
                            String base64Data = "data:image/jpeg;base64," + base64;
                            imageData.put("base64", base64Data);
                            
                            result.put("value", imageData);
                        } catch (Exception e) {
                            call.reject("Failed to process photo file", e);
                            return;
                        }
                        call.resolve(result);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        call.reject("Photo capture failed: " + exception.getMessage());
                    }
                }
            );
        } catch (Exception e) {
            call.reject("Capture error: " + e.getMessage(), e);
        }
    }

    @PluginMethod
    public void setZoom(PluginCall call) {
        float zoom;
        if (call.hasOption("zoom")) {
            Double zoomValue = call.getDouble("zoom");
            zoom = zoomValue != null ? zoomValue.floatValue() : currentConfig.zoomRatio;
            Log.i("CameraMultiCapture", "setZoom called with zoom parameter: " + zoom);
        } else {
            Log.i("CameraMultiCapture", "setZoom called without zoom parameter");
            zoom = currentConfig.zoomRatio;
        }
        currentConfig.zoomRatio = zoom;
        getActivity().runOnUiThread(() -> {
            if (camera != null) {
                camera.getCameraControl().setZoomRatio(zoom);
                Log.i("CameraMultiCapture", "Zoom updated to: " + zoom);
            }
        });
        call.resolve();
    }

    @PluginMethod
    public void switchCamera(PluginCall call) {
        currentConfig.lensFacing = (currentConfig.lensFacing == CameraSelector.LENS_FACING_BACK)
            ? CameraSelector.LENS_FACING_FRONT
            : CameraSelector.LENS_FACING_BACK;

        try {
            getActivity().runOnUiThread(() -> {
                bindCameraSession();
                Log.i("CameraMultiCapture", "Switched camera to: " + currentConfig.lensFacing);
                call.resolve();
            });
        } catch (Exception e) {
            call.reject("Failed to switch camera: " + e.getMessage(), e);
        }
    }
}
