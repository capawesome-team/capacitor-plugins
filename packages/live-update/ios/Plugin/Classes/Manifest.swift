import Foundation
import Capacitor

@objc public class Manifest: NSObject, Codable {
    let items: [ManifestItem]

    init(items: [ManifestItem]) {
        self.items = items
    }

    public static func findDuplicateItems(_ manifest1: Manifest, _ manifest2: Manifest) -> [ManifestItem] {
        let checksumSet = Set(manifest2.items.map { $0.checksum })
        return manifest1.items.filter { checksumSet.contains($0.checksum) }
    }

    public static func findMissingItems(_ manifest1: Manifest, _ manifest2: Manifest) -> [ManifestItem] {
        let checksumSet = Set(manifest2.items.map { $0.checksum })
        return manifest1.items.filter { !checksumSet.contains($0.checksum) }
    }
}
