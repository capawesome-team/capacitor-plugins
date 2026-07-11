package io.capawesome.capacitorjs.plugins.crisp.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.crisp.classes.CustomExceptions;

public class SetSessionSegmentOptions {

    @NonNull
    private final String segment;

    public SetSessionSegmentOptions(@NonNull PluginCall call) throws Exception {
        String segment = call.getString("segment");
        if (segment == null) {
            throw CustomExceptions.SEGMENT_MISSING;
        }
        this.segment = segment;
    }

    @NonNull
    public String getSegment() {
        return segment;
    }
}
