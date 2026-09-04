package io.capawesome.capacitorjs.plugins.pdfannotator.classes.results;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.pdfannotator.interfaces.Result;
import java.io.File;

public class OpenResult implements Result {

    @NonNull
    private final File file;

    public OpenResult(@NonNull File file) {
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
