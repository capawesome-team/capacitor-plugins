# Capacitor Permissions Plugin

Capacitor plugin to check and request device permissions with a unified API.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🖥️ **Cross-platform**: Supports Android, iOS and Web (partial).
- 🧩 **Unified API**: Check and request many different permissions with a single API instead of installing a feature plugin for each one.
- 🗂️ **Curated catalog**: Supports Bluetooth, calendar, camera, contacts, location, background location, microphone, motion, notifications, photos and reminders.
- 🔍 **Prompt-free checks**: The `check(...)` method never triggers a permission prompt.
- 🪶 **Lightweight**: The plugin does not declare any permissions itself. Your app only declares the permissions it actually uses.
- 🤝 **Compatibility**: Works alongside the [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) and [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Permissions plugin is typically used whenever an app needs to manage several device permissions in one place, for example:

- **Onboarding screens**: Check the states of all permissions your app uses with the prompt-free `check(...)` method and present them in a single overview.
- **Contextual permission requests**: Request the camera or microphone permission right before the user starts a video call or recording.
- **Background location upgrades**: Request the `LOCATION` permission first and upgrade to `LOCATION_ALWAYS` afterwards, as recommended by the platforms.
- **Recovering from denied permissions**: Detect the `denied` state and guide the user to the app settings to re-enable the permission.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-permissions` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-permissions
npx cap sync
```

**Attention**: The plugin itself does **not** declare any permissions and does **not** require any Info.plist keys. Your app must declare exactly the permissions it actually uses (see below). This way, your app does not request permissions it does not need.

### Android

#### Permissions

Add the manifest entries for the permissions you want to check or request to your `AndroidManifest.xml` file before or after the `application` tag. If a permission is not declared, the `request(...)` method rejects with a clear error message.

| Permission        | Required manifest entries                                                                                                     |
| ----------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| `BLUETOOTH`       | `android.permission.BLUETOOTH_SCAN`, `android.permission.BLUETOOTH_CONNECT`                                                    |
| `CALENDAR`        | `android.permission.READ_CALENDAR`, `android.permission.WRITE_CALENDAR`                                                        |
| `CAMERA`          | `android.permission.CAMERA`                                                                                                    |
| `CONTACTS`        | `android.permission.READ_CONTACTS`, `android.permission.WRITE_CONTACTS`                                                        |
| `LOCATION`        | `android.permission.ACCESS_COARSE_LOCATION`, `android.permission.ACCESS_FINE_LOCATION`                                         |
| `LOCATION_ALWAYS` | `android.permission.ACCESS_BACKGROUND_LOCATION` (plus the `LOCATION` entries)                                                  |
| `MICROPHONE`      | `android.permission.RECORD_AUDIO`                                                                                               |
| `MOTION`          | `android.permission.ACTIVITY_RECOGNITION`                                                                                       |
| `NOTIFICATIONS`   | `android.permission.POST_NOTIFICATIONS`                                                                                         |
| `PHOTOS`          | `android.permission.READ_MEDIA_IMAGES`, `android.permission.READ_MEDIA_VISUAL_USER_SELECTED`, `android.permission.READ_EXTERNAL_STORAGE` (max SDK version `32`) |
| `REMINDERS`       | Not available on Android.                                                                                                       |

**Example**:

```xml
<!-- Required if you want to use the `BLUETOOTH` permission. -->
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" android:usesPermissionFlags="neverForLocation" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<!-- Required if you want to use the `CALENDAR` permission. -->
<uses-permission android:name="android.permission.READ_CALENDAR" />
<uses-permission android:name="android.permission.WRITE_CALENDAR" />
<!-- Required if you want to use the `CAMERA` permission. -->
<uses-permission android:name="android.permission.CAMERA" />
<!-- Required if you want to use the `CONTACTS` permission. -->
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.WRITE_CONTACTS" />
<!-- Required if you want to use the `LOCATION` or `LOCATION_ALWAYS` permission. -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<!-- Required if you want to use the `LOCATION_ALWAYS` permission. -->
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<!-- Required if you want to use the `MICROPHONE` permission. -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<!-- Required if you want to use the `MOTION` permission. -->
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
<!-- Required if you want to use the `NOTIFICATIONS` permission. -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<!-- Required if you want to use the `PHOTOS` permission. -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_MEDIA_VISUAL_USER_SELECTED" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />
```

### iOS

#### Privacy Descriptions

Add the usage description keys for the permissions you want to request to the `ios/App/App/Info.plist` file. If a key is missing, the `request(...)` method rejects with a clear error message.

| Permission        | Required Info.plist keys                                                                    |
| ----------------- | ------------------------------------------------------------------------------------------- |
| `BLUETOOTH`       | `NSBluetoothAlwaysUsageDescription`                                                          |
| `CALENDAR`        | `NSCalendarsFullAccessUsageDescription` (iOS 17+), `NSCalendarsUsageDescription` (iOS 16 and older) |
| `CAMERA`          | `NSCameraUsageDescription`                                                                   |
| `CONTACTS`        | `NSContactsUsageDescription`                                                                 |
| `LOCATION`        | `NSLocationWhenInUseUsageDescription`                                                        |
| `LOCATION_ALWAYS` | `NSLocationAlwaysAndWhenInUseUsageDescription`, `NSLocationWhenInUseUsageDescription`        |
| `MICROPHONE`      | `NSMicrophoneUsageDescription`                                                               |
| `MOTION`          | `NSMotionUsageDescription`                                                                   |
| `NOTIFICATIONS`   | None                                                                                         |
| `PHOTOS`          | `NSPhotoLibraryUsageDescription`                                                             |
| `REMINDERS`       | `NSRemindersFullAccessUsageDescription` (iOS 17+), `NSRemindersUsageDescription` (iOS 16 and older) |

**Example**:

```xml
<key>NSCameraUsageDescription</key>
<string>The camera is used to take photos.</string>
<key>NSMicrophoneUsageDescription</key>
<string>The microphone is used to record audio.</string>
```

**Note**: Since the plugin references the system frameworks of all supported permissions, App Store Connect may ask you to provide additional usage descriptions when you upload your app, even if you do not request those permissions.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check the states of one or more permissions and how to request them from the user.

### Check the states of one or more permissions

Use the `check(...)` method to read the current states of one or more permissions. This method never displays a permission prompt, so it is safe to call at any time, for example to build an onboarding or settings screen:

```typescript
import { Permission, Permissions } from '@capawesome/capacitor-permissions';

const checkPermissions = async () => {
  const { statuses } = await Permissions.check({
    permissions: [Permission.Camera, Permission.Microphone],
  });
  return statuses;
};
```

### Request one or more permissions

Use the `request(...)` method to prompt the user for one or more permissions. Permissions that are already granted or that cannot be requested on the current platform are not requested again; in that case, the current state is returned. On the web, only the `NOTIFICATIONS` permission can be requested:

```typescript
import { Permission, Permissions } from '@capawesome/capacitor-permissions';

const requestPermissions = async () => {
  const { statuses } = await Permissions.request({
    permissions: [Permission.Camera, Permission.Microphone],
  });
  return statuses;
};
```

## API

<docgen-index>

* [`check(...)`](#check)
* [`request(...)`](#request)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### check(...)

```typescript
check(options: CheckOptions) => Promise<CheckResult>
```

Check the current states of one or more permissions.

This method never displays a permission prompt.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#checkoptions">CheckOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#checkresult">CheckResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### request(...)

```typescript
request(options: RequestOptions) => Promise<RequestResult>
```

Request one or more permissions from the user.

Permissions that are already granted or that cannot be requested
on the current platform are not requested again. In this case,
the current state of the permission is returned.

On Android, the corresponding permissions must be declared in the
`AndroidManifest.xml` file of your app. Otherwise, the call is rejected.

On iOS, the corresponding usage descriptions must be provided in the
`Info.plist` file of your app. Otherwise, the call is rejected.

On the web, only the `NOTIFICATIONS` permission can be requested.
For all other permissions, the current state is returned since
browsers display the permission prompt when the corresponding
web API is used for the first time.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#requestoptions">RequestOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#requestresult">RequestResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CheckResult

| Prop           | Type                            | Description                                                                          | Since |
| -------------- | ------------------------------- | ------------------------------------------------------------------------------------ | ----- |
| **`statuses`** | <code>PermissionStatus[]</code> | The states of the checked permissions in the same order as the provided permissions. | 0.1.0 |


#### PermissionStatus

| Prop             | Type                                                        | Description                                   | Since |
| ---------------- | ----------------------------------------------------------- | --------------------------------------------- | ----- |
| **`permission`** | <code><a href="#permission">Permission</a></code>           | The permission that was checked or requested. | 0.1.0 |
| **`state`**      | <code><a href="#permissionstate">PermissionState</a></code> | The state of the permission.                  | 0.1.0 |


#### CheckOptions

| Prop              | Type                      | Description               | Since |
| ----------------- | ------------------------- | ------------------------- | ----- |
| **`permissions`** | <code>Permission[]</code> | The permissions to check. | 0.1.0 |


#### RequestResult

| Prop           | Type                            | Description                                                                            | Since |
| -------------- | ------------------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`statuses`** | <code>PermissionStatus[]</code> | The states of the requested permissions in the same order as the provided permissions. | 0.1.0 |


#### RequestOptions

| Prop              | Type                      | Description                 | Since |
| ----------------- | ------------------------- | --------------------------- | ----- |
| **`permissions`** | <code>Permission[]</code> | The permissions to request. | 0.1.0 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### Permission

| Members              | Value                          | Description                                                                                                                                                                                                                                                                                                        | Since |
| -------------------- | ------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`Bluetooth`**      | <code>'BLUETOOTH'</code>       | <a href="#permission">Permission</a> to use Bluetooth. On Android 12 (API level 31) and later, this covers the `BLUETOOTH_SCAN` and `BLUETOOTH_CONNECT` permissions. On older Android versions, no runtime permission is required and the state is always `granted`. On the web, this permission is not available. | 0.1.0 |
| **`Calendar`**       | <code>'CALENDAR'</code>        | <a href="#permission">Permission</a> to read and write calendar events. On iOS 17 and later, write-only access is reported as `limited`. On the web, this permission is not available.                                                                                                                             | 0.1.0 |
| **`Camera`**         | <code>'CAMERA'</code>          | <a href="#permission">Permission</a> to use the camera.                                                                                                                                                                                                                                                            | 0.1.0 |
| **`Contacts`**       | <code>'CONTACTS'</code>        | <a href="#permission">Permission</a> to read and write contacts. On iOS 18 and later, limited access is reported as `limited`. On the web, this permission is not available.                                                                                                                                       | 0.1.0 |
| **`Location`**       | <code>'LOCATION'</code>        | <a href="#permission">Permission</a> to access the location while the app is in use. On Android 12 (API level 31) and later, the user may grant only approximate location access. In this case, the state is still reported as `granted`.                                                                          | 0.1.0 |
| **`LocationAlways`** | <code>'LOCATION_ALWAYS'</code> | <a href="#permission">Permission</a> to access the location even while the app is in the background. This permission should only be requested after the `LOCATION` permission has been granted. On the web, this permission is not available.                                                                      | 0.1.0 |
| **`Microphone`**     | <code>'MICROPHONE'</code>      | <a href="#permission">Permission</a> to use the microphone.                                                                                                                                                                                                                                                        | 0.1.0 |
| **`Motion`**         | <code>'MOTION'</code>          | <a href="#permission">Permission</a> to access motion and fitness data. On Android 10 (API level 29) and later, this covers the `ACTIVITY_RECOGNITION` permission. On older Android versions, no runtime permission is required and the state is always `granted`. On the web, this permission is not available.   | 0.1.0 |
| **`Notifications`**  | <code>'NOTIFICATIONS'</code>   | <a href="#permission">Permission</a> to display notifications. On Android 13 (API level 33) and later, this covers the `POST_NOTIFICATIONS` permission. On older Android versions, no runtime permission is required and the state is always `granted`.                                                            | 0.1.0 |
| **`Photos`**         | <code>'PHOTOS'</code>          | <a href="#permission">Permission</a> to access the photo library. On Android 14 (API level 34) and later and on iOS, partial access to the photo library is reported as `limited`. On the web, this permission is not available.                                                                                   | 0.1.0 |
| **`Reminders`**      | <code>'REMINDERS'</code>       | <a href="#permission">Permission</a> to read and write reminders. Only available on iOS.                                                                                                                                                                                                                           | 0.1.0 |

</docgen-api>

## Platform behavior

The following table summarizes the behavior of each permission on each platform:

| Permission        | Android                                                                                                                                           | iOS                                                                                                                        | Web                                                                    |
| ----------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------ |
| `BLUETOOTH`       | Covers `BLUETOOTH_SCAN` and `BLUETOOTH_CONNECT` on Android 12+. On older versions, always `granted`.                                                | Covers the Core Bluetooth authorization.                                                                                      | Always `unavailable`.                                                    |
| `CALENDAR`        | Covers `READ_CALENDAR` and `WRITE_CALENDAR`.                                                                                                        | On iOS 17+, write-only access is reported as `limited`.                                                                       | Always `unavailable`.                                                    |
| `CAMERA`          | Covers `CAMERA`.                                                                                                                                     | Covers the camera authorization.                                                                                              | Check supported. Request returns the current state.                      |
| `CONTACTS`        | Covers `READ_CONTACTS` and `WRITE_CONTACTS`.                                                                                                        | On iOS 18+, limited access is reported as `limited`.                                                                          | Always `unavailable`.                                                    |
| `LOCATION`        | Covers `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`. Reported as `granted` if at least approximate location access has been granted.        | Covers the when-in-use location authorization.                                                                                | Check supported. Request returns the current state.                      |
| `LOCATION_ALWAYS` | Covers `ACCESS_BACKGROUND_LOCATION` on Android 10+. On older versions, behaves like `LOCATION`.                                                     | Covers the always location authorization. iOS only displays the upgrade prompt once.                                          | Always `unavailable`.                                                    |
| `MICROPHONE`      | Covers `RECORD_AUDIO`.                                                                                                                               | Covers the microphone authorization.                                                                                          | Check supported. Request returns the current state.                      |
| `MOTION`          | Covers `ACTIVITY_RECOGNITION` on Android 10+. On older versions, always `granted`.                                                                  | Covers the motion activity authorization. Requires a real device.                                                             | Always `unavailable`.                                                    |
| `NOTIFICATIONS`   | Covers `POST_NOTIFICATIONS` on Android 13+. On older versions, always `granted`.                                                                    | Covers the notifications authorization.                                                                                       | Check and request supported.                                             |
| `PHOTOS`          | Covers `READ_MEDIA_IMAGES` (and `READ_MEDIA_VISUAL_USER_SELECTED`) on Android 13+ and `READ_EXTERNAL_STORAGE` on older versions. Partial access is reported as `limited`. | Partial access is reported as `limited`.                                                                                      | Always `unavailable`.                                                    |
| `REMINDERS`       | Always `unavailable`.                                                                                                                                | Covers the reminders authorization.                                                                                           | Always `unavailable`.                                                    |

## Requesting background location

The `LOCATION_ALWAYS` permission should only be requested after the `LOCATION` permission has been granted:

1. Request the `LOCATION` permission and explain why you need location access.
2. Once granted, request the `LOCATION_ALWAYS` permission.

On Android 11 and later, the system does not display a prompt for background location. Instead, the user is taken to the system settings to select the "Allow all the time" option. If the `LOCATION` permission has not been granted yet, the request is denied immediately by the system.

On iOS, the system only displays the upgrade prompt from when-in-use to always access once. If the user has already made a decision, the current state is returned.

## Recovering from denied permissions

If a permission is in the `denied` state, it can no longer be requested from within the app. In this case, you can guide the user to the app settings using the [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) plugin:

```typescript
import { SettingsLauncher } from '@capawesome/capacitor-settings-launcher';

const openAppSettings = async () => {
  await SettingsLauncher.openAppSettings();
};
```

## App Tracking Transparency

The App Tracking Transparency permission is deliberately not part of this plugin. Use the [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/) plugin instead.

## FAQ

### Does the `check` method trigger a permission prompt?

No, the `check(...)` method never displays a permission prompt. It only reads the current states of the given permissions, so you can safely call it at any time, for example to build an onboarding or settings screen.

### Why does the `request` method reject with an error?

On Android, the corresponding permissions must be declared in the `AndroidManifest.xml` file of your app. On iOS, the corresponding usage description keys must be provided in the `Info.plist` file of your app. If a declaration is missing, the `request(...)` method rejects with a clear error message. See the [Installation](#installation) section for the required entries per permission.

### Do I need to declare all supported permissions in my app?

No, the plugin itself does not declare any permissions and does not require any Info.plist keys. Your app only declares exactly the permissions it actually uses. This way, your app does not request permissions it does not need.

### How do I request background location access?

Request the `LOCATION` permission first and, once it has been granted, request the `LOCATION_ALWAYS` permission. On Android 11 and later, the system does not display a prompt for background location; instead, the user is taken to the system settings to select the "Allow all the time" option. On iOS, the system only displays the upgrade prompt from when-in-use to always access once.

### What can I do if a permission is denied?

If a permission is in the `denied` state, it can no longer be requested from within the app. In this case, you can guide the user to the app settings using the [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) plugin so they can grant the permission manually.

### Which permissions are supported on the web?

On the web, only the `NOTIFICATIONS` permission can be checked and requested. The `CAMERA`, `LOCATION` and `MICROPHONE` permissions support checking, while requesting returns the current state since browsers display the permission prompt when the corresponding web API is used for the first time. All other permissions are reported as `unavailable`. See the [Platform behavior](#platform-behavior) section for details.

## Related Plugins

- [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/): Open native settings screens, for example to recover from denied permissions.
- [App Tracking Transparency](https://capawesome.io/docs/sdks/capacitor/app-tracking-transparency/): Request the App Tracking Transparency permission on iOS.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/permissions/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/permissions/LICENSE).
