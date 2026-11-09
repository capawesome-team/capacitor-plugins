package io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.Instruction;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.classes.Location;
import io.capawesome.capacitorjs.plugins.ferrostarnavigation.interfaces.Result;
import org.json.JSONObject;

public class NavigationStateChangedEvent implements Result {

    @Nullable
    private final Double distanceRemainingMeters;

    @Nullable
    private final Double distanceToNextManeuverMeters;

    @Nullable
    private final Double distanceTraveledMeters;

    @Nullable
    private final Double durationRemainingSeconds;

    @Nullable
    private final Instruction instruction;

    private final boolean isOffRoute;

    @Nullable
    private final Location location;

    @Nullable
    private final String spokenInstructionText;

    @NonNull
    private final String status;

    public NavigationStateChangedEvent(
        @NonNull String status,
        @Nullable Instruction instruction,
        @Nullable Double distanceToNextManeuverMeters,
        @Nullable Double distanceRemainingMeters,
        @Nullable Double durationRemainingSeconds,
        @Nullable Double distanceTraveledMeters,
        @Nullable Location location,
        boolean isOffRoute,
        @Nullable String spokenInstructionText
    ) {
        this.status = status;
        this.instruction = instruction;
        this.distanceToNextManeuverMeters = distanceToNextManeuverMeters;
        this.distanceRemainingMeters = distanceRemainingMeters;
        this.durationRemainingSeconds = durationRemainingSeconds;
        this.distanceTraveledMeters = distanceTraveledMeters;
        this.location = location;
        this.isOffRoute = isOffRoute;
        this.spokenInstructionText = spokenInstructionText;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("status", status);
        result.put("instruction", instruction == null ? JSONObject.NULL : instruction.toJSObject());
        result.put("distanceToNextManeuverMeters", distanceToNextManeuverMeters == null ? JSONObject.NULL : distanceToNextManeuverMeters);
        result.put("distanceRemainingMeters", distanceRemainingMeters == null ? JSONObject.NULL : distanceRemainingMeters);
        result.put("durationRemainingSeconds", durationRemainingSeconds == null ? JSONObject.NULL : durationRemainingSeconds);
        result.put("distanceTraveledMeters", distanceTraveledMeters == null ? JSONObject.NULL : distanceTraveledMeters);
        result.put("location", location == null ? JSONObject.NULL : location.toJSObject());
        result.put("isOffRoute", isOffRoute);
        result.put("spokenInstructionText", spokenInstructionText == null ? JSONObject.NULL : spokenInstructionText);
        return result;
    }
}
