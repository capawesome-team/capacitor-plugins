package io.capawesome.capacitorjs.plugins.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

@CapacitorPlugin(name = "FilePicker")
public class FilePickerPlugin extends Plugin {

    public static final String TAG = "FilePickerPlugin";

    public static final String ERROR_PICK_FILE_FAILED = "pickFiles failed.";
    public static final String ERROR_PICK_FILE_CANCELED = "pickFiles canceled.";
    private FilePicker implementation;

    public void load() {
        implementation = new FilePicker(this.getBridge());
    }

    @PluginMethod
    public void convertHeicToJpeg(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }

    @PluginMethod
    public void pickFiles(PluginCall call) {
        try {
            int limit = call.getInt("limit", 0);
            JSArray types = call.getArray("types", null);
            String[] parsedTypes = parseTypesOption(types);

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, limit == 0);
            if (limit == 1 && parsedTypes != null && parsedTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, parsedTypes);
            }

            startActivityForResult(call, intent, "pickFilesResult");
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void pickImages(PluginCall call) {
        try {
            int limit = call.getInt("limit", 0);

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, limit == 0);
            intent.setType("image/*");
            intent.putExtra("multi-pick", limit == 0);
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] { "image/*" });

            startActivityForResult(call, intent, "pickFilesResult");
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void pickMedia(PluginCall call) {
        try {
            int limit = call.getInt("limit", 0);

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, limit == 0);
            intent.setType("*/*");
            intent.putExtra("multi-pick", limit == 0);
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] { "image/*", "video/*" });

            startActivityForResult(call, intent, "pickFilesResult");
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void pickVideos(PluginCall call) {
        try {
            int limit = call.getInt("limit", 0);

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, limit == 0);
            intent.setType("video/*");
            intent.putExtra("multi-pick", limit == 0);
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] { "video/*" });

            startActivityForResult(call, intent, "pickFilesResult");
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @Nullable
    private String[] parseTypesOption(@Nullable JSArray types) {
        if (types == null) {
            return null;
        }
        try {
            List<String> typesList = types.toList();
            if (typesList.contains("text/csv")) {
                typesList.add("text/comma-separated-values");
            }
            return typesList.toArray(new String[0]);
        } catch (JSONException exception) {
            Logger.error("parseTypesOption failed.", exception);
            return null;
        }
    }

    @ActivityCallback
    private void pickFilesResult(PluginCall call, ActivityResult result) {
        try {
            if (call == null) {
                return;
            }
            boolean readData = call.getBoolean("readData", false);
            int resultCode = result.getResultCode();
            switch (resultCode) {
                case Activity.RESULT_OK:
                    JSObject callResult = createPickFilesResult(result.getData(), readData);
                    call.resolve(callResult);
                    break;
                case Activity.RESULT_CANCELED:
                    call.reject(ERROR_PICK_FILE_CANCELED);
                    break;
                default:
                    call.reject(ERROR_PICK_FILE_FAILED);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    private JSObject createPickFilesResult(@Nullable Intent data, boolean readData) {
        JSObject callResult = new JSObject();
        List<JSObject> filesResultList = new ArrayList<>();
        if (data == null) {
            callResult.put("files", JSArray.from(filesResultList));
            return callResult;
        }
        List<Uri> uris = new ArrayList<>();
        if (data.getClipData() == null && data.getData() == null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();
            if (bundle.containsKey("selectedItems")) {
                ArrayList<Parcelable> selectedItems = bundle.getParcelableArrayList("selectedItems");
                if (selectedItems != null) {
                    for (Parcelable selectedItem : selectedItems) {
                        if (selectedItem instanceof Uri) {
                            uris.add((Uri) selectedItem);
                        }
                    }
                }
            }
        } else if (data.getClipData() == null) {
            Uri uri = data.getData();
            uris.add(uri);
        } else {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                Uri uri = data.getClipData().getItemAt(i).getUri();
                uris.add(uri);
            }
        }
        for (int i = 0; i < uris.size(); i++) {
            Uri uri = uris.get(i);
            if (uri == null) {
                continue;
            }
            JSObject fileResult = new JSObject();
            if (readData) {
                fileResult.put("data", implementation.getDataFromUri(uri));
            }
            Long duration = implementation.getDurationFromUri(uri);
            if (duration != null) {
                fileResult.put("duration", duration);
            }
            FileResolution resolution = implementation.getHeightAndWidthFromUri(uri);
            if (resolution != null) {
                fileResult.put("height", resolution.height);
                fileResult.put("width", resolution.width);
            }
            fileResult.put("mimeType", implementation.getMimeTypeFromUri(uri));
            Long modifiedAt = implementation.getModifiedAtFromUri(uri);
            if (modifiedAt != null) {
                fileResult.put("modifiedAt", modifiedAt);
            }
            fileResult.put("name", implementation.getNameFromUri(uri));
            fileResult.put("path", implementation.getPathFromUri(uri));
            fileResult.put("size", implementation.getSizeFromUri(uri));
            filesResultList.add(fileResult);
        }
        callResult.put("files", JSArray.from(filesResultList.toArray()));
        return callResult;
    }
}
