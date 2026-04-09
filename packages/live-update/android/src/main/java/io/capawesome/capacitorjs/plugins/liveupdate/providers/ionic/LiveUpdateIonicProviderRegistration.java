package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic;

import androidx.annotation.NonNull;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdatePlugin;
import io.ionic.liveupdateprovider.LiveUpdateProviderRegistry;

/**
 * Helper that registers the Capawesome live update provider with the Ionic
 * {@link LiveUpdateProviderRegistry}, but only when the optional
 * {@code io.ionic:liveupdateprovider} dependency is present at runtime.
 *
 * The Gradle build wires the SDK as either {@code implementation} (when the consumer opts in
 * via {@code capawesomeCapacitorLiveUpdateIncludeIonicProvider = true}) or {@code compileOnly}
 * (the default). When opted out, the SDK classes are stripped from the consumer APK and the
 * reflection check below short-circuits registration.
 */
public final class LiveUpdateIonicProviderRegistration {

    private LiveUpdateIonicProviderRegistration() {
        // Static utility — no instances.
    }

    public static boolean isAvailable() {
        try {
            Class.forName("io.ionic.liveupdateprovider.LiveUpdateProviderRegistry");
            return true;
        } catch (ClassNotFoundException exception) {
            return false;
        }
    }

    public static void tryRegister(@NonNull LiveUpdate liveUpdate) {
        try {
            if (LiveUpdateProviderRegistry.resolve(LiveUpdateIonicProvider.ID) != null) {
                Logger.debug(LiveUpdatePlugin.TAG, "Ionic provider '" + LiveUpdateIonicProvider.ID + "' already registered.");
                return;
            }
            LiveUpdateProviderRegistry.register(new LiveUpdateIonicProvider(liveUpdate));
            Logger.debug(LiveUpdatePlugin.TAG, "Registered Ionic provider '" + LiveUpdateIonicProvider.ID + "'.");
        } catch (Exception exception) {
            Logger.error(LiveUpdatePlugin.TAG, "Failed to register Ionic provider: " + exception.getMessage(), exception);
        }
    }
}
