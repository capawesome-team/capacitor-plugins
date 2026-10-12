import Foundation
import UIKit
import WebKit

@objc public class WebViewController: UIViewController, WKNavigationDelegate, WKScriptMessageHandler, WKUIDelegate {
    var onClosed: ((WebViewController) -> Void)?
    var onMessageReceived: ((String) -> Void)?
    var onNavigationCompleted: ((String) -> Void)?
    var onPageLoaded: (() -> Void)?
    var onUrlChanged: ((String) -> Void)?

    private static let bridgeJavaScript =
        "window.CapacitorInAppBrowser = window.CapacitorInAppBrowser || { postMessage: function (data) "
        + "{ window.webkit.messageHandlers.capacitorInAppBrowser.postMessage(JSON.stringify(data)); } };"
    private static let messageHandlerName = "capacitorInAppBrowser"

    private let options: OpenInWebViewOptions

    private var backButtonItem: UIBarButtonItem?
    private var canGoBackObservation: NSKeyValueObservation?
    private var canGoForwardObservation: NSKeyValueObservation?
    private var forwardButtonItem: UIBarButtonItem?
    private var initialLoadNotified = false
    private var lastNotifiedUrl: String?
    private var titleObservation: NSKeyValueObservation?
    private var urlObservation: NSKeyValueObservation?
    private var webView: WKWebView?

