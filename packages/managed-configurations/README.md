# @capawesome/capacitor-managed-configurations

Capacitor plugin to access managed configuration settings.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-managed-configurations
npx cap sync
```

### Android

See [Define managed configurations](https://developer.android.com/work/managed-configurations#define-configuration) and follow the instructions to declare the app's managed configurations correctly.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { ManagedConfigurations } from '@capawesome/capacitor-managed-configurations';

const getString = async () => {
  const result = await ManagedConfigurations.getString({ key: 'server_url' });
  return result.value;
};

const getNumber = async () => {
  const result = await ManagedConfigurations.getNumber({ key: 'server_port' });
  return result.value;
};

const getBoolean = async () => {
  const result = await ManagedConfigurations.getBoolean({
    key: 'download_on_cellular',
  });
  return result.value;
};
```

## API

<docgen-index>

* [`getString(...)`](#getstring)
* [`getNumber(...)`](#getnumber)
* [`getBoolean(...)`](#getboolean)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getString(...)

```typescript
getString(options: GetOptions) => Promise<GetResult<string>>
```

Fetches the value associated with the given key, or `null` if no mapping exists for the given key.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&lt;string&gt;&gt;</code>

--------------------


### getNumber(...)

```typescript
getNumber(options: GetOptions) => Promise<GetResult<number>>
```

Fetches the value associated with the given key, or `null` if no mapping exists for the given key.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&lt;number&gt;&gt;</code>

--------------------


### getBoolean(...)

```typescript
getBoolean(options: GetOptions) => Promise<GetResult<boolean>>
```

Fetches the value associated with the given key, or `null` if no mapping exists for the given key.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#getoptions">GetOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&lt;boolean&gt;&gt;</code>

--------------------


### Interfaces


#### GetResult

| Prop        | Type                   | Description                                                                             |
| ----------- | ---------------------- | --------------------------------------------------------------------------------------- |
| **`value`** | <code>T \| null</code> | The value of the configuration entry, or `null` if no mapping exists for the given key. |


#### GetOptions

| Prop      | Type                | Description                             |
| --------- | ------------------- | --------------------------------------- |
| **`key`** | <code>string</code> | Unique key for the configuration entry. |

</docgen-api>

## Test your implementation

On **Android**, see [Set up device owner for testing](https://source.android.com/devices/tech/admin/testing-setup#set_up_the_device_owner_for_testing) and follow the instructions to set up a device owner testing environment.

On **iOS**, you need to install the app as a [managed app](https://support.apple.com/de-de/guide/deployment-reference-ios/iorf4d72eded/web) with a MDM solution.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/managed-configurations/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/managed-configurations/LICENSE).

## Credits

This plugin is based on the [Capacitor Managed Configurations](https://github.com/capawesome-team/capacitor-managed-configurations) plugin.
Thanks to everyone who contributed to the project!
