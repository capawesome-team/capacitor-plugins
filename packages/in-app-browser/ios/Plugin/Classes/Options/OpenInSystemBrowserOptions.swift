import Foundation
import Capacitor
import SafariServices
import UIKit

@objc public class OpenInSystemBrowserOptions: NSObject {
    let barCollapsing: Bool
    let dismissButtonStyle: SFSafariViewController.DismissButtonStyle
    let readerMode: Bool
    let toolbarColor: UIColor?
    let url: URL

    init(_ call: CAPPluginCall) throws {
        let iosOptions = call.getObject("ios")
        self.barCollapsing = iosOptions?["barCollapsing"] as? Bool ?? true
        self.dismissButtonStyle = OpenInSystemBrowserOptions.getDismissButtonStyleFromObject(iosOptions)
        self.readerMode = iosOptions?["readerMode"] as? Bool ?? false
        self.toolbarColor = try OpenInSystemBrowserOptions.getToolbarColorFromObject(iosOptions)
        self.url = try OpenInSystemBrowserOptions.getUrlFromCall(call)
    }

    private static func getDismissButtonStyleFromObject(_ object: JSObject?) -> SFSafariViewController.DismissButtonStyle {
        switch object?["dismissButtonStyle"] as? String {
        case "cancel":
            return .cancel
        case "close":
            return .close
        default:
            return .done
        }
    }

    private static func getToolbarColorFromObject(_ object: JSObject?) throws -> UIColor? {
        guard let toolbarColor = object?["toolbarColor"] as? String else {
            return nil
        }
        guard let color = InAppBrowserHelper.parseColor(toolbarColor) else {
            throw CustomError.toolbarColorInvalid
        }
        return color
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let urlString = call.getString("url"), !urlString.isEmpty else {
            throw CustomError.urlMissing
        }
        guard let url = URL(string: urlString), let scheme = url.scheme, ["http", "https"].contains(scheme.lowercased()) else {
            throw CustomError.urlInvalid
        }
        return url
    }
}
