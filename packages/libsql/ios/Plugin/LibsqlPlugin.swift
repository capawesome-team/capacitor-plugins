import Foundation
import Capacitor

@objc(LibsqlPlugin)
public class LibsqlPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "LibsqlPlugin"
    public let jsName = "Libsql"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "connect", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "execute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "executeBatch", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "query", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "beginTransaction", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "commitTransaction", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "rollbackTransaction", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sync", returnType: CAPPluginReturnPromise)
    ]
    
    public static let tag = "Libsql"
    private let implementation = Libsql()

    @objc func connect(_ call: CAPPluginCall) {
        do {
            let options = try ConnectOptions(call)

            try implementation.connect(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func execute(_ call: CAPPluginCall) {
        do {
            let options = try ExecuteOptions(call)

            try implementation.execute(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func executeBatch(_ call: CAPPluginCall) {
        do {
            let options = try ExecuteBatchOptions(call)

            try implementation.executeBatch(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func query(_ call: CAPPluginCall) {
        do {
            let options = try QueryOptions(call)

            try implementation.query(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func beginTransaction(_ call: CAPPluginCall) {
        do {
            let options = try BeginTransactionOptions(call)

            try implementation.beginTransaction(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func commitTransaction(_ call: CAPPluginCall) {
        do {
            let options = try CommitTransactionOptions(call)

            try implementation.commitTransaction(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func rollbackTransaction(_ call: CAPPluginCall) {
        do {
            let options = try RollbackTransactionOptions(call)

            try implementation.rollbackTransaction(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func sync(_ call: CAPPluginCall) {
        do {
            let options = try SyncOptions(call)

            try implementation.sync(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", LibsqlPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
