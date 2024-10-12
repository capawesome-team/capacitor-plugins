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
import com.getcapacitor.Logger;
import com.getcapacitor.plugin.WebView;

import io.capawesome.capacitorjs.plugins.liveupdate.classes.Manifest;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.api.GetLatestBundleResponse;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.ManifestItem;
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
import io.capawesome.capacitorjs.plugins.liveupdate.enums.ArtifactType;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveUpdate {

    @NonNull
    private final LiveUpdateConfig config;
    private final String defaultWebAssetDir;
    @NonNull
    private final String host;
    @NonNull
    private final LiveUpdateHttpClient httpClient;
    @NonNull
    private final LiveUpdatePlugin plugin;
    @NonNull
    private final LiveUpdatePreferences preferences;
    @NonNull
    private final SharedPreferences.Editor webViewSettingsEditor;
    private final String bundlesDirectory = "_capacitor_live_update_bundles"; // Do NOT change this value!
    private final Handler rollbackHandler = new Handler();
    private final String manifestFileName = "capawesome-live-update-manifest.json";

    @Nullable
    private List<String> assetsList;

    public LiveUpdate(@NonNull LiveUpdateConfig config, @NonNull LiveUpdatePlugin plugin) throws PackageManager.NameNotFoundException {
        this.config = config;
        this.defaultWebAssetDir = plugin.getBridge().DEFAULT_WEB_ASSET_DIR;
        if (config.getLocation() != null && config.getLocation().equals("eu")) {
            this.host = "paths-annotation-von-order.trycloudflare.com";
        } else {
            this.host = "api.cloud.capawesome.io";
        }
        this.httpClient = new LiveUpdateHttpClient(config);
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

        try {
            loadAssetsList();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        String checksum = options.getChecksum();
        String url = options.getUrl();

        // Check if the bundle already exists
        if (hasBundle(bundleId)) {
            Exception exception = new Exception(LiveUpdatePlugin.ERROR_BUNDLE_EXISTS);
            callback.error(exception);
            return;
        }

        // Download the bundle
        downloadBundleOfTypeZip(bundleId, url, checksum);
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
        String channel = getChannel();
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
        GetLatestBundleResponse result = fetchLatestBundle();
        ArtifactType artifactType = result.getArtifactType();
        String downloadUrl = result.getDownloadUrl();
        String latestBundleId = result.getId();
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
        // Download the bundle
        if (artifactType == ArtifactType.ZIP) {
            downloadBundleOfTypeZip(latestBundleId, downloadUrl, null);
        } else {
            downloadBundleOfTypeManifest(latestBundleId);
        }
        // Set the next bundle
        setNextBundle(latestBundleId);
        SyncResult syncResult = new SyncResult(latestBundleId);
        callback.success(syncResult);
    }

    private void addBundle(@NonNull String bundleId, @NonNull File file) throws Exception {
        // Search folder with index.html file
        File indexHtmlFile = searchIndexHtmlFile(file);
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

    private void addBundleOfTypeZip(@NonNull String bundleId, @NonNull File file) throws Exception {
        // Unzip the file to the bundle directory
        File unzippedDirectory = unzipFile(file);

        // Add the bundle
        addBundle(bundleId, unzippedDirectory);
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

    private File buildBundleDirectoryFor(@NonNull String bundleId) {
        return new File(plugin.getContext().getFilesDir(), bundlesDirectory + "/" + bundleId);
    }

    private void copyCurrentBundleFile(@NonNull String href, @NonNull File directory) throws IOException {
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId.equals(defaultWebAssetDir)) {
            // Create the source input stream
            AssetManager assets = plugin.getContext().getAssets();
            InputStream inputStream = assets.open(defaultWebAssetDir + "/" + href);
            // Create the destination file
            File destination = new File(directory, href);
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(inputStream, destination);
        } else {
            File currentBundleDirectory = buildBundleDirectoryFor(currentBundleId);
            // Create the source and destination files
            File source = new File(currentBundleDirectory, href);
            File destination = new File(directory, href);
            // Create all destination directories if they do not exist
            destination.getParentFile().mkdirs();
            // Copy the file
            copyFile(source, destination);
        }
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

    private void deleteBundle(@NonNull String bundleId) {
        // Delete the bundle directory
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        deleteFileRecursively(bundleDirectory);
        // Reset the next bundle if it is the deleted bundle
        String currentBundleId = getCurrentBundleId();
        String nextBundleId = getNextBundleId();
        if (bundleId.equals(currentBundleId) && bundleId.equals(nextBundleId)) {
            setNextCapacitorServerPathToDefaultWebAssetDir();
        }
    }

    private void deleteFileIfExists(@NonNull File file) {
        if (file.exists()) {
            file.delete();
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
        String[] bundleIds = getBundleIds();
        for (String bundleId : bundleIds) {
            if (!isBundleInUse(bundleId)) {
                deleteBundle(bundleId);
            }
        }
    }

    private File downloadBundleFile(@NonNull String bundleId, @NonNull String href, @NonNull File directory) throws Exception {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://" + host + "/v1/apps/" + config.getAppId() + "/bundles/" + bundleId + "/download").newBuilder();
        urlBuilder.addQueryParameter("href", href);
        String url = urlBuilder.build().toString();

        File file = new File(directory, href);
        file.getParentFile().mkdirs();
        downloadAndVerifyFile(url, file, null);
        return file;
    }

    private void downloadBundleOfTypeManifest(@NonNull String bundleId) throws Exception {
        // Create a temporary directory
        File directory = createTemporaryDirectory();
        // Download the latest manifest
        File latestManifestFile = downloadBundleFile(bundleId, manifestFileName, directory);
        Manifest latestManifest = loadManifest(latestManifestFile);
        // Load the current manifest
        Manifest currentManifest = loadCurrentManifest();
        // Compare the manifests
        List<ManifestItem> itemsToCopy = Manifest.findDuplicateItems(latestManifest, currentManifest);
        List<ManifestItem> itemsToDownload = Manifest.findMissingItems(latestManifest, currentManifest);
        // Copy the files
        for (ManifestItem item : itemsToCopy) {
            String href = item.getHref();
            copyCurrentBundleFile(href, directory);
        }
        // Download the files
        for (ManifestItem item : itemsToDownload) {
            String href = item.getHref();
            downloadBundleFile(bundleId, href, directory);
        }
        // Add the bundle
        addBundleOfTypeManifest(bundleId, directory);
    }

    private void downloadBundleOfTypeZip(@NonNull String bundleId, @NonNull String url, @Nullable String checksum) throws Exception {
        File file = buildTemporaryZipFile();
        // Download the bundle
        downloadAndVerifyFile(url, file, checksum);
        // Add the bundle
        addBundleOfTypeZip(bundleId, file);
        // Delete the temporary file
        file.delete();
    }

    private void downloadAndVerifyFile(@NonNull String url, @NonNull File file, @Nullable String checksum) throws Exception {
        Response response = httpClient.execute(url);
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            LiveUpdateHttpClient.writeResponseBodyToFile(responseBody, file);
            // Verify the file
            checksum = checksum == null ? LiveUpdateHttpClient.getChecksumFromResponse(response) : checksum;
            String publicKey = config.getPublicKey();
            if (publicKey != null) {
                // Verify the signature
                String signature = LiveUpdateHttpClient.getSignatureFromResponse(response);
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
        } else {
            throw new Exception(response.message());
        }
    }

    @Nullable
    private GetLatestBundleResponse fetchLatestBundle() throws Exception {
        String url = new HttpUrl.Builder()
            .scheme("https")
            .host(host)
            .addPathSegment("v1")
            .addPathSegment("apps")
            .addPathSegment(config.getAppId())
            .addPathSegment("bundles")
            .addPathSegment("latest")
            .addQueryParameter("appVersionCode", getVersionCode())
            .addQueryParameter("appVersionName", getVersionName())
            .addQueryParameter("bundleId", getCurrentBundleId())
            .addQueryParameter("channelName", getChannel())
            .addQueryParameter("customId", preferences.getCustomId())
            .addQueryParameter("deviceId", getDeviceId())
            .addQueryParameter("osVersion", String.valueOf(Build.VERSION.SDK_INT))
            .addQueryParameter("platform", "0")
            .addQueryParameter("pluginVersion", LiveUpdatePlugin.VERSION)
            .build()
            .toString();
        Response response = httpClient.execute(url);
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String responseBodyString = responseBody.string();
            JSONObject responseJson = new JSONObject(responseBodyString);
            return new GetLatestBundleResponse(responseJson);
        } else {
            throw new Exception(response.message());
        }
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

    private String getChecksumForInputStreamAsString(@NonNull InputStream inputStream) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            BufferedSource source = Okio.buffer(Okio.source(inputStream));
            Buffer buffer = new Buffer();
            for (long bytesRead; (bytesRead = source.read(buffer, 2048)) != -1;) {
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

    @Nullable
    private String getChannel() {
        String channel = null;
        if (config.getDefaultChannel() != null) {
            channel = config.getDefaultChannel();
        }
        if (preferences.getChannel() != null) {
            channel = preferences.getChannel();
        }
        return channel;
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

    private String getVersionCode() throws PackageManager.NameNotFoundException {
        return String.valueOf(getPackageInfo().versionCode);
    }

    private String getVersionName() throws PackageManager.NameNotFoundException {
        return getPackageInfo().versionName;
    }

    private boolean hasBundle(@NonNull String bundleId) {
        File bundleDirectory = buildBundleDirectoryFor(bundleId);
        return bundleDirectory.exists();
    }

    private void loadAssetsList() throws Exception {
        long startTime = System.currentTimeMillis();
        List<String> assetsList = loadAssetsListRecursively("");
        long difference = System.currentTimeMillis() - startTime;
        Logger.debug(LiveUpdatePlugin.TAG, "Loaded assets list in " + difference + "ms.");
        this.assetsList = assetsList;
    }

    private Manifest loadCurrentManifest() throws Exception {
        AssetManager assets = plugin.getContext().getAssets();
        InputStream inputStream = assets.open(defaultWebAssetDir + "/" + manifestFileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        return loadManifest(source);
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

    private List<String> loadAssetsListRecursively(@NonNull String path) throws Exception {
        List<String> assetsList = new ArrayList<>();
        String[] assets = plugin.getContext().getAssets().list(path);
        for (String asset : assets) {
            String assetPath = path.length() == 0 ? asset : path + "/" + asset;
            if (asset.contains(".")) {
                assetsList.add(assetPath);
                AssetManager assetManager = plugin.getContext().getAssets();
                InputStream inputStream = assetManager.open(assetPath);
                String checksum = getChecksumForInputStreamAsString(inputStream);
            } else {
                assetsList.addAll(loadAssetsListRecursively(assetPath));
            }
        }
        return assetsList;
    }

    private boolean hasCurrentBundleFile(@NonNull String href, @NonNull String expectedChecksum) {
        String currentBundleId = getCurrentBundleId();
        if (currentBundleId.equals(defaultWebAssetDir)) {
            return assetsList.contains(defaultWebAssetDir + "/" + href);
        } else {
            File currentBundleDirectory = buildBundleDirectoryFor(currentBundleId);
            File file = new File(currentBundleDirectory, href);
            boolean fileExists = file.exists();
            if (!fileExists) {
                return false;
            }
            try {
                String receivedChecksum = getChecksumForFileAsString(file);
                return expectedChecksum.equals(receivedChecksum);
            } catch (Exception exception) {
                return false;
            }
        }
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

    private boolean verifyChecksumForFile(@NonNull File file, @NonNull String checksum) throws Exception {
        String receivedChecksum = getChecksumForFileAsString(file);
        return checksum.equals(receivedChecksum);
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
