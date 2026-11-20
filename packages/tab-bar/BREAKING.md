# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### Tab bar controller

The tab bar is now rendered by a native tab bar controller, and the web view is embedded as its content. This has the following effects:

- The system decides where the tab bar is placed: at the bottom on iPhone, at the top on iPad and in the vertical bar on iPhone Duo. On iPad, the tab bar therefore moves from the bottom to the top of the screen.
- The safe area insets of the web view are extended on the edge where the tab bar is placed, not only at the bottom. Make sure your content respects all safe area insets, for example using `padding: env(safe-area-inset-top) env(safe-area-inset-right) env(safe-area-inset-bottom) env(safe-area-inset-left)`.
- The `view` of the Capacitor bridge view controller is no longer the web view itself. Native code that needs the web view must use `bridge.webView`.
- A maximum of five tabs is supported. `setTabs(...)` rejects if more tabs are provided.
- The plugin requires iOS 18 or later. On earlier iOS versions, all methods reject as unavailable. The deployment target of your app can stay at iOS 15.
