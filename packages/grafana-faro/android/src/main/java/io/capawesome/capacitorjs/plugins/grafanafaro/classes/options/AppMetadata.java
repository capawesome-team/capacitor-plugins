package io.capawesome.capacitorjs.plugins.grafanafaro.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.grafanafaro.GrafanaFaroPlugin;

public class AppMetadata {

    @Nullable
    private final String environment;

    @NonNull
    private final String name;

    @Nullable
    private final String namespace;

    @Nullable
    private final String version;

    public AppMetadata(@NonNull JSObject source) throws Exception {
        String name = source.getString("name");
        if (name == null || name.isEmpty()) {
            throw new Exception(GrafanaFaroPlugin.ERROR_APP_NAME_MISSING);
        }
        this.environment = source.getString("environment");
        this.name = name;
        this.namespace = source.getString("namespace");
        this.version = source.getString("version");
    }

    @Nullable
    public String getEnvironment() {
        return environment;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getNamespace() {
        return namespace;
    }

    @Nullable
    public String getVersion() {
        return version;
    }
}
