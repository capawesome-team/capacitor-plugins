import Foundation
import Capacitor
import SafariServices
import UIKit
import WebKit

@objc public class InAppBrowser: NSObject, SFSafariViewControllerDelegate {
    let plugin: InAppBrowserPlugin

    private var safariViewController: SFSafariViewController?
    private var webViewController: WebViewController?
    private var webViewNavigationController: UINavigationController?

    init(plugin: InAppBrowserPlugin) {
        self.plugin = plugin
    }

    @objc public func clearCache(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            let types: Set<String> = [WKWebsiteDataTypeDiskCache, WKWebsiteDataTypeMemoryCache]
            WKWebsiteDataStore.default().removeData(ofTypes: types, modifiedSince: .distantPast) {
                completion(nil)
            }
        }
    }

    @objc public func clearSessionData(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            let types: Set<String> = [WKWebsiteDataTypeCookies, WKWebsiteDataTypeLocalStorage, WKWebsiteDataTypeSessionStorage]
            WKWebsiteDataStore.default().removeData(ofTypes: types, modifiedSince: .distantPast) {
                completion(nil)
            }
        }
    }

    @objc public func close(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if let webViewController = self.webViewController {
                if self.webViewNavigationController?.presentingViewController == nil {
                    webViewController.handleCloseWithoutPresentation()
                    completion(nil)
                    return
                }
                webViewController.dismiss(animated: true) {
                    completion(nil)
                }
                return
            }
            if let safariViewController = self.safariViewController {
                safariViewController.dismiss(animated: true) {
                    self.safariViewController = nil
                    self.plugin.notifyBrowserClosedListeners()
                    completion(nil)
                }
                return
            }
            completion(CustomError.noBrowserOpen)
        }
    }

    @objc public func executeScript(_ options: ExecuteScriptOptions, completion: @escaping (_ result: ExecuteScriptResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let webViewController = self.webViewController else {
                completion(nil, CustomError.noBrowserOpen)
                return
            }
            webViewController.executeScript(options.script) { result, _ in
                completion(ExecuteScriptResult(value: result), nil)
            }
        }
    }

    @objc public func getCookies(_ options: GetCookiesOptions, completion: @escaping (_ result: GetCookiesResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            WKWebsiteDataStore.default().httpCookieStore.getAllCookies { cookies in
                var result: [String: String] = [:]
                for cookie in cookies where InAppBrowserHelper.isCookie(cookie, matchingURL: options.url) {
                    result[cookie.name] = cookie.value
                }
                completion(GetCookiesResult(cookies: result), nil)
            }
        }
    }

    @objc public func openInExternalBrowser(_ options: OpenInExternalBrowserOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.open(options.url, options: [:]) { success in
                completion(success ? nil : CustomError.browserNotFound)
            }
        }
    }

    @objc public func openInSystemBrowser(_ options: OpenInSystemBrowserOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.dismissActiveBrowser {
                guard let bridgeViewController = self.plugin.bridge?.viewController else {
                    completion(CustomError.noBrowserOpen)
                    return
                }
                let configuration = SFSafariViewController.Configuration()
                configuration.barCollapsingEnabled = options.barCollapsing
                configuration.entersReaderIfAvailable = options.readerMode
                let safariViewController = SFSafariViewController(url: options.url, configuration: configuration)
                safariViewController.delegate = self
                safariViewController.dismissButtonStyle = options.dismissButtonStyle
                if let toolbarColor = options.toolbarColor {
                    safariViewController.preferredBarTintColor = toolbarColor
                }
                self.safariViewController = safariViewController
                bridgeViewController.present(safariViewController, animated: true) {
                    completion(nil)
                }
            }
        }
    }

    @objc public func openInWebView(_ options: OpenInWebViewOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            self.dismissActiveBrowser {
                guard let bridgeViewController = self.plugin.bridge?.viewController else {
                    completion(CustomError.noBrowserOpen)
                    return
                }
                let webViewController = WebViewController(options: options)
                webViewController.onClosed = { [weak self] viewController in
                    guard let self = self else {
                        return
                    }
                    if self.webViewController === viewController {
                        self.webViewController = nil
                        self.webViewNavigationController = nil
                    }
                    self.plugin.notifyBrowserClosedListeners()
                }
                webViewController.onMessageReceived = { [weak self] data in
                    self?.handleMessageReceived(data)
                }
                webViewController.onPageLoaded = { [weak self] in
                    self?.plugin.notifyBrowserPageLoadedListeners()
                }
                webViewController.onPageNavigationCompleted = { [weak self] url in
                    self?.plugin.notifyBrowserPageNavigationCompletedListeners(BrowserPageNavigationCompletedEvent(url: url))
                }
                let navigationController = UINavigationController(rootViewController: webViewController)
                navigationController.modalPresentationStyle = .fullScreen
                navigationController.setNavigationBarHidden(!options.toolbar.visible, animated: false)
                self.webViewController = webViewController
                self.webViewNavigationController = navigationController
                if options.visible {
                    bridgeViewController.present(navigationController, animated: true) {
                        completion(nil)
                    }
                } else {
                    webViewController.loadViewIfNeeded()
                    completion(nil)
                }
            }
        }
    }

    @objc public func postMessage(_ options: PostMessageOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let webViewController = self.webViewController else {
                completion(CustomError.noBrowserOpen)
                return
            }
            let data = (try? JSONSerialization.data(withJSONObject: options.data)).flatMap { String(data: $0, encoding: .utf8) } ?? "{}"
            webViewController.postMessage(data)
            completion(nil)
        }
    }

    public func safariViewController(_ controller: SFSafariViewController, didCompleteInitialLoad didLoadSuccessfully: Bool) {
        if didLoadSuccessfully {
            plugin.notifyBrowserPageLoadedListeners()
        }
    }

    public func safariViewControllerDidFinish(_ controller: SFSafariViewController) {
        safariViewController = nil
        plugin.notifyBrowserClosedListeners()
    }

    @objc public func show(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let navigationController = self.webViewNavigationController else {
                completion(CustomError.noBrowserOpen)
                return
            }
            if navigationController.presentingViewController != nil {
                completion(nil)
                return
            }
            guard let bridgeViewController = self.plugin.bridge?.viewController else {
                completion(CustomError.noBrowserOpen)
                return
            }
            bridgeViewController.present(navigationController, animated: true) {
                completion(nil)
            }
        }
    }

    private func dismissActiveBrowser(_ completion: @escaping () -> Void) {
        if let webViewController = self.webViewController {
            if self.webViewNavigationController?.presentingViewController == nil {
                webViewController.handleCloseWithoutPresentation()
                completion()
                return
            }
            webViewController.dismiss(animated: false) {
                completion()
            }
        } else if let safariViewController = self.safariViewController {
            safariViewController.dismiss(animated: false) {
                self.safariViewController = nil
                self.plugin.notifyBrowserClosedListeners()
                completion()
            }
        } else {
            completion()
        }
    }

    private func handleMessageReceived(_ data: String) {
        var value: Any = data
        if let jsonData = data.data(using: .utf8), let parsedValue = try? JSONSerialization.jsonObject(with: jsonData, options: [.fragmentsAllowed]) {
            value = parsedValue
        }
        plugin.notifyMessageReceivedListeners(MessageReceivedEvent(data: value))
    }
}
