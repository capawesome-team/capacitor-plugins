// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorGoogleSignIn",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorGoogleSignIn",
            targets: ["GoogleSignInPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/google/GoogleSignIn-iOS", .upToNextMajor(from: "8.0.0"))
    ],
    targets: [
        .target(
            name: "GoogleSignInPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "GoogleSignIn", package: "GoogleSignIn-iOS")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "GoogleSignInPluginTests",
            dependencies: ["GoogleSignInPlugin"],
            path: "ios/PluginTests")
    ]
)
