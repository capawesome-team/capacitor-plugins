import Capacitor

@objc public class ResizeARViewOptions: NSObject {
    // swiftlint:disable:next identifier_name
    let x: Int
    // swiftlint:disable:next identifier_name
    let y: Int
    let width: Int
    let height: Int

    init(_ call: CAPPluginCall) throws {
        self.x = try ResizeARViewOptions.getXFromCall(call)
        self.y = try ResizeARViewOptions.getYFromCall(call)
        self.width = try ResizeARViewOptions.getWidthFromCall(call)
        self.height = try ResizeARViewOptions.getHeightFromCall(call)
    }

    private static func getXFromCall(_ call: CAPPluginCall) throws -> Int {
        // swiftlint:disable:next identifier_name
        guard let x = call.getInt("x") else {
            throw CustomError.xMissing
        }
        return x
    }

    private static func getYFromCall(_ call: CAPPluginCall) throws -> Int {
        // swiftlint:disable:next identifier_name
        guard let y = call.getInt("y") else {
            throw CustomError.yMissing
        }
        return y
    }

    private static func getWidthFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let width = call.getInt("width") else {
            throw CustomError.widthMissing
        }
        return width
    }

    private static func getHeightFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let height = call.getInt("height") else {
            throw CustomError.heightMissing
        }
        return height
    }
}
