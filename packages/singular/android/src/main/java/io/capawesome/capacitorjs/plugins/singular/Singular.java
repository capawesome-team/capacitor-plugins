package io.capawesome.capacitorjs.plugins.singular;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.singular.sdk.SDIDAccessorHandler;
import com.singular.sdk.ShortLinkHandler;
import com.singular.sdk.SingularAdData;
import com.singular.sdk.SingularConfig;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.singular.classes.events.DeviceAttributionInfoReceivedEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.events.SdidReceivedEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.events.SdidSetEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.events.SingularLinkResolvedEvent;
import io.capawesome.capacitorjs.plugins.singular.classes.options.CreateReferrerShortLinkOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.InitializeOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetCustomUserIdOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetDeviceTokenOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetGlobalPropertyOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetLimitAdvertisingIdentifiersOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.SetLimitDataSharingOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackAdRevenueOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackEventOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.TrackRevenueOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.options.UnsetGlobalPropertyOptions;
import io.capawesome.capacitorjs.plugins.singular.classes.results.CreateReferrerShortLinkResult;
import io.capawesome.capacitorjs.plugins.singular.classes.results.GetGlobalPropertiesResult;
import io.capawesome.capacitorjs.plugins.singular.classes.results.GetLimitDataSharingResult;
import io.capawesome.capacitorjs.plugins.singular.classes.results.IsAllTrackingStoppedResult;
import io.capawesome.capacitorjs.plugins.singular.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.singular.interfaces.NonEmptyResultCallback;
import java.util.List;
import java.util.Map;

public class Singular {

    @NonNull
    private final SingularPlugin plugin;

    @Nullable
    private InitializeOptions initializeOptions;

    private boolean initialized = false;

    public Singular(@NonNull SingularPlugin plugin) {
        this.plugin = plugin;
    }

