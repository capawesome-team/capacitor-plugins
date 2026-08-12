// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorAppLanguage",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorAppLanguage",
            targets: ["AppLanguagePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "AppLanguagePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "AppLanguagePluginTests",
            dependencies: ["AppLanguagePlugin"],
            path: "ios/PluginTests")
    ]
)
