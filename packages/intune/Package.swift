// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorIntune",
    platforms: [.iOS(.v17)],
    products: [
        .library(
            name: "CapawesomeCapacitorIntune",
            targets: ["IntunePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/msintuneappsdk/ms-intune-app-sdk-ios.git", exact: "21.7.1"),
        .package(url: "https://github.com/AzureAD/microsoft-authentication-library-for-objc.git", .upToNextMajor(from: "2.13.0"))
    ],
    targets: [
        .target(
            name: "IntunePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "IntuneMAMSwift", package: "ms-intune-app-sdk-ios"),
                .product(name: "MSAL", package: "microsoft-authentication-library-for-objc")
            ],
            path: "ios/Plugin")
    ]
)
