package io.capawesome.capacitorjs.plugins.localization.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.localization.interfaces.Result;

public class GetSettingsResult implements Result {

    private final int firstDayOfWeek;

    @NonNull
    private final String timeZone;

    private final boolean uses24HourClock;

    public GetSettingsResult(@NonNull String timeZone, boolean uses24HourClock, int firstDayOfWeek) {
        this.timeZone = timeZone;
        this.uses24HourClock = uses24HourClock;
        this.firstDayOfWeek = firstDayOfWeek;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("firstDayOfWeek", firstDayOfWeek);
        result.put("timeZone", timeZone);
        result.put("uses24HourClock", uses24HourClock);
        return result;
    }
}
