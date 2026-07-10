package io.capawesome.capacitorjs.plugins.facebooksignin;

import androidx.annotation.NonNull;
import com.facebook.AccessToken;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;

public class FacebookSignInHelper {

    @NonNull
    public static JSObject createAccessTokenJSObject(@NonNull AccessToken accessToken) {
        JSArray permissionsResult = new JSArray();
        for (String permission : accessToken.getPermissions()) {
            if (permission != null) {
                permissionsResult.put(permission);
            }
        }
        JSObject result = new JSObject();
        result.put("expiresAt", accessToken.getExpires().getTime());
        result.put("permissions", permissionsResult);
        result.put("token", accessToken.getToken());
        result.put("userId", accessToken.getUserId());
        return result;
    }
}
