import SwiftUI
import CapawesomeWatchSDK

@main
struct ExampleWatchApp: App {
    init() {
        CapawesomeWatch.shared.activate()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
