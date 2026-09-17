package io.capawesome.capacitorjs.plugins.intercom.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.intercom.classes.CustomExceptions;
import java.util.ArrayList;
import java.util.List;

public class PresentContentOptions {

    @Nullable
    private final String id;

    @Nullable
    private final List<String> ids;

    @NonNull
    private final String type;

    public PresentContentOptions(@NonNull PluginCall call) throws Exception {
        String type = call.getString("type");
        if (type == null) {
            throw CustomExceptions.TYPE_INVALID;
        }
        this.type = type;
        this.id = call.getString("id");
        JSArray ids = call.getArray("ids");
        if (ids == null) {
            this.ids = null;
        } else {
            this.ids = new ArrayList<>();
            for (int i = 0; i < ids.length(); i++) {
                this.ids.add(ids.getString(i));
            }
        }
        switch (type) {
            case "article":
            case "carousel":
            case "survey":
            case "conversation":
                if (id == null) {
                    throw CustomExceptions.ID_MISSING;
                }
                break;
            case "help-center-collections":
                if (this.ids == null || this.ids.isEmpty()) {
                    throw CustomExceptions.IDS_MISSING;
                }
                break;
            default:
                throw CustomExceptions.TYPE_INVALID;
        }
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public List<String> getIds() {
        return ids;
    }

    @NonNull
    public String getType() {
        return type;
    }
}
