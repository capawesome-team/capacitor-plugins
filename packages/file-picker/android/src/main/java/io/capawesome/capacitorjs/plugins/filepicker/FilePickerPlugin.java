package io.capawesome.capacitorjs.plugins.filepicker;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
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
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;

@CapacitorPlugin(
    name = "FilePicker",
    permissions = {
        @Permission(strings = { Manifest.permission.ACCESS_MEDIA_LOCATION }, alias = FilePickerPlugin.PERMISSION_ACCESS_MEDIA_LOCATION),
        @Permission(strings = { Manifest.permission.READ_EXTERNAL_STORAGE }, alias = FilePickerPlugin.PERMISSION_READ_EXTERNAL_STORAGE)
    }
)
public class FilePickerPlugin extends Plugin {

    public static final String ERROR_COPY_FILE_FAILED = "copyFile failed.";
    public static final String ERROR_FILE_ALREADY_EXISTS = "File already exists.";
    public static final String ERROR_FROM_MISSING = "from must be provided.";
    public static final String ERROR_TO_MISSING = "to must be provided.";
    public static final String ERROR_PICK_FILE_FAILED = "pickFiles failed.";
    public static final String ERROR_PICK_FILE_CANCELED = "pickFiles canceled.";
    public static final String ERROR_PICK_DIRECTORY_FAILED = "pickDirectory failed.";
    public static final String ERROR_PICK_DIRECTORY_CANCELED = "pickDirectory canceled.";
    public static final String PERMISSION_ACCESS_MEDIA_LOCATION = "accessMediaLocation";
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = "readExternalStorage";
    public static final String TAG = "FilePickerPlugin";

    private FilePicker implementation;

    public void load() {
        implementation = new FilePicker(this);
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        super.checkPermissions(call);
    }

    @PluginMethod
    public void convertHeicToJpeg(PluginCall call) {
        call.unimplemented("Not implemented on Android.");
    }

    @PluginMethod
    public void copyFile(PluginCall call) {
        try {
            String from = call.getString("from");
            if (from == null) {
                call.reject(ERROR_FROM_MISSING);
                return;
            }
            Uri fromUri = implementation.getUriByPath(from);
            String to = call.getString("to");
            if (to == null) {
                call.reject(ERROR_TO_MISSING);
                return;
            }
            Uri toUri = implementation.getUriByPath(to);
            Boolean shouldOverwrite = call.getBoolean("overwrite", true);

            implementation.copyFile(fromUri, toUri, shouldOverwrite);

            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void pickFiles(PluginCall call) {
        try {
            boolean persistContentUri = call.getBoolean("persistContentUri", false);
            String intentAction = persistContentUri ? Intent.ACTION_OPEN_DOCUMENT : Intent.ACTION_GET_CONTENT;
            int limit = call.getInt("limit", 0);
            JSArray types = call.getArray("types", null);
            String[] parsedTypes = parseTypesOption(types);

            Intent intent = new Intent(intentAction);
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
    public void pickDirectory(PluginCall call) {
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            startActivityForResult(call, intent, "pickDirectoryResult");
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

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        List<String> permissionsList = new ArrayList<>();
        permissionsList.add(PERMISSION_ACCESS_MEDIA_LOCATION);
        permissionsList.add(PERMISSION_READ_EXTERNAL_STORAGE);

        JSArray permissions = call.getArray("permissions");
        if (permissions != null) {
            try {
                permissionsList = permissions.toList();
            } catch (JSONException e) {}
        }

        requestPermissionForAliases(permissionsList.toArray(new String[0]), call, "permissionsCallback");
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

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }

    @ActivityCallback
    private void pickFilesResult(PluginCall call, ActivityResult result) {
        try {
            if (call == null) {
                return;
            }
            boolean persistContentUri = call.getBoolean("persistContentUri", false);
            boolean readData = call.getBoolean("readData", false);
            int resultCode = result.getResultCode();
            switch (resultCode) {
                case Activity.RESULT_OK:
                    JSObject callResult = createPickFilesResult(result.getData(), persistContentUri, readData);
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

    @ActivityCallback
    private void pickDirectoryResult(PluginCall call, ActivityResult result) {
        try {
            int resultCode = result.getResultCode();
            switch (resultCode) {
                case Activity.RESULT_OK:
                    JSObject callResult = createPickDirectoryResult(result.getData());
                    call.resolve(callResult);
                    break;
                case Activity.RESULT_CANCELED:
                    call.reject(ERROR_PICK_DIRECTORY_CANCELED);
                    break;
                default:
                    call.reject(ERROR_PICK_DIRECTORY_FAILED);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    private JSObject createPickFilesResult(@Nullable Intent data, boolean persistContentUri, boolean readData) {
        ContentResolver contentResolver = getContext().getContentResolver();
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
            if (persistContentUri) {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

    private JSObject createPickDirectoryResult(@Nullable Intent data) {
        JSObject callResult = new JSObject();
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                callResult.put("path", implementation.getPathFromUri(uri));
            }
        }
        return callResult;
    }
}
