require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapawesomeCapacitorPixlive'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target = '15.0'
  s.dependency 'Capacitor'
  s.swift_version = '5.1'
  s.static_framework = true
  s.frameworks = 'ARKit', 'AVFoundation', 'AVKit', 'Accelerate', 'AudioToolbox', 'CoreBluetooth', 'CoreGraphics', 'CoreImage', 'CoreLocation', 'CoreMotion', 'CoreVideo', 'ImageIO', 'JavaScriptCore', 'MediaPlayer', 'MessageUI', 'Metal', 'MetalKit', 'OpenGLES', 'QuartzCore', 'QuickLook', 'Security', 'Social', 'UIKit', 'UserNotifications', 'WebKit'
  s.libraries = 'z', 'c++', 'iconv'
  s.pod_target_xcconfig = {
    'FRAMEWORK_SEARCH_PATHS[sdk=iphoneos*]' => '$(inherited) "${PODS_ROOT}/../Frameworks/VDARSDK.xcframework/ios-arm64"',
    'FRAMEWORK_SEARCH_PATHS[sdk=iphonesimulator*]' => '$(inherited) "${PODS_ROOT}/../Frameworks/VDARSDK.xcframework/ios-arm64_x86_64-simulator"',
    'OTHER_LDFLAGS' => '-framework VDARSDK'
  }
  s.user_target_xcconfig = {
    'FRAMEWORK_SEARCH_PATHS[sdk=iphoneos*]' => '$(inherited) "${PODS_ROOT}/../Frameworks/VDARSDK.xcframework/ios-arm64"',
    'FRAMEWORK_SEARCH_PATHS[sdk=iphonesimulator*]' => '$(inherited) "${PODS_ROOT}/../Frameworks/VDARSDK.xcframework/ios-arm64_x86_64-simulator"',
    'OTHER_LDFLAGS' => '-framework VDARSDK'
  }
end
