import Foundation
import UIKit
import Capacitor
import VDARSDK

// swiftlint:disable:next type_body_length
@objc public class Pixlive: NSObject, VDARSDKControllerDelegate, RemoteControllerDelegate {

    private weak var plugin: PixlivePlugin?
    private var arViewController: VDARLiveAnnotationViewController?
    private var touchForwarderView: TouchForwarderView?
    private var currentContext: VDARContext?

    public init(_ plugin: PixlivePlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func initialize() {
        guard let plugin = plugin else { return }
        let licenseKey = plugin.getConfig().getString("licenseKey") ?? ""
        let apiUrl = plugin.getConfig().getString("apiUrl")
        let sdkUrl = plugin.getConfig().getString("sdkUrl")

        guard let documentsPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first else { return }
        let storagePath = (documentsPath as NSString).appendingPathComponent("pixliveSDK")
        let fileManager = FileManager.default
        if !fileManager.fileExists(atPath: storagePath) {
            try? fileManager.createDirectory(atPath: storagePath, withIntermediateDirectories: true)
        }

        VDARSDKController.startSDK(storagePath, withLicenseKey: licenseKey)
        guard let controller = VDARSDKController.sharedInstance() else { return }
        if let apiUrl = apiUrl {
            VDARRemoteController.sharedInstance()?.customAPIServer = apiUrl
        }
        if let sdkUrl = sdkUrl {
            VDARRemoteController.sharedInstance()?.customSdkServer = sdkUrl
        }
        controller.enableCodesRecognition = true
        let cameraSender = VDARCameraImageSource()
        controller.imageSender = cameraSender
        controller.detectionDelegates.add(self)
        VDARRemoteController.sharedInstance()?.delegate = self
    }

    @objc public func synchronize(_ options: SynchronizeOptions, completion: @escaping (_ error: Error?) -> Void) {
        let priors = PixliveHelper.buildTagPriors(options.tags)
        VDARRemoteController.sharedInstance()?.syncRemoteModelsAsynchronously(withPriors: priors, withCompletionBlock: { _, err in
            if let err = err {
                completion(err)
            } else {
                completion(nil)
            }
        })
    }

    @objc public func synchronizeWithToursAndContexts(_ options: SynchronizeWithToursAndContextsOptions, completion: @escaping (_ error: Error?) -> Void) {
        let priors = PixliveHelper.buildFullPriors(tags: options.tags, tourIds: options.tourIds, contextIds: options.contextIds)
        VDARRemoteController.sharedInstance()?.syncRemoteModelsAsynchronously(withPriors: priors, withCompletionBlock: { _, err in
            if let err = err {
                completion(err)
            } else {
                completion(nil)
            }
        })
    }

    @objc public func updateTagMapping(_ options: UpdateTagMappingOptions, completion: @escaping (_ error: Error?) -> Void) {
        VDARRemoteController.sharedInstance()?.syncTagContexts(options.tags, withCompletionBlock: { _, err in
            if let err = err {
                completion(err)
            } else {
                completion(nil)
            }
        })
    }

    @objc public func enableContextsWithTags(_ options: EnableContextsWithTagsOptions, completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.disableContexts()
        VDARSDKController.sharedInstance()?.enableContexts(withTags: options.tags)
        completion(nil)
    }

    @objc public func getContexts(completion: @escaping (_ result: GetContextsResult?, _ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(nil, CustomError.sdkNotInitialized)
            return
        }
        var contextsArray = JSArray()
        if let contextIds = controller.contextIDs as? [String] {
            for contextId in contextIds {
                if let context = controller.getContext(contextId) {
                    contextsArray.append(PixliveHelper.contextToJSObject(context))
                }
            }
        }
        completion(GetContextsResult(contexts: contextsArray), nil)
    }

    @objc public func getContext(_ options: GetContextOptions, completion: @escaping (_ result: GetContextResult?, _ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(nil, CustomError.sdkNotInitialized)
            return
        }
        guard let context = controller.getContext(options.contextId) else {
            completion(nil, CustomError.contextNotFound)
            return
        }
        completion(GetContextResult(context: PixliveHelper.contextToJSObject(context)), nil)
    }

    @objc public func activateContext(_ options: ActivateContextOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(CustomError.sdkNotInitialized)
            return
        }
        if let context = controller.getContext(options.contextId) {
            context.activate()
        }
        completion(nil)
    }

