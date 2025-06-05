# @capawesome-team/capacitor-bluetooth-low-energy

Capacitor plugin for Bluetooth Low Energy (BLE) communication in the central and peripheral role.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for Bluetooth Low Energy communication. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🔄 **Central Role**: Communicate with BLE peripherals as a central device.
- 📳 **Peripheral Role**: Act as a BLE peripheral to communicate with other central devices.
- 🦾 **Headless Task**: Add custom native code for specific events.
- 🌙 **Foreground Service**: Keep the connection alive even when the app is in the background.
- ⏳ **Command Queue**: Queue up incoming commands to prevent operation failures.
- 📱 **Multiple Devices**: Connect to multiple devices at the same time.
- 🛠️ **Utils**: Utility functions to make your life easier. 
- ⚔️ **Battle-Tested**: Used in more than 30 projects.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll add it for you!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |
| 6.x.x          | 6.x.x             | Deprecated     |

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-heart-rate-monitor-app).

| Android                                                                                                   | iOS                                                                                                       |
| --------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------- |
| <img src="https://github.com/user-attachments/assets/c4cf7ddc-7f98-42e1-8334-34a26dfdf457" width="266" /> | <img src="https://github.com/user-attachments/assets/3cfac38f-22ef-4b8e-a439-529079926a4e" width="266" /> |

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
npm install @capawesome-team/capacitor-bluetooth-low-energy
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<!-- Request legacy Bluetooth permissions on older devices. -->
<uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30" />
<!-- Needed only if your app uses advertising -->
<uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />
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

#### Features

Add the following feature to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-feature android:name="android.hardware.bluetooth_le" android:required="false" />
```

Set it to `true` if BLE is required for your app. Then the Google Play store will hide your app from users on devices lacking those features.
You can read more about Bluetooth features in the [Android documentation](https://developer.android.com/develop/connectivity/bluetooth/bt-permissions#features).

#### Services

You also need to add the following service **inside** the `application` tag in your `AndroidManifest.xml` (usually `android/app/src/main/AndroidManifest.xml`):

```xml
<service android:name="io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyService" android:foregroundServiceType="connectedDevice" />
```

#### Headless Task

If you want to run your own native code when a specific event occurs, you can create a headless task.
For this, you need to create a Java class with the name `BluetoothLowEnergyHeadlessTask` in the same package as your `MainActivity`.
Then you need to add the `onCharacteristicChanged` method to your class:

```java
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import androidx.annotation.NonNull;

public class BluetoothLowEnergyHeadlessTask {
  public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
    // Your code here
  }

  public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
    // Your code here
  }
}
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the `NSBluetoothAlwaysUsageDescription` key to the `Info.plist` file (usually `ios/App/App/Info.plist`), which tells the user why the app needs access to Bluetooth peripherals:

```xml
<key>NSBluetoothAlwaysUsageDescription</key>
<string>The app needs access to Bluetooth peripherals to communicate with Bluetooth devices.</string>
```

#### Background Modes

If the app wants to use Bluetooth in the background, add the `UIBackgroundModes` key with the `bluetooth-central` value:

```xml
<key>UIBackgroundModes</key>
<array>
    <string>bluetooth-central</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Guides

- [Announcing the Capacitor Bluetooth Low Energy Plugin](https://capawesome.io/blog/announcing-the-capacitor-bluetooth-low-energy-plugin/)
- [How to Build a Heart Rate Monitor with Capacitor](https://capawesome.io/blog/how-to-build-a-heart-rate-monitor-with-capacitor/)

## Usage

```typescript
import { BluetoothLowEnergy, BluetoothLowEnergyUtils, ConnectionPriority } from '@capawesome-team/capacitor-bluetooth-low-energy';

const connect = async () => {
  await BluetoothLowEnergy.connect({ deviceId: '00:00:00:00:00:00' });
};

