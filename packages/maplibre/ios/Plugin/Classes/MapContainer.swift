import Foundation
import MapLibre
import UIKit
import WebKit

/// Locates the internal scroll view of the web view that renders a map element and inserts the map view into it.
public class MapContainer {
    private static let searchAttempts = 5
    private static let searchDelay = 0.2

    /// Inserts the map view behind the web content of the scroll view that renders the map element.
    static func attach(_ mapView: MLNMapView, to container: UIScrollView) {
        container.isScrollEnabled = false
        container.insertSubview(mapView, at: 0)
    }

    static func find(in webView: WKWebView, matching contentSize: MapContentSize) -> UIScrollView? {
        return find(in: webView.scrollView, matching: contentSize)
    }

    /// Searches for the container of a map element that may not have been laid out by the web view yet.
    static func findWhenLaidOut(
        in webView: WKWebView,
        matching contentSize: MapContentSize,
        completion: @escaping (_ container: UIScrollView?) -> Void
    ) {
        find(in: webView, matching: contentSize, attempts: searchAttempts, completion: completion)
    }

    private static func find(in view: UIView, matching contentSize: MapContentSize) -> UIScrollView? {
        for subview in view.subviews {
            if let scrollView = subview as? UIScrollView, contentSize.matches(scrollView.contentSize) {
                return scrollView
            }
            if let scrollView = find(in: subview, matching: contentSize) {
                return scrollView
            }
        }
        return nil
    }

    private static func find(
        in webView: WKWebView,
        matching contentSize: MapContentSize,
        attempts: Int,
        completion: @escaping (_ container: UIScrollView?) -> Void
    ) {
        if let container = find(in: webView, matching: contentSize) {
            completion(container)
            return
        }
        guard attempts > 1 else {
            completion(nil)
            return
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + searchDelay) {
            find(in: webView, matching: contentSize, attempts: attempts - 1, completion: completion)
        }
    }
}
