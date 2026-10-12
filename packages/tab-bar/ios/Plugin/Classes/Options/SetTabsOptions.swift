import Foundation
import Capacitor

@objc public class SetTabsOptions: NSObject {
    let selectedTabId: String?
    let tabs: [Tab]

    convenience init(_ call: CAPPluginCall) throws {
        try self.init(tabs: call.getArray("tabs", JSObject.self), selectedTabId: call.getString("selectedTabId"))
    }

    init(tabs objects: [JSObject]?, selectedTabId: String?) throws {
        let tabs = try SetTabsOptions.parseTabs(objects)
        if let selectedTabId = selectedTabId, !tabs.contains(where: { $0.id == selectedTabId }) {
            throw CustomError.selectedTabIdInvalid
        }
        self.selectedTabId = selectedTabId
        self.tabs = tabs
    }

    private static func parseTabs(_ objects: [JSObject]?) throws -> [Tab] {
        guard let objects = objects else {
            throw CustomError.tabsMissing
        }
        if objects.isEmpty {
            throw CustomError.tabsEmpty
        }
        let tabs = try objects.map { try Tab($0) }
        if Set(tabs.map { $0.id }).count != tabs.count {
            throw CustomError.tabIdsNotUnique
        }
        return tabs
    }
}
