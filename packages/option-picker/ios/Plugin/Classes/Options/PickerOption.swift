import Foundation
import Capacitor

@objc public class PickerOption: NSObject {
    let label: String
    let value: String

    init(_ object: JSObject) throws {
        guard let label = object["label"] as? String else {
            throw CustomError.optionLabelMissing
        }
        guard let value = object["value"] as? String else {
            throw CustomError.optionValueMissing
        }
        self.label = label
        self.value = value
    }
}