    @objc public func stopContext(completion: @escaping (_ error: Error?) -> Void) {
        if let context = currentContext {
            context.stop()
        }
        completion(nil)
    }

    @objc public func getNearbyGPSPoints(_ options: GetNearbyGPSPointsOptions, completion: @escaping (_ result: GetNearbyGPSPointsResult?, _ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(nil, CustomError.sdkNotInitialized)
            return
        }
        let points = controller.getNearbyGPSPointsfromLat(options.latitude, lon: options.longitude)
        var pointsArray = JSArray()
        if let points = points {
            for point in points {
                pointsArray.append(PixliveHelper.gpsPointToJSObject(point))
            }
        }
        completion(GetNearbyGPSPointsResult(points: pointsArray), nil)
    }

    @objc public func getGPSPointsInBoundingBox(_ options: GetGPSPointsInBoundingBoxOptions, completion: @escaping (_ result: GetGPSPointsInBoundingBoxResult?, _ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(nil, CustomError.sdkNotInitialized)
            return
        }
        let points = controller.getGPSPoints(
            inBoundingBoxOfMinLat: options.minLatitude, minLon: options.minLongitude,
            maxLat: options.maxLatitude, maxLon: options.maxLongitude
        )
        var pointsArray = JSArray()
        if let points = points {
            for point in points {
                pointsArray.append(PixliveHelper.gpsPointToJSObject(point))
            }
        }
        completion(GetGPSPointsInBoundingBoxResult(points: pointsArray), nil)
    }

    @objc public func getNearbyBeacons(completion: @escaping (_ result: GetNearbyBeaconsResult?, _ error: Error?) -> Void) {
        guard let controller = VDARSDKController.sharedInstance() else {
            completion(nil, CustomError.sdkNotInitialized)
            return
        }
        let beaconContextIds = controller.getNearbyBeacons()
        var contextsArray = JSArray()
        if let beaconContextIds = beaconContextIds {
            for contextId in beaconContextIds {
                if let context = controller.getContext(contextId) {
                    contextsArray.append(PixliveHelper.contextToJSObject(context))
                }
            }
        }
        completion(GetNearbyBeaconsResult(contexts: contextsArray), nil)
    }

    @objc public func startNearbyGPSDetection(completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.startNearbyGPSDetection()
        completion(nil)
    }

    @objc public func stopNearbyGPSDetection(completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.stopNearbyGPSDetection()
        completion(nil)
    }

    @objc public func startGPSNotifications(completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.startGPSNotifications()
        completion(nil)
    }

    @objc public func stopGPSNotifications(completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.stopGPSNotifications()
        completion(nil)
    }

    @objc public func setNotificationsSupport(_ options: SetNotificationsSupportOptions, completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.isNotificationsEnabled = options.enabled
        completion(nil)
    }

    @objc public func setInterfaceLanguage(_ options: SetInterfaceLanguageOptions, completion: @escaping (_ error: Error?) -> Void) {
        VDARSDKController.sharedInstance()?.forceLanguage(options.language)
        completion(nil)
    }

    @objc public func createARView(_ options: CreateARViewOptions, completion: @escaping (_ error: Error?) -> Void) {
        if arViewController != nil {
            completion(CustomError.arViewAlreadyExists)
            return
        }
        guard let plugin = plugin else { return }
        DispatchQueue.main.async {
            guard let webView = plugin.webView, let superview = webView.superview else { return }
            let arVC = CapacitorARViewController()
            arVC.pixlive = self
            self.arViewController = arVC
            _ = arVC.view
            arVC.viewDidLoad()
            let frame = CGRect(x: options.x, y: options.y, width: options.width, height: options.height)
            arVC.view.frame = frame
            webView.isOpaque = false
            webView.backgroundColor = .clear
            webView.scrollView.backgroundColor = .clear

            let forwarder = TouchForwarderView(frame: webView.frame)
            forwarder.autoresizingMask = webView.autoresizingMask
            superview.insertSubview(forwarder, aboveSubview: webView)
            webView.removeFromSuperview()
            forwarder.addSubview(webView)
            webView.frame = forwarder.bounds
            webView.autoresizingMask = [.flexibleWidth, .flexibleHeight]

            forwarder.arView = arVC.view
            self.touchForwarderView = forwarder

            superview.insertSubview(arVC.view, belowSubview: forwarder)
            arVC.viewWillAppear(false)
            arVC.viewDidAppear(false)
            completion(nil)
        }
    }

