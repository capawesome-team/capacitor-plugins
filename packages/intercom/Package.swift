// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorIntercom",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorIntercom",
            targets: ["IntercomPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/intercom/intercom-ios-sp.git", .upToNextMajor(from: "19.0.0"))
    ],
    targets: [
        .target(
            name: "IntercomPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Intercom", package: "intercom-ios-sp")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "IntercomPluginTests",
            dependencies: ["IntercomPlugin"],
            path: "ios/PluginTests")
    ]
)
