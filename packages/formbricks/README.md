# Capacitor Formbricks Plugin

Unofficial Capacitor plugin for [Formbricks](https://formbricks.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Formbricks plugin is typically used to collect user feedback directly in your app, for example:

- **In-app surveys**: Track actions like a button press to trigger Formbricks surveys at the right moment.
- **Personalized feedback**: Identify users with a user ID to connect survey responses to specific users.
- **Targeted surveys**: Set user attributes such as plan or tier to show surveys only to specific user segments.
- **Localized surveys**: Set the survey language to show surveys in the user's language.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-formbricks` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-formbricks @formbricks/js
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$formbricksAndroidVersion` version of `com.formbricks:android` (default: `1.2.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

#### Data Binding

The Formbricks Android SDK requires Data Binding to be enabled in your app's `build.gradle` file:

```groovy
android {
    buildFeatures {
        dataBinding true
    }
}
```

### iOS

#### Minimum Deployment Target

If you are using **Swift Package Manager**, make sure that your iOS deployment target is set to at least `16.6` in your Xcode project settings (usually in `ios/App/App.xcodeproj`):

```diff
-IPHONEOS_DEPLOYMENT_TARGET = 15.0
+IPHONEOS_DEPLOYMENT_TARGET = 16.6
```

If you are using **CocoaPods**, make sure that your iOS deployment target is set to at least `16.6` in your `Podfile`:

```ruby
platform :ios, '16.6'
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to set up the SDK, identify the user, set user attributes, set the survey language, track an action, and log out the user.

### Set up the SDK

Set up the Formbricks SDK with the URL of your Formbricks instance and the environment ID of your Formbricks project. This method must be called before any other method:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const setup = async () => {
  await Formbricks.setup({
    appUrl: 'https://app.formbricks.com',
    environmentId: 'YOUR_ENVIRONMENT_ID',
  });
};
```

### Identify the user

Set the user ID of the current user to connect survey responses to that user:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const setUserId = async () => {
  await Formbricks.setUserId({
    userId: 'user-123',
  });
};
```

### Set user attributes

Set a single attribute with `setAttribute(...)` or multiple attributes at once with `setAttributes(...)`, for example to target surveys to specific user segments:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const setAttribute = async () => {
  await Formbricks.setAttribute({
    key: 'plan',
    value: 'pro',
  });
};

const setAttributes = async () => {
  await Formbricks.setAttributes({
    attributes: {
      plan: 'pro',
      tier: 'gold',
    },
  });
};
```

### Set the survey language

Set the language in which surveys are displayed:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const setLanguage = async () => {
  await Formbricks.setLanguage({
    language: 'de',
  });
};
```

### Track an action

Track an action that may trigger a survey:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const track = async () => {
  await Formbricks.track({
    action: 'button_pressed',
  });
};
```

### Log out the user

Log out the current user and clear all attributes:

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const logout = async () => {
  await Formbricks.logout();
};
```

## API

<docgen-index>

* [`logout()`](#logout)
* [`setAttribute(...)`](#setattribute)
* [`setAttributes(...)`](#setattributes)
* [`setLanguage(...)`](#setlanguage)
* [`setUserId(...)`](#setuserid)
* [`setup(...)`](#setup)
* [`track(...)`](#track)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### logout()

```typescript
logout() => Promise<void>
```

Log out the current user and clear all attributes.

**Since:** 0.1.0

--------------------


### setAttribute(...)

```typescript
setAttribute(options: SetAttributeOptions) => Promise<void>
```

Set a single user attribute.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setattributeoptions">SetAttributeOptions</a></code> |

**Since:** 0.1.0

--------------------


### setAttributes(...)

```typescript
setAttributes(options: SetAttributesOptions) => Promise<void>
```

Set multiple user attributes at once.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setattributesoptions">SetAttributesOptions</a></code> |

**Since:** 0.1.0

--------------------


### setLanguage(...)

```typescript
setLanguage(options: SetLanguageOptions) => Promise<void>
```

Set the survey language.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#setlanguageoptions">SetLanguageOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUserId(...)

```typescript
setUserId(options: SetUserIdOptions) => Promise<void>
```

Set the user ID of the current user.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setuseridoptions">SetUserIdOptions</a></code> |

**Since:** 0.1.0

--------------------


### setup(...)

```typescript
setup(options: SetupOptions) => Promise<void>
```

Setup the Formbricks SDK with the provided options.

**Attention**: This method must be called before any other method.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#setupoptions">SetupOptions</a></code> |

**Since:** 0.1.0

--------------------


### track(...)

```typescript
track(options: TrackOptions) => Promise<void>
```

Track an action that may trigger a survey.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#trackoptions">TrackOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### SetAttributeOptions

| Prop        | Type                | Description                 | Since |
| ----------- | ------------------- | --------------------------- | ----- |
| **`key`**   | <code>string</code> | The name of the attribute.  | 0.1.0 |
| **`value`** | <code>string</code> | The value of the attribute. | 0.1.0 |


#### SetAttributesOptions

| Prop             | Type                                    | Description            | Since |
| ---------------- | --------------------------------------- | ---------------------- | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | The attributes to set. | 0.1.0 |


#### SetLanguageOptions

| Prop           | Type                | Description                                           | Since |
| -------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`language`** | <code>string</code> | The language code of the survey language (e.g. `de`). | 0.1.0 |


#### SetUserIdOptions

| Prop         | Type                | Description                      | Since |
| ------------ | ------------------- | -------------------------------- | ----- |
| **`userId`** | <code>string</code> | The user ID of the current user. | 0.1.0 |


#### SetupOptions

| Prop                | Type                | Description                                    | Since |
| ------------------- | ------------------- | ---------------------------------------------- | ----- |
| **`appUrl`**        | <code>string</code> | The URL of your Formbricks instance.           | 0.1.0 |
| **`environmentId`** | <code>string</code> | The environment ID of your Formbricks project. | 0.1.0 |


#### TrackOptions

| Prop         | Type                | Description                      | Since |
| ------------ | ------------------- | -------------------------------- | ----- |
| **`action`** | <code>string</code> | The name of the action to track. | 0.1.0 |

</docgen-api>

## FAQ

### Can I use this plugin with a self-hosted Formbricks instance?

Yes, the `appUrl` option of the `setup(...)` method takes the URL of your Formbricks instance. Simply pass the URL of your self-hosted instance instead of `https://app.formbricks.com`.

### Why is no survey shown after calling `track`?

Make sure that `setup(...)` has been called first, since it must be called before any other method. Also note that `track(...)` only tracks an action that *may* trigger a survey. Whether a survey is actually shown depends on how the survey is configured in your Formbricks project.

### Why does my iOS build fail after installing the plugin?

The Formbricks iOS SDK requires an iOS deployment target of at least `16.6`. Make sure that your deployment target is set accordingly in your Xcode project settings or `Podfile`, as described in the [Installation](#installation) section.

### Why does my Android build fail after installing the plugin?

The Formbricks Android SDK requires Data Binding to be enabled in your app's `build.gradle` file. See the [Installation](#installation) section for the required configuration.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Review](https://capawesome.io/docs/sdks/capacitor/app-review/): Let users submit app store reviews and ratings.
- [Grafana Faro](https://capawesome.io/docs/sdks/capacitor/grafana-faro/): Unofficial Capacitor plugin for Grafana Faro.
- [Intercom](https://capawesome.io/docs/sdks/capacitor/intercom/): Unofficial Capacitor plugin for the Intercom live chat and customer support platform.
- [PostHog](https://capawesome.io/docs/sdks/capacitor/posthog/): Unofficial Capacitor plugin for PostHog.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/formbricks/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/formbricks/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Formbricks GmbH or any of their affiliates or subsidiaries.
