package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class PromptParameters {

    @Nullable
    private final String mode;

    @NonNull
    private final List<String> additionalMethods;

    public PromptParameters(@NonNull JSObject obj) throws JSONException {
        this.mode = obj.getString("mode");

        this.additionalMethods = new ArrayList<>();
        JSArray additionalMethodsArray = obj.getArray("additionalMethods");
        if (additionalMethodsArray != null) {
            for (int i = 0; i < additionalMethodsArray.length(); i++) {
                String method = additionalMethodsArray.getString(i);
                if (method != null) {
                    this.additionalMethods.add(method);
                }
            }
        }
    }

    @Nullable
    public String getMode() {
        return mode;
    }

    @NonNull
    public List<String> getAdditionalMethods() {
        return additionalMethods;
    }
}
