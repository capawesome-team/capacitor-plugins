# Ionic Live Update Provider SDK integration

This plugin implements the [Ionic Live Update Provider SDK](https://github.com/ionic-team/live-update-provider-sdk) (`1.0.0`) contract, so **Ionic Portals** and **Federated Capacitor** apps can deliver live updates through Capawesome Cloud.

There is no registry and no separate provider id: Federated Capacitor resolves the provider by its **Capacitor plugin name** — `LiveUpdate` on both platforms — and calls `createManager(...)` natively on the resolved plugin instance.

The SDK integration is **optional** on both platforms, in different ways:

- **Android** — zero configuration. The SDK is a compile-time-only dependency of the plugin; Federated Capacitor and Ionic Portals bring it at runtime. The plugin deliberately does not implement the SDK's `LiveUpdateProvider` interface — instead it exposes `createManager` with the exact interface signature, which Federated Capacitor invokes via its reflection fallback. Apps that don't use Portals/FedCap ship zero extra bytes.
- **iOS** — opt-in at build time via the `IonicProvider` CocoaPods subspec or SPM package trait (see below), which links the SDK and compiles in the provider classes.

> **Real-world example:** [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) is a complete Ionic Portals app that delivers live updates to multiple portals via Capawesome Cloud.

## Contents

- [Installation](#installation)
- [Provider configuration](#provider-configuration)
- [Federated Capacitor usage](#federated-capacitor-usage)
- [Ionic Portals usage](#ionic-portals-usage)
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
    "@capawesome/capacitor-live-update": "^8.3.0"
  }
}
```

```ruby
# Podfile
pod 'CapawesomeCapacitorLiveUpdate', :path => 'node_modules/@capawesome/capacitor-live-update'
pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => 'node_modules/@capawesome/capacitor-live-update'
```

> Prefer not to commit a `node_modules`/npm step? Vendor the plugin's `ios/` directory, `CapawesomeCapacitorLiveUpdate.podspec`, and `package.json` into your repo and point `:path` at that folder instead.

#### Native iOS host (Ionic Portals, Swift Package Manager)

Add the package root (`node_modules/@capawesome/capacitor-live-update`) as a **local package dependency with the `IonicProvider` trait enabled**. A package trait is enabled in a `Package.swift` (`traits: [...]`), so route the dependency through a small local wrapper package — exactly how the Capacitor CLI wires SPM under the hood:

```swift
// Package.swift of a local wrapper package next to your app
// swift-tools-version: 6.1
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

## Provider configuration

When Federated Capacitor or Portals creates a manager via `createManager(...)`, it passes a configuration map. The following keys are accepted:

| Key          | Type   | Required | Description                                                                                    |
| ------------ | ------ | -------- | ---------------------------------------------------------------------------------------------- |
| `managerKey` | string | Yes      | Scopes per-manager persisted state. Use a stable, unique key per shell / portal.               |
| `appId`      | string | No       | Capawesome Cloud app UUID. Falls back to the plugin-wide `appId` from `capacitor.config.json`. |
| `channel`    | string | No       | Channel to sync. Falls back to the plugin-wide `defaultChannel` from `capacitor.config.json`.  |

All other settings (`serverDomain`, `publicKey`, `httpTimeout`, etc.) come from the plugin-wide Capacitor config. To set the per-device `customId`, call `LiveUpdate.setCustomId({ customId })` from JavaScript once at app startup.

## Federated Capacitor usage

Select the provider by this plugin's Capacitor plugin name (**`LiveUpdate`**) and pass the provider configuration for each app:

```ts
liveUpdateConfig: {
  pluginName: 'LiveUpdate',
  config: {
    managerKey: 'my-shell',
    appId: '6e351b4f-69a7-415e-a057-4567df7ffe94',
    channel: 'production',
  },
  autoUpdateMethod: 'none',
}
```

> Requires a `@ionic-enterprise/federated-capacitor` version built against Live Update Provider SDK `1.0.0`. See the [Federated Capacitor documentation](https://ionic.io/docs/portals/for-capacitor/live-updates) for details.

## Ionic Portals usage

Ionic Portals uses a `ProviderManager` directly — there is no resolution step. You construct the manager in your native app and attach it to the Portal's configuration; Portals reads `latestAppDirectory` to locate the web assets and calls `sync()` to refresh them.

> Requires a Portals release built against Live Update Provider SDK `1.0.0` (earlier releases consumed the pre-1.0 registry-based contract). Direct manager construction support in this plugin is tracked as a follow-up; this guide and the [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) will be updated with concrete snippets once available.

Bundle **seed content** at each Portal's `startDir` so the first Portal can render on a fresh, offline launch; Live Updates refresh it afterward.

## Verifying updates

- **Publish a bundle** to the matching Capawesome Cloud `appId` and `channel` with the [`@capawesome/cli`](https://capawesome.io/cloud/live-updates/). Each shell/portal syncs the app whose `appId` it was configured with.
- On sync, the manager downloads the latest bundle, sets `latestAppDirectory` to the new on-disk location, and persists it per `managerKey` so the correct bundle is restored on the next launch.
- When an update was applied, `sync()` returns a `MetadataSyncResult` whose `metadata` carries `bundleId`, `channel`, and any `customProperties` attached to the bundle in Capawesome Cloud. Federated Capacitor forwards this metadata to the web layer. When no update is available, `sync()` returns `nil`/`null` (nothing to report). Failures are thrown/propagated as errors.

> Provider mode and the standalone JavaScript sync flow share the same on-disk bundle storage but use isolated, `managerKey`-scoped state pointers. Pick one mode per app to avoid surprises.
