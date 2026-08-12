package io.capawesome.capacitorjs.plugins.permissions.classes;

public class CustomExceptions {

    public static final CustomException PERMISSION_INVALID = new CustomException(null, "permission must be one of the supported values.");
    public static final CustomException PERMISSIONS_MISSING = new CustomException(null, "permissions must be provided.");
}
