package io.capawesome.capacitorjs.plugins.deviceinfo;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetIdResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetInfoResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.classes.results.GetUptimeResult;
import io.capawesome.capacitorjs.plugins.deviceinfo.interfaces.NonEmptyResultCallback;

public class DeviceInfo {

    @NonNull
    private final Context context;

    public DeviceInfo(@NonNull Context context) {
        this.context = context;
    }

    public void getId(@NonNull NonEmptyResultCallback<GetIdResult> callback) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String identifier = androidId == null ? "" : androidId;
        callback.success(new GetIdResult(identifier));
    }

    public void getInfo(@NonNull NonEmptyResultCallback<GetInfoResult> callback) {
        GetInfoResult result = new GetInfoResult(
            Build.VERSION.SDK_INT,
            determineDeviceType(),
            null,
            determineIsVirtual(),
            Build.MANUFACTURER,
            Build.MODEL,
            determineName(),
            "android",
            Build.VERSION.RELEASE,
            "android",
            determineTotalMemory(),
            determineUsedMemory(),
            determineWebViewVersion()
        );
        callback.success(result);
    }

    public void getUptime(@NonNull NonEmptyResultCallback<GetUptimeResult> callback) {
        long uptime = SystemClock.elapsedRealtime();
        callback.success(new GetUptimeResult(uptime));
    }

    @NonNull
    private String determineDeviceType() {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        if (uiModeManager != null && uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
            return "tv";
        }
        Configuration configuration = context.getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return "tablet";
        }
        return "phone";
    }

    private boolean determineIsVirtual() {
        return (
            Build.FINGERPRINT.startsWith("generic") ||
            Build.FINGERPRINT.startsWith("unknown") ||
            Build.MODEL.contains("google_sdk") ||
            Build.MODEL.contains("Emulator") ||
            Build.MODEL.contains("Android SDK built for x86") ||
            Build.MANUFACTURER.contains("Genymotion") ||
            (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
            "google_sdk".equals(Build.PRODUCT) ||
            Build.HARDWARE.contains("goldfish") ||
            Build.HARDWARE.contains("ranchu")
        );
    }

    @Nullable
    private String determineName() {
        return Settings.Global.getString(context.getContentResolver(), Settings.Global.DEVICE_NAME);
    }

    @Nullable
    private Long determineTotalMemory() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    @NonNull
    private Long determineUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    @Nullable
    private String determineWebViewVersion() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return null;
        }
        PackageInfo packageInfo = WebView.getCurrentWebViewPackage();
        if (packageInfo == null) {
            return null;
        }
        return packageInfo.versionName;
    }
}
