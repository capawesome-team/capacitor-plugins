import Foundation
import Capacitor
import PassKit

@objc public class Wallet: NSObject {
    private let plugin: WalletPlugin
    private var addPassesCompletion: ((Error?) -> Void)?

    init(plugin: WalletPlugin) {
        self.plugin = plugin
    }

    @objc public func addPasses(_ options: AddPassesOptions, completion: @escaping (_ error: Error?) -> Void) {
        var passes: [PKPass] = []
        for encodedPass in options.passes {
            guard let data = Data(base64Encoded: encodedPass), let pass = try? PKPass(data: data) else {
                completion(CustomError.passInvalid)
                return
            }
            passes.append(pass)
        }
        guard PKAddPassesViewController.canAddPasses() else {
            completion(CustomError.unavailable)
            return
        }
        DispatchQueue.main.async {
            guard let viewController = self.plugin.bridge?.viewController,
                  let addPassesViewController = PKAddPassesViewController(passes: passes) else {
                completion(CustomError.addFailed)
                return
            }
            addPassesViewController.delegate = self
            self.addPassesCompletion = completion
            viewController.present(addPassesViewController, animated: true)
        }
    }

    @objc public func canAddPasses(completion: @escaping (_ result: CanAddPassesResult?, _ error: Error?) -> Void) {
        let canAdd = PKAddPassesViewController.canAddPasses()
        let result = CanAddPassesResult(canAdd: canAdd)
        completion(result, nil)
    }
}

extension Wallet: PKAddPassesViewControllerDelegate {
    public func addPassesViewControllerDidFinish(_ controller: PKAddPassesViewController) {
        controller.dismiss(animated: true) {
            let completion = self.addPassesCompletion
            self.addPassesCompletion = nil
            completion?(nil)
        }
    }
}
