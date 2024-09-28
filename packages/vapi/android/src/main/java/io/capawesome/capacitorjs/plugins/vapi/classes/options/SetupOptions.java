package io.capawesome.capacitorjs.plugins.vapi.classes.options;

import androidx.annotation.NonNull;

public class SetupOptions {
    @NonNull
    private String apiKey;

    public SetupOptions(@NonNull String apiKey) {
        this.apiKey = apiKey;
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }
}
