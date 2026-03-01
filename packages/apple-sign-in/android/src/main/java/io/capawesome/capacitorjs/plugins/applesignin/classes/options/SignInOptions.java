package io.capawesome.capacitorjs.plugins.applesignin.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.applesignin.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class SignInOptions {

    @NonNull
    private final String redirectUrl;

    @NonNull
    private final List<String> scopes;

    @Nullable
    private final String nonce;

    @Nullable
    private final String state;

    public SignInOptions(@NonNull PluginCall call) throws Exception {
        this.redirectUrl = getRedirectUrlFromCall(call);
        this.scopes = getScopesFromCall(call);
        this.nonce = call.getString("nonce");
        this.state = call.getString("state");
    }

    @NonNull
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @NonNull
    public List<String> getScopes() {
        return scopes;
    }

    @Nullable
    public String getNonce() {
        return nonce;
    }

    @Nullable
    public String getState() {
        return state;
    }

    @NonNull
    private static String getRedirectUrlFromCall(@NonNull PluginCall call) throws Exception {
        String redirectUrl = call.getString("redirectUrl");
        if (redirectUrl == null) {
            throw CustomExceptions.REDIRECT_URL_MISSING;
        }
        return redirectUrl;
    }

    @NonNull
    private static List<String> getScopesFromCall(@NonNull PluginCall call) {
        List<String> scopes = new ArrayList<>();
        JSArray scopesArray = call.getArray("scopes");
        if (scopesArray == null) {
            return scopes;
        }
        try {
            JSONArray jsonArray = scopesArray;
            for (int i = 0; i < jsonArray.length(); i++) {
                String scope = jsonArray.getString(i);
                if ("EMAIL".equals(scope)) {
                    scopes.add("email");
                } else if ("FULL_NAME".equals(scope)) {
                    scopes.add("name");
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return scopes;
    }
}
