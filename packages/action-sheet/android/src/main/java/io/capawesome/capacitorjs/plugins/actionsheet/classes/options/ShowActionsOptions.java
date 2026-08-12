package io.capawesome.capacitorjs.plugins.actionsheet.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.actionsheet.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ShowActionsOptions {

    @NonNull
    private final List<ActionSheetButton> buttons;

    private final boolean cancelable;

    @Nullable
    private final String message;

    @Nullable
    private final String title;

    public ShowActionsOptions(@NonNull PluginCall call) throws Exception {
        JSArray optionsArray = call.getArray("options");
        if (optionsArray == null) {
            throw CustomExceptions.OPTIONS_MISSING;
        }
        List<ActionSheetButton> buttons = new ArrayList<>();
        for (int i = 0; i < optionsArray.length(); i++) {
            JSONObject object = optionsArray.getJSONObject(i);
            buttons.add(new ActionSheetButton(object));
        }
        if (buttons.isEmpty()) {
            throw CustomExceptions.OPTIONS_EMPTY;
        }
        this.buttons = buttons;
        this.cancelable = call.getBoolean("cancelable", true);
        this.message = call.getString("message");
        this.title = call.getString("title");
    }

    @NonNull
    public List<ActionSheetButton> getButtons() {
        return buttons;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public boolean isCancelable() {
        return cancelable;
    }
}
