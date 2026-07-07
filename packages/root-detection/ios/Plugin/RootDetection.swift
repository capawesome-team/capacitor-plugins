import Foundation
import UIKit

@objc public class RootDetection: NSObject {
    private let plugin: RootDetectionPlugin

    init(plugin: RootDetectionPlugin) {
        self.plugin = plugin
    }

    @objc public func isEmulator(completion: @escaping (IsEmulatorResult?, Error?) -> Void) {
        #if targetEnvironment(simulator)
        completion(IsEmulatorResult(emulator: true), nil)
        #else
        completion(IsEmulatorResult(emulator: false), nil)
        #endif
    }

    @objc public func isRooted(completion: @escaping (IsRootedResult?, Error?) -> Void) {
        completion(IsRootedResult(rooted: isJailbroken()), nil)
    }

    private func canOpenJailbreakScheme() -> Bool {
        guard let url = URL(string: "cydia://package/com.example.package") else {
            return false
        }
        return UIApplication.shared.canOpenURL(url)
    }

    private func canWriteOutsideSandbox() -> Bool {
        let path = "/private/jailbreak.txt"
        do {
            try "jailbreak".write(toFile: path, atomically: true, encoding: .utf8)
            try FileManager.default.removeItem(atPath: path)
            return true
        } catch {
            return false
        }
    }

    private func hasSuspiciousFiles() -> Bool {
        let paths = [
            "/Applications/Cydia.app",
            "/Library/MobileSubstrate/MobileSubstrate.dylib",
            "/bin/bash",
            "/usr/sbin/sshd",
            "/etc/apt",
            "/private/var/lib/apt",
            "/usr/bin/ssh"
        ]
        for path in paths where FileManager.default.fileExists(atPath: path) {
            return true
        }
        return false
    }

    private func isJailbroken() -> Bool {
        #if targetEnvironment(simulator)
        return false
        #else
        return hasSuspiciousFiles() || canWriteOutsideSandbox() || canOpenJailbreakScheme()
        #endif
    }
}
