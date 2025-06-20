package io.capawesome.capacitorjs.plugins.libsql;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.libsql.classes.options.*;
import io.capawesome.capacitorjs.plugins.libsql.classes.results.*;
import io.capawesome.capacitorjs.plugins.libsql.interfaces.*;

@CapacitorPlugin(name = "Libsql")
public class LibsqlPlugin extends Plugin {

    public static final String TAG = "Libsql";
    private static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";

    private Libsql implementation;

    @Override
    public void load() {
        implementation = new Libsql(this);
    }

    @PluginMethod
    public void connect(PluginCall call) {
        try {
            ConnectOptions options = new ConnectOptions(call);
            NonEmptyResultCallback<ConnectResult> callback = new NonEmptyResultCallback<ConnectResult>() {
                @Override
                public void success(@NonNull ConnectResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.connect(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void execute(PluginCall call) {
        try {
            ExecuteOptions options = new ExecuteOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.execute(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void executeBatch(PluginCall call) {
        try {
            ExecuteBatchOptions options = new ExecuteBatchOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.executeBatch(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void query(PluginCall call) {
        try {
            QueryOptions options = new QueryOptions(call);
            NonEmptyResultCallback<QueryResult> callback = new NonEmptyResultCallback<QueryResult>() {
                @Override
                public void success(@NonNull QueryResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.query(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void beginTransaction(PluginCall call) {
        try {
            BeginTransactionOptions options = new BeginTransactionOptions(call);
            NonEmptyResultCallback<BeginTransactionResult> callback = new NonEmptyResultCallback<BeginTransactionResult>() {
                @Override
                public void success(@NonNull BeginTransactionResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.beginTransaction(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void commitTransaction(PluginCall call) {
        try {
            CommitTransactionOptions options = new CommitTransactionOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.commitTransaction(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void rollbackTransaction(PluginCall call) {
        try {
            RollbackTransactionOptions options = new RollbackTransactionOptions(call);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.rollbackTransaction(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void sync(PluginCall call) {
        rejectCallAsUnavailable(call);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Log.e(TAG, message, exception);
        call.reject(message);
    }

    private void rejectCallAsUnavailable(@NonNull PluginCall call) {
        call.unimplemented("Not available on this platform.");
    }

    private void resolveCall(@NonNull PluginCall call) {
        call.resolve();
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }
}