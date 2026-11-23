import Foundation
import Capacitor

@objc public class Manifest: NSObject, Codable {
    let items: [ManifestItem]

    init(items: [ManifestItem]) {
        self.items = items
    }

    /// - Returns: Per checksum, the first path that holds this content.
    func mapFirstHrefsByChecksum() -> [String: String] {
        var hrefsByChecksum = [String: String]()
        for item in items where hrefsByChecksum[item.checksum] == nil {
            hrefsByChecksum[item.checksum] = item.href
        }
        return hrefsByChecksum
    }

    /// - Returns: `nil` if the data is not a JSON list or an item cannot be used.
    static func parse(_ data: Data) -> Manifest? {
        guard let items = try? JSONDecoder().decode([ManifestItem].self, from: data) else {
            return nil
        }
        return Manifest(items: items)
    }
}
