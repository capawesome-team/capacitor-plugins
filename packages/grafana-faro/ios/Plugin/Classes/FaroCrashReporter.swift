import CrashReporter
import Foundation

class FaroCrashReporter {

    private let reporter: PLCrashReporter

    init() {
        let config = PLCrashReporterConfig(
            signalHandlerType: .BSD,
            symbolicationStrategy: .all
        )
        self.reporter = PLCrashReporter(configuration: config) ?? PLCrashReporter()
    }

    func install() {
        do {
            try reporter.enableAndReturnError()
        } catch {
            NSLog("[GrafanaFaro] Failed to enable PLCrashReporter: \(error.localizedDescription)")
        }
    }

    func collectPreviousCrash() -> PreviousCrash? {
        guard reporter.hasPendingCrashReport() else {
            return nil
        }
        defer {
            reporter.purgePendingCrashReport()
        }
        guard let data = reporter.loadPendingCrashReportData() else {
            return nil
        }
        guard let report = try? PLCrashReport(data: data) else {
            return nil
        }
        var type = "NativeCrash"
        var value = "Native crash"
        if let info = report.signalInfo {
            type = info.name ?? type
            value = "\(info.name ?? "Signal") (\(info.code ?? "?"))"
        }
        if let exception = report.exceptionInfo {
            type = exception.exceptionName ?? type
            value = exception.exceptionReason ?? value
        }
        let frames = extractFrames(report)
        let timestamp = report.systemInfo?.timestamp ?? Date()
        return PreviousCrash(type: type, value: value, frames: frames, timestamp: timestamp)
    }

    private func extractFrames(_ report: PLCrashReport) -> [[String: Any]] {
        guard let threads = report.threads as? [PLCrashReportThreadInfo] else {
            return []
        }
        let crashedThread = threads.first { $0.crashed } ?? threads.first
        guard let stackFrames = crashedThread?.stackFrames as? [PLCrashReportStackFrameInfo] else {
            return []
        }
        var result: [[String: Any]] = []
        for frame in stackFrames {
            var frameDict: [String: Any] = [:]
            if let symbol = frame.symbolInfo?.symbolName {
                frameDict["function"] = symbol
            }
            if let imageName = report.image(forAddress: frame.instructionPointer)?.imageName {
                let url = URL(fileURLWithPath: imageName)
                frameDict["filename"] = url.lastPathComponent
            }
            frameDict["lineno"] = NSNumber(value: frame.instructionPointer)
            result.append(frameDict)
        }
        return result
    }
}
