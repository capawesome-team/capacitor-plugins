package io.capawesome.capacitorjs.plugins.textzoom.classes;

public class CustomExceptions {

    public static final CustomException ZOOM_INVALID = new CustomException(null, "zoom must be greater than 0.");
    public static final CustomException ZOOM_MISSING = new CustomException(null, "zoom must be provided.");
}
