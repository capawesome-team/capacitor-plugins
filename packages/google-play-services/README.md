# Capacitor Google Play Services Plugin

Unofficial Capacitor plugin to check the availability of Google Play Services on Android and prompt the user to install or update it.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✅ **Availability Check**: Check whether Google Play Services is installed, enabled and up to date.
- 🔍 **Status**: Find out why Google Play Services is not available, for example on Huawei or AOSP devices.
- 📲 **Resolution Dialog**: Prompt the user to install, enable or update Google Play Services with the system dialog.
- 🔢 **Version**: Read the version code of the installed Google Play Services APK.
- 🪶 **Lightweight**: Only depends on the `play-services-base` library, no other Google SDKs.
- 🔒 **Play Store safe**: Uses only official Google APIs.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Google Play Services plugin is typically used whenever an app depends on Google Play Services and wants to degrade gracefully on devices without it, for example:

- **Graceful degradation**: Hide or disable features that depend on Google Play Services, such as maps or push notifications, on devices without it.
- **Alternative services**: Fall back to alternative providers, such as Huawei Mobile Services, when Google Play Services is missing.
- **Update prompts**: Ask the user to update Google Play Services before using a feature that requires a recent version.
- **Diagnostics**: Include the Google Play Services status and version in bug reports and analytics.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-google-play-services` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-google-play-services
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidPlayServicesBaseVersion` version of `com.google.android.gms:play-services-base` (default: `18.10.1`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

This plugin is not available on iOS.

### Web

This plugin is not available on the web.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check the availability of Google Play Services, detect devices without it, prompt the user to make it available and read the installed version.

### Check the availability

Check whether Google Play Services is installed, enabled and up to date before using a feature that depends on it:

```typescript
import { GooglePlayServices } from '@capawesome/capacitor-google-play-services';

const isAvailable = async () => {
  const { available } = await GooglePlayServices.isAvailable();
  return available;
};
```

### Detect devices without Google Play Services

Some devices, such as Huawei devices with Huawei Mobile Services or devices with a custom Android build, ship without Google Play Services. Use the `getStatus()` method to find out why Google Play Services is not available and hide the features that depend on it:

```typescript
import { GooglePlayServices, Status } from '@capawesome/capacitor-google-play-services';

const getStatus = async () => {
  const { status } = await GooglePlayServices.getStatus();
  if (status === Status.ServiceMissing) {
    console.log('This device has no Google Play Services.');
  } else if (status !== Status.Success) {
    console.log('Google Play Services is installed but not usable: ', status);
  }
};
```

### Make Google Play Services available

If Google Play Services is disabled, outdated or currently updating, prompt the user to fix it with the system dialog. The promise resolves once Google Play Services is available and is rejected with the `CANCELED` error code if the user dismisses the dialog:

```typescript
import { ErrorCode, GooglePlayServices } from '@capawesome/capacitor-google-play-services';

const makeAvailable = async () => {
  try {
    await GooglePlayServices.makeAvailable();
    console.log('Google Play Services is now available.');
  } catch (error) {
    if (error.code === ErrorCode.Canceled) {
      console.log('The user dismissed the dialog.');
    }
  }
};
```

### Get the version

Read the version code of the installed Google Play Services APK, for example to include it in bug reports:

```typescript
import { GooglePlayServices } from '@capawesome/capacitor-google-play-services';

const getVersion = async () => {
  const { version } = await GooglePlayServices.getVersion();
  console.log('Google Play Services version: ', version);
};
```

## API

<docgen-index>

* [`getStatus()`](#getstatus)
* [`getVersion()`](#getversion)
* [`isAvailable()`](#isavailable)
* [`makeAvailable()`](#makeavailable)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getStatus()

```typescript
getStatus() => Promise<GetStatusResult>
```

Get the status of Google Play Services on this device.

Use this method to find out why Google Play Services is not available,
for example to distinguish devices without Google Play Services
from devices with an outdated version.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getstatusresult">GetStatusResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getVersion()

```typescript
getVersion() => Promise<GetVersionResult>
```

Get the version code of the Google Play Services APK installed on this device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getversionresult">GetVersionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether Google Play Services is available on this device.

This resolves to `true` if Google Play Services is installed, enabled and up to date.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### makeAvailable()

```typescript
makeAvailable() => Promise<void>
```

Make Google Play Services available on this device.

If Google Play Services is missing, disabled, outdated or currently updating,
this shows the system dialog that prompts the user to install, enable or update it.
The promise resolves once Google Play Services is available.
If the user dismisses the dialog, the promise is rejected with the `CANCELED` error code.
If Google Play Services can not be made available, for example on devices
without the Google Play Store, the promise is rejected with the error message
from Google Play Services.

Only available on Android.

**Since:** 0.1.0

--------------------


### Interfaces


#### GetStatusResult

| Prop         | Type                                      | Description                                        | Since |
| ------------ | ----------------------------------------- | -------------------------------------------------- | ----- |
| **`status`** | <code><a href="#status">Status</a></code> | The status of Google Play Services on this device. | 0.1.0 |


#### GetVersionResult

| Prop          | Type                | Description                                                                                                                      | Since |
| ------------- | ------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`version`** | <code>number</code> | The version code of the Google Play Services APK installed on this device. This is `0` if Google Play Services is not installed. | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                      | Since |
| --------------- | -------------------- | ---------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not Google Play Services is available on this device. | 0.1.0 |


### Enums


#### Status

| Members                            | Value                                          | Description                                                     | Since |
| ---------------------------------- | ---------------------------------------------- | --------------------------------------------------------------- | ----- |
| **`ServiceDisabled`**              | <code>'SERVICE_DISABLED'</code>                | Google Play Services is disabled on this device.                | 0.1.0 |
| **`ServiceInvalid`**               | <code>'SERVICE_INVALID'</code>                 | The installed version of Google Play Services is not authentic. | 0.1.0 |
| **`ServiceMissing`**               | <code>'SERVICE_MISSING'</code>                 | Google Play Services is not installed on this device.           | 0.1.0 |
| **`ServiceUpdating`**              | <code>'SERVICE_UPDATING'</code>                | Google Play Services is currently being updated on this device. | 0.1.0 |
| **`ServiceVersionUpdateRequired`** | <code>'SERVICE_VERSION_UPDATE_REQUIRED'</code> | The installed version of Google Play Services is out of date.   | 0.1.0 |
| **`Success`**                      | <code>'SUCCESS'</code>                         | Google Play Services is available.                              | 0.1.0 |

</docgen-api>

## FAQ

### How is this plugin different from other similar plugins?

It focuses on one thing: telling your app whether Google Play Services can be used on the current device and, if not, why. It wraps the official `GoogleApiAvailability` API in a small, fully typed plugin that only depends on the `play-services-base` library, so you can degrade gracefully on devices without Google Play Services without pulling in Firebase, Maps or any other Google SDK. Actively maintained against the latest Capacitor version, it pairs naturally with the App Integrity, App Update and Google Sign-In plugins.

### Does this plugin work on iOS or Web?

No, this plugin only provides an Android implementation. Google Play Services does not exist on other platforms, so all methods are rejected with the `UNIMPLEMENTED` error code on iOS and Web. Check the platform with `Capacitor.getPlatform()` before calling the plugin in a cross-platform app.

### Does this plugin crash on devices without Google Play Services?

No. The availability check is part of the `play-services-base` library that is bundled with your app. It only asks the package manager whether Google Play Services is installed and signed by Google, so it works on any Android device, including devices without Google Play Services.

### How do I detect devices without Google Play Services, such as Huawei devices?

Call the `getStatus()` method. It resolves to `SERVICE_MISSING` on devices where Google Play Services is not installed at all, which is the case for Huawei devices with Huawei Mobile Services, Amazon Fire devices and custom Android builds. See [Detect devices without Google Play Services](#detect-devices-without-google-play-services).

### Why does `makeAvailable()` not work on my device?

The system dialog can only enable or update Google Play Services if it is already installed, or install it via the Google Play Store. On devices without the Google Play Store, such as Huawei devices with Huawei Mobile Services, there is no way to install Google Play Services and the promise is rejected. Check the status with the `getStatus()` method first and only call `makeAvailable()` if the status is `SERVICE_DISABLED`, `SERVICE_VERSION_UPDATE_REQUIRED` or `SERVICE_UPDATING`.

### Can I check the availability of a specific Google API, such as Maps or Location?

No, the plugin only checks the availability of Google Play Services as a whole. Individual Google APIs are covered by dedicated plugins.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/): Verify app and device integrity using the Play Integrity API and App Attest.
- [App Update](https://capawesome.io/docs/sdks/capacitor/app-update/): Check for app updates and start in-app updates.
- [Google Sign-In](https://capawesome.io/docs/sdks/capacitor/google-sign-in/): Sign in with Google on Android, iOS and Web.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/google-play-services/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/google-play-services/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of its affiliates or subsidiaries. "Google Play" is a trademark of Google LLC.
