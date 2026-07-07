package io.capawesome.capacitorjs.plugins.appintegrity.classes;

public class CustomExceptions {

    public static final CustomException CLOUD_PROJECT_NUMBER_MISSING = new CustomException(null, "cloudProjectNumber must be provided.");
    public static final CustomException INTEGRITY_TOKEN_PROVIDER_NOT_PREPARED = new CustomException(
        null,
        "No integrity token provider available. Call prepareIntegrityToken() first."
    );
    public static final CustomException NONCE_OR_REQUEST_HASH_MISSING = new CustomException(null, "nonce or requestHash must be provided.");
}
