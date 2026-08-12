package io.capawesome.capacitorjs.plugins.exif.classes;

public class CustomExceptions {

    public static final CustomException FILE_NOT_FOUND = new CustomException(
        "FILE_NOT_FOUND",
        "The file was not found at the provided path."
    );
    public static final CustomException GPS_COORDINATES_INCOMPLETE = new CustomException(
        null,
        "gpsLatitude and gpsLongitude must be provided together."
    );
    public static final CustomException PATH_MISSING = new CustomException(null, "path must be provided.");
    public static final CustomException READ_FAILED = new CustomException(
        "READ_FAILED",
        "The EXIF metadata could not be read from the file."
    );
    public static final CustomException TAGS_MISSING = new CustomException(null, "tags must be provided.");
    public static final CustomException UNSUPPORTED_FORMAT = new CustomException(
        "UNSUPPORTED_FORMAT",
        "The file format does not support writing or removing EXIF metadata on this platform."
    );
    public static final CustomException WRITE_FAILED = new CustomException(
        "WRITE_FAILED",
        "The EXIF metadata could not be written to the file."
    );
}
