import Foundation

@objc public class SessionReplayOptions: NSObject {
    private var screenshotMode: Bool?
    private var maskAllTextInputs: Bool?
    private var maskAllImages: Bool?
    private var maskAllSandboxedViews: Bool?
    private var captureNetworkTelemetry: Bool?
    private var debouncerDelay: Double?

    init(screenshotMode: Bool? = false, maskAllTextInputs: Bool? = true, maskAllImages: Bool? = true, maskAllSandboxedViews: Bool? = true, captureNetworkTelemetry: Bool? = true, debouncerDelay: Double? = 1.0) {
        self.screenshotMode = screenshotMode
        self.maskAllTextInputs = maskAllTextInputs
        self.maskAllImages = maskAllImages
        self.maskAllSandboxedViews = maskAllSandboxedViews
        self.captureNetworkTelemetry = captureNetworkTelemetry
        self.debouncerDelay = debouncerDelay
    }

    func getScreenshotMode() -> Bool? {
        return screenshotMode
    }

    func getMaskAllTextInputs() -> Bool? {
        return maskAllTextInputs
    }

    func getMaskAllImages() -> Bool? {
        return maskAllImages
    }

    func getMaskAllSandboxedViews() -> Bool? {
        return maskAllSandboxedViews
    }

    func getCaptureNetworkTelemetry() -> Bool? {
        return captureNetworkTelemetry
    }

    func getDebouncerDelay() -> Double? {
        return debouncerDelay
    }
}
