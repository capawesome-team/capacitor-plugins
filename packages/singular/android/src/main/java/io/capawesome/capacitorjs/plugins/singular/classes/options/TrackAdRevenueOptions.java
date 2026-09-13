package io.capawesome.capacitorjs.plugins.singular.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.singular.classes.CustomExceptions;

public class TrackAdRevenueOptions {

    @Nullable
    private final String adGroupId;

    @Nullable
    private final String adGroupName;

    @Nullable
    private final String adGroupPriority;

    @Nullable
    private final String adGroupType;

    @Nullable
    private final String adPlacementName;

    @NonNull
    private final String adPlatform;

    @Nullable
    private final String adType;

    @Nullable
    private final String adUnitId;

    @Nullable
    private final String adUnitName;

    @NonNull
    private final String currency;

    @Nullable
    private final String impressionId;

    @Nullable
    private final String networkName;

    @Nullable
    private final String placementId;

    @Nullable
    private final String precision;

    private final double revenue;

    public TrackAdRevenueOptions(@NonNull PluginCall call) throws Exception {
        String adPlatform = call.getString("adPlatform");
        if (adPlatform == null) {
            throw CustomExceptions.AD_PLATFORM_MISSING;
        }
        String currency = call.getString("currency");
        if (currency == null) {
            throw CustomExceptions.CURRENCY_MISSING;
        }
        Double revenue = call.getDouble("revenue");
        if (revenue == null) {
            throw CustomExceptions.REVENUE_MISSING;
        }
        this.adGroupId = call.getString("adGroupId");
        this.adGroupName = call.getString("adGroupName");
        this.adGroupPriority = call.getString("adGroupPriority");
        this.adGroupType = call.getString("adGroupType");
        this.adPlacementName = call.getString("adPlacementName");
        this.adPlatform = adPlatform;
        this.adType = call.getString("adType");
        this.adUnitId = call.getString("adUnitId");
        this.adUnitName = call.getString("adUnitName");
        this.currency = currency;
        this.impressionId = call.getString("impressionId");
        this.networkName = call.getString("networkName");
        this.placementId = call.getString("placementId");
        this.precision = call.getString("precision");
        this.revenue = revenue;
    }

    @Nullable
    public String getAdGroupId() {
        return adGroupId;
    }

    @Nullable
    public String getAdGroupName() {
        return adGroupName;
    }

    @Nullable
    public String getAdGroupPriority() {
        return adGroupPriority;
    }

    @Nullable
    public String getAdGroupType() {
        return adGroupType;
    }

    @Nullable
    public String getAdPlacementName() {
        return adPlacementName;
    }

    @NonNull
    public String getAdPlatform() {
        return adPlatform;
    }

    @Nullable
    public String getAdType() {
        return adType;
    }

    @Nullable
    public String getAdUnitId() {
        return adUnitId;
    }

    @Nullable
    public String getAdUnitName() {
        return adUnitName;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    @Nullable
    public String getImpressionId() {
        return impressionId;
    }

    @Nullable
    public String getNetworkName() {
        return networkName;
    }

    @Nullable
    public String getPlacementId() {
        return placementId;
    }

    @Nullable
    public String getPrecision() {
        return precision;
    }

    public double getRevenue() {
        return revenue;
    }
}
