package io.capawesome.capacitorjs.plugins.appintegrity.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.appintegrity.classes.CustomExceptions;

public class RequestIntegrityTokenOptions {

    @Nullable
    private final Long cloudProjectNumber;

    @Nullable
    private final String nonce;

    @Nullable
    private final String requestHash;

    public RequestIntegrityTokenOptions(@NonNull PluginCall call) throws Exception {
        this.cloudProjectNumber = RequestIntegrityTokenOptions.getCloudProjectNumberFromCall(call);
        this.nonce = call.getString("nonce");
        this.requestHash = call.getString("requestHash");
        if (this.nonce == null && this.requestHash == null) {
            throw CustomExceptions.NONCE_OR_REQUEST_HASH_MISSING;
        }
    }

    @Nullable
    public Long getCloudProjectNumber() {
        return cloudProjectNumber;
    }

    @Nullable
    public String getNonce() {
        return nonce;
    }

    @Nullable
    public String getRequestHash() {
        return requestHash;
    }

    @Nullable
    private static Long getCloudProjectNumberFromCall(@NonNull PluginCall call) {
        Object value = call.getData().opt("cloudProjectNumber");
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return null;
    }
}
