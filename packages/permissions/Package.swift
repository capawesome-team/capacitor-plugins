// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPermissions",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorPermissions",
            targets: ["PermissionsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "PermissionsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "PermissionsPluginTests",
            dependencies: ["PermissionsPlugin"],
            path: "ios/PluginTests")
    ]
)
