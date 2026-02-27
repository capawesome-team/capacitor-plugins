package io.capawesome.capacitorjs.plugins.liveupdate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Handler;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Bridge;
import com.getcapacitor.Logger;
import com.getcapacitor.plugin.WebView;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.Manifest;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.ManifestItem;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.GetLatestBundleResponse;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.DownloadBundleProgressEvent;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.events.NextBundleSetEvent;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DeleteBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchLatestBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetChannelOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetConfigOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetCustomIdOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetNextBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SyncOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.FetchLatestBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBlockedBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetChannelResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetConfigResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCurrentBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCustomIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDefaultChannelResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDeviceIdResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetDownloadedBundlesResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetNextBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionCodeResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetVersionNameResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.IsSyncingResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.ReadyResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.SyncResult;
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.DownloadProgressCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import net.lingala.zip4j.ZipFile;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import org.json.JSONArray;
import org.json.JSONObject;

public class LiveUpdate {

    private final long autoUpdateIntervalMs = 15 * 60 * 1000; // 15 minutes

    @NonNull
    private final LiveUpdateConfig config;

    private final String defaultWebAssetDir = Bridge.DEFAULT_WEB_ASSET_DIR;

    @NonNull
    private final LiveUpdateHttpClient httpClient;

    @NonNull
    private final LiveUpdatePlugin plugin;

    @NonNull
    private final LiveUpdatePreferences preferences;

    @NonNull
    private final SharedPreferences.Editor webViewSettingsEditor;

    private final String bundlesDirectory = "_capacitor_live_update_bundles"; // DO NOT CHANGE!
    private final Handler rollbackHandler = new Handler();
    private final String manifestFileName = "capawesome-live-update-manifest.json"; // DO NOT CHANGE!

    private boolean initialPageLoaded = false;
    private long lastAutoUpdateCheckTimestamp = 0;
    private boolean rollbackPerformed = false;
    private boolean syncInProgress = false;

