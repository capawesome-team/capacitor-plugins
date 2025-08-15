package io.capawesome.capacitorjs.plugins.realtimekit.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import io.capawesome.capacitorjs.plugins.realtimekit.RealtimeKitPlugin;
import java.util.Objects;

public class StartMeetingOptions {

    @NonNull
    private final String authToken;

    private final boolean enableAudio;
    private final boolean enableVideo;

    public StartMeetingOptions(@NonNull PluginCall call) throws Exception {
        this.authToken = StartMeetingOptions.getAuthTokenFromCall(call);
        this.enableAudio = StartMeetingOptions.getEnableAudioFromCall(call);
        this.enableVideo = StartMeetingOptions.getEnableVideoFromCall(call);
    }

    @NonNull
    public String getAuthToken() {
        return authToken;
    }

    public boolean getEnableAudio() {
        return enableAudio;
    }

    public boolean getEnableVideo() {
        return enableVideo;
    }

    private static String getAuthTokenFromCall(@NonNull PluginCall call) throws Exception {
        String authToken = call.getString("authToken");
        if (authToken == null) {
            throw new Exception(RealtimeKitPlugin.ERROR_AUTH_TOKEN_MISSING);
        }
        return authToken;
    }

    private static boolean getEnableAudioFromCall(@NonNull PluginCall call) {
        return Objects.requireNonNull(call.getBoolean("enableAudio", true));
    }

    private static boolean getEnableVideoFromCall(@NonNull PluginCall call) {
        return Objects.requireNonNull(call.getBoolean("enableVideo", true));
    }
}
