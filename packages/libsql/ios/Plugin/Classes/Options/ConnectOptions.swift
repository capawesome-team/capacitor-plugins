import Foundation
import Capacitor

@objc public class ConnectOptions: NSObject {
    let authToken: String?
    private let path: String?
    let url: String?

    init(_ call: CAPPluginCall) throws {
        self.authToken = call.getString("authToken")
        if let path = call.getString("path") {
            let documentsPath = NSSearchPathForDirectoriesInDomains(.libraryDirectory, .userDomainMask, true).first!
            let databasePath = URL(fileURLWithPath: documentsPath).appendingPathComponent(path).path
            self.path = databasePath
        } else {
            self.path = nil
        }
        self.url = call.getString("url")
    }

    func getPath() -> String? {
        return path
    }
}
