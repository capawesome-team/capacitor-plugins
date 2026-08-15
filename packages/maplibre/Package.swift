// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorMapLibre",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorMapLibre",
            targets: ["MapLibrePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/maplibre/maplibre-gl-native-distribution.git", from: "6.28.0")
    ],
    targets: [
        .target(
            name: "MapLibrePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "MapLibre", package: "maplibre-gl-native-distribution")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "MapLibrePluginTests",
            dependencies: ["MapLibrePlugin"],
            path: "ios/PluginTests")
    ]
)
