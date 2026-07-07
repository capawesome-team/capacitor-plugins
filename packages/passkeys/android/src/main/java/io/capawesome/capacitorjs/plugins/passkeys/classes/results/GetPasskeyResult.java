package io.capawesome.capacitorjs.plugins.passkeys.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.passkeys.interfaces.Result;
import org.json.JSONException;

public class GetPasskeyResult implements Result {

    @NonNull
    private final JSObject authenticationResponse;

    public GetPasskeyResult(@NonNull String authenticationResponseJson) throws JSONException {
        this.authenticationResponse = new JSObject(authenticationResponseJson);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        return authenticationResponse;
    }
}
