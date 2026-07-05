package io.capawesome.capacitorjs.plugins.nodejs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.system.Os;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.nodejs.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.nodejs.classes.events.MessageEvent;
import io.capawesome.capacitorjs.plugins.nodejs.classes.options.SendOptions;
import io.capawesome.capacitorjs.plugins.nodejs.classes.options.StartOptions;
import io.capawesome.capacitorjs.plugins.nodejs.interfaces.EmptyCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class Nodejs {

    private static final String ASSETS_BUILTIN_MODULES_DIR = "builtin_modules";
    private static final String BASE_DIR = "capawesome_nodejs";
    private static final String CHANNEL_EVENTS = "_EVENTS_";
    private static final String CHANNEL_SYSTEM = "_SYSTEM_";
    private static final String SHARED_PREFS = "CapawesomeNodejsPlugin";
    private static final String SHARED_PREFS_APK_LAST_UPDATE_TIME = "apkLastUpdateTime";
    private static final String SYSTEM_MESSAGE_PAUSE = "pause";
    private static final String SYSTEM_MESSAGE_READY = "ready-for-app-events";
    private static final String SYSTEM_MESSAGE_RESUME = "resume";

    @Nullable
    private static Nodejs instance;

    static {
        System.loadLibrary("node");
        System.loadLibrary("capacitor-nodejs");
    }

    @NonNull
    private final NodejsConfig config;

    @NonNull
    private final Context context;

    @NonNull
    private final NodejsPlugin plugin;

    private volatile boolean ready = false;

    private volatile boolean started = false;

    public Nodejs(@NonNull NodejsPlugin plugin, @NonNull Context context, @NonNull NodejsConfig config) {
        this.config = config;
        this.context = context;
        this.plugin = plugin;
        Nodejs.instance = this;
    }

    public boolean isReady() {
        return ready;
    }

    public void send(@NonNull SendOptions options) throws Exception {
        if (!ready) {
            throw CustomExceptions.NODE_NOT_READY;
        }
        JSONObject envelope = new JSONObject();
        envelope.put("event", options.getEventName());
        envelope.put("payload", options.getArgs().toString());
        sendMessageToNodeChannel(CHANNEL_EVENTS, envelope.toString());
    }

    public synchronized void start(@NonNull StartOptions options, @NonNull EmptyCallback callback) {
        if (started) {
            callback.error(CustomExceptions.NODE_ALREADY_RUNNING);
            return;
        }
        started = true;
        Thread thread = new Thread(() -> {
            try {
                File projectDir = new File(getBaseDir(), "nodejs-project");
                File builtinModulesDir = new File(getBaseDir(), ASSETS_BUILTIN_MODULES_DIR);
                copyNodeProjectFromApk(projectDir, builtinModulesDir);
                File scriptFile = resolveScriptFile(projectDir, options.getScript());
                for (Map.Entry<String, String> entry : options.getEnv().entrySet()) {
                    Os.setenv(entry.getKey(), entry.getValue(), true);
                }
                Os.setenv("TMPDIR", context.getCacheDir().getAbsolutePath(), true);
                registerNodeDataDirPath(context.getFilesDir().getAbsolutePath());
                List<String> args = new ArrayList<>();
                args.add("node");
                args.add(scriptFile.getAbsolutePath());
                args.addAll(options.getArgs());
                String nodePath = projectDir.getAbsolutePath() + ":" + builtinModulesDir.getAbsolutePath();
                callback.success();
                startNodeWithArguments(args.toArray(new String[0]), nodePath, true);
            } catch (Exception exception) {
                started = false;
                callback.error(exception);
            }
        });
        thread.start();
    }

    public void handleOnPause() {
        if (ready) {
            sendMessageToNodeChannel(CHANNEL_SYSTEM, SYSTEM_MESSAGE_PAUSE);
        }
    }

    public void handleOnResume() {
        if (ready) {
            sendMessageToNodeChannel(CHANNEL_SYSTEM, SYSTEM_MESSAGE_RESUME);
        }
    }

    @SuppressWarnings("unused")
    private static void onMessageReceived(@NonNull String channelName, @NonNull String message) {
        Nodejs instance = Nodejs.instance;
        if (instance == null) {
            return;
        }
        try {
            instance.handleMessage(channelName, message);
        } catch (Exception exception) {
            Logger.error(NodejsPlugin.TAG, "Failed to handle message from the Node.js runtime.", exception);
        }
    }

    private void handleMessage(@NonNull String channelName, @NonNull String message) throws Exception {
        if (CHANNEL_SYSTEM.equals(channelName)) {
            if (SYSTEM_MESSAGE_READY.equals(message)) {
                ready = true;
                plugin.notifyReadyListeners();
            }
        } else if (CHANNEL_EVENTS.equals(channelName)) {
            JSONObject envelope = new JSONObject(message);
            String eventName = envelope.getString("event");
            JSArray args = new JSArray(envelope.optString("payload", "[]"));
            plugin.notifyMessageListeners(new MessageEvent(eventName, args));
        }
    }

    private void copyNodeProjectFromApk(@NonNull File projectDir, @NonNull File builtinModulesDir) throws Exception {
        AssetManager assetManager = context.getAssets();
        String projectAssetsPath = "public/" + config.getNodeDir();
        String[] projectAssets = assetManager.list(projectAssetsPath);
        if (projectAssets == null || projectAssets.length == 0) {
            throw CustomExceptions.PROJECT_NOT_FOUND;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        long previousLastUpdateTime = sharedPreferences.getLong(SHARED_PREFS_APK_LAST_UPDATE_TIME, 0);
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        long lastUpdateTime = packageInfo.lastUpdateTime;
        if (lastUpdateTime != previousLastUpdateTime || !projectDir.exists()) {
            deleteRecursively(projectDir);
            deleteRecursively(builtinModulesDir);
            copyAssetDirectory(assetManager, projectAssetsPath, projectDir);
            copyAssetDirectory(assetManager, ASSETS_BUILTIN_MODULES_DIR, builtinModulesDir);
            sharedPreferences.edit().putLong(SHARED_PREFS_APK_LAST_UPDATE_TIME, lastUpdateTime).apply();
        }
    }

    @NonNull
    private File resolveScriptFile(@NonNull File projectDir, @Nullable String script) throws Exception {
        String scriptPath = script;
        if (scriptPath == null) {
            scriptPath = getMainFromPackageJson(projectDir);
        }
        if (scriptPath == null) {
            scriptPath = "index.js";
        }
        File scriptFile = new File(projectDir, scriptPath);
        if (!scriptFile.exists()) {
            throw CustomExceptions.SCRIPT_NOT_FOUND;
        }
        return scriptFile;
    }

    @Nullable
    private String getMainFromPackageJson(@NonNull File projectDir) {
        try {
            File packageJsonFile = new File(projectDir, "package.json");
            if (!packageJsonFile.exists()) {
                return null;
            }
            byte[] bytes = new byte[(int) packageJsonFile.length()];
            try (InputStream inputStream = new java.io.FileInputStream(packageJsonFile)) {
                int read = inputStream.read(bytes);
                if (read != bytes.length) {
                    return null;
                }
            }
            JSONObject packageJson = new JSONObject(new String(bytes, StandardCharsets.UTF_8));
            return packageJson.optString("main", null);
        } catch (Exception exception) {
            return null;
        }
    }

    @NonNull
    private File getBaseDir() {
        return new File(context.getFilesDir(), BASE_DIR);
    }

    private void copyAssetDirectory(@NonNull AssetManager assetManager, @NonNull String assetPath, @NonNull File targetDir)
        throws Exception {
        String[] children = assetManager.list(assetPath);
        if (children == null || children.length == 0) {
            copyAssetFile(assetManager, assetPath, targetDir);
        } else {
            if (!targetDir.exists() && !targetDir.mkdirs()) {
                throw new Exception("Failed to create directory: " + targetDir.getAbsolutePath());
            }
            for (String child : children) {
                copyAssetDirectory(assetManager, assetPath + "/" + child, new File(targetDir, child));
            }
        }
    }

    private void copyAssetFile(@NonNull AssetManager assetManager, @NonNull String assetPath, @NonNull File targetFile) throws Exception {
        try (InputStream inputStream = assetManager.open(assetPath); OutputStream outputStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

    private void deleteRecursively(@NonNull File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }

    private static native void registerNodeDataDirPath(String dataDir);

    private static native void sendMessageToNodeChannel(String channelName, String msg);

    private static native int startNodeWithArguments(String[] arguments, String nodePath, boolean redirectOutputToLogcat);
}
