package io.capawesome.capacitorjs.plugins.squaremobilepayments;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.*;

@CapacitorPlugin(
    name = "SquareMobilePayments",
    permissions = {
        @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = SquareMobilePaymentsPlugin.PERMISSION_LOCATION),
        @Permission(strings = { Manifest.permission.RECORD_AUDIO }, alias = SquareMobilePaymentsPlugin.PERMISSION_RECORD_AUDIO),
        @Permission(strings = { Manifest.permission.BLUETOOTH_CONNECT }, alias = SquareMobilePaymentsPlugin.PERMISSION_BLUETOOTH_CONNECT),
        @Permission(strings = { Manifest.permission.BLUETOOTH_SCAN }, alias = SquareMobilePaymentsPlugin.PERMISSION_BLUETOOTH_SCAN),
        @Permission(strings = { Manifest.permission.READ_PHONE_STATE }, alias = SquareMobilePaymentsPlugin.PERMISSION_READ_PHONE_STATE)
    }
)
public class SquareMobilePaymentsPlugin extends Plugin {

    public static final String TAG = "SquareMobilePayments";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error occurred.";
    public static final String PERMISSION_LOCATION = "location";
    public static final String PERMISSION_RECORD_AUDIO = "recordAudio";
    public static final String PERMISSION_BLUETOOTH_CONNECT = "bluetoothConnect";
    public static final String PERMISSION_BLUETOOTH_SCAN = "bluetoothScan";
    public static final String PERMISSION_READ_PHONE_STATE = "readPhoneState";

    private SquareMobilePayments implementation;

    @Override
    public void load() {
        implementation = new SquareMobilePayments(this);
        implementation.setReaderChangedCallback();
        implementation.setAvailableCardInputMethodsCallback();
    }

    @Override
    protected void handleOnDestroy() {
        if (implementation != null) {
            implementation.clearReaderChangedCallback();
            implementation.clearAvailableCardInputMethodsCallback();
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        try {
            InitializeOptions options = new InitializeOptions(call);
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
            implementation.initialize(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void authorize(PluginCall call) {
        try {
            AuthorizeOptions options = new AuthorizeOptions(call);
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
            implementation.authorize(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isAuthorized(PluginCall call) {
        try {
            NonEmptyResultCallback<IsAuthorizedResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsAuthorizedResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.isAuthorized(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void deauthorize(PluginCall call) {
        try {
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
            implementation.deauthorize(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void showSettings(PluginCall call) {
        try {
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
            implementation.showSettings(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getSettings(PluginCall call) {
        try {
            NonEmptyResultCallback<GetSettingsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetSettingsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getSettings(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startPairing(PluginCall call) {
        try {
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
            implementation.startPairing(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopPairing(PluginCall call) {
        try {
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
            implementation.stopPairing(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isPairingInProgress(PluginCall call) {
        try {
            NonEmptyResultCallback<IsPairingInProgressResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull IsPairingInProgressResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.isPairingInProgress(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getReaders(PluginCall call) {
        try {
            NonEmptyResultCallback<GetReadersResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetReadersResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getReaders(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void forgetReader(PluginCall call) {
        try {
            ForgetReaderOptions options = new ForgetReaderOptions(call);
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
            implementation.forgetReader(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void retryConnection(PluginCall call) {
        try {
            RetryConnectionOptions options = new RetryConnectionOptions(call);
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
            implementation.retryConnection(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startPayment(PluginCall call) {
        try {
            StartPaymentOptions options = new StartPaymentOptions(call);
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
            implementation.startPayment(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void cancelPayment(PluginCall call) {
        try {
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
            implementation.cancelPayment(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAvailableCardInputMethods(PluginCall call) {
        try {
            NonEmptyResultCallback<GetAvailableCardInputMethodsResult> callback = new NonEmptyResultCallback<>() {
                @Override
                public void success(@NonNull GetAvailableCardInputMethodsResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(@NonNull Exception exception) {
                    rejectCall(call, exception);
                }
            };

            assert implementation != null;
            implementation.getAvailableCardInputMethods(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void checkPermissions(PluginCall call) {
        super.checkPermissions(call);
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        super.requestPermissions(call);
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
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

    public void notifyReaderPairingDidBeginListeners() {
        notifyListeners("readerPairingDidBegin", new JSObject());
    }

    public void notifyReaderPairingDidSucceedListeners() {
        notifyListeners("readerPairingDidSucceed", new JSObject());
    }

    public void notifyReaderPairingDidFailListeners(@NonNull ReaderPairingDidFailEvent event) {
        notifyListeners("readerPairingDidFail", event.toJSObject());
    }

    public void notifyReaderWasAddedListeners(@NonNull ReaderWasAddedEvent event) {
        notifyListeners("readerWasAdded", event.toJSObject());
    }

    public void notifyReaderWasRemovedListeners(@NonNull ReaderWasRemovedEvent event) {
        notifyListeners("readerWasRemoved", event.toJSObject());
    }

    public void notifyReaderDidChangeListeners(@NonNull ReaderDidChangeEvent event) {
        notifyListeners("readerDidChange", event.toJSObject());
    }

    public void notifyAvailableCardInputMethodsDidChangeListeners(@NonNull AvailableCardInputMethodsDidChangeEvent event) {
        notifyListeners("availableCardInputMethodsDidChange", event.toJSObject());
    }

    public void notifyPaymentDidFinishListeners(@NonNull PaymentDidFinishEvent event) {
        notifyListeners("paymentDidFinish", event.toJSObject());
    }

    public void notifyPaymentDidFailListeners(@NonNull PaymentDidFailEvent event) {
        notifyListeners("paymentDidFail", event.toJSObject());
    }

    public void notifyPaymentDidCancelListeners(@NonNull PaymentDidCancelEvent event) {
        notifyListeners("paymentDidCancel", event.toJSObject());
    }
}
