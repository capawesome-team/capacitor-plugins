import Capacitor

/// Capacitor bridge view controller subclass that registers this app's
/// custom in-app plugin(s). In-app plugins declared inside the App target
/// are not auto-discovered by the Capacitor runtime — they must be
/// registered explicitly via `bridge?.registerPluginInstance(...)`.
///
/// See https://capacitorjs.com/docs/ios/custom-code
class MyViewController: CAPBridgeViewController {
    override open func capacitorDidLoad() {
        bridge?.registerPluginInstance(IonicProviderTestPlugin())
    }
}
