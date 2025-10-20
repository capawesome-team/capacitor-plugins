// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorRealtimekit",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorRealtimekit",
            targets: ["RealtimeKitPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/dyte-in/RealtimeKitCoreiOS.git", exact: "1.3.2"),
        .package(url: "https://github.com/dyte-in/RealtimeKitUI.git", exact: "0.4.4")
    ],
    targets: [
        .target(
            name: "RealtimeKitPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "RealtimeKitUI", package: "RealtimeKitUI")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "RealtimeKitPluginTests",
            dependencies: ["RealtimeKitPlugin"],
            path: "ios/PluginTests")
    ]
)
