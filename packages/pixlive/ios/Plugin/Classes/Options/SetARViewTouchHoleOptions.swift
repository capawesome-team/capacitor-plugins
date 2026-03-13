import Capacitor

@objc public class SetARViewTouchHoleOptions: NSObject {
    let top: Int
    let bottom: Int
    let left: Int
    let right: Int

    init(_ call: CAPPluginCall) throws {
        self.top = try SetARViewTouchHoleOptions.getTopFromCall(call)
        self.bottom = try SetARViewTouchHoleOptions.getBottomFromCall(call)
        self.left = try SetARViewTouchHoleOptions.getLeftFromCall(call)
        self.right = try SetARViewTouchHoleOptions.getRightFromCall(call)
    }

    private static func getTopFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let value = call.getInt("top") else {
            throw CustomError.topMissing
        }
        return value
    }

    private static func getBottomFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let value = call.getInt("bottom") else {
            throw CustomError.bottomMissing
        }
        return value
    }

    private static func getLeftFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let value = call.getInt("left") else {
            throw CustomError.leftMissing
        }
        return value
    }

    private static func getRightFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let value = call.getInt("right") else {
            throw CustomError.rightMissing
        }
        return value
    }
}
