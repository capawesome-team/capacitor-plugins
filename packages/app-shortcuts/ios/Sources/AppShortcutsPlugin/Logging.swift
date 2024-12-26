import Capacitor

func log(_ items: Any..., separator: String = " ", terminator: String = "\n") {
    CAPLog.print("⚡️  AppShortcuts -", terminator: separator)
    for (itemIndex, item) in items.enumerated() {
        CAPLog.print(item, terminator: itemIndex == items.count - 1 ? terminator : separator)
    }
}
