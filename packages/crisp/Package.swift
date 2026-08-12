// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorCrisp",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorCrisp",
            targets: ["CrispPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/crisp-im/crisp-sdk-ios.git", .upToNextMajor(from: "2.13.0"))
    ],
    targets: [
        .target(
            name: "CrispPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Crisp", package: "crisp-sdk-ios")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "CrispPluginTests",
            dependencies: ["CrispPlugin"],
            path: "ios/PluginTests")
    ]
)
