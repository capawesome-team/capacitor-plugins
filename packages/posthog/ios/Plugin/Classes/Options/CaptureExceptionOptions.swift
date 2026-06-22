import Foundation
import Capacitor

@objc public class CaptureExceptionOptions: NSObject {
    private var message: String
    private var name: String?
    private var stacktrace: [StackFrame]?
    private var properties: [String: Any]?

    init(message: String, name: String?, stacktrace: JSArray?, properties: JSObject?) {
        self.message = message
        self.name = name
        self.stacktrace = CaptureExceptionOptions.createStackFrames(stacktrace)
        self.properties = PosthogHelper.createHashMapFromJSObject(properties)
    }

    func getMessage() -> String {
        return message
    }

    func getName() -> String? {
        return name
    }

    func getStacktrace() -> [StackFrame]? {
        return stacktrace
    }

    func getProperties() -> [String: Any]? {
        return properties
    }

    private static func createStackFrames(_ stacktrace: JSArray?) -> [StackFrame]? {
        guard let stacktrace = stacktrace else {
            return nil
        }
        var frames: [StackFrame] = []
        for element in stacktrace {
            if let frame = element as? JSObject {
                frames.append(StackFrame(frame))
            }
        }
        return frames
    }
}
