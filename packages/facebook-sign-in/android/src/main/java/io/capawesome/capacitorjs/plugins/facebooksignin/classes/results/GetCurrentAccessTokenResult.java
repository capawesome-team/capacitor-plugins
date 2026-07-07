package io.capawesome.capacitorjs.plugins.facebooksignin.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.AccessToken;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.facebooksignin.FacebookSignInHelper;
import io.capawesome.capacitorjs.plugins.facebooksignin.interfaces.Result;
import org.json.JSONObject;

public class GetCurrentAccessTokenResult implements Result {

    @Nullable
    private final AccessToken accessToken;

    public GetCurrentAccessTokenResult(@Nullable AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("accessToken", accessToken == null ? JSONObject.NULL : FacebookSignInHelper.createAccessTokenJSObject(accessToken));
        return result;
    }
}
