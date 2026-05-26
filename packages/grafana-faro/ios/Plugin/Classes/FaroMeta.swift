import Foundation
import UIKit

class FaroMeta {

    let app: AppMetadata
    var session: FaroSession?
    var user: UserMetadata?
    var view: ViewMetadata?

    init(app: AppMetadata) {
        self.app = app
    }

    func toDictionary() -> [String: Any] {
        var result: [String: Any] = [:]
        result["sdk"] = [
            "name": GrafanaFaroPlugin.sdkName
        ]
        var appDict: [String: Any] = ["name": app.getName()]
        if let value = app.getVersion() { appDict["version"] = value }
        if let value = app.getEnvironment() { appDict["environment"] = value }
        if let value = app.getNamespace() { appDict["namespace"] = value }
        result["app"] = appDict
        if let session = session {
            var sessionDict: [String: Any] = ["id": session.id]
            if !session.attributes.isEmpty {
                sessionDict["attributes"] = session.attributes
            }
            result["session"] = sessionDict
        }
        if let user = user {
            var userDict: [String: Any] = [:]
            if let value = user.getId() { userDict["id"] = value }
            if let value = user.getEmail() { userDict["email"] = value }
            if let value = user.getUsername() { userDict["username"] = value }
            if let value = user.getFullName() { userDict["fullName"] = value }
            if let attributes = user.getAttributes(), !attributes.isEmpty {
                userDict["attributes"] = attributes
            }
            if !userDict.isEmpty {
                result["user"] = userDict
            }
        }
        if let view = view {
            result["view"] = ["name": view.getName()]
        }
        let device = UIDevice.current
        result["os"] = [
            "name": device.systemName,
            "version": device.systemVersion,
            "build_id": ProcessInfo.processInfo.operatingSystemVersionString,
            "detail": "iOS \(device.systemVersion)"
        ]
        result["device"] = [
            "manufacturer": "Apple",
            "model_identifier": modelIdentifier(),
            "model_name": UIDevice.current.model,
            "brand": "Apple",
            "is_physical": isPhysicalDevice(),
            "type": "mobile"
        ]
        return result
    }

    private func modelIdentifier() -> String {
        var systemInfo = utsname()
        uname(&systemInfo)
        let machineMirror = Mirror(reflecting: systemInfo.machine)
        return machineMirror.children.reduce(into: "") { identifier, element in
            guard let value = element.value as? Int8, value != 0 else { return }
            identifier.append(Character(UnicodeScalar(UInt8(value))))
        }
    }

    private func isPhysicalDevice() -> Bool {
        #if targetEnvironment(simulator)
        return false
        #else
        return true
        #endif
    }
}
