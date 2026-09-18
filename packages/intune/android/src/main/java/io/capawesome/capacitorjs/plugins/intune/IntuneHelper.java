package io.capawesome.capacitorjs.plugins.intune;

import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;

public class IntuneHelper {

    @NonNull
    public static String getFilePath(@NonNull String pathOrUri) {
        Uri uri = Uri.parse(pathOrUri);
        String path = uri.getPath();
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()) && path != null) {
            return path;
        }
        return pathOrUri;
    }
}
