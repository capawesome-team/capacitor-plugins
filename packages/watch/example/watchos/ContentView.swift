import SwiftUI
import CapawesomeWatchSDK

struct ContentView: View {
    @ObservedObject private var watch = CapawesomeWatch.shared
    @State private var lastMessage = "-"

    var body: some View {
        ScrollView {
            VStack(spacing: 8) {
                Text(watch.reachable ? "Reachable" : "Not reachable")
                    .font(.footnote)
                Text("Last message: \(lastMessage)")
                    .font(.footnote)
                Button("Send Message") {
                    watch.sendMessage(["text": "Hello from the watch!"])
                }
                Button("Send Message (Reply)") {
                    watch.sendMessage(["text": "Hello from the watch!"], replyHandler: { reply in
                        lastMessage = String(describing: reply)
                    })
                }
                Button("Transfer User Info") {
                    watch.transferUserInfo(["sentAt": Date().timeIntervalSince1970])
                }
                Button("Update State") {
                    try? watch.updateState(["counter": Int.random(in: 0...100)])
                }
            }
        }
        .onAppear {
            watch.onMessageReceived = { data, reply in
                lastMessage = String(describing: data)
                reply?(["text": "Hello from the watch!"])
            }
            watch.onUserInfoReceived = { data in
                lastMessage = String(describing: data)
            }
        }
    }
}
