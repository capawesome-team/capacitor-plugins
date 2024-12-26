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

    private final WebView webView;

    public Screenshot(WebView webView) {
        this.webView = webView;
    }

    public void take(Context context, @NonNull NonEmptyCallback<Result> callback) {
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);
        webView.draw(new android.graphics.Canvas(bitmap));
        File screenshot = ScreenshotHelper.createFileOnCache(context);
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
