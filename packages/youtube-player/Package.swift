// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorYoutubePlayer",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorYoutubePlayer",
            targets: ["YoutubePlayerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/mukeshydv/YoutubePlayerView.git", .upToNextMajor(from: "1.2.2"))
    ],
    targets: [
        .target(
            name: "YoutubePlayerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "YoutubePlayerView", package: "YoutubePlayerView")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "YoutubePlayerPluginTests",
            dependencies: ["YoutubePlayerPlugin"],
            path: "ios/PluginTests")
    ]
)