    public LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull LiveUpdatePlugin plugin) throws PackageManager.NameNotFoundException {
        this.config = config;
        this.httpClient = new LiveUpdateHttpClient(config);
        this.plugin = plugin;
        this.preferences = new LiveUpdatePreferences(plugin.getContext());
        this.webViewSettingsEditor = plugin.getContext().getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE).edit();

        // Check version and reset config if version changed
        checkAndResetConfigIfVersionChanged();

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
        ArtifactType artifactType = options.getArtifactType();
        String bundleId = options.getBundleId();
        String checksum = options.getChecksum();
        String signature = options.getSignature();
        String url = options.getUrl();

        // Check if the bundle already exists
        if (hasBundleById(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_EXISTS);
            callback.error(exception);
            return;
        }

        // Download the bundle
        if (artifactType == ArtifactType.MANIFEST) {
            downloadBundleOfTypeManifest(bundleId, url, callback);
        } else {
            downloadBundleOfTypeZip(bundleId, checksum, signature, url, callback);
        }
    }

    public void fetchLatestBundle(@NonNull FetchLatestBundleOptions options, @NonNull NonEmptyCallback<FetchLatestBundleResult> callback) {
        fetchLatestBundleInternal(
            options,
            new NonEmptyCallback<GetLatestBundleResponse>() {
                @Override
                public void success(@Nullable GetLatestBundleResponse response) {
                    ArtifactType artifactType = response == null ? null : response.getArtifactType();
                    String bundleId = response == null ? null : response.getBundleId();
                    String checksum = response == null ? null : response.getChecksum();
                    JSONObject customProperties = response == null ? null : response.getCustomProperties();
                    String downloadUrl = response == null ? null : response.getUrl();
                    String signature = response == null ? null : response.getSignature();
                    FetchLatestBundleResult result = new FetchLatestBundleResult(
                        artifactType,
                        bundleId,
                        checksum,
                        customProperties,
                        downloadUrl,
                        signature
                    );
                    callback.success(result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    callback.error(exception);
                }
            }
        );
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

    public void getCustomId(@NonNull NonEmptyCallback callback) {
        String customId = preferences.getCustomId();
        GetCustomIdResult result = new GetCustomIdResult(customId);
        callback.success(result);
    }

    public void getDefaultChannel(@NonNull NonEmptyCallback<GetDefaultChannelResult> callback) {
        String channel = getDefaultChannel();
        GetDefaultChannelResult result = new GetDefaultChannelResult(channel);
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

        String channel = options.getChannel();
        // Fetch the latest bundle
        FetchLatestBundleOptions fetchLatestBundleOptions = new FetchLatestBundleOptions(channel);
        fetchLatestBundleInternal(
            fetchLatestBundleOptions,
            new NonEmptyCallback<GetLatestBundleResponse>() {
                @Override
                public void success(@Nullable GetLatestBundleResponse response) {
                    try {
                        if (response == null) {
                            Logger.debug(LiveUpdatePlugin.TAG, "No update available.");
                            syncInProgress = false;
                            SyncResult syncResult = new SyncResult(null);
                            callback.success(syncResult);
                            return;
                        }
                        ArtifactType artifactType = response.getArtifactType();
                        String latestBundleId = response.getBundleId();
                        String checksum = response.getChecksum();
                        String signature = response.getSignature();
                        String url = response.getUrl();
                        // Check if the bundle is blocked
                        if (isBlockedBundleId(latestBundleId)) {
                            Logger.warn(LiveUpdatePlugin.TAG, "Bundle is blocked and will not be downloaded.");
                            syncInProgress = false;
                            SyncResult syncResult = new SyncResult(null);
                            callback.success(syncResult);
                            return;
                        }
                        // Check if the bundle already exists
                        if (hasBundleById(latestBundleId)) {
                            String nextBundleId = null;
                            String currentBundleId = getCurrentBundleId();
                            if (!latestBundleId.equals(currentBundleId)) {
                                // Set the next bundle
                                setNextBundleById(latestBundleId);
                                nextBundleId = latestBundleId;
                            }
                            syncInProgress = false;
                            SyncResult syncResult = new SyncResult(nextBundleId);
                            callback.success(syncResult);
                            return;
                        }

                        // Download the bundle
                        EmptyCallback downloadCallback = new EmptyCallback() {
                            @Override
                            public void success() {
                                try {
                                    // Set the next bundle
                                    setNextBundleById(latestBundleId);
                                    syncInProgress = false;
                                    SyncResult syncResult = new SyncResult(latestBundleId);
                                    callback.success(syncResult);
                                } catch (Exception e) {
                                    syncInProgress = false;
                                    callback.error(e);
                                }
                            }

                            @Override
                            public void error(@NonNull Exception exception) {
                                syncInProgress = false;
                                callback.error(exception);
                            }
                        };

                        if (artifactType == ArtifactType.MANIFEST) {
                            downloadBundleOfTypeManifest(latestBundleId, url, downloadCallback);
                        } else {
                            downloadBundleOfTypeZip(latestBundleId, checksum, signature, url, downloadCallback);
                        }
                    } catch (Exception e) {
                        syncInProgress = false;
                        callback.error(e);
                    }
                }

                @Override
                public void error(@NonNull Exception exception) {
                    syncInProgress = false;
                    callback.error(exception);
                }
            }
        );
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
        indexHtmlFile.getParentFile().renameTo(bundleDirectory);
    }

    private void addBundleOfTypeManifest(@NonNull String bundleId, @NonNull File directory) throws Exception {
        addBundle(bundleId, directory);
    }

    private void addBundleOfTypeZip(@NonNull String bundleId, @NonNull File zipFile) throws Exception {
        // Unzip the file to the bundle directory
        File unzippedDirectory = unzipFile(zipFile);
        // Add the bundle
        addBundle(bundleId, unzippedDirectory);
    }

    private File buildBundlesDirectory() {
        return new File(plugin.getContext().getFilesDir(), bundlesDirectory);
    }

    private File buildBundleDirectoryFor(@NonNull String bundleId) {
        return new File(plugin.getContext().getFilesDir(), bundlesDirectory + "/" + bundleId);
    }

    private File buildTemporaryDirectory() {
        String fileName = UUID.randomUUID().toString();
        return new File(plugin.getContext().getCacheDir(), fileName);
    }

    private File buildTemporaryZipFile() {
        String fileName = UUID.randomUUID().toString() + ".zip";
        return new File(plugin.getContext().getCacheDir(), fileName);
    }

    private void copyCurrentBundleFile(@NonNull ManifestItem fileToCopy, @NonNull File destinationDirectory) throws IOException {
        String href = fileToCopy.getHref();
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId == null) {
            // Create the source input stream
            AssetManager assets = plugin.getContext().getAssets();
            InputStream inputStream = assets.open(defaultWebAssetDir + "/" + href);
            // Create the destination file
            File destination = new File(destinationDirectory, href);
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(inputStream, destination);
        } else {
            File currentBundleDirectory = buildBundleDirectoryFor(currentBundleId);
            // Create the source and destination files
            File source = new File(currentBundleDirectory, href);
            File destination = new File(destinationDirectory, href);
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(source, destination);
        }
    }

    private List<ManifestItem> copyCurrentBundleFilesAndReturnFailures(
        @NonNull List<ManifestItem> filesToCopy,
        @NonNull File destinationDirectory
    ) {
        List<ManifestItem> missingItems = new ArrayList<>();
        for (ManifestItem fileToCopy : filesToCopy) {
            boolean success = tryCopyCurrentBundleFile(fileToCopy, destinationDirectory);
            if (!success) {
                Logger.warn(LiveUpdatePlugin.TAG, "Failed to copy file: " + fileToCopy.getHref());
                // If the file could not be copied, add it to the list of missing items
                missingItems.add(fileToCopy);
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

    private File createTemporaryDirectory() {
        File file = buildTemporaryDirectory();
        file.mkdir();
        return file;
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
            for (File child : file.listFiles()) {
                deleteFileRecursively(child);
            }
        }
        file.delete();
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
        String urlWithDeviceId = HttpUrl.parse(url).newBuilder().addQueryParameter("deviceId", getDeviceId()).build().toString();
        return httpClient.enqueue(
            urlWithDeviceId,
            new NonEmptyCallback<Response>() {
                @Override
                public void success(@NonNull Response response) {
                    try {
                        if (response.isSuccessful()) {
                            ResponseBody responseBody = response.body();
                            LiveUpdateHttpClient.writeResponseBodyToFile(responseBody, file, progressCallback);

                            // Extract checksum/signature from headers
                            String finalChecksum = checksum == null ? LiveUpdateHttpClient.getChecksumFromResponse(response) : checksum;
                            String finalSignature = signature == null ? LiveUpdateHttpClient.getSignatureFromResponse(response) : signature;

                            // Verify file
                            verifyFile(file, finalChecksum, finalSignature);
                            completionCallback.success();
                        } else {
                            String errorMessage = response.body().string();
                            Exception exception = new Exception(LiveUpdatePlugin.ERROR_DOWNLOAD_FAILED);
                            Logger.error(LiveUpdatePlugin.TAG, errorMessage, exception);
                            completionCallback.error(exception);
                        }
                    } catch (Exception e) {
                        completionCallback.error(e);
                    }
                }

                @Override
                public void error(@NonNull Exception exception) {
                    completionCallback.error(exception);
                }
            }
        );
    }

    private Call downloadBundleFile(
        @NonNull String baseUrl,
        @NonNull String href,
        @NonNull File destinationDirectory,
        @Nullable DownloadProgressCallback progressCallback,
        @NonNull EmptyCallback completionCallback
    ) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        urlBuilder.addQueryParameter("href", href);
        String url = urlBuilder.build().toString();

        File destinationFile = new File(destinationDirectory, href);
        destinationFile.getParentFile().mkdirs();
        return downloadAndVerifyFile(url, destinationFile, null, null, progressCallback, completionCallback);
    }

    private void downloadBundleFiles(
        @NonNull String baseUrl,
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
                baseUrl,
                item.getHref(),
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
        @NonNull String downloadUrl,
        @NonNull EmptyCallback completionCallback
    ) {
        try {
            // Create a temporary directory
            File temporaryDirectory = createTemporaryDirectory();

            // Download the latest manifest
            downloadBundleFile(
                downloadUrl,
                manifestFileName,
                temporaryDirectory,
                null,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        try {
                            File latestManifestFile = new File(temporaryDirectory, manifestFileName);
                            Manifest latestManifest = loadManifest(latestManifestFile);
                            // Load the current manifest
                            Manifest currentManifest = loadCurrentManifest();
                            // Compare the manifests
                            List<ManifestItem> itemsToCopy = new ArrayList<>();
                            List<ManifestItem> itemsToDownload = new ArrayList<>();
                            if (currentManifest == null) {
                                itemsToDownload.addAll(latestManifest.getItems());
                            } else {
                                itemsToCopy.addAll(Manifest.findDuplicateItems(latestManifest, currentManifest));
                                itemsToDownload.addAll(Manifest.findMissingItems(latestManifest, currentManifest));
                            }
                            // Copy the files
                            List<ManifestItem> missingItems = copyCurrentBundleFilesAndReturnFailures(itemsToCopy, temporaryDirectory);
                            // If items could not be copied, add them to the list of items to download
                            if (!missingItems.isEmpty()) {
                                itemsToDownload.addAll(missingItems);
                            }

                            // Download the files
                            downloadBundleFiles(
                                downloadUrl,
                                itemsToDownload,
                                temporaryDirectory,
                                (downloadedBytes, totalBytes) -> {
                                    DownloadBundleProgressEvent event = new DownloadBundleProgressEvent(
                                        bundleId,
                                        downloadedBytes,
                                        totalBytes
                                    );
                                    notifyDownloadBundleProgressListeners(event);
                                },
                                new EmptyCallback() {
                                    @Override
                                    public void success() {
                                        try {
                                            // Add the bundle
                                            addBundleOfTypeManifest(bundleId, temporaryDirectory);
                                            completionCallback.success();
                                        } catch (Exception e) {
                                            completionCallback.error(e);
                                        }
                                    }

                                    @Override
                                    public void error(@NonNull Exception exception) {
                                        completionCallback.error(exception);
                                    }
                                }
                            );
                        } catch (Exception e) {
                            completionCallback.error(e);
                        }
                    }

                    @Override
                    public void error(@NonNull Exception exception) {
                        completionCallback.error(exception);
                    }
                }
            );
        } catch (Exception e) {
            completionCallback.error(e);
        }
    }

    private void downloadBundleOfTypeZip(
        @NonNull String bundleId,
        @Nullable String checksum,
        @Nullable String signature,
        @NonNull String downloadUrl,
        @NonNull EmptyCallback completionCallback
    ) {
        File file = buildTemporaryZipFile();
        // Download the bundle
        downloadAndVerifyFile(
            downloadUrl,
            file,
            checksum,
            signature,
            (downloadedBytes, totalBytes) -> {
                DownloadBundleProgressEvent event = new DownloadBundleProgressEvent(bundleId, downloadedBytes, totalBytes);
                notifyDownloadBundleProgressListeners(event);
            },
            new EmptyCallback() {
                @Override
                public void success() {
                    try {
                        // Add the bundle
                        addBundleOfTypeZip(bundleId, file);
                        // Delete the temporary file
                        file.delete();
                        completionCallback.success();
                    } catch (Exception e) {
                        completionCallback.error(e);
                    }
                }

                @Override
                public void error(@NonNull Exception exception) {
                    // Delete the temporary file on error
                    file.delete();
                    completionCallback.error(exception);
                }
            }
        );
    }

    private void fetchLatestBundleInternal(
        @NonNull FetchLatestBundleOptions options,
        @NonNull NonEmptyCallback<GetLatestBundleResponse> callback
    ) {
        try {
            String channel = options.getChannel() == null ? getChannel() : options.getChannel();
            String url = new HttpUrl.Builder()
                .scheme("https")
                .host(config.getServerDomain())
                .addPathSegment("v1")
                .addPathSegment("apps")
                .addPathSegment(getAppId())
                .addPathSegment("bundles")
                .addPathSegment("latest")
                .addQueryParameter("appVersionCode", getVersionCodeAsString())
                .addQueryParameter("appVersionName", getVersionName())
                .addQueryParameter("bundleId", getCurrentBundleId())
                .addQueryParameter("channelName", channel)
                .addQueryParameter("customId", preferences.getCustomId())
                .addQueryParameter("deviceId", getDeviceId())
                .addQueryParameter("osVersion", String.valueOf(Build.VERSION.SDK_INT))
                .addQueryParameter("platform", "0")
                .addQueryParameter("pluginVersion", LiveUpdatePlugin.VERSION)
                .build()
                .toString();
            Logger.debug(LiveUpdatePlugin.TAG, "Fetching latest bundle: " + url);

            httpClient.enqueue(
                url,
                new NonEmptyCallback<Response>() {
                    @Override
                    public void success(@NonNull Response response) {
                        try {
                            String responseBodyString = response.body().string();
                            Logger.debug(LiveUpdatePlugin.TAG, "Latest bundle response: " + responseBodyString);
                            if (response.isSuccessful()) {
                                JSONObject responseJson = new JSONObject(responseBodyString);
                                GetLatestBundleResponse result = new GetLatestBundleResponse(responseJson);
                                callback.success(result);
                            } else {
                                callback.success(null);
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
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1;) {
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
    private String getAppId() {
        String appId = preferences.getAppId();
        if (appId != null) {
            return appId;
        }
        return config.getAppId();
    }

    @Nullable
    private String getChannel() {
        String channel = getDefaultChannel();
        if (preferences.getChannel() != null) {
            channel = preferences.getChannel();
        }
        return channel;
    }

    @Nullable
    private String getDefaultChannel() {
        String channel = null;
        if (config.getDefaultChannel() != null) {
            channel = config.getDefaultChannel();
        }
        String nativeChannel = getNativeChannel();
        if (nativeChannel != null) {
            channel = nativeChannel;
        }
        return channel;
    }

    @Nullable
    private String getNativeChannel() {
        int resId = plugin
            .getContext()
            .getResources()
            .getIdentifier("capawesome_live_update_default_channel", "string", plugin.getContext().getPackageName());
        if (resId == 0) {
            return null;
        }
        return plugin.getContext().getResources().getString(resId);
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
        return plugin.getBridge().getServerBasePath();
    }

    @NonNull
    private String getDeviceId() {
        String deviceId = preferences.getDeviceIdForApp(getAppId());
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString().toLowerCase();
            preferences.setDeviceIdForApp(getAppId(), deviceId);
        }
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
        String path = plugin
            .getContext()
            .getSharedPreferences(WebView.WEBVIEW_PREFS_NAME, Activity.MODE_PRIVATE)
            .getString(WebView.CAP_SERVER_PATH, defaultWebAssetDir);
        // Empty path means default path
        if (path.isEmpty()) {
            path = defaultWebAssetDir;
        }
        return path;
    }

    /**
     * @return The previous bundle ID or `null` if the default bundle was used.
     */
    @Nullable
    private String getPreviousBundleId() {
        return preferences.getPreviousBundleId();
    }

    private int getVersionCodeAsInt() throws PackageManager.NameNotFoundException {
        return getPackageInfo().versionCode;
    }

    private String getVersionCodeAsString() throws PackageManager.NameNotFoundException {
        return String.valueOf(getVersionCodeAsInt());
    }

    private String getVersionName() throws PackageManager.NameNotFoundException {
        return getPackageInfo().versionName;
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

    @Nullable
    private Manifest loadCurrentManifest() throws Exception {
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId == null) {
            AssetManager assets = plugin.getContext().getAssets();
            boolean manifestFileExists = Arrays.asList(assets.list(defaultWebAssetDir)).contains(manifestFileName);
            if (manifestFileExists) {
                InputStream inputStream = assets.open(defaultWebAssetDir + "/" + manifestFileName);
                BufferedSource source = Okio.buffer(Okio.source(inputStream));
                return loadManifest(source);
            } else {
                return null;
            }
        } else {
            File currentBundleDirectory = buildBundleDirectoryFor(currentBundleId);
            File manifestFile = new File(currentBundleDirectory, manifestFileName);
            if (manifestFile.exists()) {
                return loadManifest(manifestFile);
            } else {
                return null;
            }
        }
    }

    private Manifest loadManifest(@NonNull BufferedSource source) throws Exception {
        String jsonAsString = source.readUtf8();
        JSONArray jsonArray = new JSONArray(jsonAsString);
        return new Manifest(jsonArray);
    }

    private Manifest loadManifest(@NonNull File file) throws Exception {
        BufferedSource source = Okio.buffer(Okio.source(file));
        return loadManifest(source);
    }

    private void notifyDownloadBundleProgressListeners(@NonNull final DownloadBundleProgressEvent event) {
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
            // Rollback to the default bundle
            setNextBundleById(null);
            setCurrentBundleById(null);
        }
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

    private void notifyNextBundleSetListeners(@Nullable String bundleId) {
        NextBundleSetEvent event = new NextBundleSetEvent(bundleId);
        plugin.notifyNextBundleSetListeners(event);
    }

    private void notifyReloadedListeners() {
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

    private void startRollbackTimer() {
        if (config.getReadyTimeout() <= 0) {
            return;
        }
        stopRollbackTimer();
        rollbackHandler.postDelayed(() -> rollback(), config.getReadyTimeout());
    }

    private void stopRollbackTimer() {
        rollbackHandler.removeCallbacksAndMessages(null);
    }

    private boolean tryCopyCurrentBundleFile(@NonNull ManifestItem fileToCopy, @NonNull File destinationDirectory) {
        try {
            copyCurrentBundleFile(fileToCopy, destinationDirectory);
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private File unzipFile(@NonNull File zipFile) throws IOException {
        File destination = buildTemporaryDirectory();
        String destinationPath = destination.getPath();
        new ZipFile(zipFile).extractAll(destinationPath);
        return destination;
    }

    private boolean verifyChecksumForFile(@NonNull File file, @NonNull String checksum) throws Exception {
        String receivedChecksum = getChecksumForFileAsString(file);
        return checksum.equals(receivedChecksum);
    }

    private void verifyFile(@NonNull File file, @Nullable String checksum, @Nullable String signature) throws Exception {
        String publicKey = config.getPublicKey();
        if (publicKey != null) {
            // Verify the signature
            if (signature == null) {
                throw new Exception(LiveUpdatePlugin.ERROR_SIGNATURE_MISSING);
            }
            // Verify the signature
            boolean verified = verifySignatureForFile(file, signature, publicKey);
            if (!verified) {
                throw new Exception(LiveUpdatePlugin.ERROR_SIGNATURE_VERIFICATION_FAILED);
            }
        }
        // Verify the checksum
        else if (checksum != null) {
            // Calculate the checksum
            boolean verified = verifyChecksumForFile(file, checksum);
            if (!verified) {
                throw new Exception(LiveUpdatePlugin.ERROR_CHECKSUM_MISMATCH);
            }
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
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1;) {
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
        String packageName = this.plugin.getContext().getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return this.plugin.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));
        } else {
            return this.plugin.getContext().getPackageManager().getPackageInfo(packageName, 0);
        }
    }
}
