package io.capawesome.capacitorjs.plugins.fileopener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FileOpener")
public class FileOpenerPlugin extends Plugin {

    public static final String TAG = "FileOpenerPlugin";

    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_FILE_NOT_EXIST = "File does not exist.";
    public static final String ERROR_CANNOT_OPEN_FILE = "File cannot be opened.";
    public static final String ERROR_CODE_FILE_NOT_FOUND = "FILE_NOT_FOUND";
    public static final String ERROR_CODE_OPEN_FAILED = "OPEN_FAILED";

    private FileOpener implementation;

    @Override
    public void load() {
        implementation = new FileOpener(this);
    }

    @PluginMethod
    public void openFile(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            String mimeType = call.getString("mimeType");

            Uri uri = implementation.getUriByPath(path);
            boolean fileExists = implementation.isFileExists(uri);
            if (!fileExists) {
                call.reject(ERROR_FILE_NOT_EXIST, ERROR_CODE_FILE_NOT_FOUND);
                return;
            }

            try {
                Intent intent = implementation.createIntent(uri, mimeType);
                getActivity().startActivity(intent);
                call.resolve();
            } catch (ActivityNotFoundException exception) {
                call.reject(ERROR_CANNOT_OPEN_FILE, ERROR_CODE_OPEN_FAILED);
            }
        } catch (Exception exception) {
            String message = exception.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }
}
