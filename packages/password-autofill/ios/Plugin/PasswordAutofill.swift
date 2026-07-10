import Foundation
import Security

@objc public class PasswordAutofill: NSObject {
    private let plugin: PasswordAutofillPlugin

    init(plugin: PasswordAutofillPlugin) {
        self.plugin = plugin
    }

    @objc public func savePassword(_ options: SavePasswordOptions, completion: @escaping (_ error: Error?) -> Void) {
        let domain = options.domain
        let username = options.username
        let password = options.password
        SecAddSharedWebCredential(domain as CFString, username as CFString, password as CFString) { error in
            if let error = error {
                completion(CustomError.saveFailed(message: (error as Error).localizedDescription))
                return
            }
            completion(nil)
        }
    }
}
