# Capacitor App Tracking Transparency Plugin

Capacitor plugin for the [App Tracking Transparency](https://developer.apple.com/documentation/apptrackingtransparency) framework.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor App Tracking Transparency plugin is one of the most complete tracking authorization solutions for Capacitor apps. Here are some of the key features:

- 🔒 **Authorization status**: Read the current tracking authorization status.
- 🙋 **Permission request**: Present the system tracking authorization prompt.
- 🆔 **Advertising identifier**: Read the advertising identifier (IDFA) when authorized.
- 🤝 **Compatibility**: Works alongside the [Permissions](https://capawesome.io/docs/sdks/capacitor/permissions/) plugin.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The App Tracking Transparency plugin is typically used whenever an iOS app collects data for tracking purposes, for example:

- **Personalized ads**: Request tracking permission before serving personalized ads to the user.
- **Ad attribution**: Read the advertising identifier (IDFA) to attribute app installs to ad campaigns once the user has authorized tracking.
- **Conditional tracking**: Check the current tracking authorization status and only enable tracking-related features when the status is `authorized`.
- **App Review compliance**: Present the system tracking authorization prompt as required by Apple for any app that tracks users.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-tracking-transparency` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-tracking-transparency
npx cap sync
```

This plugin is only available on **iOS**. On Android and Web, all methods reject as unimplemented.

### iOS

#### Privacy Descriptions

The `NSUserTrackingUsageDescription` key must be added to the `Info.plist` file of your app. Otherwise, the `requestPermission(...)` method will reject with an error.

```xml
<key>NSUserTrackingUsageDescription</key>
<string>Your data will be used to deliver personalized ads to you.</string>
```

The purpose string must clearly explain why your app is requesting permission to track the user. See the [Apple documentation](https://developer.apple.com/documentation/bundleresources/information-property-list/nsusertrackingusagedescription) for more information.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check the tracking authorization status, request tracking permission, and read the advertising identifier.

### Check the tracking authorization status

Read the current tracking authorization status, for example to decide whether tracking-related features should be enabled. Only available on iOS:

```typescript
import { AppTrackingTransparency } from '@capawesome/capacitor-app-tracking-transparency';

const getStatus = async () => {
  const { status } = await AppTrackingTransparency.getStatus();
  return status;
};
```

### Request tracking permission

Present the system tracking authorization prompt. The prompt is only shown once per install while the status is `notDetermined`; afterwards, the method resolves with the existing status. Only available on iOS:

```typescript
import { AppTrackingTransparency } from '@capawesome/capacitor-app-tracking-transparency';

const requestPermission = async () => {
  const { status } = await AppTrackingTransparency.requestPermission();
  return status;
};
```

### Read the advertising identifier

Read the advertising identifier (IDFA) of the device. It is only available if the tracking authorization status is `authorized`; otherwise, `null` is returned. Only available on iOS:

```typescript
import { AppTrackingTransparency } from '@capawesome/capacitor-app-tracking-transparency';

const getAdvertisingIdentifier = async () => {
  const { advertisingIdentifier } =
    await AppTrackingTransparency.getAdvertisingIdentifier();
  return advertisingIdentifier;
};
```

## API

<docgen-index>

* [`getAdvertisingIdentifier()`](#getadvertisingidentifier)
* [`getStatus()`](#getstatus)
* [`requestPermission()`](#requestpermission)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getAdvertisingIdentifier()

```typescript
getAdvertisingIdentifier() => Promise<GetAdvertisingIdentifierResult>
```

Get the advertising identifier (IDFA) of the device.

The advertising identifier is only available if the tracking authorization
status is `authorized`. Otherwise, `null` is returned.

**Note**: The iOS Simulator always returns `null`, even if the tracking
authorization status is `authorized`. Use a real device to test this method.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getadvertisingidentifierresult">GetAdvertisingIdentifierResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getStatus()

```typescript
getStatus() => Promise<GetStatusResult>
```

Get the current tracking authorization status.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#getstatusresult">GetStatusResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### requestPermission()

```typescript
requestPermission() => Promise<RequestPermissionResult>
```

Request permission to track the user.

This will present the system tracking authorization prompt if the status
has not been determined yet. The prompt is only shown once per install.

The `NSUserTrackingUsageDescription` key must be added to the `Info.plist`
file of your app.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#requestpermissionresult">RequestPermissionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetAdvertisingIdentifierResult

| Prop                        | Type                        | Description                                                                                                                                                                                                           | Since |
| --------------------------- | --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`advertisingIdentifier`** | <code>string \| null</code> | The advertising identifier (IDFA) of the device. Returns `null` if the tracking authorization status is not `authorized` or if no advertising identifier is available, which is always the case on the iOS Simulator. | 0.1.0 |


#### GetStatusResult

| Prop         | Type                                                      | Description                                | Since |
| ------------ | --------------------------------------------------------- | ------------------------------------------ | ----- |
| **`status`** | <code><a href="#trackingstatus">TrackingStatus</a></code> | The current tracking authorization status. | 0.1.0 |


#### RequestPermissionResult

| Prop         | Type                                                      | Description                                          | Since |
| ------------ | --------------------------------------------------------- | ---------------------------------------------------- | ----- |
| **`status`** | <code><a href="#trackingstatus">TrackingStatus</a></code> | The tracking authorization status after the request. | 0.1.0 |


### Type Aliases


#### TrackingStatus

The tracking authorization status.

- `authorized`: The user authorized access to app-related data for tracking.
- `denied`: The user denied access to app-related data for tracking.
- `notDetermined`: The user has not yet received a tracking authorization request.
- `restricted`: Tracking authorization is restricted and cannot be changed by the user.

<code>'authorized' | 'denied' | 'notDetermined' | 'restricted'</code>

</docgen-api>

## App Review Guidance

Apple requires that any app that tracks users requests permission via the App Tracking Transparency framework. Keep the following guidelines in mind to avoid App Review rejections:

- **Prompt timing**: Call `requestPermission(...)` only when the user is in a context where tracking makes sense. The system prompt is only shown once per install while the status is `notDetermined`. Calling the method again afterwards resolves with the existing status without showing the prompt.
- **Purpose string**: The `NSUserTrackingUsageDescription` purpose string must accurately describe how the collected data is used. Vague or misleading descriptions are a common reason for rejection.
- **Advertising identifier**: The advertising identifier (IDFA) is only available while the status is `authorized`. In all other cases, `getAdvertisingIdentifier(...)` returns `null`.
- **Simulator**: The iOS Simulator never provides an advertising identifier, so `getAdvertisingIdentifier(...)` always returns `null` there, even if the status is `authorized`. Use a real device to test the advertising identifier.

## FAQ

### How is this plugin different from other similar plugins?

It wraps Apple's App Tracking Transparency framework in a small, fully typed API: read the current authorization status, present the system prompt, and read the advertising identifier (IDFA) once the user has authorized tracking. It also ships focused App Review guidance on prompt timing, purpose strings, and simulator behavior — the details that most often decide whether a tracking-enabled app is approved. It supports CocoaPods and Swift Package Manager and is actively maintained against the latest Capacitor version.

### Does this plugin work on Android or Web?

No, the App Tracking Transparency framework is an iOS-only concept. The plugin is only available on iOS; on Android and Web, all methods reject as unimplemented.

### Why does the requestPermission method reject with an error?

The most common reason is a missing `NSUserTrackingUsageDescription` key in your app's `Info.plist` file. This key is required for the system tracking authorization prompt and its purpose string must clearly explain why your app is requesting permission to track the user. See the [Installation](#installation) section for details.

### Why is the tracking authorization prompt not shown?

The system prompt is only shown once per install while the tracking authorization status is `notDetermined`. If the user has already responded to the prompt, calling `requestPermission()` again resolves with the existing status without showing the prompt. To test the prompt again, reinstall the app.

### Why does getAdvertisingIdentifier return null?

The advertising identifier (IDFA) is only available if the tracking authorization status is `authorized`; in all other cases, `null` is returned. Additionally, the iOS Simulator never provides an advertising identifier, even if the status is `authorized`, so use a real device to test this method.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Permissions](https://capawesome.io/docs/sdks/capacitor/permissions/): Check and request device permissions with a unified API.
- [Privacy Screen](https://capawesome.io/docs/sdks/capacitor/privacy-screen/): Hide sensitive app content in the app switcher and block screenshots.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-tracking-transparency/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-tracking-transparency/LICENSE).
