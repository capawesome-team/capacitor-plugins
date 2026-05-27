package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.JsonUtils;
import java.util.Map;

public class UserMetadata {

    @Nullable
    private final Map<String, String> attributes;

    @Nullable
    private final String email;

    @Nullable
    private final String fullName;

    @Nullable
    private final String id;

    @Nullable
    private final String username;

    public UserMetadata(@NonNull PluginCall call) {
        this.attributes = JsonUtils.toStringMap(call.getObject("attributes"));
        this.email = call.getString("email");
        this.fullName = call.getString("fullName");
        this.id = call.getString("id");
        this.username = call.getString("username");
    }

    public UserMetadata(@NonNull JSObject source) {
        this.attributes = JsonUtils.toStringMap(source.getJSObject("attributes"));
        this.email = source.getString("email");
        this.fullName = source.getString("fullName");
        this.id = source.getString("id");
        this.username = source.getString("username");
    }

    @Nullable
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getFullName() {
        return fullName;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getUsername() {
        return username;
    }
}
