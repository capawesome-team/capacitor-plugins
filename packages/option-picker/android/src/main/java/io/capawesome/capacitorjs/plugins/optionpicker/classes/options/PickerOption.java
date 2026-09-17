package io.capawesome.capacitorjs.plugins.optionpicker.classes.options;

import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.optionpicker.classes.CustomExceptions;
import org.json.JSONObject;

public class PickerOption {

    @NonNull
    private final String label;

    @NonNull
    private final String value;

    public PickerOption(@NonNull JSONObject object) throws Exception {
        this.label = PickerOption.getLabelFromObject(object);
        this.value = PickerOption.getValueFromObject(object);
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    @NonNull
    private static String getLabelFromObject(@NonNull JSONObject object) throws Exception {
        String label = object.isNull("label") ? null : object.optString("label", null);
        if (label == null) {
            throw CustomExceptions.OPTION_LABEL_MISSING;
        }
        return label;
    }

    @NonNull
    private static String getValueFromObject(@NonNull JSONObject object) throws Exception {
        String value = object.isNull("value") ? null : object.optString("value", null);
        if (value == null) {
            throw CustomExceptions.OPTION_VALUE_MISSING;
        }
        return value;
    }
}
