import Foundation
import Capacitor
import Crisp

@objc public class PushSessionEventOptions: NSObject {
    let color: SessionEventColor
    let name: String

    init(_ call: CAPPluginCall) throws {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        self.name = name
        self.color = PushSessionEventOptions.parseColor(call.getString("color"))
    }

    private static func parseColor(_ color: String?) -> SessionEventColor {
        switch color {
        case "BLACK":
            return .black
        case "BROWN":
            return .brown
        case "GREEN":
            return .green
        case "GREY":
            return .grey
        case "ORANGE":
            return .orange
        case "PINK":
            return .pink
        case "PURPLE":
            return .purple
        case "RED":
            return .red
        case "YELLOW":
            return .yellow
        default:
            return .blue
        }
    }
}
