# Capacitor Flic Plugin

Capacitor plugin to connect and interact with [Flic](https://flic.io/) smart buttons.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔍 **Pairing wizard**: Pair new Flic buttons with a single call using the integrated scan wizard.
- 🔗 **Connection management**: Connect, disconnect and forget buttons at any time.
- 🖱️ **Button events**: Listen for single click, double click, hold, down and up events.
- 📥 **Queued events**: Receive events that occurred while the button was disconnected.
- 🔋 **Battery level**: Read the last known battery voltage of each button.
- 🔐 **Permissions**: Check and request the required permissions with a single call.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Flic plugin is typically used to trigger app actions with a physical button, for example:

- **Smart home control**: Toggle lights, scenes or other devices with a single press.
- **Safety buttons**: Send an alert or start a call when the user presses the button.
- **Time tracking**: Start and stop timers without opening the app.
- **Accessibility**: Provide a simple physical trigger for essential app actions.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-flic` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-flic
npx cap sync
```

### Android

The plugin already declares all required Bluetooth permissions in its manifest, so no additional configuration is required.

The [flic2lib-android](https://github.com/50ButtonsEach/flic2lib-android) library is resolved from [JitPack](https://jitpack.io/). The plugin already declares the JitPack repository in its `build.gradle` file. If your project restricts repository declarations to the settings file (e.g. via `dependencyResolutionManagement` with `FAIL_ON_PROJECT_REPOS`), add the JitPack repository to your `settings.gradle` file:

```groovy
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$flic2libVersion` version of `com.github.50ButtonsEach:flic2lib-android` (default: `2.0.1`)

### iOS

Add the `NSBluetoothAlwaysUsageDescription` and `NSBluetoothPeripheralUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why the app needs access to Bluetooth:

```xml
<key>NSBluetoothAlwaysUsageDescription</key>
<string>The app needs access to Bluetooth to communicate with your Flic buttons.</string>
<key>NSBluetoothPeripheralUsageDescription</key>
<string>The app needs access to Bluetooth to communicate with your Flic buttons.</string>
```

If you want to receive button events while the app is in the background, you must also enable the `Uses Bluetooth LE accessories` background mode in the **Signing & Capabilities** section of your Xcode project and call `initialize(...)` with the `iosBackground` option set to `true`.

## Configuration

No configuration required for this plugin.

## Usage

### Initialize the plugin

Call `initialize()` as soon as possible after the app has launched to minimize the delay of any pending button events:

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const initialize = async () => {
  await Flic.initialize({ iosBackground: true });
};
```

### Pair a new button

To pair a new button, start a scan and press and hold the button for at least 6 seconds:

```typescript
import { Flic, ScanStatus } from '@capawesome/capacitor-flic';

const pairButton = async () => {
  await Flic.addListener('scanStatusChanged', (event) => {
    if (event.status === ScanStatus.Discovered) {
      console.log('Button discovered. Keep holding it...');
    }
  });
  const { button } = await Flic.startScan();
  return button;
};
```

### List paired buttons

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const getButtons = async () => {
  const { buttons } = await Flic.getButtons();
  return buttons;
};
```

### Connect a button

After an app restart, each paired button must be connected again:

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const connectButtons = async () => {
  const { buttons } = await Flic.getButtons();
  for (const button of buttons) {
    await Flic.connectButtonById({ id: button.id });
  }
};
```

### Listen for button events

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const addListeners = async () => {
  await Flic.addListener('buttonSingleClick', (event) => {
    console.log('Button was clicked', event);
  });
  await Flic.addListener('buttonDoubleClick', (event) => {
    console.log('Button was double clicked', event);
  });
  await Flic.addListener('buttonHold', (event) => {
    console.log('Button was held down', event);
  });
};
```

### Forget a button

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const forgetButton = async (id: string) => {
  await Flic.forgetButtonById({ id });
};
```

### Check and request permissions

```typescript
import { Flic } from '@capawesome/capacitor-flic';

const checkPermissions = async () => {
  const permissionStatus = await Flic.checkPermissions();
  return permissionStatus;
};

const requestPermissions = async () => {
  const permissionStatus = await Flic.requestPermissions();
  return permissionStatus;
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`connectButtonById(...)`](#connectbuttonbyid)
* [`disconnectButtonById(...)`](#disconnectbuttonbyid)
* [`forgetButtonById(...)`](#forgetbuttonbyid)
* [`getButtons()`](#getbuttons)
* [`initialize(...)`](#initialize)
* [`requestPermissions()`](#requestpermissions)
* [`startScan()`](#startscan)
* [`stopScan()`](#stopscan)
* [`addListener('buttonConnected', ...)`](#addlistenerbuttonconnected-)
* [`addListener('buttonConnectionFailed', ...)`](#addlistenerbuttonconnectionfailed-)
* [`addListener('buttonDisconnected', ...)`](#addlistenerbuttondisconnected-)
* [`addListener('buttonDoubleClick', ...)`](#addlistenerbuttondoubleclick-)
* [`addListener('buttonDown', ...)`](#addlistenerbuttondown-)
* [`addListener('buttonHold', ...)`](#addlistenerbuttonhold-)
* [`addListener('buttonReady', ...)`](#addlistenerbuttonready-)
* [`addListener('buttonSingleClick', ...)`](#addlistenerbuttonsingleclick-)
* [`addListener('buttonUnpaired', ...)`](#addlistenerbuttonunpaired-)
* [`addListener('buttonUp', ...)`](#addlistenerbuttonup-)
* [`addListener('scanStatusChanged', ...)`](#addlistenerscanstatuschanged-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the status of the permissions that are required to use Flic buttons.

On iOS, the Bluetooth permission is requested when `initialize(...)` is called.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### connectButtonById(...)

```typescript
connectButtonById(options: ConnectButtonByIdOptions) => Promise<void>
```

Connect a button.

The returned promise resolves immediately. The connection is established
as soon as the button is available and does not time out. Listen to the
`buttonConnected` and `buttonReady` events to know when the button is
ready to be used.

Only available on Android and iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#connectbuttonbyidoptions">ConnectButtonByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### disconnectButtonById(...)

```typescript
disconnectButtonById(options: DisconnectButtonByIdOptions) => Promise<void>
```

Disconnect a button or cancel a pending connection.

Only available on Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#disconnectbuttonbyidoptions">DisconnectButtonByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### forgetButtonById(...)

```typescript
forgetButtonById(options: ForgetButtonByIdOptions) => Promise<void>
```

Forget a button.

This removes the pairing with the button. To use the button again,
it must be paired again using `startScan()`.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#forgetbuttonbyidoptions">ForgetButtonByIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### getButtons()

```typescript
getButtons() => Promise<GetButtonsResult>
```

Get all buttons that are currently paired with the app.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getbuttonsresult">GetButtonsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initialize the plugin.

This method must be called before any other method.
It is recommended to call this method as soon as possible
after the app has launched to minimize the delay of any
pending button events.

On iOS, this method triggers the Bluetooth permission prompt
if the permission has not yet been granted.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request the permissions that are required to use Flic buttons.

On iOS, this method only returns the current permission status since
the Bluetooth permission is requested when `initialize(...)` is called.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### startScan()

```typescript
startScan() => Promise<StartScanResult>
```

Start scanning for new buttons to pair.

To pair a button, press and hold it for at least 6 seconds while scanning.
The returned promise resolves with the paired button once the pairing
has completed. Listen to the `scanStatusChanged` event to keep the user
informed about the scan progress.

Only one scan can be running at a time.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#startscanresult">StartScanResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

Stop an ongoing scan.

This rejects the pending `startScan()` call.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### addListener('buttonConnected', ...)

```typescript
addListener(eventName: 'buttonConnected', listenerFunc: (event: ButtonConnectedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button establishes a Bluetooth connection.

The button is not ready to be used until the `buttonReady` event is emitted.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonConnected'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#buttonconnectedevent">ButtonConnectedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonConnectionFailed', ...)

```typescript
addListener(eventName: 'buttonConnectionFailed', listenerFunc: (event: ButtonConnectionFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a connection attempt to a button fails.

Only available on Android and iOS.

| Param              | Type                                                                                                    |
| ------------------ | ------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonConnectionFailed'</code>                                                                   |
| **`listenerFunc`** | <code>(event: <a href="#buttonconnectionfailedevent">ButtonConnectionFailedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonDisconnected', ...)

```typescript
addListener(eventName: 'buttonDisconnected', listenerFunc: (event: ButtonDisconnectedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Bluetooth connection with a button is lost.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonDisconnected'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#buttondisconnectedevent">ButtonDisconnectedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonDoubleClick', ...)

```typescript
addListener(eventName: 'buttonDoubleClick', listenerFunc: (event: ButtonEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button is double clicked.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonDoubleClick'</code>                                        |
| **`listenerFunc`** | <code>(event: <a href="#buttonevent">ButtonEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonDown', ...)

```typescript
addListener(eventName: 'buttonDown', listenerFunc: (event: ButtonEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button is pressed down.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonDown'</code>                                               |
| **`listenerFunc`** | <code>(event: <a href="#buttonevent">ButtonEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonHold', ...)

```typescript
addListener(eventName: 'buttonHold', listenerFunc: (event: ButtonEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button is held down.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonHold'</code>                                               |
| **`listenerFunc`** | <code>(event: <a href="#buttonevent">ButtonEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonReady', ...)

```typescript
addListener(eventName: 'buttonReady', listenerFunc: (event: ButtonReadyEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button has been cryptographically verified after a connection
and is ready to be used.

Only available on Android and iOS.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonReady'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#buttonreadyevent">ButtonReadyEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonSingleClick', ...)

```typescript
addListener(eventName: 'buttonSingleClick', listenerFunc: (event: ButtonEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button is clicked once.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonSingleClick'</code>                                        |
| **`listenerFunc`** | <code>(event: <a href="#buttonevent">ButtonEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonUnpaired', ...)

```typescript
addListener(eventName: 'buttonUnpaired', listenerFunc: (event: ButtonUnpairedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the pairing with a button is no longer valid, for example
because the button has been factory reset. In this case, the button must
be forgotten using `forgetButtonById(...)` and then paired again.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonUnpaired'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#buttonunpairedevent">ButtonUnpairedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('buttonUp', ...)

```typescript
addListener(eventName: 'buttonUp', listenerFunc: (event: ButtonEvent) => void) => Promise<PluginListenerHandle>
```

Called when a button is released.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonUp'</code>                                                 |
| **`listenerFunc`** | <code>(event: <a href="#buttonevent">ButtonEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('scanStatusChanged', ...)

```typescript
addListener(eventName: 'scanStatusChanged', listenerFunc: (event: ScanStatusChangedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the status of an ongoing scan changes.

Only available on Android and iOS.

| Param              | Type                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanStatusChanged'</code>                                                              |
| **`listenerFunc`** | <code>(event: <a href="#scanstatuschangedevent">ScanStatusChangedEvent</a>) =&gt; void</code> |

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


#### PermissionStatus

| Prop                   | Type                                                        | Description                                                                                                                        | Since |
| ---------------------- | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bluetooth`**        | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of the Bluetooth permission. Only available on iOS.                                                           | 0.1.0 |
| **`bluetoothConnect`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of the `BLUETOOTH_CONNECT` permission. Only required on Android 12 and later. Only available on Android.      | 0.1.0 |
| **`bluetoothScan`**    | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of the `BLUETOOTH_SCAN` permission. Only required on Android 12 and later. Only available on Android.         | 0.1.0 |
| **`location`**         | <code><a href="#permissionstate">PermissionState</a></code> | The permission state of the `ACCESS_FINE_LOCATION` permission. Only required on Android 11 and earlier. Only available on Android. | 0.1.0 |


#### ConnectButtonByIdOptions

| Prop     | Type                | Description                   | Since |
| -------- | ------------------- | ----------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### DisconnectButtonByIdOptions

| Prop     | Type                | Description                   | Since |
| -------- | ------------------- | ----------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### ForgetButtonByIdOptions

| Prop     | Type                | Description                   | Since |
| -------- | ------------------- | ----------------------------- | ----- |
| **`id`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### GetButtonsResult

| Prop          | Type                  | Description                                         | Since |
| ------------- | --------------------- | --------------------------------------------------- | ----- |
| **`buttons`** | <code>Button[]</code> | The buttons that are currently paired with the app. | 0.1.0 |


#### Button

| Prop                  | Type                                                                    | Description                                                                                                                                                                                                                                | Since |
| --------------------- | ----------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`batteryVoltage`**  | <code>number</code>                                                     | The last known battery voltage of the button in volts. If no battery sample has been taken yet, this property is not available. It is recommended to show a "change the battery soon" hint in your app once the voltage goes below `2.65`. | 0.1.0 |
| **`connectionState`** | <code><a href="#buttonconnectionstate">ButtonConnectionState</a></code> | The connection state of the button.                                                                                                                                                                                                        | 0.1.0 |
| **`firmwareVersion`** | <code>number</code>                                                     | The revision of the firmware currently running on the button.                                                                                                                                                                              | 0.1.0 |
| **`id`**              | <code>string</code>                                                     | The identifier of the button on this device. On Android, this is the Bluetooth address of the button. On iOS, this is an identifier that is guaranteed to be the same for each button paired to a particular device.                       | 0.1.0 |
| **`isReady`**         | <code>boolean</code>                                                    | Whether or not the button has been cryptographically verified after a connection and is ready to be used.                                                                                                                                  | 0.1.0 |
| **`isUnpaired`**      | <code>boolean</code>                                                    | Whether or not the pairing with the button is no longer valid, for example because the button has been factory reset. In this case, the button must be forgotten using `forgetButtonById(...)` and then paired again.                      | 0.1.0 |
| **`name`**            | <code>string</code>                                                     | The human readable name of the button that the user may change in the official Flic app.                                                                                                                                                   | 0.1.0 |
| **`pressCount`**      | <code>number</code>                                                     | The number of times the button has been clicked since it last booted.                                                                                                                                                                      | 0.1.0 |
| **`serialNumber`**    | <code>string</code>                                                     | The serial number of the button that is printed on the backside of the button inside the battery hatch.                                                                                                                                    | 0.1.0 |
| **`uuid`**            | <code>string</code>                                                     | The unique identifier of the button that is the same across devices and apps.                                                                                                                                                              | 0.1.0 |


#### InitializeOptions

| Prop                | Type                 | Description                                                                                                                                                                                                              | Default            | Since |
| ------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------ | ----- |
| **`iosBackground`** | <code>boolean</code> | Whether or not you intend to use the buttons while the app is in the background. If set to `true`, the `Uses Bluetooth LE accessories` background mode must be enabled in the app's capabilities. Only available on iOS. | <code>false</code> | 0.1.0 |


#### StartScanResult

| Prop         | Type                                      | Description                 | Since |
| ------------ | ----------------------------------------- | --------------------------- | ----- |
| **`button`** | <code><a href="#button">Button</a></code> | The button that was paired. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ButtonConnectedEvent

| Prop           | Type                | Description                   | Since |
| -------------- | ------------------- | ----------------------------- | ----- |
| **`buttonId`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### ButtonConnectionFailedEvent

| Prop           | Type                | Description                                                   | Since |
| -------------- | ------------------- | ------------------------------------------------------------- | ----- |
| **`buttonId`** | <code>string</code> | The identifier of the button.                                 | 0.1.0 |
| **`message`**  | <code>string</code> | The message that describes why the connection attempt failed. | 0.1.0 |


#### ButtonDisconnectedEvent

| Prop           | Type                | Description                   | Since |
| -------------- | ------------------- | ----------------------------- | ----- |
| **`buttonId`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### ButtonEvent

| Prop            | Type                 | Description                                                                              | Since |
| --------------- | -------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`buttonId`**  | <code>string</code>  | The identifier of the button.                                                            | 0.1.0 |
| **`timestamp`** | <code>number</code>  | The timestamp of the event in milliseconds since the Unix epoch.                         | 0.1.0 |
| **`wasQueued`** | <code>boolean</code> | Whether or not the event was queued because it occurred before the button was connected. | 0.1.0 |


#### ButtonReadyEvent

| Prop           | Type                | Description                   | Since |
| -------------- | ------------------- | ----------------------------- | ----- |
| **`buttonId`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### ButtonUnpairedEvent

| Prop           | Type                | Description                   | Since |
| -------------- | ------------------- | ----------------------------- | ----- |
| **`buttonId`** | <code>string</code> | The identifier of the button. | 0.1.0 |


#### ScanStatusChangedEvent

| Prop         | Type                                              | Description             | Since |
| ------------ | ------------------------------------------------- | ----------------------- | ----- |
| **`status`** | <code><a href="#scanstatus">ScanStatus</a></code> | The status of the scan. | 0.1.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### ButtonConnectionState

| Members             | Value                        | Description                                                                                                          | Since |
| ------------------- | ---------------------------- | -------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Connected`**     | <code>'CONNECTED'</code>     | The button is connected.                                                                                             | 0.1.0 |
| **`Connecting`**    | <code>'CONNECTING'</code>    | The button is disconnected but a pending connection is set. The button will connect as soon as it becomes available. | 0.1.0 |
| **`Disconnected`**  | <code>'DISCONNECTED'</code>  | The button is disconnected and no pending connection is set.                                                         | 0.1.0 |
| **`Disconnecting`** | <code>'DISCONNECTING'</code> | The button is connected but is attempting to disconnect. Only available on iOS.                                      | 0.1.0 |


#### ScanStatus

| Members                      | Value                                     | Description                                                                            | Since |
| ---------------------------- | ----------------------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`AskToAcceptPairRequest`** | <code>'ASK_TO_ACCEPT_PAIR_REQUEST'</code> | The user must accept the system pairing dialog to continue. Only available on Android. | 0.1.0 |
| **`Connected`**              | <code>'CONNECTED'</code>                  | A button was found and a connection is being established.                              | 0.1.0 |
| **`Discovered`**             | <code>'DISCOVERED'</code>                 | A button was discovered.                                                               | 0.1.0 |
| **`Verified`**               | <code>'VERIFIED'</code>                   | The button has been cryptographically verified. Only available on iOS.                 | 0.1.0 |

</docgen-api>

## FAQ

### Which Flic buttons are supported?

The plugin supports Flic 2 buttons. For the Flic Duo, only the events of the big button are currently delivered.

### Does the plugin receive button events while the app is in the background?

On Android, events are delivered as long as the app process is alive. Consider using the [Android Foreground Service](https://capawesome.io/docs/sdks/capacitor/android-foreground-service/) plugin to keep the app process alive. On iOS, enable the `Uses Bluetooth LE accessories` background mode and call `initialize(...)` with the `iosBackground` option set to `true` (see [Installation](#installation)).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Android Foreground Service](https://capawesome.io/docs/sdks/capacitor/android-foreground-service/): Keep the app process alive to receive button events in the background on Android.
- [Bluetooth Low Energy](https://capawesome.io/docs/sdks/capacitor/bluetooth-low-energy/): Communicate with other Bluetooth Low Energy devices.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/flic/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/flic/LICENSE).

The bundled [flic2lib](https://github.com/50ButtonsEach/flic2lib-ios) binary is licensed under the terms of Shortcut Labs AB (see [flic2lib-LICENCE.txt](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/flic/ios/flic2lib-LICENCE.txt)).
