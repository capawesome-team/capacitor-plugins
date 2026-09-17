import Foundation
import Capacitor
import flic2lib

public class FlicHelper {

    public static func createButtonConnectionState(_ button: FLICButton) -> ButtonConnectionState {
        switch button.state {
        case .connecting:
            return .connecting
        case .connected:
            return .connected
        case .disconnecting:
            return .disconnecting
        default:
            return .disconnected
        }
    }

    public static func createButtonJSObject(_ button: FLICButton) -> JSObject {
        var result = JSObject()
        if button.batteryVoltage > 0 {
            result["batteryVoltage"] = Double(button.batteryVoltage)
        }
        result["connectionState"] = createButtonConnectionState(button).rawValue
        result["firmwareVersion"] = Int(button.firmwareRevision)
        result["id"] = button.identifier.uuidString
        result["isReady"] = button.isReady
        result["isUnpaired"] = button.isUnpaired
        if let nickname = button.nickname {
            result["name"] = nickname
        }
        result["pressCount"] = Int(button.pressCount)
        result["serialNumber"] = button.serialNumber
        result["uuid"] = button.uuid
        return result
    }
}
