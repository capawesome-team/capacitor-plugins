package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.Result;
import org.json.JSONObject;

public class Instruction implements Result {

    @Nullable
    private final String maneuverModifier;

    @Nullable
    private final String maneuverType;

    @Nullable
    private final String secondaryText;

    @NonNull
    private final String text;

    public Instruction(
        @NonNull String text,
        @Nullable String secondaryText,
        @Nullable String maneuverType,
        @Nullable String maneuverModifier
    ) {
        this.text = text;
        this.secondaryText = secondaryText;
        this.maneuverType = maneuverType;
        this.maneuverModifier = maneuverModifier;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("text", text);
        result.put("secondaryText", secondaryText == null ? JSONObject.NULL : secondaryText);
        result.put("maneuverType", maneuverType == null ? JSONObject.NULL : maneuverType);
        result.put("maneuverModifier", maneuverModifier == null ? JSONObject.NULL : maneuverModifier);
        return result;
    }
}
