// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorDeviceInfo",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorDeviceInfo",
            targets: ["DeviceInfoPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "DeviceInfoPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin",
            resources: [.copy("PrivacyInfo.xcprivacy")]),
        .testTarget(
            name: "DeviceInfoPluginTests",
            dependencies: ["DeviceInfoPlugin"],
            path: "ios/PluginTests")
    ]
)
