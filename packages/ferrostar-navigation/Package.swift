// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorFerrostarNavigation",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "CapawesomeCapacitorFerrostarNavigation",
            targets: ["FerrostarNavigationPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/stadiamaps/ferrostar.git", from: "0.59.0")
    ],
    targets: [
        .target(
            name: "FerrostarNavigationPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FerrostarCore", package: "ferrostar")
            ],
            path: "ios/Plugin")
    ]
)
