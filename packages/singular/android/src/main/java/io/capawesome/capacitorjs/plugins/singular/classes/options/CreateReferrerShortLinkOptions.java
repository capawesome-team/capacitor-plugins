package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class CreateReferrerShortLinkOptions {

    @NonNull
    private final String baseLink;

    @Nullable
    private final JSObject passthroughParameters;

    @NonNull
    private final String referrerId;

    @NonNull
    private final String referrerName;

    public CreateReferrerShortLinkOptions(@NonNull PluginCall call) throws Exception {
        String baseLink = call.getString("baseLink");
        if (baseLink == null) {
            throw CustomExceptions.BASE_LINK_MISSING;
        }
        String referrerId = call.getString("referrerId");
        if (referrerId == null) {
            throw CustomExceptions.REFERRER_ID_MISSING;
        }
        String referrerName = call.getString("referrerName");
        if (referrerName == null) {
            throw CustomExceptions.REFERRER_NAME_MISSING;
        }
        this.baseLink = baseLink;
        this.passthroughParameters = call.getObject("passthroughParameters");
        this.referrerId = referrerId;
        this.referrerName = referrerName;
    }

    @NonNull
    public String getBaseLink() {
        return baseLink;
    }

    @Nullable
    public JSObject getPassthroughParameters() {
        return passthroughParameters;
    }

    @NonNull
    public String getReferrerId() {
        return referrerId;
    }

    @NonNull
    public String getReferrerName() {
        return referrerName;
    }
}
