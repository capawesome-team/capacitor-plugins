import Foundation
import UIKit

@objc public class BackgroundTask: NSObject {
    private let isolationQueue = DispatchQueue(label: "io.capawesome.backgroundtask.isolation")
    private var taskIds: [String: UIBackgroundTaskIdentifier] = [:]

    @objc public func beforeExit(_ callbackId: String) {
        // Use the isolation queue to synchronize access to the taskIds dictionary
        isolationQueue.async { [weak self] in
            guard let self = self else { return }

            var taskId = UIBackgroundTaskIdentifier.invalid
            taskId = UIApplication.shared.beginBackgroundTask {
                // Finish the task if time expires. This expiration handler can run on any thread,
                // so we MUST hop back to the isolation queue.
                self.isolationQueue.async {
                    UIApplication.shared.endBackgroundTask(taskId)
                    self.taskIds.removeValue(forKey: callbackId)
                }
            }
            self.taskIds[callbackId] = taskId
        }
    }

    @objc public func finish(_ callbackId: String) {
        isolationQueue.async { [weak self] in
            guard let self = self, let taskId = self.taskIds[callbackId] else {
                return
            }
            UIApplication.shared.endBackgroundTask(taskId)
            self.taskIds.removeValue(forKey: callbackId)
        }
    }
}
