// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorSuperwall",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorSuperwall",
            targets: ["SuperwallPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/superwall/Superwall-iOS.git", from: "4.11.0")
    ],
    targets: [
        .target(
            name: "SuperwallPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "SuperwallKit", package: "Superwall-iOS")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "SuperwallPluginTests",
            dependencies: ["SuperwallPlugin"],
            path: "ios/PluginTests")
    ]
)
