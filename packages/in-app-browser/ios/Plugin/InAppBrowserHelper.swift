import Foundation
import UIKit

public class InAppBrowserHelper {
    public static func isCookie(_ cookie: HTTPCookie, matchingURL url: URL) -> Bool {
        guard let host = url.host else {
            return false
        }
        guard isHost(host, matchingDomain: cookie.domain) else {
            return false
        }
        return isPath(url.path.isEmpty ? "/" : url.path, matchingCookiePath: cookie.path)
    }

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

    private static func isHost(_ host: String, matchingDomain domain: String) -> Bool {
        let normalizedDomain = (domain.hasPrefix(".") ? String(domain.dropFirst()) : domain).lowercased()
        let normalizedHost = host.lowercased()
        return normalizedHost == normalizedDomain || normalizedHost.hasSuffix("." + normalizedDomain)
    }

    private static func isPath(_ path: String, matchingCookiePath cookiePath: String) -> Bool {
        if path == cookiePath {
            return true
        }
        guard path.hasPrefix(cookiePath) else {
            return false
        }
        if cookiePath.hasSuffix("/") {
            return true
        }
        let index = path.index(path.startIndex, offsetBy: cookiePath.count)
        return path[index] == "/"
    }
}
