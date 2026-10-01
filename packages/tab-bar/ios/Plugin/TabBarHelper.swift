import Foundation
import UIKit

public class TabBarHelper {
    public static func parseColor(_ string: String) -> UIColor? {
        guard string.hasPrefix("#") else {
            return nil
        }
        let hex = String(string.dropFirst())
        guard hex.count == 6 || hex.count == 8, let value = UInt64(hex, radix: 16) else {
            return nil
        }
        let alpha = hex.count == 8 ? CGFloat((value >> 24) & 0xFF) / 255.0 : 1.0
        let red = CGFloat((value >> 16) & 0xFF) / 255.0
        let green = CGFloat((value >> 8) & 0xFF) / 255.0
        let blue = CGFloat(value & 0xFF) / 255.0
        return UIColor(red: red, green: green, blue: blue, alpha: alpha)
    }
}
