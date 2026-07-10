// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorSystemWebView",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorSystemWebView",
            targets: ["SystemWebViewPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "SystemWebViewPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "SystemWebViewPluginTests",
            dependencies: ["SystemWebViewPlugin"],
            path: "ios/PluginTests")
    ]
)
