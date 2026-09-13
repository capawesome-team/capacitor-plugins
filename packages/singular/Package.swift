// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorSingular",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorSingular",
            targets: ["SingularPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/singular-labs/Singular-iOS-SDK.git", .upToNextMajor(from: "12.14.1"))
    ],
    targets: [
        .target(
            name: "SingularPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Singular", package: "Singular-iOS-SDK")
            ],
            path: "ios/Plugin")
    ]
)
