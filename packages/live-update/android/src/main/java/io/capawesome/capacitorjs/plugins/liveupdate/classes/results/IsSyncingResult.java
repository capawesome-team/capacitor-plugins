package io.capawesome.capacitorjs.plugins.liveupdate.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

public class IsSyncingResult implements Result {

    private final boolean syncing;

    public IsSyncingResult(boolean syncing) {
        this.syncing = syncing;
    }

    public boolean getSyncing() {
        return syncing;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("syncing", syncing);
        return result;
    }
}
