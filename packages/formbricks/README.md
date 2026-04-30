# @capawesome/capacitor-formbricks

Unofficial Capacitor plugin for [Formbricks](https://formbricks.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

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

```typescript
import { Formbricks } from '@capawesome/capacitor-formbricks';

const setup = async () => {
  await Formbricks.setup({
    appUrl: 'https://app.formbricks.com',
    environmentId: 'YOUR_ENVIRONMENT_ID',
  });
};

const setUserId = async () => {
  await Formbricks.setUserId({
    userId: 'user-123',
  });
};

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

const setLanguage = async () => {
  await Formbricks.setLanguage({
    language: 'de',
  });
};

const track = async () => {
  await Formbricks.track({
    action: 'button_pressed',
  });
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/formbricks/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/formbricks/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Formbricks GmbH or any of their affiliates or subsidiaries.
