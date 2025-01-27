// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorFilePicker",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorFilePicker",
            targets: ["FilePickerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "FilePickerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/FilePickerPlugin"),
        .testTarget(
            name: "FilePickerPluginTests",
            dependencies: ["FilePickerPlugin"],
            path: "ios/Tests/FilePickerPluginTests")
    ]
)
