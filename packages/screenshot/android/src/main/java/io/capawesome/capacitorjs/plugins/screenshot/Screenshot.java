package io.capawesome.capacitorjs.plugins.screenshot;

import android.graphics.Bitmap;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.screenshot.classes.interfaces.Result;
import io.capawesome.capacitorjs.plugins.screenshot.classes.results.TakeResult;
import java.io.ByteArrayOutputStream;

public class Screenshot {

    private final WebView webView;

    public Screenshot(WebView webView) {
        this.webView = webView;
    }

    public void take(@NonNull NonEmptyCallback<Result> callback) {
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webView.getHeight(), Bitmap.Config.ARGB_8888);
        webView.draw(new android.graphics.Canvas(bitmap));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        TakeResult result = new TakeResult(byteArray);
        callback.success(result);
    }
}
