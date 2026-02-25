import Capacitor

@objc public class UpdateTagMappingOptions: NSObject {
    let tags: [String]

    init(_ call: CAPPluginCall) throws {
        self.tags = try UpdateTagMappingOptions.getTagsFromCall(call)
    }

    private static func getTagsFromCall(_ call: CAPPluginCall) throws -> [String] {
        guard let tags = call.getArray("tags") as? [String] else {
            throw CustomError.tagsMissing
        }
        return tags
    }
}
