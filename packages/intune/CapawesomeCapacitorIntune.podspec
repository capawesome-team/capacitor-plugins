require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))
intune_mam_sdk_version = '21.7.1'

Pod::Spec.new do |s|
  s.name = 'CapawesomeCapacitorIntune'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  # The Microsoft Intune App SDK for iOS is not available via CocoaPods.
  # It is downloaded from the official Microsoft repository at install time
  # (see https://github.com/msintuneappsdk/ms-intune-app-sdk-ios).
  s.prepare_command = <<-CMD
    rm -rf IntuneAppSDK
    git clone --depth 1 --branch #{intune_mam_sdk_version} --filter=blob:none --sparse https://github.com/msintuneappsdk/ms-intune-app-sdk-ios.git IntuneAppSDK
    cd IntuneAppSDK
    git sparse-checkout set IntuneMAMSwift.xcframework IntuneMAMSwiftStub.xcframework
  CMD
  s.vendored_frameworks = 'IntuneAppSDK/IntuneMAMSwift.xcframework', 'IntuneAppSDK/IntuneMAMSwiftStub.xcframework'
  s.ios.deployment_target = '17.0'
  s.dependency 'Capacitor'
  s.dependency 'MSAL', '~> 2.13'
  s.static_framework = true
  s.swift_version = '5.1'
end
