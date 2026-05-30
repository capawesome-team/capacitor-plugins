// swift-tools-version: 6.1
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorLiveUpdate",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorLiveUpdate",
            targets: ["LiveUpdatePlugin"])
    ],
    traits: [
        .trait(
            name: "IonicProvider",
            description: "Enables registration with the Ionic Live Update Provider SDK."
        )
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/Alamofire/Alamofire.git", .upToNextMinor(from: "5.10.2")),
        .package(url: "https://github.com/weichsel/ZIPFoundation.git", .upToNextMinor(from: "0.9.0")),
        .package(url: "https://github.com/ionic-team/live-update-provider-sdk.git", exact: "0.1.0-alpha.2")
    ],
    targets: [
        .target(
            name: "LiveUpdatePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Alamofire", package: "Alamofire"),
                .product(name: "ZIPFoundation", package: "ZIPFoundation"),
                .product(
                    name: "LiveUpdateProvider",
                    package: "live-update-provider-sdk",
                    condition: .when(traits: ["IonicProvider"])
                )
            ],
            path: "ios/Plugin",
            swiftSettings: [
                .define("CAPAWESOME_INCLUDE_IONIC_PROVIDER", .when(traits: ["IonicProvider"]))
            ]),
        .testTarget(
            name: "LiveUpdatePluginTests",
            dependencies: ["LiveUpdatePlugin"],
            path: "ios/PluginTests")
    ],
    swiftLanguageModes: [.v5]
)
