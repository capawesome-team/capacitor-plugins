import Capacitor
import Foundation

@objc public class PushErrorOptions: NSObject {
    private let context: [String: String]?
    private let fatal: Bool
    private let stackFrames: [StackFrameOptions]?
    private let type: String
    private let value: String

    init(call: CAPPluginCall) throws {
        guard let type = call.getString("type") else {
            throw CustomError.typeMissing
        }
        guard let value = call.getString("value") else {
            throw CustomError.valueMissing
        }
        self.context = call.getObject("context") as? [String: String]
        self.fatal = call.getBool("fatal") ?? false
        self.stackFrames = PushErrorOptions.parseStackFrames(call.getArray("stackFrames"))
        self.type = type
        self.value = value
    }

    func getContext() -> [String: String]? {
        return context
    }

    func getStackFrames() -> [StackFrameOptions]? {
        return stackFrames
    }

    func getType() -> String {
        return type
    }

    func getValue() -> String {
        return value
    }

    func isFatal() -> Bool {
        return fatal
    }

    private static func parseStackFrames(_ array: JSArray?) -> [StackFrameOptions]? {
        guard let array = array, !array.isEmpty else {
            return nil
        }
        var frames: [StackFrameOptions] = []
        for entry in array {
            guard let entry = entry as? JSObject else { continue }
            frames.append(StackFrameOptions(source: entry))
        }
        return frames.isEmpty ? nil : frames
    }
}
