package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.CustomException;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.CheckResult;
import java.math.BigInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateCheckResponse {

    private static final long DEFAULT_RETRY_AFTER_MS = 15 * 60 * 1000;
    private static final BigInteger MAX_RETRY_AFTER_SECONDS = BigInteger.valueOf(24 * 60 * 60);

    @Nullable
    private CreateCheckResponseBundle bundle;

    @Nullable
    private String channelName;

    @Nullable
    private String reason;

    @Nullable
    private CheckResult result;

    @Nullable
    private Long retryAfterMs;

    private CreateCheckResponse() {}

    /**
     * @return An answer without an update and without a wait.
     */
    @NonNull
    public static CreateCheckResponse createWithoutUpdate() {
        return new CreateCheckResponse();
    }

    @Nullable
    public CreateCheckResponseBundle getBundle() {
        return bundle;
    }

    @Nullable
    public String getChannelName() {
        return channelName;
    }

    /**
     * @return The reason as the server sent it, only set if the check was skipped.
     */
    @Nullable
    public String getReason() {
        return reason;
    }

    @Nullable
    public CheckResult getResult() {
        return result;
    }

    /**
     * @return The milliseconds to wait before the next check, or `null` if no wait was asked for.
     */
    @Nullable
    public Long getRetryAfterMs() {
        return retryAfterMs;
    }

    /**
     * @throws CustomException With the code `REQUEST_FAILED` for every status other than 200 and 429.
     */
    @NonNull
    public static CreateCheckResponse parse(int statusCode, @Nullable String retryAfter, @NonNull String body) throws CustomException {
        if (statusCode == 429) {
            CreateCheckResponse response = new CreateCheckResponse();
            response.retryAfterMs = parseRetryAfterMs(retryAfter);
            return response;
        }
        if (statusCode == 200) {
            return parseBody(body);
        }
        throw CustomExceptions.requestFailed("The check failed with status " + statusCode + ".");
    }

    @NonNull
    private static CreateCheckResponse parseBody(@NonNull String body) {
        JSONObject json;
        try {
            json = new JSONObject(body);
        } catch (JSONException exception) {
            return createWithoutUpdate();
        }
        CheckResult result = CheckResult.fromValue(getNonEmptyString(json, "result"));
        if (result == null) {
            return createWithoutUpdate();
        }
        CreateCheckResponseBundle bundle = null;
        if (result == CheckResult.AVAILABLE) {
            JSONObject bundleJson = json.optJSONObject("bundle");
            bundle = bundleJson == null ? null : CreateCheckResponseBundle.parse(bundleJson);
            if (bundle == null) {
                return createWithoutUpdate();
            }
        }
        CreateCheckResponse response = new CreateCheckResponse();
        response.result = result;
        response.bundle = bundle;
        response.channelName = getNonEmptyString(json, "channelName");
        response.reason = result == CheckResult.SKIPPED ? getNonEmptyString(json, "reason") : null;
        return response;
    }

    private static long parseRetryAfterMs(@Nullable String retryAfter) {
        if (retryAfter == null || !retryAfter.matches("[0-9]+")) {
            return DEFAULT_RETRY_AFTER_MS;
        }
        BigInteger seconds = new BigInteger(retryAfter).min(MAX_RETRY_AFTER_SECONDS);
        return seconds.longValue() * 1000;
    }
}
