package io.capawesome.capacitorjs.plugins.passkeys.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.passkeys.interfaces.Result;
import org.json.JSONException;

public class CreatePasskeyResult implements Result {

    @NonNull
    private final JSObject registrationResponse;

    public CreatePasskeyResult(@NonNull String registrationResponseJson) throws JSONException {
        this.registrationResponse = new JSObject(registrationResponseJson);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        return registrationResponse;
    }
}
