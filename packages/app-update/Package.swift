// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorAppUpdate",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapawesomeCapacitorAppUpdate",
            targets: ["AppUpdatePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "AppUpdatePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AppUpdatePlugin"),
        .testTarget(
            name: "AppUpdatePluginTests",
            dependencies: ["AppUpdatePlugin"],
            path: "ios/Tests/AppUpdatePluginTests")
    ]
)
