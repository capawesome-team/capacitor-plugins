import Capacitor
import UniformTypeIdentifiers

@objc public class PickFilesOptions: NSObject {
    private static let wildcardContentTypes: [String: UTType] = [
        "audio/*": .audio,
        "image/*": .image,
        "text/*": .text,
        "video/*": .movie
    ]

    private let contentTypes: [UTType]
    private let limit: Int

    init(_ call: CAPPluginCall) {
        let types = call.getArray("types", String.self) ?? []
        let contentTypes = types.compactMap { PickFilesOptions.contentType(forMimeType: $0) }
        self.contentTypes = contentTypes.isEmpty ? [.data] : contentTypes
        self.limit = call.getInt("limit", 0)
    }

    func getContentTypes() -> [UTType] {
        return contentTypes
    }

    func getLimit() -> Int {
        return limit
    }

    private static func contentType(forMimeType mimeType: String) -> UTType? {
        if let wildcardContentType = wildcardContentTypes[mimeType.lowercased()] {
            return wildcardContentType
        }
        guard let contentType = UTType(mimeType: mimeType), !contentType.isDynamic else {
            return nil
        }
        return contentType
    }
}
