package io.capawesome.capacitorjs.plugins.squaremobilepayments;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.squareup.sdk.mobilepayments.MobilePaymentsSdk;
import com.squareup.sdk.mobilepayments.authorization.AuthorizationManager;
import com.squareup.sdk.mobilepayments.cardreader.CardEntryMethod;
import com.squareup.sdk.mobilepayments.cardreader.ReaderChangedEvent;
import com.squareup.sdk.mobilepayments.cardreader.ReaderInfo;
import com.squareup.sdk.mobilepayments.cardreader.ReaderManager;
import com.squareup.sdk.mobilepayments.core.CallbackReference;
import com.squareup.sdk.mobilepayments.payment.AdditionalPaymentMethod;
import com.squareup.sdk.mobilepayments.payment.Card;
import com.squareup.sdk.mobilepayments.payment.CardPaymentDetails;
import com.squareup.sdk.mobilepayments.payment.CurrencyCode;
import com.squareup.sdk.mobilepayments.payment.DelayAction;
import com.squareup.sdk.mobilepayments.payment.Money;
import com.squareup.sdk.mobilepayments.payment.Payment;
import com.squareup.sdk.mobilepayments.payment.PaymentHandle;
import com.squareup.sdk.mobilepayments.payment.PaymentManager;
import com.squareup.sdk.mobilepayments.payment.PaymentParameters;
import com.squareup.sdk.mobilepayments.payment.ProcessingMode;
import com.squareup.sdk.mobilepayments.payment.PromptMode;
import com.squareup.sdk.mobilepayments.payment.PromptParameters;
import com.squareup.sdk.mobilepayments.settings.Environment;
import com.squareup.sdk.mobilepayments.settings.SdkSettings;
import com.squareup.sdk.mobilepayments.settings.SettingsManager;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.events.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.*;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SquareMobilePayments {

    @NonNull
    private final SquareMobilePaymentsPlugin plugin;

    private boolean isInitialized = false;
    private boolean isAuthorized = false;

    @Nullable
    private Object pairingHandle;

    @Nullable
    private PaymentHandle paymentHandle;

    @Nullable
    private CallbackReference readerChangedCallbackReference;

    @Nullable
    private CallbackReference availableCardInputMethodsCallbackReference;

    public SquareMobilePayments(@NonNull SquareMobilePaymentsPlugin plugin) {
        this.plugin = plugin;
    }

    private String locationId;

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        try {
            this.locationId = options.getLocationId();
            Application application = (Application) plugin.getContext().getApplicationContext();

            MobilePaymentsSdk.initialize(locationId, application);

            isInitialized = true;
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void authorize(@NonNull AuthorizeOptions options, @NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            String accessToken = options.getAccessToken();

            AuthorizationManager authManager = MobilePaymentsSdk.authorizationManager();
            authManager.authorize(accessToken, locationId, result -> {
                if (result.isSuccess()) {
                    isAuthorized = true;
                    callback.success();
                } else {
                    callback.error(new Exception(result.errorMessage()));
                }
            });
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void isAuthorized(@NonNull NonEmptyResultCallback<IsAuthorizedResult> callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            AuthorizationManager authManager = MobilePaymentsSdk.authorizationManager();
            boolean authorized = authManager.getAuthorizationState().isAuthorized();

            IsAuthorizedResult result = new IsAuthorizedResult(authorized);
            callback.success(result);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void deauthorize(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            AuthorizationManager authManager = MobilePaymentsSdk.authorizationManager();
            authManager.deauthorize();
            isAuthorized = false;
            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void showSettings(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }
            if (!isAuthorized) {
                throw CustomExceptions.NOT_AUTHORIZED;
            }

            SettingsManager settingsManager = MobilePaymentsSdk.settingsManager();
            settingsManager.showSettings(result -> {
                if (result.isSuccess()) {
                    callback.success();
                } else {
                    callback.error(new Exception(result.errorMessage()));
                }
                return null;
            });
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getSettings(@NonNull NonEmptyResultCallback<GetSettingsResult> callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            SettingsManager settingsManager = MobilePaymentsSdk.settingsManager();
            SdkSettings sdkSettings = settingsManager.getSdkSettings();

            String version = sdkSettings.getSdkVersion();
            String environment = convertSdkEnvironmentToString(sdkSettings.getSdkEnvironment());

            GetSettingsResult result = new GetSettingsResult(version, environment);
            callback.success(result);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void startPairing(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }
            if (!isAuthorized) {
                throw CustomExceptions.NOT_AUTHORIZED;
            }

            ReaderManager readerManager = MobilePaymentsSdk.readerManager();

            if (readerManager.isPairingInProgress()) {
                throw CustomExceptions.PAIRING_ALREADY_IN_PROGRESS;
            }

            // Notify that pairing has begun
            plugin.notifyReaderPairingDidBeginListeners();

            pairingHandle = readerManager.pairReader(result -> {
                if (result.isSuccess()) {
                    // Notify that pairing succeeded
                    plugin.notifyReaderPairingDidSucceedListeners();
                } else {
                    // Notify that pairing failed
                    ReaderPairingDidFailEvent event = new ReaderPairingDidFailEvent(
                        result.errorCode() != null ? result.errorCode().toString() : null,
                        result.errorMessage()
                    );
                    plugin.notifyReaderPairingDidFailListeners(event);
                }
                pairingHandle = null;
            });

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void stopPairing(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            if (pairingHandle != null) {
                try {
                    pairingHandle.getClass().getMethod("stop").invoke(pairingHandle);
                } catch (Exception e) {
                    // Ignore reflection errors
                }
                pairingHandle = null;
            }

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void isPairingInProgress(@NonNull NonEmptyResultCallback<IsPairingInProgressResult> callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            ReaderManager readerManager = MobilePaymentsSdk.readerManager();
            boolean inProgress = readerManager.isPairingInProgress();

            IsPairingInProgressResult result = new IsPairingInProgressResult(inProgress);
            callback.success(result);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getReaders(@NonNull NonEmptyResultCallback<GetReadersResult> callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            ReaderManager readerManager = MobilePaymentsSdk.readerManager();
            List<ReaderInfo> sdkReaders = readerManager.getReaders();

            List<io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo> readers = new ArrayList<>();
            for (ReaderInfo sdkReader : sdkReaders) {
                io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo reader = convertSdkReaderToReaderInfo(
                    sdkReader
                );
                readers.add(reader);
            }

            GetReadersResult result = new GetReadersResult(readers);
            callback.success(result);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void forgetReader(@NonNull ForgetReaderOptions options, @NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            String serialNumber = options.getSerialNumber();

            ReaderManager readerManager = MobilePaymentsSdk.readerManager();
            ReaderInfo sdkReader = findReaderBySerialNumber(serialNumber);

            if (sdkReader == null) {
                throw CustomExceptions.READER_NOT_FOUND;
            }

            readerManager.forget(sdkReader);

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void retryConnection(@NonNull RetryConnectionOptions options, @NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            String serialNumber = options.getSerialNumber();

            ReaderManager readerManager = MobilePaymentsSdk.readerManager();
            com.squareup.sdk.mobilepayments.cardreader.ReaderInfo sdkReader = findReaderBySerialNumber(serialNumber);

            if (sdkReader == null) {
                throw CustomExceptions.READER_NOT_FOUND;
            }

            readerManager.retryConnection(sdkReader);

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void startPayment(@NonNull StartPaymentOptions options, @NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }
            if (!isAuthorized) {
                throw CustomExceptions.NOT_AUTHORIZED;
            }

            io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options.PaymentParameters params =
                options.getPaymentParameters();
            io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options.PromptParameters promptParams =
                options.getPromptParameters();

            PaymentManager paymentManager = MobilePaymentsSdk.paymentManager();

            // Build Money object
            Money amountMoney = new Money(params.getAmountMoney().getAmount(), CurrencyCode.valueOf(params.getAmountMoney().getCurrency()));

            // Determine processing mode
            ProcessingMode processingMode = ProcessingMode.AUTO_DETECT;
            if (params.getProcessingMode() != null) {
                processingMode = ProcessingMode.valueOf(params.getProcessingMode());
            }

            // Determine autocomplete
            boolean autocomplete = params.getAutocomplete() != null ? params.getAutocomplete() : true;

            // Build PaymentParameters
            PaymentParameters.Builder paramsBuilder = new PaymentParameters.Builder(
                amountMoney,
                params.getPaymentAttemptId(),
                processingMode,
                false // allowCardSurcharge - not supported yet
            );

            paramsBuilder.autocomplete(autocomplete);

            if (params.getReferenceId() != null) {
                paramsBuilder.referenceId(params.getReferenceId());
            }

            if (params.getNote() != null) {
                paramsBuilder.note(params.getNote());
            }

            if (params.getOrderId() != null) {
                paramsBuilder.orderId(params.getOrderId());
            }

            if (params.getTipMoney() != null) {
                Money tipMoney = new Money(params.getTipMoney().getAmount(), CurrencyCode.valueOf(params.getTipMoney().getCurrency()));
                paramsBuilder.tipMoney(tipMoney);
            }

            if (params.getApplicationFee() != null) {
                Money applicationFee = new Money(
                    params.getApplicationFee().getAmount(),
                    CurrencyCode.valueOf(params.getApplicationFee().getCurrency())
                );
                paramsBuilder.appFeeMoney(applicationFee);
            }

            if (params.getDelayDuration() != null) {
                Duration duration = Duration.parse(params.getDelayDuration());
                paramsBuilder.delayDuration(duration.toMillis());
            }

            if (params.getDelayAction() != null) {
                paramsBuilder.delayAction(DelayAction.valueOf(params.getDelayAction()));
            }

            PaymentParameters sdkPaymentParams = paramsBuilder.build();

            // Build PromptParameters
            PromptMode promptMode = PromptMode.DEFAULT;
            if (promptParams.getMode() != null) {
                promptMode = PromptMode.valueOf(promptParams.getMode());
            }

            List<AdditionalPaymentMethod.Type> additionalMethods = new ArrayList<>();
            for (String method : promptParams.getAdditionalMethods()) {
                additionalMethods.add(AdditionalPaymentMethod.Type.valueOf(method));
            }

            PromptParameters sdkPromptParams = new PromptParameters(promptMode, additionalMethods);

            // Start payment
            paymentHandle = paymentManager.startPaymentActivity(sdkPaymentParams, sdkPromptParams, result -> {
                if (result.isSuccess()) {
                    Payment sdkPayment = result.value();
                    io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment payment = convertSdkPaymentToPayment(
                        sdkPayment
                    );

                    PaymentDidFinishEvent event = new PaymentDidFinishEvent(payment);
                    plugin.notifyPaymentDidFinishListeners(event);
                } else {
                    PaymentDidFailEvent event = new PaymentDidFailEvent(
                        null,
                        result.errorCode() != null ? result.errorCode().toString() : null,
                        result.errorMessage()
                    );
                    plugin.notifyPaymentDidFailListeners(event);
                }
                paymentHandle = null;
            });

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void cancelPayment(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            if (paymentHandle != null) {
                paymentHandle.cancel();
                paymentHandle = null;

                PaymentDidCancelEvent event = new PaymentDidCancelEvent(null);
                plugin.notifyPaymentDidCancelListeners(event);
            } else {
                throw CustomExceptions.NO_PAYMENT_IN_PROGRESS;
            }

            callback.success();
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void getAvailableCardInputMethods(@NonNull NonEmptyResultCallback<GetAvailableCardInputMethodsResult> callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            PaymentManager paymentManager = MobilePaymentsSdk.paymentManager();
            Set<CardEntryMethod> methods = paymentManager.getAvailableCardEntryMethods();

            List<String> cardInputMethods = methods.stream().map(this::convertCardEntryMethodToString).collect(Collectors.toList());

            GetAvailableCardInputMethodsResult result = new GetAvailableCardInputMethodsResult(cardInputMethods);
            callback.success(result);
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void showMockReader(@NonNull EmptyCallback callback) {
        try {
            if (!isInitialized) {
                throw CustomExceptions.NOT_INITIALIZED;
            }

            if (!isAuthorized) {
                throw CustomExceptions.NOT_AUTHORIZED;
            }

            // Show MockReader UI - only available in Debug builds with mockreader-ui dependency
            // Use reflection to avoid compile-time dependency
            Class<?> mockReaderUIClass = Class.forName("com.squareup.sdk.mockreader.ui.MockReaderUI");
            mockReaderUIClass.getMethod("show").invoke(null);
            callback.success();
        } catch (ClassNotFoundException e) {
            callback.error(
                new Exception("MockReaderUI is only available in Debug builds. Please ensure you're using a Debug build configuration.")
            );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void hideMockReader(@NonNull EmptyCallback callback) {
        try {
            // Hide MockReader UI
            // Use reflection to avoid compile-time dependency
            Class<?> mockReaderUIClass = Class.forName("com.squareup.sdk.mockreader.ui.MockReaderUI");
            mockReaderUIClass.getMethod("hide").invoke(null);
            callback.success();
        } catch (ClassNotFoundException e) {
            callback.error(
                new Exception("MockReaderUI is only available in Debug builds. Please ensure you're using a Debug build configuration.")
            );
        } catch (Exception exception) {
            callback.error(exception);
        }
    }

    public void setReaderChangedCallback() {
        if (!isInitialized) {
            return;
        }

        ReaderManager readerManager = MobilePaymentsSdk.readerManager();

        readerChangedCallbackReference = readerManager.setReaderChangedCallback(event -> {
            com.squareup.sdk.mobilepayments.cardreader.ReaderInfo sdkReader = event.getReader();
            ReaderChangedEvent.Change change = event.getChange();

            io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo reader = convertSdkReaderToReaderInfo(
                sdkReader
            );

            // Notify based on change type
            if (change == ReaderChangedEvent.Change.ADDED) {
                ReaderWasAddedEvent addedEvent = new ReaderWasAddedEvent(reader);
                plugin.notifyReaderWasAddedListeners(addedEvent);
            } else if (change == ReaderChangedEvent.Change.REMOVED) {
                ReaderWasRemovedEvent removedEvent = new ReaderWasRemovedEvent(reader);
                plugin.notifyReaderWasRemovedListeners(removedEvent);
            }

            // Also notify general reader changed event
            ReaderDidChangeEvent didChangeEvent = new ReaderDidChangeEvent(reader, convertReaderChangeToString(change));
            plugin.notifyReaderDidChangeListeners(didChangeEvent);
        });
    }

    public void clearReaderChangedCallback() {
        if (readerChangedCallbackReference != null) {
            readerChangedCallbackReference.clear();
            readerChangedCallbackReference = null;
        }
    }

    public void setAvailableCardInputMethodsCallback() {
        if (!isInitialized) {
            return;
        }

        PaymentManager paymentManager = MobilePaymentsSdk.paymentManager();

        availableCardInputMethodsCallbackReference = paymentManager.setAvailableCardEntryMethodChangedCallback(methods -> {
            List<String> cardInputMethods = methods.stream().map(this::convertCardEntryMethodToString).collect(Collectors.toList());

            AvailableCardInputMethodsDidChangeEvent event = new AvailableCardInputMethodsDidChangeEvent(cardInputMethods);
            plugin.notifyAvailableCardInputMethodsDidChangeListeners(event);
        });
    }

    public void clearAvailableCardInputMethodsCallback() {
        if (availableCardInputMethodsCallbackReference != null) {
            availableCardInputMethodsCallbackReference.clear();
            availableCardInputMethodsCallbackReference = null;
        }
    }

    // Helper methods for converting SDK types to plugin types

    @Nullable
    private com.squareup.sdk.mobilepayments.cardreader.ReaderInfo findReaderBySerialNumber(@NonNull String serialNumber) {
        ReaderManager readerManager = MobilePaymentsSdk.readerManager();
        List<com.squareup.sdk.mobilepayments.cardreader.ReaderInfo> readers = readerManager.getReaders();

        for (com.squareup.sdk.mobilepayments.cardreader.ReaderInfo reader : readers) {
            if (reader.getSerialNumber().equals(serialNumber)) {
                return reader;
            }
        }

        return null;
    }

    @NonNull
    private io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo convertSdkReaderToReaderInfo(
        @NonNull com.squareup.sdk.mobilepayments.cardreader.ReaderInfo sdkReader
    ) {
        String serialNumber = sdkReader.getSerialNumber();
        String model = convertReaderModelToString(sdkReader.getModel());
        String status = convertReaderStatusToString(sdkReader.getStatus());
        String firmwareVersion = sdkReader.getFirmwareVersion();
        Integer batteryLevel = sdkReader.getBatteryStatus().getPercent();
        Boolean isCharging = sdkReader.getBatteryStatus().isCharging();

        List<String> supportedCardInputMethods = sdkReader
            .getSupportedCardEntryMethods()
            .stream()
            .map(this::convertCardEntryMethodToString)
            .collect(Collectors.toList());

        UnavailableReasonInfo unavailableReasonInfo = null;
        if (sdkReader.getStatus() instanceof ReaderInfo.Status.ReaderUnavailable) {
            ReaderInfo.Status.ReaderUnavailable unavailable = (ReaderInfo.Status.ReaderUnavailable) sdkReader.getStatus();
            unavailableReasonInfo = new UnavailableReasonInfo(convertUnavailableReasonToString(unavailable.getReason()), null);
        }

        return new io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo(
            serialNumber,
            model,
            status,
            firmwareVersion,
            batteryLevel,
            isCharging,
            supportedCardInputMethods,
            unavailableReasonInfo
        );
    }

    @NonNull
    private io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment convertSdkPaymentToPayment(
        @NonNull Payment sdkPayment
    ) {
        String id = null;
        String type;
        String status;
        io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.CardPaymentDetails cardDetails = null;

        if (sdkPayment instanceof Payment.OnlinePayment) {
            Payment.OnlinePayment onlinePayment = (Payment.OnlinePayment) sdkPayment;
            id = onlinePayment.getId();
            type = "ONLINE";
            status = convertPaymentStatusToString(onlinePayment.getStatus());

            // Extract card details
            com.squareup.sdk.mobilepayments.payment.CardPaymentDetails sdkCardDetails = onlinePayment.getCardDetails();
            if (sdkCardDetails != null) {
                cardDetails = convertSdkCardDetailsToCardPaymentDetails(sdkCardDetails);
            }
        } else if (sdkPayment instanceof Payment.OfflinePayment) {
            Payment.OfflinePayment offlinePayment = (Payment.OfflinePayment) sdkPayment;
            id = offlinePayment.getId();
            type = "OFFLINE";
            status = "PENDING";

            // Extract card details
            com.squareup.sdk.mobilepayments.payment.CardPaymentDetails sdkCardDetails = offlinePayment.getCardDetails();
            if (sdkCardDetails != null) {
                cardDetails = convertSdkCardDetailsToCardPaymentDetails(sdkCardDetails);
            }
        } else {
            type = "ONLINE";
            status = "PENDING";
        }

        MoneyResult amountMoney = new MoneyResult(
            (int) sdkPayment.getTotalMoney().getAmount(),
            sdkPayment.getTotalMoney().getCurrencyCode().toString()
        );

        MoneyResult tipMoney = sdkPayment.getTipMoney() != null
            ? new MoneyResult((int) sdkPayment.getTipMoney().getAmount(), sdkPayment.getTipMoney().getCurrencyCode().toString())
            : null;

        MoneyResult applicationFee = sdkPayment.getAppFeeMoney() != null
            ? new MoneyResult((int) sdkPayment.getAppFeeMoney().getAmount(), sdkPayment.getAppFeeMoney().getCurrencyCode().toString())
            : null;

        return new io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment(
            id,
            type,
            status,
            amountMoney,
            tipMoney,
            applicationFee,
            sdkPayment.getReferenceId(),
            sdkPayment.getOrderId(),
            cardDetails,
            sdkPayment.getCreatedAt() != null ? sdkPayment.getCreatedAt().toString() : null,
            sdkPayment.getUpdatedAt() != null ? sdkPayment.getUpdatedAt().toString() : null
        );
    }

    @NonNull
    private io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.CardPaymentDetails convertSdkCardDetailsToCardPaymentDetails(
        @NonNull com.squareup.sdk.mobilepayments.payment.CardPaymentDetails sdkCardDetails
    ) {
        Card sdkCard = sdkCardDetails.getCard();

        io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Card card =
            new io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Card(
                convertCardBrandToString(sdkCard.getBrand()),
                sdkCard.getLastFourDigits(),
                sdkCard.getCardholderName(),
                sdkCard.getExpirationMonth(),
                sdkCard.getExpirationYear()
            );

        String entryMethod = sdkCardDetails.getEntryMethod() != null ? sdkCardDetails.getEntryMethod().toString() : "KEYED";

        return new io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.CardPaymentDetails(card, entryMethod);
    }

    @NonNull
    private String convertSdkEnvironmentToString(@NonNull Environment environment) {
        return environment.toString().toLowerCase();
    }

    @NonNull
    private String convertReaderModelToString(@NonNull ReaderInfo.Model model) {
        return model.toString();
    }

    @NonNull
    private String convertReaderStatusToString(@NonNull ReaderInfo.Status status) {
        if (status instanceof ReaderInfo.Status.Ready) {
            return "READY";
        } else if (status instanceof ReaderInfo.Status.ConnectingToSquare) {
            return "CONNECTING_TO_SQUARE";
        } else if (status instanceof ReaderInfo.Status.ConnectingToDevice) {
            return "CONNECTING_TO_DEVICE";
        } else if (status instanceof ReaderInfo.Status.Faulty) {
            return "FAULTY";
        } else if (status instanceof ReaderInfo.Status.ReaderUnavailable) {
            return "READER_UNAVAILABLE";
        }
        return "READER_UNAVAILABLE";
    }

    @NonNull
    private String convertCardEntryMethodToString(@NonNull CardEntryMethod method) {
        if (method == CardEntryMethod.CONTACTLESS) {
            return "TAP";
        } else if (method == CardEntryMethod.EMV) {
            return "DIP";
        } else if (method == CardEntryMethod.SWIPED) {
            return "SWIPE";
        }
        return "UNKNOWN";
    }

    @NonNull
    private String convertUnavailableReasonToString(@NonNull ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason reason) {
        if (reason == ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason.BLOCKING_UPDATE) {
            return "BLOCKING_UPDATE";
        } else if (reason == ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason.BLUETOOTH_DISABLED) {
            return "BLUETOOTH_DISABLED";
        } else if (reason == ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason.READER_UNAVAILABLE_OFFLINE) {
            return "READER_UNAVAILABLE_OFFLINE";
        } else if (reason == ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason.BLUETOOTH_FAILURE) {
            return "BLUETOOTH_FAILURE";
        } else if (reason == ReaderInfo.Status.ReaderUnavailable.ReaderUnavailableReason.DEVICE_DEVELOPER_MODE) {
            return "DEVICE_DEVELOPER_MODE";
        }
        return "UNKNOWN";
    }

    @NonNull
    private String convertReaderChangeToString(@NonNull ReaderChangedEvent.Change change) {
        if (change == ReaderChangedEvent.Change.BATTERY_CHARGING) {
            return "BATTERY_CHARGING";
        } else if (change == ReaderChangedEvent.Change.ADDED) {
            return "ADDED";
        } else if (change == ReaderChangedEvent.Change.REMOVED) {
            return "REMOVED";
        }
        // Default for any other status changes
        return "STATUS_DID_CHANGE";
    }

    @NonNull
    private String convertPaymentStatusToString(@NonNull Payment.OnlinePayment.Status status) {
        if (status == Payment.OnlinePayment.Status.COMPLETED) {
            return "COMPLETED";
        } else if (status == Payment.OnlinePayment.Status.APPROVED) {
            return "APPROVED";
        } else if (status == Payment.OnlinePayment.Status.CANCELED) {
            return "CANCELED";
        } else if (status == Payment.OnlinePayment.Status.FAILED) {
            return "FAILED";
        } else if (status == Payment.OnlinePayment.Status.UNKNOWN) {
            return "UNKNOWN";
        }
        return "UNKNOWN";
    }

    @NonNull
    private String convertCardBrandToString(@NonNull Card.Brand brand) {
        if (brand == Card.Brand.VISA) {
            return "VISA";
        } else if (brand == Card.Brand.MASTERCARD) {
            return "MASTERCARD";
        } else if (brand == Card.Brand.AMERICAN_EXPRESS) {
            return "AMERICAN_EXPRESS";
        } else if (brand == Card.Brand.DISCOVER) {
            return "DISCOVER";
        } else if (brand == Card.Brand.DISCOVER_DINERS) {
            return "DISCOVER_DINERS";
        } else if (brand == Card.Brand.JCB) {
            return "JCB";
        } else if (brand == Card.Brand.INTERAC) {
            return "INTERAC";
        } else if (brand == Card.Brand.EFTPOS) {
            return "EFTPOS";
        }
        return "OTHER";
    }
}
