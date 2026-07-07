# Capacitor Network Plugin

Capacitor plugin to access network information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for network information. Here are some of the key features:

- 📶 **Network status**: Read whether the device is connected and how (Wi-Fi, cellular, ethernet, VPN).
- 🌍 **Internet reachability**: Detect whether the connection has verified access to the internet (Android).
- ✈️ **Airplane mode**: Read whether the airplane mode is enabled (Android).
- 👂 **Change events**: Listen for changes to the network status.
- 🌐 **Web support**: Read the network status on the web.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🤝 **Compatibility**: Works alongside the [Wifi](https://capawesome.io/docs/sdks/capacitor/wifi/) plugin.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-network` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-network
npx cap sync
```

On **Android**, the plugin declares the [`ACCESS_NETWORK_STATE`](https://developer.android.com/reference/android/Manifest.permission#ACCESS_NETWORK_STATE) permission in its manifest, so no additional configuration is required.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Network } from '@capawesome/capacitor-network';

const getStatus = async () => {
  const status = await Network.getStatus();
  return status;
};

const isAirplaneModeEnabled = async () => {
  const { enabled } = await Network.isAirplaneModeEnabled();
  return enabled;
};

const addNetworkStatusChangeListener = async () => {
  await Network.addListener('networkStatusChange', status => {
    console.log('Network status changed:', status);
  });
};

const removeAllListeners = async () => {
  await Network.removeAllListeners();
};
```

## API

<docgen-index>

* [`getStatus()`](#getstatus)
* [`isAirplaneModeEnabled()`](#isairplanemodeenabled)
* [`addListener('networkStatusChange', ...)`](#addlistenernetworkstatuschange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getStatus()

```typescript
getStatus() => Promise<GetStatusResult>
```

Get the current network status of the device.

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#getstatusresult">GetStatusResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAirplaneModeEnabled()

```typescript
isAirplaneModeEnabled() => Promise<IsAirplaneModeEnabledResult>
```

Get whether the airplane mode is currently enabled.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isairplanemodeenabledresult">IsAirplaneModeEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('networkStatusChange', ...)

```typescript
addListener(eventName: 'networkStatusChange', listenerFunc: (event: GetStatusResult) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the network status of the device.

The device is only observed while at least one listener is attached.

Only available on Android, iOS and Web.

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'networkStatusChange'</code>                                              |
| **`listenerFunc`** | <code>(event: <a href="#getstatusresult">GetStatusResult</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### GetStatusResult

| Prop                    | Type                                                      | Description                                                                                                                                                                                                                               | Since |
| ----------------------- | --------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`connected`**         | <code>boolean</code>                                      | Whether the device is currently connected to a network.                                                                                                                                                                                   | 0.1.0 |
| **`connectionType`**    | <code><a href="#connectiontype">ConnectionType</a></code> | The type of the currently active network connection.                                                                                                                                                                                      | 0.1.0 |
| **`internetReachable`** | <code>boolean \| null</code>                              | Whether the active network connection has verified access to the internet. This is `null` on platforms that cannot validate internet access (iOS and Web), where connectivity does not guarantee reachability. Only available on Android. | 0.1.0 |


#### IsAirplaneModeEnabledResult

| Prop          | Type                 | Description                                     | Since |
| ------------- | -------------------- | ----------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether the airplane mode is currently enabled. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Enums


#### ConnectionType

| Members        | Value                   | Description                                                  | Since |
| -------------- | ----------------------- | ------------------------------------------------------------ | ----- |
| **`Cellular`** | <code>'CELLULAR'</code> | The device is connected via a cellular network.              | 0.1.0 |
| **`Ethernet`** | <code>'ETHERNET'</code> | The device is connected via a wired ethernet network.        | 0.1.0 |
| **`None`**     | <code>'NONE'</code>     | The device is not connected to any network.                  | 0.1.0 |
| **`Unknown`**  | <code>'UNKNOWN'</code>  | The type of the network connection could not be determined.  | 0.1.0 |
| **`Vpn`**      | <code>'VPN'</code>      | The device is connected via a virtual private network (VPN). | 0.1.0 |
| **`Wifi`**     | <code>'WIFI'</code>     | The device is connected via a Wi-Fi network.                 | 0.1.0 |

</docgen-api>

## Network Information

Keep the following platform differences in mind when accessing network information:

- **Android**: The network status is read from the [`ConnectivityManager`](https://developer.android.com/reference/android/net/ConnectivityManager). The `internetReachable` property reflects the [`NET_CAPABILITY_VALIDATED`](https://developer.android.com/reference/android/net/NetworkCapabilities#NET_CAPABILITY_VALIDATED) capability, which fixes false positives when a VPN or captive portal is active.
- **iOS**: The network status is read from the [`NWPathMonitor`](https://developer.apple.com/documentation/network/nwpathmonitor) of the Network framework. The `internetReachable` property is always `null` because iOS cannot distinguish validated internet access from mere connectivity. VPN connections are reported as `UNKNOWN`. The `isAirplaneModeEnabled()` method is not available because there is no public API for it.
- **Web**: The network status is read from [`navigator.onLine`](https://developer.mozilla.org/en-US/docs/Web/API/Navigator/onLine) and the [Network Information API](https://developer.mozilla.org/en-US/docs/Web/API/Network_Information_API) (where supported). The `internetReachable` property is always `null`. The `isAirplaneModeEnabled()` method is not available.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/network/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/network/LICENSE).
