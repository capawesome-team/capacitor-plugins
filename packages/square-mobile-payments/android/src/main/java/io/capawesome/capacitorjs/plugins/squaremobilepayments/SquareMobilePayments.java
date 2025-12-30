package io.capawesome.capacitorjs.plugins.squaremobilepayments;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import com.squareup.sdk.mobilepayments.MobilePaymentsSdk;
import com.squareup.sdk.mobilepayments.authorization.AuthorizationManager;
import com.squareup.sdk.mobilepayments.core.CallbackReference;
import com.squareup.sdk.mobilepayments.payment.AdditionalPaymentMethod;
import com.squareup.sdk.mobilepayments.payment.Card;
import com.squareup.sdk.mobilepayments.payment.CardDetails;
import com.squareup.sdk.mobilepayments.payment.CardEntryMethod;
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
import com.squareup.sdk.mobilepayments.PairingStopHandle;
import com.squareup.sdk.mobilepayments.ReaderChangedEvent;
import com.squareup.sdk.mobilepayments.ReaderInfo;
import com.squareup.sdk.mobilepayments.ReaderManager;
import com.squareup.sdk.mobilepayments.settings.SdkEnvironment;
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
    private PairingStopHandle pairingHandle;

    @Nullable
    private PaymentHandle paymentHandle;

    @Nullable
    private CallbackReference readerChangedCallbackReference;

    @Nullable
    private CallbackReference availableCardInputMethodsCallbackReference;

    public SquareMobilePayments(@NonNull SquareMobilePaymentsPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(@NonNull InitializeOptions options, @NonNull EmptyCallback callback) {
        try {
            String locationId = options.getLocationId();
            Context context = plugin.getContext();

            MobilePaymentsSdk.initialize(locationId, context);

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
            authManager.authorize(accessToken, result -> {
                if (result.isSuccess()) {
                    isAuthorized = true;
                    callback.success();
                } else {
                    callback.error(new Exception(result.getErrorMessage()));
                }
                return null;
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
            authManager.deauthorize(result -> {
                if (result.isSuccess()) {
                    isAuthorized = false;
                    callback.success();
                } else {
                    callback.error(new Exception(result.getErrorMessage()));
                }
                return null;
            });
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
                    callback.error(new Exception(result.getErrorMessage()));
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

            String version = sdkSettings.getVersion();
            String environment = convertSdkEnvironmentToString(sdkSettings.getEnvironment());

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
            plugin.notifyListeners("readerPairingDidBegin", new JSObject());

            pairingHandle = readerManager.pairReader(result -> {
                if (result.isSuccess()) {
                    // Notify that pairing succeeded
                    plugin.notifyListeners("readerPairingDidSucceed", new JSObject());
                } else {
                    // Notify that pairing failed
                    ReaderPairingDidFailEvent event = new ReaderPairingDidFailEvent(
                        result.getErrorCode() != null ? result.getErrorCode().toString() : null,
                        result.getErrorMessage()
                    );
                    plugin.notifyListeners("readerPairingDidFail", event.toJSObject());
                }
                pairingHandle = null;
                return null;
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
                pairingHandle.stop();
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
                io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo reader = convertSdkReaderToReaderInfo(sdkReader);
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
            ReaderInfo sdkReader = findReaderBySerialNumber(serialNumber);

            if (sdkReader == null) {
                throw CustomExceptions.READER_NOT_FOUND;
            }

            readerManager.retryConnection();

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

            PaymentParameters params = options.getPaymentParameters();
            PromptParameters promptParams = options.getPromptParameters();

            PaymentManager paymentManager = MobilePaymentsSdk.paymentManager();

            // Build Money object
            Money amountMoney = new Money(
                params.getAmountMoney().getAmount(),
                CurrencyCode.valueOf(params.getAmountMoney().getCurrency())
            );

            // Build PaymentParameters
            PaymentParameters.Builder paramsBuilder = new PaymentParameters.Builder(
                amountMoney,
                params.getPaymentAttemptId()
            );

            // Add optional parameters
            if (params.getProcessingMode() != null) {
                paramsBuilder.processingMode(
                    ProcessingMode.valueOf(params.getProcessingMode())
                );
            }

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
                Money tipMoney = new Money(
                    params.getTipMoney().getAmount(),
                    CurrencyCode.valueOf(params.getTipMoney().getCurrency())
                );
                paramsBuilder.tipMoney(tipMoney);
            }

            if (params.getApplicationFee() != null) {
                Money applicationFee = new Money(
                    params.getApplicationFee().getAmount(),
                    CurrencyCode.valueOf(params.getApplicationFee().getCurrency())
                );
                paramsBuilder.applicationFee(applicationFee);
            }

            if (params.getAutocomplete() != null) {
                paramsBuilder.autocomplete(params.getAutocomplete());
            }

            if (params.getDelayDuration() != null) {
                paramsBuilder.delayDuration(Duration.parse(params.getDelayDuration()));
            }

            if (params.getDelayAction() != null) {
                paramsBuilder.delayAction(
                    DelayAction.valueOf(params.getDelayAction())
                );
            }

            PaymentParameters sdkPaymentParams = paramsBuilder.build();

            // Build PromptParameters
            PromptMode promptMode = PromptMode.DEFAULT;
            if (promptParams.getMode() != null) {
                promptMode = PromptMode.valueOf(promptParams.getMode());
            }

            List<AdditionalPaymentMethod> additionalMethods = new ArrayList<>();
            for (String method : promptParams.getAdditionalMethods()) {
                additionalMethods.add(AdditionalPaymentMethod.valueOf(method));
            }

            PromptParameters sdkPromptParams = new PromptParameters(
                promptMode,
                additionalMethods
            );

            // Start payment
            paymentHandle = paymentManager.startPaymentActivity(
                sdkPaymentParams,
                sdkPromptParams,
                result -> {
                    if (result.isSuccess()) {
                        Payment sdkPayment = result.getValue();
                        io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment payment = convertSdkPaymentToPayment(sdkPayment);

                        PaymentDidFinishEvent event = new PaymentDidFinishEvent(payment);
                        plugin.notifyListeners("paymentDidFinish", event.toJSObject());
                    } else {
                        PaymentDidFailEvent event = new PaymentDidFailEvent(
                            null,
                            result.getErrorCode() != null ? result.getErrorCode().toString() : null,
                            result.getErrorMessage()
                        );
                        plugin.notifyListeners("paymentDidFail", event.toJSObject());
                    }
                    paymentHandle = null;
                    return null;
                }
            );

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
                plugin.notifyListeners("paymentDidCancel", event.toJSObject());
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

            List<String> cardInputMethods = methods
                .stream()
                .map(this::convertCardEntryMethodToString)
                .collect(Collectors.toList());

            GetAvailableCardInputMethodsResult result = new GetAvailableCardInputMethodsResult(cardInputMethods);
            callback.success(result);
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
            ReaderInfo sdkReader = event.getReader();
            ReaderChangedEvent.Change change = event.getChange();

            io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo reader = convertSdkReaderToReaderInfo(sdkReader);

            // Notify based on change type
            if (change == ReaderChangedEvent.Change.ADDED) {
                ReaderWasAddedEvent addedEvent = new ReaderWasAddedEvent(reader);
                plugin.notifyListeners("readerWasAdded", addedEvent.toJSObject());
            } else if (change == ReaderChangedEvent.Change.REMOVED) {
                ReaderWasRemovedEvent removedEvent = new ReaderWasRemovedEvent(reader);
                plugin.notifyListeners("readerWasRemoved", removedEvent.toJSObject());
            }

            // Also notify general reader changed event
            ReaderDidChangeEvent didChangeEvent = new ReaderDidChangeEvent(reader, convertReaderChangeToString(change));
            plugin.notifyListeners("readerDidChange", didChangeEvent.toJSObject());

            return null;
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
            List<String> cardInputMethods = methods
                .stream()
                .map(this::convertCardEntryMethodToString)
                .collect(Collectors.toList());

            AvailableCardInputMethodsDidChangeEvent event = new AvailableCardInputMethodsDidChangeEvent(cardInputMethods);
            plugin.notifyListeners("availableCardInputMethodsDidChange", event.toJSObject());

            return null;
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
    private ReaderInfo findReaderBySerialNumber(@NonNull String serialNumber) {
        ReaderManager readerManager = MobilePaymentsSdk.readerManager();
        List<ReaderInfo> readers = readerManager.getReaders();

        for (ReaderInfo reader : readers) {
            if (reader.getSerialNumber().equals(serialNumber)) {
                return reader;
            }
        }

        return null;
    }

    @NonNull
    private io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.ReaderInfo convertSdkReaderToReaderInfo(@NonNull ReaderInfo sdkReader) {
        String serialNumber = sdkReader.getSerialNumber();
        String model = convertReaderModelToString(sdkReader.getModel());
        String status = convertReaderStatusToString(sdkReader.getStatus());
        String firmwareVersion = sdkReader.getFirmwareVersion();
        Integer batteryLevel = sdkReader.getBatteryLevel();
        Boolean isCharging = sdkReader.isCharging();

        List<String> supportedCardInputMethods = sdkReader
            .getSupportedCardEntryMethods()
            .stream()
            .map(this::convertCardEntryMethodToString)
            .collect(Collectors.toList());

        UnavailableReasonInfo unavailableReasonInfo = null;
        if (sdkReader.getStatus() instanceof ReaderInfo.Status.ReaderUnavailable) {
            ReaderInfo.Status.ReaderUnavailable unavailable =
                (ReaderInfo.Status.ReaderUnavailable) sdkReader.getStatus();
            unavailableReasonInfo = new UnavailableReasonInfo(
                convertUnavailableReasonToString(unavailable.getReason()),
                unavailable.getMessage()
            );
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
    private io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Payment convertSdkPaymentToPayment(@NonNull Payment sdkPayment) {
        String id = null;
        String type;
        String status;
        CardPaymentDetails cardDetails = null;

        if (sdkPayment instanceof Payment.OnlinePayment) {
            Payment.OnlinePayment onlinePayment = (Payment.OnlinePayment) sdkPayment;
            id = onlinePayment.getId();
            type = "ONLINE";
            status = convertPaymentStatusToString(onlinePayment.getStatus());

            // Extract card details
            CardDetails sdkCardDetails = onlinePayment.getCardDetails();
            if (sdkCardDetails != null) {
                cardDetails = convertSdkCardDetailsToCardPaymentDetails(sdkCardDetails);
            }
        } else if (sdkPayment instanceof Payment.OfflinePayment) {
            Payment.OfflinePayment offlinePayment = (Payment.OfflinePayment) sdkPayment;
            id = offlinePayment.getId();
            type = "OFFLINE";
            status = "PENDING";

            // Extract card details
            CardDetails sdkCardDetails = offlinePayment.getCardDetails();
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
            ? new MoneyResult(
                (int) sdkPayment.getTipMoney().getAmount(),
                sdkPayment.getTipMoney().getCurrencyCode().toString()
            )
            : null;

        MoneyResult applicationFee = sdkPayment.getAppFeeMoney() != null
            ? new MoneyResult(
                (int) sdkPayment.getAppFeeMoney().getAmount(),
                sdkPayment.getAppFeeMoney().getCurrencyCode().toString()
            )
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
    private CardPaymentDetails convertSdkCardDetailsToCardPaymentDetails(@NonNull CardDetails sdkCardDetails) {
        Card sdkCard = sdkCardDetails.getCard();

        io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Card card = new io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results.Card(
            convertCardBrandToString(sdkCard.getBrand()),
            sdkCard.getLastFourDigits(),
            sdkCard.getCardholderName(),
            sdkCard.getExpirationMonth(),
            sdkCard.getExpirationYear()
        );

        String entryMethod = convertCardEntryMethodToString(sdkCardDetails.getEntryMethod());

        return new CardPaymentDetails(
            card,
            entryMethod,
            sdkCardDetails.getAuthorizationCode(),
            sdkCardDetails.getApplicationName(),
            sdkCardDetails.getApplicationId()
        );
    }

    @NonNull
    private String convertSdkEnvironmentToString(@NonNull SdkEnvironment environment) {
        if (environment == SdkEnvironment.PRODUCTION) {
            return "production";
        } else if (environment == SdkEnvironment.SANDBOX) {
            return "sandbox";
        }
        return "production";
    }

    @NonNull
    private String convertReaderModelToString(@NonNull ReaderInfo.Model model) {
        if (model == ReaderInfo.Model.CONTACTLESS_AND_CHIP) {
            return "CONTACTLESS_AND_CHIP";
        } else if (model == ReaderInfo.Model.MAGSTRIPE) {
            return "MAGSTRIPE";
        } else if (model == ReaderInfo.Model.STAND) {
            return "STAND";
        }
        return "UNKNOWN";
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
        } else if (method == CardEntryMethod.KEYED) {
            return "KEYED";
        }
        return "KEYED";
    }

    @NonNull
    private String convertUnavailableReasonToString(@NonNull ReaderInfo.UnavailableReason reason) {
        if (reason == ReaderInfo.UnavailableReason.BLUETOOTH_ERROR) {
            return "BLUETOOTH_ERROR";
        } else if (reason == ReaderInfo.UnavailableReason.FIRMWARE_UPDATE) {
            return "FIRMWARE_UPDATE";
        } else if (reason == ReaderInfo.UnavailableReason.DISABLED_IN_DASHBOARD) {
            return "DISABLED_IN_DASHBOARD";
        } else if (reason == ReaderInfo.UnavailableReason.ACCOUNT_NOT_AUTHORIZED) {
            return "ACCOUNT_NOT_AUTHORIZED";
        }
        return "UNKNOWN";
    }

    @NonNull
    private String convertReaderChangeToString(@NonNull ReaderChangedEvent.Change change) {
        if (change == ReaderChangedEvent.Change.BATTERY_DID_BEGIN_CHARGING) {
            return "BATTERY_DID_BEGIN_CHARGING";
        } else if (change == ReaderChangedEvent.Change.BATTERY_DID_END_CHARGING) {
            return "BATTERY_DID_END_CHARGING";
        } else if (change == ReaderChangedEvent.Change.BATTERY_LEVEL_DID_CHANGE) {
            return "BATTERY_LEVEL_DID_CHANGE";
        } else if (change == ReaderChangedEvent.Change.STATUS_DID_CHANGE) {
            return "STATUS_DID_CHANGE";
        }
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
        } else if (status == Payment.OnlinePayment.Status.PENDING) {
            return "PENDING";
        }
        return "PENDING";
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
        } else if (brand == Card.Brand.UNION_PAY) {
            return "UNION_PAY";
        } else if (brand == Card.Brand.INTERAC) {
            return "INTERAC";
        } else if (brand == Card.Brand.EFTPOS) {
            return "EFTPOS";
        }
        return "OTHER";
    }
}
