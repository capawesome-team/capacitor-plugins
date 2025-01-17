import Foundation
import Capacitor

@objc public class AliasOptions: NSObject {
    private var alias: String

    init(alias: String) {
        self.alias = alias
    }

    func getAlias() -> String {
        return alias
    }
}
