package io.capawesome.capacitorjs.plugins.flic.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.flic.FlicHelper;
import io.capawesome.capacitorjs.plugins.flic.interfaces.Result;
import io.flic.flic2libandroid.Flic2Button;

public class StartScanResult implements Result {

    @NonNull
    private final Flic2Button button;

    public StartScanResult(@NonNull Flic2Button button) {
        this.button = button;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("button", FlicHelper.createButtonJSObject(button));
        return result;
    }
}
