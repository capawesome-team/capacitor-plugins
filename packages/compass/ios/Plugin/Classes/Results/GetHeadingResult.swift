import Foundation
import Capacitor

@objc public class GetHeadingResult: NSObject, Result {
    private let heading: Heading

    init(heading: Heading) {
        self.heading = heading
    }

    @objc public func toJSObject() -> AnyObject {
        return heading.toJSObject()
    }
}
