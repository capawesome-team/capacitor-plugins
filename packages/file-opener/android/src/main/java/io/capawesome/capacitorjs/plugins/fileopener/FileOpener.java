package io.capawesome.capacitorjs.plugins.fileopener;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class FileOpener {

    private FileOpenerPlugin plugin;

    FileOpener(FileOpenerPlugin plugin) {
        this.plugin = plugin;
    }

    public void openFile(@NonNull Uri uri, @Nullable String mimeType) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (mimeType == null || mimeType.trim().equals("")) {
            intent.setDataAndNormalize(uri);
        } else {
            intent.setDataAndTypeAndNormalize(uri, mimeType);
        }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        plugin.getActivity().startActivity(intent);
    }

    public boolean isFileExists(@NonNull Uri uri) {
        DocumentFile document = DocumentFile.fromSingleUri(plugin.getContext(), uri);
        return document.exists();
    }

    public Uri getUriByPath(@NonNull String path) {
        Uri uri = Uri.parse(path);
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            return uri;
        } else if (uri.getScheme() == null || uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            return FileProvider.getUriForFile(
                plugin.getActivity(),
                plugin.getContext().getPackageName() + ".fileprovider",
                new File(uri.getPath())
            );
        } else {
            return FileProvider.getUriForFile(plugin.getActivity(), plugin.getContext().getPackageName() + ".fileprovider", new File(path));
        }
    }
}
