import Foundation

/// What the plugin reads from the answer to a check. An answer that cannot be used means "no update".
struct CreateCheckResponse {
    struct Bundle {
        let id: String
        let artifactType: ArtifactType
        /// Only `nil` for a template without the placeholder, which is loaded as before the placeholder existed.
        let checksum: String?
        let customProperties: [String: Any]?
        let fileUrlTemplate: FileUrlTemplate
        let signature: String?
    }

    private static let defaultRetryAfterMs: Int64 = 15 * 60 * 1000
    private static let maximumRetryAfterSeconds: Int64 = 24 * 60 * 60
    private static let rateLimitedSkipReason = "RATE_LIMITED"

    let outcome: CheckOutcome?
    let skipReason: String?
    let channelName: String?
    let bundle: Bundle?
    let retryAfterMs: Int64?
    let clearsPendingFailure: Bool

    static func parse(statusCode: Int, retryAfter: String?, body: String?) -> CreateCheckResponse {
        switch statusCode {
        case 200:
            return parseProcessedAnswer(body: body)
        case 429:
            return rateLimited(retryAfterMs: parseRetryAfterMs(retryAfter))
        case 400..<500:
            return noUpdate(clearsPendingFailure: true)
        default:
            return noUpdate(clearsPendingFailure: false)
        }
    }

    static func rateLimited(retryAfterMs: Int64?) -> CreateCheckResponse {
        return CreateCheckResponse(outcome: .skipped, skipReason: rateLimitedSkipReason, channelName: nil, bundle: nil,
                                   retryAfterMs: retryAfterMs, clearsPendingFailure: false)
    }

    private static func noUpdate(clearsPendingFailure: Bool) -> CreateCheckResponse {
        return CreateCheckResponse(outcome: nil, skipReason: nil, channelName: nil, bundle: nil,
                                   retryAfterMs: nil, clearsPendingFailure: clearsPendingFailure)
    }

    private static func parseBundle(_ object: Any?) -> Bundle? {
        guard let object = object as? [String: Any],
              let id = readText(object["id"]),
              let artifactType = readText(object["artifactType"]).flatMap(ArtifactType.init(rawValue:)),
              let fileUrlTemplateValue = readText(object["fileUrlTemplate"]) else {
            return nil
        }
        let checksum = readText(object["checksum"])
        let fileUrlTemplate = FileUrlTemplate(value: fileUrlTemplateValue)
        if fileUrlTemplate.hasChecksumPlaceholder && checksum == nil {
            return nil
        }
        return Bundle(id: id, artifactType: artifactType, checksum: checksum,
                      customProperties: object["customProperties"] as? [String: Any],
                      fileUrlTemplate: fileUrlTemplate, signature: readText(object["signature"]))
    }

    private static func parseJsonObject(_ text: String?) -> [String: Any]? {
        guard let data = text?.data(using: .utf8) else {
            return nil
        }
        return (try? JSONSerialization.jsonObject(with: data)) as? [String: Any]
    }

    private static func parseProcessedAnswer(body: String?) -> CreateCheckResponse {
        guard let object = parseJsonObject(body) else {
            return noUpdate(clearsPendingFailure: false)
        }
        let channelName = readText(object["channelName"])
        switch readText(object["result"]).flatMap(CheckOutcome.init(rawValue:)) {
        case .available:
            guard let bundle = parseBundle(object["bundle"]) else {
                return noUpdate(clearsPendingFailure: true)
            }
            return CreateCheckResponse(outcome: .available, skipReason: nil, channelName: channelName, bundle: bundle,
                                       retryAfterMs: nil, clearsPendingFailure: true)
        case .skipped:
            // `uppercased()` does not depend on the locale of the device
            let skipReason = readText(object["reason"])?.uppercased()
            return CreateCheckResponse(outcome: .skipped, skipReason: skipReason, channelName: channelName, bundle: nil,
                                       retryAfterMs: nil, clearsPendingFailure: true)
        case .upToDate:
            return CreateCheckResponse(outcome: .upToDate, skipReason: nil, channelName: channelName, bundle: nil,
                                       retryAfterMs: nil, clearsPendingFailure: true)
        case nil:
            return noUpdate(clearsPendingFailure: true)
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
