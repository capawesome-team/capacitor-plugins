import Foundation
import Capacitor
import SuperwallKit

@objc public class ConfigureOptions: NSObject {
    let apiKey: String
    let options: SuperwallOptions?

    init(_ call: CAPPluginCall) throws {
        self.apiKey = try ConfigureOptions.getApiKeyFromCall(call)
        self.options = ConfigureOptions.buildSuperwallOptions(ConfigureOptions.getOptionsFromCall(call))
    }

    private static func getApiKeyFromCall(_ call: CAPPluginCall) throws -> String {
        guard let apiKey = call.getString("apiKey") else {
            throw CustomError.apiKeyMissing
        }
        return apiKey
    }

    private static func getOptionsFromCall(_ call: CAPPluginCall) -> JSObject? {
        return call.getObject("options")
    }

    private static func buildSuperwallOptions(_ optionsObject: JSObject?) -> SuperwallOptions? {
        guard let optionsObject = optionsObject else {
            return nil
        }

        let superwallOptions = SuperwallOptions()

        // Paywall options
        if let paywallsObj = optionsObject["paywalls"] as? JSObject {
            if let isHapticFeedbackEnabled = paywallsObj["isHapticFeedbackEnabled"] as? Bool {
                superwallOptions.paywalls.isHapticFeedbackEnabled = isHapticFeedbackEnabled
            }

            if let shouldShowPurchaseFailureAlert = paywallsObj["shouldShowPurchaseFailureAlert"] as? Bool {
                superwallOptions.paywalls.shouldShowPurchaseFailureAlert = shouldShowPurchaseFailureAlert
            }

            if let shouldPreload = paywallsObj["shouldPreload"] as? Bool {
                superwallOptions.paywalls.shouldPreload = shouldPreload
            }

            if let automaticallyDismiss = paywallsObj["automaticallyDismiss"] as? Bool {
                superwallOptions.paywalls.automaticallyDismiss = automaticallyDismiss
            }
        }

        // Logging options
        if let loggingObj = optionsObject["logging"] as? JSObject {
            if let levelStr = loggingObj["level"] as? String {
                switch levelStr {
                case "DEBUG":
                    superwallOptions.logging.level = .debug
                case "INFO":
                    superwallOptions.logging.level = .info
                case "WARN":
                    superwallOptions.logging.level = .warn
                case "ERROR":
                    superwallOptions.logging.level = .error
                default:
                    superwallOptions.logging.level = .warn
                }
            }

            if let scopesArray = loggingObj["scopes"] as? [String], !scopesArray.isEmpty {
                var scopes: Set<LogScope> = []
                for scopeStr in scopesArray {
                    switch scopeStr {
                    case "ALL":
                        scopes.insert(.all)
                    case "ANALYTICS":
                        scopes.insert(.analytics)
                    case "CONFIG":
                        scopes.insert(.configManager)
                    case "EVENTS":
                        scopes.insert(.paywallEvents)
                    case "PAYWALLS":
                        scopes.insert(.paywallPresentation)
                    case "PURCHASES":
                        scopes.insert(.productsManager)
                    default:
                        break
                    }
                }
                if !scopes.isEmpty {
                    superwallOptions.logging.scopes = scopes
                }
            }
        }

        // Network environment
        if let networkEnvStr = optionsObject["networkEnvironment"] as? String {
            switch networkEnvStr {
            case "RELEASE":
                superwallOptions.networkEnvironment = .release
            case "DEVELOPER":
                superwallOptions.networkEnvironment = .developer
            default:
                superwallOptions.networkEnvironment = .release
            }
        }

        // Locale identifier
        if let localeIdentifier = optionsObject["localeIdentifier"] as? String {
            superwallOptions.localeIdentifier = localeIdentifier
        }

        return superwallOptions
    }
}
