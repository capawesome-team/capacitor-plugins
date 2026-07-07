// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorMapsLauncher",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorMapsLauncher",
            targets: ["MapsLauncherPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "MapsLauncherPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "MapsLauncherPluginTests",
            dependencies: ["MapsLauncherPlugin"],
            path: "ios/PluginTests")
    ]
)
