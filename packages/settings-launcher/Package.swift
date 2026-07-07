// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorSettingsLauncher",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorSettingsLauncher",
            targets: ["SettingsLauncherPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "SettingsLauncherPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "SettingsLauncherPluginTests",
            dependencies: ["SettingsLauncherPlugin"],
            path: "ios/PluginTests")
    ]
)
