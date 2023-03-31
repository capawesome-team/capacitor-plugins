package io.capawesome.capacitorjs.plugins.photoeditor;

import android.app.Activity;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "PhotoEditor")
public class PhotoEditorPlugin extends Plugin {

    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_EDIT_PHOTO_FAILED = "editPhoto failed.";
    public static final String ERROR_EDIT_PHOTO_CANCELED = "editPhoto canceled.";

    private PhotoEditor implementation;

    @Override
    public void load() {
        implementation = new PhotoEditor(getBridge());
    }

    @PluginMethod
    public void editPhoto(PluginCall call) {
        String path = call.getString("path");
        if (path == null) {
            call.reject(ERROR_PATH_MISSING);
            return;
        }
        Intent intent = implementation.createEditPhotoIntent(path);
        if (intent == null) {
            call.reject(ERROR_EDIT_PHOTO_FAILED);
            return;
        }
        startActivityForResult(call, intent, "handleEditPhotoResult");
    }

    @ActivityCallback
    private void handleEditPhotoResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }
        int resultCode = result.getResultCode();
        switch (resultCode) {
            case Activity.RESULT_OK:
                call.resolve();
                break;
            case Activity.RESULT_CANCELED:
                call.reject(ERROR_EDIT_PHOTO_CANCELED);
                break;
            default:
                call.reject(ERROR_EDIT_PHOTO_FAILED);
        }
    }
}
