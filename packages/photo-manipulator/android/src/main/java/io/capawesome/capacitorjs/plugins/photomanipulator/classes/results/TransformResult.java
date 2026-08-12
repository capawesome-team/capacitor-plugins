package io.capawesome.capacitorjs.plugins.photomanipulator.classes.results;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.photomanipulator.interfaces.Result;
import java.io.File;

public class TransformResult implements Result {

    @NonNull
    private final File file;

    private final int height;

    private final int width;

    public TransformResult(@NonNull File file, int height, int width) {
        this.file = file;
        this.height = height;
        this.width = width;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("height", height);
        result.put("path", Uri.fromFile(file).toString());
        result.put("width", width);
        return result;
    }
}
