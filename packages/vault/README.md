# @capawesome-team/capacitor-vault

Capacitor plugin to securely store key/value pairs in lockable, biometric-protected vaults.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for secure, lockable storage. Here are some of the key features:

- 🖥️ **Cross-platform**: Native secure vault on Android and iOS, with a `localStorage`-backed web implementation for development.
- 🔒 **Secure**: Hardware-backed encryption via the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and [iOS Keychain](https://developer.apple.com/documentation/security/keychain-services).
- 🔓 **Lockable**: Unlock once with a biometric or passcode prompt, then perform many read/write operations until `lock()` is called.
- ⏱️ **Auto-lock**: Automatically lock the vault after the app has been backgrounded for a configurable duration.
- 🗂️ **Multi-vault**: Create multiple independent vaults with different configurations on the same device.
- 🔐 **Multiple vault types**: Authenticate with biometrics, the device passcode, or either.
- ♻️ **Key invalidation**: Optionally invalidate the encryption key when the device's biometric set changes.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- ✨ **Customizable**: Customize the authentication prompt with a title, subtitle, and button text.
- 🤝 **Compatibility**: Compatible with the [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/) and [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-vault-demo).

| Android                                                                                                                                                | iOS                                                                                                                                                |
| ------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| <video src="https://capawesome.io/docs/assets/videos/posts/announcing-the-capacitor-vault-plugin/vault-demo-android.mp4" width="324" controls></video> | <video src="https://capawesome.io/docs/assets/videos/posts/announcing-the-capacitor-vault-plugin/vault-demo-ios.mp4" width="266" controls></video> |

## Guides

- [Announcing the Capacitor Vault Plugin](https://capawesome.io/blog/announcing-the-capacitor-vault-plugin/)
- [Alternatives to Ionic Enterprise Plugins](https://capawesome.io/blog/alternatives-to-ionic-enterprise-plugins/)
- [Alternative to the Ionic Identity Vault Plugin](https://capawesome.io/blog/alternative-to-ionic-identity-vault-plugin/)

## Videos

- [How to Secure Sensitive Data Using Capacitor Vault Plugin](https://youtu.be/wCNMqVBnQqs?si=mePC0ADiRHy_rFQz)

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-vault` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-vault
npx cap sync
```

### Android

#### Minimum Android version

The `DEVICE_PASSCODE` and `BIOMETRIC_OR_DEVICE_PASSCODE` vault types require Android API 30 (Android 11) or higher. On lower versions, `initialize(...)` will reject with the `AUTHENTICATOR_UNAVAILABLE` error code. The `BIOMETRIC` vault type is supported on all supported Android versions.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variables in your app's `variables.gradle` file to change the default version of the dependencies:

- `$androidxBiometricVersion` version of `androidx.biometric:biometric` (default: `1.1.0`)
- `$androidxLifecycleProcessVersion` version of `androidx.lifecycle:lifecycle-process` (default: `2.9.4`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Privacy Descriptions

Add the `NSFaceIDUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to Face ID:

```xml
<key>NSFaceIDUsageDescription</key>
<string>This app uses Face ID to unlock your data.</string>
```

### Web

**Attention**: The web implementation uses `localStorage` to make cross-platform development easier. It is intended for development and testing purposes only and should NOT be used in production.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import {
  Vault,
  VaultType,
  ErrorCode,
} from '@capawesome-team/capacitor-vault';

// Call once per session before any other method.
const initialize = async () => {
  await Vault.initialize({
    type: VaultType.Biometric,
    title: 'Unlock vault',
    cancelButtonText: 'Cancel',
    iosFallbackButtonText: 'Use Passcode',
    lockAfterBackgrounded: 30000,
  });
};

// Check whether a vault was created in a previous session.
const exists = async () => {
  const { exists } = await Vault.exists();
  return exists;
};

// Prompt the user for biometric authentication.
const unlock = async () => {
  try {
    await Vault.unlock();
  } catch (error) {
    if (error.code === ErrorCode.UnlockCanceled) {
      console.log('The user canceled the authentication prompt.');
    } else if (error.code === ErrorCode.KeyInvalidated) {
      console.log('The encryption key was invalidated.');
    } else {
      console.log('Another error occurred:', error);
    }
  }
};

// Check whether the vault is currently locked.
const isLocked = async () => {
  const { isLocked } = await Vault.isLocked();
  return isLocked;
};

// Store a value.
const setValue = async () => {
  await Vault.setValue({ key: 'token', value: 'abc123' });
};

// Retrieve a value.
const getValue = async () => {
  const { value } = await Vault.getValue({ key: 'token' });
  return value;
};

// Remove a single value.
const removeValue = async () => {
  await Vault.removeValue({ key: 'token' });
};

// List all keys currently stored in the vault.
const getKeys = async () => {
  const { keys } = await Vault.getKeys();
  return keys;
};

// Remove all values without destroying the vault.
const clear = async () => {
  await Vault.clear();
};

// Lock the vault manually.
const lock = async () => {
  await Vault.lock();
};

// React to lock and unlock events.
const addListeners = async () => {
  await Vault.addListener('lock', ({ vaultId, trigger }) => {
    console.log(`Vault ${vaultId} locked (trigger: ${trigger}).`);
  });
  await Vault.addListener('unlock', ({ vaultId }) => {
    console.log(`Vault ${vaultId} unlocked.`);
  });
};

// Permanently destroy the vault.
const destroy = async () => {
  await Vault.destroy();
};
```

## API

<docgen-index>

* [`clear(...)`](#clear)
* [`destroy(...)`](#destroy)
* [`exists(...)`](#exists)
* [`exportData(...)`](#exportdata)
* [`getKeys(...)`](#getkeys)
* [`getValue(...)`](#getvalue)
* [`importData(...)`](#importdata)
* [`initialize(...)`](#initialize)
* [`isEmpty(...)`](#isempty)
* [`isLocked(...)`](#islocked)
* [`lock(...)`](#lock)
* [`removeValue(...)`](#removevalue)
* [`setValue(...)`](#setvalue)
* [`unlock(...)`](#unlock)
* [`addListener('lock', ...)`](#addlistenerlock-)
* [`addListener('unlock', ...)`](#addlistenerunlock-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clear(...)

```typescript
clear(options?: ClearOptions | undefined) => Promise<void>
```

Remove all values from the vault while preserving its configuration.

The vault must be unlocked before calling this method.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#clearoptions">ClearOptions</a></code> |

**Since:** 0.1.0

--------------------


### destroy(...)

```typescript
destroy(options?: DestroyOptions | undefined) => Promise<void>
```

Destroy the vault, removing all values and its configuration.

After calling this method, the vault must be reinitialized before use.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#destroyoptions">DestroyOptions</a></code> |

**Since:** 0.1.0

--------------------


### exists(...)

```typescript
exists(options?: ExistsOptions | undefined) => Promise<ExistsResult>
```

Check whether a vault with the given identifier exists on the device.

Returns `true` for any vault that was previously created and has
not been destroyed, even if `initialize()` has not been called in
the current session. Useful for detecting whether a fresh setup or
an unlock flow is required.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#existsoptions">ExistsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#existsresult">ExistsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### exportData(...)

```typescript
exportData(options?: ExportDataOptions | undefined) => Promise<ExportDataResult>
```

Export all values from the vault as a key/value map.

The vault must be unlocked before calling this method.

**Note**: Designed for small datasets such as backup/restore flows.
The entire set of values is loaded into memory and serialized across
the Capacitor bridge in a single call. Avoid using this method on
vaults with large amounts of data to prevent out-of-memory errors.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#exportdataoptions">ExportDataOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#exportdataresult">ExportDataResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getKeys(...)

```typescript
getKeys(options?: GetKeysOptions | undefined) => Promise<GetKeysResult>
```

Get a list of all keys stored in the vault.

The vault must be unlocked before calling this method.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#getkeysoptions">GetKeysOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getkeysresult">GetKeysResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getValue(...)

```typescript
getValue(options: GetValueOptions) => Promise<GetValueResult>
```

Get the value associated with a key.

The vault must be unlocked before calling this method.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#getvalueoptions">GetValueOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getvalueresult">GetValueResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### importData(...)

```typescript
importData(options: ImportDataOptions) => Promise<void>
```

Import a key/value map into the vault, replacing all existing values.

The vault must be unlocked before calling this method. Any values
previously stored in the vault are removed before the new entries are
written. On partial failure, the vault may be left in an inconsistent
state — callers that need atomicity should call `exportData()` first
and re-import on failure.

**Note**: Designed for small datasets such as backup/restore flows.
The entire set of values is loaded into memory and serialized across
the Capacitor bridge in a single call. Avoid using this method on
vaults with large amounts of data to prevent out-of-memory errors.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#importdataoptions">ImportDataOptions</a></code> |

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize a vault with the given configuration.

Must be called once per session before any other method.

The `type` and `invalidateOnBiometricEnrollment` options are baked
into the encryption key at vault creation and cannot be changed
afterwards; values passed for existing vaults are silently ignored.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### isEmpty(...)

```typescript
isEmpty(options?: IsEmptyOptions | undefined) => Promise<IsEmptyResult>
```

Check whether the vault contains no values.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#isemptyoptions">IsEmptyOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isemptyresult">IsEmptyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isLocked(...)

```typescript
isLocked(options?: IsLockedOptions | undefined) => Promise<IsLockedResult>
```

Check whether the vault is currently locked.

Rejects with `VAULT_NOT_FOUND` if the vault has not been initialized
in the current session.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#islockedoptions">IsLockedOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#islockedresult">IsLockedResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### lock(...)

```typescript
lock(options?: LockOptions | undefined) => Promise<void>
```

Lock the vault.

Clears the in-memory encryption key. Stored values remain encrypted
on disk and are recoverable via `unlock()`.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#lockoptions">LockOptions</a></code> |

**Since:** 0.1.0

--------------------


### removeValue(...)

```typescript
removeValue(options: RemoveValueOptions) => Promise<void>
```

Remove the value associated with a key.

The vault must be unlocked before calling this method.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#removevalueoptions">RemoveValueOptions</a></code> |

**Since:** 0.1.0

--------------------


### setValue(...)

```typescript
setValue(options: SetValueOptions) => Promise<void>
```

Set the value associated with a key.

The vault must be unlocked before calling this method.

On **Web**, the value is stored unencrypted in `localStorage`.
This is for development purposes only and should NOT be used in production.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setvalueoptions">SetValueOptions</a></code> |

**Since:** 0.1.0

--------------------


### unlock(...)

```typescript
unlock(options?: UnlockOptions | undefined) => Promise<void>
```

Unlock the vault.

Prompts the user for biometric authentication or device credential,
depending on the vault's `type`.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#unlockoptions">UnlockOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('lock', ...)

```typescript
addListener(eventName: 'lock', listenerFunc: (event: LockEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for vault events.

| Param              | Type                                                                |
| ------------------ | ------------------------------------------------------------------- |
| **`eventName`**    | <code>'lock'</code>                                                 |
| **`listenerFunc`** | <code>(event: <a href="#lockevent">LockEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('unlock', ...)

```typescript
addListener(eventName: 'unlock', listenerFunc: (event: UnlockEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for vault events.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'unlock'</code>                                                   |
| **`listenerFunc`** | <code>(event: <a href="#unlockevent">UnlockEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners registered for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### ClearOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### DestroyOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### ExistsResult

| Prop         | Type                 | Description                                    | Since |
| ------------ | -------------------- | ---------------------------------------------- | ----- |
| **`exists`** | <code>boolean</code> | Whether or not the vault has been initialized. | 0.1.0 |


#### ExistsOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### ExportDataResult

| Prop       | Type                                    | Description                 | Since |
| ---------- | --------------------------------------- | --------------------------- | ----- |
| **`data`** | <code>{ [key: string]: string; }</code> | The exported key/value map. | 0.1.0 |


#### ExportDataOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### GetKeysResult

| Prop       | Type                  | Description                             | Since |
| ---------- | --------------------- | --------------------------------------- | ----- |
| **`keys`** | <code>string[]</code> | The keys currently stored in the vault. | 0.1.0 |


#### GetKeysOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### GetValueResult

| Prop        | Type                        | Description                                                            | Since |
| ----------- | --------------------------- | ---------------------------------------------------------------------- | ----- |
| **`value`** | <code>string \| null</code> | The retrieved value, or `null` if no value is associated with the key. | 0.1.0 |


#### GetValueOptions

| Prop          | Type                | Description                               | Default                | Since |
| ------------- | ------------------- | ----------------------------------------- | ---------------------- | ----- |
| **`key`**     | <code>string</code> | The key associated with the stored value. |                        | 0.1.0 |
| **`vaultId`** | <code>string</code> | The identifier of the vault.              | <code>'default'</code> | 0.1.0 |


#### ImportDataOptions

| Prop          | Type                                    | Description                                                                            | Default                | Since |
| ------------- | --------------------------------------- | -------------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`data`**    | <code>{ [key: string]: string; }</code> | The key/value map to import into the vault. Replaces all existing values in the vault. |                        | 0.1.0 |
| **`vaultId`** | <code>string</code>                     | The identifier of the vault.                                                           | <code>'default'</code> | 0.1.0 |


#### InitializeOptions

| Prop                                  | Type                                            | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | Default                | Since |
| ------------------------------------- | ----------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------- | ----- |
| **`cancelButtonText`**                | <code>string</code>                             | The text displayed on the cancel button of the authentication prompt. Only available on Android and iOS.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |                        | 0.1.0 |
| **`invalidateOnBiometricEnrollment`** | <code>boolean</code>                            | Whether the encryption key should be invalidated when the device's biometric set changes (e.g., a new fingerprint or face is enrolled). If `true` and the biometric set changes, the next `unlock()` call rejects with `KEY_INVALIDATED`. The app must call `destroy()` and reinitialize. Per-platform behavior: - **Android**: when biometric is involved, invalidates the entire encryption key. For `BIOMETRIC_OR_DEVICE_PASSCODE` vaults, the device passcode path is also rendered unusable. - **iOS**: when biometric is involved, invalidates the biometric branch only. For `BIOMETRIC_OR_DEVICE_PASSCODE` vaults, the user can still unlock with the device passcode. Only applies to vault types that use biometric authentication. **Note**: This option is baked into the encryption key at vault creation time. The value passed when reinitializing an existing vault is silently ignored. | <code>false</code>     | 0.1.0 |
| **`iosFallbackButtonText`**           | <code>string</code>                             | The text displayed on the fallback button of the authentication prompt. Only available on iOS.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |                        | 0.1.0 |
| **`lockAfterBackgrounded`**           | <code>number</code>                             | The duration in milliseconds the app must be backgrounded before the vault is automatically locked. If omitted, the vault is never automatically locked.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |                        | 0.1.0 |
| **`subtitle`**                        | <code>string</code>                             | The subtitle of the authentication prompt.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |                        | 0.1.0 |
| **`title`**                           | <code>string</code>                             | The title of the authentication prompt.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |                        | 0.1.0 |
| **`type`**                            | <code><a href="#vaulttype">VaultType</a></code> | The type of the vault.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |                        | 0.1.0 |
| **`vaultId`**                         | <code>string</code>                             | The identifier of the vault.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | <code>'default'</code> | 0.1.0 |


#### IsEmptyResult

| Prop          | Type                 | Description                           | Since |
| ------------- | -------------------- | ------------------------------------- | ----- |
| **`isEmpty`** | <code>boolean</code> | Whether the vault contains no values. | 0.1.0 |


#### IsEmptyOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### IsLockedResult

| Prop           | Type                 | Description                            | Since |
| -------------- | -------------------- | -------------------------------------- | ----- |
| **`isLocked`** | <code>boolean</code> | Whether the vault is currently locked. | 0.1.0 |


#### IsLockedOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### LockOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### RemoveValueOptions

| Prop          | Type                | Description                               | Default                | Since |
| ------------- | ------------------- | ----------------------------------------- | ---------------------- | ----- |
| **`key`**     | <code>string</code> | The key associated with the stored value. |                        | 0.1.0 |
| **`vaultId`** | <code>string</code> | The identifier of the vault.              | <code>'default'</code> | 0.1.0 |


#### SetValueOptions

| Prop          | Type                | Description                               | Default                | Since |
| ------------- | ------------------- | ----------------------------------------- | ---------------------- | ----- |
| **`key`**     | <code>string</code> | The key associated with the stored value. |                        | 0.1.0 |
| **`value`**   | <code>string</code> | The value to store.                       |                        | 0.1.0 |
| **`vaultId`** | <code>string</code> | The identifier of the vault.              | <code>'default'</code> | 0.1.0 |


#### UnlockOptions

| Prop          | Type                | Description                  | Default                | Since |
| ------------- | ------------------- | ---------------------------- | ---------------------- | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault. | <code>'default'</code> | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### LockEvent

| Prop          | Type                                                | Description                              | Since |
| ------------- | --------------------------------------------------- | ---------------------------------------- | ----- |
| **`trigger`** | <code><a href="#locktrigger">LockTrigger</a></code> | What caused the vault to lock.           | 0.1.0 |
| **`vaultId`** | <code>string</code>                                 | The identifier of the vault that locked. | 0.1.0 |


#### UnlockEvent

| Prop          | Type                | Description                                | Since |
| ------------- | ------------------- | ------------------------------------------ | ----- |
| **`vaultId`** | <code>string</code> | The identifier of the vault that unlocked. | 0.1.0 |


### Enums


#### VaultType

| Members                         | Value                                       | Description                                                                                                                                                                                                             | Since |
| ------------------------------- | ------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Biometric`**                 | <code>'BIOMETRIC'</code>                    | The vault is unlocked with the device's biometric authentication. Requires a Class 3 (Strong) biometric sensor on Android. Initialization rejects with `AUTHENTICATOR_UNAVAILABLE` if no Strong biometric is available. | 0.1.0 |
| **`BiometricOrDevicePasscode`** | <code>'BIOMETRIC_OR_DEVICE_PASSCODE'</code> | The vault is unlocked with either the device's biometric authentication or the device's credential (PIN, pattern, or password).                                                                                         | 0.1.0 |
| **`DevicePasscode`**            | <code>'DEVICE_PASSCODE'</code>              | The vault is unlocked with the device's credential (PIN, pattern, or password).                                                                                                                                         | 0.1.0 |


#### LockTrigger

| Members       | Value                  | Description                                                                                | Since |
| ------------- | ---------------------- | ------------------------------------------------------------------------------------------ | ----- |
| **`Manual`**  | <code>'MANUAL'</code>  | The vault was locked by an explicit call to `lock()`.                                      | 0.1.0 |
| **`Timeout`** | <code>'TIMEOUT'</code> | The vault was locked because the app was backgrounded longer than `lockAfterBackgrounded`. | 0.1.0 |

</docgen-api>

## FAQ

### Where is the data stored?

On Android, the encryption key is stored in the [Android Keystore](https://developer.android.com/privacy-and-security/keystore) and the encrypted values are stored in two `SharedPreferences` files per vault. On iOS, both the encryption key and the encrypted values are stored as separate [Keychain](https://developer.apple.com/documentation/security/keychain-services) items.

The encryption key is never included in cloud backups on either platform. On Android, the encrypted values in `SharedPreferences` are part of [Android Auto Backup](https://developer.android.com/identity/data/autobackup) by default, but the backed-up ciphertext is unusable on another device without the Keystore key; to exclude the preference files (`capawesome_capacitor_vault_key_<vaultId>.xml` and `capawesome_capacitor_vault_data_<vaultId>.xml`) from backup, see the [Android documentation](https://developer.android.com/identity/data/autobackup#IncludingFiles). On iOS, the Keychain items use the `*ThisDeviceOnly` accessibility classes, so they are never synced to iCloud Keychain or included in device backups.

### When should I use Vault instead of Secure Preferences or SQLite?

All three plugins protect data on the device, but they target different problems:

- **[Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/)** is a transparent key/value store. Values are encrypted at rest using the Android Keystore and iOS Keychain, but the app can read them at any time without prompting the user. Reach for it when you need to keep small bits of sensitive data around that the app itself accesses in the background — typical examples are OAuth refresh tokens, server-issued API keys, or preference flags that contain personal information.

- **[SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/)** is a full relational database with optional SQLCipher encryption. Use it when the shape of your data calls for queries, joins, indexes, or large record sets — for example, an offline-first app that syncs structured records, or anything you would otherwise model with a server-side database.

- **Vault** (this plugin) is a key/value store with an active lock state and biometric or device-passcode gating. The user has to unlock it before any read or write, and it locks again on demand or after a configurable background timeout. Reach for it when access to the data should require an explicit user action — a password manager's entries, an authenticator app's TOTP secrets, or the credentials sitting behind an "app lock" screen.

A quick decision tree:

- Need queries, relations, or large datasets? → **SQLite**.
- Need encrypted key/value storage the app can read freely in the background? → **Secure Preferences**.
- Need encrypted key/value storage the user must actively unlock with biometrics or a passcode? → **Vault**.

The three plugins are designed to coexist. A real-world app might use Secure Preferences for app-managed tokens, SQLite for synced records, and Vault for the master password that protects everything else.

### Is this plugin an alternative to Ionic Identity Vault?

Yes. This plugin was built as an alternative to [Ionic Identity Vault](https://ionic.io/products/identity-vault) and offers a similar feature set:

- Hardware-backed encryption using the Android Keystore and iOS Keychain
- Biometric, device passcode, or combined authentication
- Lockable session model with manual lock and auto-lock on background
- Multiple independent vaults per device
- Key invalidation when the device's biometric set changes

### How do I migrate from Ionic Identity Vault?

For an AI-assisted migration of your code, add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool:

```bash
npx skills add capawesome-team/skills --skill ionic-enterprise-sdk-migration
```

Then use the following prompt:

```
Use the `ionic-enterprise-sdk-migration` skill from `capawesome-team/skills` to migrate my project from Ionic Identity Vault to `@capawesome-team/capacitor-vault`.
```

Alternatively, if you want to perform the migration manually, you can follow the instructions in this blog post:[Alternative to the Ionic Identity Vault plugin](https://capawesome.io/blog/alternative-to-ionic-identity-vault-plugin/).

The stored data needs to be migrated at runtime while **both** plugins are still installed. Identity Vault exposes `exportVault()`, which returns a plain key/value map after the user unlocks the vault. That map has the exact shape expected by this plugin's [`importData(...)`](#importdata) method, so the two can be bridged directly:

```typescript
import { Vault, VaultType } from '@capawesome-team/capacitor-vault';
// Your existing Identity Vault instance.
import { vault as identityVault } from './identity-vault';

const MIGRATION_KEY = 'capacitor-vault-migrated';

const migrateFromIdentityVault = async () => {
  // Skip if the migration has already been performed.
  if (localStorage.getItem(MIGRATION_KEY)) {
    return;
  }
  // Nothing to migrate if the old vault is empty.
  if (await identityVault.isEmpty()) {
    localStorage.setItem(MIGRATION_KEY, 'true');
    return;
  }

  // Unlocking prompts the user to authenticate (e.g. via biometrics).
  await identityVault.unlock();
  const data = await identityVault.exportVault();

  // Initialize the new vault and import the data.
  await Vault.initialize({ type: VaultType.Biometric });
  await Vault.unlock();
  await Vault.importData({ data });

  // Clear the old vault and mark the migration as complete.
  await identityVault.clear();
  localStorage.setItem(MIGRATION_KEY, 'true');
};
```

Once all users have migrated (for example, after a release cycle in which everyone has opened the app at least once), you can remove the Identity Vault dependency in a follow-up release.

**Note**: The user has to authenticate once during the migration to unlock the old vault. This is unavoidable, since the data is protected by the device's biometric or passcode authentication by design.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vault/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vault/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/vault/LICENSE).
