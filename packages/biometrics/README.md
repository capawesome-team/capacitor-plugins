# @capawesome-team/capacitor-biometrics

Capacitor plugin to request biometric authentication, such as using face recognition or fingerprint recognition.

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for biometric authentication. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 👁️ **Fingerprint, Face and Iris**: Supports fingerprint, face and iris recognition.
- 🔑 **Device Credential**: Optionally allow the user to authenticate using their device's credential (e.g., PIN, password) if biometric authentication is not available or fails.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- ✨ **Customizable**: Customize the authentication prompt with a title, subtitle, and button text.
- 🤝 **Compatibility**: Compatible with the [Secure Preferences](https://capawesome.io/plugins/secure-preferences/) plugin.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 7.x.x          | >=7.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-biometrics
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxBiometricVersion` version of `androidx.biometric:biometric` (default: `1.1.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

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

const cancelAuthentication = async () => {
  await Biometrics.cancelAuthentication();
};

const enroll = async () => {
  await Biometrics.enroll();
};


const getBiometricStrengthLevel = async () => {
  const { strengthLevel } = await Biometrics.getBiometricStrengthLevel();
  return strengthLevel;
};

const hasDeviceCredential = async () => {
  const { hasDeviceCredential } = await Biometrics.hasDeviceCredential();
  return hasDeviceCredential;
};

const isAvailable = async () => {
  const { isAvailable } = await Biometrics.isAvailable();
  return isAvailable;
};

const isEnrolled = async () => {
  const { isEnrolled } = await Biometrics.isEnrolled();
  return isEnrolled;
};
```

## API

<docgen-index>

* [`authenticate(...)`](#authenticate)
* [`cancelAuthentication()`](#cancelauthentication)
* [`enroll()`](#enroll)
* [`getAuthenticationType()`](#getauthenticationtype)
* [`getBiometricStrengthLevel()`](#getbiometricstrengthlevel)
* [`hasDeviceCredential()`](#hasdevicecredential)
* [`isAvailable()`](#isavailable)
* [`isEnrolled()`](#isenrolled)
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

It is recommended to check if biometrics is available and enrolled
using the `isAvailable()` and `isEnrolled()` methods before calling
this method.

On **iOS**, the first time the user is prompted to authenticate, they will
be asked for permission to use biometrics. If the user denies permission,
the promise will reject with an error.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#authenticateoptions">AuthenticateOptions</a></code> |

**Since:** 0.1.0

--------------------


### cancelAuthentication()

```typescript
cancelAuthentication() => Promise<void>
```

Cancel the ongoing authentication session and dismisses the prompt.

This method is only available on Android (SDK 29+) and iOS.

**Since:** 7.0.0

--------------------


### enroll()

```typescript
enroll() => Promise<void>
```

Prompt the user to enroll their biometrics.

This method is only available on Android.

**Since:** 7.0.0

--------------------


### getAuthenticationType()

```typescript
getAuthenticationType() => Promise<GetAuthenticationTypeResult>
```

Check whether the user authenticated using a device credential
or a biometric credential.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getauthenticationtyperesult">GetAuthenticationTypeResult</a>&gt;</code>

**Since:** 0.2.0

--------------------


### getBiometricStrengthLevel()

```typescript
getBiometricStrengthLevel() => Promise<GetBiometricStrengthLevelResult>
```

Returns the biometric strength level of the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getbiometricstrengthlevelresult">GetBiometricStrengthLevelResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### hasDeviceCredential()

```typescript
hasDeviceCredential() => Promise<HasDeviceCredentialResult>
```

Check whether or not the device's credential (e.g., PIN, password)
has been set up by the current user of the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#hasdevicecredentialresult">HasDeviceCredentialResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether or not biometrics is available on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isEnrolled()

```typescript
isEnrolled() => Promise<IsEnrolledResult>
```

Check whether or not biometrics is available on the device and
has been configured by the current user of the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isenrolledresult">IsEnrolledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### AuthenticateOptions

| Prop                           | Type                                                            | Description                                                                                                                                                                                                                                                                                                                                                                                                             | Default                                    | Since |
| ------------------------------ | --------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`allowDeviceCredential`**    | <code>boolean</code>                                            | Whether or not to allow the user to authenticate using their device's credential (e.g., PIN, password) if biometric authentication is not available or fails. You can check if the device's credential is set up using the `hasDeviceCredential()` method.                                                                                                                                                              | <code>false</code>                         | 0.1.0 |
| **`androidBiometricStrength`** | <code><a href="#biometricstrength">BiometricStrength</a></code> | The Android biometric strength to use for authentication. You can check the supported biometric strength level of the device using the `getBiometricStrengthLevel()` method. **Note**: On Android API Level 28 and 29, this will always be set to `AndroidBiometricStrength.WEAK` regardless of the value passed in if `allowDeviceCredential` is set to `true`. This is a known limitation. Only available on Android. | <code>AndroidBiometricStrength.WEAK</code> | 0.1.0 |
| **`cancelButtonText`**         | <code>string</code>                                             | The negative button text of the authentication prompt.                                                                                                                                                                                                                                                                                                                                                                  |                                            | 0.1.0 |
| **`iosFallbackButtonText`**    | <code>string</code>                                             | The fallback button text of the authentication prompt. Only available on iOS.                                                                                                                                                                                                                                                                                                                                           |                                            | 0.1.0 |
| **`subtitle`**                 | <code>string</code>                                             | The subtitle of the authentication prompt.                                                                                                                                                                                                                                                                                                                                                                              |                                            | 0.1.0 |
| **`title`**                    | <code>string</code>                                             | The title of the authentication prompt.                                                                                                                                                                                                                                                                                                                                                                                 |                                            | 0.1.0 |


#### GetAuthenticationTypeResult

| Prop                     | Type                                                              | Description                                  | Since |
| ------------------------ | ----------------------------------------------------------------- | -------------------------------------------- | ----- |
| **`authenticationType`** | <code><a href="#authenticationtype">AuthenticationType</a></code> | The type of authentication used by the user. | 0.2.0 |


#### GetBiometricStrengthLevelResult

| Prop                | Type                                                            | Description                                           | Since |
| ------------------- | --------------------------------------------------------------- | ----------------------------------------------------- | ----- |
| **`strengthLevel`** | <code><a href="#biometricstrength">BiometricStrength</a></code> | The supported biometric strength level of the device. | 0.1.0 |


#### HasDeviceCredentialResult

| Prop                      | Type                 | Description                                                                                                     | Since |
| ------------------------- | -------------------- | --------------------------------------------------------------------------------------------------------------- | ----- |
| **`hasDeviceCredential`** | <code>boolean</code> | Whether or not the device's credential (e.g., PIN, password) has been set up by the current user of the device. | 0.1.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                         | Since |
| ----------------- | -------------------- | ------------------------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether or not biometric authentication is available on the device. | 0.1.0 |


#### IsEnrolledResult

| Prop             | Type                 | Description                                                                                                     | Since |
| ---------------- | -------------------- | --------------------------------------------------------------------------------------------------------------- | ----- |
| **`isEnrolled`** | <code>boolean</code> | Whether or not biometrics is supported by the device and has been configured by the current user of the device. | 0.1.0 |


### Enums


#### BiometricStrength

| Members      | Value                 | Since |
| ------------ | --------------------- | ----- |
| **`Strong`** | <code>'STRONG'</code> | 0.1.0 |
| **`Weak`**   | <code>'WEAK'</code>   | 0.1.0 |


#### AuthenticationType

| Members                | Value                            | Description                                                                                                                                         | Since |
| ---------------------- | -------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Biometric`**        | <code>'BIOMETRIC'</code>         | The user authenticated using a biometric credential.                                                                                                | 0.2.0 |
| **`DeviceCredential`** | <code>'DEVICE_CREDENTIAL'</code> | The user authenticated using a device credential (e.g., PIN, password).                                                                             | 0.2.0 |
| **`Unknown`**          | <code>'UNKNOWN'</code>           | The user authenticated via an unknown method. This value may be returned on older Android versions due to partial incompatibility with a newer API. | 0.2.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/biometrics/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/biometrics/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/biometrics/LICENSE).
