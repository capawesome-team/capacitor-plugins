package io.capawesome.capacitorjs.plugins.photomanipulator.classes;

public class CustomExceptions {

    public static final CustomException CROP_INVALID = new CustomException(null, "crop must be within the bounds of the source image.");
    public static final CustomException FILE_NOT_FOUND = new CustomException("FILE_NOT_FOUND", "The file was not found.");
    public static final CustomException FORMAT_INVALID = new CustomException(null, "format must be one of the supported values.");
    public static final CustomException PATH_INVALID = new CustomException(null, "path must be a local file path or file URI.");
    public static final CustomException PATH_MISSING = new CustomException(null, "path must be provided.");
    public static final CustomException QUALITY_INVALID = new CustomException(null, "quality must be between 0 and 1.");
    public static final CustomException RESIZE_INVALID = new CustomException(null, "resize must contain a positive width or height.");
    public static final CustomException ROTATE_INVALID = new CustomException(null, "rotate must be 90, 180 or 270.");
    public static final CustomException TRANSFORM_FAILED = new CustomException("TRANSFORM_FAILED", "The image could not be transformed.");
    public static final CustomException UNSUPPORTED_FORMAT = new CustomException(
        "UNSUPPORTED_FORMAT",
        "The image format is not supported."
    );
}
