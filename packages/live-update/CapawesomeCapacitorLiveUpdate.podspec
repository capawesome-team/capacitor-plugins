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
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target = '15.0'
  s.dependency 'Capacitor'
  s.dependency 'Alamofire', '>= 5.10.2', '< 6.0.0'
  s.dependency 'ZIPFoundation', '~> 0.9.0'
  s.swift_version = '5.1'
  s.static_framework = true
  s.default_subspec = 'Default'

  s.subspec 'Default' do |ss|
    # Default subspec
  end

  # Opt-in subspec that pulls in the Ionic Live Update Provider SDK and
  # compiles in the provider/manager classes guarded by
  # `#if CAPAWESOME_INCLUDE_IONIC_PROVIDER`.
  s.subspec 'IonicProvider' do |ss|
    ss.dependency 'LiveUpdateProvider', '0.1.0-alpha.2'
    ss.pod_target_xcconfig = {
      'OTHER_SWIFT_FLAGS' => '$(inherited) -DCAPAWESOME_INCLUDE_IONIC_PROVIDER'
    }
  end
end
