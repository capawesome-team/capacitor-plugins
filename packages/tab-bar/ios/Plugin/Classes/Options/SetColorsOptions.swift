import Foundation
import Capacitor

@objc public class SetColorsOptions: NSObject {
    let selectedColor: UIColor?
    let unselectedColor: UIColor?

    convenience init(_ call: CAPPluginCall) throws {
        try self.init(selectedColor: call.getString("selectedColor"), unselectedColor: call.getString("unselectedColor"))
    }

    init(selectedColor: String?, unselectedColor: String?) throws {
        self.selectedColor = try SetColorsOptions.parseColor(selectedColor, invalidError: .selectedColorInvalid)
        self.unselectedColor = try SetColorsOptions.parseColor(unselectedColor, invalidError: .unselectedColorInvalid)
    }

    private static func parseColor(_ value: String?, invalidError: CustomError) throws -> UIColor? {
        guard let value = value else {
            return nil
        }
        guard let color = TabBarHelper.parseColor(value) else {
            throw invalidError
        }
        return color
    }
}
