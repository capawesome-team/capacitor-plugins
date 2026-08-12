package io.capawesome.capacitorjs.plugins.androidintentlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.options.IntentOptions;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.results.CanResolveActivityResult;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.classes.results.StartActivityResult;
import io.capawesome.capacitorjs.plugins.androidintentlauncher.interfaces.NonEmptyResultCallback;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class AndroidIntentLauncher {

    @NonNull
    private final AndroidIntentLauncherPlugin plugin;

    public AndroidIntentLauncher(@NonNull AndroidIntentLauncherPlugin plugin) {
        this.plugin = plugin;
    }

    public void canResolveActivity(@NonNull IntentOptions options, @NonNull NonEmptyResultCallback<CanResolveActivityResult> callback) {
        Intent intent = createIntent(options);
        PackageManager packageManager = getContext().getPackageManager();
        boolean canResolve = intent.resolveActivity(packageManager) != null;
        callback.success(new CanResolveActivityResult(canResolve));
    }

    @NonNull
    public Intent createIntent(@NonNull IntentOptions options) {
        Intent intent = new Intent();
        intent.setAction(options.getAction());
        String dataUri = options.getDataUri();
        String type = options.getType();
        if (dataUri != null && type != null) {
            intent.setDataAndType(Uri.parse(dataUri), type);
        } else if (dataUri != null) {
            intent.setData(Uri.parse(dataUri));
        } else if (type != null) {
            intent.setType(type);
        }
        List<String> categories = options.getCategories();
        if (categories != null) {
            for (String category : categories) {
                intent.addCategory(category);
            }
        }
        String packageName = options.getPackageName();
        String className = options.getClassName();
        if (packageName != null && className != null) {
            intent.setClassName(packageName, className);
        } else if (packageName != null) {
            intent.setPackage(packageName);
        }
        JSObject extras = options.getExtras();
        if (extras != null) {
            applyExtras(intent, extras);
        }
        Integer flags = options.getFlags();
        if (flags != null) {
            intent.setFlags(flags);
        }
        return intent;
    }

    @NonNull
    public StartActivityResult createStartActivityResult(@NonNull ActivityResult result) {
        Intent data = result.getData();
        String dataUri = data != null && data.getData() != null ? data.getData().toString() : null;
        return new StartActivityResult(result.getResultCode(), dataUri);
    }

    private void applyExtras(@NonNull Intent intent, @NonNull JSObject extras) {
        Iterator<String> keys = extras.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value;
            try {
                value = extras.get(key);
            } catch (JSONException exception) {
                continue;
            }
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            } else if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                intent.putExtra(key, (Long) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (Double) value);
            }
        }
    }

    @NonNull
    private Context getContext() {
        return plugin.getContext();
    }
}
