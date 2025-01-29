// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorLiveUpdate",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorLiveUpdate",
            targets: ["LiveUpdatePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/Alamofire/Alamofire.git", .upToNextMajor(from: "5.9.0")),
        .package(url: "https://github.com/ZipArchive/ZipArchive.git", .upToNextMinor(from: "2.4.0"))
    ],
    targets: [
        .target(
            name: "LiveUpdatePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Alamofire", package: "Alamofire"),
                .product(name: "ZipArchive", package: "ZipArchive")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "LiveUpdatePluginTests",
            dependencies: ["LiveUpdatePlugin"],
            path: "ios/PluginTests")
    ]
)
