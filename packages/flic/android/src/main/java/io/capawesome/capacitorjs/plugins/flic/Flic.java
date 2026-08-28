package io.capawesome.capacitorjs.plugins.flic;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.capawesome.capacitorjs.plugins.flic.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonConnectedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonConnectionFailedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonDisconnectedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonReadyEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ButtonUnpairedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.events.ScanStatusChangedEvent;
import io.capawesome.capacitorjs.plugins.flic.classes.options.ConnectButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.options.DisconnectButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.options.ForgetButtonByIdOptions;
import io.capawesome.capacitorjs.plugins.flic.classes.results.GetButtonsResult;
import io.capawesome.capacitorjs.plugins.flic.classes.results.StartScanResult;
import io.capawesome.capacitorjs.plugins.flic.enums.ScanStatus;
import io.capawesome.capacitorjs.plugins.flic.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.flic.interfaces.NonEmptyResultCallback;
import io.flic.flic2libandroid.Flic2Button;
import io.flic.flic2libandroid.Flic2ButtonListener;
import io.flic.flic2libandroid.Flic2Manager;
import io.flic.flic2libandroid.Flic2ScanCallback;

public class Flic {

    @NonNull
    private final Flic2ButtonListener buttonListener = new Flic2ButtonListener() {
        @Override
        public void onConnect(Flic2Button button) {
            plugin.notifyButtonConnectedListeners(new ButtonConnectedEvent(button.getBdAddr()));
        }

        @Override
        public void onReady(Flic2Button button, long timestamp) {
            plugin.notifyButtonReadyListeners(new ButtonReadyEvent(button.getBdAddr()));
        }

        @Override
        public void onDisconnect(Flic2Button button) {
            plugin.notifyButtonDisconnectedListeners(new ButtonDisconnectedEvent(button.getBdAddr()));
        }

        @Override
        public void onUnpaired(Flic2Button button) {
            plugin.notifyButtonUnpairedListeners(new ButtonUnpairedEvent(button.getBdAddr()));
        }

        @Override
        public void onFailure(Flic2Button button, int errorCode, int subCode) {
            plugin.notifyButtonConnectionFailedListeners(
                new ButtonConnectionFailedEvent(button.getBdAddr(), Flic2Manager.errorCodeToString(errorCode))
            );
        }

        @Override
        public void onButtonUpOrDown(
            Flic2Button button,
            boolean wasQueued,
            boolean lastQueued,
            long timestamp,
            boolean isUp,
            boolean isDown
        ) {
            ButtonEvent event = createButtonEvent(button, wasQueued, timestamp);
            if (isDown) {
                plugin.notifyButtonDownListeners(event);
            }
            if (isUp) {
                plugin.notifyButtonUpListeners(event);
            }
        }

        @Override
        public void onButtonSingleOrDoubleClickOrHold(
            Flic2Button button,
            boolean wasQueued,
            boolean lastQueued,
            long timestamp,
            boolean isSingleClick,
            boolean isDoubleClick,
            boolean isHold
        ) {
            ButtonEvent event = createButtonEvent(button, wasQueued, timestamp);
            if (isSingleClick) {
                plugin.notifyButtonSingleClickListeners(event);
            }
            if (isDoubleClick) {
                plugin.notifyButtonDoubleClickListeners(event);
            }
            if (isHold) {
                plugin.notifyButtonHoldListeners(event);
            }
        }
    };

    @NonNull
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    private Flic2Manager manager;

    @NonNull
    private final FlicPlugin plugin;

    @Nullable
    private NonEmptyResultCallback<StartScanResult> scanCallback;

    public Flic(@NonNull FlicPlugin plugin) {
        this.plugin = plugin;
    }

