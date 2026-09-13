package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InitializeOptions {

    @Nullable
    private final String androidFacebookAppId;

    @NonNull
    private final String apiKey;

    @Nullable
    private final List<String> brandedDomains;

    @Nullable
    private final String customUserId;

    @Nullable
    private final List<String> espDomains;

    @Nullable
    private final Map<String, String> globalProperties;

    private final boolean limitAdvertisingIdentifiers;

    @Nullable
    private final Boolean limitDataSharing;

    private final boolean loggingEnabled;

    @NonNull
    private final String secret;

    private final int sessionTimeout;

    private final int shortLinkResolveTimeout;

    public InitializeOptions(@NonNull PluginCall call) throws Exception {
        String apiKey = call.getString("apiKey");
        if (apiKey == null) {
            throw CustomExceptions.API_KEY_MISSING;
        }
        String secret = call.getString("secret");
        if (secret == null) {
            throw CustomExceptions.SECRET_MISSING;
        }
        this.androidFacebookAppId = call.getString("androidFacebookAppId");
        this.apiKey = apiKey;
        this.brandedDomains = createStringList(call.getArray("brandedDomains"));
        this.customUserId = call.getString("customUserId");
        this.espDomains = createStringList(call.getArray("espDomains"));
        this.globalProperties = createStringMap(call.getObject("globalProperties"));
        this.limitAdvertisingIdentifiers = call.getBoolean("limitAdvertisingIdentifiers", false);
        this.limitDataSharing = call.getBoolean("limitDataSharing");
        this.loggingEnabled = call.getBoolean("loggingEnabled", false);
        this.secret = secret;
        this.sessionTimeout = call.getInt("sessionTimeout", 60);
        this.shortLinkResolveTimeout = call.getInt("shortLinkResolveTimeout", 10);
    }

    @Nullable
    public String getAndroidFacebookAppId() {
        return androidFacebookAppId;
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    @Nullable
    public List<String> getBrandedDomains() {
        return brandedDomains;
    }

    @Nullable
    public String getCustomUserId() {
        return customUserId;
    }

    @Nullable
    public List<String> getEspDomains() {
        return espDomains;
    }

    @Nullable
    public Map<String, String> getGlobalProperties() {
        return globalProperties;
    }

    @Nullable
    public Boolean getLimitDataSharing() {
        return limitDataSharing;
    }

    @NonNull
    public String getSecret() {
        return secret;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public int getShortLinkResolveTimeout() {
        return shortLinkResolveTimeout;
    }

    public boolean isLimitAdvertisingIdentifiers() {
        return limitAdvertisingIdentifiers;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    @Nullable
    private static List<String> createStringList(@Nullable JSArray array) {
        if (array == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int index = 0; index < array.length(); index++) {
            Object value = array.opt(index);
            if (value != null) {
                list.add(value.toString());
            }
        }
        return list;
    }

    @Nullable
    private static Map<String, String> createStringMap(@Nullable JSObject object) {
        if (object == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = object.opt(key);
            if (value != null) {
                map.put(key, value.toString());
            }
        }
        return map;
    }
}
