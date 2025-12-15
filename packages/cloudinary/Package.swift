// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorCloudinary",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorCloudinary",
            targets: ["CloudinaryPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/cloudinary/cloudinary_ios.git", .upToNextMajor(from: "5.1.0"))
    ],
    targets: [
        .target(
            name: "CloudinaryPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "Cloudinary", package: "cloudinary_ios")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "CloudinaryPluginTests",
            dependencies: ["CloudinaryPlugin"],
            path: "ios/PluginTests")
    ]
)
