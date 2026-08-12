import Foundation
import Capacitor

@objc public class RemoveExifOptions: NSObject {
    let keepOrientation: Bool
    let url: URL

    init(_ call: CAPPluginCall) throws {
        self.keepOrientation = call.getBool("keepOrientation", true)
        self.url = try ReadExifOptions.getUrlFromCall(call)
    }
}
