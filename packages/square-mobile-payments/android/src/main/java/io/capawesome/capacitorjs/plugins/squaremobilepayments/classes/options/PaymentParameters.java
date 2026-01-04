package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.CustomExceptions;

public class PaymentParameters {

    @NonNull
    private final Money amountMoney;

    @NonNull
    private final String paymentAttemptId;

    @Nullable
    private final String processingMode;

    @Nullable
    private final String referenceId;

    @Nullable
    private final String note;

    @Nullable
    private final String orderId;

    @Nullable
    private final Money tipMoney;

    @Nullable
    private final Money applicationFee;

    @Nullable
    private final Boolean autocomplete;

    @Nullable
    private final String delayDuration;

    @Nullable
    private final String delayAction;

    public PaymentParameters(@NonNull JSObject obj) throws Exception {
        JSObject amountMoneyObj = obj.getJSObject("amountMoney");
        if (amountMoneyObj == null) {
            throw CustomExceptions.AMOUNT_MONEY_MISSING;
        }
        this.amountMoney = new Money(amountMoneyObj);

        String paymentAttemptId = obj.getString("paymentAttemptId");
        if (paymentAttemptId == null) {
            throw CustomExceptions.PAYMENT_ATTEMPT_ID_MISSING;
        }
        this.paymentAttemptId = paymentAttemptId;

        this.processingMode = obj.getString("processingMode");
        this.referenceId = obj.getString("referenceId");
        this.note = obj.getString("note");
        this.orderId = obj.getString("orderId");

        JSObject tipMoneyObj = obj.getJSObject("tipMoney");
        this.tipMoney = tipMoneyObj != null ? new Money(tipMoneyObj) : null;

        JSObject applicationFeeObj = obj.getJSObject("applicationFee");
        this.applicationFee = applicationFeeObj != null ? new Money(applicationFeeObj) : null;

        this.autocomplete = obj.getBool("autocomplete");
        this.delayDuration = obj.getString("delayDuration");
        this.delayAction = obj.getString("delayAction");
    }

    @NonNull
    public Money getAmountMoney() {
        return amountMoney;
    }

    @NonNull
    public String getPaymentAttemptId() {
        return paymentAttemptId;
    }

    @Nullable
    public String getProcessingMode() {
        return processingMode;
    }

    @Nullable
    public String getReferenceId() {
        return referenceId;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    @Nullable
    public String getOrderId() {
        return orderId;
    }

    @Nullable
    public Money getTipMoney() {
        return tipMoney;
    }

    @Nullable
    public Money getApplicationFee() {
        return applicationFee;
    }

    @Nullable
    public Boolean getAutocomplete() {
        return autocomplete;
    }

    @Nullable
    public String getDelayDuration() {
        return delayDuration;
    }

    @Nullable
    public String getDelayAction() {
        return delayAction;
    }
}
