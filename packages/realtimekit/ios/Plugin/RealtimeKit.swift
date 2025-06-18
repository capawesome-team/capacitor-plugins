import Foundation
import RealtimeKitUI
import RealtimeKitCore

@objc public class RealtimeKit: NSObject {
    @objc public func startMeeting(_ options: StartMeetingOptions, completion: @escaping (Error?) -> Void) {
        let meetingInfo = RtkMeetingInfo(authToken: options.authToken,
                                         enableAudio: options.enableAudio,
                                         enableVideo: options.enableVideo)
        let rtkUikit = RealtimeKitUI.init(meetingInfo: rtkMeetingInfo)

        guard let viewController = plugin.bridge?.viewController else {
            completion(CustomError.viewControllerUnavailable)
            return
        }

        Task { @MainActor in
            let controller = rtkUikit.startMeeting {
                [weak self] in
                guard let self = self else {return}
                self.dismiss(animated: true)
            }
            controller.modalPresentationStyle = .fullScreen
            viewController.present(controller, animated: true)
        }

        completion(nil)
    }
}
