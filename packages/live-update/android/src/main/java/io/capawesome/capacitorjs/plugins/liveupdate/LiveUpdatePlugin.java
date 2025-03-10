package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DeleteBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.FetchLatestBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetChannelOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetCustomIdOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetNextBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SyncOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetCurrentBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.results.GetNextBundleResult;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

@CapacitorPlugin(name = "LiveUpdate")
public class LiveUpdatePlugin extends Plugin {

    public static final String TAG = "LiveUpdate";
    public static final String VERSION = "6.8.0";
    public static final String SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdate"; // DO NOT CHANGE
    public static final String ERROR_APP_ID_MISSING = "appId must be configured.";
    public static final String ERROR_BUNDLE_EXISTS = "bundle already exists.";
    public static final String ERROR_BUNDLE_ID_MISSING = "bundleId must be provided.";
    public static final String ERROR_BUNDLE_INDEX_HTML_MISSING = "The bundle does not contain an index.html file.";
    public static final String ERROR_BUNDLE_NOT_FOUND = "bundle not found.";
    public static final String ERROR_CHECKSUM_CALCULATION_FAILED = "Failed to calculate checksum.";
    public static final String ERROR_CHECKSUM_MISMATCH = "Checksum mismatch.";
    public static final String ERROR_CUSTOM_ID_MISSING = "customId must be provided.";
    public static final String ERROR_DOWNLOAD_FAILED = "Bundle could not be downloaded.";
    public static final String ERROR_HTTP_TIMEOUT = "Request timed out.";
    public static final String ERROR_URL_MISSING = "url must be provided.";
    public static final String ERROR_SIGNATURE_VERIFICATION_FAILED = "Signature verification failed.";
    public static final String ERROR_PUBLIC_KEY_INVALID = "Invalid public key.";
    public static final String ERROR_SIGNATURE_MISSING = "Bundle does not contain a signature.";
    public static final String ERROR_SYNC_IN_PROGRESS = "Sync is already in progress.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";

    private boolean syncInProgress = false;

    @Nullable
    private LiveUpdateConfig config;

    @Nullable
    private LiveUpdate implementation;

    public void load() {
        try {
            config = getLiveUpdateConfig();
            implementation = new LiveUpdate(config, this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void deleteBundle(PluginCall call) {
        try {
            String bundleId = call.getString("bundleId");
            if (bundleId == null) {
                call.reject(ERROR_BUNDLE_ID_MISSING);
                return;
            }

            DeleteBundleOptions options = new DeleteBundleOptions(bundleId);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.deleteBundle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void downloadBundle(PluginCall call) {
        try {
            String artifactType = call.getString("artifactType", "zip");
            String bundleId = call.getString("bundleId");
            if (bundleId == null) {
                call.reject(ERROR_BUNDLE_ID_MISSING);
                return;
            }
            String checksum = call.getString("checksum");
            String signature = call.getString("signature");
            String url = call.getString("url");
            if (url == null) {
                call.reject(ERROR_URL_MISSING);
                return;
            }

            DownloadBundleOptions options = new DownloadBundleOptions(artifactType, bundleId, checksum, signature, url);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.downloadBundle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void fetchLatestBundle(PluginCall call) {
        try {
            FetchLatestBundleOptions options = new FetchLatestBundleOptions(call);
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.fetchLatestBundle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getBundle(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getBundle(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getBundles(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getBundles(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getChannel(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getChannel(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCurrentBundle(PluginCall call) {
        try {
            NonEmptyCallback<GetCurrentBundleResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetCurrentBundleResult result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getCurrentBundle(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getCustomId(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getCustomId(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getDeviceId(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getDeviceId(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getNextBundle(PluginCall call) {
        try {
            NonEmptyCallback<GetNextBundleResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetNextBundleResult result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getNextBundle(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getVersionCode(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getVersionCode(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getVersionName(PluginCall call) {
        try {
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.getVersionName(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void ready(PluginCall call) {
        try {
            implementation.ready();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void reload(PluginCall call) {
        try {
            implementation.reload();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void reset(PluginCall call) {
        try {
            implementation.reset();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setBundle(PluginCall call) {
        try {
            String bundleId = call.getString("bundleId");
            if (bundleId == null) {
                call.reject(ERROR_BUNDLE_ID_MISSING);
                return;
            }

            SetBundleOptions options = new SetBundleOptions(bundleId);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setBundle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setChannel(PluginCall call) {
        try {
            String channel = call.getString("channel");

            SetChannelOptions options = new SetChannelOptions(channel);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setChannel(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setCustomId(PluginCall call) {
        try {
            String customId = call.getString("customId");
            if (customId == null) {
                call.reject(ERROR_CUSTOM_ID_MISSING);
                return;
            }

            SetCustomIdOptions options = new SetCustomIdOptions(customId);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setCustomId(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void setNextBundle(PluginCall call) {
        try {
            SetNextBundleOptions options = new SetNextBundleOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.setNextBundle(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sync(PluginCall call) {
        try {
            String appId = config.getAppId();
            if (appId == null) {
                call.reject(ERROR_APP_ID_MISSING);
                return;
            }

            if (syncInProgress) {
                call.reject(ERROR_SYNC_IN_PROGRESS);
                return;
            }
            syncInProgress = true;

            SyncOptions options = new SyncOptions(call);
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    syncInProgress = false;
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    syncInProgress = false;
                    rejectCall(call, exception);
                }
            };

            implementation.sync(options, callback);
        } catch (Exception exception) {
            syncInProgress = false;
            rejectCall(call, exception);
        }
    }

    private LiveUpdateConfig getLiveUpdateConfig() {
        LiveUpdateConfig config = new LiveUpdateConfig();

        String appId = getConfig().getString("appId", config.getAppId());
        config.setAppId(appId);
        boolean autoDeleteBundles = getConfig().getBoolean("autoDeleteBundles", config.getAutoDeleteBundles());
        config.setAutoDeleteBundles(autoDeleteBundles);
        String defaultChannel = getConfig().getString("defaultChannel", config.getDefaultChannel());
        config.setDefaultChannel(defaultChannel);
        boolean enabled = getConfig().getBoolean("enabled", config.getEnabled());
        config.setEnabled(enabled);
        int httpTimeout = getConfig().getInt("httpTimeout", config.getHttpTimeout());
        config.setHttpTimeout(httpTimeout);
        String location = getConfig().getString("location", config.getLocation());
        config.setLocation(location);
        String publicKey = getConfig().getString("publicKey", config.getPublicKey());
        config.setPublicKey(publicKey);
        int readyTimeout = getConfig().getInt("readyTimeout", config.getReadyTimeout());
        config.setReadyTimeout(readyTimeout);
        boolean resetOnUpdate = getConfig().getBoolean("resetOnUpdate", config.getResetOnUpdate());
        config.setResetOnUpdate(resetOnUpdate);

        return config;
    }

    private void rejectCall(PluginCall call, Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof java.net.SocketTimeoutException) {
            message = ERROR_HTTP_TIMEOUT;
        } else {
            message = message == null ? ERROR_UNKNOWN_ERROR : message;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
