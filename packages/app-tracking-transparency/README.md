# @capawesome/capacitor-app-tracking-transparency

Capacitor plugin for the [App Tracking Transparency](https://developer.apple.com/documentation/apptrackingtransparency) framework.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for App Tracking Transparency. Here are some of the key features:

- 🔒 **Authorization status**: Read the current tracking authorization status.
- 🙋 **Permission request**: Present the system tracking authorization prompt.
- 🆔 **Advertising identifier**: Read the advertising identifier (IDFA) when authorized.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
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

```typescript
import { AppTrackingTransparency } from '@capawesome/capacitor-app-tracking-transparency';

const getStatus = async () => {
  const { status } = await AppTrackingTransparency.getStatus();
  return status;
};

const requestPermission = async () => {
  const { status } = await AppTrackingTransparency.requestPermission();
  return status;
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-tracking-transparency/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-tracking-transparency/LICENSE).
