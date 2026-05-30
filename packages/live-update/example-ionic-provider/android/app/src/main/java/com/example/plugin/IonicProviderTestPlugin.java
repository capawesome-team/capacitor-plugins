package com.example.plugin;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.ionic.liveupdateprovider.FederatedCapacitorSyncResult;
import io.ionic.liveupdateprovider.LiveUpdateProvider;
import io.ionic.liveupdateprovider.LiveUpdateProviderError;
import io.ionic.liveupdateprovider.LiveUpdateProviderManager;
import io.ionic.liveupdateprovider.LiveUpdateProviderRegistry;
import io.ionic.liveupdateprovider.LiveUpdateProviderSyncCallback;
import io.ionic.liveupdateprovider.LiveUpdateProviderSyncResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Capacitor plugin for the example app that exposes the Ionic Live Update
 * Provider SDK to JavaScript so the integration can be tested end-to-end
 * without needing a real Federated Capacitor or Portals host.
 */
@CapacitorPlugin(name = "IonicProviderTest")
public class IonicProviderTestPlugin extends Plugin {

    public static final String ERROR_INVALID_CONFIGURATION = "Invalid configuration.";
    public static final String ERROR_MANAGER_KEY_MISSING = "managerKey must be provided.";
    public static final String ERROR_PROVIDER_NOT_REGISTERED =
        "Provider 'capawesome' is not registered. Make sure capawesomeCapacitorLiveUpdateIncludeIonicProvider = true in variables.gradle.";
    public static final String ERROR_SYNC_FAILED = "Sync failed.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private static final String PROVIDER_ID = "capawesome";

    @PluginMethod
    public void isProviderRegistered(PluginCall call) {
        LiveUpdateProvider provider = LiveUpdateProviderRegistry.resolve(PROVIDER_ID);
        JSObject ret = new JSObject();
        ret.put("registered", provider != null);
        call.resolve(ret);
    }

    @PluginMethod
    public void getLatestAppDirectory(PluginCall call) {
        try {
            LiveUpdateProviderManager manager = createManagerFromCall(call);
            if (manager == null) {
                return;
            }
            File directory = manager.getLatestAppDirectory();
            JSObject ret = new JSObject();
            ret.put("latestAppDirectory", directory == null ? null : directory.getAbsolutePath());
            call.resolve(ret);
        } catch (Exception exception) {
            call.reject(exception.getMessage() == null ? ERROR_UNKNOWN_ERROR : exception.getMessage());
        }
    }

    @PluginMethod
    public void syncManager(PluginCall call) {
        try {
            LiveUpdateProviderManager manager = createManagerFromCall(call);
            if (manager == null) {
                return;
            }
            manager.sync(
                new LiveUpdateProviderSyncCallback() {
                    @Override
                    public void onSuccess(@NonNull LiveUpdateProviderSyncResult result) {
                        JSObject ret = new JSObject();
                        File directory = manager.getLatestAppDirectory();
                        ret.put("latestAppDirectory", directory == null ? null : directory.getAbsolutePath());
                        if (result instanceof FederatedCapacitorSyncResult) {
                            Map<String, Object> metadata = ((FederatedCapacitorSyncResult) result).getMetadata();
                            if (metadata != null) {
                                try {
                                    ret.put("metadata", new JSObject(new org.json.JSONObject(metadata).toString()));
                                } catch (Exception ignored) {
                                    // Best-effort metadata bridging.
                                }
                            }
                        }
                        call.resolve(ret);
                    }

                    @Override
                    public void onFailure(@NonNull LiveUpdateProviderError.SyncFailed error) {
                        call.reject(error.getMessage() == null ? ERROR_SYNC_FAILED : error.getMessage());
                    }
                }
            );
        } catch (Exception exception) {
            call.reject(exception.getMessage() == null ? ERROR_UNKNOWN_ERROR : exception.getMessage());
        }
    }

    private LiveUpdateProviderManager createManagerFromCall(PluginCall call) {
        String managerKey = call.getString("managerKey");
        if (managerKey == null || managerKey.isEmpty()) {
            call.reject(ERROR_MANAGER_KEY_MISSING);
            return null;
        }
        LiveUpdateProvider provider = LiveUpdateProviderRegistry.resolve(PROVIDER_ID);
        if (provider == null) {
            call.reject(ERROR_PROVIDER_NOT_REGISTERED);
            return null;
        }
        Map<String, Object> config = new HashMap<>();
        config.put("managerKey", managerKey);
        String appId = call.getString("appId");
        if (appId != null) {
            config.put("appId", appId);
        }
        String channel = call.getString("channel");
        if (channel != null) {
            config.put("channel", channel);
        }
        try {
            return provider.createManager(getContext().getApplicationContext(), config);
        } catch (LiveUpdateProviderError.InvalidConfiguration error) {
            call.reject(error.getMessage() == null ? ERROR_INVALID_CONFIGURATION : error.getMessage());
            return null;
        }
    }
}
