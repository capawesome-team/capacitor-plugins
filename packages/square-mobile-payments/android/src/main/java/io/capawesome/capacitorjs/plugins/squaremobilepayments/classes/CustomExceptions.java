package io.capawesome.capacitorjs.plugins.squaremobilepayments.classes;

public class CustomExceptions {

    public static final CustomException LOCATION_ID_MISSING = new CustomException("LOCATION_ID_MISSING", "locationId must be provided.");
    public static final CustomException ACCESS_TOKEN_MISSING = new CustomException("ACCESS_TOKEN_MISSING", "accessToken must be provided.");
    public static final CustomException SERIAL_NUMBER_MISSING = new CustomException("SERIAL_NUMBER_MISSING", "serialNumber must be provided.");
    public static final CustomException PAYMENT_PARAMETERS_MISSING = new CustomException("PAYMENT_PARAMETERS_MISSING", "paymentParameters must be provided.");
    public static final CustomException PROMPT_PARAMETERS_MISSING = new CustomException("PROMPT_PARAMETERS_MISSING", "promptParameters must be provided.");
    public static final CustomException AMOUNT_MONEY_MISSING = new CustomException("AMOUNT_MONEY_MISSING", "amountMoney must be provided.");
    public static final CustomException PAYMENT_ATTEMPT_ID_MISSING = new CustomException("PAYMENT_ATTEMPT_ID_MISSING", "paymentAttemptId must be provided.");
    public static final CustomException NOT_INITIALIZED = new CustomException("NOT_INITIALIZED", "The SDK is not initialized. Call initialize() first.");
    public static final CustomException NOT_AUTHORIZED = new CustomException("NOT_AUTHORIZED", "The SDK is not authorized. Call authorize() first.");
    public static final CustomException PAIRING_ALREADY_IN_PROGRESS = new CustomException("PAIRING_ALREADY_IN_PROGRESS", "A pairing process is already in progress.");
    public static final CustomException NO_PAYMENT_IN_PROGRESS = new CustomException("NO_PAYMENT_IN_PROGRESS", "No payment is currently in progress.");
    public static final CustomException READER_NOT_FOUND = new CustomException("READER_NOT_FOUND", "Reader not found.");
}
