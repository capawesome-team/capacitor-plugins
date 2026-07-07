import Foundation
import Capacitor
import UIKit

@objc public class PhoneDialer: NSObject {
    private let plugin: PhoneDialerPlugin

    init(plugin: PhoneDialerPlugin) {
        self.plugin = plugin
    }

    @objc public func canDial() -> Bool {
        guard let url = URL(string: "tel://") else {
            return false
        }
        return UIApplication.shared.canOpenURL(url)
    }

    @objc public func dial(_ options: DialOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let url = createDialUrl(options.number) else {
            completion(CustomError.numberInvalid)
            return
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url, options: [:]) { success in
                completion(success ? nil : CustomError.dialFailed)
            }
        }
    }

    private func createDialUrl(_ number: String) -> URL? {
        let allowed = CharacterSet(charactersIn: "0123456789+*")
        let encoded = number.addingPercentEncoding(withAllowedCharacters: allowed) ?? number
        return URL(string: "tel:" + encoded)
    }
}
