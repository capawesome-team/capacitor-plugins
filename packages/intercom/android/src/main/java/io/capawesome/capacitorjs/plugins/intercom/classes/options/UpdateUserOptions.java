package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class UpdateUserOptions {

    @Nullable
    private final List<JSObject> companies;

    @Nullable
    private final JSObject customAttributes;

    @Nullable
    private final String email;

    @Nullable
    private final String languageOverride;

    @Nullable
    private final String name;

    @Nullable
    private final String phone;

    @Nullable
    private final Long signedUpAt;

    @Nullable
    private final Boolean unsubscribedFromEmails;

    @Nullable
    private final String userId;

    public UpdateUserOptions(@NonNull PluginCall call) throws Exception {
        this.userId = call.getString("userId");
        this.email = call.getString("email");
        this.name = call.getString("name");
        this.phone = call.getString("phone");
        this.languageOverride = call.getString("languageOverride");
        Double signedUpAt = call.getDouble("signedUpAt");
        this.signedUpAt = signedUpAt == null ? null : signedUpAt.longValue();
        this.unsubscribedFromEmails = call.getBoolean("unsubscribedFromEmails");
        this.customAttributes = call.getObject("customAttributes");
        JSArray companies = call.getArray("companies");
        if (companies == null) {
            this.companies = null;
        } else {
            List<JSObject> parsedCompanies = new ArrayList<>();
            for (int i = 0; i < companies.length(); i++) {
                JSONObject company = companies.getJSONObject(i);
                parsedCompanies.add(JSObject.fromJSONObject(company));
            }
            this.companies = parsedCompanies;
        }
    }

    @Nullable
    public List<JSObject> getCompanies() {
        return companies;
    }

    @Nullable
    public JSObject getCustomAttributes() {
        return customAttributes;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getLanguageOverride() {
        return languageOverride;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public Long getSignedUpAt() {
        return signedUpAt;
    }

    @Nullable
    public Boolean getUnsubscribedFromEmails() {
        return unsubscribedFromEmails;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }
}
