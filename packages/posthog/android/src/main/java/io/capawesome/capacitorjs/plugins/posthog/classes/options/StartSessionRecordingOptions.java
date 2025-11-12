package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.Nullable;

public class StartSessionRecordingOptions {

    @Nullable
    private Boolean linkedFlag;

    @Nullable
    private Double sampling;

    public StartSessionRecordingOptions(@Nullable Boolean linkedFlag, @Nullable Double sampling) {
        this.linkedFlag = linkedFlag;
        this.sampling = sampling;
    }

    @Nullable
    public Boolean getLinkedFlag() {
        return linkedFlag;
    }

    @Nullable
    public Double getSampling() {
        return sampling;
    }
}
