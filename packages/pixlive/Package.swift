// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapawesomeCapacitorPixlive",
    platforms: [.iOS(.v15)],
    products: [
        .library(
            name: "CapawesomeCapacitorPixlive",
            targets: ["PixlivePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "8.0.0")
    ],
    targets: [
        .target(
            name: "PixlivePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .target(name: "VDARSDK")
            ],
            path: "ios/Plugin",
            linkerSettings: [
                .linkedFramework("ARKit"),
                .linkedFramework("AVFoundation"),
                .linkedFramework("AVKit"),
                .linkedFramework("Accelerate"),
                .linkedFramework("AudioToolbox"),
                .linkedFramework("CoreBluetooth"),
                .linkedFramework("CoreGraphics"),
                .linkedFramework("CoreImage"),
                .linkedFramework("CoreLocation"),
                .linkedFramework("CoreMotion"),
                .linkedFramework("CoreVideo"),
                .linkedFramework("ImageIO"),
                .linkedFramework("JavaScriptCore"),
                .linkedFramework("MediaPlayer"),
                .linkedFramework("MessageUI"),
                .linkedFramework("Metal"),
                .linkedFramework("MetalKit"),
                .linkedFramework("OpenGLES"),
                .linkedFramework("QuartzCore"),
                .linkedFramework("QuickLook"),
                .linkedFramework("Security"),
                .linkedFramework("Social"),
                .linkedFramework("UIKit"),
                .linkedFramework("UserNotifications"),
                .linkedFramework("WebKit"),
                .linkedLibrary("z"),
                .linkedLibrary("c++"),
                .linkedLibrary("iconv"),
                // The SDK is a static framework with Objective-C categories, which are only loaded with `-ObjC`
                .unsafeFlags(["-ObjC"])
            ]),
        // The PixLive SDK is not bundled with this package and must be copied into the
        // `ios/App/Frameworks/` directory of the Capacitor app (same location as for CocoaPods)
        .binaryTarget(
            name: "VDARSDK",
            path: "../../../ios/App/Frameworks/VDARSDK.xcframework")
    ]
)
