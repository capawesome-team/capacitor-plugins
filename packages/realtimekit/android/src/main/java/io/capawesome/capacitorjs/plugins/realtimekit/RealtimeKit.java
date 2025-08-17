package io.capawesome.capacitorjs.plugins.realtimekit;

import androidx.annotation.NonNull;
import com.cloudflare.realtimekit.models.RtkMeetingInfo;
import com.cloudflare.realtimekit.ui.RealtimeKitUI;
import com.cloudflare.realtimekit.ui.RealtimeKitUIBuilder;
import com.cloudflare.realtimekit.ui.RealtimeKitUIInfo;
import io.capawesome.capacitorjs.plugins.realtimekit.classes.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.realtimekit.classes.options.StartMeetingOptions;

public class RealtimeKit {

    private final RealtimeKitPlugin plugin;

    public RealtimeKit(@NonNull RealtimeKitPlugin plugin) {
        this.plugin = plugin;
    }

    public void startMeeting(@NonNull StartMeetingOptions options, @NonNull EmptyCallback callback) {
        RtkMeetingInfo meetingInfo = new RtkMeetingInfo(options.getAuthToken(), options.getEnableAudio(), options.getEnableVideo());
        RealtimeKitUIInfo realtimeKitUIInfo = new RealtimeKitUIInfo(plugin.getActivity(), meetingInfo);
        RealtimeKitUI realtimeKitUI = RealtimeKitUIBuilder.build(realtimeKitUIInfo);
        realtimeKitUI.startMeeting();
        callback.success();
    }
}
