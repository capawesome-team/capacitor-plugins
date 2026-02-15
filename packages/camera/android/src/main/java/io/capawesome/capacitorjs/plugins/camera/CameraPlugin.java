package io.capawesome.capacitorjs.plugins.camera;

import android.content.Intent;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.File;
import java.util.ArrayList;

@CapacitorPlugin(name = "Camera")
public class CameraPlugin extends Plugin {

    @PluginMethod
    public void takeMultiplePhotos(PluginCall call) {
        Intent intent = new Intent(getContext(), CameraActivity.class);
        startActivityForResult(call, intent, "handleCameraActivityResult");
    }

    @ActivityCallback
    private void handleCameraActivityResult(PluginCall call, ActivityResult result) {
        if (call == null) {
            return;
        }

        if (result.getResultCode() == android.app.Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                ArrayList<String> photoPaths = data.getStringArrayListExtra("photoPaths");
                if (photoPaths != null) {
                    JSObject ret = new JSObject();
                    JSArray photos = new JSArray();
                    for (String path : photoPaths) {
                        JSObject photo = new JSObject();
                        photo.put("path", path);
                        photo.put("webPath", getBridge().getLocalUrl(new File(path)));
                        photo.put("format", "jpeg");
                        photos.put(photo);
                    }
                    ret.put("photos", photos);
                    call.resolve(ret);
                } else {
                    call.reject("No photos returned from camera.");
                }
            } else {
                call.reject("No data returned from camera.");
            }
        } else {
            call.reject("Camera activity canceled.");
        }
    }
}
