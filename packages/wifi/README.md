# @capawesome-team/capacitor-wifi

Capacitor plugin to manage Wi-Fi connectivity.

## Features

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🌐 **Network Management**: Connect and disconnect networks.
- 🔍 **Network Scan**: Perform scans for available networks.
- 📟 **Device Info**: Retrieve essential device information like IP address.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-wifi
npx cap sync
```

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

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                 | Type                 | Description                                                                                                             | Default            | Since |
| -------------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`useWifiManager`** | <code>boolean</code> | Whether or not to use the **deprecated** `WifiManager` API for connecting to Wi-Fi networks. Only available on Android. | <code>false</code> | 6.3.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "Wifi": {
      "useWifiManager": undefined
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome-team/capacitor-wifi" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    Wifi: {
      useWifiManager: undefined,
    },
  },
};

export default config;
```

</docgen-config>

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
* [`addListener('networksScanned', ...)`](#addlistenernetworksscanned-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

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

| Prop                | Type                               | Description                                                                            | Since |
| ------------------- | ---------------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`rssi`**          | <code>number</code>                | The received signal strength indicator (RSSI) of the network in dBm.                   | 6.1.0 |
| **`securityTypes`** | <code>NetworkSecurityType[]</code> | The service set identifier (SSID) of the network. Only available on Android (SDK 33+). | 6.1.0 |
| **`ssid`**          | <code>string</code>                | The service set identifier (SSID) of the network.                                      | 6.0.0 |


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


### Enums


#### NetworkSecurityType

| Members                           | Value           | Description                                                                                | Since |
| --------------------------------- | --------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`UNKNOWN`**                     | <code>-1</code> | Unknown security type.                                                                     | 6.1.0 |
| **`OPEN`**                        | <code>0</code>  | Open network.                                                                              | 6.1.0 |
| **`WEP`**                         | <code>1</code>  | WEP network.                                                                               | 6.1.0 |
| **`PSK`**                         | <code>2</code>  | PSK network.                                                                               | 6.1.0 |
| **`EAP`**                         | <code>3</code>  | EAP network.                                                                               | 6.1.0 |
| **`SAE`**                         | <code>4</code>  | SAE network.                                                                               | 6.1.0 |
| **`EAP_WPA3_ENTERPRISE_192_BIT`** | <code>5</code>  | WPA3-Enterprise in 192-bit security network.                                               | 6.1.0 |
| **`OWE`**                         | <code>6</code>  | OWE network.                                                                               | 6.1.0 |
| **`WAPI_PSK`**                    | <code>7</code>  | WAPI PSK network.                                                                          | 6.1.0 |
| **`WAPI_CERT`**                   | <code>8</code>  | WAPI Certificate network.                                                                  | 6.1.0 |
| **`WPA3_ENTERPRISE`**             | <code>9</code>  | WPA3-Enterprise network.                                                                   | 6.1.0 |
| **`OSEN`**                        | <code>10</code> | OSEN network.                                                                              | 6.1.0 |
| **`PASSPOINT_R1_R2`**             | <code>11</code> | Passpoint R1/R2 network, where TKIP and WEP are not allowed.                               | 6.1.0 |
| **`PASSPOINT_R3`**                | <code>12</code> | Passpoint R3 network, where TKIP and WEP are not allowed, and PMF must be set to Required. | 6.1.0 |
| **`DPP`**                         | <code>13</code> | Easy Connect (DPP) network.                                                                | 6.1.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wifi/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/wifi/LICENSE).
