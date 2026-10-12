package io.capawesome.capacitorjs.plugins.intune.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

public class CustomException extends Exception {

    @Nullable
    private final String code;

    @Nullable
    private final JSObject data;

    public CustomException(@Nullable String code, @NonNull String message) {
        this(code, message, null);
    }

    public CustomException(@Nullable String code, @NonNull String message, @Nullable JSObject data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    @Nullable
    public JSObject getData() {
        return data;
    }
}
