package io.capawesome.capacitorjs.plugins.liveupdate.classes;

public class CustomExceptions {

    public static final CustomException CHECKSUM_MISSING = new CustomException(null, "Bundle does not contain a checksum.");
    public static final CustomException DOWNLOAD_URL_EXPIRED = new CustomException(
        "DOWNLOAD_URL_EXPIRED",
        "Bundle download URL has expired."
    );
}
