package io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.CustomExceptions;
import java.util.List;
import org.json.JSONException;

public class IntentOptions {

    @NonNull
    private final String action;

    @Nullable
    private final List<String> categories;

    @Nullable
    private final String className;

    @Nullable
    private final String dataUri;

    @Nullable
    private final JSObject extras;

    @Nullable
    private final Integer flags;

    @Nullable
    private final String packageName;

    @Nullable
    private final String type;

    public IntentOptions(@NonNull PluginCall call) throws Exception {
        this.action = getActionFromCall(call);
        this.categories = getCategoriesFromCall(call);
        this.className = call.getString("className");
        this.dataUri = call.getString("dataUri");
        this.extras = call.getObject("extras");
        this.flags = call.getInt("flags");
        this.packageName = call.getString("packageName");
        this.type = call.getString("type");
    }

    @NonNull
    public String getAction() {
        return action;
    }

    @Nullable
    public List<String> getCategories() {
        return categories;
    }

    @Nullable
    public String getClassName() {
        return className;
    }

    @Nullable
    public String getDataUri() {
        return dataUri;
    }

    @Nullable
    public JSObject getExtras() {
        return extras;
    }

    @Nullable
    public Integer getFlags() {
        return flags;
    }

    @Nullable
    public String getPackageName() {
        return packageName;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @NonNull
    private static String getActionFromCall(@NonNull PluginCall call) throws Exception {
        String action = call.getString("action");
        if (action == null) {
            throw CustomExceptions.ACTION_MISSING;
        }
        return action;
    }

    @Nullable
    private static List<String> getCategoriesFromCall(@NonNull PluginCall call) {
        JSArray categories = call.getArray("categories");
        if (categories == null) {
            return null;
        }
        try {
            return categories.toList();
        } catch (JSONException exception) {
            return null;
        }
    }
}
