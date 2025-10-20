// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorScreenshot",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorScreenshot",
            targets: ["ScreenshotPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "ScreenshotPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "ScreenshotPluginTests",
            dependencies: ["ScreenshotPlugin"],
            path: "ios/PluginTests")
    ]
)
