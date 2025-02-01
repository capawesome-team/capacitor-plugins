// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorAssetManager",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorAssetManager",
            targets: ["AssetManagerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "AssetManagerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "AssetManagerPluginTests",
            dependencies: ["AssetManagerPlugin"],
            path: "ios/PluginTests")
    ]
)