package io.capawesome.capacitorjs.plugins.libsql.classes.options;

import androidx.annotation.Nullable;
import com.getcapacitor.PluginCall;

public class ConnectOptions {

    @Nullable
    private final String authToken;
    @Nullable
    private final String path;
    @Nullable
    private final String url;

    public ConnectOptions(PluginCall call) throws Exception {
        this.authToken = call.getString("authToken");
        this.path = call.getString("path");
        this.url = call.getString("url");
    }

    @Nullable
    public String getAuthToken() {
        return authToken;
    }

    @Nullable
    public String getPath() {
        return path;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}