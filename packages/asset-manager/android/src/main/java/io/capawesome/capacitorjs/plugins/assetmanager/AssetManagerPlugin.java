package io.capawesome.capacitorjs.plugins.assetmanager;

import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.CopyOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.ListOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.options.ReadOptions;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.results.ListResult;
import io.capawesome.capacitorjs.plugins.assetmanager.classes.results.ReadResult;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.assetmanager.interfaces.NonEmptyCallback;

@CapacitorPlugin(name = "AssetManager")
public class AssetManagerPlugin extends Plugin {

    public static final String ERROR_FROM_MISSING = "from must be provided.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_TO_MISSING = "to must be provided.";
    public static final String TAG = "AssetManager";

    @Nullable
    private AssetManager implementation;

    public void load() {
        try {
            implementation = new AssetManager(this);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void copy(PluginCall call) {
        try {
            CopyOptions options = new CopyOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.copy(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void list(PluginCall call) {
        try {
            ListOptions options = new ListOptions(call);
            NonEmptyCallback<ListResult> callback = new NonEmptyCallback<ListResult>() {
                @Override
                public void success(ListResult result) {
                    resolveCall(call, result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.list(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void read(PluginCall call) {
        try {
            ReadOptions options = new ReadOptions(call);
            NonEmptyCallback<ReadResult> callback = new NonEmptyCallback<ReadResult>() {
                @Override
                public void success(ReadResult result) {
                    resolveCall(call, result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };
            assert implementation != null;
            implementation.read(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    private void resolveCall(PluginCall call) {
        call.resolve();
    }

    private void resolveCall(PluginCall call, JSObject result) {
        call.resolve(result);
    }

    private void rejectCall(PluginCall call, Exception exception) {
        String message = exception.getMessage();
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
