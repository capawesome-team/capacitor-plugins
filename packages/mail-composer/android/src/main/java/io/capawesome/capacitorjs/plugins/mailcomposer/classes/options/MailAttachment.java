package io.capawesome.capacitorjs.plugins.mailcomposer.classes.options;

import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.mailcomposer.classes.CustomExceptions;
import org.json.JSONObject;

public class MailAttachment {

    @Nullable
    private final byte[] data;

    @Nullable
    private final String name;

    @Nullable
    private final String path;

    public MailAttachment(@NonNull JSONObject object) throws Exception {
        String data = getStringFromObject(object, "data");
        this.name = getStringFromObject(object, "name");
        this.path = getStringFromObject(object, "path");
        if (data == null && this.path == null) {
            throw CustomExceptions.ATTACHMENT_DATA_OR_PATH_MISSING;
        }
        if (data == null) {
            this.data = null;
        } else {
            if (this.name == null) {
                throw CustomExceptions.ATTACHMENT_NAME_MISSING;
            }
            this.data = decodeData(data);
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
    private static String getStringFromObject(@NonNull JSONObject object, @NonNull String key) {
        return object.isNull(key) ? null : object.optString(key, null);
    }
}
