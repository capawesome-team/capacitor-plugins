import Capacitor
import Foundation

@objc public class InstrumentationsOptions: NSObject {
    private let nativeCrashReporting: Bool

    init(source: JSObject?) {
        self.nativeCrashReporting = (source?["nativeCrashReporting"] as? Bool) ?? false
    }

    func isNativeCrashReportingEnabled() -> Bool {
        return nativeCrashReporting
    }
}
