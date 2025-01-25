// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPosthog",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapawesomeCapacitorPosthog",
            targets: ["PosthogPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0"),
        .package(url: "https://github.com/PostHog/posthog-ios.git", .upToNextMajor(from: "3.19.1"))
    ],
    targets: [
        .target(
            name: "PosthogPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "PostHog", package: "posthog-ios")
            ],
            path: "ios/Sources/PosthogPlugin"),
        .testTarget(
            name: "PosthogPluginTests",
            dependencies: ["PosthogPlugin"],
            path: "ios/Tests/PosthogPluginTests")
    ]
)
