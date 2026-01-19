require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'CapawesomeCapacitorSquareMobilePayments'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :git => package['repository']['url'], :tag => s.version.to_s }
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
  s.ios.deployment_target = '15.0'
  s.dependency 'Capacitor'
  s.dependency 'SquareMobilePaymentsSDK', '~> 2.3.1'
  s.swift_version = '5.1'

  # Optional MockReaderUI dependency for Debug builds
  s.xcconfig = {
    'OTHER_LDFLAGS[config=Debug]' => '$(inherited) -weak_framework MockReaderUI'
  }
end
