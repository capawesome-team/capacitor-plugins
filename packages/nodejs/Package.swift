// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorNodejs",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorNodejs",
            targets: ["NodejsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .binaryTarget(
            name: "NodeMobile",
            path: "ios/libnode/NodeMobile.xcframework"),
        .target(
            name: "NodejsPluginNative",
            dependencies: ["NodeMobile"],
            path: "ios/PluginNative",
            publicHeadersPath: "include",
            cxxSettings: [
                .headerSearchPath("../libnode/include/node")
            ]),
        .target(
            name: "NodejsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                "NodejsPluginNative"
            ],
            path: "ios/Plugin",
            resources: [.copy("Assets/builtin_modules")]),
        .testTarget(
            name: "NodejsPluginTests",
            dependencies: ["NodejsPlugin"],
            path: "ios/PluginTests")
    ],
    cxxLanguageStandard: .gnucxx17
)
