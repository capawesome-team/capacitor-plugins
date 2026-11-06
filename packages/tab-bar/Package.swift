// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorTabBar",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorTabBar",
            targets: ["TabBarPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/webview-native-ui/engine.git", from: "1.0.0")
    ],
    targets: [
        .target(
            name: "TabBarPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "WebViewNativeUIEngine", package: "engine")
            ],
            path: "ios/Plugin")
    ]
)