    public void connectButtonById(@NonNull ConnectButtonByIdOptions options, @NonNull EmptyCallback callback) {
        handler.post(() -> {
            try {
                Flic2Button button = getButtonByIdOrThrow(options.getId());
                button.connect();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void disconnectButtonById(@NonNull DisconnectButtonByIdOptions options, @NonNull EmptyCallback callback) {
        handler.post(() -> {
            try {
                Flic2Button button = getButtonByIdOrThrow(options.getId());
                button.disconnectOrAbortPendingConnection();
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void forgetButtonById(@NonNull ForgetButtonByIdOptions options, @NonNull EmptyCallback callback) {
        handler.post(() -> {
            try {
                Flic2Button button = getButtonByIdOrThrow(options.getId());
                getManagerOrThrow().forgetButton(button);
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void getButtons(@NonNull NonEmptyResultCallback<GetButtonsResult> callback) {
        handler.post(() -> {
            try {
                GetButtonsResult result = new GetButtonsResult(getManagerOrThrow().getButtons());
                callback.success(result);
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void initialize(@NonNull EmptyCallback callback) {
        handler.post(() -> {
            try {
                if (manager == null) {
                    manager = Flic2Manager.initAndGetInstance(
                        plugin.getContext().getApplicationContext(),
                        new Handler(Looper.getMainLooper())
                    );
                    for (Flic2Button button : manager.getButtons()) {
                        button.addListener(buttonListener);
                    }
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void startScan(@NonNull NonEmptyResultCallback<StartScanResult> callback) {
        handler.post(() -> {
            try {
                Flic2Manager manager = getManagerOrThrow();
                if (scanCallback != null) {
                    throw CustomExceptions.SCAN_ALREADY_RUNNING;
                }
                scanCallback = callback;
                try {
                    manager.startScan(
                        new Flic2ScanCallback() {
                            @Override
                            public void onDiscoveredAlreadyPairedButton(Flic2Button button) {}

                            @Override
                            public void onDiscovered(String bdAddr) {
                                plugin.notifyScanStatusChangedListeners(new ScanStatusChangedEvent(ScanStatus.DISCOVERED));
                            }

                            @Override
                            public void onConnected() {
                                plugin.notifyScanStatusChangedListeners(new ScanStatusChangedEvent(ScanStatus.CONNECTED));
                            }

                            @Override
                            public void onAskToAcceptPairRequest() {
                                plugin.notifyScanStatusChangedListeners(new ScanStatusChangedEvent(ScanStatus.ASK_TO_ACCEPT_PAIR_REQUEST));
                            }

                            @Override
                            public void onComplete(int result, int subCode, Flic2Button button) {
                                NonEmptyResultCallback<StartScanResult> savedCallback = scanCallback;
                                scanCallback = null;
                                if (savedCallback == null) {
                                    return;
                                }
                                if (result == Flic2ScanCallback.RESULT_SUCCESS) {
                                    button.addListener(buttonListener);
                                    savedCallback.success(new StartScanResult(button));
                                } else {
                                    savedCallback.error(new Exception(Flic2Manager.errorCodeToString(result)));
                                }
                            }
                        }
                    );
                } catch (Exception exception) {
                    scanCallback = null;
                    throw exception;
                }
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    public void stopScan(@NonNull EmptyCallback callback) {
        handler.post(() -> {
            try {
                getManagerOrThrow().stopScan();
                NonEmptyResultCallback<StartScanResult> savedCallback = scanCallback;
                scanCallback = null;
                if (savedCallback != null) {
                    savedCallback.error(CustomExceptions.SCAN_STOPPED);
                }
                callback.success();
            } catch (Exception exception) {
                callback.error(exception);
            }
        });
    }

    @NonNull
    private ButtonEvent createButtonEvent(@NonNull Flic2Button button, boolean wasQueued, long timestamp) {
        long eventTimestamp = System.currentTimeMillis();
        if (wasQueued) {
            eventTimestamp -= Math.max(0, button.getReadyTimestamp() - timestamp);
        }
        return new ButtonEvent(button.getBdAddr(), eventTimestamp, wasQueued);
    }

    @NonNull
    private Flic2Button getButtonByIdOrThrow(@NonNull String id) throws Exception {
        for (Flic2Button button : getManagerOrThrow().getButtons()) {
            if (button.getBdAddr().equalsIgnoreCase(id)) {
                return button;
            }
        }
        throw CustomExceptions.BUTTON_NOT_FOUND;
    }

    @NonNull
    private Flic2Manager getManagerOrThrow() throws Exception {
        if (manager == null) {
            throw CustomExceptions.NOT_INITIALIZED;
        }
        return manager;
    }
}
