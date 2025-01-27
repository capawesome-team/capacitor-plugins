// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeTeamCapacitorDatetimePicker",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeTeamCapacitorDatetimePicker",
            targets: ["DatetimePickerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "DatetimePickerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/DatetimePickerPlugin"),
        .testTarget(
            name: "DatetimePickerPluginTests",
            dependencies: ["DatetimePickerPlugin"],
            path: "ios/Tests/DatetimePickerPluginTests")
    ]
)
