import CoreBluetooth
import Foundation

class BluetoothPermissionHandler: NSObject, CBCentralManagerDelegate {
    private var centralManager: CBCentralManager?
    private var completion: (() -> Void)?

    func centralManagerDidUpdateState(_ central: CBCentralManager) {
        guard CBCentralManager.authorization != .notDetermined else {
            return
        }
        centralManager = nil
        completion?()
        completion = nil
    }

    func requestAuthorization(completion: @escaping () -> Void) {
        self.completion = completion
        centralManager = CBCentralManager(delegate: self, queue: nil, options: [CBCentralManagerOptionShowPowerAlertKey: false])
    }
}
