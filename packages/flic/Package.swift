// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorFlic",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorFlic",
            targets: ["FlicPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "FlicPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .target(name: "flic2lib")
            ],
            path: "ios/Plugin"),
        .binaryTarget(
            name: "flic2lib",
            path: "ios/flic2lib.xcframework"),
        .testTarget(
            name: "FlicPluginTests",
            dependencies: ["FlicPlugin"],
            path: "ios/PluginTests")
    ]
)
