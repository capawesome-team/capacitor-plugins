package io.capawesome.capacitorjs.plugins.liveupdate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.plugin.WebView;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.GetLatestBundleResponse;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DeleteBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetChannelOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetCustomIdOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetChannelResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCustomIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDeviceIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionCodeResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionNameResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.SyncResult;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import net.lingala.zip4j.ZipFile;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    @NonNull
    private final LiveUpdateConfig config;

    private final String defaultWebAssetDir;

    @NonNull
    private final LiveUpdatePlugin plugin;

    @NonNull
    private final LiveUpdatePreferences preferences;

    @NonNull
    private final SharedPreferences.Editor webViewSettingsEditor;

    private final String bundlesDirectory = "_capacitor_live_update_bundles";
    private final Handler rollbackHandler = new Handler();

    public LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull LiveUpdatePlugin plugin) throws PackageManager.NameNotFoundException {
        this.config = config;
        this.defaultWebAssetDir = plugin.getBridge().DEFAULT_WEB_ASSET_DIR;
        this.plugin = plugin;
        this.preferences = new LiveUpdatePreferences(plugin.getContext());
        this.webViewSettingsEditor = plugin.getContext().getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE).edit();

        if (config.getEnabled()) {
            if (wasUpdated() && config.getResetOnUpdate()) {
                reset();
            } else {
                startRollbackTimer();
            }
            saveCurrentVersionCode();
        }
    }

    public void deleteBundle(@NonNull DeleteBundleOptions options, @NonNull EmptyCallback callback) {
        String bundleId = options.getBundleId();

        if (!hasBundle(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_NOT_FOUND);
            callback.error(exception);
            return;
        }
        deleteBundle(bundleId);

        callback.success();
    }

    public void downloadBundle(@NonNull DownloadBundleOptions options, @NonNull EmptyCallback callback) throws Exception {
        String bundleId = options.getBundleId();
        String url = options.getUrl();

        // Check if the bundle already exists
        if (hasBundle(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_EXISTS);
            callback.error(exception);
            return;
        }

        // Download the bundle
        downloadBundle(bundleId, null, url, callback);
    }

    public void getBundle(@NonNull NonEmptyCallback callback) {
        String bundleId = getCurrentBundleId();
        if (bundleId.equals(defaultWebAssetDir)) {
            bundleId = null;
        }
        GetBundleResult result = new GetBundleResult(bundleId);
        callback.success(result);
    }

    public void getBundles(@NonNull NonEmptyCallback callback) {
        String[] bundleIds = getBundleIds();
        GetBundlesResult result = new GetBundlesResult(bundleIds);
        callback.success(result);
    }

    public void getChannel(@NonNull NonEmptyCallback callback) {
        String channel = preferences.getChannel();
        GetChannelResult result = new GetChannelResult(channel);
        callback.success(result);
    }

    public void getCustomId(@NonNull NonEmptyCallback callback) {
        String customId = preferences.getCustomId();
        GetCustomIdResult result = new GetCustomIdResult(customId);
        callback.success(result);
    }

    public void getDeviceId(@NonNull NonEmptyCallback callback) {
        String deviceId = getDeviceId();
        GetDeviceIdResult result = new GetDeviceIdResult(deviceId);
        callback.success(result);
    }

    public void getVersionCode(@NonNull NonEmptyCallback callback) throws PackageManager.NameNotFoundException {
        String versionCode = getVersionCode();
        GetVersionCodeResult result = new GetVersionCodeResult(versionCode);
        callback.success(result);
    }

    public void getVersionName(@NonNull NonEmptyCallback callback) throws PackageManager.NameNotFoundException {
        String versionName = getVersionName();
        GetVersionNameResult result = new GetVersionNameResult(versionName);
        callback.success(result);
    }

    public void ready() {
        Logger.debug(LiveUpdatePlugin.TAG, "App is ready.");
        stopRollbackTimer();
        if (config.getAutoDeleteBundles()) {
            deleteUnusedBundles();
        }
    }

    public void reload() {
        String path = getNextCapacitorServerPath();
        setCurrentCapacitorServerPath(path);
        startRollbackTimer();
    }

    public void reset() {
        setNextCapacitorServerPathToDefaultWebAssetDir();
    }

    public void setBundle(@NonNull SetBundleOptions options, @NonNull EmptyCallback callback) {
        String bundleId = options.getBundleId();

        if (!hasBundle(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_NOT_FOUND);
            callback.error(exception);
            return;
        }

        setNextBundle(bundleId);
        callback.success();
    }

    public void setChannel(@NonNull SetChannelOptions options, @NonNull EmptyCallback callback) {
        String channel = options.getChannel();

        preferences.setChannel(channel);
        callback.success();
    }

    public void setCustomId(@NonNull SetCustomIdOptions options, @NonNull EmptyCallback callback) {
        String customId = options.getCustomId();

        preferences.setCustomId(customId);
        callback.success();
    }

    public void sync(@NonNull NonEmptyCallback<Result> callback) throws Exception {
        // Fetch the latest bundle
        fetchLatestBundle(
            new NonEmptyCallback<GetLatestBundleResponse>() {
                @Override
                public void error(Exception exception) {
                    // No update available
                    Logger.debug(LiveUpdatePlugin.TAG, "No update available.");
                    SyncResult syncResult = new SyncResult(null);
                    callback.success(syncResult);
                }

                @Override
                public void success(@NonNull GetLatestBundleResponse result) {
                    String latestBundleId = result.getBundleId();
                    String checksum = result.getChecksum();
                    String url = result.getUrl();

                    // Check if the bundle already exists
                    if (hasBundle(latestBundleId)) {
                        String nextBundleId = null;
                        String currentBundleId = getCurrentBundleId();
                        if (!latestBundleId.equals(currentBundleId)) {
                            // Set the next bundle
                            setNextBundle(latestBundleId);
                            nextBundleId = latestBundleId;
                        }
                        SyncResult syncResult = new SyncResult(nextBundleId);
                        callback.success(syncResult);
                        return;
                    }
                    // Download and unzip the bundle
                    downloadBundle(
                        latestBundleId,
                        checksum,
                        url,
                        new EmptyCallback() {
                            @Override
                            public void success() {
                                // Set the next bundle
                                setNextBundle(latestBundleId);
                                SyncResult syncResult = new SyncResult(latestBundleId);
                                callback.success(syncResult);
                            }

                            @Override
                            public void error(Exception exception) {
                                callback.error(exception);
                            }
                        }
                    );
                }
            }
        );
    }

    private void addBundle(@NonNull String bundleId, @NonNull File zipFile) throws Exception {
        // Unzip the file to the bundle directory
        File unzippedDirectory = unzipFile(zipFile);

        // Search folder with index.html file
        File indexHtmlFile = searchIndexHtmlFile(unzippedDirectory);
        if (indexHtmlFile == null) {
            throw new Exception(LiveUpdatePlugin.ERROR_BUNDLE_INDEX_HTML_MISSING);
        }

        // Create the bundles directory if it does not exist
        createBundlesDirectory();

        // Move the bundle directory to the bundles directory
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        indexHtmlFile.getParentFile().renameTo(bundleDirectory);
    }

    private File buildTemporaryDirectory() {
        String fileName = UUID.randomUUID().toString();
        return new File(plugin.getContext().getCacheDir(), fileName);
    }

    private File buildTemporaryZipFile() {
        String fileName = UUID.randomUUID().toString() + ".zip";
        return new File(plugin.getContext().getCacheDir(), fileName);
    }

    private File buildBundlesDirectory() {
        return new File(plugin.getContext().getFilesDir(), bundlesDirectory);
    }

    private File buildBundleDirectoryFor(@NonNull String bundle) {
        return new File(plugin.getContext().getFilesDir(), bundlesDirectory + "/" + bundle);
    }

    /**
     * @param file The file to calculate the checksum for.
     * @return The sha256 checksum in hexadecimal format.
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private String calculateChecksumForFile(@NonNull File file) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            BufferedSource source = Okio.buffer(Okio.source(file));
            Buffer buffer = new Buffer();
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1; ) {
                digest.update(buffer.readByteArray());
            }
            source.close();
            byte[] checksumBytes = digest.digest();
            StringBuilder checksum = new StringBuilder();
            for (byte checksumByte : checksumBytes) {
                checksum.append(Integer.toString((checksumByte & 0xff) + 0x100, 16).substring(1));
            }
            return checksum.toString();
        } catch (IOException exception) {
            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
            throw new Exception(LiveUpdatePlugin.ERROR_CHECKSUM_CALCULATION_FAILED);
        }
    }

    private void createBundlesDirectory() {
        File bundlesDirectory = buildBundlesDirectory();
        if (!bundlesDirectory.exists()) {
            bundlesDirectory.mkdir();
        }
    }

    private void deleteBundle(@NonNull String bundleId) {
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        deleteFileRecursively(bundleDirectory);
    }

    private void deleteFileRecursively(@NonNull File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteFileRecursively(child);
            }
        }
        file.delete();
    }

    private void deleteUnusedBundles() {
        String[] bundleIds = getBundleIds();
        for (String bundleId : bundleIds) {
            if (!isBundleInUse(bundleId)) {
                deleteBundle(bundleId);
            }
        }
    }

    private void downloadBundle(@NonNull String bundleId, @Nullable String checksum, @NonNull String url, @NonNull EmptyCallback callback) {
        downloadFile(
            url,
            new NonEmptyCallback<File>() {
                @Override
                public void error(Exception exception) {
                    callback.error(exception);
                }

                @Override
                public void success(@NonNull File result) {
                    try {
                        if (checksum != null) {
                            // Calculate the checksum
                            String calculatedChecksum = calculateChecksumForFile(result);
                            if (!checksum.equals(calculatedChecksum)) {
                                throw new Exception(LiveUpdatePlugin.ERROR_CHECKSUM_MISMATCH);
                            }
                        }

                        // Add the bundle
                        addBundle(bundleId, result);

                        // Delete the temporary file
                        result.delete();

                        callback.success();
                    } catch (Exception exception) {
                        callback.error(exception);
                    }
                }
            }
        );
    }

    private void downloadFile(@NonNull String url, @NonNull NonEmptyCallback<File> callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient
            .newCall(request)
            .enqueue(
                new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException exception) {
                        callback.error(exception);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        if (response.isSuccessful()) {
                            File destinationFile = buildTemporaryZipFile();

                            long contentLength = responseBody.contentLength();
                            BufferedSource source = responseBody.source();

                            BufferedSink sink = Okio.buffer(Okio.sink(destinationFile));
                            Buffer sinkBuffer = sink.getBuffer();

                            long totalBytesRead = 0;
                            int bufferSize = 8 * 1024;
                            for (long bytesRead; (bytesRead = source.read(sinkBuffer, bufferSize)) != -1;) {
                                sink.emit();
                                totalBytesRead += bytesRead;
                                int progress = (int) ((totalBytesRead * 100) / contentLength);
                            }
                            sink.flush();
                            sink.close();
                            source.close();
                            callback.success(destinationFile);
                        } else {
                            Exception exception = new Exception(responseBody.string());
                            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
                            callback.error(new Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_FAILED));
                        }
                    }
                }
            );
    }

    @Nullable
    private void fetchLatestBundle(@NonNull NonEmptyCallback<GetLatestBundleResponse> callback)
        throws PackageManager.NameNotFoundException {
        HttpUrl url = new HttpUrl.Builder()
            .scheme("https")
            .host("api.cloud.capawesome.io")
            .addPathSegment("v1")
            .addPathSegment("apps")
            .addPathSegment(config.getAppId())
            .addPathSegment("bundles")
            .addPathSegment("latest")
            .addQueryParameter("appVersionCode", getVersionCode())
            .addQueryParameter("appVersionName", getVersionName())
            .addQueryParameter("bundleId", getCurrentBundleId())
            .addQueryParameter("channelName", preferences.getChannel())
            .addQueryParameter("customId", preferences.getCustomId())
            .addQueryParameter("deviceId", getDeviceId())
            .addQueryParameter("osVersion", String.valueOf(Build.VERSION.SDK_INT))
            .addQueryParameter("platform", "0")
            .addQueryParameter("pluginVersion", LiveUpdatePlugin.VERSION)
            .build();
        Logger.debug(LiveUpdatePlugin.TAG, "Fetching latest bundle from " + url);
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
        client
            .newCall(request)
            .enqueue(
                new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException exception) {
                        Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
                        callback.error(exception);
                    }

                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                        ResponseBody responseBody = response.body();
                        if (response.isSuccessful()) {
                            String responseBodyString = responseBody.string();
                            JSONObject responseJson = null;
                            try {
                                responseJson = new JSONObject(responseBodyString);
                            } catch (JSONException exception) {
                                callback.error(exception);
                            }
                            GetLatestBundleResponse getLatestBundleResponse = new GetLatestBundleResponse(responseJson);
                            callback.success(getLatestBundleResponse);
                        } else {
                            Exception exception = new Exception(responseBody.string());
                            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
                            callback.error(exception);
                        }
                    }
                }
            );
    }

    private String getVersionCode() throws PackageManager.NameNotFoundException {
        return String.valueOf(getPackageInfo().versionCode);
    }

    private String getVersionName() throws PackageManager.NameNotFoundException {
        return getPackageInfo().versionName;
    }

    private String[] getBundleIds() {
        File bundlesDirectory = buildBundlesDirectory();
        File[] bundles = bundlesDirectory.listFiles();
        if (bundles == null) {
            return new String[0];
        }

        String[] bundleIds = new String[bundles.length];
        for (int i = 0; i < bundles.length; i++) {
            bundleIds[i] = bundles[i].getName();
        }

        return bundleIds;
    }

    /**
     * @return The current bundle ID (`public` for the built-in bundle).
     */
    private String getCurrentBundleId() {
        String currentPath = getCurrentCapacitorServerPath();
        if (currentPath.equals(defaultWebAssetDir)) {
            return defaultWebAssetDir;
        }
        return new File(currentPath).getName();
    }

    /**
     * @return The absolute path to the current bundle directory (`public` for the built-in bundle).
     */
    private String getCurrentCapacitorServerPath() {
        return plugin.getBridge().getServerBasePath();
    }

    @NonNull
    private String getDeviceId() {
        String deviceId = preferences.getDeviceIdForApp(config.getAppId());
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString().toLowerCase();
            preferences.setDeviceIdForApp(config.getAppId(), deviceId);
        }
        return deviceId;
    }

    /**
     * @return The next bundle ID (`public` for the built-in bundle).
     */
    @NonNull
    private String getNextBundleId() {
        String nextPath = getNextCapacitorServerPath();
        if (nextPath.equals(defaultWebAssetDir)) {
            return defaultWebAssetDir;
        }
        return new File(nextPath).getName();
    }

    /**
     * @return The absolute path to the next bundle directory (`public` for the built-in bundle).
     */
    @NonNull
    private String getNextCapacitorServerPath() {
        return plugin
            .getContext()
            .getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE)
            .getString(WebView.CAP_SERVER_PATH, defaultWebAssetDir);
    }

    private boolean hasBundle(@NonNull String bundleId) {
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        return bundleDirectory.exists();
    }

    private boolean isBundleInUse(@NonNull String bundleId) {
        String currentBundleId = getCurrentBundleId();
        String nextBundleId = getNextBundleId();
        return bundleId.equals(currentBundleId) || bundleId.equals(nextBundleId);
    }

    private void rollback() {
        if (getCurrentBundleId() == defaultWebAssetDir) {
            Logger.debug(LiveUpdatePlugin.TAG, "App is not ready. Default bundle is already in use.");
            return;
        }
        Logger.debug(LiveUpdatePlugin.TAG, "App is not ready. Rolling back to default bundle.");
        setNextCapacitorServerPathToDefaultWebAssetDir();
        setCurrentCapacitorServerPathToDefaultWebAssetDir();
    }

    private void saveCurrentVersionCode() throws PackageManager.NameNotFoundException {
        int currentVersionCode = getPackageInfo().versionCode;
        preferences.setLastVersionCode(currentVersionCode);
    }

    @Nullable
    private File searchIndexHtmlFile(@NonNull File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return null;
        }
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }
        if (Arrays.asList(fileNames).contains("index.html")) {
            return new File(directory, "index.html");
        } else {
            for (File file : files) {
                if (file.isDirectory()) {
                    File indexHtmlFile = searchIndexHtmlFile(file);
                    if (indexHtmlFile != null) {
                        return indexHtmlFile;
                    }
                }
            }
        }
        return null;
    }

    private void setCurrentCapacitorServerPath(@NonNull String path) {
        if (path.equals(defaultWebAssetDir)) {
            this.plugin.getBridge().setServerAssetPath(path);
        } else {
            this.plugin.getBridge().setServerBasePath(path);
        }
        this.plugin.getBridge().reload();
    }

    private void setCurrentCapacitorServerPathToDefaultWebAssetDir() {
        setCurrentCapacitorServerPath(defaultWebAssetDir);
    }

    private void setNextBundle(@NonNull String bundleId) {
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        setNextCapacitorServerPath(bundleDirectory.getPath());
    }

    private void setNextCapacitorServerPath(@NonNull String path) {
        this.webViewSettingsEditor.putString(WebView.CAP_SERVER_PATH, path);
        this.webViewSettingsEditor.commit();
    }

    private void setNextCapacitorServerPathToDefaultWebAssetDir() {
        setNextCapacitorServerPath(defaultWebAssetDir);
    }

    private void startRollbackTimer() {
        stopRollbackTimer();
        rollbackHandler.postDelayed(() -> rollback(), config.getReadyTimeout());
    }

    private void stopRollbackTimer() {
        rollbackHandler.removeCallbacksAndMessages(null);
    }

    private File unzipFile(@NonNull File zipFile) throws IOException {
        File destination = buildTemporaryDirectory();
        String destinationPath = destination.getPath();
        new ZipFile(zipFile).extractAll(destinationPath);
        return destination;
    }

    private boolean wasUpdated() throws PackageManager.NameNotFoundException {
        int lastVersionCode = preferences.getLastVersionCode();
        int currentVersionCode = getPackageInfo().versionCode;
        return lastVersionCode != currentVersionCode;
    }

    private PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.plugin.getContext().getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return this.plugin.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));
        } else {
            return this.plugin.getContext().getPackageManager().getPackageInfo(packageName, 0);
        }
    }
}
