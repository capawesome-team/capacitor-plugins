import Foundation
import Capacitor

@objc public class GetLocalesResult: NSObject, Result {
    let locales: [LocaleResult]

    init(locales: [LocaleResult]) {
        self.locales = locales
    }

    @objc public func toJSObject() -> AnyObject {
        let localesArray: [JSValue] = locales.compactMap { $0.toJSObject() as? JSObject }
        var result = JSObject()
        result["locales"] = localesArray
        return result as AnyObject
    }
}
