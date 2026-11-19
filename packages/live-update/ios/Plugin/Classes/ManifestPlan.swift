import Foundation

/// Which files of a new manifest are copied from the current bundle and which are downloaded.
struct ManifestPlan {
    struct Copy {
        /// The path in the current bundle that holds the content of the item.
        let sourceHref: String
        let item: ManifestItem
    }

    let copies: [Copy]
    let downloads: [ManifestItem]

    /// - Parameter currentManifest: `nil` if the current bundle has no usable manifest. Every file is downloaded then.
    init(latestManifest: Manifest, currentManifest: Manifest?) {
        var hrefsByChecksum = [String: String]()
        for item in currentManifest?.items ?? [] where hrefsByChecksum[item.checksum] == nil {
            hrefsByChecksum[item.checksum] = item.href
        }
        var copies = [Copy]()
        var downloads = [ManifestItem]()
        for item in latestManifest.items {
            if let sourceHref = hrefsByChecksum[item.checksum] {
                copies.append(Copy(sourceHref: sourceHref, item: item))
            } else {
                downloads.append(item)
            }
        }
        self.copies = copies
        self.downloads = downloads
    }
}
