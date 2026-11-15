import Foundation
import Capacitor

@objc public class Manifest: NSObject, Codable {
    let items: [ManifestItem]

    init(items: [ManifestItem]) {
        self.items = items
    }

    /// Throws a `DecodingError` if the data is not a JSON list of items with `href` and `checksum`.
    static func parse(_ data: Data) throws -> Manifest {
        let items = try JSONDecoder().decode([ManifestItem].self, from: data)
        return Manifest(items: items)
    }
}
