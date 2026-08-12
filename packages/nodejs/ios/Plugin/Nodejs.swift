import Foundation
import Capacitor
import NodejsPluginNative

@objc public class Nodejs: NSObject {
    private static let channelEvents = "_EVENTS_"
    private static let channelSystem = "_SYSTEM_"
    private static let systemMessageReady = "ready-for-app-events"

    private let config: NodejsConfig
    private weak var plugin: NodejsPlugin?

    private var ready = false
    private var started = false

    init(plugin: NodejsPlugin, config: NodejsConfig) {
        self.config = config
        self.plugin = plugin
        super.init()
        NodeRunner.setMessageHandler { [weak self] channelName, message in
            self?.handleMessage(channelName: channelName, message: message)
        }
        if let dataDirPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
            NodeRunner.registerDataDirPath(dataDirPath)
        }
    }

    @objc public func isReady() -> Bool {
        return ready
    }

    @objc public func send(_ options: SendOptions) throws {
        guard ready else {
            throw CustomError.nodeNotReady
        }
        guard JSONSerialization.isValidJSONObject(options.args) else {
            throw NSError(domain: "NodejsPlugin", code: 0, userInfo: [NSLocalizedDescriptionKey: "args must be JSON-serializable."])
        }
        let payload = try JSONSerialization.data(withJSONObject: options.args)
        let envelope: [String: Any] = [
            "event": options.eventName,
            "payload": String(data: payload, encoding: .utf8) ?? "[]"
        ]
        let envelopeData = try JSONSerialization.data(withJSONObject: envelope)
        guard let message = String(data: envelopeData, encoding: .utf8) else {
            return
        }
        NodeRunner.sendMessage(Nodejs.channelEvents, message: message)
    }

    @objc public func start(_ options: StartOptions, completion: @escaping (_ error: Error?) -> Void) {
        objc_sync_enter(self)
        defer {
            objc_sync_exit(self)
        }
        guard !started else {
            completion(CustomError.nodeAlreadyRunning)
            return
        }
        started = true
        let thread = Thread { [self] in
            do {
                let projectPath = try self.getProjectPath()
                let builtinModulesPath = self.getBuiltinModulesPath()
                let scriptPath = try self.resolveScriptPath(projectPath: projectPath, script: options.script)
                for (key, value) in options.env {
                    setenv(key, value, 1)
                }
                setenv("TMPDIR", NSTemporaryDirectory(), 1)
                var arguments = ["node", scriptPath]
                arguments.append(contentsOf: options.args)
                var nodePath = projectPath
                if let builtinModulesPath = builtinModulesPath {
                    nodePath += ":" + builtinModulesPath
                }
                completion(nil)
                NodeRunner.startEngine(withArguments: arguments, nodePath: nodePath)
            } catch {
                self.started = false
                completion(error)
            }
        }
        thread.name = "NodejsPlugin"
        thread.stackSize = 2 * 1024 * 1024
        thread.start()
    }

    private func handleMessage(channelName: String, message: String) {
        if channelName == Nodejs.channelSystem {
            if message == Nodejs.systemMessageReady {
                ready = true
                plugin?.notifyReadyListeners()
            }
        } else if channelName == Nodejs.channelEvents {
            guard let envelopeData = message.data(using: .utf8),
                  let envelope = try? JSONSerialization.jsonObject(with: envelopeData) as? [String: Any],
                  let eventName = envelope["event"] as? String else {
                return
            }
            var args: [Any] = []
            if let payload = envelope["payload"] as? String, let payloadData = payload.data(using: .utf8) {
                args = (try? JSONSerialization.jsonObject(with: payloadData) as? [Any]) ?? []
            }
            plugin?.notifyMessageListeners(MessageEvent(eventName: eventName, args: args))
        }
    }

    private func getProjectPath() throws -> String {
        let projectPath = Bundle.main.bundlePath + "/public/" + config.nodeDir
        guard FileManager.default.fileExists(atPath: projectPath) else {
            throw CustomError.projectNotFound
        }
        return projectPath
    }

    private func getBuiltinModulesPath() -> String? {
        #if SWIFT_PACKAGE
        return Bundle.module.path(forResource: "builtin_modules", ofType: nil)
        #else
        return Bundle(for: Nodejs.self).path(forResource: "builtin_modules", ofType: nil)
            ?? Bundle.main.path(forResource: "builtin_modules", ofType: nil)
        #endif
    }

    private func resolveScriptPath(projectPath: String, script: String?) throws -> String {
        var scriptFile = script
        if scriptFile == nil {
            scriptFile = getMainFromPackageJson(projectPath: projectPath)
        }
        if scriptFile == nil {
            scriptFile = "index.js"
        }
        let scriptPath = projectPath + "/" + (scriptFile ?? "index.js")
        guard FileManager.default.fileExists(atPath: scriptPath) else {
            throw CustomError.scriptNotFound
        }
        return scriptPath
    }

    private func getMainFromPackageJson(projectPath: String) -> String? {
        let packageJsonPath = projectPath + "/package.json"
        guard let packageJsonData = FileManager.default.contents(atPath: packageJsonPath),
              let packageJson = try? JSONSerialization.jsonObject(with: packageJsonData) as? [String: Any] else {
            return nil
        }
        return packageJson["main"] as? String
    }
}
