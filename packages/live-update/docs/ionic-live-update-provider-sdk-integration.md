# Ionic Live Update Provider SDK integration

This plugin implements the [Ionic Live Update Provider SDK](https://github.com/ionic-team/live-update-provider-sdk) (`1.0.0`) contract, so **Ionic Portals** and **Federated Capacitor** apps can deliver live updates through Capawesome Cloud.

There is no registry and no separate provider id: the plugin class itself implements the SDK's `LiveUpdateProvider` contract, and Federated Capacitor resolves it by its **Capacitor plugin name** — `LiveUpdate` on both platforms. The SDK is a regular native dependency of this plugin, so no opt-in is required.

> **Real-world example:** [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) is a complete Ionic Portals app that delivers live updates to multiple portals via Capawesome Cloud.

## Contents

- [Installation](#installation)
- [Provider configuration](#provider-configuration)
- [Federated Capacitor usage](#federated-capacitor-usage)
- [Ionic Portals usage](#ionic-portals-usage)
- [Verifying updates](#verifying-updates)

## Installation

### Capacitor app host

Nothing extra to do — installing the plugin is enough. The SDK ships as a regular dependency on both platforms:

- **Android**: `io.ionic:liveupdateprovider` (override the version via the `ionicLiveUpdateProviderVersion` project variable, default: `1.0.0`).
- **iOS**: the `LiveUpdateProvider` pod (CocoaPods) or the `live-update-provider-sdk` package (SPM).

> The Ionic Live Update Provider SDK requires `minSdkVersion` **24** (Android) and iOS **15** or higher.

### Native Android host (Ionic Portals)

A native Portals host isn't a Capacitor project, so there's no `npx cap sync` to generate the plugin's Gradle module include — wire it from a local `node_modules` instead. The plugin's module depends on the local `:capacitor-android` module, while Portals pulls Capacitor in via Maven, so you reconcile the two to a **single** Capacitor core.

1. **Provide the module sources.** Add a `package.json` next to your Gradle project and `npm install`. This only fetches the Gradle module sources into `node_modules`; it does **not** make the app a Capacitor project (no web build, no `npx cap sync`):

   ```json
   {
     "name": "android-app",
     "private": true,
     "dependencies": {
       "@capacitor/android": "8.2.0",
       "@capawesome/capacitor-live-update": "^8.3.0"
     }
   }
   ```

2. **Include the modules** in `settings.gradle`:

   ```groovy
   include ':capacitor-android'
   project(':capacitor-android').projectDir = new File('node_modules/@capacitor/android/capacitor')

   include ':capawesome-capacitor-live-update'
   project(':capawesome-capacitor-live-update').projectDir = new File('node_modules/@capawesome/capacitor-live-update/android')
   ```

3. **Wire the dependencies** in your app's `build.gradle`. Because the plugin module requires the local `:capacitor-android`, exclude the transitive Maven `com.capacitorjs:core` from Portals (and from any other Maven-published Capacitor plugin you use, e.g. Camera) so there is only one Capacitor core on the classpath:

   ```groovy
   dependencies {
       implementation('io.ionic:portals:<version>') {
           exclude group: 'com.capacitorjs', module: 'core'
       }
       implementation project(':capacitor-android')
       implementation project(':capawesome-capacitor-live-update')
   }
   ```

   > Use a Portals version that is built against Live Update Provider SDK `1.0.0`. Both Portals and this plugin depend on `io.ionic:liveupdateprovider`; Gradle resolves them to a single version, so mixing a pre-1.0 Portals release with this plugin will fail at runtime.

4. **Apply `variables.gradle`** from your root `build.gradle` (Capacitor's module reads `minSdkVersion` etc. from root `ext` properties):

   ```groovy
   // build.gradle (root)
   apply from: "variables.gradle"
   ```

### Native iOS host (Ionic Portals)

A Portals host is a plain native iOS app, so there is no Capacitor project — and therefore no `capacitor_pods` — next to the `Podfile`. Two things follow:

1. There is no `capacitor_pods` to supply the pod, so you reference it yourself.
2. The plugin is **not published to CocoaPods trunk**, so a bare `pod '...'` (without `:path`) won't resolve — the pod needs a local source.

To provide that source, add a minimal `package.json` next to the host app and run `npm install`. Note this is **not** turning the app into a Capacitor project — `npm install` here only downloads the plugin's pod source into `node_modules` so CocoaPods has something to resolve `:path` against:

```json
{
  "name": "ios-app",
  "private": true,
  "dependencies": {
    "@capawesome/capacitor-live-update": "^8.3.0"
  }
}
```

```ruby
# Podfile
pod 'CapawesomeCapacitorLiveUpdate', :path => 'node_modules/@capawesome/capacitor-live-update'
```

If the host uses SPM instead of CocoaPods, add the package root (`node_modules/@capawesome/capacitor-live-update`) as a local package dependency — no wrapper package or package trait is needed anymore.

> Prefer not to commit a `node_modules`/npm step? Vendor the plugin's `ios/` directory, `CapawesomeCapacitorLiveUpdate.podspec`, `Package.swift`, and `package.json` into your repo and point the local reference at that folder instead.

## Provider configuration

When Federated Capacitor or Portals creates a manager via `createManager(...)`, it passes a configuration map. The following keys are accepted:

| Key          | Type   | Required | Description                                                                                    |
| ------------ | ------ | -------- | ---------------------------------------------------------------------------------------------- |
| `managerKey` | string | Yes      | Scopes per-manager persisted state. Use a stable, unique key per shell / portal.               |
| `appId`      | string | No       | Capawesome Cloud app UUID. Falls back to the plugin-wide `appId` from `capacitor.config.json`. |
| `channel`    | string | No       | Channel to sync. Falls back to the plugin-wide `defaultChannel` from `capacitor.config.json`.  |

All other settings (`serverDomain`, `publicKey`, `httpTimeout`, etc.) come from the plugin-wide Capacitor config. To set the per-device `customId`, call `LiveUpdate.setCustomId({ customId })` from JavaScript once at app startup.

## Federated Capacitor usage

Federated Capacitor resolves providers by their Capacitor plugin name (`jsName` on iOS, `@CapacitorPlugin(name = ...)` on Android). For this plugin, that name is **`LiveUpdate`** on both platforms. Reference it in your Federated Capacitor configuration together with the provider configuration for each app:

```ts
liveUpdateConfig: {
  provider: 'LiveUpdate',
  providerConfig: {
    managerKey: 'my-shell',
    appId: '6e351b4f-69a7-415e-a057-4567df7ffe94',
    channel: 'production',
  },
}
```

> Requires a `@ionic-enterprise/federated-capacitor` version built against Live Update Provider SDK `1.0.0`. Check the [Federated Capacitor documentation](https://ionic.io/docs/portals/for-capacitor/live-updates) for the exact configuration key names supported by your version.

## Ionic Portals usage

Ionic Portals uses a `ProviderManager` directly — no provider resolution is involved. You construct the manager and attach it to the Portal's configuration; Portals reads `latestAppDirectory` to locate the web assets and calls `sync()` to refresh them.

> Requires a Portals release built against Live Update Provider SDK `1.0.0`. Earlier releases (up to `0.14.0-rc.0`) consumed the pre-1.0 registry-based contract, which this plugin no longer implements. This guide and the [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) will be updated with concrete snippets once such a release is available.

Bundle **seed content** at each Portal's `startDir` so the first Portal can render on a fresh, offline launch; Live Updates refresh it afterward.

## Verifying updates

- **Publish a bundle** to the matching Capawesome Cloud `appId` and `channel` with the [`@capawesome/cli`](https://capawesome.io/cloud/live-updates/). Each shell/portal syncs the app whose `appId` it was configured with.
- On sync, the manager downloads the latest bundle, sets `latestAppDirectory` to the new on-disk location, and persists it per `managerKey` so the correct bundle is restored on the next launch.
- When an update was applied, `sync()` returns a `MetadataSyncResult` whose `metadata` carries `bundleId`, `channel`, and any `customProperties` attached to the bundle in Capawesome Cloud. Federated Capacitor forwards this metadata to the web layer. When no update is available, `sync()` returns `nil`/`null` (nothing to report). Failures are thrown/propagated as errors.

> Provider mode and the standalone JavaScript sync flow share the same on-disk bundle storage but use isolated, `managerKey`-scoped state pointers. Pick one mode per app to avoid surprises.
