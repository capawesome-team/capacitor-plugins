// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPixlive",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorPixlive",
            targets: ["PixlivePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "PixlivePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "PixlivePluginTests",
            dependencies: ["PixlivePlugin"],
            path: "ios/PluginTests")
    ]
)
