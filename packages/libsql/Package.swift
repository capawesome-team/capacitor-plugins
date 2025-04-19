// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorLibsql",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorLibsql",
            targets: ["LibsqlPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "LibsqlPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "LibsqlPluginTests",
            dependencies: ["LibsqlPlugin"],
            path: "ios/PluginTests")
    ]
)