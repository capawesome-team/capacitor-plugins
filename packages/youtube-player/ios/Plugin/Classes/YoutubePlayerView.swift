//
//  YoutubePlayerView.swift
//
//  Modified copy of YoutubePlayerView (https://github.com/mukeshydv/YoutubePlayerView),
//  trimmed to the functionality used by this plugin and extended with a `host` parameter.
//
//  Copyright (c) 2018 Mukesh Yadav <mails4ymukesh@gmail.com>
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files (the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
//

import UIKit
import WebKit

public class YoutubePlayerView: UIView {
    private enum Callback: String {
        case onError
        case onPlayTime
        case onReady
        case onStateChange
    }

    private static let callbackScheme = "ytplayer"
    private static let htmlTemplate = """
    <head>
        <script type="text/javascript">
            var tag = document.createElement('script');
            tag.src = 'https://www.youtube.com/iframe_api';
            var firstScriptTag = document.getElementsByTagName('script')[0];
            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

            var player;
            var error = false;

            function onYouTubeIframeAPIReady() {
                player = new YT.Player('player', {
                    events: {
                        'onReady': onReady,
                        'onStateChange': onStateChange,
                        'onError': onPlayerError
                    }
                });

                function sendCurrentTime() {
                    if (player.getPlayerState() == YT.PlayerState.PLAYING) {
                        window.location.href = '\(callbackScheme)://onPlayTime?data=' + player.getCurrentTime();
                    }
                }

                window.setInterval(sendCurrentTime, 500);
            }

            function onReady(event) {
                window.location.href = '\(callbackScheme)://onReady?data=' + event.data;
            }

            function onStateChange(event) {
                if (!error) {
                    window.location.href = '\(callbackScheme)://onStateChange?data=' + event.data;
                } else {
                    error = false;
                }
            }

            function onPlayerError(event) {
                if (event.data == 100) {
                    error = true;
                }
                window.location.href = '\(callbackScheme)://onError?data=' + event.data;
            }

            window.onresize = function() {
                player.setSize(window.innerWidth, window.innerHeight);
            }
        </script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style type="text/css">body { margin: 0; }</style>
    </head>
    <iframe id="player" width="100%" height="100%" src="{{embedUrl}}" frameborder="0" allowfullscreen></iframe>
    """

    public weak var delegate: YoutubePlayerViewDelegate?

    private var autoplay = false
    private let webView: WKWebView = {
        let configuration = WKWebViewConfiguration()
        configuration.allowsInlineMediaPlayback = true
        configuration.mediaTypesRequiringUserActionForPlayback = []
        let webView = WKWebView(frame: .zero, configuration: configuration)
        webView.scrollView.bounces = false
        webView.scrollView.isScrollEnabled = false
        webView.translatesAutoresizingMaskIntoConstraints = false
        return webView
    }()

    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeWebView()
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
        initializeWebView()
    }

    public func cueVideoById(_ videoId: String, startSeconds: Float) {
        evaluateJavaScript("player.cueVideoById('\(videoId)', \(startSeconds));")
    }

    public func fetchCurrentTime(_ completion: @escaping (Double?) -> Void) {
        webView.evaluateJavaScript("player.getCurrentTime();") { data, _ in
            completion(data as? Double)
        }
    }

    public func fetchDuration(_ completion: @escaping (Double?) -> Void) {
        webView.evaluateJavaScript("player.getDuration();") { data, _ in
            completion(data as? Double)
        }
    }

    public func load(videoId: String, host: String, playerVars: [String: Any]) {
        guard let embedUrl = createEmbedUrl(videoId: videoId, host: host, playerVars: playerVars) else {
            return
        }
        autoplay = (playerVars["autoplay"] as? Int) == 1
        let origin = playerVars["origin"] as? String ?? ""
        webView.loadHTMLString(createHtml(embedUrl: embedUrl), baseURL: URL(string: origin))
    }

    public func loadVideoById(_ videoId: String, startSeconds: Float) {
        evaluateJavaScript("player.loadVideoById('\(videoId)', \(startSeconds));")
    }

    public func mute() {
        evaluateJavaScript("player.mute();")
    }

    public func pause() {
        delegate?.playerView(self, didChangeToState: .paused)
        evaluateJavaScript("player.pauseVideo();")
    }

    public func play() {
        evaluateJavaScript("player.playVideo();")
    }

    public func seek(to seconds: Float, allowSeekAhead: Bool) {
        evaluateJavaScript("player.seekTo(\(seconds), \(allowSeekAhead));")
    }

    public func setPlaybackRate(_ rate: Float) {
        evaluateJavaScript("player.setPlaybackRate(\(rate));")
    }

    public func setVolume(_ volume: Int) {
        evaluateJavaScript("player.setVolume(\(volume));")
    }

    public func unmute() {
        evaluateJavaScript("player.unMute();")
    }

    private func createEmbedUrl(videoId: String, host: String, playerVars: [String: Any]) -> URL? {
        var queryItems = playerVars.map { URLQueryItem(name: $0.key, value: "\($0.value)") }
        queryItems.append(URLQueryItem(name: "enablejsapi", value: "1"))
        var components = URLComponents(string: host)
        components?.path = "/embed/\(videoId)"
        components?.queryItems = queryItems
        return components?.url
    }

    private func createHtml(embedUrl: URL) -> String {
        return YoutubePlayerView.htmlTemplate.replacingOccurrences(of: "{{embedUrl}}", with: embedUrl.absoluteString)
    }

    private func evaluateJavaScript(_ javaScript: String) {
        webView.evaluateJavaScript(javaScript, completionHandler: nil)
    }

    private func handleCallback(_ url: URL) {
        guard let host = url.host, let callback = Callback(rawValue: host) else {
            return
        }
        let data = url.query?.components(separatedBy: "=").last
        switch callback {
        case .onError:
            delegate?.playerView(self, receivedError: YoutubePlayerError(code: data))
        case .onPlayTime:
            if let data = data, let time = Float(data) {
                delegate?.playerView(self, didPlayTime: time)
            }
        case .onReady:
            if autoplay {
                play()
            }
            delegate?.playerViewDidBecomeReady(self)
        case .onStateChange:
            if let data = data, let state = YoutubePlayerState(rawValue: data) {
                delegate?.playerView(self, didChangeToState: state)
            }
        }
    }

    private func initializeWebView() {
        webView.navigationDelegate = self
        addSubview(webView)
        NSLayoutConstraint.activate([
            webView.leadingAnchor.constraint(equalTo: leadingAnchor),
            webView.trailingAnchor.constraint(equalTo: trailingAnchor),
            webView.topAnchor.constraint(equalTo: topAnchor),
            webView.bottomAnchor.constraint(equalTo: bottomAnchor)
        ])
    }
}

extension YoutubePlayerView: WKNavigationDelegate {
    public func webView(
        _ webView: WKWebView,
        decidePolicyFor navigationAction: WKNavigationAction,
        decisionHandler: @escaping (WKNavigationActionPolicy) -> Void
    ) {
        guard let url = navigationAction.request.url, url.scheme == YoutubePlayerView.callbackScheme else {
            decisionHandler(.allow)
            return
        }
        handleCallback(url)
        decisionHandler(.cancel)
    }
}
