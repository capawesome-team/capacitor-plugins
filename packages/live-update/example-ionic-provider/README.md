# example-ionic-provider

Isolated test harness for the **Ionic Live Update Provider SDK** integration in
`@capawesome/capacitor-live-update`. The plugin class implements the SDK's
`LiveUpdateProvider` contract, so Federated Capacitor resolves it by its
Capacitor plugin name (`LiveUpdate`) and Ionic Portals shells can use
Capawesome Cloud as a live update provider.

> **This is not an Ionic Portals app.** It is a plain Capacitor app that ships a
> tiny custom Capacitor plugin (`IonicProviderTest`) to exercise the provider
> SDK end-to-end **in isolation** — without needing a Portals or Federated
> Capacitor host. For a full, real Ionic Portals app that delivers live updates
> to multiple portals via Capawesome Cloud, see
> [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo).

## How it's wired up

- **Android** — the plugin ships `io.ionic:liveupdateprovider` as a regular
  dependency, so no opt-in is needed. The custom `IonicProviderTestPlugin.kt`
  lives in `android/app/src/main/java/com/example/plugin/` and is registered in
  `MainActivity.java`. It resolves the provider the same way Federated
  Capacitor does: `bridge.getPlugin("LiveUpdate")` plus a cast to
  `LiveUpdateProvider`.
- **iOS** — the plugin's pod depends on `LiveUpdateProvider`, so no opt-in is
  needed. The custom `IonicProviderTestPlugin.swift` lives in `ios/App/App/`
  and is auto-discovered by the Capacitor bridge via the `CAPBridgedPlugin`
  protocol. It resolves the provider via `bridge?.plugin(withName: "LiveUpdate")`
  plus a cast to `LiveUpdateProvider`.

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

1. **Is Provider Available?** — resolves the `LiveUpdate` plugin from the Capacitor bridge and checks that it implements/conforms to `LiveUpdateProvider`. Should return `{ available: true }` once the app finishes loading.
2. **Get Latest App Directory** — constructs a manager via `createManager(...)` on the plugin using the values from the form, then returns its `latestAppDirectory` *without* syncing. Useful to verify state restoration: after a successful sync, kill the app, relaunch, tap this button — the directory should be restored from the per-`managerKey` preference store.
3. **Sync Manager** — constructs a manager and calls `manager.sync()`. On success, returns the new `latestAppDirectory` plus any `MetadataSyncResult.metadata` (empty when the sync had nothing to report — `sync()` returns `nil`/`null` when no update is available). On failure, surfaces the error message. This is the main end-to-end test.

The form fields populate the provider configuration map:

| Field        | Required | Notes                                                                       |
| ------------ | -------- | --------------------------------------------------------------------------- |
| `managerKey` | yes      | Scopes per-manager persisted state. Use different values to test isolation. |
| `appId`      | no       | Capawesome Cloud app UUID; falls back to `capacitor.config.ts`.             |
| `channel`    | no       | Channel name; falls back to plugin-wide `defaultChannel`.                   |

## Things worth checking

- **State isolation** — sync with `managerKey=shell-a`, then `managerKey=shell-b`. Both should persist independently. Calling `getLatestAppDirectory` for each should return distinct paths after the next launch.
- **Standalone coexistence** — provider mode never touches the standalone plugin's `currentBundleId` / `nextBundleId`. You can verify by calling the standalone JS API in parallel (this example doesn't expose it, but you can drop into the main `example/` app side by side).

## Notes

- The example uses `com.example.plugin` as the bundle id (same as the sibling `example/` app). Don't install both at the same time on the same device.
- The Capawesome Cloud app id in `capacitor.config.ts` is just a placeholder — replace it (or override per-manager via the `appId` form field) with one of your own.
