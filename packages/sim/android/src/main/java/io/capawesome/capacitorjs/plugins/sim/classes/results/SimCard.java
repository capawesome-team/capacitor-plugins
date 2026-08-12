package io.capawesome.capacitorjs.plugins.sim.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.sim.interfaces.Result;
import org.json.JSONObject;

public class SimCard implements Result {

    @Nullable
    private final String carrierName;

    @Nullable
    private final String displayName;

    @Nullable
    private final Boolean isEmbedded;

    @Nullable
    private final String isoCountryCode;

    @Nullable
    private final String mobileCountryCode;

    @Nullable
    private final String mobileNetworkCode;

    @Nullable
    private final String phoneNumber;

    private final int slotIndex;

    public SimCard(
        int slotIndex,
        @Nullable String carrierName,
        @Nullable String displayName,
        @Nullable String isoCountryCode,
        @Nullable String mobileCountryCode,
        @Nullable String mobileNetworkCode,
        @Nullable Boolean isEmbedded,
        @Nullable String phoneNumber
    ) {
        this.slotIndex = slotIndex;
        this.carrierName = carrierName;
        this.displayName = displayName;
        this.isoCountryCode = isoCountryCode;
        this.mobileCountryCode = mobileCountryCode;
        this.mobileNetworkCode = mobileNetworkCode;
        this.isEmbedded = isEmbedded;
        this.phoneNumber = phoneNumber;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("slotIndex", slotIndex);
        result.put("carrierName", carrierName == null ? JSONObject.NULL : carrierName);
        result.put("displayName", displayName == null ? JSONObject.NULL : displayName);
        result.put("isoCountryCode", isoCountryCode == null ? JSONObject.NULL : isoCountryCode);
        result.put("mobileCountryCode", mobileCountryCode == null ? JSONObject.NULL : mobileCountryCode);
        result.put("mobileNetworkCode", mobileNetworkCode == null ? JSONObject.NULL : mobileNetworkCode);
        result.put("isEmbedded", isEmbedded == null ? JSONObject.NULL : isEmbedded);
        result.put("phoneNumber", phoneNumber == null ? JSONObject.NULL : phoneNumber);
        return result;
    }
}
