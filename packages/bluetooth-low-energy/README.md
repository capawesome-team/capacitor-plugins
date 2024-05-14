# @capawesome-team/capacitor-bluetooth-low-energy

Capacitor plugin for Bluetooth Low Energy (BLE) communication in the central role.

## Features

- 🔋 Supports Android and iOS
- ⚡️ Capacitor 6 support
- 🦾 **Headless Task**: Add custom native code for specific events.
- 🌙 **Foreground Service**: Keep the connection alive even when the app is in the background.
- ⏳ **Command Queue**: Queue up incoming commands to prevent operation failures.
- 📱 **Multiple Devices**: Connect to multiple devices at the same time.
- 🛠️ **Utils**: Utility functions to make your life easier. 

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

See [Getting started with Insiders](https://capawesome.io/sponsors/insiders/getting-started/?plugin=capacitor-bluetooth-low-energy) and follow the instructions to install the plugin.

After that, follow the platform-specific instructions in the sections [Android](#android) and [iOS](#ios).

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Needed only if your app looks for Bluetooth devices.  -->
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<!-- Needed only if your app communicates with already-paired Bluetooth devices. -->
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<!-- Needed only if your app uses Bluetooth scan results to derive physical location. -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<!-- Needed only if your app uses the foreground service. -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

You can read more about Bluetooth permissions in the [Android documentation](https://developer.android.com/develop/connectivity/bluetooth/bt-permissions).

#### Services

You also need to add the following service **inside** the `application` tag in your `AndroidManifest.xml` (usually `android/app/src/main/AndroidManifest.xml`):

```xml
<service android:name="io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyService" android:foregroundServiceType="connectedDevice" />
```

### iOS

Add the `NSBluetoothPeripheralUsageDescription` and `NSBluetoothAlwaysUsageDescription` keys to the `Info.plist` file (usually `ios/App/App/Info.plist`), which tells the user why the app needs access to Bluetooth peripherals:

```xml
<key>NSBluetoothAlwaysUsageDescription</key>
<string>The app needs access to Bluetooth peripherals to communicate with Bluetooth devices.</string>
```

If the app wants to use Bluetooth in the background, add the `UIBackgroundModes` key with the `bluetooth-central` value:

```xml
<key>UIBackgroundModes</key>
<array>
    <string>bluetooth-central</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { BluetoothLowEnergy, BluetoothLowEnergyUtils } from '@capawesome-team/capacitor-bluetooth-low-energy';

const connect = async () => {
  await BluetoothLowEnergy.connect({ deviceId: '00:00:00:00:00:00' });
};

const disconnect = async () => {
  await BluetoothLowEnergy.disconnect({ deviceId: '00:00:00:00:00:00' });
};

const discoverServices = async () => {
  await BluetoothLowEnergy.discoverServices({ deviceId: '00:00:00:00:00:00' });
};

const getConnectedDevices = async () => {
  const result = await BluetoothLowEnergy.getConnectedDevices();
  return result.devices;
};

const getServices = async () => {
  const result = await BluetoothLowEnergy.getServices({ deviceId: '00:00:00:00:00:00' });
  return result.services;
};

const initialize = async () => {
  await BluetoothLowEnergy.initialize();
};

const isEnabled = async () => {
  const result = await BluetoothLowEnergy.isEnabled();
  return result.enabled;
};

const openAppSettings = async () => {
  await BluetoothLowEnergy.openAppSettings();
};

const openBluetoothSettings = async () => {
  await BluetoothLowEnergy.openBluetoothSettings();
};

const openLocationSettings = async () => {
  await BluetoothLowEnergy.openLocationSettings();
};

const readCharacteristic = async () => {
  const result = await BluetoothLowEnergy.readCharacteristic({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
  });
  return result.value;
};

const readDescriptor = async () => {
  const result = await BluetoothLowEnergy.readDescriptor({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    descriptorId: '00002902-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
  });
  return result.value;
};

const readRssi = async () => {
  const result = await BluetoothLowEnergy.readRssi({ deviceId: '00:00:00:00:00:00' });
  return result.rssi;
};

const startCharacteristicNotifications = async () => {
  await BluetoothLowEnergy.startCharacteristicNotifications({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
  });
};

const startForegroundService = async () => {
  await BluetoothLowEnergy.startForegroundService({
    body: 'Body',
    id: 1,
    smallIcon: 'smallIcon',
    title: 'Title',
  });
};

const startScan = async () => {
  await BluetoothLowEnergy.startScan();
};

const stopCharacteristicNotifications = async () => {
  await BluetoothLowEnergy.stopCharacteristicNotifications({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
  });
};

const stopForegroundService = async () => {
  await BluetoothLowEnergy.stopForegroundService();
};

const stopScan = async () => {
  await BluetoothLowEnergy.stopScan();
};

const writeCharacteristic = async () => {
  await BluetoothLowEnergy.writeCharacteristic({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
    value: [0x01, 0x02, 0x03],
  });
};

const writeDescriptor = async () => {
  await BluetoothLowEnergy.writeDescriptor({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    descriptorId: '00002902-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
    value: [0x01, 0x02, 0x03],
  });
};

const checkPermissions = async () => {
  const result = await BluetoothLowEnergy.checkPermissions();
  return result;
};

const requestPermissions = async () => {
  const result = await BluetoothLowEnergy.requestPermissions();
  return result;
};

const addListener = () => {
  BluetoothLowEnergy.addListener('characteristicChanged', (event) => {
    console.log('Characteristic changed', event);
  });

  BluetoothLowEnergy.addListener('deviceDisconnected', (event) => {
    console.log('Device disconnected', event);
  });

  BluetoothLowEnergy.addListener('deviceScanned', (event) => {
    console.log('Device scanned', event);
  });
};

const removeAllListeners = () => {
  BluetoothLowEnergy.removeAllListeners();
};

const convertBytesToHex = (bytes: number[]) => {
  return BluetoothLowEnergyUtils.convertBytesToHex({ bytes });
};
```

## API

<docgen-index>

* [`connect(...)`](#connect)
* [`disconnect(...)`](#disconnect)
* [`discoverServices(...)`](#discoverservices)
* [`getConnectedDevices()`](#getconnecteddevices)
* [`getServices(...)`](#getservices)
* [`initialize()`](#initialize)
* [`isEnabled()`](#isenabled)
* [`openAppSettings()`](#openappsettings)
* [`openBluetoothSettings()`](#openbluetoothsettings)
* [`openLocationSettings()`](#openlocationsettings)
* [`readCharacteristic(...)`](#readcharacteristic)
* [`readDescriptor(...)`](#readdescriptor)
* [`readRssi(...)`](#readrssi)
* [`startCharacteristicNotifications(...)`](#startcharacteristicnotifications)
* [`startForegroundService(...)`](#startforegroundservice)
* [`startScan(...)`](#startscan)
* [`stopCharacteristicNotifications(...)`](#stopcharacteristicnotifications)
* [`stopForegroundService()`](#stopforegroundservice)
* [`stopScan()`](#stopscan)
* [`writeCharacteristic(...)`](#writecharacteristic)
* [`writeDescriptor(...)`](#writedescriptor)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('characteristicChanged', ...)`](#addlistenercharacteristicchanged)
* [`addListener('deviceDisconnected', ...)`](#addlistenerdevicedisconnected)
* [`addListener('deviceScanned', ...)`](#addlistenerdevicescanned)
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

Connect to a BLE device.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#connectoptions">ConnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### disconnect(...)

```typescript
disconnect(options: DisconnectOptions) => Promise<void>
```

Disconnect from the BLE device.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#disconnectoptions">DisconnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### discoverServices(...)

```typescript
discoverServices(options: DiscoverServiceOptions) => Promise<void>
```

Discover services provided by the device.

On **iOS**, this operation may take up to 30 seconds.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#discoverserviceoptions">DiscoverServiceOptions</a></code> |

**Since:** 6.0.0

--------------------


### getConnectedDevices()

```typescript
getConnectedDevices() => Promise<GetConnectedDevicesResult>
```

Get a list of connected devices.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getconnecteddevicesresult">GetConnectedDevicesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### getServices(...)

```typescript
getServices(options: GetServicesOptions) => Promise<GetServicesResult>
```

Get a list of services provided by the device.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getservicesoptions">GetServicesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getservicesresult">GetServicesResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### initialize()

```typescript
initialize() => Promise<void>
```

Initialize the plugin. This method must be called before any other method.

On **iOS**, this will prompt the user for Bluetooth permissions.

Only available on iOS.

**Since:** 6.0.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check if Bluetooth is enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### openAppSettings()

```typescript
openAppSettings() => Promise<void>
```

Open the Bluetooth settings on the device.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### openBluetoothSettings()

```typescript
openBluetoothSettings() => Promise<void>
```

Open the Bluetooth settings on the device.

Only available on Android.

**Since:** 6.0.0

--------------------


### openLocationSettings()

```typescript
openLocationSettings() => Promise<void>
```

Open the location settings on the device.

Only available on Android.

**Since:** 6.0.0

--------------------


### readCharacteristic(...)

```typescript
readCharacteristic(options: ReadCharacteristicOptions) => Promise<ReadCharacteristicResult>
```

Read the value of a characteristic.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#readcharacteristicoptions">ReadCharacteristicOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readcharacteristicresult">ReadCharacteristicResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### readDescriptor(...)

```typescript
readDescriptor(options: ReadDescriptorOptions) => Promise<ReadDescriptorResult>
```

Read the value of a descriptor.

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#readdescriptoroptions">ReadDescriptorOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readdescriptorresult">ReadDescriptorResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### readRssi(...)

```typescript
readRssi(options: ReadRssiOptions) => Promise<ReadRssiResult>
```

Read the RSSI value of the device.

Only available on Android and iOS.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#readrssioptions">ReadRssiOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readrssiresult">ReadRssiResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### startCharacteristicNotifications(...)

```typescript
startCharacteristicNotifications(options: StartCharacteristicNotificationsOptions) => Promise<void>
```

Start listening for characteristic value changes.

Only available on Android and iOS.

| Param         | Type                                                                                                        |
| ------------- | ----------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startcharacteristicnotificationsoptions">StartCharacteristicNotificationsOptions</a></code> |

**Since:** 6.0.0

--------------------


### startForegroundService(...)

```typescript
startForegroundService(options: StartForegroundServiceOptions) => Promise<void>
```

Start the foreground service.

Only available on Android.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startforegroundserviceoptions">StartForegroundServiceOptions</a></code> |

**Since:** 6.0.0

--------------------


### startScan(...)

```typescript
startScan(options?: StartScanOptions | undefined) => Promise<void>
```

Start scanning for BLE devices.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#startscanoptions">StartScanOptions</a></code> |

**Since:** 6.0.0

--------------------


### stopCharacteristicNotifications(...)

```typescript
stopCharacteristicNotifications(options: StopCharacteristicNotificationsOptions) => Promise<void>
```

Stop listening for characteristic value changes.

Only available on Android and iOS.

| Param         | Type                                                                                                      |
| ------------- | --------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#stopcharacteristicnotificationsoptions">StopCharacteristicNotificationsOptions</a></code> |

**Since:** 6.0.0

--------------------


### stopForegroundService()

```typescript
stopForegroundService() => Promise<void>
```

Stop the foreground service.

Only available on Android.

**Since:** 6.0.0

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

Stop scanning for BLE devices.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### writeCharacteristic(...)

```typescript
writeCharacteristic(options: WriteCharacteristicOptions) => Promise<void>
```

Write a value to a characteristic.

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#writecharacteristicoptions">WriteCharacteristicOptions</a></code> |

**Since:** 6.0.0

--------------------


### writeDescriptor(...)

```typescript
writeDescriptor(options: WriteDescriptorOptions) => Promise<void>
```

Write a value to a descriptor.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#writedescriptoroptions">WriteDescriptorOptions</a></code> |

**Since:** 6.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permission for

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(permissions?: BluetoothLowEnergyPluginPermission | undefined) => Promise<PermissionStatus>
```

Request permission

Only available on Android.

| Param             | Type                                                                                              |
| ----------------- | ------------------------------------------------------------------------------------------------- |
| **`permissions`** | <code><a href="#bluetoothlowenergypluginpermission">BluetoothLowEnergyPluginPermission</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('characteristicChanged', ...)

```typescript
addListener(eventName: 'characteristicChanged', listenerFunc: (event: CharacteristicChangedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a characteristic value changes.

Only available on Android and iOS.

| Param              | Type                                                                                                  |
| ------------------ | ----------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'characteristicChanged'</code>                                                                  |
| **`listenerFunc`** | <code>(event: <a href="#characteristicchangedevent">CharacteristicChangedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('deviceDisconnected', ...)

```typescript
addListener(eventName: 'deviceDisconnected', listenerFunc: (event: DeviceDisconnectedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a device is disconnected.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'deviceDisconnected'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#devicedisconnectedevent">DeviceDisconnectedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### addListener('deviceScanned', ...)

```typescript
addListener(eventName: 'deviceScanned', listenerFunc: (event: DeviceScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during the scan session.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'deviceScanned'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#devicescannedevent">DeviceScannedEvent</a>) =&gt; void</code> |

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

| Prop           | Type                | Description                                                                                                                         | Default            | Since |
| -------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to connect to.                                                                                            |                    | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the connect operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>10000</code> | 6.0.0 |


#### DisconnectOptions

| Prop           | Type                | Description                                                                                                                            | Default           | Since |
| -------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to disconnect from.                                                                                          |                   | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the disconnect operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### DiscoverServiceOptions

| Prop           | Type                | Description                                                                                                                                   | Default            | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to discover services for.                                                                                           |                    | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the discover services operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>20000</code> | 6.0.0 |


#### GetConnectedDevicesResult

| Prop          | Type                  | Description                    | Since |
| ------------- | --------------------- | ------------------------------ | ----- |
| **`devices`** | <code>Device[]</code> | An array of connected devices. | 6.0.0 |


#### Device

| Prop       | Type                | Description                       | Since |
| ---------- | ------------------- | --------------------------------- | ----- |
| **`id`**   | <code>string</code> | The UUID of the connected device. | 6.0.0 |
| **`name`** | <code>string</code> | The name of the connected device. | 6.0.0 |


#### GetServicesResult

| Prop           | Type                   | Description                                  | Since |
| -------------- | ---------------------- | -------------------------------------------- | ----- |
| **`services`** | <code>Service[]</code> | An array of services provided by the device. | 6.0.0 |


#### Service

| Prop                  | Type                          | Description                         | Since |
| --------------------- | ----------------------------- | ----------------------------------- | ----- |
| **`id`**              | <code>string</code>           | The UUID of the service.            | 6.0.0 |
| **`characteristics`** | <code>Characteristic[]</code> | The characteristics of the service. | 6.0.0 |


#### Characteristic

| Prop              | Type                                                                          | Description                            | Since |
| ----------------- | ----------------------------------------------------------------------------- | -------------------------------------- | ----- |
| **`id`**          | <code>string</code>                                                           | The UUID of the characteristic.        | 6.0.0 |
| **`descriptors`** | <code>Descriptor[]</code>                                                     | The descriptors of the characteristic. | 6.0.0 |
| **`properties`**  | <code><a href="#characteristicproperties">CharacteristicProperties</a></code> | The properties of the characteristic.  | 6.0.0 |


#### Descriptor

| Prop     | Type                | Description                 | Since |
| -------- | ------------------- | --------------------------- | ----- |
| **`id`** | <code>string</code> | The UUID of the descriptor. | 6.0.0 |


#### CharacteristicProperties

| Prop                             | Type                 | Description                                                        | Since |
| -------------------------------- | -------------------- | ------------------------------------------------------------------ | ----- |
| **`broadcast`**                  | <code>boolean</code> | Whether or not the characteristic can be broadcast.                | 6.0.0 |
| **`read`**                       | <code>boolean</code> | Whether or not the characteristic can be read.                     | 6.0.0 |
| **`writeWithoutResponse`**       | <code>boolean</code> | Whether or not the characteristic can be written without response. | 6.0.0 |
| **`write`**                      | <code>boolean</code> | Whether or not the characteristic can be written.                  | 6.0.0 |
| **`notify`**                     | <code>boolean</code> | Whether or not the characteristic supports notifications.          | 6.0.0 |
| **`indicate`**                   | <code>boolean</code> | Whether or not the characteristic supports indications.            | 6.0.0 |
| **`authenticatedSignedWrites`**  | <code>boolean</code> | Whether or not the characteristic supports signed writes.          | 6.0.0 |
| **`extendedProperties`**         | <code>boolean</code> | Whether or not the characteristic supports extended properties.    | 6.0.0 |
| **`notifyEncryptionRequired`**   | <code>boolean</code> | Whether or not the characteristic supports reliable writes.        | 6.0.0 |
| **`indicateEncryptionRequired`** | <code>boolean</code> | Whether or not the characteristic supports writable auxiliaries.   | 6.0.0 |


#### GetServicesOptions

| Prop           | Type                | Description                                                                                                                              | Default           | Since |
| -------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to get the services for.                                                                                       |                   | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the get services operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not Bluetooth is enabled. | 6.0.0 |


#### ReadCharacteristicResult

| Prop        | Type                  | Description                            | Since |
| ----------- | --------------------- | -------------------------------------- | ----- |
| **`value`** | <code>number[]</code> | The value bytes of the characteristic. | 6.0.0 |


#### ReadCharacteristicOptions

| Prop                   | Type                | Description                                                                                                                      | Default           | Since |
| ---------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code> | The UUID of the characteristic to read.                                                                                          |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code> | The address of the device to read the characteristic from.                                                                       |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code> | The UUID of the service to read the characteristic from.                                                                         |                   | 6.0.0 |
| **`timeout`**          | <code>number</code> | The timeout for the read operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### ReadDescriptorResult

| Prop        | Type                  | Description                        | Since |
| ----------- | --------------------- | ---------------------------------- | ----- |
| **`value`** | <code>number[]</code> | The value bytes of the descriptor. | 6.0.0 |


#### ReadDescriptorOptions

| Prop                   | Type                | Description                                                                                                                      | Default           | Since |
| ---------------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code> | The UUID of the characteristic that the descriptor belongs to.                                                                   |                   | 6.0.0 |
| **`descriptorId`**     | <code>string</code> | The UUID of the descriptor to read.                                                                                              |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code> | The address of the device to read the descriptor from.                                                                           |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code> | The UUID of the service that the descriptor belongs to.                                                                          |                   | 6.0.0 |
| **`timeout`**          | <code>number</code> | The timeout for the read operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### ReadRssiResult

| Prop       | Type                | Description     | Since |
| ---------- | ------------------- | --------------- | ----- |
| **`rssi`** | <code>number</code> | The RSSI value. | 6.0.0 |


#### ReadRssiOptions

| Prop           | Type                | Description                                                                                                                           | Default           | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to read the RSSI for.                                                                                       |                   | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the read RSSI operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### StartCharacteristicNotificationsOptions

| Prop                   | Type                | Description                                                                                                                                     | Default           | Since |
| ---------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code> | The UUID of the characteristic to start notifications for.                                                                                      |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code> | The address of the device to start notifications for.                                                                                           |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code> | The UUID of the service to start notifications for.                                                                                             |                   | 6.0.0 |
| **`timeout`**          | <code>number</code> | The timeout for the start notifications operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### StartForegroundServiceOptions

| Prop            | Type                | Description                                                                                                                                                                                                     | Since |
| --------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`body`**      | <code>string</code> | The body of the notification, shown below the title.                                                                                                                                                            | 6.0.0 |
| **`id`**        | <code>number</code> | The notification identifier.                                                                                                                                                                                    | 6.0.0 |
| **`smallIcon`** | <code>string</code> | The status bar icon for the notification. Icons should be placed in your app's `res/drawable` folder. The value for this option should be the drawable resource ID, which is the filename without an extension. | 6.0.0 |
| **`title`**     | <code>string</code> | The title of the notification.                                                                                                                                                                                  | 6.0.0 |


#### StartScanOptions

| Prop             | Type                  | Description                                                                             | Since |
| ---------------- | --------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`serviceIds`** | <code>string[]</code> | Find devices with services that match any of the provided UUIDs. Only available on iOS. | 6.0.0 |


#### StopCharacteristicNotificationsOptions

| Prop                   | Type                | Description                                                                                                                                    | Default           | Since |
| ---------------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code> | The UUID of the characteristic to stop notifications for.                                                                                      |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code> | The address of the device to stop notifications for.                                                                                           |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code> | The UUID of the service to stop notifications for.                                                                                             |                   | 6.0.0 |
| **`timeout`**          | <code>number</code> | The timeout for the stop notifications operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |


#### WriteCharacteristicOptions

| Prop                   | Type                  | Description                                                                                                                       | Default           | Since |
| ---------------------- | --------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code>   | The UUID of the characteristic to write.                                                                                          |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code>   | The address of the device to write the characteristic to.                                                                         |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code>   | The UUID of the service to write the characteristic to.                                                                           |                   | 6.0.0 |
| **`timeout`**          | <code>number</code>   | The timeout for the write operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |
| **`value`**            | <code>number[]</code> | The value bytes to write to the characteristic.                                                                                   |                   | 6.0.0 |


#### WriteDescriptorOptions

| Prop                   | Type                  | Description                                                                                                                       | Default           | Since |
| ---------------------- | --------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`characteristicId`** | <code>string</code>   | The UUID of the characteristic that the descriptor belongs to.                                                                    |                   | 6.0.0 |
| **`descriptorId`**     | <code>string</code>   | The UUID of the descriptor.                                                                                                       |                   | 6.0.0 |
| **`deviceId`**         | <code>string</code>   | The address of the device that the descriptor belongs to.                                                                         |                   | 6.0.0 |
| **`serviceId`**        | <code>string</code>   | The UUID of the service that the descriptor belongs to.                                                                           |                   | 6.0.0 |
| **`timeout`**          | <code>number</code>   | The timeout for the write operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code> | 6.0.0 |
| **`value`**            | <code>number[]</code> | The value bytes of the descriptor.                                                                                                |                   | 6.0.0 |


#### PermissionStatus

| Prop                   | Type                                                        | Description                                                                 | Since |
| ---------------------- | ----------------------------------------------------------- | --------------------------------------------------------------------------- | ----- |
| **`bluetooth`**        | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using bluetooth. Only available on iOS.                | 6.0.0 |
| **`bluetoothConnect`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for connecting to a BLE device. Only available on Android. | 6.0.0 |
| **`bluetoothScan`**    | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for scanning for BLE devices. Only available on Android.   | 6.0.0 |
| **`location`**         | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using location services. Only available on Android.    | 6.0.0 |
| **`notifications`**    | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using notifications. Only available on Android.        | 6.0.0 |


#### BluetoothLowEnergyPluginPermission

| Prop              | Type                                            |
| ----------------- | ----------------------------------------------- |
| **`permissions`** | <code>BluetoothLowEnergyPermissionType[]</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CharacteristicChangedEvent

| Prop                   | Type                  | Description                                    | Since |
| ---------------------- | --------------------- | ---------------------------------------------- | ----- |
| **`characteristicId`** | <code>string</code>   | The UUID of the characteristic.                | 6.0.0 |
| **`deviceId`**         | <code>string</code>   | The address of the device.                     | 6.0.0 |
| **`serviceId`**        | <code>string</code>   | The UUID of the service.                       | 6.0.0 |
| **`value`**            | <code>number[]</code> | The changed value bytes of the characteristic. | 6.0.0 |


#### DeviceDisconnectedEvent

| Prop           | Type                | Description                             | Since |
| -------------- | ------------------- | --------------------------------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the disconnected device. | 6.0.0 |
| **`name`**     | <code>string</code> | The name of the disconnected device.    | 6.0.0 |


#### DeviceScannedEvent

| Prop       | Type                | Description                                                  | Since |
| ---------- | ------------------- | ------------------------------------------------------------ | ----- |
| **`id`**   | <code>string</code> | The address of the scanned device.                           | 6.0.0 |
| **`name`** | <code>string</code> | The name of the scanned device.                              | 6.0.0 |
| **`rssi`** | <code>number</code> | The RSSI value of the scanned device. Only available on iOS. | 6.0.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### BluetoothLowEnergyPermissionType

<code>'bluetooth' | 'bluetoothConnect' | 'bluetoothScan' | 'location' | 'notifications'</code>

</docgen-api>

## Utils

See [docs/utils/README.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/docs/utils/README.md).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/LICENSE).