    public void clearGlobalProperties(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.clearGlobalProperties();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void createReferrerShortLink(
        @NonNull CreateReferrerShortLinkOptions options,
        @NonNull NonEmptyResultCallback<CreateReferrerShortLinkResult> callback
    ) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.createReferrerShortLink(
                options.getBaseLink(),
                options.getReferrerName(),
                options.getReferrerId(),
                options.getPassthroughParameters(),
                new ShortLinkHandler() {
                    @Override
                    public void onSuccess(String link) {
                        callback.success(new CreateReferrerShortLinkResult(link));
                    }

                    @Override
                    public void onError(String error) {
                        callback.error(new Exception(error));
                    }
                }
            );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getGlobalProperties(@NonNull NonEmptyResultCallback<GetGlobalPropertiesResult> callback) {
        try {
            requireInitialized();
            Map<String, String> properties = com.singular.sdk.Singular.getGlobalProperties();
            callback.success(new GetGlobalPropertiesResult(properties));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getLimitDataSharing(@NonNull NonEmptyResultCallback<GetLimitDataSharingResult> callback) {
        try {
            requireInitialized();
            boolean limit = com.singular.sdk.Singular.getLimitDataSharing();
            callback.success(new GetLimitDataSharingResult(limit));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void handleNewIntent(@NonNull Intent intent) {
        InitializeOptions options = initializeOptions;
        if (!initialized || options == null) {
            return;
        }
        // Initializing the SDK again with the new intent delivers the Singular Link of a warm start.
        com.singular.sdk.Singular.init(plugin.getContext().getApplicationContext(), createConfig(options, intent));
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        try {
            initializeOptions = options;
            SingularConfig config = createConfig(options, plugin.getActivity().getIntent());
            boolean success = com.singular.sdk.Singular.init(plugin.getContext().getApplicationContext(), config);
            if (!success) {
                throw CustomExceptions.INITIALIZATION_FAILED;
            }
            initialized = true;
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void isAllTrackingStopped(@NonNull NonEmptyResultCallback<IsAllTrackingStoppedResult> callback) {
        try {
            requireInitialized();
            boolean stopped = com.singular.sdk.Singular.isAllTrackingStopped();
            callback.success(new IsAllTrackingStoppedResult(stopped));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void resumeAllTracking(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.resumeAllTracking();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setCustomUserId(@NonNull SetCustomUserIdOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.setCustomUserId(options.getCustomUserId());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setDeviceToken(@NonNull SetDeviceTokenOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.setFCMDeviceToken(options.getToken());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setGlobalProperty(@NonNull SetGlobalPropertyOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            boolean success = com.singular.sdk.Singular.setGlobalProperty(
                options.getKey(),
                options.getValue(),
                options.isOverrideExisting()
            );
            if (!success) {
                throw CustomExceptions.GLOBAL_PROPERTY_COULD_NOT_BE_SET;
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setLimitAdvertisingIdentifiers(@NonNull SetLimitAdvertisingIdentifiersOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.setLimitAdvertisingIdentifiers(options.isLimit());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setLimitDataSharing(@NonNull SetLimitDataSharingOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.limitDataSharing(options.isLimit());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void stopAllTracking(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.stopAllTracking();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackAdRevenue(@NonNull TrackAdRevenueOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.adRevenue(createAdData(options));
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackEvent(@NonNull TrackEventOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            JSObject attributes = options.getAttributes();
            boolean success =
                attributes == null
                    ? com.singular.sdk.Singular.event(options.getName())
                    : com.singular.sdk.Singular.eventJSON(options.getName(), attributes);
            if (!success) {
                throw CustomExceptions.EVENT_COULD_NOT_BE_TRACKED;
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackRevenue(@NonNull TrackRevenueOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            String currency = options.getCurrency();
            double amount = options.getAmount();
            Map<String, Object> attributes = options.getAttributes();
            String eventName = options.getEventName();
            boolean success;
            if (eventName == null) {
                success =
                    attributes == null
                        ? com.singular.sdk.Singular.revenue(currency, amount)
                        : com.singular.sdk.Singular.revenue(currency, amount, attributes);
            } else {
                success =
                    attributes == null
                        ? com.singular.sdk.Singular.customRevenue(eventName, currency, amount)
                        : com.singular.sdk.Singular.customRevenue(eventName, currency, amount, attributes);
            }
            if (!success) {
                throw CustomExceptions.REVENUE_COULD_NOT_BE_TRACKED;
            }
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackingOptIn(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.trackingOptIn();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void trackingUnder13(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.trackingUnder13();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void unsetCustomUserId(@NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.unsetCustomUserId();
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void unsetGlobalProperty(@NonNull UnsetGlobalPropertyOptions options, @NonNull EmptyCallback callback) {
        try {
            requireInitialized();
            com.singular.sdk.Singular.unsetGlobalProperty(options.getKey());
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @NonNull
    private SingularAdData createAdData(@NonNull TrackAdRevenueOptions options) {
        SingularAdData adData = new SingularAdData(options.getAdPlatform(), options.getCurrency(), options.getRevenue());
        String adGroupId = options.getAdGroupId();
        if (adGroupId != null) {
            adData.withAdGroupId(adGroupId);
        }
        String adGroupName = options.getAdGroupName();
        if (adGroupName != null) {
            adData.withAdGroupName(adGroupName);
        }
        String adGroupPriority = options.getAdGroupPriority();
        if (adGroupPriority != null) {
            adData.withAdGroupPriority(adGroupPriority);
        }
        String adGroupType = options.getAdGroupType();
        if (adGroupType != null) {
            adData.withAdGroupType(adGroupType);
        }
        String adPlacementName = options.getAdPlacementName();
        if (adPlacementName != null) {
            adData.withAdPlacementName(adPlacementName);
        }
        String adType = options.getAdType();
        if (adType != null) {
            adData.withAdType(adType);
        }
        String adUnitId = options.getAdUnitId();
        if (adUnitId != null) {
            adData.withAdUnitId(adUnitId);
        }
        String adUnitName = options.getAdUnitName();
        if (adUnitName != null) {
            adData.withAdUnitName(adUnitName);
        }
        String impressionId = options.getImpressionId();
        if (impressionId != null) {
            adData.withImpressionId(impressionId);
        }
        String networkName = options.getNetworkName();
        if (networkName != null) {
            adData.withNetworkName(networkName);
        }
        String placementId = options.getPlacementId();
        if (placementId != null) {
            adData.withPlacementId(placementId);
        }
        String precision = options.getPrecision();
        if (precision != null) {
            adData.withPrecision(precision);
        }
        return adData;
    }

    @NonNull
    private SingularConfig createConfig(@NonNull InitializeOptions options, @NonNull Intent intent) {
        SingularConfig config = new SingularConfig(options.getApiKey(), options.getSecret());
        config.withSessionTimeoutInSec(options.getSessionTimeout());
        config.withSingularLink(
            intent,
            linkParams -> plugin.notifySingularLinkResolvedListeners(new SingularLinkResolvedEvent(linkParams)),
            options.getShortLinkResolveTimeout()
        );
        config.withSingularDeviceAttribution(attributionInfo ->
            plugin.notifyDeviceAttributionInfoReceivedListeners(new DeviceAttributionInfoReceivedEvent(attributionInfo))
        );
        String androidFacebookAppId = options.getAndroidFacebookAppId();
        if (androidFacebookAppId != null) {
            config.withFacebookAppId(androidFacebookAppId);
        }
        List<String> brandedDomains = options.getBrandedDomains();
        if (brandedDomains != null) {
            config.withBrandedDomains(brandedDomains);
        }
        SDIDAccessorHandler sdidAccessorHandler = createSdidAccessorHandler();
        String customSdid = options.getCustomSdid();
        if (customSdid == null) {
            config.withSdidAccessorHandler(sdidAccessorHandler);
        } else {
            config.withCustomSdid(customSdid, sdidAccessorHandler);
        }
        String customUserId = options.getCustomUserId();
        if (customUserId != null) {
            config.withCustomUserId(customUserId);
        }
        List<String> espDomains = options.getEspDomains();
        if (espDomains != null) {
            config.withESPDomains(espDomains);
        }
        Map<String, String> globalProperties = options.getGlobalProperties();
        if (globalProperties != null) {
            for (Map.Entry<String, String> property : globalProperties.entrySet()) {
                config.withGlobalProperty(property.getKey(), property.getValue(), true);
            }
        }
        if (options.isLimitAdvertisingIdentifiers()) {
            config.withLimitAdvertisingIdentifiers();
        }
        Boolean limitDataSharing = options.getLimitDataSharing();
        if (limitDataSharing != null) {
            config.withLimitDataSharing(limitDataSharing);
        }
        if (options.isLoggingEnabled()) {
            config.withLoggingEnabled().withLogLevel(Log.DEBUG);
        }
        return config;
    }

    @NonNull
    private SDIDAccessorHandler createSdidAccessorHandler() {
        return new SDIDAccessorHandler() {
            @Override
            public void didSetSdid(String sdid) {
                plugin.notifySdidSetListeners(new SdidSetEvent(sdid));
            }

            @Override
            public void sdidReceived(String sdid) {
                plugin.notifySdidReceivedListeners(new SdidReceivedEvent(sdid));
            }
        };
    }

    private void requireInitialized() throws Exception {
        if (!initialized) {
            throw CustomExceptions.NOT_INITIALIZED;
        }
    }
}
