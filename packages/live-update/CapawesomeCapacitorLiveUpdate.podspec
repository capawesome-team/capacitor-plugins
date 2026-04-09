require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapawesomeCapacitorLiveUpdate'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.ios.deployment_target = '15.0'
  s.swift_version = '5.1'
  s.static_framework = true

  s.default_subspec = 'Default'

  # Default subspec — plain plugin, no Ionic Live Update Provider SDK integration.
  s.subspec 'Default' do |ss|
    ss.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    ss.dependency 'Capacitor'
    ss.dependency 'Alamofire', '~> 5.10.2'
    ss.dependency 'ZIPFoundation', '~> 0.9.0'
  end

  # Opt-in subspec that pulls in the Ionic Live Update Provider SDK and registers
  # the Capawesome provider with `LiveUpdateProviderRegistry` on plugin load.
  s.subspec 'IonicProvider' do |ss|
    ss.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    ss.dependency 'Capacitor'
    ss.dependency 'Alamofire', '~> 5.10.2'
    ss.dependency 'ZIPFoundation', '~> 0.9.0'
    ss.dependency 'LiveUpdateProvider', '0.1.0-alpha.2'
    ss.pod_target_xcconfig = {
      'OTHER_SWIFT_FLAGS' => '$(inherited) -DCAPAWESOME_INCLUDE_IONIC_PROVIDER'
    }
  end
end
