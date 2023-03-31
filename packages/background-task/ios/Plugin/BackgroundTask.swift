import Foundation
import UIKit

@objc public class BackgroundTask: NSObject {
    var taskIds: [String: UIBackgroundTaskIdentifier] = [:]

    @objc public func beforeExit(_ callbackId: String) {
        DispatchQueue.global().async {
            var taskId = UIBackgroundTaskIdentifier.invalid
            taskId = UIApplication.shared.beginBackgroundTask {
                // Finish the task if time expires.
                UIApplication.shared.endBackgroundTask(taskId)
                self.taskIds.removeValue(forKey: callbackId)
            }
            self.taskIds[callbackId] = taskId
        }
    }

    @objc public func finish(_ callbackId: String) {
        DispatchQueue.global().async {
            guard let taskId = self.taskIds[callbackId] else {
                return
            }
            UIApplication.shared.endBackgroundTask(taskId)
            self.taskIds.removeValue(forKey: callbackId)
        }
    }
}
