package io.capawesome.capacitorjs.plugins.actionsheet.classes.options;

import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.CustomExceptions;
import org.json.JSONObject;

public class ActionSheetButton {

    public static final String STYLE_CANCEL = "CANCEL";
    public static final String STYLE_DEFAULT = "DEFAULT";
    public static final String STYLE_DESTRUCTIVE = "DESTRUCTIVE";

    @NonNull
    private final String style;

    @NonNull
    private final String title;

    public ActionSheetButton(@NonNull JSONObject object) throws Exception {
        String title = object.isNull("title") ? null : object.optString("title", null);
        if (title == null) {
            throw CustomExceptions.BUTTON_TITLE_MISSING;
        }
        this.title = title;
        this.style = object.optString("style", STYLE_DEFAULT);
    }

    @NonNull
    public String getStyle() {
        return style;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public boolean isCancel() {
        return style.equals(STYLE_CANCEL);
    }

    public boolean isDestructive() {
        return style.equals(STYLE_DESTRUCTIVE);
    }
}
