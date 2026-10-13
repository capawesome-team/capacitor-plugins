# Ionic Live Update Provider SDK integration

This plugin implements the [Ionic Live Update Provider SDK](https://github.com/ionic-team/live-update-provider-sdk) (`1.0.0`) contract, so **Ionic Portals** and **Federated Capacitor** apps can deliver live updates through Capawesome Cloud.

There is no registry and no separate provider id: Federated Capacitor resolves the provider by its **Capacitor plugin name** — `LiveUpdate` on both platforms — and calls `createManager(...)` natively on the resolved plugin instance.

The SDK integration is **optional** on both platforms, in different ways:

- **Android** — zero configuration. The SDK is a compile-time-only dependency of the plugin; Federated Capacitor and Ionic Portals bring it at runtime. The plugin deliberately does not implement the SDK's `LiveUpdateProvider` interface — instead it exposes a `createManager(Context, Map)` method that Federated Capacitor resolves reflectively and invokes. Apps that don't use Portals/FedCap still ship the manager class and the Kotlin standard library, but not the SDK itself.
- **iOS** — opt-in at build time via the `IonicProvider` CocoaPods subspec or SPM package trait (see below), which links the SDK and compiles in the provider classes.

> **Real-world example:** [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) is a complete Ionic Portals app that delivers live updates to multiple portals via Capawesome Cloud.

## Contents

- [Installation](#installation)
- [Provider configuration](#provider-configuration)
- [Federated Capacitor usage](#federated-capacitor-usage)
- [Ionic Portals usage](#ionic-portals-usage)
- [Bundle storage](#bundle-storage)
- [Verifying updates](#verifying-updates)

## Installation

### Android

Nothing to do. The plugin compiles against `io.ionic:liveupdateprovider` (override the version via the `ionicLiveUpdateProviderVersion` project variable, default: `1.0.0`), and Federated Capacitor / Ionic Portals provide the SDK at runtime.

> The Ionic Live Update Provider SDK requires `minSdkVersion` **24** or higher.

#### Native Android host (Ionic Portals)

A native Portals host isn't a Capacitor project, so there's no `npx cap sync` to generate the plugin's Gradle module include — wire it from a local `node_modules` instead. The plugin's module depends on the local `:capacitor-android` module, while Portals pulls Capacitor in via Maven, so you reconcile the two to a **single** Capacitor core.

1. **Provide the module sources.** Add a `package.json` next to your Gradle project and `npm install`. This only fetches the Gradle module sources into `node_modules`; it does **not** make the app a Capacitor project (no web build, no `npx cap sync`):

   ```json
   {
     "name": "android-app",
     "private": true,
     "dependencies": {
       "@capacitor/android": "8.2.0",
       "@capawesome/capacitor-live-update": "^8.7.0"
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

   Portals brings `io.ionic:liveupdateprovider` transitively — use a Portals version that is built against Live Update Provider SDK `1.0.0`.

4. **Apply `variables.gradle`** from your root `build.gradle` (Capacitor's module reads `minSdkVersion` etc. from root `ext` properties):

   ```groovy
   // build.gradle (root)
   apply from: "variables.gradle"
   ```

### iOS

How you install on iOS depends on what kind of app hosts the web content:

- **A Capacitor app** — a regular Capacitor app, or a Federated Capacitor super-app shell. The Capacitor CLI manages the `Podfile`, and `capacitor_pods` already includes the base `CapawesomeCapacitorLiveUpdate` pod. You only opt into the Ionic provider.
- **A native iOS app** — an Ionic Portals host, not a Capacitor project. You wire up `IonicPortals` and reference the pods manually.

#### Capacitor app host (CocoaPods)

Add the `IonicProvider` subspec **outside** the `capacitor_pods` function (the Capacitor CLI regenerates the body of `capacitor_pods` on every `npx cap sync`, reverting any edit inside it):

```diff
target 'App' do
  capacitor_pods
  # Add your Pods here
+  pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => '../../node_modules/@capawesome/capacitor-live-update'
end
```

The `IonicProvider` subspec additionally pulls in the `LiveUpdateProvider` pod and sets the `-DCAPAWESOME_INCLUDE_IONIC_PROVIDER` compile flag that includes the Ionic provider classes.

#### Capacitor app host (Swift Package Manager)

Enable the `IonicProvider` package trait in `capacitor.config.json` (or `capacitor.config.ts`):

```json
{
  "experimental": {
    "ios": {
      "spm": {
        "swiftToolsVersion": "6.1",
        "packageTraits": {
          "@capawesome/capacitor-live-update": ["IonicProvider"]
        }
      }
    }
  }
}
```

> SPM traits live in `capacitor.config.json`, so this path applies to Capacitor app hosts only. Requires Capacitor CLI 8.3.0+ and Xcode 16.3+ (Swift 6.1+).

#### Native iOS host (Ionic Portals, CocoaPods)

A Portals host is a plain native iOS app, so there is no Capacitor project — and therefore no `capacitor_pods` — next to the `Podfile`. Two things follow:

1. There is no `capacitor_pods` to supply the base pod, so you reference **both** the base pod and the `IonicProvider` subspec yourself.
2. The plugin is **not published to CocoaPods trunk**, so a bare `pod '...'` (without `:path`) won't resolve — the pod needs a local source.

To provide that source, add a minimal `package.json` next to the host app and run `npm install`. Note this is **not** turning the app into a Capacitor project — `npm install` here only downloads the plugin's pod source into `node_modules` so CocoaPods has something to resolve `:path` against:

```json
{
  "name": "ios-app",
  "private": true,
  "dependencies": {
    "@capawesome/capacitor-live-update": "^8.7.0"
  }
}
```

```ruby
# Podfile
pod 'CapawesomeCapacitorLiveUpdate', :path => 'node_modules/@capawesome/capacitor-live-update'
pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => 'node_modules/@capawesome/capacitor-live-update'
```

> Prefer not to commit a `node_modules`/npm step? Vendor the plugin's `ios/` directory, `CapawesomeCapacitorLiveUpdate.podspec`, and `package.json` into your repo and point `:path` at that folder instead.

Xcode 27 refuses to build pods whose deployment target is below iOS 15, and Ionic Portals pulls in three of them (`ZIPFoundation`, `Alamofire` and `IonicLiveUpdates`). A Capacitor app gets the fix from the `post_install` hook the Capacitor CLI generates. A native host has no such hook, so add one to the `Podfile`:

```ruby
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      if config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'].to_f < 15.0
        config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '15.0'
      end
    end
  end
end
```

#### Native iOS host (Ionic Portals, Swift Package Manager)

Add the package root (`node_modules/@capawesome/capacitor-live-update`) as a **local package dependency with the `IonicProvider` trait enabled**. A package trait is enabled in a `Package.swift` (`traits: [...]`), so route the dependency through a small local wrapper package — exactly how the Capacitor CLI wires SPM under the hood:

```swift
// swift-tools-version: 6.1
// Package.swift of a local wrapper package next to your app
import PackageDescription

let package = Package(
    name: "Portals-SPM",
    platforms: [.iOS(.v15)],
    products: [
        .library(name: "Portals-SPM", targets: ["Portals-SPM"])
    ],
    dependencies: [
        .package(
            name: "CapawesomeCapacitorLiveUpdate",
            path: "../../node_modules/@capawesome/capacitor-live-update",
            traits: ["IonicProvider"]
        )
    ],
    targets: [
        .target(
            name: "Portals-SPM",
            dependencies: [
                .product(name: "CapawesomeCapacitorLiveUpdate", package: "CapawesomeCapacitorLiveUpdate")
            ]
        )
    ]
)
```

Reference the wrapper from your app (_File → Add Package Dependencies… → Add Local…_) and link its product to your app target.

With Swift Package Manager the Swift module is named after the target, so your code imports `LiveUpdatePlugin` instead of `CapawesomeCapacitorLiveUpdate`. The product name only appears in `Package.swift`.

## Provider configuration

When Federated Capacitor or Portals creates a manager via `createManager(...)`, it passes a configuration map. The following keys are accepted:

| Key            | Type   | Description                                                                                                                                                                                                                                                                                                                        |
| -------------- | ------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `appId`        | string | Capawesome Cloud app UUID. Required in a native Portals host. Under Federated Capacitor, falls back to the plugin's `appId` (a value set via `setConfig()` wins over `capacitor.config.json`). If neither is set, the manager construction fails with `appId must be configured.`.                                                 |
| `channel`      | string | Channel to sync. Falls back to the plugin's channel: a value set via `setChannel()` wins over the native channel configuration (`CapawesomeLiveUpdateDefaultChannel` in `Info.plist` on iOS, `capawesome_live_update_default_channel` in `strings.xml` on Android), which wins over `defaultChannel` from `capacitor.config.json`. |
| `publicKey`    | string | Public key to verify the integrity of the bundle, as a single-line PEM string. Enables code signing verification for this manager. Native Portals host only.                                                                                                                                                                       |
| `serverDomain` | string | API domain of the Capawesome Cloud server **without** scheme or path. Native Portals host only.                                                                                                                                                                                                                                    |
| `httpTimeout`  | number | Timeout in milliseconds for HTTP requests. Native Portals host only.                                                                                                                                                                                                                                                               |

`publicKey`, `serverDomain` and `httpTimeout` only apply when your native code constructs the manager directly (Ionic Portals). If one of them is present with the wrong type, the manager construction fails with `<key> has an invalid type.`. Under Federated Capacitor, `createManager(...)` reuses the running plugin instance, so the plugin's Capacitor config applies and these three keys are ignored.

There is no configuration key for the per-device `customId`. Under Federated Capacitor, call `LiveUpdate.setCustomId({ customId })` from JavaScript once at app startup. A native Portals host has no JavaScript API, so `customId` is not available there.

### Code signing

We recommend signing your bundles in production. A native Portals host passes the public key via the `publicKey` key of the manager configuration. Under Federated Capacitor, set `publicKey` in the plugin's Capacitor config instead. See [Code Signing](https://capawesome.io/docs/cloud/live-updates/code-signing/) for how to generate a key pair and upload signed bundles. Once a channel only receives signed bundles, you can mark it as [protected](https://capawesome.io/docs/cloud/live-updates/channels/#protected-channels) in Capawesome Cloud so that unsigned bundles cannot be distributed through it.

## Federated Capacitor usage

Select the provider by this plugin's Capacitor plugin name (**`LiveUpdate`**) and pass the provider configuration for each app:

```ts
liveUpdateConfig: {
  pluginName: 'LiveUpdate',
  config: {
    appId: '6e351b4f-69a7-415e-a057-4567df7ffe94',
    channel: 'production',
  },
  autoUpdateMethod: 'none',
}
```

> Requires a `@ionic-enterprise/federated-capacitor` version built against Live Update Provider SDK `1.0.0`. See the [Federated Capacitor documentation](https://ionic.io/docs/portals/for-capacitor/live-updates) for details.

## Ionic Portals usage

Ionic Portals uses a `ProviderManager` directly — there is no resolution step. You construct the manager in your native app and attach it to the Portal's configuration; Portals reads `latestAppDirectory` to locate the web assets, and you trigger `syncProvider()` to refresh them.

> Requires Portals `0.14.0` or later (earlier releases consumed the pre-1.0 registry-based contract).

### iOS

With CocoaPods the module is `CapawesomeCapacitorLiveUpdate`. With Swift Package Manager import `LiveUpdatePlugin` instead.

```swift
import CapawesomeCapacitorLiveUpdate
import IonicPortals

let manager = try LiveUpdateIonicManager(configuration: [
    "appId": "6e351b4f-69a7-415e-a057-4567df7ffe94",
    "channel": "production"
])

let checkout = Portal(
    name: "checkout",
    startDir: "portals/shopwebapp",
    liveUpdateSource: .provider(manager: manager)
)

// Trigger a sync (e.g. at app launch or on resume):
let result = try await checkout.syncProvider()
```

### Android

```kotlin
import io.capawesome.capacitorjs.plugins.liveupdate.providers.ionic.LiveUpdateIonicManager
import io.ionic.portals.PortalManager

val manager = LiveUpdateIonicManager(
    context,
    mapOf(
        "appId" to "6e351b4f-69a7-415e-a057-4567df7ffe94",
        "channel" to "production",
    ),
)

PortalManager.newPortal("checkout")
    .setStartDir("webapp")
    .setLiveUpdateProviderManager(manager)
    .create()

// Trigger a sync: `portal.syncProvider()` from a coroutine,
// or `portal.syncProviderAsync()` (CompletableFuture) from Java.
```

The directly constructed manager runs headless: there is no Capacitor config, so pass `appId` (required), `channel` and, if needed, `publicKey`, `serverDomain` and `httpTimeout` via the configuration map. Because there is no registration step, no retry logic is needed: construct and sync right away.

Bundle **seed content** at each Portal's `startDir` so the first Portal can render on a fresh, offline launch; Live Updates refresh it afterward.

## Bundle storage

Provider managers share the plugin's on-disk bundle storage, but keep their own state:

- The state of a manager is scoped by its resolved app ID and channel (`<appId>` or `<appId>.<channel>`). Managers with the same app ID and channel share their state by design, so Portals that show the same web app share one bundle.
- After a native app update (the version code on Android or `CFBundleVersion` on iOS changes), each manager forgets its synced bundle and serves the seed content until the next sync.
- Bundles that were replaced by a sync, or dropped after an app update, are deleted the next time a manager is constructed (usually at app launch), not right after a sync, because a visible portal may still serve them. A bundle is kept as long as any manager points to it or it is the plugin's current or next bundle. Managers never delete bundles they did not sync themselves.
- Leftovers of interrupted downloads are removed once per process at startup. Concurrent syncs or downloads of the same bundle do not fail; the bundle counts as installed.

## Verifying updates

- **Publish a bundle** to the matching Capawesome Cloud `appId` and `channel` with the [`@capawesome/cli`](https://capawesome.io/cloud/live-updates/). Each shell/portal syncs the app whose `appId` it was configured with.
- On sync, the manager downloads the latest bundle, sets `latestAppDirectory` to the new on-disk location, and persists it per app ID and channel so the same bundle is restored on the next launch.
- When an update was applied, `sync()` returns a `MetadataSyncResult` whose `metadata` carries `bundleId`, `channel`, and any `customProperties` attached to the bundle in Capawesome Cloud. Federated Capacitor forwards this metadata to the web layer.
- `sync()` returns `nil`/`null` when there is nothing to report: the server answered with an error status (for example, no bundle matches the request) or the bundle is unchanged.
- `sync()` throws on network failures (offline, DNS, TLS, timeout), on an invalid response body, and when the download or the verification of the bundle fails. The behavior is the same on Android and iOS.

> Provider mode and the standalone JavaScript sync flow share the same on-disk bundle storage but use separate state: the provider state is scoped by app ID and channel, the JavaScript flow uses the plugin's `currentBundleId` / `nextBundleId`. Pick one mode per app to avoid surprises. In particular, do not enable `autoDeleteBundles` in a Federated Capacitor shell that calls `ready()`: the plugin then deletes every bundle that is not its current or next bundle, including the bundles synced by provider managers.
