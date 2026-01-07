// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorSquareMobilePayments",
    platforms: [.iOS(.v16)],
    products: [
        .library(
            name: "CapawesomeCapacitorSquareMobilePayments",
            targets: ["SquareMobilePaymentsPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0"),
        .package(url: "https://github.com/square/mobile-payments-sdk-ios.git", exact: "2.3.1")
    ],
    targets: [
        .target(
            name: "SquareMobilePaymentsPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "SquareMobilePaymentsSDK", package: "mobile-payments-sdk-ios"),
                .product(name: "MockReaderUI", package: "mobile-payments-sdk-ios")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "SquareMobilePaymentsPluginTests",
            dependencies: ["SquareMobilePaymentsPlugin"],
            path: "ios/PluginTests")
    ]
)
