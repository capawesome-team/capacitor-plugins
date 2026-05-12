// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorFormbricks",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "CapawesomeCapacitorFormbricks",
            targets: ["FormbricksPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/formbricks/ios.git", .upToNextMajor(from: "1.2.0"))
    ],
    targets: [
        .target(
            name: "FormbricksPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FormbricksSDK", package: "ios")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FormbricksPluginTests",
            dependencies: ["FormbricksPlugin"],
            path: "ios/PluginTests")
    ]
)
