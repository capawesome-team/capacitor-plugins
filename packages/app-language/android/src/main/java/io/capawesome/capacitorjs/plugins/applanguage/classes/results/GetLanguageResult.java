package io.capawesome.capacitorjs.plugins.applanguage.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.applanguage.interfaces.Result;
import org.json.JSONObject;

public class GetLanguageResult implements Result {

    @Nullable
    private final String languageTag;

    public GetLanguageResult(@Nullable String languageTag) {
        this.languageTag = languageTag;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("languageTag", languageTag == null ? JSONObject.NULL : languageTag);
        return result;
    }
}
