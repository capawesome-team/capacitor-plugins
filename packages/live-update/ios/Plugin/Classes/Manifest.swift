import Foundation
import Capacitor

@objc public class Manifest: NSObject, Codable {
    let items: [ManifestItem]

    init(items: [ManifestItem]) {
        self.items = items
    }
    
    public static func findDuplicateItems(_ manifest1: Manifest, _ manifest2: Manifest) -> [ManifestItem] {
        var duplicateItems = [ManifestItem]()
        for item1 in manifest1.items {
            for item2 in manifest2.items {
                if item1.checksum == item2.checksum {
                    duplicateItems.append(item1)
                    break
                }
            }
        }
        return duplicateItems
    }

    public static func findMissingItems(_ manifest1: Manifest, _ manifest2: Manifest) -> [ManifestItem] {
        var missingItems = [ManifestItem]()
        for item1 in manifest1.items {
            var found = false
            for item2 in manifest2.items {
                if item1.checksum == item2.checksum {
                    found = true
                    break
                }
            }
            if !found {
                missingItems.append(item1)
            }
        }
        return missingItems
    }
}
