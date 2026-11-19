package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.CheckOutcome;
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

    private boolean clearsPendingFailure;

    @Nullable
    private CheckOutcome outcome;

    @Nullable
    private String reason;

    @Nullable
    private Long retryAfterMs;

    private CreateCheckResponse() {}

    public boolean clearsPendingFailure() {
        return clearsPendingFailure;
    }

    /**
     * @return An answer without an update, without a wait and without clearing the pending failure.
     */
    @NonNull
    public static CreateCheckResponse createWithoutUpdate() {
        return createWithoutUpdate(false);
    }

    @Nullable
    public CreateCheckResponseBundle getBundle() {
        return bundle;
    }

    @Nullable
    public String getChannelName() {
        return channelName;
    }

    @Nullable
    public CheckOutcome getOutcome() {
        return outcome;
    }

    /**
     * @return The reason as the server sent it, only set if the check was skipped.
     */
    @Nullable
    public String getReason() {
        return reason;
    }

    /**
     * @return The milliseconds to wait before the next check, or `null` if no wait was asked for.
     */
    @Nullable
    public Long getRetryAfterMs() {
        return retryAfterMs;
    }

    @NonNull
    public static CreateCheckResponse parse(int statusCode, @Nullable String retryAfter, @NonNull String body) {
        if (statusCode == 429) {
            CreateCheckResponse response = createWithoutUpdate();
            response.retryAfterMs = parseRetryAfterMs(retryAfter);
            return response;
        }
        if (statusCode == 200) {
            return parseProcessedBody(body);
        }
        boolean isClientError = statusCode >= 400 && statusCode < 500;
        return createWithoutUpdate(isClientError);
    }

    @NonNull
    private static CreateCheckResponse createWithoutUpdate(boolean clearsPendingFailure) {
        CreateCheckResponse response = new CreateCheckResponse();
        response.clearsPendingFailure = clearsPendingFailure;
        return response;
    }

    @NonNull
    private static CreateCheckResponse parseProcessedBody(@NonNull String body) {
        JSONObject json;
        try {
            json = new JSONObject(body);
        } catch (JSONException exception) {
            return createWithoutUpdate(false);
        }
        CheckOutcome outcome = CheckOutcome.fromValue(getNonEmptyString(json, "result"));
        if (outcome == null) {
            return createWithoutUpdate(true);
        }
        CreateCheckResponseBundle bundle = null;
        if (outcome == CheckOutcome.AVAILABLE) {
            JSONObject bundleJson = json.optJSONObject("bundle");
            bundle = bundleJson == null ? null : CreateCheckResponseBundle.parse(bundleJson);
            if (bundle == null) {
                return createWithoutUpdate(true);
            }
        }
        CreateCheckResponse response = createWithoutUpdate(true);
        response.outcome = outcome;
        response.bundle = bundle;
        response.channelName = getNonEmptyString(json, "channelName");
        response.reason = outcome == CheckOutcome.SKIPPED ? getNonEmptyString(json, "reason") : null;
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
