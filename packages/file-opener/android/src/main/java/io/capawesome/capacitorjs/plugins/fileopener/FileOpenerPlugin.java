package io.capawesome.capacitorjs.plugins.fileopener;

import android.net.Uri;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.File;

@CapacitorPlugin(name = "FileOpener")
public class FileOpenerPlugin extends Plugin {

    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_FILE_NOT_EXIST = "File does not exist.";

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
                call.reject(ERROR_FILE_NOT_EXIST);
                return;
            }

            implementation.openFile(uri, mimeType);
            call.resolve();
        } catch (Exception ex) {
            call.reject(ex.getMessage());
        }
    }
}
