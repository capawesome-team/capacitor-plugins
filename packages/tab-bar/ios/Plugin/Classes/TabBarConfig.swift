import Foundation
import Capacitor

@objc public class TabBarConfig: NSObject {
    let colors: SetColorsOptions
    let tabs: SetTabsOptions?

    init(_ config: PluginConfig) throws {
        self.colors = try SetColorsOptions(selectedColor: config.getString("selectedColor"), unselectedColor: config.getString("unselectedColor"))
        self.tabs = try TabBarConfig.getTabsFromConfig(config)
    }

    private static func getTabsFromConfig(_ config: PluginConfig) throws -> SetTabsOptions? {
        guard let tabs = config.getArray("tabs") else {
            return nil
        }
        return try SetTabsOptions(tabs: tabs as? [JSObject], selectedTabId: config.getString("selectedTabId"))
    }
}
