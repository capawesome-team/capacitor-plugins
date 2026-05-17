# Ionic Live Update Provider SDK integration

This plugin can register itself as a provider for the [Ionic Live Update Provider SDK](https://github.com/ionic-team/live-update-provider-sdk), so that **Ionic Portals** and **Federated Capacitor** apps can use Capawesome Cloud to deliver live updates.

The Ionic SDK is an **optional native dependency**: you only pay the install footprint when you opt in. The plugin's registered provider id is `capawesome`.

## Android

Set the `capawesomeCapacitorLiveUpdateIncludeIonicProvider` variable to `true` in your app's `variables.gradle` file:

```diff
ext {
+  capawesomeCapacitorLiveUpdateIncludeIonicProvider = true // Default: false
}
```

When enabled, the plugin pulls in `io.ionic:liveupdateprovider` and registers the Capawesome provider with `LiveUpdateProviderRegistry` automatically when the Capacitor plugin loads.

**Attention**: The Ionic Live Update Provider SDK requires `minSdkVersion` **24** or higher. If your app targets a lower minimum, bump `minSdkVersion` in your `variables.gradle` when opting in.

## iOS

### CocoaPods

Add the `IonicProvider` subspec to your app's `Podfile`, **outside** the `capacitor_pods` function (Capacitor CLI regenerates the body of `capacitor_pods` on every `npx cap sync`, so any edit inside it is reverted):

```diff
target 'App' do
  capacitor_pods
  # Add your Pods here
+  pod 'CapawesomeCapacitorLiveUpdate/IonicProvider', :path => '../../node_modules/@capawesome/capacitor-live-update'
end
```

The `IonicProvider` subspec is layered on top of the default pod installation that `capacitor_pods` emits — CocoaPods merges both references into a single installation that additionally pulls in the `LiveUpdateProvider` pod and sets the `-DCAPAWESOME_INCLUDE_IONIC_PROVIDER` Swift compile flag, which compiles in the Ionic provider classes.

### Swift Package Manager

Enable the `IonicProvider` package trait in your `capacitor.config.json` (or `capacitor.config.ts`):

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

**Attention**: SPM trait support requires Capacitor CLI 8.3.0+ and Xcode 16.3+ (Swift 6.1+).

## Provider configuration

When Federated Capacitor or Portals creates a manager via this provider, it passes a configuration map. V1 accepts the following keys:

| Key          | Type   | Required | Description                                                                                              |
| ------------ | ------ | -------- | -------------------------------------------------------------------------------------------------------- |
| `managerKey` | string | Yes      | Scopes per-manager persisted state. Use a stable, unique key per shell / portal.                         |
| `appId`      | string | No       | Capawesome Cloud app UUID. Falls back to the plugin-wide `appId` from `capacitor.config.json`.           |
| `channel`    | string | No       | Channel to sync. Falls back to the plugin-wide `defaultChannel` from `capacitor.config.json`.            |

All other settings (`serverDomain`, `publicKey`, `httpTimeout`, etc.) come from the plugin-wide capacitor config. To set the per-device `customId`, call `LiveUpdate.setCustomId({ customId })` from JavaScript once at app startup.

## Federated Capacitor usage

Registration is automatic — the provider is registered on plugin load. In your Federated Capacitor configuration, reference the provider id:

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

## Portals usage

Construct the manager directly in your host app and hand it to your Portal:

```swift
import CapawesomeCapacitorLiveUpdate
import LiveUpdateProvider

let provider = LiveUpdateProviderRegistry.shared.resolve("capawesome")
let manager = try provider?.createManager(config: [
  "managerKey": "my-portal",
  "appId": "6e351b4f-69a7-415e-a057-4567df7ffe94",
  "channel": "production",
])
// hand `manager` to your Portal config
```

```kotlin
import io.ionic.liveupdateprovider.LiveUpdateProviderRegistry

val provider = LiveUpdateProviderRegistry.resolve("capawesome")
val manager = provider?.createManager(applicationContext, mapOf(
  "managerKey" to "my-portal",
  "appId" to "6e351b4f-69a7-415e-a057-4567df7ffe94",
  "channel" to "production",
))
// hand `manager` to your Portal config
```

**Note**: Provider mode and the standalone JavaScript sync flow share the same on-disk bundle storage but use isolated state pointers (`managerKey`-scoped). Pick one mode per app to avoid surprises.
