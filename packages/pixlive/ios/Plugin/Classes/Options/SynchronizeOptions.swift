import Capacitor

@objc public class SynchronizeOptions: NSObject {
    let tags: [[String]]

    init(_ call: CAPPluginCall) throws {
        self.tags = try SynchronizeOptions.getTagsFromCall(call)
    }

    private static func getTagsFromCall(_ call: CAPPluginCall) throws -> [[String]] {
        guard let tagsArray = call.getArray("tags") as? [[String]] else {
            throw CustomError.tagsMissing
        }
        return tagsArray
    }
}
