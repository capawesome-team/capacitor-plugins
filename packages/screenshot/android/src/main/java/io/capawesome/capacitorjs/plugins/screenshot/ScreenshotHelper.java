package io.capawesome.capacitorjs.plugins.screenshot;

import android.content.Context;
import java.io.File;
import java.util.UUID;

public class ScreenshotHelper {

    static File createFileOnCache(Context context) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        return new File(context.getCacheDir(), fileName);
    }
}
