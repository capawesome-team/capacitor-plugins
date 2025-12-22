// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorLiveUpdate",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorLiveUpdate",
            targets: ["LiveUpdatePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/Alamofire/Alamofire.git", .upToNextMinor(from: "5.10.2")),
        .package(url: "https://github.com/weichsel/ZIPFoundation.git", .upToNextMinor(from: "0.9.0"))
    ],
    targets: [
        .target(
            name: "LiveUpdatePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Alamofire", package: "Alamofire"),
                .product(name: "ZIPFoundation", package: "ZIPFoundation")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "LiveUpdatePluginTests",
            dependencies: ["LiveUpdatePlugin"],
            path: "ios/PluginTests")
    ]
)
