package io.capawesome.capacitorjs.plugins.agesignals.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import com.google.android.play.agesignals.ApplicableAgeFeaturesResult;
import com.google.android.play.agesignals.model.ApplicableAgeFeature;
import io.capawesome.capacitorjs.plugins.agesignals.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.agesignals.enums.RegulatoryFeature;
import java.util.HashSet;
import java.util.Set;

public class SetNextApplicableAgeFeaturesResultOptions {

    @NonNull
    private final Set<RegulatoryFeature> regulatoryFeatures;

    public SetNextApplicableAgeFeaturesResultOptions(@NonNull PluginCall call) throws Exception {
        this.regulatoryFeatures = SetNextApplicableAgeFeaturesResultOptions.getRegulatoryFeaturesFromCall(call);
    }

    @NonNull
    public ApplicableAgeFeaturesResult buildApplicableAgeFeaturesResult() {
        Set<Integer> features = new HashSet<>();
        if (this.regulatoryFeatures.contains(RegulatoryFeature.DECLARED_AGE_RANGE_REQUIRED)) {
            features.add(ApplicableAgeFeature.MANDATORY_SHARING);
        }
        return ApplicableAgeFeaturesResult.builder().setFeatures(features).build();
    }

    @NonNull
    private static Set<RegulatoryFeature> getRegulatoryFeaturesFromCall(@NonNull PluginCall call) throws Exception {
        JSArray regulatoryFeatures = call.getArray("regulatoryFeatures");
        if (regulatoryFeatures == null) {
            throw CustomExceptions.REGULATORY_FEATURES_MISSING;
        }
        Set<RegulatoryFeature> result = new HashSet<>();
        for (String regulatoryFeature : regulatoryFeatures.<String>toList()) {
            try {
                result.add(RegulatoryFeature.valueOf(regulatoryFeature));
            } catch (IllegalArgumentException exception) {
                throw new Exception("Invalid regulatoryFeature: " + regulatoryFeature);
            }
        }
        return result;
    }
}
