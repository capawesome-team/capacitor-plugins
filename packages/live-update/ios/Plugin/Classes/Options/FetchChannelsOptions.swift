import Foundation
import Capacitor

@objc public class FetchChannelsOptions: NSObject {
    private var limit: Int?
    private var offset: Int?
    private var query: String?

    init(_ call: CAPPluginCall) {
        self.limit = call.getInt("limit") as Int?
        self.offset = call.getInt("offset") as Int?
        self.query = call.getString("query")
    }

    func getLimit() -> Int? {
        return limit
    }

    func getOffset() -> Int? {
        return offset
    }

    func getQuery() -> String? {
        return query
    }
}
