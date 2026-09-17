package io.capawesome.capacitorjs.plugins.optionpicker.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.optionpicker.enums.Theme;
import java.util.ArrayList;
import java.util.List;

public class PresentOptions {

    @NonNull
    private final String cancelButtonText;

    @NonNull
    private final String doneButtonText;

    @NonNull
    private final List<PickerOption> options;

    @NonNull
    private final Theme theme;

    @Nullable
    private final String title;

    @Nullable
    private final String value;

    public PresentOptions(@NonNull PluginCall call) throws Exception {
        this.cancelButtonText = call.getString("cancelButtonText", "Cancel");
        this.doneButtonText = call.getString("doneButtonText", "Ok");
        this.options = PresentOptions.getOptionsFromCall(call);
        this.theme = PresentOptions.getThemeFromCall(call);
        this.title = call.getString("title");
        this.value = call.getString("value");
    }

    @NonNull
    public String getCancelButtonText() {
        return cancelButtonText;
    }

    @NonNull
    public String getDoneButtonText() {
        return doneButtonText;
    }

    @NonNull
    public List<PickerOption> getOptions() {
        return options;
    }

    @NonNull
    public Theme getTheme() {
        return theme;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    @NonNull
    private static List<PickerOption> getOptionsFromCall(@NonNull PluginCall call) throws Exception {
        JSArray optionsArray = call.getArray("options");
        if (optionsArray == null) {
            throw CustomExceptions.OPTIONS_MISSING;
        }
        if (optionsArray.length() == 0) {
            throw CustomExceptions.OPTIONS_EMPTY;
        }
        List<PickerOption> options = new ArrayList<>();
        for (int i = 0; i < optionsArray.length(); i++) {
            options.add(new PickerOption(optionsArray.getJSONObject(i)));
        }
        return options;
    }

    @NonNull
    private static Theme getThemeFromCall(@NonNull PluginCall call) throws Exception {
        Theme theme = Theme.fromValue(call.getString("theme", "auto"));
        if (theme == null) {
            throw CustomExceptions.THEME_INVALID;
        }
        return theme;
    }
}
