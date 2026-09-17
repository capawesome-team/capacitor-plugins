# Capacitor Phone Dialer Plugin

Capacitor plugin to open the native phone dialer prefilled with a phone number.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📞 **Dial**: Open the native phone dialer prefilled with a phone number.
- ✅ **Capability check**: Check whether the device is able to open the phone dialer.
- 🔒 **Privacy-friendly**: The user always reviews the number and places the call. The plugin never calls on its own.
- 🤝 **Compatibility**: Works alongside the [Mail Composer](https://capawesome.io/docs/sdks/capacitor/mail-composer/) and [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Phone Dialer plugin is typically used whenever an app wants to offer a tap-to-call experience, for example:

- **Customer support**: Let users call your support hotline directly from a help or contact screen.
- **Business directories**: Let users call a listed business, restaurant, or practice with a single tap.
- **CRM and field service apps**: Let users call customers or leads from a detail view, with the number prefilled in the dialer.
- **Capability-aware UI**: Use the `canDial()` method to hide or disable call buttons on devices without telephony capability, such as Wi-Fi-only tablets.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-phone-dialer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-phone-dialer
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On Web, all methods reject as unimplemented. Use an `<a href="tel:...">` link on the web instead.

### Android

No additional configuration is required. The plugin declares the necessary `<queries>` element for the `tel` scheme in its `AndroidManifest.xml`, so the phone dialer can be detected on Android 11 (API level 30) and higher.

### iOS

To be able to detect whether the device can open the phone dialer, add the `tel` scheme to the `LSApplicationQueriesSchemes` array in your app's `Info.plist` file:

```xml
<key>LSApplicationQueriesSchemes</key>
<array>
  <string>tel</string>
</array>
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether the device can open the phone dialer and how to open it with a prefilled number.

### Check whether the device can open the phone dialer

Use the `canDial()` method to check whether the device is able to open the phone dialer, for example to hide or disable call buttons. On devices without telephony capability (e.g. Wi-Fi-only tablets or iPod touch), it resolves with `false`. Only available on Android and iOS:

```typescript
import { PhoneDialer } from '@capawesome/capacitor-phone-dialer';

const canDial = async () => {
  const { canDial } = await PhoneDialer.canDial();
  return canDial;
};
```

### Open the phone dialer with a prefilled number

Use the `dial(...)` method to open the native phone dialer prefilled with the given number. The user reviews the number and decides whether to place the call. Only available on Android and iOS:

```typescript
import { PhoneDialer } from '@capawesome/capacitor-phone-dialer';

const dial = async () => {
  await PhoneDialer.dial({ number: '+41791234567' });
};
```

## API

<docgen-index>

* [`canDial()`](#candial)
* [`dial(...)`](#dial)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### canDial()

```typescript
canDial() => Promise<CanDialResult>
```

Check whether the device is able to open the phone dialer.

On devices without telephony capability (e.g. Wi-Fi-only tablets or
iPod touch), this resolves with `canDial` set to `false`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#candialresult">CanDialResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### dial(...)

```typescript
dial(options: DialOptions) => Promise<void>
```

Open the native phone dialer prefilled with the given number.

The user reviews the number and decides whether to place the call. The
plugin never places the call itself.

If the device is not able to open the phone dialer, the call rejects as
unavailable.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#dialoptions">DialOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### CanDialResult

| Prop          | Type                 | Description                                          | Since |
| ------------- | -------------------- | ---------------------------------------------------- | ----- |
| **`canDial`** | <code>boolean</code> | Whether the device is able to open the phone dialer. | 0.1.0 |


#### DialOptions

| Prop         | Type                | Description                                                                                                                                                                   | Since |
| ------------ | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`number`** | <code>string</code> | The phone number to prefill in the dialer. Only digits and the characters `+`, `*` and `#` are kept. All other characters (e.g. spaces or dashes) are removed before dialing. | 0.1.0 |

</docgen-api>

## Direct Calling

This plugin only **opens** the phone dialer prefilled with the number. It never places the call directly. This is a deliberate design decision:

- On **Android**, direct calling would require the `android.permission.CALL_PHONE` runtime permission and comes with additional Google Play policy requirements. The plugin uses `Intent.ACTION_DIAL`, which does not require any permission.
- On **iOS**, there is no public API to place a call without user confirmation. The plugin opens the `tel:` URL, which shows the system Call/Cancel confirmation.

## Number Sanitization

Before opening the dialer, the phone number is sanitized: all characters except digits and `+`, `*` and `#` are removed. If nothing remains after sanitization, the call rejects with an error.

## FAQ

### How is this plugin different from other similar plugins?

It opens the native phone dialer prefilled with a number on Android and iOS, and adds a `canDial()` capability check so you can hide or disable call buttons on devices without telephony, such as Wi-Fi-only tablets. It is privacy-friendly by design: the user always reviews the number and places the call, so on Android it needs no runtime permission by using `Intent.ACTION_DIAL`, and it sanitizes the number for you before dialing. The API is fully typed, supports both CocoaPods and Swift Package Manager on iOS, and is actively maintained against the latest Capacitor and OS versions.

### Can the plugin place a phone call directly without user confirmation?

No, this is a deliberate design decision. On Android, direct calling would require the `CALL_PHONE` runtime permission and comes with additional Google Play policy requirements, so the plugin uses `Intent.ACTION_DIAL` instead. On iOS, there is no public API to place a call without user confirmation. The plugin always opens the dialer prefilled with the number and the user decides whether to place the call.

### Does the plugin require any permissions?

No, the plugin does not require any runtime permissions. On Android, it uses `Intent.ACTION_DIAL`, which does not require any permission. On iOS, you only need to add the `tel` scheme to the `LSApplicationQueriesSchemes` array in your app's `Info.plist` file so that the `canDial()` method can detect whether the device can open the phone dialer (see [Installation](#installation)).

### Why does the `canDial` method return `false`?

The `canDial()` method resolves with `false` on devices without telephony capability, such as Wi-Fi-only tablets or iPod touch. On iOS, it also returns `false` if the `tel` scheme is missing from the `LSApplicationQueriesSchemes` array in your app's `Info.plist` file.

### What happens to spaces or dashes in the phone number?

Before opening the dialer, the phone number is sanitized: all characters except digits and `+`, `*` and `#` are kept, everything else (e.g. spaces or dashes) is removed. If nothing remains after sanitization, the call rejects with an error.

### Can I use this plugin on the Web?

No, the plugin is only available on Android and iOS. On the Web, all methods reject as unimplemented. Use an `<a href="tel:...">` link on the web instead.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Contacts](https://capawesome.io/docs/sdks/capacitor/contacts/): Read, write, or select device contacts, for example to look up a phone number before dialing.
- [Mail Composer](https://capawesome.io/docs/sdks/capacitor/mail-composer/): Open the native email composer.
- [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/): Open the native SMS composer prefilled with recipients and a message body.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/phone-dialer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/phone-dialer/LICENSE).
