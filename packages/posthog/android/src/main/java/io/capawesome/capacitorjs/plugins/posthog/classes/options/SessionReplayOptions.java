package io.capawesome.capacitorjs.plugins.posthog.classes.options;

import androidx.annotation.Nullable;

public class SessionReplayOptions {

    @Nullable
    private Boolean screenshotMode = false;

    @Nullable
    private Boolean maskAllTextInputs = true;

    @Nullable
    private Boolean maskAllImages = true;

    @Nullable
    private Boolean maskAllSandboxedViews = true;

    @Nullable
    private Boolean captureNetworkTelemetry = true;

    @Nullable
    private Double debouncerDelay = 1.0;

    public SessionReplayOptions() {}

    public SessionReplayOptions(
        @Nullable Boolean screenshotMode,
        @Nullable Boolean maskAllTextInputs,
        @Nullable Boolean maskAllImages,
        @Nullable Boolean maskAllSandboxedViews,
        @Nullable Boolean captureNetworkTelemetry,
        @Nullable Double debouncerDelay
    ) {
        this.screenshotMode = screenshotMode;
        this.maskAllTextInputs = maskAllTextInputs;
        this.maskAllImages = maskAllImages;
        this.maskAllSandboxedViews = maskAllSandboxedViews;
        this.captureNetworkTelemetry = captureNetworkTelemetry;
        this.debouncerDelay = debouncerDelay;
    }

    @Nullable
    public Boolean getScreenshotMode() {
        return screenshotMode;
    }

    @Nullable
    public Boolean getMaskAllTextInputs() {
        return maskAllTextInputs;
    }

    @Nullable
    public Boolean getMaskAllImages() {
        return maskAllImages;
    }

    @Nullable
    public Boolean getMaskAllSandboxedViews() {
        return maskAllSandboxedViews;
    }

    @Nullable
    public Boolean getCaptureNetworkTelemetry() {
        return captureNetworkTelemetry;
    }

    @Nullable
    public Double getDebouncerDelay() {
        return debouncerDelay;
    }
}
