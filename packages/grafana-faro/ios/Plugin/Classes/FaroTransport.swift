import Foundation

class FaroTransport {

    private struct Signal {
        let type: FaroSignalType
        let payload: [String: Any]
    }

    private static let itemLimit = 50
    private static let sendTimeoutMs: Int = 250
    private static let requestTimeoutSeconds: TimeInterval = 10

    private let apiKey: String?
    private let meta: FaroMeta
    private let url: URL
    private let session: URLSession
    private let queueLock = NSLock()
    private var queue: [Signal] = []
    private var paused: Bool
    private var pendingFlush: DispatchWorkItem?
    private let dispatchQueue = DispatchQueue(label: "io.capawesome.grafanafaro.transport", qos: .utility)

    init(url: URL, apiKey: String?, meta: FaroMeta, startPaused: Bool) {
        self.apiKey = apiKey
        self.meta = meta
        self.paused = startPaused
        self.url = url
        let configuration = URLSessionConfiguration.ephemeral
        configuration.timeoutIntervalForRequest = FaroTransport.requestTimeoutSeconds
        configuration.timeoutIntervalForResource = FaroTransport.requestTimeoutSeconds
        self.session = URLSession(configuration: configuration)
    }

    func enqueue(type: FaroSignalType, payload: [String: Any]) {
        queueLock.lock()
        queue.append(Signal(type: type, payload: payload))
        let shouldFlushImmediately = queue.count >= FaroTransport.itemLimit
        let needsScheduledFlush = !shouldFlushImmediately && pendingFlush == nil
        queueLock.unlock()

        if shouldFlushImmediately {
            scheduleFlush(afterMs: 0)
        } else if needsScheduledFlush {
            scheduleFlush(afterMs: FaroTransport.sendTimeoutMs)
        }
    }

    func pause() {
        queueLock.lock()
        paused = true
        queueLock.unlock()
    }

    func unpause() {
        queueLock.lock()
        paused = false
        queueLock.unlock()
    }

    private func scheduleFlush(afterMs: Int) {
        let workItem = DispatchWorkItem { [weak self] in
            self?.flush()
        }
        queueLock.lock()
        pendingFlush?.cancel()
        pendingFlush = workItem
        queueLock.unlock()
        dispatchQueue.asyncAfter(deadline: .now() + .milliseconds(afterMs), execute: workItem)
    }

    private func drain() -> [Signal]? {
        queueLock.lock()
        defer { queueLock.unlock() }
        pendingFlush = nil
        if paused || queue.isEmpty {
            return nil
        }
        let snapshot = queue
        queue = []
        return snapshot
    }

    private func flush() {
        guard let snapshot = drain() else { return }
        let body = buildBody(snapshot)
        send(body)
    }

    private func buildBody(_ signals: [Signal]) -> [String: Any] {
        var logs: [[String: Any]] = []
        var events: [[String: Any]] = []
        var exceptions: [[String: Any]] = []
        var measurements: [[String: Any]] = []
        for signal in signals {
            switch signal.type {
            case .log: logs.append(signal.payload)
            case .event: events.append(signal.payload)
            case .exception: exceptions.append(signal.payload)
            case .measurement: measurements.append(signal.payload)
            }
        }
        var body: [String: Any] = ["meta": meta.toDictionary()]
        if !logs.isEmpty { body[FaroSignalType.log.rawValue] = logs }
        if !events.isEmpty { body[FaroSignalType.event.rawValue] = events }
        if !exceptions.isEmpty { body[FaroSignalType.exception.rawValue] = exceptions }
        if !measurements.isEmpty { body[FaroSignalType.measurement.rawValue] = measurements }
        return body
    }

    private func send(_ body: [String: Any]) {
        guard let data = try? JSONSerialization.data(withJSONObject: body, options: []) else {
            return
        }
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        if let key = apiKey, !key.isEmpty {
            request.setValue(key, forHTTPHeaderField: "x-api-key")
        }
        if let sessionId = meta.session?.id {
            request.setValue(sessionId, forHTTPHeaderField: "x-faro-session-id")
        }
        request.httpBody = data
        let task = session.dataTask(with: request) { _, response, error in
            if let error = error {
                NSLog("[GrafanaFaro] Failed to send signals: \(error.localizedDescription)")
                return
            }
            if let httpResponse = response as? HTTPURLResponse, !(200...299).contains(httpResponse.statusCode) {
                NSLog("[GrafanaFaro] Faro collector responded with status \(httpResponse.statusCode)")
            }
        }
        task.resume()
    }
}
