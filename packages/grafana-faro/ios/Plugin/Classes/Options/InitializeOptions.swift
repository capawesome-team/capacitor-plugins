import Capacitor
import Foundation

@objc public class InitializeOptions: NSObject {
    private let apiKey: String?
    private let app: AppMetadata
    private let instrumentations: InstrumentationsOptions
    private let paused: Bool
    private let sessionAttributes: [String: String]?
    private let sessionSamplingRate: Double
    private let url: URL
    private let user: UserMetadata?
    private let view: ViewMetadata?

    init(call: CAPPluginCall) throws {
        guard let urlString = call.getString("url"), !urlString.isEmpty, let url = URL(string: urlString) else {
            throw CustomError.urlMissing
        }
        guard let appObject = call.getObject("app") else {
            throw CustomError.appMissing
        }
        self.apiKey = call.getString("apiKey")
        self.app = try AppMetadata(source: appObject)
        self.instrumentations = InstrumentationsOptions(source: call.getObject("instrumentations"))
        self.paused = call.getBool("paused") ?? false
        self.sessionAttributes = call.getObject("sessionAttributes") as? [String: String]
        self.sessionSamplingRate = call.getDouble("sessionSamplingRate") ?? 1.0
        self.url = url
        self.user = (call.getObject("user")).map { UserMetadata(source: $0) }
        self.view = try (call.getObject("view")).map { try ViewMetadata(source: $0) }
    }

    func getApiKey() -> String? {
        return apiKey
    }

    func getApp() -> AppMetadata {
        return app
    }

    func getInstrumentations() -> InstrumentationsOptions {
        return instrumentations
    }

    func getSessionAttributes() -> [String: String]? {
        return sessionAttributes
    }

    func getSessionSamplingRate() -> Double {
        return sessionSamplingRate
    }

    func getUrl() -> URL {
        return url
    }

    func getUser() -> UserMetadata? {
        return user
    }

    func getView() -> ViewMetadata? {
        return view
    }

    func isPaused() -> Bool {
        return paused
    }
}
