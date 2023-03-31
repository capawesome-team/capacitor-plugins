package io.capawesome.capacitorjs.plugins.cloudinary;

import androidx.annotation.NonNull;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.HashMap;
import java.util.Map;

@CapacitorPlugin(name = "Cloudinary")
public class CloudinaryPlugin extends Plugin {

    public static final String TAG = "Cloudinary";
    public static final String ERROR_NOT_INITIALIZED = "Plugin is not initialized.";
    public static final String ERROR_CLOUD_NAME_MISSING = "cloudName must be provided.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_RESOURCE_TYPE_MISSING = "resourceType must be provided.";
    public static final String ERROR_UPLOAD_PRESET_MISSING = "uploadPreset must be provided.";
    public static final String ERROR_URL_MISSING = "url must be provided.";
    public static Bridge staticBridge = null;

    private Cloudinary implementation;
    private boolean initialized = false;

    public void load() {
        staticBridge = this.bridge;
        implementation = new Cloudinary(this);
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            if (initialized) {
                return;
            }
            String cloudName = call.getString("cloudName");
            if (cloudName == null) {
                call.reject(ERROR_CLOUD_NAME_MISSING);
                return;
            }

            implementation.initialize(cloudName);
            initialized = true;
            call.resolve();
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void uploadResource(PluginCall call) {
        try {
            if (!initialized) {
                call.reject(ERROR_NOT_INITIALIZED);
                return;
            }
            String resourceType = call.getString("resourceType");
            if (resourceType == null) {
                call.reject(ERROR_RESOURCE_TYPE_MISSING);
                return;
            }
            String uploadPreset = call.getString("uploadPreset");
            if (uploadPreset == null) {
                call.reject(ERROR_UPLOAD_PRESET_MISSING);
                return;
            }
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            String publicId = call.getString("publicId");

            implementation.uploadResource(
                resourceType,
                path,
                uploadPreset,
                publicId,
                new UploadResourceResultCallback() {
                    @Override
                    public void success(Map resultData) {
                        JSObject result = CloudinaryHelper.createUploadResourceResult(resultData);
                        call.resolve(result);
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void downloadResource(PluginCall call) {
        try {
            if (!initialized) {
                call.reject(ERROR_NOT_INITIALIZED);
                return;
            }
            String url = call.getString("url");
            if (url == null) {
                call.reject(ERROR_URL_MISSING);
                return;
            }

            implementation.downloadResource(
                url,
                new DownloadResourceResultCallback() {
                    @Override
                    public void success(String path) {
                        JSObject result = new JSObject();
                        result.put("path", path);
                        call.resolve(result);
                    }

                    @Override
                    public void error(String message) {
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            call.reject(exception.getMessage());
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    public Cloudinary getImplementation() {
        return implementation;
    }

    public static void onDownloadCompleted(long downloadId) {
        CloudinaryPlugin plugin = CloudinaryPlugin.getCloudinaryPluginInstance();
        if (plugin != null) {
            plugin.getImplementation().handleDownloadCompleted(downloadId);
        }
    }

    private static CloudinaryPlugin getCloudinaryPluginInstance() {
        if (staticBridge == null || staticBridge.getWebView() == null) {
            return null;
        }
        PluginHandle handle = staticBridge.getPlugin("Cloudinary");
        if (handle == null) {
            return null;
        }
        return (CloudinaryPlugin) handle.getInstance();
    }
}
