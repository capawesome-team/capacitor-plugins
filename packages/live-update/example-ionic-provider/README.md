# example-ionic-provider

Isolated test harness for the **Ionic Live Update Provider SDK** integration in
`@capawesome/capacitor-live-update`. The plugin registers itself with
`LiveUpdateProviderRegistry` under the provider id `capawesome` so Federated
Capacitor and Ionic Portals shells can use Capawesome Cloud as a live update
provider.

> **This is not an Ionic Portals app.** It is a plain Capacitor app that ships a
> tiny custom Capacitor plugin (`IonicProviderTest`) to exercise the provider
> SDK end-to-end **in isolation** — without needing a Portals or Federated
> Capacitor host. For a full, real Ionic Portals app that delivers live updates
> to multiple portals via Capawesome Cloud, see
> [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo).

## How it's wired up

- **Android** — `android/variables.gradle` sets
  `capawesomeCapacitorLiveUpdateIncludeIonicProvider = true`, so the plugin
  pulls in `io.ionic:liveupdateprovider` and registers the provider on plugin
  load. The custom `IonicProviderTestPlugin.java` lives in
  `android/app/src/main/java/com/example/plugin/` and is registered in
  `MainActivity.java`.
- **iOS** — `ios/App/Podfile` uses the
  `CapawesomeCapacitorLiveUpdate/IonicProvider` subspec, which adds the
  `LiveUpdateProvider` pod and sets `-DCAPAWESOME_INCLUDE_IONIC_PROVIDER`. The
  custom `IonicProviderTestPlugin.swift` lives in `ios/App/App/` and is
  auto-discovered by the Capacitor bridge via the `CAPBridgedPlugin` protocol.

## Setup

```bash
npm install
npm run build
npx cap sync
```

Then either:

```bash
npx cap run android
# or
cd ios/App && pod install && cd -
npx cap run ios
```

## Test UI

The web UI exposes three buttons backed by the custom `IonicProviderTest`
plugin:

1. **Is Provider Registered?** — calls `LiveUpdateProviderRegistry.resolve("capawesome")`. Should return `{ registered: true }` once the app finishes loading. If it returns `false`, the optional native dependency was not actually linked — re-check `variables.gradle` (Android) or the `Podfile` subspec (iOS).
2. **Get Latest App Directory** — constructs a manager via `provider.createManager(config)` using the values from the form, then returns its `latestAppDirectory` *without* syncing. Useful to verify state restoration: after a successful sync, kill the app, relaunch, tap this button — the directory should be restored from the per-`managerKey` preference store.
3. **Sync Manager** — constructs a manager and calls `manager.sync()`. On success, returns the new `latestAppDirectory` plus any `FederatedCapacitorSyncResult.metadata`. On failure, surfaces the SDK error message. This is the main end-to-end test.

The form fields populate the provider config map:

| Field        | Required | Notes                                                                       |
| ------------ | -------- | --------------------------------------------------------------------------- |
| `managerKey` | yes      | Scopes per-manager persisted state. Use different values to test isolation. |
| `appId`      | no       | Capawesome Cloud app UUID; falls back to `capacitor.config.ts`.             |
| `channel`    | no       | Channel name; falls back to plugin-wide `defaultChannel`.                   |

## Things worth checking

- **Registration log** — on Android, watch `adb logcat | grep LiveUpdate` for `Registered Ionic provider 'capawesome'.`. On iOS, check the Xcode console for the same message.
- **State isolation** — sync with `managerKey=shell-a`, then `managerKey=shell-b`. Both should persist independently. Calling `getLatestAppDirectory` for each should return distinct paths after the next launch.
- **Standalone coexistence** — provider mode never touches the standalone plugin's `currentBundleId` / `nextBundleId`. You can verify by calling the standalone JS API in parallel (this example doesn't expose it, but you can drop into the main `example/` app side by side).

## Notes

- The example uses `com.example.plugin` as the bundle id (same as the sibling `example/` app). Don't install both at the same time on the same device.
- The Capawesome Cloud app id in `capacitor.config.ts` is just a placeholder — replace it (or override per-manager via the `appId` form field) with one of your own.
