// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPhotoManipulator",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorPhotoManipulator",
            targets: ["PhotoManipulatorPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "PhotoManipulatorPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "PhotoManipulatorPluginTests",
            dependencies: ["PhotoManipulatorPlugin"],
            path: "ios/PluginTests")
    ]
)
