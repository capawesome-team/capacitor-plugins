package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import java.util.List;

public class GetAvailableCardInputMethodsResult implements Result {

    @NonNull
    private final List<String> cardInputMethods;

    public GetAvailableCardInputMethodsResult(@NonNull List<String> cardInputMethods) {
        this.cardInputMethods = cardInputMethods;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        JSArray array = new JSArray();
        for (String method : cardInputMethods) {
            array.put(method);
        }
        result.put("cardInputMethods", array);
        return result;
    }
}
