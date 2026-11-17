package io.capawesome.capacitorjs.plugins.liveupdate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.pm.PackageInfoCompat;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;
import com.getcapacitor.plugin.WebView;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.DownloadUrl;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.FileUrlTemplate;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.Manifest;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.ManifestFileCopy;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.ManifestItem;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.ManifestPlan;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.UpdateFailure;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckRequest;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponse;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.CreateCheckResponseBundle;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.GetChannelsResponseItem;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.DownloadBundleProgressEvent;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.NextBundleSetEvent;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DeleteBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchChannelsOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchLatestBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetChannelOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetConfigOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetCustomIdOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetNextBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SyncOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.ChannelResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.FetchChannelsResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.FetchLatestBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBlockedBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetChannelResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetConfigResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCurrentBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCustomIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDeviceIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDownloadedBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetNextBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionCodeResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionNameResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.IsSyncingResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.ReadyResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.SyncResult;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.FailureReason;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.CallbackStep;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.DownloadProgressCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.FileVerifier;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.ResponseBodyHandler;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    private static final AtomicBoolean downloadsDirectoryDeleted = new AtomicBoolean(false);

    /**
     * Per app ID, the `SystemClock.elapsedRealtime()` before which no check is made.
     * Shared by every instance and kept in memory only, so a restart of the app always checks.
     */
    private static final Map<String, Long> nextCheckAllowedAt = new ConcurrentHashMap<>();

    private final long autoUpdateIntervalMs = 15 * 60 * 1000; // 15 minutes

    @NonNull
    private final LiveUpdateConfig config;

    @NonNull
    private final Context context;

    private final String defaultWebAssetDir = Bridge.DEFAULT_WEB_ASSET_DIR;

    @NonNull
    private final LiveUpdateHttpClient httpClient;

    @Nullable
    private final LiveUpdatePlugin plugin;

    @NonNull
    private final LiveUpdatePreferences preferences;

    @NonNull
    private final SharedPreferences.Editor webViewSettingsEditor;

    private final String bundlesDirectory = "_capacitor_live_update_bundles"; // DO NOT CHANGE!
    private final String downloadsDirectory = "capawesome_capacitor_live_update_downloads";
    private final Handler rollbackHandler = new Handler(Looper.getMainLooper());
    private final String manifestFileName = "capawesome-live-update-manifest.json"; // DO NOT CHANGE!

    private boolean activityStopped = false;
    private boolean initialPageLoaded = false;
    private long lastAutoUpdateCheckTimestamp = 0;

    /** Milliseconds left on the rollback timer while it is paused in the background. */
    @Nullable
    private Long remainingRollbackTimeout = null;

    /** Uptime at which the scheduled rollback fires, or {@code null} if none is scheduled. */
    @Nullable
    private Long rollbackDeadline = null;

    private boolean rollbackPerformed = false;
    private boolean syncInProgress = false;

    public LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull LiveUpdatePlugin plugin) throws PackageManager.NameNotFoundException {
        this(config, plugin.getContext(), plugin);
    }

    /**
     * Creates a headless instance without a Capacitor plugin, e.g. for Ionic Portals hosts
     * that construct a provider manager directly. WebView-related operations (server path
     * changes, reload) and plugin events are skipped in this mode.
     */
    public LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull Context context) throws PackageManager.NameNotFoundException {
        this(config, context, null);
    }

    private LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull Context context, @Nullable LiveUpdatePlugin plugin)
        throws PackageManager.NameNotFoundException {
        this.config = config;
        this.context = context;
        this.httpClient = new LiveUpdateHttpClient(config);
        this.plugin = plugin;
        this.preferences = new LiveUpdatePreferences(context);
        this.webViewSettingsEditor = context.getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE).edit();

        // Check version and reset config if version changed
        checkAndResetConfigIfVersionChanged();

        // Set the device ID on the HTTP client (after any potential config reset)
        this.httpClient.setDeviceId(getDeviceId());

        // Delete the leftovers of downloads that were interrupted by a process kill.
        // Only once per process, because other instances may be downloading at this moment.
        if (downloadsDirectoryDeleted.compareAndSet(false, true)) {
            tryDeleteDownloadsDirectory();
        }

        // Start the rollback timer to rollback to the default bundle
        // if the app is not ready after a certain time
        startRollbackTimer();
    }

    public void clearBlockedBundles() {
        preferences.setBlockedBundleIds(null);
    }

    public void deleteBundle(@NonNull DeleteBundleOptions options, @NonNull EmptyCallback callback) {
        String bundleId = options.getBundleId();

        if (!hasBundleById(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_NOT_FOUND);
            callback.error(exception);
            return;
        }
        deleteBundleById(bundleId);

        callback.success();
    }

    public void downloadBundle(@NonNull DownloadBundleOptions options, @NonNull EmptyCallback callback) {
        try {
            DownloadUrl downloadUrl = DownloadUrl.fromOptions(options);
            String bundleId = options.getBundleId();
            // Check if the bundle already exists
            if (hasBundleById(bundleId)) {
                Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_EXISTS);
                callback.error(exception);
                return;
            }
            String failureAppId = options.getAppId() == null ? getAppId() : options.getAppId();
            boolean recordsFailure = downloadUrl.hasMarker() && failureAppId != null;
            EmptyCallback downloadCallback = recordsFailure
                ? recordFailureOnError(new UpdateFailure().setAppId(failureAppId).setBundleId(bundleId), callback)
                : callback;
            downloadBundle(bundleId, downloadUrl, downloadCallback);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void fetchChannels(@NonNull FetchChannelsOptions options, @NonNull NonEmptyCallback<FetchChannelsResult> callback) {
        try {
            HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("https")
                .host(config.getServerDomain())
                .addPathSegment("v1")
                .addPathSegment("apps")
                .addPathSegment(requireAppId())
                .addPathSegment("channels");
            if (options.getLimit() != null) {
                urlBuilder.addQueryParameter("limit", String.valueOf(options.getLimit()));
            }
            if (options.getOffset() != null) {
                urlBuilder.addQueryParameter("offset", String.valueOf(options.getOffset()));
            }
            if (options.getQuery() != null) {
                urlBuilder.addQueryParameter("query", options.getQuery());
            }
            String url = urlBuilder.build().toString();

            httpClient.enqueueWithDeviceId(
                url,
                new NonEmptyCallback<Response>() {
                    @Override
                    public void success(@NonNull Response response) {
                        try {
                            String responseBodyString = response.body().string();
                            if (response.isSuccessful()) {
                                JSONArray responseJsonArray = new JSONArray(responseBodyString);
                                ChannelResult[] channels = new ChannelResult[responseJsonArray.length()];
                                for (int i = 0; i < responseJsonArray.length(); i++) {
                                    GetChannelsResponseItem item = new GetChannelsResponseItem(responseJsonArray.getJSONObject(i));
                                    channels[i] = new ChannelResult(item.getId(), item.getName());
                                }
                                FetchChannelsResult result = new FetchChannelsResult(channels);
                                callback.success(result);
                            } else if (response.code() == 401) {
                                callback.error(new Exception("Unauthorized. Channel Discovery may not be enabled for this app."));
                            } else {
                                callback.error(new Exception(responseBodyString));
                            }
                        } catch (Exception e) {
                            callback.error(e);
                        }
                    }

                    @Override
                    public void error(@NonNull Exception exception) {
                        callback.error(exception);
                    }
                }
            );
        } catch (Exception e) {
            callback.error(e);
        }
    }

    public void fetchLatestBundle(@NonNull FetchLatestBundleOptions options, @NonNull NonEmptyCallback<FetchLatestBundleResult> callback) {
        createCheck(
            options,
            new NonEmptyCallback<CreateCheckResponse>() {
                @Override
                public void success(@NonNull CreateCheckResponse response) {
                    callback.success(new FetchLatestBundleResult(response));
                }

                @Override
                public void error(@NonNull Exception exception) {
                    callback.error(exception);
                }
            }
        );
    }

    @Nullable
    public String getAppId() {
        String appId = preferences.getAppId();
        if (appId != null) {
            return appId;
        }
        return config.getAppId();
    }

    public void getBlockedBundles(@NonNull NonEmptyCallback<GetBlockedBundlesResult> callback) {
        String blockedIds = preferences.getBlockedBundleIds();
        String[] bundleIds;
        if (blockedIds == null || blockedIds.isEmpty()) {
            bundleIds = new String[0];
        } else {
            bundleIds = blockedIds.split(",");
        }
        GetBlockedBundlesResult result = new GetBlockedBundlesResult(bundleIds);
        callback.success(result);
    }

    public void getBundles(@NonNull NonEmptyCallback callback) {
        String[] bundleIds = getDownloadedBundleIds();
        GetBundlesResult result = new GetBundlesResult(bundleIds);
        callback.success(result);
    }

    public void getDownloadedBundles(@NonNull NonEmptyCallback<GetDownloadedBundlesResult> callback) {
        String[] bundleIds = getDownloadedBundleIds();
        GetDownloadedBundlesResult result = new GetDownloadedBundlesResult(bundleIds);
        callback.success(result);
    }

    public void getChannel(@NonNull NonEmptyCallback callback) {
        String channel = getChannel();
        GetChannelResult result = new GetChannelResult(channel);
        callback.success(result);
    }

    @Nullable
    public String getChannel() {
        String channel = null;
        if (config.getDefaultChannel() != null) {
            channel = config.getDefaultChannel();
        }
        String nativeChannel = getNativeChannel();
        if (nativeChannel != null) {
            channel = nativeChannel;
        }
        if (preferences.getChannel() != null) {
            channel = preferences.getChannel();
        }
        return channel;
    }

    public void getConfig(@NonNull NonEmptyCallback<GetConfigResult> callback) {
        String appId = getAppId();
        String autoUpdateStrategy = config.getAutoUpdateStrategy();
        GetConfigResult result = new GetConfigResult(appId, autoUpdateStrategy);
        callback.success(result);
    }

    public void getCurrentBundle(@NonNull NonEmptyCallback<GetCurrentBundleResult> callback) {
        String bundleId = getCurrentBundleId();
        GetCurrentBundleResult result = new GetCurrentBundleResult(bundleId);
        callback.success(result);
    }

    /**
     * @return The on-disk directory of a downloaded bundle, or `null` if the bundle does not exist.
     */
    @Nullable
    public File getBundleDirectory(@NonNull String bundleId) {
        if (!hasBundleById(bundleId)) {
            return null;
        }
        return buildBundleDirectoryFor(bundleId);
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

    public void getNextBundle(@NonNull NonEmptyCallback<GetNextBundleResult> callback) {
        String bundleId = getNextBundleId();
        GetNextBundleResult result = new GetNextBundleResult(bundleId);
        callback.success(result);
    }

    public void getVersionCode(@NonNull NonEmptyCallback callback) throws PackageManager.NameNotFoundException {
        String versionCode = getVersionCodeAsString();
        GetVersionCodeResult result = new GetVersionCodeResult(versionCode);
        callback.success(result);
    }

    public String getVersionCodeAsString() throws PackageManager.NameNotFoundException {
        return String.valueOf(getVersionCodeAsInt());
    }

    public void getVersionName(@NonNull NonEmptyCallback callback) throws PackageManager.NameNotFoundException {
        String versionName = getVersionName();
        GetVersionNameResult result = new GetVersionNameResult(versionName);
        callback.success(result);
    }

    public void isSyncing(@NonNull NonEmptyCallback<IsSyncingResult> callback) {
        IsSyncingResult result = new IsSyncingResult(syncInProgress);
        callback.success(result);
    }

    public void handleOnPageLoaded() {
        // Wait for initial page load to perform auto update to make sure the WebViewLocalServer is initialized.
        // Otherwise, a NullPointerException may occur for `com.getcapacitor.WebViewLocalServer.getBasePath()`.
        if ("background".equals(config.getAutoUpdateStrategy()) && !initialPageLoaded) {
            initialPageLoaded = true;
            performAutoUpdate();
        }
    }

    public void handleOnResume() {
        if ("background".equals(config.getAutoUpdateStrategy()) && initialPageLoaded) {
            performAutoUpdate();
        }
    }

    public synchronized void handleOnStart() {
        activityStopped = false;
        resumeRollbackTimer();
    }

    public synchronized void handleOnStop() {
        activityStopped = true;
        pauseRollbackTimer();
    }

    public void ready(@NonNull NonEmptyCallback callback) {
        Logger.debug(LiveUpdatePlugin.TAG, "App is ready.");
        if (config.getReadyTimeout() <= 0) {
            Logger.warn(LiveUpdatePlugin.TAG, "Ready timeout is set to 0. Automatic rollback is disabled.");
        }
        // Stop the rollback timer
        stopRollbackTimer();
        // Delete unused bundles
        if (config.getAutoDeleteBundles()) {
            deleteUnusedBundles();
        }
        // Get the current and previous bundle IDs
        String currentBundleId = getCurrentBundleId();
        String previousBundleId = getPreviousBundleId();
        // Block the rolled back bundle if enabled
        if (config.getAutoBlockRolledBackBundles() && rollbackPerformed && previousBundleId != null) {
            addBlockedBundleId(previousBundleId);
        }
        // Return the result
        ReadyResult result = new ReadyResult(currentBundleId, previousBundleId, rollbackPerformed);
        callback.success(result);
        // Set the new previous bundle ID
        setPreviousBundleId(currentBundleId);
        // Reset the rollback flag
        rollbackPerformed = false;
    }

    public void reload() {
        String path = getNextCapacitorServerPath();
        setCurrentCapacitorServerPath(path);
        startRollbackTimer();
    }

    public void reset() {
        setNextBundleById(null);
    }

    public void resetConfig() {
        preferences.setAppId(null);
    }

    public void setChannel(@NonNull SetChannelOptions options, @NonNull EmptyCallback callback) {
        String channel = options.getChannel();

        preferences.setChannel(channel);
        callback.success();
    }

    public void setConfig(@NonNull SetConfigOptions options) {
        String appId = options.getAppId();
        preferences.setAppId(appId);
    }

    public void setCustomId(@NonNull SetCustomIdOptions options, @NonNull EmptyCallback callback) {
        String customId = options.getCustomId();

        preferences.setCustomId(customId);
        callback.success();
    }

    public void setNextBundle(@NonNull SetNextBundleOptions options, @NonNull EmptyCallback callback) {
        String bundleId = options.getBundleId();

        if (bundleId == null) {
            reset();
        } else {
            if (hasBundleById(bundleId)) {
                setNextBundleById(bundleId);
            } else {
                Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_NOT_FOUND);
                callback.error(exception);
                return;
            }
        }
        callback.success();
    }

    public void sync(@NonNull SyncOptions options, @NonNull NonEmptyCallback<Result> callback) {
        if (syncInProgress) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_SYNC_IN_PROGRESS);
            callback.error(exception);
            return;
        }
        syncInProgress = true;

        NonEmptyCallback<Result> syncCallback = finishSyncOnCompletion(callback);
        FetchLatestBundleOptions fetchLatestBundleOptions = new FetchLatestBundleOptions(options.getChannel());
        createCheck(
            fetchLatestBundleOptions,
            new NonEmptyCallback<CreateCheckResponse>() {
                @Override
                public void success(@NonNull CreateCheckResponse response) {
                    try {
                        handleSyncCheckResponse(response, syncCallback);
                    } catch (Exception e) {
                        syncCallback.error(e);
                    }
                }

                @Override
                public void error(@NonNull Exception exception) {
                    syncCallback.error(exception);
                }
            }
        );
    }

    /**
     * @return `true` if the bundle does not exist or was deleted, `false` if it is the current or next bundle.
     */
    public boolean tryDeleteUnusedBundle(@NonNull String bundleId) {
        if (!hasBundleById(bundleId)) {
            return true;
        }
        if (isBundleInUse(bundleId)) {
            return false;
        }
        deleteBundleById(bundleId);
        return true;
    }

    private void addBundle(@NonNull String bundleId, @NonNull File sourceDirectory) throws Exception {
        // Search folder with index.html file
        File indexHtmlFile = searchIndexHtmlFile(sourceDirectory);
        if (indexHtmlFile == null) {
            throw new Exception(LiveUpdatePlugin.ERROR_BUNDLE_INDEX_HTML_MISSING);
        }

        // Create the bundles directory if it does not exist
        createBundlesDirectory();

        // Move the bundle directory to the bundles directory
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        boolean moved = indexHtmlFile.getParentFile().renameTo(bundleDirectory);
        // A concurrent download of the same bundle may have finished first
        if (!moved && !hasBundleById(bundleId)) {
            throw new Exception(LiveUpdatePlugin.ERROR_BUNDLE_MOVE_FAILED);
        }
    }

    private void addBundleOfTypeManifest(@NonNull String bundleId, @NonNull File directory) throws Exception {
        addBundle(bundleId, directory);
    }

    private void addBundleOfTypeZip(@NonNull String bundleId, @NonNull File zipFile) throws Exception {
        // Unzip the file to the bundle directory
        File unzippedDirectory;
        try {
            unzippedDirectory = unzipDownloadedBundle(zipFile);
        } catch (ZipException exception) {
            throw exception;
        } catch (Exception exception) {
            // Every error of the unzip step counts as an invalid bundle, with the message of the original error.
            throw new ZipException(exception.getMessage(), exception);
        }
        // Add the bundle
        addBundle(bundleId, unzippedDirectory);
    }

    private File buildBundlesDirectory() {
        return new File(context.getFilesDir(), bundlesDirectory);
    }

    private File buildBundleDirectoryFor(@NonNull String bundleId) {
        return new File(context.getFilesDir(), bundlesDirectory + "/" + bundleId);
    }

    @NonNull
    private CreateCheckRequest buildCreateCheckRequest(@NonNull FetchLatestBundleOptions options, @Nullable UpdateFailure pendingFailure)
        throws PackageManager.NameNotFoundException {
        String bundleId = options.getBundleId() == null ? getCurrentBundleId() : options.getBundleId();
        String channel = options.getChannel() == null ? getChannel() : options.getChannel();
        return new CreateCheckRequest()
            .setAppVersionCode(getVersionCodeAsString())
            .setAppVersionName(getVersionName())
            .setBundleId(bundleId)
            .setChannelName(channel)
            .setCustomId(preferences.getCustomId())
            .setDeviceId(getDeviceId())
            .setFailure(pendingFailure)
            .setOsVersion(String.valueOf(Build.VERSION.SDK_INT))
            .setPlatform("android")
            .setPluginVersion(LiveUpdatePlugin.VERSION)
            .setRuntime("capacitor");
    }

    @NonNull
    private String buildCreateCheckUrl(@NonNull String appId) {
        return new HttpUrl.Builder()
            .scheme("https")
            .host(config.getServerDomain())
            .addPathSegment("v1")
            .addPathSegment("live-updates")
            .addPathSegment("apps")
            .addPathSegment(appId)
            .addPathSegment("checks")
            .build()
            .toString();
    }

    private File buildDownloadsDirectory() {
        return new File(context.getCacheDir(), downloadsDirectory);
    }

    @NonNull
    private String buildHrefUrl(@NonNull String baseUrl, @NonNull String href) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("href", href);
        return urlBuilder.build().toString();
    }

    private void copyCurrentBundleFile(@NonNull ManifestFileCopy fileCopy, @NonNull File destinationDirectory) throws IOException {
        String sourceHref = fileCopy.getSourceHref();
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId == null) {
            // Create the source input stream
            AssetManager assets = context.getAssets();
            InputStream inputStream = assets.open(defaultWebAssetDir + "/" + sourceHref);
            // Create the destination file
            File destination = new File(destinationDirectory, fileCopy.getItem().getHref());
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(inputStream, destination);
        } else {
            File currentBundleDirectory = buildBundleDirectoryFor(currentBundleId);
            // Create the source and destination files
            File source = new File(currentBundleDirectory, sourceHref);
            File destination = new File(destinationDirectory, fileCopy.getItem().getHref());
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(source, destination);
        }
    }

    private List<ManifestItem> copyCurrentBundleFilesAndReturnFailures(
        @NonNull List<ManifestFileCopy> filesToCopy,
        @NonNull File destinationDirectory
    ) {
        List<ManifestItem> missingItems = new ArrayList<>();
        for (ManifestFileCopy fileToCopy : filesToCopy) {
            boolean success = tryCopyCurrentBundleFile(fileToCopy, destinationDirectory);
            if (!success) {
                Logger.warn(LiveUpdatePlugin.TAG, "Failed to copy file: " + fileToCopy.getItem().getHref());
                // If the file could not be copied, add it to the list of missing items
                missingItems.add(fileToCopy.getItem());
            }
        }
        return missingItems;
    }

    private void copyFile(File input, File output) throws IOException {
        FileInputStream in = new FileInputStream(input);
        FileOutputStream out = new FileOutputStream(output);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.close();
        in.close();
    }

    private void copyFile(InputStream input, File output) throws IOException {
        FileOutputStream out = new FileOutputStream(output);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.close();
        input.close();
    }

    private void createBundlesDirectory() {
        File file = buildBundlesDirectory();
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void createCheck(@NonNull FetchLatestBundleOptions options, @NonNull NonEmptyCallback<CreateCheckResponse> callback) {
        try {
            String appId = requireAppId(options.getAppId());
            if (!isCheckAllowed(appId)) {
                Logger.debug(LiveUpdatePlugin.TAG, "Check skipped. The server asked to wait before the next check.");
                callback.success(CreateCheckResponse.createRateLimited());
                return;
            }
            UpdateFailure pendingFailure = getPendingFailureOfApp(appId);
            JSONObject body = buildCreateCheckRequest(options, pendingFailure).toJSONObject();
            String url = buildCreateCheckUrl(appId);
            Logger.debug(LiveUpdatePlugin.TAG, "Creating check: " + url + " " + body);

            httpClient.enqueuePost(url, body, createCheckResponseCallback(appId, pendingFailure, callback));
        } catch (Exception e) {
            callback.error(e);
        }
    }

    /**
     * Applies the wait of a 429 and clears the reported failure if the answer says so.
     */
    @NonNull
    private NonEmptyCallback<Response> createCheckResponseCallback(
        @NonNull String appId,
        @Nullable UpdateFailure reportedFailure,
        @NonNull NonEmptyCallback<CreateCheckResponse> callback
    ) {
        return new NonEmptyCallback<Response>() {
            @Override
            public void success(@NonNull Response response) {
                try {
                    CreateCheckResponse checkResponse = readCreateCheckResponse(response);
                    updateNextCheckAllowedAt(appId, checkResponse);
                    if (reportedFailure != null && checkResponse.clearsPendingFailure()) {
                        preferences.removePendingFailureIfUnchanged(reportedFailure);
                    }
                    callback.success(checkResponse);
                } catch (Exception e) {
                    callback.error(e);
                }
            }

            @Override
            public void error(@NonNull Exception exception) {
                callback.error(exception);
            }
        };
    }

    @NonNull
    private DownloadProgressCallback createDownloadProgressCallback(@NonNull String bundleId) {
        return (downloadedBytes, totalBytes) -> {
            DownloadBundleProgressEvent event = new DownloadBundleProgressEvent(bundleId, downloadedBytes, totalBytes);
            notifyDownloadBundleProgressListeners(event);
        };
    }

    private PublicKey createPublicKeyFromString(@NonNull String value) throws Exception {
        try {
            value = value.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n", "");
            byte[] byteKey = Base64.decode(value, Base64.DEFAULT);
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(X509publicKey);
        } catch (Exception exception) {
            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
            throw new Exception(LiveUpdatePlugin.ERROR_PUBLIC_KEY_INVALID);
        }
    }

    /**
     * Sets the next bundle once the download completed.
     */
    @NonNull
    private EmptyCallback createSyncDownloadCallback(@NonNull String bundleId, @NonNull NonEmptyCallback<Result> callback) {
        return new EmptyCallback() {
            @Override
            public void success() {
                try {
                    setNextBundleById(bundleId);
                    SyncResult syncResult = new SyncResult(bundleId);
                    callback.success(syncResult);
                } catch (Exception e) {
                    callback.error(e);
                }
            }

            @Override
            public void error(@NonNull Exception exception) {
                callback.error(exception);
            }
        };
    }

    private File createTemporaryDownloadDirectory() {
        File temporaryDirectory = new File(buildDownloadsDirectory(), UUID.randomUUID().toString());
        temporaryDirectory.mkdirs();
        return temporaryDirectory;
    }

    private void deleteBundleById(@NonNull String bundleId) {
        // Delete the bundle directory
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        deleteFileRecursively(bundleDirectory);
        // Reset the next bundle if it is the deleted bundle
        String nextBundleId = getNextBundleId();
        if (bundleId.equals(nextBundleId)) {
            setNextBundleById(null);
        }
    }

    private void deleteFileRecursively(@NonNull File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteFileRecursively(child);
                }
            }
        }
        file.delete();
    }

    @NonNull
    private EmptyCallback deleteTemporaryDirectoryOnCompletion(@NonNull File temporaryDirectory, @NonNull EmptyCallback callback) {
        return new EmptyCallback() {
            @Override
            public void success() {
                deleteFileRecursively(temporaryDirectory);
                callback.success();
            }

            @Override
            public void error(@NonNull Exception exception) {
                deleteFileRecursively(temporaryDirectory);
                callback.error(exception);
            }
        };
    }

    private void deleteUnusedBundles() {
        String[] bundleIds = getDownloadedBundleIds();
        for (String bundleId : bundleIds) {
            if (!isBundleInUse(bundleId)) {
                deleteBundleById(bundleId);
            }
        }
    }

    private Call downloadAndVerifyFile(
        @NonNull String url,
        @NonNull File file,
        @Nullable String checksum,
        @Nullable String signature,
        @Nullable DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        return httpClient.enqueueWithDeviceId(
            url,
            new NonEmptyCallback<Response>() {
                @Override
                public void success(@NonNull Response response) {
                    handleDownloadResponse(
                        response,
                        body -> {
                            LiveUpdateHttpClient.writeResponseBodyToFile(body, file, progressCallback);

                            // Extract checksum/signature from headers
                            String finalChecksum = checksum == null ? LiveUpdateHttpClient.getChecksumFromResponse(response) : checksum;
                            String finalSignature = signature == null ? LiveUpdateHttpClient.getSignatureFromResponse(response) : signature;

                            // Verify file
                            verifyFile(file, finalChecksum, finalSignature);
                        },
                        completionCallback
                    );
                }

                @Override
                public void error(@NonNull Exception exception) {
                    handleDownloadFailure(exception, completionCallback);
                }
            }
        );
    }

    /**
     * Downloads a file of an address with the marker: without the device ID header, and without reading any response header.
     */
    private Call downloadAndVerifyProtocolFile(
        @NonNull String url,
        @NonNull File file,
        @NonNull FileVerifier verifier,
        @Nullable DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        return httpClient.enqueue(
            url,
            new NonEmptyCallback<Response>() {
                @Override
                public void success(@NonNull Response response) {
                    if (response.code() == 403) {
                        response.close();
                        completionCallback.error(CustomExceptions.DOWNLOAD_URL_EXPIRED);
                        return;
                    }
                    handleDownloadResponse(
                        response,
                        body -> {
                            LiveUpdateHttpClient.writeResponseBodyToFile(body, file, progressCallback);
                            verifier.verify(file);
                        },
                        completionCallback
                    );
                }

                @Override
                public void error(@NonNull Exception exception) {
                    handleDownloadFailure(exception, completionCallback);
                }
            }
        );
    }

    private void downloadBundle(@NonNull String bundleId, @NonNull DownloadUrl downloadUrl, @NonNull EmptyCallback callback) {
        if (downloadUrl.getArtifactType() == ArtifactType.MANIFEST) {
            downloadBundleOfTypeManifest(bundleId, downloadUrl, callback);
        } else {
            downloadBundleOfTypeZip(bundleId, downloadUrl, callback);
        }
    }

    private Call downloadBundleFile(
        @NonNull DownloadUrl downloadUrl,
        @NonNull ManifestItem item,
        @NonNull File destinationDirectory,
        @Nullable DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        File destinationFile = new File(destinationDirectory, item.getHref());
        destinationFile.getParentFile().mkdirs();
        FileUrlTemplate fileUrlTemplate = downloadUrl.getFileUrlTemplate();
        if (fileUrlTemplate == null) {
            String url = buildHrefUrl(downloadUrl.getUrl(), item.getHref());
            return downloadAndVerifyFile(url, destinationFile, null, null, progressCallback, completionCallback);
        }
        String checksum = item.getChecksum();
        String url = fileUrlTemplate.fill(checksum);
        return downloadAndVerifyProtocolFile(
            url,
            destinationFile,
            file -> verifyChecksum(file, checksum),
            progressCallback,
            completionCallback
        );
    }

    private void downloadBundleFiles(
        @NonNull DownloadUrl downloadUrl,
        @NonNull List<ManifestItem> filesToDownload,
        @NonNull File destinationDirectory,
        @Nullable DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        if (filesToDownload.isEmpty()) {
            if (progressCallback != null) {
                progressCallback.onProgress(0, 0);
            }
            completionCallback.success();
            return;
        }

        // Thread-safe progress tracking
        AtomicLong totalBytesDownloaded = new AtomicLong(0);
        long totalBytesToDownload = 0;
        for (ManifestItem item : filesToDownload) {
            totalBytesToDownload += item.getSizeInBytes();
        }
        final long finalTotalBytesToDownload = totalBytesToDownload;

        // Coordination primitives
        CountDownLatch latch = new CountDownLatch(filesToDownload.size());
        AtomicReference<Exception> firstError = new AtomicReference<>();
        AtomicBoolean completionHandled = new AtomicBoolean(false);
        List<Call> activeCalls = new ArrayList<>();

        // Start all downloads asynchronously (OkHttp's dispatcher handles parallelization)
        for (ManifestItem item : filesToDownload) {
            Call call = downloadBundleFile(
                downloadUrl,
                item,
                destinationDirectory,
                (downloadedBytes, totalBytes) -> {
                    // Per-file progress
                    if (progressCallback != null) {
                        long totalProgress = totalBytesDownloaded.get() + downloadedBytes;
                        progressCallback.onProgress(totalProgress, finalTotalBytesToDownload);
                    }
                },
                new EmptyCallback() {
                    @Override
                    public void success() {
                        // Update total progress
                        totalBytesDownloaded.addAndGet(item.getSizeInBytes());
                        if (progressCallback != null) {
                            progressCallback.onProgress(totalBytesDownloaded.get(), finalTotalBytesToDownload);
                        }
                        latch.countDown();

                        // Check if this was the last download to complete
                        if (latch.getCount() == 0 && completionHandled.compareAndSet(false, true)) {
                            Exception error = firstError.get();
                            if (error != null) {
                                completionCallback.error(error);
                            } else {
                                // Final progress update
                                if (progressCallback != null) {
                                    progressCallback.onProgress(finalTotalBytesToDownload, finalTotalBytesToDownload);
                                }
                                completionCallback.success();
                            }
                        }
                    }

                    @Override
                    public void error(@NonNull Exception e) {
                        // Capture first error and cancel all remaining downloads
                        if (firstError.compareAndSet(null, e)) {
                            Logger.error(LiveUpdatePlugin.TAG, "Failed to download file: " + item.getHref(), e);
                            // Cancel all in-flight downloads (fail-fast)
                            synchronized (activeCalls) {
                                for (Call activeCall : activeCalls) {
                                    if (!activeCall.isCanceled() && !activeCall.isExecuted()) {
                                        activeCall.cancel();
                                    }
                                }
                            }
                        }
                        latch.countDown();

                        // Check if this was the last download to complete
                        if (latch.getCount() == 0 && completionHandled.compareAndSet(false, true)) {
                            completionCallback.error(firstError.get());
                        }
                    }
                }
            );
            synchronized (activeCalls) {
                activeCalls.add(call);
            }
        }
    }

    private void downloadBundleOfTypeManifest(
        @NonNull String bundleId,
        @NonNull DownloadUrl downloadUrl,
        @NonNull EmptyCallback completionCallback
    ) {
        // Create a temporary directory that is deleted once the download completed or failed
        File temporaryDirectory = createTemporaryDownloadDirectory();
        EmptyCallback callback = deleteTemporaryDirectoryOnCompletion(temporaryDirectory, completionCallback);
        File latestManifestFile = new File(temporaryDirectory, manifestFileName);
        EmptyCallback addBundleCallback = runOnSuccess(callback, () -> {
            addBundleOfTypeManifest(bundleId, temporaryDirectory);
            callback.success();
        });
        EmptyCallback downloadFilesCallback = runOnSuccess(callback, () -> {
            Manifest latestManifest = loadLatestManifest(latestManifestFile);
            List<ManifestItem> itemsToDownload = copyUnchangedFilesAndListFilesToDownload(latestManifest, temporaryDirectory);
            downloadBundleFiles(
                downloadUrl,
                itemsToDownload,
                temporaryDirectory,
                createDownloadProgressCallback(bundleId),
                addBundleCallback
            );
        });
        try {
            downloadManifestFile(downloadUrl, latestManifestFile, downloadFilesCallback);
        } catch (Exception e) {
            callback.error(e);
        }
    }

    private void downloadBundleOfTypeZip(
        @NonNull String bundleId,
        @NonNull DownloadUrl downloadUrl,
        @NonNull EmptyCallback completionCallback
    ) {
        // Create a temporary directory that is deleted once the download completed or failed
        File temporaryDirectory = createTemporaryDownloadDirectory();
        EmptyCallback callback = deleteTemporaryDirectoryOnCompletion(temporaryDirectory, completionCallback);
        File zipFile = new File(temporaryDirectory, "bundle.zip");
        try {
            // Download the bundle
            downloadZipFile(
                downloadUrl,
                zipFile,
                createDownloadProgressCallback(bundleId),
                new EmptyCallback() {
                    @Override
                    public void success() {
                        try {
                            // Add the bundle
                            addBundleOfTypeZip(bundleId, zipFile);
                            callback.success();
                        } catch (Exception e) {
                            callback.error(e);
                        }
                    }

                    @Override
                    public void error(@NonNull Exception exception) {
                        callback.error(exception);
                    }
                }
            );
        } catch (Exception e) {
            callback.error(e);
        }
    }

    private void downloadManifestFile(@NonNull DownloadUrl downloadUrl, @NonNull File manifestFile, @NonNull EmptyCallback callback) {
        if (downloadUrl.hasMarker()) {
            downloadAndVerifyProtocolFile(
                downloadUrl.getUrl(),
                manifestFile,
                file -> verifyFirstProtocolFile(file, downloadUrl),
                null,
                callback
            );
        } else {
            String url = buildHrefUrl(downloadUrl.getUrl(), manifestFileName);
            downloadAndVerifyFile(url, manifestFile, null, null, null, callback);
        }
    }

    private void downloadZipFile(
        @NonNull DownloadUrl downloadUrl,
        @NonNull File zipFile,
        @NonNull DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        if (downloadUrl.hasMarker()) {
            downloadAndVerifyProtocolFile(
                downloadUrl.getUrl(),
                zipFile,
                file -> verifyFirstProtocolFile(file, downloadUrl),
                progressCallback,
                completionCallback
            );
        } else {
            downloadAndVerifyFile(
                downloadUrl.getUrl(),
                zipFile,
                downloadUrl.getChecksum(),
                downloadUrl.getSignature(),
                progressCallback,
                completionCallback
            );
        }
    }

    @NonNull
    private NonEmptyCallback<Result> finishSyncOnCompletion(@NonNull NonEmptyCallback<Result> callback) {
        return new NonEmptyCallback<Result>() {
            @Override
            public void success(@NonNull Result result) {
                syncInProgress = false;
                callback.success(result);
            }

            @Override
            public void error(@NonNull Exception exception) {
                syncInProgress = false;
                callback.error(exception);
            }
        };
    }

    private String[] getDownloadedBundleIds() {
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

    private byte[] getChecksumForFileAsBytes(@NonNull File file) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            BufferedSource source = Okio.buffer(Okio.source(file));
            Buffer buffer = new Buffer();
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1; ) {
                digest.update(buffer.readByteArray());
            }
            source.close();
            return digest.digest();
        } catch (IOException exception) {
            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
            throw new Exception(LiveUpdatePlugin.ERROR_CHECKSUM_CALCULATION_FAILED);
        }
    }

    private String getChecksumForFileAsString(@NonNull File file) throws Exception {
        byte[] checksumBytes = getChecksumForFileAsBytes(file);
        StringBuilder checksum = new StringBuilder();
        for (byte checksumByte : checksumBytes) {
            checksum.append(Integer.toString((checksumByte & 0xff) + 0x100, 16).substring(1));
        }
        return checksum.toString();
    }

    @Nullable
    private String getNativeChannel() {
        int resId = context.getResources().getIdentifier("capawesome_live_update_default_channel", "string", context.getPackageName());
        if (resId == 0) {
            return null;
        }
        return context.getResources().getString(resId);
    }

    /**
     * @return The current bundle ID or `null` if the default bundle is in use.
     */
    @Nullable
    private String getCurrentBundleId() {
        String currentPath = getCurrentCapacitorServerPath();
        if (currentPath.equals(defaultWebAssetDir)) {
            return null;
        }
        return new File(currentPath).getName();
    }

    /**
     * @return The absolute path to the current bundle directory (`public` for the built-in bundle).
     */
    private String getCurrentCapacitorServerPath() {
        if (plugin == null) {
            // Headless mode: there is no WebView, so the default bundle is always current.
            return defaultWebAssetDir;
        }
        return plugin.getBridge().getServerBasePath();
    }

    @NonNull
    private String getDeviceId() {
        String deviceId = preferences.getDeviceId();
        if (deviceId != null) {
            return deviceId;
        }
        deviceId = preferences.getLegacyDeviceIdForApp(getAppId());
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString().toLowerCase();
        }
        preferences.setDeviceId(deviceId);
        return deviceId;
    }

    /**
     * @return The next bundle ID or `null` if the default bundle will be used.
     */
    @Nullable
    private String getNextBundleId() {
        String nextPath = getNextCapacitorServerPath();
        if (nextPath.equals(defaultWebAssetDir)) {
            return null;
        }
        return new File(nextPath).getName();
    }

    /**
     * @return The absolute path to the next bundle directory (`public` for the built-in bundle).
     */
    @NonNull
    private String getNextCapacitorServerPath() {
        String path = context
            .getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE)
            .getString(WebView.CAP_SERVER_PATH, defaultWebAssetDir);
        // Empty path means default path
        if (path.isEmpty()) {
            path = defaultWebAssetDir;
        }
        return path;
    }

    @Nullable
    private UpdateFailure getPendingFailureOfApp(@NonNull String appId) {
        UpdateFailure failure = preferences.getPendingFailure();
        if (failure == null || !appId.equals(failure.getAppId())) {
            return null;
        }
        return failure;
    }

    /**
     * @return The previous bundle ID or `null` if the default bundle was used.
     */
    @Nullable
    private String getPreviousBundleId() {
        return preferences.getPreviousBundleId();
    }

    private int getVersionCodeAsInt() throws PackageManager.NameNotFoundException {
        return (int) PackageInfoCompat.getLongVersionCode(getPackageInfo());
    }

    private String getVersionName() throws PackageManager.NameNotFoundException {
        return getPackageInfo().versionName;
    }

    private void handleDownloadFailure(@NonNull Exception exception, @NonNull EmptyCallback callback) {
        Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
        if (exception instanceof SocketTimeoutException) {
            callback.error(exception);
        } else {
            callback.error(new Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_FAILED));
        }
    }

    private void handleDownloadResponse(
        @NonNull Response response,
        @NonNull ResponseBodyHandler bodyHandler,
        @NonNull EmptyCallback completionCallback
    ) {
        try {
            if (response.isSuccessful()) {
                bodyHandler.handle(response.body());
                completionCallback.success();
            } else {
                String errorMessage = response.body().string();
                Exception exception = new Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_FAILED);
                Logger.error(LiveUpdatePlugin.TAG, errorMessage, exception);
                completionCallback.error(exception);
            }
        } catch (IOException e) {
            handleDownloadFailure(e, completionCallback);
        } catch (Exception e) {
            completionCallback.error(e);
        }
    }

    private void handleSyncCheckResponse(@NonNull CreateCheckResponse response, @NonNull NonEmptyCallback<Result> callback)
        throws Exception {
        CreateCheckResponseBundle bundle = response.getBundle();
        if (bundle == null) {
            Logger.debug(LiveUpdatePlugin.TAG, "No update available.");
            callback.success(SyncResult.createWithoutNextBundle(response.getSkipReason()));
            return;
        }
        String latestBundleId = bundle.getId();
        if (isBlockedBundleId(latestBundleId)) {
            Logger.warn(LiveUpdatePlugin.TAG, "Bundle is blocked and will not be downloaded.");
            callback.success(new SyncResult(null));
            return;
        }
        if (hasBundleById(latestBundleId)) {
            String nextBundleId = setNextBundleIfNotCurrent(latestBundleId);
            callback.success(new SyncResult(nextBundleId));
            return;
        }
        UpdateFailure failure = new UpdateFailure().setAppId(requireAppId()).setBundleId(latestBundleId);
        EmptyCallback downloadCallback = recordFailureOnError(failure, createSyncDownloadCallback(latestBundleId, callback));
        downloadBundle(latestBundleId, DownloadUrl.fromBundle(bundle), downloadCallback);
    }

    private boolean hasBundleById(@NonNull String bundleId) {
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        return bundleDirectory.exists();
    }

    private boolean isBundleInUse(@NonNull String bundleId) {
        String currentBundleId = getCurrentBundleId();
        String nextBundleId = getNextBundleId();
        return bundleId.equals(currentBundleId) || bundleId.equals(nextBundleId);
    }

    /**
     * A check is not allowed while the wait that the server asked for after a 429 lasts.
     */
    private boolean isCheckAllowed(@NonNull String appId) {
        Long allowedAt = nextCheckAllowedAt.get(appId);
        return allowedAt == null || SystemClock.elapsedRealtime() >= allowedAt;
    }

    @NonNull
    private Manifest loadLatestManifest(@NonNull File manifestFile) throws Exception {
        Manifest manifest = Manifest.parse(readText(Okio.buffer(Okio.source(manifestFile))));
        if (manifest == null) {
            throw new Exception(LiveUpdatePlugin.ERROR_MANIFEST_INVALID);
        }
        return manifest;
    }

    private void notifyDownloadBundleProgressListeners(@NonNull final DownloadBundleProgressEvent event) {
        if (plugin == null) {
            return;
        }
        plugin.notifyDownloadBundleProgressListeners(event);
    }

    private void performAutoUpdate() {
        // Check if enough time has passed since the last check
        long now = System.currentTimeMillis();
        if (lastAutoUpdateCheckTimestamp > 0 && (now - lastAutoUpdateCheckTimestamp) < autoUpdateIntervalMs) {
            Logger.debug(LiveUpdatePlugin.TAG, "Auto-update skipped. Last check was less than 15 minutes ago.");
            return;
        }

        // Update the timestamp
        lastAutoUpdateCheckTimestamp = now;

        // Run sync
        Logger.debug(LiveUpdatePlugin.TAG, "Auto-update started.");
        SyncOptions options = new SyncOptions((String) null);
        NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
            @Override
            public void success(@NonNull Result result) {
                Logger.debug(LiveUpdatePlugin.TAG, "Auto-update completed successfully.");
            }

            @Override
            public void error(@NonNull Exception exception) {
                Logger.error(LiveUpdatePlugin.TAG, "Auto-update failed: " + exception.getMessage(), exception);
            }
        };
        sync(options, callback);
    }

    private void pauseRollbackTimer() {
        if (rollbackDeadline == null) {
            return;
        }
        rollbackHandler.removeCallbacksAndMessages(null);
        remainingRollbackTimeout = Math.max(0, rollbackDeadline - SystemClock.uptimeMillis());
        rollbackDeadline = null;
    }

    /**
     * @return The files of the new manifest that must be downloaded, after the others were copied from the current bundle.
     */
    @NonNull
    private List<ManifestItem> copyUnchangedFilesAndListFilesToDownload(
        @NonNull Manifest latestManifest,
        @NonNull File destinationDirectory
    ) {
        ManifestPlan plan = latestManifest.createPlan(tryLoadCurrentManifest());
        List<ManifestItem> itemsToDownload = new ArrayList<>(plan.getItemsToDownload());
        // If files could not be copied, download them instead
        itemsToDownload.addAll(copyCurrentBundleFilesAndReturnFailures(plan.getFilesToCopy(), destinationDirectory));
        return itemsToDownload;
    }

    @NonNull
    private CreateCheckResponse readCreateCheckResponse(@NonNull Response response) throws IOException {
        String body = response.body().string();
        Logger.debug(LiveUpdatePlugin.TAG, "Check response: " + response.code() + " " + body);
        return CreateCheckResponse.parse(response.code(), response.header("Retry-After"), body);
    }

    /**
     * @return The text of the manifest of the current bundle, or `null` if it has none.
     */
    @Nullable
    private String readCurrentManifestText() throws IOException {
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId == null) {
            AssetManager assets = context.getAssets();
            boolean manifestFileExists = Arrays.asList(assets.list(defaultWebAssetDir)).contains(manifestFileName);
            if (!manifestFileExists) {
                return null;
            }
            return readText(Okio.buffer(Okio.source(assets.open(defaultWebAssetDir + "/" + manifestFileName))));
        }
        File manifestFile = new File(buildBundleDirectoryFor(currentBundleId), manifestFileName);
        if (!manifestFile.exists()) {
            return null;
        }
        return readText(Okio.buffer(Okio.source(manifestFile)));
    }

    @NonNull
    private String readText(@NonNull BufferedSource source) throws IOException {
        try (source) {
            return source.readUtf8();
        }
    }

    /**
     * Records the failure of a download as the pending failure, so that the next check reports it.
     */
    /**
     * @param failure The app ID and bundle ID of the download. The reason is set when the download fails.
     */
    @NonNull
    private EmptyCallback recordFailureOnError(@NonNull UpdateFailure failure, @NonNull EmptyCallback callback) {
        return new EmptyCallback() {
            @Override
            public void success() {
                callback.success();
            }

            @Override
            public void error(@NonNull Exception exception) {
                FailureReason reason = toFailureReason(exception);
                if (reason != null) {
                    tryRecordFailure(failure.setReason(reason));
                }
                callback.error(exception);
            }
        };
    }

    private void resumeRollbackTimer() {
        if (remainingRollbackTimeout == null) {
            return;
        }
        scheduleRollback(remainingRollbackTimeout);
        remainingRollbackTimeout = null;
    }

    @NonNull
    private String requireAppId() throws Exception {
        return requireAppId(null);
    }

    @NonNull
    private String requireAppId(@Nullable String appIdOverride) throws Exception {
        String appId = appIdOverride == null ? getAppId() : appIdOverride;
        if (appId == null || appId.isEmpty()) {
            throw new Exception(LiveUpdatePlugin.ERROR_APP_ID_MISSING);
        }
        return appId;
    }

    private void rollback() {
        // Set the rollback flag
        rollbackPerformed = true;
        // Set the new previous bundle ID
        String currentBundleId = getCurrentBundleId();
        setPreviousBundleId(currentBundleId);
        // Log the rollback result
        if (currentBundleId == null) {
            Logger.debug(LiveUpdatePlugin.TAG, "App is not ready. Default bundle is already in use.");
        } else {
            Logger.debug(LiveUpdatePlugin.TAG, "App is not ready. Rolling back to default bundle.");
            String appId = getAppId();
            if (appId != null) {
                tryRecordFailure(
                    new UpdateFailure().setAppId(appId).setBundleId(currentBundleId).setReason(FailureReason.READINESS_TIMED_OUT)
                );
            }
            // Rollback to the default bundle
            setNextBundleById(null);
            setCurrentBundleById(null);
        }
    }

    /**
     * @return A callback that runs the step on success and passes every error, also one of the step, to the given callback.
     */
    @NonNull
    private EmptyCallback runOnSuccess(@NonNull EmptyCallback callback, @NonNull CallbackStep step) {
        return new EmptyCallback() {
            @Override
            public void success() {
                try {
                    step.run();
                } catch (Exception e) {
                    callback.error(e);
                }
            }

            @Override
            public void error(@NonNull Exception exception) {
                callback.error(exception);
            }
        };
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

    /**
     * @param bundleId The bundle ID to set as the current bundle. If `null`, the default bundle will be used.
     */
    private void setCurrentBundleById(@Nullable String bundleId) {
        if (bundleId == null) {
            setCurrentCapacitorServerPath(defaultWebAssetDir);
        } else {
            File bundleDirectory = buildBundleDirectoryFor(bundleId);
            setCurrentCapacitorServerPath(bundleDirectory.getPath());
        }
    }

    private void setCurrentCapacitorServerPath(@NonNull String path) {
        if (plugin == null) {
            // Headless mode: there is no WebView to point at the new path.
            return;
        }
        if (path.equals(defaultWebAssetDir)) {
            this.plugin.getBridge().setServerAssetPath(path);
        } else {
            this.plugin.getBridge().setServerBasePath(path);
        }
        this.plugin.getBridge().reload();
        // Notify listeners
        notifyReloadedListeners();
    }

    /**
     * @param bundleId The bundle ID to set as the next bundle. If `null`, the default bundle will be used.
     */
    private void setNextBundleById(@Nullable String bundleId) {
        if (bundleId == null) {
            setNextCapacitorServerPath(defaultWebAssetDir);
        } else {
            File bundleDirectory = buildBundleDirectoryFor(bundleId);
            setNextCapacitorServerPath(bundleDirectory.getPath());
        }

        // Notify listeners
        notifyNextBundleSetListeners(bundleId);
    }

    /**
     * @return The next bundle ID, or `null` if the bundle is already the current bundle.
     */
    @Nullable
    private String setNextBundleIfNotCurrent(@NonNull String bundleId) {
        if (bundleId.equals(getCurrentBundleId())) {
            return null;
        }
        setNextBundleById(bundleId);
        return bundleId;
    }

    private void notifyNextBundleSetListeners(@Nullable String bundleId) {
        if (plugin == null) {
            return;
        }
        NextBundleSetEvent event = new NextBundleSetEvent(bundleId);
        plugin.notifyNextBundleSetListeners(event);
    }

    private void notifyReloadedListeners() {
        if (plugin == null) {
            return;
        }
        plugin.notifyReloadedListeners();
    }

    private void addBlockedBundleId(@NonNull String bundleId) {
        String blockedIds = preferences.getBlockedBundleIds();
        List<String> blockedList = new ArrayList<>();

        // Parse existing blocked IDs
        if (blockedIds != null && !blockedIds.isEmpty()) {
            String[] ids = blockedIds.split(",");
            blockedList.addAll(Arrays.asList(ids));
        }

        // Skip if already blocked
        if (blockedList.contains(bundleId)) {
            return;
        }

        // Remove oldest if limit reached
        if (blockedList.size() >= 100) {
            blockedList.remove(0);
        }

        // Add new bundle
        blockedList.add(bundleId);

        // Save back to preferences
        String newBlockedIds = String.join(",", blockedList);
        preferences.setBlockedBundleIds(newBlockedIds);

        Logger.debug(LiveUpdatePlugin.TAG, "Bundle blocked: " + bundleId);
    }

    private boolean isBlockedBundleId(@NonNull String bundleId) {
        String blockedIds = preferences.getBlockedBundleIds();
        if (blockedIds == null || blockedIds.isEmpty()) {
            return false;
        }

        String[] ids = blockedIds.split(",");
        return Arrays.asList(ids).contains(bundleId);
    }

    private void setNextCapacitorServerPath(@NonNull String path) {
        this.webViewSettingsEditor.putString(WebView.CAP_SERVER_PATH, path);
        this.webViewSettingsEditor.commit();
    }

    private void checkAndResetConfigIfVersionChanged() throws PackageManager.NameNotFoundException {
        int currentVersionCode = getVersionCodeAsInt();
        int lastVersionCode = preferences.getLastVersionCode();

        if (lastVersionCode == -1 || lastVersionCode != currentVersionCode) {
            Logger.debug(
                LiveUpdatePlugin.TAG,
                "App version changed (last: " + lastVersionCode + ", current: " + currentVersionCode + "), resetting config."
            );
            resetConfig();
            preferences.setLastVersionCode(currentVersionCode);
        }
    }

    private void setPreviousBundleId(@Nullable String bundleId) {
        preferences.setPreviousBundleId(bundleId);
    }

    private void scheduleRollback(long delayMillis) {
        rollbackDeadline = SystemClock.uptimeMillis() + delayMillis;
        rollbackHandler.postDelayed(() -> {
            synchronized (this) {
                rollbackDeadline = null;
            }
            rollback();
        }, delayMillis);
    }

    /** The timer only counts time in the foreground, because a stopped activity may not run JS. */
    private synchronized void startRollbackTimer() {
        if (config.getReadyTimeout() <= 0) {
            return;
        }
        stopRollbackTimer();
        if (activityStopped) {
            remainingRollbackTimeout = (long) config.getReadyTimeout();
        } else {
            scheduleRollback(config.getReadyTimeout());
        }
    }

    private synchronized void stopRollbackTimer() {
        rollbackHandler.removeCallbacksAndMessages(null);
        rollbackDeadline = null;
        remainingRollbackTimeout = null;
    }

    @Nullable
    private FailureReason toFailureReason(@NonNull Exception exception) {
        if (exception == CustomExceptions.DOWNLOAD_URL_EXPIRED || exception instanceof SocketTimeoutException) {
            return FailureReason.DOWNLOAD_FAILED;
        }
        if (exception instanceof ZipException) {
            return FailureReason.BUNDLE_INVALID;
        }
        String message = exception.getMessage();
        if (message == null) {
            return null;
        }
        switch (message) {
            case LiveUpdatePlugin.ERROR_BUNDLE_INDEX_HTML_MISSING:
            case LiveUpdatePlugin.ERROR_MANIFEST_INVALID:
                return FailureReason.BUNDLE_INVALID;
            case LiveUpdatePlugin.ERROR_CHECKSUM_MISMATCH:
                return FailureReason.CHECKSUM_MISMATCHED;
            case LiveUpdatePlugin.ERROR_DOWNLOAD_FAILED:
                return FailureReason.DOWNLOAD_FAILED;
            case LiveUpdatePlugin.ERROR_PUBLIC_KEY_INVALID:
            case LiveUpdatePlugin.ERROR_SIGNATURE_MISSING:
            case LiveUpdatePlugin.ERROR_SIGNATURE_VERIFICATION_FAILED:
                return FailureReason.SIGNATURE_VERIFICATION_FAILED;
            default:
                return null;
        }
    }

    private boolean tryCopyCurrentBundleFile(@NonNull ManifestFileCopy fileToCopy, @NonNull File destinationDirectory) {
        try {
            copyCurrentBundleFile(fileToCopy, destinationDirectory);
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private void tryDeleteDownloadsDirectory() {
        deleteFileRecursively(buildDownloadsDirectory());
    }

    /**
     * A current manifest that is missing or cannot be used means that every file is downloaded.
     */
    @Nullable
    private Manifest tryLoadCurrentManifest() {
        try {
            String text = readCurrentManifestText();
            return text == null ? null : Manifest.parse(text);
        } catch (IOException exception) {
            Logger.warn(LiveUpdatePlugin.TAG, "Failed to read the current manifest: " + exception.getMessage());
            return null;
        }
    }

    private void tryRecordFailure(@NonNull UpdateFailure failure) {
        try {
            preferences.setPendingFailure(failure);
        } catch (JSONException exception) {
            Logger.warn(LiveUpdatePlugin.TAG, "Failed to record the failed update: " + exception.getMessage());
        }
    }

    private File unzipDownloadedBundle(@NonNull File zipFile) throws IOException {
        File destination = new File(zipFile.getParentFile(), "bundle");
        String destinationPath = destination.getPath();
        ZipFile zip = new ZipFile(zipFile);
        // Clear stored Unix permissions to prevent EACCES errors on newer Android versions
        // where restrictive directory permissions from the zip block file creation.
        for (FileHeader fileHeader : zip.getFileHeaders()) {
            fileHeader.setExternalFileAttributes(null);
        }
        zip.extractAll(destinationPath);
        return destination;
    }

    private void updateNextCheckAllowedAt(@NonNull String appId, @NonNull CreateCheckResponse response) {
        Long retryAfterMs = response.getRetryAfterMs();
        if (retryAfterMs == null) {
            return;
        }
        nextCheckAllowedAt.put(appId, SystemClock.elapsedRealtime() + retryAfterMs);
    }

    private void verifyChecksum(@NonNull File file, @NonNull String checksum) throws Exception {
        boolean verified = verifyChecksumForFile(file, checksum);
        if (!verified) {
            throw new Exception(LiveUpdatePlugin.ERROR_CHECKSUM_MISMATCH);
        }
    }

    private boolean verifyChecksumForFile(@NonNull File file, @NonNull String checksum) throws Exception {
        String receivedChecksum = getChecksumForFileAsString(file);
        return checksum.equals(receivedChecksum);
    }

    private void verifyFile(@NonNull File file, @Nullable String checksum, @Nullable String signature) throws Exception {
        String publicKey = config.getPublicKey();
        if (publicKey != null) {
            verifySignature(file, signature, publicKey);
        }
        // Verify the checksum
        else if (checksum != null) {
            verifyChecksum(file, checksum);
        }
    }

    /**
     * Verifies the zip file or the manifest of an address with the marker: the checksum always,
     * and the signature as well if a public key is configured.
     */
    private void verifyFirstProtocolFile(@NonNull File file, @NonNull DownloadUrl downloadUrl) throws Exception {
        verifyChecksum(file, downloadUrl.getChecksum());
        String publicKey = config.getPublicKey();
        if (publicKey != null) {
            verifySignature(file, downloadUrl.getSignature(), publicKey);
        }
    }

    private void verifySignature(@NonNull File file, @Nullable String signature, @NonNull String publicKey) throws Exception {
        if (signature == null) {
            throw new Exception(LiveUpdatePlugin.ERROR_SIGNATURE_MISSING);
        }
        boolean verified = verifySignatureForFile(file, signature, publicKey);
        if (!verified) {
            throw new Exception(LiveUpdatePlugin.ERROR_SIGNATURE_VERIFICATION_FAILED);
        }
    }

    private boolean verifySignatureForFile(@NonNull File file, @NonNull String signature, @NonNull String keyAsString) throws Exception {
        PublicKey key = createPublicKeyFromString(keyAsString);
        return verifySignatureForFile(file, signature, key);
    }

    private boolean verifySignatureForFile(@NonNull File file, @NonNull String signature, @NonNull PublicKey key) throws Exception {
        try {
            byte[] signatureBytes = Base64.decode(signature, Base64.DEFAULT);
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(key);
            BufferedSource source = Okio.buffer(Okio.source(file));
            Buffer buffer = new Buffer();
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1; ) {
                sig.update(buffer.readByteArray());
            }
            source.close();
            return sig.verify(signatureBytes);
        } catch (Exception exception) {
            Logger.error(LiveUpdatePlugin.TAG, exception.getMessage(), exception);
            throw new Exception(LiveUpdatePlugin.ERROR_SIGNATURE_VERIFICATION_FAILED);
        }
    }

    private PackageInfo getPackageInfo() throws PackageManager.NameNotFoundException {
        String packageName = this.context.getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return this.context.getPackageManager().getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));
        } else {
            return this.context.getPackageManager().getPackageInfo(packageName, 0);
        }
    }
}
