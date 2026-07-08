# Capacitor Managed Configurations Plugin

Capacitor plugin to access managed configuration settings.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Managed Configurations plugin is typically used in enterprise apps that are distributed through an EMM or MDM solution, for example:

- **Preconfigured server settings**: Read a server URL or port that the organization's IT department has configured, instead of asking the user to enter it.
- **Policy-driven features**: Use boolean configuration entries to enable or disable features per deployment, such as allowing downloads on cellular connections.
- **Multi-tenant deployments**: Ship a single app to multiple organizations and adapt it at runtime based on the managed configuration values of each tenant.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-managed-configurations` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

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

Import the plugin and call its methods:

```typescript
import { ManagedConfigurations } from '@capawesome/capacitor-managed-configurations';
```

### Read managed configuration values

Fetch the value associated with a given key as a string, number, or boolean. The result contains `null` if no mapping exists for the given key. These methods are only available on Android and iOS:

```typescript
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

## FAQ

### What are managed configurations?

Managed configurations are settings that an organization's EMM or MDM solution pushes to an app, for example a server URL or a feature toggle. This plugin lets your Capacitor app read these settings at runtime using the `getString`, `getNumber`, and `getBoolean` methods.

### What happens if a configuration key does not exist?

The `getString`, `getNumber`, and `getBoolean` methods return a result whose `value` is `null` if no mapping exists for the given key. Your app should handle this case, for example by falling back to a default value.

### Do I need to declare the managed configurations in my app?

On Android, yes. You need to declare your app's managed configurations as described in [Define managed configurations](https://developer.android.com/work/managed-configurations#define-configuration) so that EMM solutions can discover and set them. See the [Installation](#installation) section for details.

### How can I test managed configurations during development?

On Android, you can set up a device owner testing environment as described in [Set up device owner for testing](https://source.android.com/devices/tech/admin/testing-setup#set_up_the_device_owner_for_testing). On iOS, the app must be installed as a managed app through an MDM solution. See the [Test your implementation](#test-your-implementation) section for details.

### Is the Web platform supported?

No, the `getString`, `getNumber`, and `getBoolean` methods are only available on Android and iOS. Managed configurations are a concept of mobile device management, which does not exist in the browser.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Securely store key/value pairs such as passwords, tokens or other sensitive information.
- [App Integrity](https://capawesome.io/docs/sdks/capacitor/app-integrity/): Verify app and device integrity using the Play Integrity API and App Attest.
- [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/): Detect rooted and jailbroken devices.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/managed-configurations/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/managed-configurations/LICENSE).

## Credits

This plugin is based on the [Capacitor Managed Configurations](https://github.com/capawesome-team/capacitor-managed-configurations) plugin.
Thanks to everyone who contributed to the project!
