// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorVapi",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapawesomeCapacitorVapi",
            targets: ["VapiPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main"),
        .package(url: "https://github.com/VapiAI/ios", branch: "main")
    ],
    targets: [
        .target(
            name: "VapiPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Vapi", package: "ios")
            ],
            path: "ios/Sources/VapiPlugin"),
        .testTarget(
            name: "VapiPluginTests",
            dependencies: ["VapiPlugin"],
            path: "ios/Tests/VapiPluginTests")
    ]
)
