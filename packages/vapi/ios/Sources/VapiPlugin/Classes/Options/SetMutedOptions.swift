import Foundation
import Capacitor

@objc public class SetMutedOptions: NSObject {
    private var muted: Bool

    init(muted: Bool) {
        self.muted = muted
    }

    func getMuted() -> Bool {
        return muted
    }
}
