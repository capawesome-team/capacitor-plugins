package io.capawesome.capacitorjs.plugins.localization.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.localization.interfaces.Result;
import java.util.List;

public class GetLocalesResult implements Result {

    @NonNull
    private final List<LocaleResult> locales;

    public GetLocalesResult(@NonNull List<LocaleResult> locales) {
        this.locales = locales;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray localesArray = new JSArray();
        for (LocaleResult locale : locales) {
            localesArray.put(locale.toJSObject());
        }
        JSObject result = new JSObject();
        result.put("locales", localesArray);
        return result;
    }
}
