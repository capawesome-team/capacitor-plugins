// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorAppReview",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorAppReview",
            targets: ["AppReviewPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "AppReviewPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/AppReviewPlugin"),
        .testTarget(
            name: "AppReviewPluginTests",
            dependencies: ["AppReviewPlugin"],
            path: "ios/Tests/AppReviewPluginTests")
    ]
)
