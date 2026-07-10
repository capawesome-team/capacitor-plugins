package io.capawesome.capacitorjs.plugins.androidsmsretriever.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.androidsmsretriever.interfaces.Result;

public class RequestPhoneNumberResult implements Result {

    @NonNull
    private final String phoneNumber;

    public RequestPhoneNumberResult(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("phoneNumber", phoneNumber);
        return result;
    }
}
