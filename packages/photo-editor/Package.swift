// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPhotoEditor",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapawesomeCapacitorPhotoEditor",
            targets: ["PhotoEditorPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "6.0.0")
    ],
    targets: [
        .target(
            name: "PhotoEditorPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/PhotoEditorPlugin"),
        .testTarget(
            name: "PhotoEditorPluginTests",
            dependencies: ["PhotoEditorPlugin"],
            path: "ios/Tests/PhotoEditorPluginTests")
    ]
)
