package io.capawesome.capacitorjs.plugins.liveupdate.interfaces;

import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.DownloadBundleProgressEvent;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.NextBundleSetEvent;

public interface LiveUpdateEventEmitter {
    void onDownloadBundleProgress(@NonNull DownloadBundleProgressEvent event);

    void onNextBundleSet(@NonNull NextBundleSetEvent event);

    void onReloaded();
}
