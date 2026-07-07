// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorFacebookSignIn",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorFacebookSignIn",
            targets: ["FacebookSignInPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/facebook/facebook-ios-sdk", .upToNextMajor(from: "18.1.0"))
    ],
    targets: [
        .target(
            name: "FacebookSignInPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FacebookCore", package: "facebook-ios-sdk"),
                .product(name: "FacebookLogin", package: "facebook-ios-sdk")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FacebookSignInPluginTests",
            dependencies: ["FacebookSignInPlugin"],
            path: "ios/PluginTests")
    ]
)
