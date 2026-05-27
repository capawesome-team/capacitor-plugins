import Capacitor
import Foundation

@objc public class GrafanaFaro: NSObject {

    private var meta: FaroMeta?
    private var transport: FaroTransport?
    private var crashReporter: FaroCrashReporter?
    private var sessionSamplingRate: Double = 1.0

    func initialize(_ options: InitializeOptions) throws {
        try initializeInternal(
            url: options.getUrl(),
            apiKey: options.getApiKey(),
            app: options.getApp(),
            sessionSamplingRate: options.getSessionSamplingRate(),
            sessionAttributes: options.getSessionAttributes(),
            user: options.getUser(),
            view: options.getView(),
            paused: options.isPaused(),
            nativeCrashReporting: options.getInstrumentations().isNativeCrashReportingEnabled()
        )
    }

    func initializeFromConfig(_ config: GrafanaFaroConfig) throws {
        guard let urlString = config.url, let url = URL(string: urlString) else { return }
        guard let appName = config.appName, !appName.isEmpty else { return }
        var appObject: JSObject = ["name": appName]
        if let value = config.appVersion { appObject["version"] = value }
        if let value = config.appEnvironment { appObject["environment"] = value }
        if let value = config.appNamespace { appObject["namespace"] = value }
        let app = try AppMetadata(source: appObject)
        try initializeInternal(
            url: url,
            apiKey: config.apiKey,
            app: app,
            sessionSamplingRate: 1.0,
            sessionAttributes: nil,
            user: nil,
            view: nil,
            paused: false,
            nativeCrashReporting: config.nativeCrashReporting
        )
    }

    private func initializeInternal(
        url: URL,
        apiKey: String?,
        app: AppMetadata,
        sessionSamplingRate: Double,
        sessionAttributes: [String: String]?,
        user: UserMetadata?,
        view: ViewMetadata?,
        paused: Bool,
        nativeCrashReporting: Bool
    ) throws {
        if transport != nil {
            throw CustomError.alreadyInitialized
        }
        self.sessionSamplingRate = sessionSamplingRate
        let meta = FaroMeta(app: app)
        let session = FaroSession.create(samplingRate: sessionSamplingRate)
        if let attrs = sessionAttributes {
            session.attributes = attrs
        }
        meta.session = session
        meta.user = user
        meta.view = view

        let transport = FaroTransport(
            url: url,
            apiKey: apiKey,
            meta: meta,
            startPaused: paused
        )
        self.meta = meta
        self.transport = transport

        if nativeCrashReporting {
            let reporter = FaroCrashReporter()
            if let previous = reporter.collectPreviousCrash() {
                pushPreviousCrash(previous, transport: transport)
            }
            reporter.install()
            self.crashReporter = reporter
        }
    }

    func getSession() -> [String: Any] {
        guard let session = meta?.session else { return [:] }
        var result: [String: Any] = ["id": session.id]
        if !session.attributes.isEmpty {
            result["attributes"] = session.attributes
        }
        return result
    }

    func getView() -> [String: Any] {
        var result: [String: Any] = [:]
        if let view = meta?.view {
            result["name"] = view.getName()
        }
        return result
    }

    func pause() throws {
        let transport = try requireTransport()
        transport.pause()
    }

    func unpause() throws {
        let transport = try requireTransport()
        transport.unpause()
    }

    func pushLog(_ options: PushLogOptions) throws {
        let transport = try requireTransport()
        var payload: [String: Any] = [
            "message": options.getMessage(),
            "level": options.getLevel(),
            "timestamp": currentTimestamp()
        ]
        if let context = options.getContext(), !context.isEmpty {
            payload["context"] = context
        }
        transport.enqueue(type: .log, payload: payload)
    }

    func pushEvent(_ options: PushEventOptions) throws {
        let transport = try requireTransport()
        var payload: [String: Any] = [
            "name": options.getName(),
            "domain": options.getDomain(),
            "timestamp": currentTimestamp()
        ]
        if let attributes = options.getAttributes(), !attributes.isEmpty {
            payload["attributes"] = attributes
        }
        transport.enqueue(type: .event, payload: payload)
    }

    func pushError(_ options: PushErrorOptions) throws {
        let transport = try requireTransport()
        var payload: [String: Any] = [
            "type": options.getType(),
            "value": options.getValue(),
            "fatal": options.isFatal(),
            "timestamp": currentTimestamp()
        ]
        if let context = options.getContext(), !context.isEmpty {
            payload["context"] = context
        }
        if let frames = buildStackFrames(options.getStackFrames()), !frames.isEmpty {
            payload["stacktrace"] = ["frames": frames]
        }
        transport.enqueue(type: .exception, payload: payload)
    }

    func pushMeasurement(_ options: PushMeasurementOptions) throws {
        let transport = try requireTransport()
        var payload: [String: Any] = [
            "type": options.getType(),
            "values": options.getValues(),
            "timestamp": currentTimestamp()
        ]
        if let context = options.getContext(), !context.isEmpty {
            payload["context"] = context
        }
        transport.enqueue(type: .measurement, payload: payload)
    }

    func resetSession() throws {
        let meta = try requireMeta()
        meta.session = FaroSession.create(samplingRate: sessionSamplingRate)
    }

    func resetUser() throws {
        let meta = try requireMeta()
        meta.user = nil
    }

    func setSession(_ options: SetSessionOptions) throws {
        let meta = try requireMeta()
        let session = FaroSession.create(id: options.getId(), samplingRate: sessionSamplingRate)
        if let attributes = options.getAttributes() {
            session.attributes = attributes
        }
        meta.session = session
    }

    func setUser(_ user: UserMetadata) throws {
        let meta = try requireMeta()
        meta.user = user
    }

    func setView(_ view: ViewMetadata) throws {
        let meta = try requireMeta()
        meta.view = view
    }

    private func buildStackFrames(_ source: [StackFrameOptions]?) -> [[String: Any]]? {
        guard let source = source, !source.isEmpty else { return nil }
        var frames: [[String: Any]] = []
        for frame in source {
            var frameDict: [String: Any] = [
                "filename": frame.getFileName() ?? "",
                "function": frame.getFunctionName() ?? ""
            ]
            if let line = frame.getLineNumber() {
                frameDict["lineno"] = line
            }
            if let col = frame.getColumnNumber() {
                frameDict["colno"] = col
            }
            frames.append(frameDict)
        }
        return frames
    }

    private func pushPreviousCrash(_ crash: PreviousCrash, transport: FaroTransport) {
        var payload: [String: Any] = [
            "type": crash.type,
            "value": crash.value,
            "fatal": true,
            "timestamp": formatTimestamp(crash.timestamp)
        ]
        if !crash.frames.isEmpty {
            payload["stacktrace"] = ["frames": crash.frames]
        }
        transport.enqueue(type: .exception, payload: payload)
    }

    private func requireMeta() throws -> FaroMeta {
        guard let meta = meta else {
            throw CustomError.notInitialized
        }
        return meta
    }

    private func requireTransport() throws -> FaroTransport {
        guard let transport = transport else {
            throw CustomError.notInitialized
        }
        return transport
    }

    private func currentTimestamp() -> String {
        return formatTimestamp(Date())
    }

    private func formatTimestamp(_ date: Date) -> String {
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        return formatter.string(from: date)
    }
}
