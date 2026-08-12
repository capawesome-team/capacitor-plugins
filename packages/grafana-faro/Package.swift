// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorGrafanaFaro",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorGrafanaFaro",
            targets: ["GrafanaFaroPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/microsoft/plcrashreporter.git", .upToNextMajor(from: "1.12.0"))
    ],
    targets: [
        .target(
            name: "GrafanaFaroPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "CrashReporter", package: "plcrashreporter")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "GrafanaFaroPluginTests",
            dependencies: ["GrafanaFaroPlugin"],
            path: "ios/PluginTests")
    ]
)
