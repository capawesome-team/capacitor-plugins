import Foundation
import Capacitor

@objc public class ActionSheetButton: NSObject {
    static let styleCancel = "CANCEL"
    static let styleDefault = "DEFAULT"
    static let styleDestructive = "DESTRUCTIVE"

    let style: String
    let title: String

    init(_ object: JSObject) throws {
        guard let title = object["title"] as? String else {
            throw CustomError.buttonTitleMissing
        }
        self.title = title
        self.style = object["style"] as? String ?? ActionSheetButton.styleDefault
    }
}
