package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.NonNull;

public class AliasOptions {

    @NonNull
    private String alias;

    public AliasOptions(@NonNull String alias) {
        this.alias = alias;
    }

    @NonNull
    public String getAlias() {
        return alias;
    }
}
