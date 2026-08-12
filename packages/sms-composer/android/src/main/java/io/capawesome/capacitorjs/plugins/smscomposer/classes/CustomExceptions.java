package io.capawesome.capacitorjs.plugins.smscomposer.classes;

public class CustomExceptions {

    public static final CustomException COMPOSE_FAILED = new CustomException(
        "COMPOSE_FAILED",
        "The SMS composer failed to compose or present the message."
    );
}
