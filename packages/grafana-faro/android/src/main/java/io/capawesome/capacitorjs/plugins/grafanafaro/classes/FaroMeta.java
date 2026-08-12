package io.capawesome.capacitorjs.plugins.grafanafaro.classes;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.AppMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.UserMetadata;
import io.capawesome.capacitorjs.plugins.grafanafaro.classes.options.ViewMetadata;
import org.json.JSONObject;

public class FaroMeta {

    @NonNull
    private final AppMetadata app;

    @NonNull
    private final Context context;

    @Nullable
    private FaroSession session;

    @Nullable
    private UserMetadata user;

    @Nullable
    private ViewMetadata view;

    public FaroMeta(@NonNull Context context, @NonNull AppMetadata app) {
        this.app = app;
        this.context = context.getApplicationContext();
    }

    @Nullable
    public FaroSession getSession() {
        return session;
    }

    @Nullable
    public UserMetadata getUser() {
        return user;
    }

    @Nullable
    public ViewMetadata getView() {
        return view;
    }

    public void setSession(@Nullable FaroSession session) {
        this.session = session;
    }

    public void setUser(@Nullable UserMetadata user) {
        this.user = user;
    }

    public void setView(@Nullable ViewMetadata view) {
        this.view = view;
    }

    public JSONObject toJson() {
        JSONObject root = new JSONObject();
        JsonUtils.putIfNotNull(root, "sdk", buildSdkMeta());
        JsonUtils.putIfNotNull(root, "app", buildAppMeta());
        JsonUtils.putIfNotNull(root, "session", buildSessionMeta());
        JsonUtils.putIfNotNull(root, "user", buildUserMeta());
        JsonUtils.putIfNotNull(root, "view", buildViewMeta());
        JsonUtils.putIfNotNull(root, "os", buildOsMeta());
        JsonUtils.putIfNotNull(root, "device", buildDeviceMeta());
        return root;
    }

    private JSONObject buildSdkMeta() {
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "name", Constants.SDK_NAME);
        return json;
    }

    private JSONObject buildAppMeta() {
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "name", app.getName());
        JsonUtils.putIfNotNull(json, "version", app.getVersion());
        JsonUtils.putIfNotNull(json, "environment", app.getEnvironment());
        JsonUtils.putIfNotNull(json, "namespace", app.getNamespace());
        return json;
    }

    @Nullable
    private JSONObject buildSessionMeta() {
        if (session == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "id", session.getId());
        JsonUtils.putIfNotNull(json, "sampled", session.isSampled());
        if (!session.getAttributes().isEmpty()) {
            JsonUtils.putIfNotNull(json, "attributes", JsonUtils.mapToJson(session.getAttributes()));
        }
        return json;
    }

    @Nullable
    private JSONObject buildUserMeta() {
        if (user == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "id", user.getId());
        JsonUtils.putIfNotNull(json, "email", user.getEmail());
        JsonUtils.putIfNotNull(json, "username", user.getUsername());
        JsonUtils.putIfNotNull(json, "fullName", user.getFullName());
        if (user.getAttributes() != null && !user.getAttributes().isEmpty()) {
            JsonUtils.putIfNotNull(json, "attributes", JsonUtils.mapToJson(user.getAttributes()));
        }
        return json;
    }

    @Nullable
    private JSONObject buildViewMeta() {
        if (view == null) {
            return null;
        }
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "name", view.getName());
        return json;
    }

    private JSONObject buildOsMeta() {
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "name", "Android");
        JsonUtils.putIfNotNull(json, "version", Build.VERSION.RELEASE);
        JsonUtils.putIfNotNull(json, "build_id", Build.DISPLAY);
        JsonUtils.putIfNotNull(json, "detail", "API " + Build.VERSION.SDK_INT);
        return json;
    }

    private JSONObject buildDeviceMeta() {
        JSONObject json = new JSONObject();
        JsonUtils.putIfNotNull(json, "manufacturer", Build.MANUFACTURER);
        JsonUtils.putIfNotNull(json, "model_identifier", Build.MODEL);
        JsonUtils.putIfNotNull(json, "model_name", Build.MODEL);
        JsonUtils.putIfNotNull(json, "brand", Build.BRAND);
        JsonUtils.putIfNotNull(json, "is_physical", !Build.FINGERPRINT.contains("generic"));
        JsonUtils.putIfNotNull(json, "type", "mobile");
        return json;
    }
}
