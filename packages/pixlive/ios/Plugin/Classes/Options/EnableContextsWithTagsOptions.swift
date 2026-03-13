import Capacitor

@objc public class EnableContextsWithTagsOptions: NSObject {
    let tags: [String]

    init(_ call: CAPPluginCall) throws {
        self.tags = try EnableContextsWithTagsOptions.getTagsFromCall(call)
    }

    private static func getTagsFromCall(_ call: CAPPluginCall) throws -> [String] {
        guard let tags = call.getArray("tags") as? [String] else {
            throw CustomError.tagsMissing
        }
        return tags
    }
}
