package io.capawesome.capacitorjs.plugins.agesignals.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.android.play.agesignals.ApplicableAgeFeaturesResult;
import com.google.android.play.agesignals.model.ApplicableAgeFeature;
import io.capawesome.capacitorjs.plugins.agesignals.enums.RegulatoryFeature;
import io.capawesome.capacitorjs.plugins.agesignals.interfaces.Result;

public class GetRegulatoryRequirementsResult implements Result {

    private final boolean ageAssuranceRequired;

    public GetRegulatoryRequirementsResult(@NonNull ApplicableAgeFeaturesResult result) {
        this.ageAssuranceRequired = result.features().contains(ApplicableAgeFeature.MANDATORY_SHARING);
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray regulatoryFeatures = new JSArray();
        if (ageAssuranceRequired) {
            regulatoryFeatures.put(RegulatoryFeature.DECLARED_AGE_RANGE_REQUIRED.name());
        }
        JSObject result = new JSObject();
        result.put("ageAssuranceRequired", ageAssuranceRequired);
        result.put("regulatoryFeatures", regulatoryFeatures);
        return result;
    }
}
