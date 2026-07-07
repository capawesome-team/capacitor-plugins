import Foundation

@objc public protocol Result {
    @objc func toJSObject() -> AnyObject
}
