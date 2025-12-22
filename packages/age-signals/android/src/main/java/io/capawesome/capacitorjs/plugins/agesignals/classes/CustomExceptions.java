package io.capawesome.capacitorjs.plugins.agesignals.classes;

public class CustomExceptions {

    public static final CustomException API_NOT_AVAILABLE = new CustomException(
        "API_NOT_AVAILABLE",
        "The Play Age Signals API is not available. The Play Store app version installed on the device might be old."
    );
    public static final CustomException PLAY_STORE_NOT_FOUND = new CustomException(
        "PLAY_STORE_NOT_FOUND",
        "No Play Store app is found on the device."
    );
    public static final CustomException NETWORK_ERROR = new CustomException("NETWORK_ERROR", "No available network is found.");
    public static final CustomException PLAY_SERVICES_NOT_FOUND = new CustomException(
        "PLAY_SERVICES_NOT_FOUND",
        "Play Services is not available or its version is too old."
    );
    public static final CustomException CANNOT_BIND_TO_SERVICE = new CustomException(
        "CANNOT_BIND_TO_SERVICE",
        "Binding to the service in the Play Store has failed. This can be due to having an old Play Store version installed on the device or device memory is overloaded."
    );
    public static final CustomException PLAY_STORE_VERSION_OUTDATED = new CustomException(
        "PLAY_STORE_VERSION_OUTDATED",
        "The Play Store app needs to be updated."
    );
    public static final CustomException PLAY_SERVICES_VERSION_OUTDATED = new CustomException(
        "PLAY_SERVICES_VERSION_OUTDATED",
        "Play Services needs to be updated."
    );
    public static final CustomException CLIENT_TRANSIENT_ERROR = new CustomException(
        "CLIENT_TRANSIENT_ERROR",
        "There was a transient error in the client device."
    );
    public static final CustomException APP_NOT_OWNED = new CustomException("APP_NOT_OWNED", "The app was not installed by Google Play.");
    public static final CustomException INTERNAL_ERROR = new CustomException("INTERNAL_ERROR", "Unknown internal error.");
    public static final CustomException USER_STATUS_MISSING = new CustomException(null, "userStatus must be provided.");
    public static final CustomException FAKE_MANAGER_NOT_ENABLED = new CustomException(null, "Fake manager is not enabled.");
}
