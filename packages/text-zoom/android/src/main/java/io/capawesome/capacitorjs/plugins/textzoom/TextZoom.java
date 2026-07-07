package io.capawesome.capacitorjs.plugins.textzoom;

import android.app.Activity;
import android.content.res.Configuration;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.textzoom.classes.options.SetZoomOptions;
import io.capawesome.capacitorjs.plugins.textzoom.classes.results.GetPreferredZoomResult;
import io.capawesome.capacitorjs.plugins.textzoom.classes.results.GetZoomResult;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.textzoom.interfaces.NonEmptyResultCallback;

public class TextZoom {

    @NonNull
    private final TextZoomPlugin plugin;

    public TextZoom(@NonNull TextZoomPlugin plugin) {
        this.plugin = plugin;
    }

    public void getPreferredZoom(@NonNull NonEmptyResultCallback<GetPreferredZoomResult> callback) {
        Configuration configuration = plugin.getContext().getResources().getConfiguration();
        double zoom = configuration.fontScale;
        callback.success(new GetPreferredZoomResult(zoom));
    }

    public void getZoom(@NonNull NonEmptyResultCallback<GetZoomResult> callback) {
        runOnUiThread(() -> {
            int textZoom = getWebView().getSettings().getTextZoom();
            double zoom = textZoom / 100.0;
            callback.success(new GetZoomResult(zoom));
        });
    }

    public void setZoom(@NonNull SetZoomOptions options, @NonNull EmptyCallback callback) {
        double zoom = options.getZoom();
        runOnUiThread(() -> {
            int textZoom = (int) Math.round(zoom * 100);
            getWebView().getSettings().setTextZoom(textZoom);
            callback.success();
        });
    }

    @NonNull
    private WebView getWebView() {
        return plugin.getBridge().getWebView();
    }

    private void runOnUiThread(@NonNull Runnable runnable) {
        Activity activity = plugin.getActivity();
        activity.runOnUiThread(runnable);
    }
}
