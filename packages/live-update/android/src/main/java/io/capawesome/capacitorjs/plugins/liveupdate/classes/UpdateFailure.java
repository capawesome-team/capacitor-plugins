package io.capawesome.capacitorjs.plugins.liveupdate.classes;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.FailureReason;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A failed update. The values are set one by one so that every call names the field it sets.
 */
public class UpdateFailure {

    @Nullable
    private String appId;

    @Nullable
    private String bundleId;

    @Nullable
    private FailureReason reason;

    @Override
    public boolean equals(@Nullable Object other) {
        if (!(other instanceof UpdateFailure)) {
            return false;
        }
        UpdateFailure failure = (UpdateFailure) other;
        return Objects.equals(appId, failure.appId) && Objects.equals(bundleId, failure.bundleId) && reason == failure.reason;
    }

    @Nullable
    public String getAppId() {
        return appId;
    }

    @Nullable
    public String getBundleId() {
        return bundleId;
    }

    @Nullable
    public FailureReason getReason() {
        return reason;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, bundleId, reason);
    }

    /**
     * @return The failure, or `null` if the text is not a readable failure.
     */
    @Nullable
    public static UpdateFailure parse(@Nullable String text) {
        if (text == null) {
            return null;
        }
        JSONObject json;
        try {
            json = new JSONObject(text);
        } catch (JSONException exception) {
            return null;
        }
        UpdateFailure failure = new UpdateFailure()
            .setAppId(getNonEmptyString(json, "appId"))
            .setBundleId(getNonEmptyString(json, "bundleId"))
            .setReason(FailureReason.fromValue(getNonEmptyString(json, "reason")));
        if (failure.appId == null || failure.bundleId == null || failure.reason == null) {
            return null;
        }
        return failure;
    }

    @NonNull
    public UpdateFailure setAppId(@Nullable String appId) {
        this.appId = appId;
        return this;
    }

    @NonNull
    public UpdateFailure setBundleId(@Nullable String bundleId) {
        this.bundleId = bundleId;
        return this;
    }

    @NonNull
    public UpdateFailure setReason(@Nullable FailureReason reason) {
        this.reason = reason;
        return this;
    }

    @NonNull
    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.putOpt("appId", appId);
        json.putOpt("bundleId", bundleId);
        json.putOpt("reason", reason == null ? null : reason.getValue());
        return json;
    }
}
