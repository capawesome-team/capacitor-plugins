import Foundation
import UIKit

@objc public class DeviceInfo: NSObject {
    let plugin: DeviceInfoPlugin

    init(plugin: DeviceInfoPlugin) {
        self.plugin = plugin
    }

    @objc public func getId(completion: @escaping (GetIdResult?, Error?) -> Void) {
        let identifier = UIDevice.current.identifierForVendor?.uuidString ?? ""
        completion(GetIdResult(identifier: identifier), nil)
    }

    @objc public func getInfo(completion: @escaping (GetInfoResult?, Error?) -> Void) {
        let result = GetInfoResult(
            androidSdkVersion: nil,
            deviceType: determineDeviceType(),
            iosVersion: ProcessInfo.processInfo.operatingSystemVersion.majorVersion,
            isVirtual: determineIsVirtual(),
            manufacturer: "Apple",
            model: determineModel(),
            name: UIDevice.current.name,
            operatingSystem: "ios",
            osVersion: UIDevice.current.systemVersion,
            platform: "ios",
            totalMemory: Int(ProcessInfo.processInfo.physicalMemory),
            usedMemory: determineUsedMemory(),
            webViewVersion: UIDevice.current.systemVersion
        )
        completion(result, nil)
    }

    @objc public func getUptime(completion: @escaping (GetUptimeResult?, Error?) -> Void) {
        let uptime = Int(ProcessInfo.processInfo.systemUptime * 1000)
        completion(GetUptimeResult(uptime: uptime), nil)
    }

    private func determineDeviceType() -> String {
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:
            return "phone"
        case .pad:
            return "tablet"
        case .tv:
            return "tv"
        case .mac:
            return "desktop"
        default:
            return "unknown"
        }
    }

    private func determineIsVirtual() -> Bool {
        #if targetEnvironment(simulator)
        return true
        #else
        return false
        #endif
    }

    private func determineModel() -> String {
        var systemInfo = utsname()
        uname(&systemInfo)
        let machineMirror = Mirror(reflecting: systemInfo.machine)
        let identifier = machineMirror.children.reduce(into: "") { identifier, element in
            guard let value = element.value as? Int8, value != 0 else {
                return
            }
            identifier += String(UnicodeScalar(UInt8(value)))
        }
        return identifier.isEmpty ? UIDevice.current.model : identifier
    }

    private func determineUsedMemory() -> Int? {
        var info = task_vm_info_data_t()
        var count = mach_msg_type_number_t(MemoryLayout<task_vm_info_data_t>.size) / 4
        let result = withUnsafeMutablePointer(to: &info) { pointer in
            pointer.withMemoryRebound(to: integer_t.self, capacity: Int(count)) { rebound in
                task_info(mach_task_self_, task_flavor_t(TASK_VM_INFO), rebound, &count)
            }
        }
        guard result == KERN_SUCCESS else {
            return nil
        }
        return Int(info.phys_footprint)
    }
}
