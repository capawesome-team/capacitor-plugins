package io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic;

import android.content.Context;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.liveupdate.LiveUpdate;
import io.ionic.liveupdateprovider.LiveUpdateProvider;
import io.ionic.liveupdateprovider.LiveUpdateProviderError;
import io.ionic.liveupdateprovider.LiveUpdateProviderManager;
import java.util.Map;

/**
 * Ionic Live Update Provider implementation backed by the Capawesome live update plugin.
 *
 * Registered with {@link io.ionic.liveupdateprovider.LiveUpdateProviderRegistry} when the
 * Capacitor plugin loads, so Federated Capacitor can resolve provider id {@code "capawesome"}
 * and create managers from the per-shell configuration.
 */
public class LiveUpdateIonicProvider implements LiveUpdateProvider {

    public static final String ID = "capawesome";

    @NonNull
    private final LiveUpdate liveUpdate;

    public LiveUpdateIonicProvider(@NonNull LiveUpdate liveUpdate) {
        this.liveUpdate = liveUpdate;
    }

    @NonNull
    @Override
    public String getId() {
        return ID;
    }

    @NonNull
    @Override
    public LiveUpdateProviderManager createManager(@NonNull Context context, @NonNull Map<String, ?> config)
        throws LiveUpdateProviderError.InvalidConfiguration {
        return new LiveUpdateIonicManager(context, config, liveUpdate);
    }
}
