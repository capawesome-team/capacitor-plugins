# Ionic Live Update Provider SDK integration

This plugin can register itself as a provider for the [Ionic Live Update Provider SDK](https://github.com/ionic-team/live-update-provider-sdk), so that **Ionic Portals** and **Federated Capacitor** apps can deliver live updates through Capawesome Cloud.

The Ionic SDK is an **optional** native dependency — you only pay the install footprint when you opt in. The registered provider id is `capawesome`.

> **Real-world example:** [`ionic-portals-ecommerce-demo`](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) is a complete Ionic Portals app that delivers live updates to multiple portals via Capawesome Cloud. The iOS snippets in this guide are taken from it.

## Contents

- [Installation](#installation)
- [Provider configuration](#provider-configuration)
- [Federated Capacitor usage](#federated-capacitor-usage)
- [Ionic Portals usage (iOS)](#ionic-portals-usage-ios)
- [Verifying updates](#verifying-updates)

## Installation

### Android

Set the `capawesomeCapacitorLiveUpdateIncludeIonicProvider` variable to `true` in your app's `variables.gradle`:

```diff
ext {
+  capawesomeCapacitorLiveUpdateIncludeIonicProvider = true // Default: false
}
```

This pulls in `io.ionic:liveupdateprovider` and registers the `capawesome` provider with `LiveUpdateProviderRegistry` automatically when the plugin loads.

> The Ionic Live Update Provider SDK requires `minSdkVersion` **24** or higher. Bump `minSdkVersion` in your `variables.gradle` if your app targets a lower minimum.

### iOS

How you install on iOS depends on what kind of app hosts the web content:

- **A Capacitor app** — a regular Capacitor app, or a Federated Capacitor super-app shell. The iOS project was created with `npx cap add ios`, so the Capacitor CLI manages the `Podfile` and `capacitor_pods` already includes the base `CapawesomeCapacitorLiveUpdate` pod. You only opt into the Ionic provider.
- **A native iOS app** — an Ionic Portals host. It is *not* a Capacitor project: there is no `npx cap sync`, no `capacitor_pods`, and no `capacitor.config.json`. You wire up `IonicPortals` and reference the pods manually. **This is the setup used by the [demo app](https://github.com/capawesome-team/ionic-portals-ecommerce-demo).**

#### Capacitor app host (CocoaPods)

Add the `IonicProvider` subspec **outside** the `capacitor_pods` function (the Capacitor CLI regenerates the body of `capacitor_pods` on every `npx cap sync`, reverting any edit inside it):

```diff
target 'App' do
  capacitor_pods
  # Add your Pods here
+  pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => '../../node_modules/@capawesome/capacitor-live-update'
end
```

You only add the subspec because `capacitor_pods` already references the base `CapawesomeCapacitorLiveUpdate` pod (the Capacitor CLI added it when you installed the plugin). CocoaPods merges the two; the `IonicProvider` subspec additionally pulls in the `LiveUpdateProvider` pod and sets the `-DCAPAWESOME_INCLUDE_IONIC_PROVIDER` compile flag that includes the Ionic provider classes.

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

#### Native iOS host (CocoaPods)

A Portals host is a plain native iOS app, so the web app(s) live in their own directories and there is no Capacitor project — and therefore no `capacitor_pods` — next to the `Podfile`:

```
parent/
├── ios-app/        # native host — Podfile lives here, not a Capacitor project
├── android-app/
├── web-app-1/
└── web-app-2/
```

Two things follow from the app not being a Capacitor project:

1. There is no `capacitor_pods` to supply the base pod, so you reference **both** the base pod and the `IonicProvider` subspec yourself.
2. The plugin is **not published to CocoaPods trunk**, so a bare `pod '...'` (without `:path`) won't resolve — the pod needs a local source.

To provide that source, add a minimal `package.json` next to the host app and run `npm install`. Note this is **not** turning the app into a Capacitor project — `npm install` here only downloads the plugin's pod source into `node_modules` so CocoaPods has something to resolve `:path` against. There is no web build and no `npx cap sync`.

```json
{
  "name": "ios-app",
  "private": true,
  "dependencies": {
    "@capawesome/capacitor-live-update": "^8.2.1"
  }
}
```

```bash
npm install
```

```ruby
# Podfile
pod 'CapawesomeCapacitorLiveUpdate', :path => 'node_modules/@capawesome/capacitor-live-update'
pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => 'node_modules/@capawesome/capacitor-live-update'
```

> Prefer not to commit a `node_modules`/npm step? Vendor the plugin's `ios/` directory, `CapawesomeCapacitorLiveUpdate.podspec`, and `package.json` into your repo and point `:path` at that folder instead.

#### Native iOS host (Swift Package Manager)

A native host can use SPM instead of CocoaPods. Because the app is an `.xcodeproj` (not a Capacitor project), there is no Capacitor CLI to generate the SPM integration — so you create the same kind of **local wrapper package** the CLI would, and enable the `IonicProvider` trait in it. (This mirrors the `CapApp-SPM` package the Capacitor CLI generates for Capacitor apps.)

1. **Provide the plugin source locally** — same as the CocoaPods path above: either add a minimal `package.json` and `npm install` (→ `node_modules/@capawesome/capacitor-live-update`), or vendor the package into your repo. The package root contains the `Package.swift` that SPM consumes.

2. **Add a local wrapper package** next to your app (e.g. `ios/App/Portals-SPM/`) that depends on the plugin **with the `IonicProvider` trait enabled** and re-exports it:

   ```swift
   // ios/App/Portals-SPM/Package.swift
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
               // path is relative to this Package.swift; adjust to where node_modules lives
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

   The target needs at least one source file, e.g. `Sources/Portals-SPM/Portals-SPM.swift`:

   ```swift
   public let includesCapawesomeLiveUpdate = true
   ```

   `traits: ["IonicProvider"]` is what pulls in the `LiveUpdateProvider` dependency and sets the `-DCAPAWESOME_INCLUDE_IONIC_PROVIDER` flag — the SPM equivalent of the `packageTraits` entry a Capacitor app uses.

3. **Reference the wrapper from your app.** In Xcode: _File → Add Package Dependencies… → Add Local…_, select the `Portals-SPM` folder, and link the `Portals-SPM` product to your app target. Your app code can then `import CapawesomeCapacitorLiveUpdate` (it resolves transitively through the wrapper).

> **Why a wrapper?** A package trait is enabled in a `Package.swift` (`traits: [...]`). Routing the dependency through a local wrapper package — exactly how the Capacitor CLI wires SPM under the hood — enables the trait reliably regardless of whether your Xcode version exposes a trait toggle in the "Add Package" UI.
>
> If you adopt SPM for the host, pull `IonicPortals` (and other native dependencies) via SPM too rather than mixing CocoaPods and SPM. For many native hosts, the CocoaPods path above is simpler.

## Provider configuration

When Federated Capacitor or Portals creates a manager via this provider, it passes a configuration map. The following keys are accepted:

| Key          | Type   | Required | Description                                                                                    |
| ------------ | ------ | -------- | ---------------------------------------------------------------------------------------------- |
| `managerKey` | string | Yes      | Scopes per-manager persisted state. Use a stable, unique key per shell / portal.               |
| `appId`      | string | No       | Capawesome Cloud app UUID. Falls back to the plugin-wide `appId` from `capacitor.config.json`. |
| `channel`    | string | No       | Channel to sync. Falls back to the plugin-wide `defaultChannel` from `capacitor.config.json`.  |

All other settings (`serverDomain`, `publicKey`, `httpTimeout`, etc.) come from the plugin-wide Capacitor config. To set the per-device `customId`, call `LiveUpdate.setCustomId({ customId })` from JavaScript once at app startup.

## Federated Capacitor usage

Registration is automatic on plugin load. Reference the provider id in your Federated Capacitor configuration:

```ts
liveUpdateConfig: {
  providerId: 'capawesome',
  providerConfig: {
    managerKey: 'my-shell',
    appId: '6e351b4f-69a7-415e-a057-4567df7ffe94',
    channel: 'production',
  },
}
```

## Ionic Portals usage (iOS)

> The Live Update Provider SDK defines an Android Portals manager contract that this plugin implements, but the Ionic Portals **Android** SDK does not yet expose a documented API to attach it to a Portal (its Live Updates docs still target Appflow), and Ionic's reference app wires the provider on iOS only. The Portals integration below is therefore iOS-only. On Android the `capawesome` provider is still registered (see [Android](#android)) and works today with Federated Capacitor.

### How registration works (read this first)

The `capawesome` provider is registered with `LiveUpdateProviderRegistry` inside the plugin's `load()` lifecycle method — which runs **only when a Portal's Capacitor bridge loads**. Two consequences follow, and getting either wrong produces `LiveUpdateProviderError.providerNotRegistered("capawesome")`:

1. **Each Portal must include `LiveUpdatePlugin` in its `plugins`.** That is what registers the provider. A Portal without it will never trigger registration.
2. **The provider is not in the registry until at least one such Portal has loaded.** So you cannot resolve it while constructing your `Portal` definitions, and an eager `syncProvider()` at app launch — before any Portal has appeared — will fail. Wrap the provider in a *deferred adapter* (resolves lazily at sync time) and sync with a short retry, or sync once a Portal is on screen.

This also means a Portal must be able to load its web content. Bundle **seed content** at each Portal's `startDir` so the first Portal can render (and bootstrap registration) on a fresh, offline launch; Live Updates refresh it afterward. See the demo's [seed build phase](https://github.com/capawesome-team/ionic-portals-ecommerce-demo) for one way to do this.

### 1. Deferred adapter

```swift
import CapawesomeCapacitorLiveUpdate
import LiveUpdateProvider

final class DeferredCapawesomeLiveUpdateManager: LiveUpdateManaging {
    private let config: [String: Any]
    private var currentManager: (any LiveUpdateManaging)?

    var latestAppDirectory: URL? { currentManager?.latestAppDirectory }

    init(config: [String: Any]) {
        self.config = config
    }

    func sync() async throws -> any LiveUpdateProvider.SyncResult {
        guard let provider = LiveUpdateProviderRegistry.shared.resolve("capawesome") else {
            throw LiveUpdateProviderError.providerNotRegistered("capawesome")
        }
        let manager = try provider.createManager(config: config)
        currentManager = manager
        return try await manager.sync()
    }
}
```

### 2. Define your Portals

Include `LiveUpdatePlugin` in `plugins` (required — see above), attach the adapter via `liveUpdateProvider`, and give each Portal a distinct `managerKey` and the Capawesome Cloud `appId` that hosts its bundle:

```swift
import IonicPortals
import CapawesomeCapacitorLiveUpdate

extension Portal {
    private static let webAppId = "0ca581ce-f6cc-4e2c-a5f8-47a8169371c4"
    private static let featuredAppId = "791104da-928d-43ac-8c06-5c01462e460e"
    private static let channel = "production"

    static let checkout = Self(
        name: "checkout",
        startDir: "portals/shopwebapp",
        plugins: [.type(LiveUpdatePlugin.self)],
        liveUpdateProvider: .provider(
            liveUpdateManager: DeferredCapawesomeLiveUpdateManager(config: [
                "managerKey": "portal-checkout",
                "appId": webAppId,
                "channel": channel
            ])
        )
    )

    static let featured = Self(
        name: "featured",
        startDir: "portals/featured",
        plugins: [.type(LiveUpdatePlugin.self)],
        liveUpdateProvider: .provider(
            liveUpdateManager: DeferredCapawesomeLiveUpdateManager(config: [
                "managerKey": "portal-featured",
                "appId": featuredAppId,
                "channel": channel
            ])
        )
    )
}
```

> `liveUpdateProvider` and `Portal.syncProvider()` require a Portals version that ships the Live Update Provider integration. The reference app pins `pod 'IonicPortals', '0.14.0-rc.0'`; check the [Portals documentation](https://ionic.io/docs/portals) for the minimum version.

### 3. Sync

`syncProvider()` downloads the latest bundle, updates the manager's `latestAppDirectory`, and the Portal serves the new content on its next load. Because the provider may not be registered the instant the app launches, sync with a short retry until it succeeds (or sync lazily once a Portal is shown):

```swift
func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
) -> Bool {
    syncProviderPortals(attempt: 1)
    return true
}

private func syncProviderPortals(attempt: Int, maxAttempts: Int = 20) {
    DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
        Task {
            var pending: [Portal] = []
            for portal in [Portal.checkout, .featured] {
                do {
                    _ = try await portal.syncProvider()
                } catch {
                    // Provider not registered yet (no Portal loaded) — retry.
                    pending.append(portal)
                }
            }
            if !pending.isEmpty, attempt < maxAttempts {
                self.syncProviderPortals(attempt: attempt + 1, maxAttempts: maxAttempts)
            }
        }
    }
}
```

## Verifying updates

- **Publish a bundle** to the matching Capawesome Cloud `appId` and `channel` with the [`@capawesome/cli`](https://capawesome.io/cloud/live-updates/). Each Portal syncs the app whose `appId` it was configured with.
- On `syncProvider()`, the manager downloads the latest bundle, sets `latestAppDirectory` to the new on-disk location, and persists it per `managerKey` so the Portal restores the correct bundle on the next launch.
- The sync result is a `FederatedCapacitorSyncResult` whose `metadata` carries `bundleId`, `channel`, and any `customProperties` attached to the bundle in Capawesome Cloud.

> Provider mode and the standalone JavaScript sync flow share the same on-disk bundle storage but use isolated, `managerKey`-scoped state pointers. Pick one mode per app to avoid surprises.
