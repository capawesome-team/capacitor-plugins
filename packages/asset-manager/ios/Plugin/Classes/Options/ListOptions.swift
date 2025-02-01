import Foundation
import Capacitor

@objc public class ListOptions: NSObject {
    public let path: String

    init(_ call: CAPPluginCall) throws {
        self.path = call.getString("path") ?? "/"
    }
}
