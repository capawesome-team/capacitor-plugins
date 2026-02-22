import Capacitor

@objc public class SynchronizeWithToursAndContextsOptions: NSObject {
    let tags: [[String]]
    let tourIds: [Int]
    let contextIds: [String]

    init(_ call: CAPPluginCall) throws {
        self.tags = try SynchronizeWithToursAndContextsOptions.getTagsFromCall(call)
        self.tourIds = try SynchronizeWithToursAndContextsOptions.getTourIdsFromCall(call)
        self.contextIds = try SynchronizeWithToursAndContextsOptions.getContextIdsFromCall(call)
    }

    private static func getTagsFromCall(_ call: CAPPluginCall) throws -> [[String]] {
        guard let tagsArray = call.getArray("tags") as? [[String]] else {
            throw CustomError.tagsMissing
        }
        return tagsArray
    }

    private static func getTourIdsFromCall(_ call: CAPPluginCall) throws -> [Int] {
        guard let tourIds = call.getArray("tourIds") as? [Int] else {
            throw CustomError.tourIdsMissing
        }
        return tourIds
    }

    private static func getContextIdsFromCall(_ call: CAPPluginCall) throws -> [String] {
        guard let contextIds = call.getArray("contextIds") as? [String] else {
            throw CustomError.contextIdsMissing
        }
        return contextIds
    }
}
