# @capawesome-team/capacitor-android-foreground-service

Capacitor plugin to run a foreground service on Android.

## Installation

See [Getting started with Insiders](https://capawesome.io/insiders/getting-started/?plugin=capacitor-android-foreground-service) and follow the instructions to install the plugin.

After that, follow the platform-specific instructions in the section [Android](#android).

### Android

This API requires the following permissions be added to your `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<!-- Required to request the manage overlay permission -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

**Attention**: Replace `FOREGROUND_SERVICE_LOCATION` with the foreground service types you want to use (see [Foreground service types](https://developer.android.com/develop/background-work/services/fg-service-types)).

You also need to add the following receiver and service **inside** the `application` tag in your `AndroidManifest.xml`:

```xml
<receiver android:name="io.capawesome.capacitorjs.plugins.foregroundservice.NotificationActionBroadcastReceiver" />
<service android:name="io.capawesome.capacitorjs.plugins.foregroundservice.AndroidForegroundService" android:foregroundServiceType="location" />
```

**Attention**: Replace `location` with the foreground service types you want to use (see [Foreground service types](https://developer.android.com/develop/background-work/services/fg-service-types)).

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/188438969-fcbf02d3-0db2-4277-8039-ddfb6bce42ae.gif" width="324" /> |

## Usage

```typescript
import { Capacitor } from '@capacitor/core';
import { ForegroundService } from '@capawesome-team/capacitor-android-foreground-service';

const startForegroundService = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return false;
  }
  await ForegroundService.startForegroundService();
};

const stopForegroundService = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return false;
  }
  await ForegroundService.stopForegroundService();
};
```

## API

<docgen-index>

* [`moveToForeground()`](#movetoforeground)
* [`startForegroundService(...)`](#startforegroundservice)
* [`stopForegroundService()`](#stopforegroundservice)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`checkManageOverlayPermission()`](#checkmanageoverlaypermission)
* [`requestManageOverlayPermission()`](#requestmanageoverlaypermission)
* [`addListener('buttonClicked', ...)`](#addlistenerbuttonclicked)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### moveToForeground()

```typescript
moveToForeground() => Promise<void>
```

Moves the app to the foreground.

On Android SDK 23+, the user must grant the manage overlay permission.
You can use the `requestManageOverlayPermission()` method to request the
permission and the `checkManageOverlayPermission()` method to check if the
permission is granted.

Only available on Android.

**Since:** 0.3.0

--------------------


### startForegroundService(...)

```typescript
startForegroundService(options: StartForegroundServiceOptions) => Promise<void>
```

Starts the foreground service.

Only available on Android.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startforegroundserviceoptions">StartForegroundServiceOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopForegroundService()

```typescript
stopForegroundService() => Promise<void>
```

Stops the foreground service.

Only available on Android.

**Since:** 0.0.1

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permission to display notifications.

On **Android**, this method only needs to be called on Android 13+.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 5.0.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to display notifications.

On **Android**, this method only needs to be called on Android 13+.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 5.0.0

--------------------


### checkManageOverlayPermission()

```typescript
checkManageOverlayPermission() => Promise<ManageOverlayPermissionResult>
```

Check if the overlay permission is granted.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#manageoverlaypermissionresult">ManageOverlayPermissionResult</a>&gt;</code>

**Since:** 0.3.0

--------------------


### requestManageOverlayPermission()

```typescript
requestManageOverlayPermission() => Promise<ManageOverlayPermissionResult>
```

Request the manage overlay permission.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#manageoverlaypermissionresult">ManageOverlayPermissionResult</a>&gt;</code>

**Since:** 0.3.0

--------------------


### addListener('buttonClicked', ...)

```typescript
addListener(eventName: 'buttonClicked', listenerFunc: ButtonClickedEventListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Called when a notification button is clicked.

Only available on iOS.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'buttonClicked'</code>                                                      |
| **`listenerFunc`** | <code><a href="#buttonclickedeventlistener">ButtonClickedEventListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 0.2.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.2.0

--------------------


### Interfaces


#### StartForegroundServiceOptions

| Prop            | Type                              | Description                                                                                                                                                                                                     | Since |
| --------------- | --------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`body`**      | <code>string</code>               | The body of the notification, shown below the title.                                                                                                                                                            | 0.0.1 |
| **`buttons`**   | <code>NotificationButton[]</code> | The buttons to show on the notification. Only available on Android (SDK 24+).                                                                                                                                   | 0.2.0 |
| **`id`**        | <code>number</code>               | The notification identifier.                                                                                                                                                                                    | 0.0.1 |
| **`smallIcon`** | <code>string</code>               | The status bar icon for the notification. Icons should be placed in your app's `res/drawable` folder. The value for this option should be the drawable resource ID, which is the filename without an extension. | 0.0.1 |
| **`title`**     | <code>string</code>               | The title of the notification.                                                                                                                                                                                  | 0.0.1 |


#### NotificationButton

| Prop        | Type                | Description                                                                                           | Since |
| ----------- | ------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`title`** | <code>string</code> | The button title.                                                                                     | 0.2.0 |
| **`id`**    | <code>number</code> | The button identifier. This is used to identify the button when the `buttonClicked` event is emitted. | 0.2.0 |


#### PermissionStatus

| Prop          | Type                                                        | Description                                   | Since |
| ------------- | ----------------------------------------------------------- | --------------------------------------------- | ----- |
| **`display`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state of displaying notifications. | 5.0.0 |


#### ManageOverlayPermissionResult

| Prop          | Type                 | Description                                                                      | Since |
| ------------- | -------------------- | -------------------------------------------------------------------------------- | ----- |
| **`granted`** | <code>boolean</code> | Whether the permission is granted. This is always `true` on Android SDK &lt; 23. | 0.3.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ButtonClickedEvent

| Prop           | Type                | Description            | Since |
| -------------- | ------------------- | ---------------------- | ----- |
| **`buttonId`** | <code>number</code> | The button identifier. | 0.2.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ButtonClickedEventListener

<code>(event: <a href="#buttonclickedevent">ButtonClickedEvent</a>): void</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-foreground-service/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-foreground-service/LICENSE).
