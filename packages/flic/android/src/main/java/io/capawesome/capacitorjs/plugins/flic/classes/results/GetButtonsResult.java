package io.capawesome.capacitorjs.plugins.flic.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.FlicHelper;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;
import io.flic.flic2libandroid.Flic2Button;
import java.util.List;

public class GetButtonsResult implements Result {

    @NonNull
    private final List<Flic2Button> buttons;

    public GetButtonsResult(@NonNull List<Flic2Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSArray buttonsResult = new JSArray();
        for (Flic2Button button : buttons) {
            buttonsResult.put(FlicHelper.createButtonJSObject(button));
        }

        JSObject result = new JSObject();
        result.put("buttons", buttonsResult);
        return result;
    }
}
