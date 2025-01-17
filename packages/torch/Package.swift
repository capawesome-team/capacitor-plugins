// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorTorch",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapawesomeCapacitorTorch",
            targets: ["TorchPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "TorchPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/TorchPlugin"),
        .testTarget(
            name: "TorchPluginTests",
            dependencies: ["TorchPlugin"],
            path: "ios/Tests/TorchPluginTests")
    ]
)
