# @capawesome-team/capacitor-biometrics

Capacitor plugin to request biometric authentication, such as using face recognition or fingerprint recognition.

## Features

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🔒 **Secure**: Authenticate users securely using the device's biometric authentication.
- 🔑 **Device Credential**: Optionally allow the user to authenticate using their device's credential (e.g., PIN, password) if biometric authentication is not available or fails.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- ✨ **Customizable**: Customize the authentication prompt with a title, subtitle, and button text.
- 🤝 **Compatibility**: Compatible with the [Secure Preferences](https://capawesome.io/plugins/secure-preferences) plugin.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

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
npm install @capawesome-team/capacitor-secure-preferences
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxBiometricVersion` version of `androidx.biometric:biometric` (default: `1.1.0`)

### iOS

#### Privacy Descriptions

Add the `NSFaceIDUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the biometric authentication:

```xml
<key>NSFaceIDUsageDescription</key>
<string>This app uses Face ID for authentication.</string>
```

## Usage

```typescript
import { Biometrics, ErrorCode } from '@capawesome-team/capacitor-biometrics';

const authenticate = async () => {
  // If the user successfully authenticates, the promise resolves.
  // If the user cancels the authentication or if an error occurs, the promise rejects.
  try {
    await Biometrics.authenticate({
      title: 'Authentication Required',
      subtitle: 'Please authenticate to continue',
      cancelButtonText: 'Cancel',
      iosFallbackButtonText: 'Use Passcode',
      allowDeviceCredential: true,
    });
  } catch (error) {
    if (error.code === ErrorCode.USER_CANCELED) {
      console.log('User canceled the authentication.');
    } else if (error.code === ErrorCode.NOT_ENROLLED) {
      console.log('No biometric authentication enrolled.');
    } else if (error.code === ErrorCode.NOT_AVAILABLE) {
      console.log('Biometric authentication not available.');
    } else {
      console.log('Another error occurred:', error);
    }
  }
};

const isAvailable = async () => {
  const result = await Biometrics.isAvailable({
    allowDeviceCredential: true,
  });
  return result.isAvailable;
};
```

## API

<docgen-index>

* [`authenticate(...)`](#authenticate)
* [`getAndroidBiometricStrengthLevel()`](#getandroidbiometricstrengthlevel)
* [`isAvailable(...)`](#isavailable)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### authenticate(...)

```typescript
authenticate(options?: AuthenticateOptions | undefined) => Promise<void>
```

Authenticates the user locally using the device's biometric authentication.

This method will show a prompt to the user asking them to authenticate
using their biometrics (e.g., fingerprint, face recognition). If the user
successfully authenticates, the promise resolves. If the user cancels
the authentication or if an error occurs, the promise rejects.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#authenticateoptions">AuthenticateOptions</a></code> |

**Since:** 7.0.0

--------------------


### getAndroidBiometricStrengthLevel()

```typescript
getAndroidBiometricStrengthLevel() => Promise<GetAndroidBiometricStrengthLevelResult>
```

Returns the biometric strength level of the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getandroidbiometricstrengthlevelresult">GetAndroidBiometricStrengthLevelResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### isAvailable(...)

```typescript
isAvailable(options?: IsAvailableOptions | undefined) => Promise<IsAvailableResult>
```

Checks if biometric authentication is available on the device.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#isavailableoptions">IsAvailableOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### Interfaces


#### AuthenticateOptions

| Prop                           | Type                                                                          | Description                                                                                                                                                                                                                                                                                          | Default                                    | Since |
| ------------------------------ | ----------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`allowDeviceCredential`**    | <code>boolean</code>                                                          | Whether or not to allow the user to authenticate using their device's credential (e.g., PIN, password) if biometric authentication is not available or fails.                                                                                                                                        | <code>false</code>                         | 7.0.0 |
| **`androidBiometricStrength`** | <code><a href="#androidbiometricstrength">AndroidBiometricStrength</a></code> | The Android biometric strength to use for authentication. **Note**: On Android API Level 28 and 29, this will always be set to `AndroidBiometricStrength.WEAK` regardless of the value passed in if `allowDeviceCredential` is set to `true`. This is a known limitation. Only available on Android. | <code>AndroidBiometricStrength.WEAK</code> | 7.0.0 |
| **`cancelButtonText`**         | <code>string</code>                                                           | The negative button text of the authentication prompt.                                                                                                                                                                                                                                               |                                            | 7.0.0 |
| **`iosFallbackButtonText`**    | <code>string</code>                                                           | The fallback button text of the authentication prompt. Only available on iOS.                                                                                                                                                                                                                        |                                            | 7.0.0 |
| **`subtitle`**                 | <code>string</code>                                                           | The subtitle of the authentication prompt.                                                                                                                                                                                                                                                           |                                            | 7.0.0 |
| **`title`**                    | <code>string</code>                                                           | The title of the authentication prompt.                                                                                                                                                                                                                                                              |                                            | 7.0.0 |


#### GetAndroidBiometricStrengthLevelResult

| Prop                | Type                                                                          | Description                                           | Since |
| ------------------- | ----------------------------------------------------------------------------- | ----------------------------------------------------- | ----- |
| **`strengthLevel`** | <code><a href="#androidbiometricstrength">AndroidBiometricStrength</a></code> | The supported biometric strength level of the device. | 7.0.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                         | Since |
| ----------------- | -------------------- | ------------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not biometric authentication is available on the device. | 7.0.0 |


#### IsAvailableOptions

| Prop                           | Type                                                                          | Description                                                                                                                                                                                                                                                                                          | Default                                    | Since |
| ------------------------------ | ----------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`allowDeviceCredential`**    | <code>boolean</code>                                                          | Whether or not to allow the user to authenticate using their device's credential (e.g., PIN, password) if biometric authentication is not available or fails.                                                                                                                                        | <code>false</code>                         | 7.0.0 |
| **`androidBiometricStrength`** | <code><a href="#androidbiometricstrength">AndroidBiometricStrength</a></code> | The Android biometric strength to use for authentication. **Note**: On Android API Level 28 and 29, this will always be set to `AndroidBiometricStrength.WEAK` regardless of the value passed in if `allowDeviceCredential` is set to `true`. This is a known limitation. Only available on Android. | <code>AndroidBiometricStrength.WEAK</code> | 7.0.0 |


### Enums


#### AndroidBiometricStrength

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`Strong`** | <code>'STRONG'</code> | 7.0.0 |
| **`Weak`**   | <code>'WEAK'</code>   | 7.0.0 |

</docgen-api>
