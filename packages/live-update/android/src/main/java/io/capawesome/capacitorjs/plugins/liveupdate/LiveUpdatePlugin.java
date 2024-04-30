package io.capawesome.capacitorjs.plugins.liveupdate;

import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DeleteBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.DownloadBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetBundleOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetChannelOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.classes.options.SetCustomIdOptions;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.liveupdate.interfaces.Result;

@CapacitorPlugin(name = "LiveUpdate")
public class LiveUpdatePlugin extends Plugin {

    public static final String TAG = "LiveUpdate";
    public static final String VERSION = "6.0.4";
    public static final String SHARED_PREFERENCES_NAME = "CapawesomeLiveUpdate"; // DO NOT CHANGE
    public static final String ERROR_APP_ID_MISSING = "appId must be configured.";
    public static final String ERROR_BUNDLE_EXISTS = "bundle already exists.";
    public static final String ERROR_BUNDLE_ID_MISSING = "bundleId must be provided.";
    public static final String ERROR_BUNDLE_INDEX_HTML_MISSING = "The bundle does not contain an index.html file.";
    public static final String ERROR_BUNDLE_NOT_FOUND = "bundle not found.";
    public static final String ERROR_CUSTOM_ID_MISSING = "customId must be provided.";
    public static final String ERROR_DOWNLOAD_FAILED = "Bundle could not be downloaded.";
    public static final String ERROR_URL_MISSING = "url must be provided.";

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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.deleteBundle(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void downloadBundle(PluginCall call) {
        try {
            String url = call.getString("url");
            if (url == null) {
                call.reject(ERROR_URL_MISSING);
                return;
            }
            String bundleId = call.getString("bundleId");
            if (bundleId == null) {
                call.reject(ERROR_BUNDLE_ID_MISSING);
                return;
            }

            DownloadBundleOptions options = new DownloadBundleOptions(bundleId, url);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.downloadBundle(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getBundle(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getBundles(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getChannel(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getCustomId(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getDeviceId(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getVersionCode(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.getVersionName(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void ready(PluginCall call) {
        try {
            implementation.ready();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void reload(PluginCall call) {
        try {
            implementation.reload();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void reset(PluginCall call) {
        try {
            implementation.reset();
            call.resolve();
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.setBundle(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.setChannel(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.setCustomId(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
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
            NonEmptyCallback<Result> callback = new NonEmptyCallback<>() {
                @Override
                public void success(Result result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.sync(callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    private LiveUpdateConfig getLiveUpdateConfig() {
        LiveUpdateConfig config = new LiveUpdateConfig();

        String appId = getConfig().getString("appId", config.getAppId());
        config.setAppId(appId);
        boolean autoDeleteBundles = getConfig().getBoolean("autoDeleteBundles", config.getAutoDeleteBundles());
        config.setAutoDeleteBundles(autoDeleteBundles);
        boolean enabled = getConfig().getBoolean("enabled", config.getEnabled());
        config.setEnabled(enabled);
        int readyTimeout = getConfig().getInt("readyTimeout", config.getReadyTimeout());
        config.setReadyTimeout(readyTimeout);
        boolean resetOnUpdate = getConfig().getBoolean("resetOnUpdate", config.getResetOnUpdate());
        config.setResetOnUpdate(resetOnUpdate);

        return config;
    }
}
