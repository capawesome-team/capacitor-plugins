package io.capawesome.capacitorjs.plugins.battery.classes;

public class CustomExceptions {

    public static final CustomException BATTERY_LEVEL_UNAVAILABLE = new CustomException(
        null,
        "The battery level is currently unavailable."
    );
}
