package io.capawesome.capacitorjs.plugins.screenshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.Result;
import io.capawesome.capacitorjs.plugins.screenshot.classes.results.TakeResult;
import java.io.File;
import java.io.FileOutputStream;

public class Screenshot {

    private final ScreenshotPlugin plugin;

    public Screenshot(@NonNull ScreenshotPlugin plugin) {
        this.plugin = plugin;
    }

    public void take(@NonNull NonEmptyCallback<Result> callback) {
        WebView webView = this.plugin.getBridge().getWebView();
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);
        webView.draw(new android.graphics.Canvas(bitmap));
        File screenshot = ScreenshotHelper.createFileOnCache(this.plugin.getContext());
        try (FileOutputStream outputStream = new FileOutputStream(screenshot)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            TakeResult result = new TakeResult(screenshot);
            callback.success(result);
        } catch (Exception error) {
            callback.error(error);
        }
    }
}
