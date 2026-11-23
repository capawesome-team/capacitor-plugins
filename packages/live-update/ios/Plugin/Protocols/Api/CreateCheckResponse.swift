import Foundation

/// What the plugin reads from the answer to a check. A 200 that cannot be used means "no update".
struct CreateCheckResponse {
    struct Bundle {
        let id: String
        let artifactType: ArtifactType
        /// Always set with `urlTemplate`.
        let checksum: String?
        let customProperties: [String: Any]?
        let signature: String?
        /// The plain address of the bundle: `url`, or else `legacyUrl`.
        let url: String
        let urlTemplate: UrlTemplate?
    }

    private static let defaultRetryAfterMs: Int64 = 15 * 60 * 1000
    private static let maximumRetryAfterSeconds: Int64 = 24 * 60 * 60

    let outcome: CheckOutcome?
    /// The reason as the server sent it, only set if the check was skipped.
    let reason: String?
    let channelName: String?
    let bundle: Bundle?
    let retryAfterMs: Int64?

    /// - Throws: `CustomError.requestFailed` for every status other than 200 and 429.
    static func parse(statusCode: Int, retryAfter: String?, body: String?) throws -> CreateCheckResponse {
        switch statusCode {
        case 200:
            return parseProcessedAnswer(body: body)
        case 429:
            return CreateCheckResponse(outcome: nil, reason: nil, channelName: nil, bundle: nil,
                                       retryAfterMs: parseRetryAfterMs(retryAfter))
        default:
            throw CustomError.requestFailed(message: "The check failed with status \(statusCode).")
        }
    }

    /// An answer without an update and without a wait.
    static func noUpdate() -> CreateCheckResponse {
        return CreateCheckResponse(outcome: nil, reason: nil, channelName: nil, bundle: nil, retryAfterMs: nil)
    }

    /// The plain address is `url`, or else `legacyUrl`. A `urlTemplate` is optional, but it needs `checksum` and the placeholder.
    private static func parseBundle(_ object: Any?) -> Bundle? {
        guard let object = object as? [String: Any],
              let id = readText(object["id"]),
              let artifactType = readText(object["artifactType"]).flatMap(ArtifactType.init(rawValue:)),
              let url = readText(object["url"]) ?? readText(object["legacyUrl"]) else {
            return nil
        }
        let checksum = readText(object["checksum"])
        let urlTemplate = readText(object["urlTemplate"]).map(UrlTemplate.init(value:))
        if let urlTemplate = urlTemplate, checksum == nil || !urlTemplate.hasChecksumPlaceholder {
            return nil
        }
        return Bundle(id: id, artifactType: artifactType, checksum: checksum,
                      customProperties: object["customProperties"] as? [String: Any],
                      signature: readText(object["signature"]), url: url, urlTemplate: urlTemplate)
    }

    private static func parseJsonObject(_ text: String?) -> [String: Any]? {
        guard let data = text?.data(using: .utf8) else {
            return nil
        }
        return (try? JSONSerialization.jsonObject(with: data)) as? [String: Any]
    }

    private static func parseProcessedAnswer(body: String?) -> CreateCheckResponse {
        guard let object = parseJsonObject(body) else {
            return noUpdate()
        }
        let channelName = readText(object["channelName"])
        switch readText(object["result"]).flatMap(CheckOutcome.init(rawValue:)) {
        case .available:
            guard let bundle = parseBundle(object["bundle"]) else {
                return noUpdate()
            }
            return CreateCheckResponse(outcome: .available, reason: nil, channelName: channelName, bundle: bundle, retryAfterMs: nil)
        case .skipped:
            return CreateCheckResponse(outcome: .skipped, reason: readText(object["reason"]), channelName: channelName, bundle: nil,
                                       retryAfterMs: nil)
        case .upToDate:
            return CreateCheckResponse(outcome: .upToDate, reason: nil, channelName: channelName, bundle: nil, retryAfterMs: nil)
        case nil:
            return noUpdate()
        }
    }

    /// Reads a whole number of seconds that is not negative. A number too large for `Int64` gives the maximum.
    private static func parseRetryAfterMs(_ retryAfter: String?) -> Int64 {
        guard let retryAfter = retryAfter, !retryAfter.isEmpty, retryAfter.allSatisfy({ $0.isASCII && $0.isNumber }) else {
            return defaultRetryAfterMs
        }
        let seconds = Int64(retryAfter) ?? maximumRetryAfterSeconds
        return min(seconds, maximumRetryAfterSeconds) * 1000
    }

    /// - Returns: The text, or `nil` for a missing value, JSON `null`, a value that is no text or an empty text.
    private static func readText(_ value: Any?) -> String? {
        guard let text = value as? String, !text.isEmpty else {
            return nil
        }
        return text
    }
}
