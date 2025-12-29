# @capawesome/capacitor-superwall

Unofficial Capacitor plugin for [Superwall SDK](https://superwall.com/).[^1]

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.0.x          | >=8.x.x           | Active support |

## Installation

```bash
npm install @capawesome/capacitor-superwall
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$superwallAndroidVersion` version of `com.superwall.sdk:superwall-android` (default: `2.6.6`)

#### Manifest

Add the required activities to your `AndroidManifest.xml` file:

```xml
<activity
  android:name="com.superwall.sdk.paywall.view.SuperwallPaywallActivity"
  android:theme="@style/Theme.MaterialComponents.DayNight.NoActionBar"
  android:configChanges="orientation|screenSize|keyboardHidden">
</activity>
<activity android:name="com.superwall.sdk.debug.DebugViewActivity" />
<activity android:name="com.superwall.sdk.debug.localizations.SWLocalizationActivity" />
<activity android:name="com.superwall.sdk.debug.SWConsoleActivity" />
```

Set your app's theme in the `android:theme` section of the `SuperwallPaywallActivity`.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
```

## API

<docgen-index>

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/superwall/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/superwall/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Nest 22, Inc. or any of their affiliates or subsidiaries.
