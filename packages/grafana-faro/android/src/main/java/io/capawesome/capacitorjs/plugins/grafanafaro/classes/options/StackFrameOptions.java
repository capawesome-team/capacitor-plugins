package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;

public class StackFrameOptions {

    @Nullable
    private final Integer columnNumber;

    @Nullable
    private final String fileName;

    @Nullable
    private final String functionName;

    @Nullable
    private final Integer lineNumber;

    public StackFrameOptions(@NonNull JSObject source) {
        this.columnNumber = source.has("columnNumber") ? source.optInt("columnNumber") : null;
        this.fileName = source.getString("fileName");
        this.functionName = source.getString("functionName");
        this.lineNumber = source.has("lineNumber") ? source.optInt("lineNumber") : null;
    }

    @Nullable
    public Integer getColumnNumber() {
        return columnNumber;
    }

    @Nullable
    public String getFileName() {
        return fileName;
    }

    @Nullable
    public String getFunctionName() {
        return functionName;
    }

    @Nullable
    public Integer getLineNumber() {
        return lineNumber;
    }
}
