package io.capawesome.capacitorjs.plugins.installreferrer.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.installreferrer.interfaces.Result;

public class GetInstallReferrerResult implements Result {

    private final boolean googlePlayInstantParam;

    private final long installBeginTimestampMillis;

    private final long referrerClickTimestampMillis;

    @NonNull
    private final String referrerUrl;

    public GetInstallReferrerResult(
        boolean googlePlayInstantParam,
        long installBeginTimestampMillis,
        long referrerClickTimestampMillis,
        @NonNull String referrerUrl
    ) {
        this.googlePlayInstantParam = googlePlayInstantParam;
        this.installBeginTimestampMillis = installBeginTimestampMillis;
        this.referrerClickTimestampMillis = referrerClickTimestampMillis;
        this.referrerUrl = referrerUrl;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("googlePlayInstantParam", googlePlayInstantParam);
        result.put("installBeginTimestampMillis", installBeginTimestampMillis);
        result.put("referrerClickTimestampMillis", referrerClickTimestampMillis);
        result.put("referrerUrl", referrerUrl);
        return result;
    }
}
