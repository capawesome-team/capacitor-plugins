import Capacitor
import Foundation

public class GestureSettings {
    let pan: Bool?
    let rotate: Bool?
    let tilt: Bool?
    let zoom: Bool?

    init(pan: Bool?, rotate: Bool?, tilt: Bool?, zoom: Bool?) {
        self.pan = pan
        self.rotate = rotate
        self.tilt = tilt
        self.zoom = zoom
    }

    static func fromJSObject(_ object: JSObject?) -> GestureSettings {
        return GestureSettings(
            pan: object?["pan"] as? Bool,
            rotate: object?["rotate"] as? Bool,
            tilt: object?["tilt"] as? Bool,
            zoom: object?["zoom"] as? Bool
        )
    }
}
