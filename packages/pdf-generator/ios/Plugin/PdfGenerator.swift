import Foundation
import Capacitor
import UIKit
import WebKit

@objc public class PdfGenerator: NSObject {
    private let documentsDirectoryName = "capawesome_capacitor_pdf_generator_documents"
    private let marginInPoints: CGFloat = 20
    private let plugin: PdfGeneratorPlugin
    private let settleDelayInSeconds: TimeInterval = 0.5

    private var currentCompletion: ((GenerateResult?, Error?) -> Void)?
    private var currentOptions: GenerateOptions?
    private var currentSettleWorkItem: DispatchWorkItem?
    private var currentTimeoutWorkItem: DispatchWorkItem?
    private var currentWebView: WKWebView?
    private var generating = false
    private var queue: [() -> Void] = []

    init(plugin: PdfGeneratorPlugin) {
        self.plugin = plugin
        super.init()
        cleanUpDocumentsDirectory()
    }

    @objc public func generateFromHtml(_ options: GenerateFromHtmlOptions, completion: @escaping (GenerateResult?, Error?) -> Void) {
        enqueue {
            let webView = self.startGeneration(options, completion: completion)
            webView.loadHTMLString(options.html, baseURL: options.baseUrl)
        }
    }

    @objc public func generateFromUrl(_ options: GenerateFromUrlOptions, completion: @escaping (GenerateResult?, Error?) -> Void) {
        enqueue {
            let webView = self.startGeneration(options, completion: completion)
            webView.load(URLRequest(url: options.url))
        }
    }

    private func cleanUpDocumentsDirectory() {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            return
        }
        let directory = cachesDirectory.appendingPathComponent(documentsDirectoryName)
        try? FileManager.default.removeItem(at: directory)
    }

    private func createDocument() {
        guard let options = currentOptions, let webView = currentWebView else {
            return
        }
        let renderer = UIPrintPageRenderer()
        renderer.addPrintFormatter(webView.viewPrintFormatter(), startingAtPageAt: 0)
        let paperRect = CGRect(origin: .zero, size: options.paperSize)
        let printableRect = paperRect.insetBy(dx: marginInPoints, dy: marginInPoints)
        // The `paperRect` and `printableRect` properties are read-only and can only be set via key-value coding
        renderer.setValue(NSValue(cgRect: paperRect), forKey: "paperRect")
        renderer.setValue(NSValue(cgRect: printableRect), forKey: "printableRect")
        let pageCount = renderer.numberOfPages
        if pageCount == 0 {
            finish(nil, CustomError.generationFailed)
            return
        }
        let data = NSMutableData()
        UIGraphicsBeginPDFContextToData(data, paperRect, nil)
        for pageIndex in 0..<pageCount {
            UIGraphicsBeginPDFPage()
            renderer.drawPage(at: pageIndex, in: UIGraphicsGetPDFContextBounds())
        }
        UIGraphicsEndPDFContext()
        do {
            let url = try getDocumentsDirectory().appendingPathComponent(options.fileName)
            try data.write(to: url, options: .atomic)
            finish(GenerateResult(url: url), nil)
        } catch {
            finish(nil, CustomError.generationFailed)
        }
    }

    private func enqueue(_ task: @escaping () -> Void) {
        DispatchQueue.main.async {
            self.queue.append(task)
            self.processQueue()
        }
    }

    private func finish(_ result: GenerateResult?, _ error: Error?) {
        guard let completion = currentCompletion else {
            return
        }
        currentSettleWorkItem?.cancel()
        currentSettleWorkItem = nil
        currentTimeoutWorkItem?.cancel()
        currentTimeoutWorkItem = nil
        currentWebView?.navigationDelegate = nil
        currentWebView?.removeFromSuperview()
        currentWebView = nil
        currentCompletion = nil
        currentOptions = nil
        generating = false
        processQueue()
        completion(result, error)
    }

    private func getDocumentsDirectory() throws -> URL {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            throw CustomError.generationFailed
        }
        let directory = cachesDirectory.appendingPathComponent(documentsDirectoryName)
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true)
        return directory
    }

    private func handleTimeout() {
        finish(nil, CustomError.timeout)
    }

    private func processQueue() {
        if generating || queue.isEmpty {
            return
        }
        generating = true
        let task = queue.removeFirst()
        task()
    }

    private func scheduleDocumentCreation() {
        currentSettleWorkItem?.cancel()
        let workItem = DispatchWorkItem { [weak self] in
            self?.createDocument()
        }
        currentSettleWorkItem = workItem
        DispatchQueue.main.asyncAfter(deadline: .now() + settleDelayInSeconds, execute: workItem)
    }

    private func startGeneration(_ options: GenerateOptions, completion: @escaping (GenerateResult?, Error?) -> Void) -> WKWebView {
        currentCompletion = completion
        currentOptions = options
        let webView = WKWebView(frame: CGRect(origin: .zero, size: options.paperSize))
        webView.isHidden = true
        webView.navigationDelegate = self
        plugin.bridge?.viewController?.view.addSubview(webView)
        currentWebView = webView
        let workItem = DispatchWorkItem { [weak self] in
            self?.handleTimeout()
        }
        currentTimeoutWorkItem = workItem
        DispatchQueue.main.asyncAfter(deadline: .now() + (Double(options.timeout) / 1000), execute: workItem)
        return webView
    }
}

extension PdfGenerator: WKNavigationDelegate {
    public func webView(_ webView: WKWebView, didFail navigation: WKNavigation!, withError error: Error) {
        if webView == currentWebView {
            finish(nil, CustomError.loadFailed)
        }
    }

    public func webView(_ webView: WKWebView, didFailProvisionalNavigation navigation: WKNavigation!, withError error: Error) {
        if webView == currentWebView {
            finish(nil, CustomError.loadFailed)
        }
    }

    public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        if webView == currentWebView {
            scheduleDocumentCreation()
        }
    }
}
