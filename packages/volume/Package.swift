// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorVolume",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorVolume",
            targets: ["VolumePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "VolumePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "VolumePluginTests",
            dependencies: ["VolumePlugin"],
            path: "ios/PluginTests")
    ]
)
