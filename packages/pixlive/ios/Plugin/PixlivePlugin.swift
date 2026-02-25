import AVFoundation
import CoreBluetooth
import CoreLocation
import Foundation
import UIKit
import UserNotifications
import Capacitor

@objc(PixlivePlugin)
// swiftlint:disable:next type_body_length
public class PixlivePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PixlivePlugin"
    public let jsName = "Pixlive"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "synchronize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "synchronizeWithToursAndContexts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateTagMapping", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enableContextsWithTags", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getContexts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getContext", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "activateContext", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopContext", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNearbyGPSPoints", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGPSPointsInBoundingBox", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNearbyBeacons", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startNearbyGPSDetection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopNearbyGPSDetection", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startGPSNotifications", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopGPSNotifications", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNotificationsSupport", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setInterfaceLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createARView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "destroyARView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resizeARView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setARViewTouchEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setARViewTouchHole", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "Pixlive"

    private var implementation: Pixlive?
    private var locationManager: CLLocationManager?

    override public func load() {
        implementation = Pixlive(self)
    }

    @objc func initialize(_ call: CAPPluginCall) {
        implementation?.initialize(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        var result = JSObject()
        result["bluetooth"] = mapBluetoothPermissionState()
        result["camera"] = mapCameraPermissionState()
        result["notifications"] = "prompt"
        UNUserNotificationCenter.current().getNotificationSettings { settings in
            result["notifications"] = self.mapNotificationPermissionState(settings.authorizationStatus)
            call.resolve(result)
        }
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        let permissions = call.getArray("permissions", String.self) ?? ["bluetooth", "camera", "location", "notifications"]
        let group = DispatchGroup()

        if permissions.contains("camera") {
            group.enter()
            AVCaptureDevice.requestAccess(for: .video) { _ in
                group.leave()
            }
        }

        if permissions.contains("location") {
            group.enter()
            DispatchQueue.main.async {
                if self.locationManager == nil {
                    self.locationManager = CLLocationManager()
                }
                self.locationManager?.requestWhenInUseAuthorization()
                group.leave()
            }
        }

        if permissions.contains("notifications") {
            group.enter()
            UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { _, _ in
                group.leave()
            }
        }

        group.notify(queue: .main) {
            self.checkPermissions(call)
        }
    }

    @objc func synchronize(_ call: CAPPluginCall) {
        do {
            let options = try SynchronizeOptions(call)
            implementation?.synchronize(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func synchronizeWithToursAndContexts(_ call: CAPPluginCall) {
        do {
            let options = try SynchronizeWithToursAndContextsOptions(call)
            implementation?.synchronizeWithToursAndContexts(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateTagMapping(_ call: CAPPluginCall) {
        do {
            let options = try UpdateTagMappingOptions(call)
            implementation?.updateTagMapping(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func enableContextsWithTags(_ call: CAPPluginCall) {
        do {
            let options = try EnableContextsWithTagsOptions(call)
            implementation?.enableContextsWithTags(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getContexts(_ call: CAPPluginCall) {
        implementation?.getContexts(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getContext(_ call: CAPPluginCall) {
        do {
            let options = try GetContextOptions(call)
            implementation?.getContext(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func activateContext(_ call: CAPPluginCall) {
        do {
            let options = try ActivateContextOptions(call)
            implementation?.activateContext(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func stopContext(_ call: CAPPluginCall) {
        implementation?.stopContext(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func getNearbyGPSPoints(_ call: CAPPluginCall) {
        do {
            let options = try GetNearbyGPSPointsOptions(call)
            implementation?.getNearbyGPSPoints(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getGPSPointsInBoundingBox(_ call: CAPPluginCall) {
        do {
            let options = try GetGPSPointsInBoundingBoxOptions(call)
            implementation?.getGPSPointsInBoundingBox(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getNearbyBeacons(_ call: CAPPluginCall) {
        implementation?.getNearbyBeacons(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func startNearbyGPSDetection(_ call: CAPPluginCall) {
        implementation?.startNearbyGPSDetection(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func stopNearbyGPSDetection(_ call: CAPPluginCall) {
        implementation?.stopNearbyGPSDetection(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func startGPSNotifications(_ call: CAPPluginCall) {
        implementation?.startGPSNotifications(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func stopGPSNotifications(_ call: CAPPluginCall) {
        implementation?.stopGPSNotifications(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func setNotificationsSupport(_ call: CAPPluginCall) {
        do {
            let options = try SetNotificationsSupportOptions(call)
            implementation?.setNotificationsSupport(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setInterfaceLanguage(_ call: CAPPluginCall) {
        do {
            let options = try SetInterfaceLanguageOptions(call)
            implementation?.setInterfaceLanguage(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func createARView(_ call: CAPPluginCall) {
        do {
            let options = try CreateARViewOptions(call)
            implementation?.createARView(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func destroyARView(_ call: CAPPluginCall) {
        implementation?.destroyARView(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func resizeARView(_ call: CAPPluginCall) {
        do {
            let options = try ResizeARViewOptions(call)
            implementation?.resizeARView(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setARViewTouchEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetARViewTouchEnabledOptions(call)
            implementation?.setARViewTouchEnabled(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setARViewTouchHole(_ call: CAPPluginCall) {
        do {
            let options = try SetARViewTouchHoleOptions(call)
            implementation?.setARViewTouchHole(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    public func notifyListenersFromImplementation(_ eventName: String, data: JSObject) {
        notifyListeners(eventName, data: data)
    }

    private func mapBluetoothPermissionState() -> String {
        if #available(iOS 13.1, *) {
            switch CBCentralManager.authorization {
            case .allowedAlways:
                return "granted"
            case .denied, .restricted:
                return "denied"
            case .notDetermined:
                return "prompt"
            @unknown default:
                return "prompt"
            }
        }
        return "granted"
    }

    private func mapCameraPermissionState() -> String {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            return "granted"
        case .denied, .restricted:
            return "denied"
        case .notDetermined:
            return "prompt"
        @unknown default:
            return "prompt"
        }
    }

    private func mapNotificationPermissionState(_ status: UNAuthorizationStatus) -> String {
        switch status {
        case .authorized, .provisional, .ephemeral:
            return "granted"
        case .denied:
            return "denied"
        case .notDetermined:
            return "prompt"
        @unknown default:
            return "prompt"
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PixlivePlugin.tag, "] ", error)
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
