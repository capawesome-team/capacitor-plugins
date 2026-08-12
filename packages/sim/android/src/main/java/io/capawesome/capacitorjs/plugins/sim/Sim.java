package io.capawesome.capacitorjs.plugins.sim;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.sim.classes.results.GetSimCardsResult;
import io.capawesome.capacitorjs.plugins.sim.classes.results.SimCard;
import io.capawesome.capacitorjs.plugins.sim.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;

public class Sim {

    @NonNull
    private final SimPlugin plugin;

    public Sim(@NonNull SimPlugin plugin) {
        this.plugin = plugin;
    }

    public void getSimCards(@NonNull NonEmptyResultCallback<GetSimCardsResult> callback) {
        try {
            List<SimCard> simCards = new ArrayList<>();
            SubscriptionManager subscriptionManager = getSubscriptionManager();
            if (subscriptionManager != null) {
                List<SubscriptionInfo> subscriptionInfos = subscriptionManager.getActiveSubscriptionInfoList();
                if (subscriptionInfos != null) {
                    for (SubscriptionInfo subscriptionInfo : subscriptionInfos) {
                        simCards.add(createSimCard(subscriptionManager, subscriptionInfo));
                    }
                }
            }
            callback.success(new GetSimCardsResult(simCards));
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    @Nullable
    private String charSequenceToString(@Nullable CharSequence value) {
        if (value == null) {
            return null;
        }
        return emptyToNull(value.toString());
    }

    @NonNull
    private SimCard createSimCard(@NonNull SubscriptionManager subscriptionManager, @NonNull SubscriptionInfo subscriptionInfo) {
        int slotIndex = subscriptionInfo.getSimSlotIndex();
        String carrierName = charSequenceToString(subscriptionInfo.getCarrierName());
        String displayName = charSequenceToString(subscriptionInfo.getDisplayName());
        String isoCountryCode = emptyToNull(subscriptionInfo.getCountryIso());
        String mobileCountryCode = getMobileCountryCode(subscriptionInfo);
        String mobileNetworkCode = getMobileNetworkCode(subscriptionInfo);
        Boolean isEmbedded = getIsEmbedded(subscriptionInfo);
        String phoneNumber = getPhoneNumber(subscriptionManager, subscriptionInfo);
        return new SimCard(
            slotIndex,
            carrierName,
            displayName,
            isoCountryCode,
            mobileCountryCode,
            mobileNetworkCode,
            isEmbedded,
            phoneNumber
        );
    }

    @Nullable
    private String emptyToNull(@Nullable String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    @Nullable
    private Boolean getIsEmbedded(@NonNull SubscriptionInfo subscriptionInfo) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return null;
        }
        return subscriptionInfo.isEmbedded();
    }

    @Nullable
    private String getMobileCountryCode(@NonNull SubscriptionInfo subscriptionInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return emptyToNull(subscriptionInfo.getMccString());
        }
        int mcc = subscriptionInfo.getMcc();
        return mcc == 0 ? null : String.valueOf(mcc);
    }

    @Nullable
    private String getMobileNetworkCode(@NonNull SubscriptionInfo subscriptionInfo) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return emptyToNull(subscriptionInfo.getMncString());
        }
        int mnc = subscriptionInfo.getMnc();
        return mnc == 0 ? null : String.valueOf(mnc);
    }

    @Nullable
    private String getPhoneNumber(@NonNull SubscriptionManager subscriptionManager, @NonNull SubscriptionInfo subscriptionInfo) {
        try {
            String phoneNumber;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                phoneNumber = subscriptionManager.getPhoneNumber(subscriptionInfo.getSubscriptionId());
            } else {
                phoneNumber = subscriptionInfo.getNumber();
            }
            return emptyToNull(phoneNumber);
        } catch (Exception exception) {
            return null;
        }
    }

    @Nullable
    private SubscriptionManager getSubscriptionManager() {
        Context context = plugin.getContext();
        return (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }
}
