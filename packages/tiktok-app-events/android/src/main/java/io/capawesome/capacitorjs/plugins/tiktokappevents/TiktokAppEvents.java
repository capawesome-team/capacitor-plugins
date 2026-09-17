package io.capawesome.capacitorjs.plugins.tiktokappevents;

import android.content.Context;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import com.tiktok.TikTokBusinessSdk;
import com.tiktok.appevents.base.TTBaseEvent;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomException;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.IdentifyOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.classes.options.TrackEventOptions;
import io.capawesome.capacitorjs.plugins.tiktokappevents.interfaces.EmptyCallback;
import java.util.Iterator;

public class TiktokAppEvents {

    @NonNull
    private final TiktokAppEventsPlugin plugin;

    public TiktokAppEvents(@NonNull TiktokAppEventsPlugin plugin) {
        this.plugin = plugin;
    }

    public void flush(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            TikTokBusinessSdk.flush();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void identify(@NonNull IdentifyOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            TikTokBusinessSdk.identify(
                options.getExternalId(),
                options.getExternalUserName(),
                options.getPhoneNumber(),
                options.getEmail()
            );
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        // The SDK ignores repeated initializations without invoking the callback.
        if (TikTokBusinessSdk.isInitialized()) {
            callback.success();
            return;
        }
        try {
            TikTokBusinessSdk.initializeSdk(
                createConfig(options),
                new TikTokBusinessSdk.TTInitCallback() {
                    @Override
                    public void success() {
                        callback.success();
                    }

                    @Override
                    public void fail(int code, String message) {
                        callback.error(new CustomException("INITIALIZATION_FAILED", message));
                    }
                }
            );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void logout(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            TikTokBusinessSdk.logout();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackEvent(@NonNull TrackEventOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            TikTokBusinessSdk.trackTTEvent(createEvent(options));
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @NonNull
    private TikTokBusinessSdk.TTConfig createConfig(@NonNull InitializeOptions options) {
        Context context = plugin.getContext().getApplicationContext();
        TikTokBusinessSdk.TTConfig config = new TikTokBusinessSdk.TTConfig(context, options.getAccessToken())
            .setAppId(context.getPackageName())
            .setTTAppId(options.getTiktokAppId());
        if (!options.isAutomaticTracking()) {
            config.disableAutoEvents();
        }
        if (!options.isAutomaticPurchaseTracking()) {
            config.disableAutoIapTrack();
        }
        if (options.isDebugMode()) {
            config.openDebugMode();
        }
        if (options.isLimitedDataUse()) {
            config.enableLimitedDataUse();
        }
        return config;
    }

    @NonNull
    private TTBaseEvent createEvent(@NonNull TrackEventOptions options) {
        String id = options.getId();
        TTBaseEvent.Builder builder =
            id == null ? TTBaseEvent.newBuilder(options.getName()) : TTBaseEvent.newBuilder(options.getName(), id);
        JSObject properties = options.getProperties();
        if (properties != null) {
            Iterator<String> keys = properties.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                builder.addProperty(key, properties.opt(key));
            }
        }
        return builder.build();
    }

    private void requireInitialized() throws Exception {
        if (!TikTokBusinessSdk.isInitialized()) {
            throw CustomExceptions.NOT_INITIALIZED;
        }
    }
}
