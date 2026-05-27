package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;

public class InstrumentationsOptions {

    private final boolean anrTracking;
    private final boolean nativeCrashReporting;

    public InstrumentationsOptions(@NonNull JSObject source) {
        this.anrTracking = source.optBoolean("anrTracking", false);
        this.nativeCrashReporting = source.optBoolean("nativeCrashReporting", false);
    }

    public boolean isAnrTrackingEnabled() {
        return anrTracking;
    }

    public boolean isNativeCrashReportingEnabled() {
        return nativeCrashReporting;
    }
}
