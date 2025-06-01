import AVFoundation
import Capacitor
import Foundation

struct CameraConfig {
    var x: CGFloat
    var y: CGFloat
    var width: CGFloat
    var height: CGFloat
    var cameraPosition: AVCaptureDevice.Position
    var quality: AVCaptureSession.Preset
    var zoom: CGFloat
    var jpegQuality: CGFloat
    var autoFocus: Bool
    var orientation: AVCaptureVideoOrientation
}

@objc(CameraMultiCapturePlugin)
public class CameraMultiCapturePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CameraMultiCapturePlugin"
    public let jsName = "CameraMultiCapture"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "start", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stop", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "capture", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setZoom", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "switchCamera", returnType: CAPPluginReturnPromise),
    ]

    var captureSession: AVCaptureSession?
    var currentInput: AVCaptureDeviceInput?
    var photoOutput: AVCapturePhotoOutput?
    var previewLayer: AVCaptureVideoPreviewLayer?
    var cameraPreviewView: UIView?
    var cameraPosition: AVCaptureDevice.Position = .back
    let sessionQueue = DispatchQueue(label: "camera.session.queue")
    var captureDelegate: PhotoCaptureDelegate?

    @objc func start(_ call: CAPPluginCall) {
        print("Received data from JS: \(call.dictionaryRepresentation)")

        guard let previewRect = call.getObject("previewRect") else {
            call.reject("Missing previewRect")
            return
        }

        let x = CGFloat((previewRect["x"] as? NSNumber)?.floatValue ?? 0)
        let y = CGFloat((previewRect["y"] as? NSNumber)?.floatValue ?? 0)
        let width = CGFloat(
            (previewRect["width"] as? NSNumber)?.floatValue ?? Float(UIScreen.main.bounds.width))
        let height = CGFloat(
            (previewRect["height"] as? NSNumber)?.floatValue ?? Float(UIScreen.main.bounds.height))

        let position =
            (call.getString("cameraPosition") == "front") ? AVCaptureDevice.Position.front : .back
        let qualityName = call.getString("captureMode") ?? "high"
        let qualityPreset: AVCaptureSession.Preset = (qualityName == "low") ? .high : .photo
        let zoom = CGFloat(call.getFloat("zoom") ?? 1.0)
        let jpegQuality = CGFloat(call.getFloat("jpegQuality") ?? 0.8)
        let autofocus = call.getBool("autoFocus") ?? true
        let rotation = call.getInt("rotation") ?? 0
        let orientation: AVCaptureVideoOrientation
        switch rotation {
        case 90: orientation = .landscapeRight
        case 180: orientation = .portraitUpsideDown
        case 270: orientation = .landscapeLeft
        default: orientation = .portrait
        }

        let config = CameraConfig(
            x: x, y: y, width: width, height: height,
            cameraPosition: position, quality: qualityPreset,
            zoom: zoom, jpegQuality: jpegQuality, autoFocus: autofocus,
            orientation: orientation
        )
        self.cameraPosition = config.cameraPosition

        AVCaptureDevice.requestAccess(for: .video) { granted in
            DispatchQueue.main.async {
                if !granted {
                    call.reject("Camera permission denied")
                    return
                }
                self.sessionQueue.async {
                    do {
                        try self.configureSession(with: config, call: call)
                        DispatchQueue.main.async {
                            call.resolve()
                        }
                    } catch {
                        DispatchQueue.main.async {
                            call.reject(
                                "Camera configuration failed: \(error.localizedDescription)")
                        }
                    }
                }
            }
        }
    }

    @objc func stop(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            self.captureSession?.stopRunning()
            self.previewLayer?.removeFromSuperlayer()
            self.captureSession = nil
            self.currentInput = nil
            self.photoOutput = nil
            call.resolve()
        }
    }

    @objc func capture(_ call: CAPPluginCall) {
        guard let session = captureSession, let photoOutput = photoOutput else {
            call.reject("Camera not initialized")
            return
        }
        
        if !session.isRunning {
            DispatchQueue.global(qos: .userInitiated).async {
                session.startRunning()
                DispatchQueue.main.async {
                    self.performCapture(call)
                }
            }
            return
        }
        
        performCapture(call)
    }
    
    private func performCapture(_ call: CAPPluginCall) {
        guard let photoOutput = photoOutput else {
            call.reject("Photo output not initialized")
            return
        }

        let resultType = call.getString("resultType") ?? "base64"
        let settings = AVCapturePhotoSettings()
        settings.isHighResolutionPhotoEnabled = photoOutput.isHighResolutionCaptureEnabled
        
        let delegate = PhotoCaptureDelegate(plugin: self, call: call, resultType: resultType)
        self.captureDelegate = delegate

        photoOutput.capturePhoto(with: settings, delegate: delegate)
    }

    @objc func setZoom(_ call: CAPPluginCall) {
        guard let input = currentInput else {
            call.reject("Camera not initialized")
            return
        }
        let zoomFactor = CGFloat(call.getFloat("zoom") ?? 1.0)
        do {
            try input.device.lockForConfiguration()
            // Clamp zoom factor to valid range
            let maxZoom = input.device.activeFormat.videoMaxZoomFactor
            input.device.videoZoomFactor = max(1.0, min(zoomFactor, maxZoom))
            input.device.unlockForConfiguration()
            call.resolve(["zoom": input.device.videoZoomFactor])
        } catch {
            call.reject("Failed to set zoom: \(error.localizedDescription)")
        }
    }

    @objc func switchCamera(_ call: CAPPluginCall) {
        guard let session = captureSession,
            let currentInput = currentInput
        else {
            call.reject("Camera not initialized")
            return
        }
        session.beginConfiguration()
        session.removeInput(currentInput)
        self.cameraPosition = (self.cameraPosition == .back) ? .front : .back
        do {
            if let newDevice = AVCaptureDevice.default(
                .builtInWideAngleCamera, for: .video, position: self.cameraPosition)
            {
                let newInput = try AVCaptureDeviceInput(device: newDevice)
                if session.canAddInput(newInput) {
                    session.addInput(newInput)
                    self.currentInput = newInput
                    call.resolve()
                } else {
                    call.reject("Cannot add new camera input")
                }
            } else {
                call.reject("Desired camera not available")
            }
        } catch {
            call.reject("Switch camera error: \(error.localizedDescription)")
        }
        session.commitConfiguration()
    }

    func configureSession(with config: CameraConfig, call: CAPPluginCall) throws {
        let session = AVCaptureSession()
        session.beginConfiguration()
        session.sessionPreset = config.quality

        guard
            let camera = AVCaptureDevice.default(
                .builtInWideAngleCamera, for: .video, position: config.cameraPosition)
        else {
            throw NSError(
                domain: "Camera", code: 0,
                userInfo: [NSLocalizedDescriptionKey: "Camera not available"])
        }

        let input = try AVCaptureDeviceInput(device: camera)
        if session.canAddInput(input) {
            session.addInput(input)
            self.currentInput = input
        } else {
            throw NSError(
                domain: "Camera", code: 0,
                userInfo: [NSLocalizedDescriptionKey: "Unable to add camera input"])
        }

        let photoOut = AVCapturePhotoOutput()
        if session.canAddOutput(photoOut) {
            session.addOutput(photoOut)
            photoOut.isHighResolutionCaptureEnabled = true
            self.photoOutput = photoOut
        }
        session.commitConfiguration()
        self.captureSession = session

        do {
            try camera.lockForConfiguration()
            camera.videoZoomFactor = config.zoom
            if config.autoFocus && camera.isFocusModeSupported(.continuousAutoFocus) {
                camera.focusMode = .continuousAutoFocus
            } else if camera.isFocusModeSupported(.locked) {
                camera.focusMode = .locked
            }
            camera.unlockForConfiguration()
        } catch {
            print("Focus/zoom config error: \(error)")
        }

        DispatchQueue.main.async {
            guard let webView = self.bridge?.webView else {
                return
            }

            let previewView = UIView(
                frame: CGRect(x: 0, y: 0, width: config.width, height: config.height))
                
            webView.isOpaque = false
            webView.backgroundColor = UIColor.clear
            webView.scrollView.backgroundColor = UIColor.clear
            webView.layer.backgroundColor = UIColor.clear.cgColor
            
            let javascript = "document.documentElement.style.backgroundColor = 'transparent'"
            webView.evaluateJavaScript(javascript) { (_, error) in
                if let error = error {
                    print("[CameraMultiCapture] JS evaluation error: \(error)")
                }
            }
            
            // Add camera preview to webView's scrollView and send to back
            // This ensures camera is behind the web content
            webView.scrollView.addSubview(previewView)
            webView.scrollView.sendSubviewToBack(previewView)
            
            // Position the preview view correctly within the scrollView
            previewView.frame = CGRect(x: config.x, y: config.y, width: config.width, height: config.height)

            let videoLayer = AVCaptureVideoPreviewLayer(session: session)
            videoLayer.frame = previewView.bounds
            videoLayer.videoGravity = .resizeAspectFill
            videoLayer.connection?.videoOrientation = config.orientation
            previewView.layer.addSublayer(videoLayer)
            self.previewLayer = videoLayer
            self.cameraPreviewView = previewView

            if let parentView = webView.superview {
                parentView.bringSubviewToFront(webView)
            }

            DispatchQueue.global(qos: .userInitiated).async {
                self.captureSession?.startRunning()
            }
        }
    }
}

