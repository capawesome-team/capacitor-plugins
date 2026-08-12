package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.posthog.PosthogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class CaptureExceptionOptions {

    @NonNull
    private final String message;

    @Nullable
    private final String name;

    @Nullable
    private final List<StackFrame> stacktrace;

    @Nullable
    private final Map<String, Object> properties;

    public CaptureExceptionOptions(
        @NonNull String message,
        @Nullable String name,
        @Nullable JSArray stacktrace,
        @Nullable JSObject properties
    ) throws JSONException {
        this.message = message;
        this.name = name;
        this.stacktrace = createStackFrames(stacktrace);
        this.properties = PosthogHelper.createHashMapFromJSONObject(properties);
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public List<StackFrame> getStacktrace() {
        return stacktrace;
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Nullable
    private static List<StackFrame> createStackFrames(@Nullable JSArray stacktrace) throws JSONException {
        if (stacktrace == null) {
            return null;
        }
        List<StackFrame> frames = new ArrayList<>();
        for (int i = 0; i < stacktrace.length(); i++) {
            JSONObject frame = stacktrace.getJSONObject(i);
            frames.add(new StackFrame(frame));
        }
        return frames;
    }
}
