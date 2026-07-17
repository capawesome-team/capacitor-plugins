# Capacitor Network Plugin

Capacitor plugin to access network information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Network plugin is one of the most complete network information solutions for Capacitor apps. Here are some of the key features:

- 📶 **Network status**: Read whether the device is connected and how (Wi-Fi, cellular, ethernet, VPN).
- 🌍 **Internet reachability**: Detect whether the connection has verified access to the internet (Android).
- ✈️ **Airplane mode**: Read whether the airplane mode is enabled (Android).
- 👂 **Change events**: Listen for changes to the network status.
- 🌐 **Web support**: Read the network status on the web.
- 🤝 **Compatibility**: Works alongside the [Wifi](https://capawesome.io/docs/sdks/capacitor/wifi/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Network plugin is typically used whenever an app needs to react to the device's connectivity, for example:

- **Offline detection**: Show an offline banner or disable network-dependent features when the connection is lost.
- **Conditional downloads**: Only download large files when the device is connected via Wi-Fi instead of a cellular network.
- **Reliable syncing**: On Android, check whether the connection has verified internet access before starting a sync, avoiding false positives caused by captive portals or VPNs.
- **Airplane mode hints**: On Android, detect that airplane mode is enabled and inform the user why no connection is available.

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

The following examples show how to get the current network status, check whether airplane mode is enabled, and listen for changes to the network status.

### Get the current network status

Read whether the device is currently connected, the type of the connection (e.g. Wi-Fi or cellular) and, on Android, whether the connection has verified access to the internet:

```typescript
import { Network } from '@capawesome/capacitor-network';

const getStatus = async () => {
  const status = await Network.getStatus();
  return status;
};
```

### Check whether airplane mode is enabled

Read whether the airplane mode is currently enabled. Only available on Android:

```typescript
import { Network } from '@capawesome/capacitor-network';

const isAirplaneModeEnabled = async () => {
  const { enabled } = await Network.isAirplaneModeEnabled();
  return enabled;
};
```

### Listen for changes to the network status

Get notified whenever the network status of the device changes. The device is only observed while at least one listener is attached:

```typescript
import { Network } from '@capawesome/capacitor-network';

const addNetworkStatusChangeListener = async () => {
  await Network.addListener('networkStatusChange', status => {
    console.log('Network status changed:', status);
  });
};
```

When you no longer need updates, remove all listeners:

```typescript
import { Network } from '@capawesome/capacitor-network';

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

## FAQ

### How is this plugin different from other similar plugins?

It reports connectivity and the connection type (Wi-Fi, cellular, ethernet, or VPN) on Android, iOS, and the web, and on Android it adds verified internet reachability that avoids false positives from captive portals or VPNs, plus an airplane-mode check. You can read the status once or listen for change events, with the device observed only while a listener is attached, all through a fully typed API with typed connection-type enums. It supports both CocoaPods and Swift Package Manager on iOS, is honest about platform limits by returning `null` where reachability cannot be validated, and is actively maintained against the latest Capacitor and OS versions.

### What is the difference between `connected` and `internetReachable`?

The `connected` property tells you whether the device is connected to any network, while `internetReachable` tells you whether that connection has verified access to the internet. A device can be connected to a network without actually reaching the internet, for example behind a captive portal or when a VPN is active. The `internetReachable` property is only available on Android, where it reflects the `NET_CAPABILITY_VALIDATED` capability of the connection.

### Why is `internetReachable` always `null` on iOS and Web?

iOS and the Web platform cannot distinguish validated internet access from mere network connectivity, so the plugin returns `null` instead of a potentially misleading value. On these platforms, being connected to a network does not guarantee that the internet is reachable. See the [Network Information](#network-information) section for the platform-specific details.

### Can I check whether airplane mode is enabled on iOS or Web?

No, the `isAirplaneModeEnabled()` method is only available on Android. On iOS, there is no public API to read the airplane mode state, and the Web platform does not expose this information either.

### Do I need any permissions to use this plugin?

No, the plugin works without any additional configuration. On Android, the plugin already declares the required `ACCESS_NETWORK_STATE` permission in its own manifest, see the [Installation](#installation) section.

### Why is a VPN connection reported as `UNKNOWN` on iOS?

On iOS, the network status is read from the `NWPathMonitor` of the Network framework, and VPN connections are reported as `UNKNOWN`. The `VPN` connection type is only reported on platforms that can detect it, such as Android. See the [Network Information](#network-information) section for more platform differences.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Battery](https://capawesome.io/docs/sdks/capacitor/battery/): Access battery information of the device.
- [Bluetooth Low Energy](https://capawesome.io/docs/sdks/capacitor/bluetooth-low-energy/): Communicate with Bluetooth Low Energy (BLE) devices in the central and peripheral role.
- [NFC](https://capawesome.io/docs/sdks/capacitor/nfc/): Read, write, and emulate NFC tags with advanced features like HCE and raw command handling.
- [Sim](https://capawesome.io/docs/sdks/capacitor/sim/): Read SIM card and carrier information.
- [Wifi](https://capawesome.io/docs/sdks/capacitor/wifi/): Manage Wi-Fi connectivity, including adding, connecting, and disconnecting networks.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/network/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/network/LICENSE).
