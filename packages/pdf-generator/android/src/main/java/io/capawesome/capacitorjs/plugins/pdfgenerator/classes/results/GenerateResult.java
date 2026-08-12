package io.capawesome.capacitorjs.plugins.pdfgenerator.classes.results;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pdfgenerator.interfaces.Result;
import java.io.File;

public class GenerateResult implements Result {

    @NonNull
    private final File file;

    public GenerateResult(@NonNull File file) {
        this.file = file;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("path", Uri.fromFile(file).toString());
        return result;
    }
}
