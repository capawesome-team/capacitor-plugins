import Capacitor
import Foundation

@objc public class PlayerFrame: NSObject {
    static let minimumSize: Double = 200

    let height: Double
    let width: Double
    // swiftlint:disable identifier_name
    let x: Double
    let y: Double
    // swiftlint:enable identifier_name

    init(_ frameObject: JSObject) throws {
        guard
            let height = PlayerFrame.getValueFromObject(frameObject, "height"),
            let width = PlayerFrame.getValueFromObject(frameObject, "width"),
            let xValue = PlayerFrame.getValueFromObject(frameObject, "x"),
            let yValue = PlayerFrame.getValueFromObject(frameObject, "y")
        else {
            throw CustomError.frameInvalid
        }
        if width < PlayerFrame.minimumSize || height < PlayerFrame.minimumSize {
            throw CustomError.frameTooSmall
        }
        self.height = height
        self.width = width
        self.x = xValue
        self.y = yValue
    }

    static func getFrameFromCall(_ call: CAPPluginCall) throws -> PlayerFrame {
        guard let frameObject = call.getObject("frame") else {
            throw CustomError.frameMissing
        }
        return try PlayerFrame(frameObject)
    }

    func toCGRect() -> CGRect {
        return CGRect(x: x, y: y, width: width, height: height)
    }

    private static func getValueFromObject(_ object: JSObject, _ key: String) -> Double? {
        if let value = object[key] as? Double {
            return value
        }
        if let value = object[key] as? Int {
            return Double(value)
        }
        if let value = object[key] as? NSNumber {
            return value.doubleValue
        }
        return nil
    }
}
