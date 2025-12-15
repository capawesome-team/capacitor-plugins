// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorBadge",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorBadge",
            targets: ["BadgePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "BadgePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "BadgePluginTests",
            dependencies: ["BadgePlugin"],
            path: "ios/PluginTests")
    ]
)
