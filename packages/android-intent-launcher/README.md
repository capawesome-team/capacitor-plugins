# Capacitor Android Intent Launcher Plugin

Capacitor plugin to launch arbitrary Android intents.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🚀 **Start activities**: Launch any Android activity via a custom intent and read its result.
- 🔍 **Resolve activities**: Check whether an activity can handle an intent before launching it.
- 🎯 **Explicit & implicit intents**: Target a specific component or let the system pick a handler.
- 📦 **Extras & flags**: Attach primitive extras and intent flags.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) and [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-android-intent-launcher` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-android-intent-launcher
npx cap sync
```

This plugin is only available on **Android**. On iOS and Web, all methods reject as unimplemented.

### Android

No additional configuration is required for this plugin.

However, on Android 11 (API level 30) and higher, [package visibility](https://developer.android.com/training/package-visibility) restricts which apps and intents your app can see. To launch or resolve intents that target other apps, you may need to declare matching [`<queries>`](https://developer.android.com/guide/topics/manifest/queries-element) entries in your app's `AndroidManifest.xml`. See [Package visibility](#package-visibility) below.

### iOS

This plugin has **no iOS implementation**. Intents are an Android-only concept. All methods reject as unimplemented on iOS.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AndroidIntentLauncher } from '@capawesome/capacitor-android-intent-launcher';

const startActivity = async () => {
  const { resultCode } = await AndroidIntentLauncher.startActivity({
    action: 'android.intent.action.VIEW',
    dataUri: 'https://capawesome.io',
  });
  return resultCode;
};

const canResolveActivity = async () => {
  const { canResolve } = await AndroidIntentLauncher.canResolveActivity({
    action: 'android.intent.action.VIEW',
    dataUri: 'https://capawesome.io',
  });
  return canResolve;
};
```

## API

<docgen-index>

* [`canResolveActivity(...)`](#canresolveactivity)
* [`startActivity(...)`](#startactivity)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### canResolveActivity(...)

```typescript
canResolveActivity(options: CanResolveActivityOptions) => Promise<CanResolveActivityResult>
```

Check whether an activity exists that can handle the given intent.

This is a wrapper around the `PackageManager.resolveActivity(...)` API.

On Android 11 (API level 30) and higher, the result is affected by
package visibility. Your app may need to declare matching `&lt;queries&gt;`
entries in its `AndroidManifest.xml` for the intent to be resolved.

Only available on Android.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startactivityoptions">StartActivityOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#canresolveactivityresult">CanResolveActivityResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### startActivity(...)

```typescript
startActivity(options: StartActivityOptions) => Promise<StartActivityResult>
```

Launch an activity for the given intent.

The intent is started via the `startActivityForResult(...)` API so the
result code and result data of the launched activity are returned once it
finishes.

This is the power-user escape hatch for system screens and app
integrations that no dedicated plugin covers. Prefer a typed plugin (such
as [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/)
or [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/))
where one exists.

Only available on Android.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startactivityoptions">StartActivityOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#startactivityresult">StartActivityResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CanResolveActivityResult

| Prop             | Type                 | Description                                                   | Since |
| ---------------- | -------------------- | ------------------------------------------------------------- | ----- |
| **`canResolve`** | <code>boolean</code> | Whether or not an activity exists that can handle the intent. | 0.1.0 |


#### StartActivityOptions

| Prop              | Type                                                         | Description                                                                                                                                                                                                                                            | Since |
| ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`action`**      | <code>string</code>                                          | The action of the intent.                                                                                                                                                                                                                              | 0.1.0 |
| **`categories`**  | <code>string[]</code>                                        | The categories to add to the intent.                                                                                                                                                                                                                   | 0.1.0 |
| **`className`**   | <code>string</code>                                          | The fully qualified class name of the component to launch. Must be used together with the `packageName` property to create an explicit intent that targets a specific component.                                                                       | 0.1.0 |
| **`dataUri`**     | <code>string</code>                                          | The data URI of the intent.                                                                                                                                                                                                                            | 0.1.0 |
| **`extras`**      | <code>{ [key: string]: string \| number \| boolean; }</code> | The extras to add to the intent. Only primitive values (string, number and boolean) are supported.                                                                                                                                                     | 0.1.0 |
| **`flags`**       | <code>number</code>                                          | The flags to add to the intent. Multiple flags can be combined using the bitwise OR operator.                                                                                                                                                          | 0.1.0 |
| **`packageName`** | <code>string</code>                                          | The package name of the component to launch. If used without the `className` property, the intent is restricted to the given package. If used together with the `className` property, an explicit intent that targets a specific component is created. | 0.1.0 |
| **`type`**        | <code>string</code>                                          | The MIME type of the intent data.                                                                                                                                                                                                                      | 0.1.0 |


#### StartActivityResult

| Prop             | Type                        | Description                                                                                                                                                                                                                        | Since |
| ---------------- | --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`dataUri`**    | <code>string \| null</code> | The data URI returned by the launched activity.                                                                                                                                                                                    | 0.1.0 |
| **`resultCode`** | <code>number</code>         | The result code returned by the launched activity. The value is `-1` if the activity finished successfully (`RESULT_OK`), `0` if it was canceled (`RESULT_CANCELED`) or any other custom result code set by the launched activity. | 0.1.0 |


### Type Aliases


#### CanResolveActivityOptions

<code><a href="#startactivityoptions">StartActivityOptions</a></code>

</docgen-api>

## Common Intents

The following table lists a few common intents to get you started. See the [Android documentation](https://developer.android.com/reference/android/content/Intent) for the full list of actions, categories, extras and flags.

| Use case          | `action`                          | `dataUri`                | `type`         | `extras`                                                     |
| ----------------- | --------------------------------- | ------------------------ | -------------- | ----------------------------------------------------------- |
| Open a website    | `android.intent.action.VIEW`      | `https://capawesome.io`  | –              | –                                                           |
| Open the dialer   | `android.intent.action.DIAL`      | `tel:+12025550123`       | –              | –                                                           |
| Compose an email  | `android.intent.action.SENDTO`    | `mailto:hi@example.com`  | –              | `{ 'android.intent.extra.SUBJECT': 'Hi' }`                  |
| Share plain text  | `android.intent.action.SEND`      | –                        | `text/plain`   | `{ 'android.intent.extra.TEXT': 'Hello world!' }`           |
| Open app settings | `android.settings.APPLICATION_DETAILS_SETTINGS` | `package:com.example.app` | – | –                                                           |

This plugin is intended as a **last resort** for system screens and app integrations that no dedicated plugin covers. Prefer a typed plugin where one exists — for example [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) for system settings screens or [App Launcher](https://capawesome.io/docs/sdks/capacitor/app-launcher/) for opening URLs and other apps.

## Package Visibility

On Android 11 (API level 30) and higher, [package visibility](https://developer.android.com/training/package-visibility) limits which other apps your app can interact with. This affects both `startActivity(...)` (an unresolvable intent rejects with the error code `ACTIVITY_NOT_FOUND`) and `canResolveActivity(...)` (which returns `false` for intents your app cannot see).

If you launch or resolve intents that target other apps, declare the intents you query in your app's `AndroidManifest.xml`. The plugin cannot predeclare arbitrary intents on your behalf.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
  <queries>
    <intent>
      <action android:name="android.intent.action.VIEW" />
      <data android:scheme="https" />
    </intent>
  </queries>
</manifest>
```

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-intent-launcher/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-intent-launcher/LICENSE).
