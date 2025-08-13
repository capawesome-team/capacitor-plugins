import Foundation
import RealtimeKit
import RealtimeKitUI

@objc public class RealtimeKit: NSObject {
    private let plugin: RealtimeKitPlugin

    init(plugin: RealtimeKitPlugin) {
        self.plugin = plugin
    }

    @objc public func startMeeting(_ options: StartMeetingOptions, completion: @escaping (Error?) -> Void) {
        let meetingInfo = RtkMeetingInfo(authToken: options.authToken,
                                         enableAudio: options.enableAudio,
                                         enableVideo: options.enableVideo)
        let rtkUikit = RealtimeKitUI.init(meetingInfo: meetingInfo)

        guard let viewController = plugin.bridge?.viewController else {
            completion(CustomError.viewControllerUnavailable)
            return
        }

        Task { @MainActor in
            let controller = rtkUikit.startMeeting(completion: {})
            controller.modalPresentationStyle = .fullScreen
            viewController.present(controller, animated: true)
        }

        completion(nil)
    }
}
