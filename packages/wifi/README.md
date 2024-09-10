# @capawesome-team/capacitor-wifi

Capacitor plugin to manage Wi-Fi connectivity.

## Features

- 🔋 Supports Android and iOS
- ⚡️ Capacitor 6 support
- 🔗 Connect and disconnect networks
- 📶 Scan for networks
- 📱 Retrieve device information such as IP address

## Sponsorware

This project is available as **Sponsorware**.

> Sponsorware is a release strategy for open-source software that enables developers to be compensated for their open-source work with fewer downsides than traditional open-source funding models. ([Source](https://github.com/sponsorware/docs))

This means...

- The source code will be published as soon as the [funding goal](https://capawesome.io/sponsors/insiders/#funding) is reached.
- Any [sponsor](https://capawesome.io/sponsors/insiders/) with a sponsorware tier gets **immediate access** to our sponsors-only repository and can start using the project right away.

## Terms

This project is licensed under the terms of the MIT license.  
However, we kindly ask you to respect our **fair use policy**:

- Please **don't distribute the source code** of the sponsors-only repository. You may freely use it for public, private or commercial projects, privately fork or mirror it, but please don't make the source code public, as it would counteract the sponsorware strategy.
- If you cancel your subscription, you're automatically removed as a collaborator and will miss out on all future updates. However, **you may use the latest version that's available to you as long as you like**.

## Installation

See [Getting started with Insiders](https://capawesome.io/sponsors/insiders/getting-started/?plugin=capacitor-wifi) and follow the instructions to install the plugin.

After that, follow the platform-specific instructions in the sections [Android](#android) and [iOS](#ios).

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
```

### iOS

#### Entitlements

Ensure `Access Wi-Fi Information` and `Hotspot` capabilities have been enabled in your application in Xcode.
See [Add a capability to a target](https://help.apple.com/xcode/mac/current/#/dev88ff319e7) for more information.

#### Privacy Descriptions

Add the `NSLocationWhenInUseUsageDescription` and `NSLocationAlwaysAndWhenInUseUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why your app is requesting location information:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>We need your location to request Wi-Fi information.</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>We need your location to request Wi-Fi information.</string>
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Wifi } from '@capawesome-team/capacitor-wifi';

const connect = async () => {
    await Wifi.connect({
        ssid: 'MyNetwork',
        password: 'MyPassword',
        isHiddenSsid: false
    });
}

const disconnect = async () => {
    await Wifi.disconnect();
}

const getAvailableNetworks = async () => {
    const result = await Wifi.getAvailableNetworks();
    return result.networks;
}

const getIpAddress = async () => {
    const result = await Wifi.getIpAddress();
    return result.address;
}

const getRssi = async () => {
    const result = await Wifi.getRssi();
    return result.rssi;
}

const getSsid = async () => {
    const result = await Wifi.getSsid();
    return result.ssid;
}

const isEnabled = async () => {
    const result = await Wifi.isEnabled();
    return result.enabled;
}

const startScan = async () => {
    await Wifi.startScan();
}
```

## API

<docgen-index>

* [`connect(...)`](#connect)
* [`disconnect(...)`](#disconnect)
* [`getAvailableNetworks()`](#getavailablenetworks)
* [`getIpAddress()`](#getipaddress)
* [`getRssi()`](#getrssi)
* [`getSsid()`](#getssid)
* [`isEnabled()`](#isenabled)
* [`startScan()`](#startscan)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('networksScanned', ...)`](#addlistenernetworksscanned)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### connect(...)

```typescript
connect(options: ConnectOptions) => Promise<void>
```

Connect to a Wi-Fi network.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#connectoptions">ConnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### disconnect(...)

```typescript
disconnect(options?: DisconnectOptions | undefined) => Promise<void>
```

Disconnect from a Wi-Fi network.

On **iOS**, you can only disconnect from networks that you connected to using the plugin.
This also removes the Wi-Fi network from the list of known networks.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#disconnectoptions">DisconnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### getAvailableNetworks()

```typescript
getAvailableNetworks() => Promise<GetAvailableNetworksResult>
```

Get a list of Wi-Fi networks found during the last scan.

The returned networks are the most recently updated results, which may be from a previous scan
if your current scan has not completed or succeeded.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getavailablenetworksresult">GetAvailableNetworksResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getIpAddress()

```typescript
getIpAddress() => Promise<GetIpAddressResult>
```

Get the current IP address of the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getipaddressresult">GetIpAddressResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getRssi()

```typescript
getRssi() => Promise<GetRssiResult>
```

Get the received signal strength indicator (RSSI) of the current network in dBm.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getrssiresult">GetRssiResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getSsid()

```typescript
getSsid() => Promise<GetSsidResult>
```

Get the service set identifier (SSID) of the current network.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getssidresult">GetSsidResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check if Wi-Fi is enabled.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### startScan()

```typescript
startScan() => Promise<void>
```

Start a scan for Wi-Fi networks.

This call may fail for any of the following reasons:
- Scan requests may be throttled because of too many scans in a short time.
- The device is idle and scanning is disabled.
- Wi-Fi hardware reports a scan failure.

Only available on Android.

**Since:** 6.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for the plugin.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions for the plugin.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('networksScanned', ...)

```typescript
addListener(eventName: 'networksScanned', listenerFunc: (event: NetworksScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the scan results are available.

Only available on Android.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'networksScanned'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#networksscannedevent">NetworksScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 6.0.0

--------------------


### Interfaces


#### ConnectOptions

| Prop               | Type                 | Description                                                   | Default            | Since |
| ------------------ | -------------------- | ------------------------------------------------------------- | ------------------ | ----- |
| **`ssid`**         | <code>string</code>  | The SSID of the network to connect to.                        |                    |       |
| **`password`**     | <code>string</code>  | The password of the network to connect to.                    |                    | 6.0.0 |
| **`isHiddenSsid`** | <code>boolean</code> | Whether or not the SSID is hidden. Only available on Android. | <code>false</code> | 6.0.0 |


#### DisconnectOptions

| Prop       | Type                | Description                                                                                                                              | Since |
| ---------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ssid`** | <code>string</code> | The SSID of the network to disconnect from. If not provided, the device will disconnect from the current network. Only available on iOS. | 6.0.0 |


#### GetAvailableNetworksResult

| Prop           | Type                   | Description                                            | Since |
| -------------- | ---------------------- | ------------------------------------------------------ | ----- |
| **`networks`** | <code>Network[]</code> | The list of Wi-Fi networks found during the last scan. | 6.0.0 |


#### Network

| Prop       | Type                | Description                                       | Since |
| ---------- | ------------------- | ------------------------------------------------- | ----- |
| **`ssid`** | <code>string</code> | The service set identifier (SSID) of the network. | 6.0.0 |


#### GetIpAddressResult

| Prop          | Type                | Description                   | Since |
| ------------- | ------------------- | ----------------------------- | ----- |
| **`address`** | <code>string</code> | The IP address of the device. | 6.0.0 |


#### GetRssiResult

| Prop       | Type                | Description                                                                  | Since |
| ---------- | ------------------- | ---------------------------------------------------------------------------- | ----- |
| **`rssi`** | <code>number</code> | The received signal strength indicator (RSSI) of the current network in dBm. | 6.0.0 |


#### GetSsidResult

| Prop       | Type                | Description                                                                                                                                                                                                        | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`ssid`** | <code>string</code> | The service set identifier (SSID) of the current network. On **iOS 14+**, the SSID can only be retrieved if the network was connected to using the plugin or if the app has permission to access precise location. | 6.0.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                      | Since |
| ------------- | -------------------- | -------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not Wi-Fi is enabled. | 6.0.0 |


#### PermissionStatus

| Prop           | Type                                                        | Since |
| -------------- | ----------------------------------------------------------- | ----- |
| **`location`** | <code><a href="#permissionstate">PermissionState</a></code> | 6.0.0 |


#### RequestPermissionsOptions

| Prop              | Type                      | Description                 | Default                   | Since |
| ----------------- | ------------------------- | --------------------------- | ------------------------- | ----- |
| **`permissions`** | <code>'location'[]</code> | The permissions to request. | <code>["location"]</code> | 6.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### NetworksScannedEvent

| Prop           | Type                   | Description                                       | Since |
| -------------- | ---------------------- | ------------------------------------------------- | ----- |
| **`networks`** | <code>Network[]</code> | The list of Wi-Fi networks found during the scan. | 6.0.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### PermissionType

<code>'location'</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wifi/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wifi/LICENSE).
