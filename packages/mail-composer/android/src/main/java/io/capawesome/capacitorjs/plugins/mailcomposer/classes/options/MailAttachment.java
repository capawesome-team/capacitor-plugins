package io.capawesome.capacitorjs.plugins.mailcomposer.classes.options;

import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.CustomExceptions;
import java.io.File;
import org.json.JSONObject;

public class MailAttachment {

    @Nullable
    private final byte[] data;

    @Nullable
    private final String name;

    @Nullable
    private final String path;

    public MailAttachment(@NonNull JSONObject object) throws Exception {
        this.name = getNameFromObject(object);
        this.path = getStringFromObject(object, "path");
        if (this.path == null) {
            String data = getStringFromObject(object, "data");
            if (data == null) {
                throw CustomExceptions.ATTACHMENT_DATA_OR_PATH_MISSING;
            }
            if (this.name == null) {
                throw CustomExceptions.ATTACHMENT_NAME_MISSING;
            }
            this.data = decodeData(data);
        } else {
            this.data = null;
        }
    }

    @Nullable
    public byte[] getData() {
        return data;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPath() {
        return path;
    }

    @NonNull
    private static byte[] decodeData(@NonNull String data) throws Exception {
        try {
            return Base64.decode(data, Base64.DEFAULT);
        } catch (IllegalArgumentException exception) {
            throw CustomExceptions.ATTACHMENT_DATA_INVALID;
        }
    }

    @Nullable
    private static String getNameFromObject(@NonNull JSONObject object) {
        String name = getStringFromObject(object, "name");
        // Strip directory components so that the name cannot escape the attachments directory.
        return name == null ? null : new File(name).getName();
    }

    @Nullable
    private static String getStringFromObject(@NonNull JSONObject object, @NonNull String key) {
        return object.isNull(key) ? null : object.optString(key, null);
    }
}
