package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import org.json.JSONObject;

public class CardPaymentDetails implements Result {

    @NonNull
    private final Card card;

    @NonNull
    private final String entryMethod;

    @Nullable
    private final String authorizationCode;

    @Nullable
    private final String applicationName;

    @Nullable
    private final String applicationId;

    public CardPaymentDetails(
        @NonNull Card card,
        @NonNull String entryMethod,
        @Nullable String authorizationCode,
        @Nullable String applicationName,
        @Nullable String applicationId
    ) {
        this.card = card;
        this.entryMethod = entryMethod;
        this.authorizationCode = authorizationCode;
        this.applicationName = applicationName;
        this.applicationId = applicationId;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("card", card.toJSObject());
        result.put("entryMethod", entryMethod);
        result.put("authorizationCode", authorizationCode == null ? JSONObject.NULL : authorizationCode);
        result.put("applicationName", applicationName == null ? JSONObject.NULL : applicationName);
        result.put("applicationId", applicationId == null ? JSONObject.NULL : applicationId);
        return result;
    }
}
