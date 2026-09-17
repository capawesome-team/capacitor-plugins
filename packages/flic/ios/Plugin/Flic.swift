import Foundation
import Capacitor
import CoreBluetooth
import flic2lib

@objc public class Flic: NSObject {
    private var initializeCompletion: (() -> Void)?
    private var isInitialized = false
    private let plugin: FlicPlugin
    private var scanCompletion: ((StartScanResult?, Error?) -> Void)?

    init(plugin: FlicPlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func checkPermissions(completion: @escaping (PermissionStatusResult) -> Void) {
        let bluetooth = createPermissionStateString(from: CBManager.authorization)
        completion(PermissionStatusResult(bluetooth: bluetooth, bluetoothConnect: "granted", bluetoothScan: "granted", location: "granted"))
    }

    @objc public func connectButtonById(_ options: ConnectButtonByIdOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let button = try self.getButtonByIdOrThrow(options.id)
                button.connect()
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    @objc public func disconnectButtonById(_ options: DisconnectButtonByIdOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let button = try self.getButtonByIdOrThrow(options.id)
                button.disconnect()
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    @objc public func forgetButtonById(_ options: ForgetButtonByIdOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let manager = try self.getManagerOrThrow()
                let button = try self.getButtonByIdOrThrow(options.id)
                manager.forgetButton(button) { _, error in
                    completion(error)
                }
            } catch {
                completion(error)
            }
        }
    }

    @objc public func getButtons(completion: @escaping (GetButtonsResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let manager = try self.getManagerOrThrow()
                completion(GetButtonsResult(buttons: manager.buttons()), nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            if self.isInitialized {
                completion(nil)
                return
            }
            if let manager = FLICManager.shared() {
                manager.delegate = self
                manager.buttonDelegate = self
                for button in manager.buttons() {
                    button.delegate = self
                }
                self.isInitialized = true
                completion(nil)
                return
            }
            self.initializeCompletion = { completion(nil) }
            _ = FLICManager.configure(with: self, buttonDelegate: self, background: options.background)
        }
    }

    @objc public func requestPermissions(completion: @escaping (PermissionStatusResult) -> Void) {
        checkPermissions(completion: completion)
    }

    @objc public func startScan(completion: @escaping (StartScanResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let manager = try self.getManagerOrThrow()
                guard self.scanCompletion == nil else {
                    throw CustomError.scanAlreadyRunning
                }
                self.scanCompletion = completion
                manager.scanForButtons(stateChangeHandler: { event in
                    switch event {
                    case .discovered:
                        self.plugin.notifyScanStatusChangedListeners(ScanStatusChangedEvent(status: .discovered))
                    case .connected:
                        self.plugin.notifyScanStatusChangedListeners(ScanStatusChangedEvent(status: .connected))
                    case .verified:
                        self.plugin.notifyScanStatusChangedListeners(ScanStatusChangedEvent(status: .verified))
                    default:
                        break
                    }
                }, completion: { button, error in
                    guard let savedCompletion = self.scanCompletion else {
                        return
                    }
                    self.scanCompletion = nil
                    if let button = button {
                        savedCompletion(StartScanResult(button: button), nil)
                    } else {
                        savedCompletion(nil, error ?? CustomError.scanFailed)
                    }
                })
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func stopScan(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let manager = try self.getManagerOrThrow()
                manager.stopScan()
                if let savedCompletion = self.scanCompletion {
                    self.scanCompletion = nil
                    savedCompletion(nil, CustomError.scanStopped)
                }
                completion(nil)
            } catch {
                completion(error)
            }
        }
    }

    private func createButtonEvent(_ button: FLICButton, queued: Bool, age: Int) -> ButtonEvent {
        let timestamp = (Date().timeIntervalSince1970 - Double(age)) * 1000
        return ButtonEvent(buttonId: button.identifier.uuidString, timestamp: timestamp.rounded(), wasQueued: queued)
    }

    private func createPermissionStateString(from authorization: CBManagerAuthorization) -> String {
        switch authorization {
        case .allowedAlways:
            return "granted"
        case .denied, .restricted:
            return "denied"
        default:
            return "prompt"
        }
    }

    private func getButtonByIdOrThrow(_ id: String) throws -> FLICButton {
        let manager = try getManagerOrThrow()
        guard let button = manager.buttons().first(where: { $0.identifier.uuidString == id }) else {
            throw CustomError.buttonNotFound
        }
        return button
    }

    private func getManagerOrThrow() throws -> FLICManager {
        guard isInitialized, let manager = FLICManager.shared() else {
            throw CustomError.notInitialized
        }
        return manager
    }
}

extension Flic: FLICManagerDelegate {
    public func managerDidRestoreState(_ manager: FLICManager) {
        isInitialized = true
        initializeCompletion?()
        initializeCompletion = nil
    }

    public func manager(_ manager: FLICManager, didUpdate state: FLICManagerState) {}
}

extension Flic: FLICButtonDelegate {
    public func buttonDidConnect(_ button: FLICButton) {
        plugin.notifyButtonConnectedListeners(ButtonConnectedEvent(buttonId: button.identifier.uuidString))
    }

    public func buttonIsReady(_ button: FLICButton) {
        plugin.notifyButtonReadyListeners(ButtonReadyEvent(buttonId: button.identifier.uuidString))
    }

    public func button(_ button: FLICButton, didDisconnectWithError error: Error?) {
        plugin.notifyButtonDisconnectedListeners(ButtonDisconnectedEvent(buttonId: button.identifier.uuidString))
    }

    public func button(_ button: FLICButton, didFailToConnectWithError error: Error?) {
        let message = error?.localizedDescription ?? "An unknown error has occurred."
        plugin.notifyButtonConnectionFailedListeners(ButtonConnectionFailedEvent(buttonId: button.identifier.uuidString, message: message))
    }

    public func button(_ button: FLICButton, didReceiveButtonDown queued: Bool, age: Int) {
        plugin.notifyButtonDownListeners(createButtonEvent(button, queued: queued, age: age))
    }

    public func button(_ button: FLICButton, didReceiveButtonUp queued: Bool, age: Int) {
        plugin.notifyButtonUpListeners(createButtonEvent(button, queued: queued, age: age))
    }

    public func button(_ button: FLICButton, didReceiveButtonClick queued: Bool, age: Int) {
        plugin.notifyButtonSingleClickListeners(createButtonEvent(button, queued: queued, age: age))
    }

    public func button(_ button: FLICButton, didReceiveButtonDoubleClick queued: Bool, age: Int) {
        plugin.notifyButtonDoubleClickListeners(createButtonEvent(button, queued: queued, age: age))
    }

    public func button(_ button: FLICButton, didReceiveButtonHold queued: Bool, age: Int) {
        plugin.notifyButtonHoldListeners(createButtonEvent(button, queued: queued, age: age))
    }

    public func button(_ button: FLICButton, didUnpairWithError error: Error?) {
        plugin.notifyButtonUnpairedListeners(ButtonUnpairedEvent(buttonId: button.identifier.uuidString))
    }
}
