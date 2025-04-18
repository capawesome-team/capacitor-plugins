# @capawesome-team/capacitor-secure-preferences

Capacitor plugin to securely store key/value pairs such as passwords, tokens or other sensitive information.

## Features

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🔑 **Secure**: Store sensitive information such as passwords securely using the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and [iOS Keychain](https://developer.apple.com/documentation/security/keychain-services).
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

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxSecurityCryptoVersion` version of `androidx.security:security-crypto` (default: `1.0.0`)

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const echo = async () => {
  await SecurePreferences.echo();
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

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/secure-preferences/LICENSE).
