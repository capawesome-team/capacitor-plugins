import Foundation
import Capacitor

@objc(SingularPlugin)
public class SingularPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SingularPlugin"
    public let jsName = "Singular"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "clearGlobalProperties", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createReferrerShortLink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGlobalProperties", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getLimitDataSharing", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAllTrackingStopped", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resumeAllTracking", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCustomUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setDeviceToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setGlobalProperty", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLimitAdvertisingIdentifiers", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLimitDataSharing", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "skanGetConversionValue", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "skanRegisterAppForAdNetworkAttribution", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "skanUpdateConversionValue", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopAllTracking", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackAdRevenue", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackRevenue", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackingOptIn", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackingUnder13", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unsetCustomUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unsetGlobalProperty", returnType: CAPPluginReturnPromise)
    ]
    public static let eventDeviceAttributionInfoReceived = "deviceAttributionInfoReceived"
    public static let eventSingularLinkResolved = "singularLinkResolved"
    public static let eventSkanConversionValueUpdated = "skanConversionValueUpdated"
    public let tag = "Singular"
    private var implementation: SingularImpl?

    override public func load() {
        super.load()
        self.implementation = SingularImpl(plugin: self)
    }

    @objc func clearGlobalProperties(_ call: CAPPluginCall) {
        do {
            try implementation?.clearGlobalProperties(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func createReferrerShortLink(_ call: CAPPluginCall) {
        do {
            let options = try CreateReferrerShortLinkOptions(call)
            try implementation?.createReferrerShortLink(options, completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getGlobalProperties(_ call: CAPPluginCall) {
        do {
            try implementation?.getGlobalProperties(completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getLimitDataSharing(_ call: CAPPluginCall) {
        do {
            try implementation?.getLimitDataSharing(completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func initialize(_ call: CAPPluginCall) {
        do {
            let options = try InitializeOptions(call)
            implementation?.initialize(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isAllTrackingStopped(_ call: CAPPluginCall) {
        do {
            try implementation?.isAllTrackingStopped(completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    public func notifyDeviceAttributionInfoReceivedListeners(_ event: DeviceAttributionInfoReceivedEvent) {
        notifyListeners(Self.eventDeviceAttributionInfoReceived, data: event.toJSObject() as? [String: Any])
    }

    public func notifySingularLinkResolvedListeners(_ event: SingularLinkResolvedEvent) {
        notifyListeners(Self.eventSingularLinkResolved, data: event.toJSObject() as? [String: Any])
    }

    public func notifySkanConversionValueUpdatedListeners(_ event: SkanConversionValueUpdatedEvent) {
        notifyListeners(Self.eventSkanConversionValueUpdated, data: event.toJSObject() as? [String: Any])
    }

    @objc func resumeAllTracking(_ call: CAPPluginCall) {
        do {
            try implementation?.resumeAllTracking(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setCustomUserId(_ call: CAPPluginCall) {
        do {
            let options = try SetCustomUserIdOptions(call)
            try implementation?.setCustomUserId(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setDeviceToken(_ call: CAPPluginCall) {
        do {
            let options = try SetDeviceTokenOptions(call)
            try implementation?.setDeviceToken(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setGlobalProperty(_ call: CAPPluginCall) {
        do {
            let options = try SetGlobalPropertyOptions(call)
            try implementation?.setGlobalProperty(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setLimitAdvertisingIdentifiers(_ call: CAPPluginCall) {
        do {
            let options = try SetLimitAdvertisingIdentifiersOptions(call)
            try implementation?.setLimitAdvertisingIdentifiers(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setLimitDataSharing(_ call: CAPPluginCall) {
        do {
            let options = try SetLimitDataSharingOptions(call)
            try implementation?.setLimitDataSharing(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func skanGetConversionValue(_ call: CAPPluginCall) {
        do {
            try implementation?.skanGetConversionValue(completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func skanRegisterAppForAdNetworkAttribution(_ call: CAPPluginCall) {
        do {
            try implementation?.skanRegisterAppForAdNetworkAttribution(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func skanUpdateConversionValue(_ call: CAPPluginCall) {
        do {
            let options = try SkanUpdateConversionValueOptions(call)
            try implementation?.skanUpdateConversionValue(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func stopAllTracking(_ call: CAPPluginCall) {
        do {
            try implementation?.stopAllTracking(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func trackAdRevenue(_ call: CAPPluginCall) {
        do {
            let options = try TrackAdRevenueOptions(call)
            try implementation?.trackAdRevenue(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func trackEvent(_ call: CAPPluginCall) {
        do {
            let options = try TrackEventOptions(call)
            try implementation?.trackEvent(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func trackRevenue(_ call: CAPPluginCall) {
        do {
            let options = try TrackRevenueOptions(call)
            try implementation?.trackRevenue(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func trackingOptIn(_ call: CAPPluginCall) {
        do {
            try implementation?.trackingOptIn(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func trackingUnder13(_ call: CAPPluginCall) {
        do {
            try implementation?.trackingUnder13(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func unsetCustomUserId(_ call: CAPPluginCall) {
        do {
            try implementation?.unsetCustomUserId(completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func unsetGlobalProperty(_ call: CAPPluginCall) {
        do {
            let options = try UnsetGlobalPropertyOptions(call)
            try implementation?.unsetGlobalProperty(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ result: Result?, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
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
