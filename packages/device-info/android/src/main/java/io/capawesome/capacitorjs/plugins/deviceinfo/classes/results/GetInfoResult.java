package io.capawesome.capacitorjs.plugins.deviceinfo.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.Result;
import org.json.JSONObject;

public class GetInfoResult implements Result {

    @Nullable
    private final Integer androidSdkVersion;

    @NonNull
    private final String deviceType;

    @Nullable
    private final Integer iosVersion;

    private final boolean isVirtual;

    @NonNull
    private final String manufacturer;

    @NonNull
    private final String model;

    @Nullable
    private final String name;

    @NonNull
    private final String operatingSystem;

    @NonNull
    private final String osVersion;

    @NonNull
    private final String platform;

    @Nullable
    private final Long totalMemory;

    @Nullable
    private final Long usedMemory;

    @Nullable
    private final String webViewVersion;

    public GetInfoResult(
        @Nullable Integer androidSdkVersion,
        @NonNull String deviceType,
        @Nullable Integer iosVersion,
        boolean isVirtual,
        @NonNull String manufacturer,
        @NonNull String model,
        @Nullable String name,
        @NonNull String operatingSystem,
        @NonNull String osVersion,
        @NonNull String platform,
        @Nullable Long totalMemory,
        @Nullable Long usedMemory,
        @Nullable String webViewVersion
    ) {
        this.androidSdkVersion = androidSdkVersion;
        this.deviceType = deviceType;
        this.iosVersion = iosVersion;
        this.isVirtual = isVirtual;
        this.manufacturer = manufacturer;
        this.model = model;
        this.name = name;
        this.operatingSystem = operatingSystem;
        this.osVersion = osVersion;
        this.platform = platform;
        this.totalMemory = totalMemory;
        this.usedMemory = usedMemory;
        this.webViewVersion = webViewVersion;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("androidSdkVersion", androidSdkVersion == null ? JSONObject.NULL : androidSdkVersion);
        result.put("deviceType", deviceType);
        result.put("iosVersion", iosVersion == null ? JSONObject.NULL : iosVersion);
        result.put("isVirtual", isVirtual);
        result.put("manufacturer", manufacturer);
        result.put("model", model);
        result.put("name", name == null ? JSONObject.NULL : name);
        result.put("operatingSystem", operatingSystem);
        result.put("osVersion", osVersion);
        result.put("platform", platform);
        result.put("totalMemory", totalMemory == null ? JSONObject.NULL : totalMemory);
        result.put("usedMemory", usedMemory == null ? JSONObject.NULL : usedMemory);
        result.put("webViewVersion", webViewVersion == null ? JSONObject.NULL : webViewVersion);
        return result;
    }
}
