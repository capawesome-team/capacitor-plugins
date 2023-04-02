package io.capawesome.capacitorjs.plugins.fileopener;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileOpener {

    private FileOpenerPlugin plugin;

    FileOpener(FileOpenerPlugin plugin) {
        this.plugin = plugin;
    }

    public void openFile(@NonNull File file, @Nullable String mimeType) throws Exception {
        if (!file.exists()) {
            throw new Exception(FileOpenerPlugin.ERROR_FILE_NOT_EXIST);
        }
        if (mimeType == null) {
            mimeType = getMimeTypeFromFile(file);
        }
        String contextPackageName = plugin.getBridge().getContext().getPackageName();
        Uri data = FileProvider.getUriForFile(plugin.getBridge().getActivity(), contextPackageName + ".fileopener.provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(data, mimeType);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        plugin.getActivity().startActivity(intent);
    }

    public File getFileByPath(@NonNull String path) {
        File file;
        try {
            Uri uri = Uri.parse(path);
            file = new File(uri.getPath());
        } catch (Exception e) {
            file = new File(path);
        }
        return file;
    }

    private String getMimeTypeFromFile(File file) {
        Uri uri = Uri.fromFile(file);
        return plugin.getBridge().getContext().getContentResolver().getType(uri);
    }
}
