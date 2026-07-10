package io.capawesome.capacitorjs.plugins.rootdetection;

import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.scottyab.rootbeer.RootBeer;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsDeveloperModeEnabledResult;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsEmulatorResult;
import io.capawesome.capacitorjs.plugins.rootdetection.classes.results.IsRootedResult;
import io.capawesome.capacitorjs.plugins.rootdetection.interfaces.NonEmptyResultCallback;

public class RootDetection {

    @NonNull
    private final RootDetectionPlugin plugin;

    @NonNull
    private final RootBeer rootBeer;

    public RootDetection(@NonNull RootDetectionPlugin plugin) {
        this.plugin = plugin;
        this.rootBeer = new RootBeer(plugin.getContext());
    }

    public void isDeveloperModeEnabled(@NonNull NonEmptyResultCallback<IsDeveloperModeEnabledResult> callback) {
        boolean enabled =
            Settings.Global.getInt(plugin.getContext().getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        callback.success(new IsDeveloperModeEnabledResult(enabled));
    }

    public void isEmulator(@NonNull NonEmptyResultCallback<IsEmulatorResult> callback) {
        callback.success(new IsEmulatorResult(isRunningOnEmulator()));
    }

    public void isRooted(@NonNull NonEmptyResultCallback<IsRootedResult> callback) {
        callback.success(new IsRootedResult(rootBeer.isRooted()));
    }

    private boolean isRunningOnEmulator() {
        String fingerprint = Build.FINGERPRINT == null ? "" : Build.FINGERPRINT;
        String model = Build.MODEL == null ? "" : Build.MODEL;
        String manufacturer = Build.MANUFACTURER == null ? "" : Build.MANUFACTURER;
        String hardware = Build.HARDWARE == null ? "" : Build.HARDWARE;
        String product = Build.PRODUCT == null ? "" : Build.PRODUCT;
        String brand = Build.BRAND == null ? "" : Build.BRAND;
        String device = Build.DEVICE == null ? "" : Build.DEVICE;

        if (fingerprint.startsWith("generic") || fingerprint.startsWith("unknown")) {
            return true;
        }
        if (model.contains("google_sdk") || model.contains("Emulator") || model.contains("Android SDK built for x86")) {
            return true;
        }
        if (manufacturer.contains("Genymotion")) {
            return true;
        }
        if (hardware.contains("goldfish") || hardware.contains("ranchu")) {
            return true;
        }
        if (
            product.contains("sdk_gphone") ||
            product.contains("google_sdk") ||
            product.contains("emulator") ||
            product.contains("simulator")
        ) {
            return true;
        }
        return brand.startsWith("generic") && device.startsWith("generic");
    }
}
