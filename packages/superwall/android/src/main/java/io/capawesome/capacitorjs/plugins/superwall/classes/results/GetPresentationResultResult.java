package io.capawesome.capacitorjs.plugins.superwall.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.superwall.sdk.models.triggers.Experiment;
import io.capawesome.capacitorjs.plugins.superwall.interfaces.Result;
import org.json.JSONObject;

public class GetPresentationResultResult implements Result {

    @NonNull
    private final String type;

    @Nullable
    private final Experiment experiment;

    public GetPresentationResultResult(@NonNull String type, @Nullable Experiment experiment) {
        this.type = type;
        this.experiment = experiment;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject jsObject = new JSObject();
        jsObject.put("type", type);
        jsObject.put("experiment", experiment == null ? JSONObject.NULL : convertExperimentToJSObject(experiment));
        return jsObject;
    }

    @NonNull
    private JSObject convertExperimentToJSObject(@NonNull Experiment experiment) {
        JSObject jsObject = new JSObject();
        jsObject.put("id", experiment.getId());
        jsObject.put("variant", experiment.getVariant().getType().toString());
        return jsObject;
    }
}
