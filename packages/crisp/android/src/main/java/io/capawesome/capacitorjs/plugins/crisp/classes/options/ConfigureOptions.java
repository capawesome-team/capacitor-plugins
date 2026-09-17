package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class ConfigureOptions {

    @NonNull
    private final String websiteId;

    @Nullable
    private final String tokenId;

    public ConfigureOptions(@NonNull PluginCall call) throws Exception {
        this.websiteId = getWebsiteIdFromCall(call);
        this.tokenId = call.getString("tokenId");
    }

    @Nullable
    public String getTokenId() {
        return tokenId;
    }

    @NonNull
    public String getWebsiteId() {
        return websiteId;
    }

    @NonNull
    private static String getWebsiteIdFromCall(@NonNull PluginCall call) throws Exception {
        String websiteId = call.getString("websiteId");
        if (websiteId == null) {
            throw CustomExceptions.WEBSITE_ID_MISSING;
        }
        return websiteId;
    }
}
