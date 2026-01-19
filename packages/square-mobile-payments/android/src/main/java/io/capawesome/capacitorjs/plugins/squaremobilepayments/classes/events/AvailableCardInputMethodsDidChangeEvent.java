package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import java.util.List;

public class AvailableCardInputMethodsDidChangeEvent {

    @NonNull
    private final List<String> cardInputMethods;

    public AvailableCardInputMethodsDidChangeEvent(@NonNull List<String> cardInputMethods) {
        this.cardInputMethods = cardInputMethods;
    }

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
