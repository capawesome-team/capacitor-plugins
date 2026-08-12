package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONObject;

public class StackFrame {

    @Nullable
    private final String functionName;

    @Nullable
    private final String fileName;

    @Nullable
    private final Integer lineNumber;

    @Nullable
    private final Integer columnNumber;

    public StackFrame(@NonNull JSONObject frame) {
        this.functionName = frame.isNull("functionName") ? null : frame.optString("functionName", null);
        this.fileName = frame.isNull("fileName") ? null : frame.optString("fileName", null);
        this.lineNumber = frame.has("lineNumber") && !frame.isNull("lineNumber") ? frame.optInt("lineNumber") : null;
        this.columnNumber = frame.has("columnNumber") && !frame.isNull("columnNumber") ? frame.optInt("columnNumber") : null;
    }

    @Nullable
    public String getFunctionName() {
        return functionName;
    }

    @Nullable
    public String getFileName() {
        return fileName;
    }

    @Nullable
    public Integer getLineNumber() {
        return lineNumber;
    }

    @Nullable
    public Integer getColumnNumber() {
        return columnNumber;
    }
}