    @objc public func destroyARView(completion: @escaping (_ error: Error?) -> Void) {
        guard let arVC = arViewController else {
            completion(CustomError.arViewNotFound)
            return
        }
        DispatchQueue.main.async {
            guard let plugin = self.plugin, let webView = plugin.webView else { return }
            webView.isOpaque = true
            webView.backgroundColor = .white
            webView.scrollView.backgroundColor = .white
            arVC.view.removeFromSuperview()
            arVC.stopAndUnload()
            self.arViewController = nil

            if let forwarder = self.touchForwarderView, let superview = forwarder.superview {
                webView.removeFromSuperview()
                let savedFrame = forwarder.frame
                let savedMask = forwarder.autoresizingMask
                superview.insertSubview(webView, aboveSubview: forwarder)
                forwarder.removeFromSuperview()
                webView.frame = savedFrame
                webView.autoresizingMask = savedMask
                self.touchForwarderView = nil
            }

            completion(nil)
        }
    }

    @objc public func resizeARView(_ options: ResizeARViewOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let arVC = arViewController else {
            completion(CustomError.arViewNotFound)
            return
        }
        DispatchQueue.main.async {
            let frame = CGRect(x: options.x, y: options.y, width: options.width, height: options.height)
            arVC.view.frame = frame
            completion(nil)
        }
    }

    @objc public func setARViewTouchEnabled(_ options: SetARViewTouchEnabledOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.touchForwarderView?.touchEnabled = options.enabled
        }
        completion(nil)
    }

    @objc public func setARViewTouchHole(_ options: SetARViewTouchHoleOptions, completion: @escaping (_ error: Error?) -> Void) {
        let hole = CGRect(
            x: options.left,
            y: options.top,
            width: options.right - options.left,
            height: options.bottom - options.top
        )
        DispatchQueue.main.async {
            self.touchForwarderView?.touchHole = hole
        }
        completion(nil)
    }

    // MARK: - CapacitorARViewController

    func annotationViewDidBecomeEmpty() {
        plugin?.notifyListenersFromImplementation("hideAnnotations", data: JSObject())
    }

    func annotationViewDidPresentAnnotations() {
        plugin?.notifyListenersFromImplementation("presentAnnotations", data: JSObject())
    }

    // MARK: - VDARSDKControllerDelegate

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func didEnter(_ context: VDARContext!) {
        guard let context = context else { return }
        self.currentContext = context
        var data = JSObject()
        data["contextId"] = context.remoteID ?? ""
        plugin?.notifyListenersFromImplementation("enterContext", data: data)
    }

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func didExit(_ context: VDARContext!) {
        guard let context = context else { return }
        self.currentContext = nil
        var data = JSObject()
        data["contextId"] = context.remoteID ?? ""
        plugin?.notifyListenersFromImplementation("exitContext", data: data)
    }

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func codesDetected(_ codes: [VDARCode]!) {
        guard let codes = codes else { return }
        for code in codes {
            if code.isSpecialCode {
                continue
            }
            var data = JSObject()
            data["code"] = code.codeData ?? ""
            data["type"] = PixliveHelper.codeTypeToString(code.codeType)
            plugin?.notifyListenersFromImplementation("codeRecognize", data: data)
        }
    }

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func errorOccurred(onModelManager err: Error!) {
        // Required delegate method - no action needed
    }

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func didDispatchEvent(inApp eventName: String!, withParams eventParams: String!) {
        guard let eventName = eventName else { return }
        var data = JSObject()
        data["name"] = eventName
        data["params"] = eventParams ?? ""
        plugin?.notifyListenersFromImplementation("eventFromContent", data: data)
    }

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func contextDidRequireSynchronization(_ priors: [VDARPrior]!) {
        guard let priors = priors else { return }
        var tags: [String] = []
        for prior in priors {
            if let tagPrior = prior as? VDARTagPrior {
                tags.append(tagPrior.tagName)
            }
        }
        var data = JSObject()
        data["tags"] = tags
        plugin?.notifyListenersFromImplementation("requireSync", data: data)
    }

    // MARK: - RemoteControllerDelegate

    // swiftlint:disable:next implicitly_unwrapped_optional
    @objc public func remoteController(_ controller: VDARRemoteController!, didProgress prc: Float, isReady: Bool, folder: String!) {
        var data = JSObject()
        data["progress"] = prc
        plugin?.notifyListenersFromImplementation("syncProgress", data: data)
    }
}
