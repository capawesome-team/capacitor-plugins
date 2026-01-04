package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes.results;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.squaremobilepayments.interfaces.Result;
import org.json.JSONObject;

public class Payment implements Result {

    @Nullable
    private final String id;

    @NonNull
    private final String type;

    @NonNull
    private final String status;

    @NonNull
    private final MoneyResult amountMoney;

    @Nullable
    private final MoneyResult tipMoney;

    @Nullable
    private final MoneyResult applicationFee;

    @Nullable
    private final String referenceId;

    @Nullable
    private final String orderId;

    @Nullable
    private final CardPaymentDetails cardDetails;

    @Nullable
    private final String createdAt;

    @Nullable
    private final String updatedAt;

    public Payment(
        @Nullable String id,
        @NonNull String type,
        @NonNull String status,
        @NonNull MoneyResult amountMoney,
        @Nullable MoneyResult tipMoney,
        @Nullable MoneyResult applicationFee,
        @Nullable String referenceId,
        @Nullable String orderId,
        @Nullable CardPaymentDetails cardDetails,
        @Nullable String createdAt,
        @Nullable String updatedAt
    ) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.amountMoney = amountMoney;
        this.tipMoney = tipMoney;
        this.applicationFee = applicationFee;
        this.referenceId = referenceId;
        this.orderId = orderId;
        this.cardDetails = cardDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", id == null ? JSONObject.NULL : id);
        result.put("type", type);
        result.put("status", status);
        result.put("amountMoney", amountMoney.toJSObject());
        result.put("tipMoney", tipMoney == null ? JSONObject.NULL : tipMoney.toJSObject());
        result.put("applicationFee", applicationFee == null ? JSONObject.NULL : applicationFee.toJSObject());
        result.put("referenceId", referenceId == null ? JSONObject.NULL : referenceId);
        result.put("orderId", orderId == null ? JSONObject.NULL : orderId);
        result.put("cardDetails", cardDetails == null ? JSONObject.NULL : cardDetails.toJSObject());
        result.put("createdAt", createdAt == null ? JSONObject.NULL : createdAt);
        result.put("updatedAt", updatedAt == null ? JSONObject.NULL : updatedAt);
        return result;
    }
}