    private var webViewExtendsUnderNavigationBar: Bool {
        guard #available(iOS 26, *) else {
            return false
        }
        // A toolbar with a background color is opaque and would hide the content.
        return options.toolbar.visible && options.toolbar.backgroundColor == nil
    }

    init(options: OpenInWebViewOptions) {
        self.options = options
        super.init(nibName: nil, bundle: nil)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    public func executeScript(_ script: String, completion: @escaping (_ result: Any?, _ error: Error?) -> Void) {
        guard let webView = webView else {
            completion(nil, nil)
            return
        }
        webView.evaluateJavaScript(script) { result, error in
            completion(result, error)
        }
    }

    public func handleCloseWithoutPresentation() {
        cleanup()
        onClosed?(self)
    }

    public func postMessage(_ data: String) {
        webView?.evaluateJavaScript(
            "window.dispatchEvent(new CustomEvent('capacitorInAppBrowserMessage', { detail: \(data) }));",
            completionHandler: nil
        )
    }

    public func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if let data = message.body as? String {
            onMessageReceived?(data)
        }
    }

    override public func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        if isBeingDismissed || navigationController?.isBeingDismissed == true {
            cleanup()
            onClosed?(self)
        }
    }

    override public func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        let webView = createWebView()
        self.webView = webView
        view.addSubview(webView)
        let topAnchor = webViewExtendsUnderNavigationBar ? view.topAnchor : view.safeAreaLayoutGuide.topAnchor
        NSLayoutConstraint.activate([
            webView.topAnchor.constraint(equalTo: topAnchor),
            webView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            webView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        configureNavigationBar()
        loadUrl()
    }

    override public func viewSafeAreaInsetsDidChange() {
        super.viewSafeAreaInsetsDidChange()
        guard #available(iOS 26, *), webViewExtendsUnderNavigationBar else {
            return
        }
        // Keeps fixed and sticky elements of the page below the navigation bar.
        webView?.obscuredContentInsets = UIEdgeInsets(top: view.safeAreaInsets.top, left: 0, bottom: 0, right: 0)
    }

    public func webView(_ webView: WKWebView, createWebViewWith configuration: WKWebViewConfiguration, for navigationAction: WKNavigationAction, windowFeatures: WKWindowFeatures) -> WKWebView? {
        if navigationAction.targetFrame == nil {
            webView.load(navigationAction.request)
        }
        return nil
    }

    public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        updateNavigationButtons()
        updateTitle()
        if !initialLoadNotified {
            initialLoadNotified = true
            onPageLoaded?()
        }
        if let url = webView.url?.absoluteString {
            onNavigationCompleted?(url)
        }
    }

    public func webView(_ webView: WKWebView, requestMediaCapturePermissionFor origin: WKSecurityOrigin, initiatedByFrame frame: WKFrameInfo, type: WKMediaCaptureType, decisionHandler: @escaping (WKPermissionDecision) -> Void) {
        decisionHandler(.grant)
    }

    private func cleanup() {
        webView?.configuration.userContentController.removeScriptMessageHandler(forName: Self.messageHandlerName)
    }

    private func configureNavigationBar() {
        let closeButtonItem = UIBarButtonItem(
            title: options.toolbar.closeButtonText,
            style: .plain,
            target: self,
            action: #selector(handleClose)
        )
        navigationItem.leftBarButtonItem = closeButtonItem
        if options.toolbar.showNavigationButtons {
            let backButtonItem = UIBarButtonItem(
                image: UIImage(systemName: "chevron.left"),
                style: .plain,
                target: self,
                action: #selector(handleGoBack)
            )
            let forwardButtonItem = UIBarButtonItem(
                image: UIImage(systemName: "chevron.right"),
                style: .plain,
                target: self,
                action: #selector(handleGoForward)
            )
            navigationItem.rightBarButtonItems = [forwardButtonItem, backButtonItem]
            self.backButtonItem = backButtonItem
            self.forwardButtonItem = forwardButtonItem
            updateNavigationButtons()
        }
        if let backgroundColor = options.toolbar.backgroundColor {
            let appearance = UINavigationBarAppearance()
            appearance.configureWithOpaqueBackground()
            appearance.backgroundColor = backgroundColor
            if let color = options.toolbar.color {
                appearance.titleTextAttributes = [.foregroundColor: color]
            }
            navigationController?.navigationBar.standardAppearance = appearance
            navigationController?.navigationBar.scrollEdgeAppearance = appearance
            navigationController?.navigationBar.compactAppearance = appearance
        }
        if let color = options.toolbar.color {
            closeButtonItem.tintColor = color
            backButtonItem?.tintColor = color
            forwardButtonItem?.tintColor = color
        }
        updateTitle()
    }

    private func createWebView() -> WKWebView {
        let configuration = WKWebViewConfiguration()
        configuration.allowsInlineMediaPlayback = true
        configuration.mediaTypesRequiringUserActionForPlayback = options.mediaPlaybackRequiresUserAction ? .all : []
        configuration.websiteDataStore = options.isolatedDataStore ? .nonPersistent() : .default()
        let userContentController = WKUserContentController()
        userContentController.addUserScript(WKUserScript(source: Self.bridgeJavaScript, injectionTime: .atDocumentStart, forMainFrameOnly: true))
        userContentController.add(self, name: Self.messageHandlerName)
        configuration.userContentController = userContentController
        let webView = WKWebView(frame: .zero, configuration: configuration)
        webView.translatesAutoresizingMaskIntoConstraints = false
        webView.navigationDelegate = self
        webView.uiDelegate = self
        webView.allowsBackForwardNavigationGestures = options.allowsBackForwardNavigationGestures
        webView.scrollView.bounces = options.overscroll
        if let userAgent = options.userAgent {
            webView.customUserAgent = userAgent
        }
        canGoBackObservation = webView.observe(\.canGoBack, options: [.new]) { [weak self] _, _ in
            self?.updateNavigationButtons()
        }
        canGoForwardObservation = webView.observe(\.canGoForward, options: [.new]) { [weak self] _, _ in
            self?.updateNavigationButtons()
        }
        titleObservation = webView.observe(\.title, options: [.new]) { [weak self] _, _ in
            self?.updateTitle()
        }
        urlObservation = webView.observe(\.url, options: [.new]) { [weak self] _, _ in
            self?.handleUrlChanged()
            self?.updateTitle()
        }
        return webView
    }

    @objc private func handleClose() {
        dismiss(animated: true)
    }

    @objc private func handleGoBack() {
        if webView?.canGoBack == true {
            webView?.goBack()
        }
    }

    @objc private func handleGoForward() {
        if webView?.canGoForward == true {
            webView?.goForward()
        }
    }

    private func handleUrlChanged() {
        guard let url = webView?.url?.absoluteString, url != lastNotifiedUrl else {
            return
        }
        lastNotifiedUrl = url
        onUrlChanged?(url)
    }

    private func loadUrl() {
        var request = URLRequest(url: options.url)
        for (key, value) in options.headers {
            request.setValue(value, forHTTPHeaderField: key)
        }
        webView?.load(request)
    }

    private func updateNavigationButtons() {
        backButtonItem?.isEnabled = webView?.canGoBack == true
        forwardButtonItem?.isEnabled = webView?.canGoForward == true
    }

    private func updateTitle() {
        if let title = options.toolbar.title {
            navigationItem.title = title
        } else if options.toolbar.showUrl {
            navigationItem.title = webView?.url?.absoluteString
        } else {
            navigationItem.title = webView?.title
        }
    }
}
