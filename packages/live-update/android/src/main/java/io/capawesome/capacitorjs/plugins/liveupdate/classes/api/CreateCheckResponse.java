package io.capawesome.capacitorjs.plugins.liveupdate.classes.api;

import static io.capawesome.capacitorjs.plugins.liveupdate.classes.JsonFields.getNonEmptyString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.CheckOutcome;
import java.math.BigInteger;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateCheckResponse {

    private static final long DEFAULT_RETRY_AFTER_MS = 15 * 60 * 1000;
    private static final BigInteger MAX_RETRY_AFTER_SECONDS = BigInteger.valueOf(24 * 60 * 60);
    private static final String SKIP_REASON_RATE_LIMITED = "RATE_LIMITED";

    @Nullable
    private CreateCheckResponseBundle bundle;

    @Nullable
    private String channelName;

    private boolean clearsPendingFailure;

    @Nullable
    private CheckOutcome outcome;

    @Nullable
    private Long retryAfterMs;

    @Nullable
    private String skipReason;

    private CreateCheckResponse() {}

    public boolean clearsPendingFailure() {
        return clearsPendingFailure;
    }

    /**
     * @return A skipped check with the reason `RATE_LIMITED`, without a wait and without clearing the pending failure.
     */
    @NonNull
    public static CreateCheckResponse createRateLimited() {
        CreateCheckResponse response = new CreateCheckResponse();
        response.outcome = CheckOutcome.SKIPPED;
        response.skipReason = SKIP_REASON_RATE_LIMITED;
        return response;
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
     * @return The milliseconds to wait before the next check, or `null` if no wait was asked for.
     */
    @Nullable
    public Long getRetryAfterMs() {
        return retryAfterMs;
    }

    /**
     * @return The reason in upper case, only set if the check was skipped.
     */
    @Nullable
    public String getSkipReason() {
        return skipReason;
    }

    @NonNull
    public static CreateCheckResponse parse(int statusCode, @Nullable String retryAfter, @NonNull String body) {
        if (statusCode == 429) {
            CreateCheckResponse response = createRateLimited();
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
        response.skipReason = outcome == CheckOutcome.SKIPPED ? parseSkipReason(json) : null;
        return response;
    }

    private static long parseRetryAfterMs(@Nullable String retryAfter) {
        if (retryAfter == null || !retryAfter.matches("[0-9]+")) {
            return DEFAULT_RETRY_AFTER_MS;
        }
        BigInteger seconds = new BigInteger(retryAfter).min(MAX_RETRY_AFTER_SECONDS);
        return seconds.longValue() * 1000;
    }

    @Nullable
    private static String parseSkipReason(@NonNull JSONObject json) {
        String reason = getNonEmptyString(json, "reason");
        return reason == null ? null : reason.toUpperCase(Locale.ROOT);
    }
}
