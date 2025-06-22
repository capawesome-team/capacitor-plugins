import Foundation
import Capacitor

@objc public class ConnectOptions: NSObject {
    let authToken: String?
    private let path: String?
    let url: String?

    init(_ call: CAPPluginCall) throws {
        self.authToken = call.getString("authToken")
        self.path = call.getString("path")
        self.url = call.getString("url")
    }
    
    func getPath() -> String? {
        if let path = self.path {
            let documentsPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first!
            let databasePath = URL(fileURLWithPath: documentsPath).appendingPathComponent(path).path
            return databasePath
        }
        return path
    }
}
