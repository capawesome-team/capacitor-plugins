# @capawesome-team/capacitor-secure-preferences

Capacitor plugin to securely store key/value pairs such as passwords, tokens or other sensitive information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for secure storage. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🔒 **Secure**: Store sensitive information such as passwords securely using the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and [iOS Keychain](https://developer.apple.com/documentation/security/keychain-services).
- 🔍 **Detailed Error Messages**: Get actionable error messages with specific failure reasons and error codes on iOS, making debugging keychain issues straightforward.
- 🤝 **Compatibility**: Compatible with the [Biometrics](https://capawesome.io/plugins/biometrics/) and [SQLite](https://capawesome.io/plugins/sqlite/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.2.x          | >=8.x.x           | Active support |

## Guides

- [Alternative to the Ionic Secure Storage plugin](https://capawesome.io/blog/alternative-to-ionic-secure-storage-plugin/)
- [Announcing the Capacitor Secure Preferences Plugin](https://capawesome.io/blog/announcing-the-capacitor-secure-preferences-plugin/)
- [Exploring the Capacitor Secure Preferences API](https://capawesome.io/blog/exploring-the-capacitor-secure-preferences-api/)
- [How to Securely Store Credentials with Capacitor](https://capawesome.io/blog/how-to-securely-store-credentials-with-capacitor/)

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
npm install @capawesome-team/capacitor-secure-preferences
npx cap sync
```

### Android

#### Backup rules

To prevent the preferences file from being backed up to the cloud, you need to add backup rules to your Android project.
You can read more about this in the [Android documentation](https://developer.android.com/identity/data/autobackup#IncludingFiles).

##### Android 11 and lower

Add the `android:fullBackupContent` attribute to the `<application>` tag in your `AndroidManifest.xml` file:

```xml
<application
  android:fullBackupContent="@xml/full_backup_content">
</application>
```

Create a new file `res/xml/full_backup_content.xml` with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<full-backup-content>
  <include domain="sharedpref" path="."/>
  <exclude domain="sharedpref" path="CAPAWESOME_SECURE_PREFERENCES.xml"/>
</full-backup-content>
```

##### Android 12 and higher

Add the `android:dataExtractionRules` attribute to the `<application>` tag in your `AndroidManifest.xml` file:

```xml
<application
  android:dataExtractionRules="@xml/data_extraction_rules">
</application>
```

Create a new file `res/xml/data_extraction_rules.xml` with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<data-extraction-rules>
 <cloud-backup [disableIfNoEncryptionCapabilities="true|false"]>
   <include domain="sharedpref" path="."/>
   <exclude domain="sharedpref" path="CAPAWESOME_SECURE_PREFERENCES.xml"/>
 </cloud-backup>
</data-extraction-rules>
```

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const clear = async () => {
  await SecurePreferences.clear();
};

const get = async () => {
  const { value } = await SecurePreferences.get({
    key: 'password',
  });
  console.log(value);
};

const keys = async () => {
  const { keys } = await SecurePreferences.keys();
  console.log(keys);
};

const remove = async () => {
  await SecurePreferences.remove({
    key: 'password',
  });
};

const set = async () => {
  await SecurePreferences.set({
    key: 'password',
    value: '123456',
  });
};
```

## API

<docgen-index>

* [`clear()`](#clear)
* [`get(...)`](#get)
* [`keys()`](#keys)
* [`remove(...)`](#remove)
* [`set(...)`](#set)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clear()

```typescript
clear() => Promise<void>
```

Clear all stored keys and values.

**Since:** 7.0.0

--------------------


### get(...)

```typescript
get(options: GetOptions) => Promise<GetResult>
```

Get the value associated with a key.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### keys()

```typescript
keys() => Promise<KeysResult>
```

Get a list of all stored keys.

**Returns:** <code>Promise&lt;<a href="#keysresult">KeysResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### remove(...)

```typescript
remove(options: RemoveOptions) => Promise<void>
```

Remove a value given its key.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#removeoptions">RemoveOptions</a></code> |

**Since:** 7.0.0

--------------------


### set(...)

```typescript
set(options: SetOptions) => Promise<void>
```

Set a value given its key.

On **Web**, the value is stored unencrypted in `localStorage`.
This is for development purposes only and should not be used in production.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#setoptions">SetOptions</a></code> |

**Since:** 7.0.0

--------------------


### Interfaces


#### GetResult

| Prop        | Type                        | Description          | Since |
| ----------- | --------------------------- | -------------------- | ----- |
| **`value`** | <code>string \| null</code> | The retrieved value. | 7.0.0 |


#### GetOptions

| Prop      | Type                | Description                               | Since |
| --------- | ------------------- | ----------------------------------------- | ----- |
| **`key`** | <code>string</code> | The key associated with the stored value. | 7.0.0 |


#### KeysResult

| Prop       | Type                  | Description                | Since |
| ---------- | --------------------- | -------------------------- | ----- |
| **`keys`** | <code>string[]</code> | The available stored keys. | 7.0.0 |


#### RemoveOptions

| Prop      | Type                | Description        | Since |
| --------- | ------------------- | ------------------ | ----- |
| **`key`** | <code>string</code> | The key to remove. | 7.0.0 |


#### SetOptions

| Prop        | Type                | Description                               | Since |
| ----------- | ------------------- | ----------------------------------------- | ----- |
| **`key`**   | <code>string</code> | The key associated with the stored value. | 7.0.0 |
| **`value`** | <code>string</code> | The value to store.                       | 7.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/LICENSE).