class PhotoCaptureDelegate: NSObject, AVCapturePhotoCaptureDelegate {
    weak var plugin: CameraMultiCapturePlugin?
    var call: CAPPluginCall
    var resultType: String

    init(plugin: CameraMultiCapturePlugin, call: CAPPluginCall, resultType: String) {
        self.plugin = plugin
        self.call = call
        self.resultType = resultType
    }

    func photoOutput(
        _ output: AVCapturePhotoOutput, didFinishProcessingPhoto photo: AVCapturePhoto,
        error: Error?
    ) {
        if let error = error {
            call.reject("Photo capture error: \(error.localizedDescription)")
            return
        }
        
        guard let data = photo.fileDataRepresentation() else {
            call.reject("No image data")
            return
        }
        
        // Write to temporary file first
        let tempDir = FileManager.default.temporaryDirectory
        let fileName = UUID().uuidString + ".jpg"
        let fileURL = tempDir.appendingPathComponent(fileName)
        
        do {
            try data.write(to: fileURL)
            
            let base64 = data.base64EncodedString()
            let base64Data = "data:image/jpeg;base64," + base64
            
            var imageData = [String: String]()
            imageData["uri"] = fileURL.absoluteString
            imageData["base64"] = base64Data
            
            call.resolve(["value": imageData])
        } catch {
            call.reject("Failed to process image: \(error.localizedDescription)")
        }
    }
}
