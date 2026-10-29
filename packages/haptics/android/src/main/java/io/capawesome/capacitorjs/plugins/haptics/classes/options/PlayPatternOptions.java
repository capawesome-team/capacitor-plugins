package io.capawesome.capacitorjs.plugins.haptics.classes.options;

import android.annotation.SuppressLint;
import android.os.VibrationEffect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.haptics.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class PlayPatternOptions {

    public static class Event {

        private final long durationMs;
        private final float intensity;

        @Nullable
        private final Integer primitive;

        private final long timeMs;

        public Event(long durationMs, float intensity, @Nullable Integer primitive, long timeMs) {
            this.durationMs = durationMs;
            this.intensity = intensity;
            this.primitive = primitive;
            this.timeMs = timeMs;
        }

        public long getDurationMs() {
            return durationMs;
        }

        public float getIntensity() {
            return intensity;
        }

        @Nullable
        public Integer getPrimitive() {
            return primitive;
        }

        public long getTimeMs() {
            return timeMs;
        }

        public boolean isTransient() {
            return durationMs == 0;
        }
    }

    @NonNull
    private final List<Event> events;

    public PlayPatternOptions(@NonNull PluginCall call) throws Exception {
        this.events = PlayPatternOptions.getEventsFromCall(call);
    }

    @NonNull
    public List<Event> getEvents() {
        return events;
    }

    @NonNull
    private static List<Event> getEventsFromCall(@NonNull PluginCall call) throws Exception {
        JSArray eventsArray = call.getArray("events");
        if (eventsArray == null || eventsArray.length() == 0) {
            throw CustomExceptions.EVENTS_MISSING;
        }
        List<Event> events = new ArrayList<>();
        for (int index = 0; index < eventsArray.length(); index++) {
            JSONObject eventObject = eventsArray.getJSONObject(index);
            if (!eventObject.has("time")) {
                throw CustomExceptions.TIME_MISSING;
            }
            double time = eventObject.getDouble("time");
            if (time < 0) {
                throw CustomExceptions.TIME_INVALID;
            }
            if (!eventObject.has("intensity")) {
                throw CustomExceptions.INTENSITY_MISSING;
            }
            double intensity = eventObject.getDouble("intensity");
            if (intensity < 0 || intensity > 1) {
                throw CustomExceptions.INTENSITY_INVALID;
            }
            long durationMs = 0;
            if (eventObject.has("duration")) {
                double duration = eventObject.getDouble("duration");
                if (duration <= 0) {
                    throw CustomExceptions.DURATION_INVALID;
                }
                durationMs = Math.round(duration * 1000);
            }
            Integer primitive = null;
            if (eventObject.has("primitive")) {
                primitive = PlayPatternOptions.getPrimitiveForName(eventObject.getString("primitive"));
            }
            events.add(new Event(durationMs, (float) intensity, primitive, Math.round(time * 1000)));
        }
        return events;
    }

    @SuppressLint("InlinedApi")
    private static int getPrimitiveForName(@NonNull String name) throws Exception {
        switch (name) {
            case "CLICK":
                return VibrationEffect.Composition.PRIMITIVE_CLICK;
            case "LOW_TICK":
                return VibrationEffect.Composition.PRIMITIVE_LOW_TICK;
            case "QUICK_FALL":
                return VibrationEffect.Composition.PRIMITIVE_QUICK_FALL;
            case "QUICK_RISE":
                return VibrationEffect.Composition.PRIMITIVE_QUICK_RISE;
            case "SLOW_RISE":
                return VibrationEffect.Composition.PRIMITIVE_SLOW_RISE;
            case "SPIN":
                return VibrationEffect.Composition.PRIMITIVE_SPIN;
            case "THUD":
                return VibrationEffect.Composition.PRIMITIVE_THUD;
            case "TICK":
                return VibrationEffect.Composition.PRIMITIVE_TICK;
            default:
                throw CustomExceptions.PRIMITIVE_INVALID;
        }
    }
}
