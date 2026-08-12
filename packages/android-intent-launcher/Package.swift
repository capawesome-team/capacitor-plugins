// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorAndroidIntentLauncher",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorAndroidIntentLauncher",
            targets: ["AndroidIntentLauncherPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "AndroidIntentLauncherPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "AndroidIntentLauncherPluginTests",
            dependencies: ["AndroidIntentLauncherPlugin"],
            path: "ios/PluginTests")
    ]
)
