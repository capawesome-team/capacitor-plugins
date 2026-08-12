import Foundation
import Capacitor

@objc public class HeadingChangeEvent: NSObject, Result {
    private let heading: Heading

    init(heading: Heading) {
        self.heading = heading
    }

    @objc public func toJSObject() -> AnyObject {
        return heading.toJSObject()
    }
}
