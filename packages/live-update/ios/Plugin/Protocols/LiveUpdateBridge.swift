import Foundation

@objc public protocol LiveUpdateBridge {
    func getServerBasePath() -> String?
    func setServerBasePath(path: String)
}