const createBond = async () => {
  await BluetoothLowEnergy.createBond({ deviceId: '00:00:00:00:00:00' });
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

const isBonded = async () => {
  const result = await BluetoothLowEnergy.isBonded({ deviceId: '00:00:00:00:00:00' });
  return result.bonded;
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

const requestConnectionPriority = async () => {
  await BluetoothLowEnergy.requestConnectionPriority({
    connectionPriority: ConnectionPriority.BALANCED,
    deviceId: '00:00:00:00:00:00',
  });
};

const requestMtu = async () => {
  await BluetoothLowEnergy.requestMtu({
    deviceId: '00:00:00:00:00:00',
    mtu: 512,
  });
};

const setCharacteristicValue = async () => {
  await BluetoothLowEnergy.setCharacteristicValue({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
    value: [1, 2, 3],
  });
};

const startAdvertising = async () => {
  await BluetoothLowEnergy.startAdvertising({
        services: [
          {
            id: '0000180A-0000-1000-8000-00805F9B34FB',
            characteristics: [
              {
                id: '00002A29-0000-1000-8000-00805F9B34FB',
                descriptors: [], // Descriptors are ignored for now
                permissions: {
                  read: true,
                  write: true,
                },
                properties: {
                  read: true,
                  write: true,
                  notify: true,
                  indicate: true,
                },
              },
            ],
          },
        ],
      });
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

const stopAdvertising = async () => {
  await BluetoothLowEnergy.stopAdvertising();
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
    value: [1, 2, 3],
  });
};

const writeDescriptor = async () => {
  await BluetoothLowEnergy.writeDescriptor({
    characteristicId: '00002a00-0000-1000-8000-00805f9b34fb',
    descriptorId: '00002902-0000-1000-8000-00805f9b34fb',
    deviceId: '00:00:00:00:00:00',
    serviceId: '00001800-0000-1000-8000-00805f9b34fb',
    value: [1, 2, 3],
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

  BluetoothLowEnergy.addListener('characteristicWriteRequest', async (event) => {
    console.log('Characteristic write request', event);
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
* [`createBond(...)`](#createbond)
* [`disconnect(...)`](#disconnect)
* [`discoverServices(...)`](#discoverservices)
* [`getConnectedDevices()`](#getconnecteddevices)
* [`getServices(...)`](#getservices)
* [`initialize(...)`](#initialize)
* [`isBonded(...)`](#isbonded)
* [`isEnabled()`](#isenabled)
* [`openAppSettings()`](#openappsettings)
* [`openBluetoothSettings()`](#openbluetoothsettings)
* [`openLocationSettings()`](#openlocationsettings)
* [`readCharacteristic(...)`](#readcharacteristic)
* [`readDescriptor(...)`](#readdescriptor)
* [`readRssi(...)`](#readrssi)
* [`requestConnectionPriority(...)`](#requestconnectionpriority)
* [`requestMtu(...)`](#requestmtu)
* [`setCharacteristicValue(...)`](#setcharacteristicvalue)
* [`startAdvertising(...)`](#startadvertising)
* [`startCharacteristicNotifications(...)`](#startcharacteristicnotifications)
* [`startForegroundService(...)`](#startforegroundservice)
* [`startScan(...)`](#startscan)
* [`stopAdvertising()`](#stopadvertising)
* [`stopCharacteristicNotifications(...)`](#stopcharacteristicnotifications)
* [`stopForegroundService()`](#stopforegroundservice)
* [`stopScan()`](#stopscan)
* [`writeCharacteristic(...)`](#writecharacteristic)
* [`writeDescriptor(...)`](#writedescriptor)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`addListener('characteristicChanged', ...)`](#addlistenercharacteristicchanged-)
* [`addListener('characteristicWriteRequest', ...)`](#addlistenercharacteristicwriterequest-)
* [`addListener('deviceConnected', ...)`](#addlistenerdeviceconnected-)
* [`addListener('deviceDisconnected', ...)`](#addlistenerdevicedisconnected-)
* [`addListener('deviceScanned', ...)`](#addlistenerdevicescanned-)
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

Connect to a BLE device.

Only available on Android and iOS.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#connectoptions">ConnectOptions</a></code> |

**Since:** 6.0.0

--------------------


### createBond(...)

```typescript
createBond(options: CreateBondOptions) => Promise<void>
```

Create a bond with the BLE device.

Only available on Android.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#createbondoptions">CreateBondOptions</a></code> |

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


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initialize the plugin. This method must be called before any other method.

On **iOS**, this will prompt the user for Bluetooth permissions.

Only available on iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 6.0.0

--------------------


### isBonded(...)

```typescript
isBonded(options: IsBondedOptions) => Promise<IsBondedResult>
```

Check if the device is bonded.

Only available on Android.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#isbondedoptions">IsBondedOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isbondedresult">IsBondedResult</a>&gt;</code>

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


### requestConnectionPriority(...)

```typescript
requestConnectionPriority(options: RequestConnectionPriorityOptions) => Promise<void>
```

Request a connection priority.

Only available on Android.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestconnectionpriorityoptions">RequestConnectionPriorityOptions</a></code> |

**Since:** 6.0.0

--------------------


### requestMtu(...)

```typescript
requestMtu(options: RequestMtuOptions) => Promise<void>
```

Request an MTU size.

Only available on Android.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#requestmtuoptions">RequestMtuOptions</a></code> |

**Since:** 6.0.0

--------------------


### setCharacteristicValue(...)

```typescript
setCharacteristicValue(options: SetCharacteristicValueOptions) => Promise<void>
```

Set the value of a characteristic.

Only available on Android.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setcharacteristicvalueoptions">SetCharacteristicValueOptions</a></code> |

**Since:** 7.2.0

--------------------


### startAdvertising(...)

```typescript
startAdvertising(options: StartAdvertisingOptions) => Promise<void>
```

Start advertising as a BLE device.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startadvertisingoptions">StartAdvertisingOptions</a></code> |

**Since:** 7.2.0

--------------------


### startCharacteristicNotifications(...)

```typescript
startCharacteristicNotifications(options: StartCharacteristicNotificationsOptions) => Promise<void>
```

Start listening for characteristic value changes. This will emit the `characteristicChanged` event when a value changes.

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

Start the foreground service and show a notification.

This method should be called when the app is moved to the background to
keep the Bluetooth connections alive.

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

Start scanning for BLE devices. This will emit the `deviceScanned` event when a device is found.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#startscanoptions">StartScanOptions</a></code> |

**Since:** 6.0.0

--------------------


### stopAdvertising()

```typescript
stopAdvertising() => Promise<void>
```

Stop advertising as a BLE device.

Only available on Android and iOS.

**Since:** 7.2.0

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

Stop the foreground service and remove the notification.

This method should be called when the app is moved to the foreground
since the foreground service is no longer needed.

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

Check permissions for the plugin.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 6.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(permissions?: BluetoothLowEnergyPluginPermission | undefined) => Promise<PermissionStatus>
```

Request permissions for the plugin.

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


### addListener('characteristicWriteRequest', ...)

```typescript
addListener(eventName: 'characteristicWriteRequest', listenerFunc: (event: CharacteristicWriteRequestEvent) => void) => Promise<PluginListenerHandle>
```

Called when a characteristic write request is received.

Only available on Android.

| Param              | Type                                                                                                            |
| ------------------ | --------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'characteristicWriteRequest'</code>                                                                       |
| **`listenerFunc`** | <code>(event: <a href="#characteristicwriterequestevent">CharacteristicWriteRequestEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.2.0

--------------------


### addListener('deviceConnected', ...)

```typescript
addListener(eventName: 'deviceConnected', listenerFunc: (event: DeviceConnectedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a device is connected.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'deviceConnected'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#deviceconnectedevent">DeviceConnectedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.1.0

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

| Prop              | Type                 | Description                                                                                                                                                            | Default            | Since |
| ----------------- | -------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`autoConnect`** | <code>boolean</code> | Whether to directly connect to the remote device (false) or to automatically connect as soon as the remote device becomes available (true). Only available on Android. | <code>false</code> | 7.1.0 |
| **`deviceId`**    | <code>string</code>  | The address of the device to connect to.                                                                                                                               |                    | 6.0.0 |
| **`timeout`**     | <code>number</code>  | The timeout for the connect operation in milliseconds. If the operation takes longer than this value, the promise will be rejected.                                    | <code>10000</code> | 6.0.0 |


#### CreateBondOptions

| Prop           | Type                | Description                                                                                                                             | Default            | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to create a bond with.                                                                                        |                    | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the create bond operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>10000</code> | 6.0.0 |


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

| Prop              | Type                                                                            | Description                                                                                                            | Since |
| ----------------- | ------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- | ----- |
| **`id`**          | <code>string</code>                                                             | The UUID of the characteristic.                                                                                        | 6.0.0 |
| **`descriptors`** | <code>Descriptor[]</code>                                                       | The descriptors of the characteristic. **Note**: This property is currently ignored when advertising a characteristic. | 6.0.0 |
| **`permissions`** | <code><a href="#characteristicpermissions">CharacteristicPermissions</a></code> | The permissions of the characteristic. Only available on Android.                                                      | 7.2.0 |
| **`properties`**  | <code><a href="#characteristicproperties">CharacteristicProperties</a></code>   | The properties of the characteristic.                                                                                  | 6.0.0 |


#### Descriptor

| Prop     | Type                | Description                 | Since |
| -------- | ------------------- | --------------------------- | ----- |
| **`id`** | <code>string</code> | The UUID of the descriptor. | 6.0.0 |


#### CharacteristicPermissions

| Prop                     | Type                 | Description                                                                                                      | Since |
| ------------------------ | -------------------- | ---------------------------------------------------------------------------------------------------------------- | ----- |
| **`read`**               | <code>boolean</code> | Whether or not the characteristic can be read.                                                                   | 7.2.0 |
| **`readEncrypted`**      | <code>boolean</code> | Whether or not the characteristic can be read with encryption.                                                   | 7.2.0 |
| **`readEncryptedMitm`**  | <code>boolean</code> | Whether or not the characteristic can be read with encryption and MITM protection. Only available on Android.    | 7.2.0 |
| **`write`**              | <code>boolean</code> | Whether or not the characteristic can be written.                                                                | 7.2.0 |
| **`writeEncrypted`**     | <code>boolean</code> | Whether or not the characteristic can be written with encryption.                                                | 7.2.0 |
| **`writeEncryptedMitm`** | <code>boolean</code> | Whether or not the characteristic can be written with encryption and MITM protection. Only available on Android. | 7.2.0 |
| **`writeSigned`**        | <code>boolean</code> | Whether or not the characteristic can be written signed. Only available on Android.                              | 7.2.0 |
| **`writeSignedMitm`**    | <code>boolean</code> | Whether or not the characteristic can be written signed with encryption. Only available on Android.              | 7.2.0 |


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


#### InitializeOptions

| Prop       | Type                                   | Description                                                         | Default                | Since |
| ---------- | -------------------------------------- | ------------------------------------------------------------------- | ---------------------- | ----- |
| **`mode`** | <code>'central' \| 'peripheral'</code> | The mode of the Bluetooth Low Energy plugin. Only available on iOS. | <code>'central'</code> | 7.2.0 |


#### IsBondedResult

| Prop         | Type                 | Description                          | Since |
| ------------ | -------------------- | ------------------------------------ | ----- |
| **`bonded`** | <code>boolean</code> | Whether or not the device is bonded. | 6.0.0 |


#### IsBondedOptions

| Prop           | Type                | Description                                                                                                                           | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to check if it is bonded.                                                                                   | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the is bonded operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | 6.0.0 |


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


#### RequestConnectionPriorityOptions

| Prop                     | Type                                                              | Description                                                                                                                                             | Since |
| ------------------------ | ----------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`deviceId`**           | <code>string</code>                                               | The address of the device to request the connection priority for.                                                                                       | 6.0.0 |
| **`connectionPriority`** | <code><a href="#connectionpriority">ConnectionPriority</a></code> | The connection priority to request.                                                                                                                     | 6.0.0 |
| **`timeout`**            | <code>number</code>                                               | The timeout for the request connection priority operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | 6.0.0 |


#### RequestMtuOptions

| Prop           | Type                | Description                                                                                                                             | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`deviceId`** | <code>string</code> | The address of the device to request the MTU size for.                                                                                  | 6.0.0 |
| **`mtu`**      | <code>number</code> | The mtu size to request.                                                                                                                | 6.0.0 |
| **`timeout`**  | <code>number</code> | The timeout for the request MTU operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | 6.0.0 |


#### SetCharacteristicValueOptions

| Prop                   | Type                  | Description                                          | Since |
| ---------------------- | --------------------- | ---------------------------------------------------- | ----- |
| **`characteristicId`** | <code>string</code>   | The UUID of the characteristic to set the value for. | 7.2.0 |
| **`serviceId`**        | <code>string</code>   | The UUID of the service to set the value for.        | 7.2.0 |
| **`value`**            | <code>number[]</code> | The value bytes to set for the characteristic.       | 7.2.0 |


#### StartAdvertisingOptions

| Prop           | Type                   | Description                                                       | Default                | Since |
| -------------- | ---------------------- | ----------------------------------------------------------------- | ---------------------- | ----- |
| **`name`**     | <code>string</code>    | The name of the local device to advertise. Only available on iOS. | <code>"Unknown"</code> | 7.2.0 |
| **`services`** | <code>Service[]</code> | The services to advertise.                                        |                        | 7.2.0 |


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

| Prop                   | Type                                        | Description                                                                                                                       | Default                | Since |
| ---------------------- | ------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`characteristicId`** | <code>string</code>                         | The UUID of the characteristic to write.                                                                                          |                        | 6.0.0 |
| **`deviceId`**         | <code>string</code>                         | The address of the device to write the characteristic to.                                                                         |                        | 6.0.0 |
| **`serviceId`**        | <code>string</code>                         | The UUID of the service to write the characteristic to.                                                                           |                        | 6.0.0 |
| **`timeout`**          | <code>number</code>                         | The timeout for the write operation in milliseconds. If the operation takes longer than this value, the promise will be rejected. | <code>5000</code>      | 6.0.0 |
| **`type`**             | <code>'default' \| 'withoutResponse'</code> | The type of write operation.                                                                                                      | <code>'default'</code> | 6.1.0 |
| **`value`**            | <code>number[]</code>                       | The value bytes to write to the characteristic.                                                                                   |                        | 6.0.0 |


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

| Prop              | Type                                            | Description                 | Default                                                                                                        |
| ----------------- | ----------------------------------------------- | --------------------------- | -------------------------------------------------------------------------------------------------------------- |
| **`permissions`** | <code>BluetoothLowEnergyPermissionType[]</code> | The permissions to request. | <code>['bluetooth', 'bluetoothAdmin', 'bluetoothConnect', 'bluetoothScan', 'location', 'notifications']</code> |


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


#### CharacteristicWriteRequestEvent

| Prop                   | Type                  | Description                                     | Since |
| ---------------------- | --------------------- | ----------------------------------------------- | ----- |
| **`characteristicId`** | <code>string</code>   | The UUID of the characteristic.                 | 7.2.0 |
| **`serviceId`**        | <code>string</code>   | The address of the device.                      | 7.2.0 |
| **`value`**            | <code>number[]</code> | The value bytes to write to the characteristic. | 7.2.0 |


#### DeviceConnectedEvent

| Prop           | Type                | Description                          | Since |
| -------------- | ------------------- | ------------------------------------ | ----- |
| **`deviceId`** | <code>string</code> | The address of the connected device. | 7.1.0 |
| **`name`**     | <code>string</code> | The name of the connected device.    | 7.1.0 |


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

<code>'bluetooth' | 'bluetoothAdmin' | 'bluetoothAdvertise' | 'bluetoothConnect' | 'bluetoothScan' | 'location' | 'notifications'</code>


### Enums


#### ConnectionPriority

| Members            | Value          | Description                          | Since |
| ------------------ | -------------- | ------------------------------------ | ----- |
| **`BALANCED`**     | <code>0</code> | Balanced connection priority.        | 6.0.0 |
| **`HIGH`**         | <code>1</code> | High connection priority.            | 6.0.0 |
| **`LOW_POWER`**    | <code>2</code> | Low power connection priority.       | 6.0.0 |
| **`PRIORITY_DCK`** | <code>3</code> | Digital Car Key connection priority. | 6.0.0 |

</docgen-api>

## Utils

See [docs/utils/README.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/docs/utils/README.md).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/bluetooth-low-energy/LICENSE).
