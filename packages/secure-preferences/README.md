# Capacitor Secure Preferences Plugin

Capacitor plugin to securely store key/value pairs such as passwords, tokens or other sensitive information.

<div class="capawesome-z29o10a">
  <a href="https://capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Secure Preferences plugin is one of the most complete secure storage solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Native secure storage on Android and iOS, with a `localStorage`-backed web implementation for development.
- 🔒 **Secure**: Store sensitive information such as passwords securely using the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and [iOS Keychain](https://developer.apple.com/documentation/security/keychain-services).
- 🔍 **Detailed Error Messages**: Get actionable error messages with specific failure reasons and error codes on iOS, making debugging keychain issues straightforward.
- 🤝 **Compatibility**: Compatible with the [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/), [SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/), and [Vault](https://capawesome.io/docs/sdks/capacitor/vault/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Secure Preferences plugin is typically used whenever an app needs to keep small pieces of sensitive data on the device, for example:

- **Authentication tokens**: Store OAuth refresh tokens or session tokens that the app reads in the background.
- **API keys**: Keep server-issued API keys encrypted at rest instead of in plain text.
- **Credentials**: Securely store passwords using the Android Keystore and iOS Keychain.
- **Sensitive settings**: Persist preference flags that contain personal information.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.3.x          | >=8.x.x           | Active support |
| 0.2.x          | >=8.x.x           | Deprecated     |

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

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-secure-preferences` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-secure-preferences
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### Web

**Attention**: The web implementation uses `localStorage` to make cross-platform development easier. It is intended for development and testing purposes only and should NOT be used in production.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to store a value, retrieve a value, list all stored keys, remove a value, and clear all stored values.

### Store a value

Store a value securely under a key. On Android and iOS, the value is encrypted at rest. On the web, the value is stored unencrypted in `localStorage` for development purposes only:

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const set = async () => {
  await SecurePreferences.set({
    key: 'password',
    value: '123456',
  });
};
```

### Retrieve a value

Get the value associated with a key:

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const get = async () => {
  const { value } = await SecurePreferences.get({
    key: 'password',
  });
  console.log(value);
};
```

### List all stored keys

Get a list of all keys that currently have a stored value:

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const keys = async () => {
  const { keys } = await SecurePreferences.keys();
  console.log(keys);
};
```

### Remove a value

Remove a single value given its key:

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const remove = async () => {
  await SecurePreferences.remove({
    key: 'password',
  });
};
```

### Clear all stored values

Remove all stored keys and values at once:

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const clear = async () => {
  await SecurePreferences.clear();
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

**Since:** 0.1.0

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

**Since:** 0.1.0

--------------------


### keys()

```typescript
keys() => Promise<KeysResult>
```

Get a list of all stored keys.

**Returns:** <code>Promise&lt;<a href="#keysresult">KeysResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### remove(...)

```typescript
remove(options: RemoveOptions) => Promise<void>
```

Remove a value given its key.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#removeoptions">RemoveOptions</a></code> |

**Since:** 0.1.0

--------------------


### set(...)

```typescript
set(options: SetOptions) => Promise<void>
```

Set a value given its key.

On **Web**, the value is stored unencrypted in `localStorage`.
This is for development purposes only and should NOT be used in production.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#setoptions">SetOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetResult

| Prop        | Type                        | Description          | Since |
| ----------- | --------------------------- | -------------------- | ----- |
| **`value`** | <code>string \| null</code> | The retrieved value. | 0.1.0 |


#### GetOptions

| Prop      | Type                | Description                               | Since |
| --------- | ------------------- | ----------------------------------------- | ----- |
| **`key`** | <code>string</code> | The key associated with the stored value. | 0.1.0 |


#### KeysResult

| Prop       | Type                  | Description                | Since |
| ---------- | --------------------- | -------------------------- | ----- |
| **`keys`** | <code>string[]</code> | The available stored keys. | 0.1.0 |


#### RemoveOptions

| Prop      | Type                | Description        | Since |
| --------- | ------------------- | ------------------ | ----- |
| **`key`** | <code>string</code> | The key to remove. | 0.1.0 |


#### SetOptions

| Prop        | Type                | Description                               | Since |
| ----------- | ------------------- | ----------------------------------------- | ----- |
| **`key`**   | <code>string</code> | The key associated with the stored value. | 0.1.0 |
| **`value`** | <code>string</code> | The value to store.                       | 0.1.0 |

</docgen-api>

## FAQ

### Where is the data stored?

On Android, the encryption key is stored in the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and the encrypted values are stored in a `SharedPreferences` file (`CAPAWESOME_SECURE_PREFERENCES.xml`). On iOS, the encrypted values are stored as [Keychain](https://developer.apple.com/documentation/security/keychain-services) items.

On Android, the encryption key in the Keystore is never backed up. The encrypted values in `SharedPreferences` are part of [Android Auto Backup](https://developer.android.com/identity/data/autobackup) by default, but the backed-up ciphertext is unusable on another device without the Keystore key; to exclude the preferences file (`CAPAWESOME_SECURE_PREFERENCES.xml`) from backup, see the [Android documentation](https://developer.android.com/identity/data/autobackup#IncludingFiles). On iOS, the Keychain items are not synced to iCloud, but they may be included in encrypted local device backups and restored on a new device.

### When should I use Secure Preferences instead of Vault or SQLite?

All three plugins protect data on the device, but they target different problems:

- **Secure Preferences** (this plugin) is a transparent key/value store. Values are encrypted at rest using the Android Keystore and iOS Keychain, but the app can read them at any time without prompting the user. Reach for it when you need to keep small bits of sensitive data around that the app itself accesses in the background — typical examples are OAuth refresh tokens, server-issued API keys, or preference flags that contain personal information.

- **[Vault](https://capawesome.io/docs/sdks/capacitor/vault/)** is a key/value store with an active lock state and biometric or device-passcode gating. The user has to unlock it before any read or write, and it locks again on demand or after a configurable background timeout. Reach for it when access to the data should require an explicit user action — a password manager's entries, an authenticator app's TOTP secrets, or the credentials sitting behind an "app lock" screen.

- **[SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/)** is a full relational database with optional SQLCipher encryption. Use it when the shape of your data calls for queries, joins, indexes, or large record sets — for example, an offline-first app that syncs structured records, or anything you would otherwise model with a server-side database.

A quick decision tree:

- Need encrypted key/value storage the app can read freely in the background? → **Secure Preferences**.
- Need encrypted key/value storage the user must actively unlock with biometrics or a passcode? → **Vault**.
- Need queries, relations, or large datasets? → **SQLite**.

The three plugins are designed to coexist. A real-world app might use Secure Preferences for app-managed tokens, SQLite for synced records, and Vault for the master password that protects everything else.

### Is this plugin an alternative to Ionic Secure Storage?

Yes, for key/value data. This plugin was built as an actively maintained alternative to [Ionic Secure Storage](https://ionic.io/products/secure-storage), which sunsets on December 31, 2027. Like Ionic Secure Storage's key/value API, it stores sensitive data encrypted at rest, backed by the Android Keystore and iOS Keychain. If you use Ionic Secure Storage's encrypted SQLite database, take a look at the [SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/) plugin instead, which provides SQLCipher-based encryption.

### How do I migrate from Ionic Secure Storage?

For an AI-assisted migration of your code, add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool and instruct it to use the `ionic-enterprise-sdk-migration` skill to migrate your project from Ionic Secure Storage to `@capawesome-team/capacitor-secure-preferences`. Alternatively, if you want to perform the migration manually, you can follow the instructions in this blog post: [Alternative to the Ionic Secure Storage plugin](https://capawesome.io/blog/alternative-to-ionic-secure-storage-plugin/).

### Can I use the plugin on the web?

Yes, but only for development and testing purposes. The web implementation stores values unencrypted in `localStorage` to make cross-platform development easier and should NOT be used in production. The secure, encrypted storage backed by the Android Keystore and iOS Keychain is only available on Android and iOS.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/): Request biometric authentication, such as face or fingerprint recognition.
- [Vault](https://capawesome.io/docs/sdks/capacitor/vault/): Securely store key/value pairs in lockable, biometric-protected vaults.
- [SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/): Access SQLite databases with support for encryption, transactions, and schema migrations.

## Next steps

Here are a few resources to help you continue:

- Read [Alternative to the Ionic Secure Storage plugin](https://capawesome.io/blog/alternative-to-ionic-secure-storage-plugin/) if you are migrating from Ionic Secure Storage.
- Need biometric-protected, lockable storage? Check out the [Capacitor Vault plugin](https://capawesome.io/docs/sdks/capacitor/vault/).
- Need encrypted relational storage? Check out the [Capacitor SQLite plugin](https://capawesome.io/docs/sdks/capacitor/sqlite/).

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/LICENSE).
