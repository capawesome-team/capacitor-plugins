package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class PromptParameters {

    @Nullable
    private final String mode;

    @NonNull
    private final List<String> additionalMethods;

    public PromptParameters(@NonNull JSObject obj) throws JSONException {
        this.mode = obj.getString("mode");

        this.additionalMethods = new ArrayList<>();
        try {
            JSONArray additionalMethodsArray = obj.getJSONArray("additionalMethods");
            if (additionalMethodsArray != null) {
                for (int i = 0; i < additionalMethodsArray.length(); i++) {
                    String method = additionalMethodsArray.getString(i);
                    if (method != null) {
                        this.additionalMethods.add(method);
                    }
                }
            }
        } catch (JSONException e) {
            // Array not provided, use empty list
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
