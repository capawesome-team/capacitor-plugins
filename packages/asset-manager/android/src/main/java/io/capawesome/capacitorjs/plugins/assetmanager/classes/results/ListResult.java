package io.capawesome.capacitorjs.plugins.assetmanager.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.Result;
import java.util.ArrayList;

public class ListResult implements Result {

    @NonNull
    private ArrayList<String> files;

    public ListResult(@NonNull ArrayList<String> files) {
        this.files = files;
    }

    public JSObject toJSObject() {
        JSArray filesResult = new JSArray();
        for (String file : files) {
            filesResult.put(file);
        }

        JSObject result = new JSObject();
        result.put("files", filesResult);
        return result;
    }
}
