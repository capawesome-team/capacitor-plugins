# Capacitor SMS Composer Plugin

Capacitor plugin to open the native SMS composer prefilled with recipients and a message body.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✉️ **Compose**: Open the native SMS composer prefilled with recipients and a message body.
- ✅ **Capability check**: Check whether the device is able to compose and send SMS messages.
- 🔒 **Privacy-friendly**: The user always reviews and sends the message. The plugin never sends SMS on its own.
- 🤝 **Compatibility**: Works alongside the [Android SMS Retriever](https://capawesome.io/docs/sdks/capacitor/android-sms-retriever/), [Contacts](https://capawesome.io/docs/sdks/capacitor/contacts/) and [Mail Composer](https://capawesome.io/docs/sdks/capacitor/mail-composer/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The SMS Composer plugin is typically used whenever an app wants to hand off a prefilled text message to the user, for example:

- **Invite friends**: Prefill an invitation message with a download link that users can send to their contacts.
- **Share content**: Let users share an article, a product, or a coupon code via text message.
- **Contact support**: Open the composer prefilled with your support phone number and a message template.
- **Feature detection**: Hide SMS-related features on devices without SMS capability, such as Wi-Fi-only tablets, using `canComposeSms`.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-sms-composer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-sms-composer
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

### Android

No additional configuration is required. The plugin declares the necessary `<queries>` element for the `smsto` scheme in its `AndroidManifest.xml`, so the SMS capability can be detected on Android 11 (API level 30) and higher.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether the device can send SMS messages and open the native SMS composer.

### Check whether the device can send SMS messages

Check whether the device is able to compose and send SMS messages, for example to hide SMS-related features on devices without SMS capability such as Wi-Fi-only tablets or iPads. Only available on Android and iOS:

```typescript
import { SmsComposer } from '@capawesome/capacitor-sms-composer';

const canComposeSms = async () => {
  const { canCompose } = await SmsComposer.canComposeSms();
  return canCompose;
};
```

### Open the SMS composer

Open the native SMS composer prefilled with recipients and a message body. The user reviews the message and decides whether to send it; the plugin never sends the message itself. The call resolves with a `status` once the composer is dismissed (see [Result Status](#result-status)). Only available on Android and iOS:

```typescript
import { SmsComposer } from '@capawesome/capacitor-sms-composer';

const composeSms = async () => {
  const { status } = await SmsComposer.composeSms({
    recipients: ['+41791234567'],
    body: 'Hello from Capacitor!',
  });
  return status;
};
```

## API

<docgen-index>

* [`canComposeSms()`](#cancomposesms)
* [`composeSms(...)`](#composesms)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### canComposeSms()

```typescript
canComposeSms() => Promise<CanComposeSmsResult>
```

Check whether the device is able to compose and send SMS messages.

On devices without SMS capability (e.g. Wi-Fi-only tablets or iPads),
this resolves with `canCompose` set to `false`.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#cancomposesmsresult">CanComposeSmsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### composeSms(...)

```typescript
composeSms(options: ComposeSmsOptions) => Promise<ComposeSmsResult>
```

Open the native SMS composer prefilled with the given recipients and body.

The user reviews the message and decides whether to send it. The plugin
never sends the message itself.

The call resolves once the composer is dismissed. If the device is not
able to compose SMS messages, the call rejects as unavailable.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#composesmsoptions">ComposeSmsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#composesmsresult">ComposeSmsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CanComposeSmsResult

| Prop             | Type                 | Description                                                  | Since |
| ---------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`canCompose`** | <code>boolean</code> | Whether the device is able to compose and send SMS messages. | 0.1.0 |


#### ComposeSmsResult

| Prop         | Type                                                          | Description                                                                                                                                                        | Since |
| ------------ | ------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`status`** | <code><a href="#smscomposestatus">SmsComposeStatus</a></code> | The status of the SMS composer after it was dismissed. On Android, the status is always `unknown` because the system does not report whether the message was sent. | 0.1.0 |


#### ComposeSmsOptions

| Prop             | Type                  | Description                                                | Since |
| ---------------- | --------------------- | ---------------------------------------------------------- | ----- |
| **`body`**       | <code>string</code>   | The message body to prefill in the composer.               | 0.1.0 |
| **`recipients`** | <code>string[]</code> | The recipients (phone numbers) to prefill in the composer. | 0.1.0 |


### Type Aliases


#### SmsComposeStatus

The status of the SMS composer after it was dismissed.

- `sent`: The user sent the message.
- `canceled`: The user canceled the composer without sending the message.
- `unknown`: The result is unknown. This is always the case on Android.

<code>'canceled' | 'sent' | 'unknown'</code>

</docgen-api>

## Result Status

The `composeSms(...)` method resolves with a `status` once the composer is dismissed:

- On **iOS**, the status reflects the actual outcome (`sent`, `canceled`).
- On **Android**, the status is always `unknown` because the system does not report whether the message was sent.

## Multiple Recipients

Multiple recipients are joined with a semicolon (`;`) in the underlying `smsto:` URI on Android. Some messaging apps expect a comma (`,`) instead, so multi-recipient prefilling may not work reliably across all Android messaging apps.

## FAQ

### How is this plugin different from other similar plugins?

It opens the native SMS composer prefilled with recipients and a message body through a fully typed API, with a `canComposeSms()` check so you can hide the feature on devices without SMS capability such as Wi-Fi-only tablets. The user always reviews and sends the message, so no SMS permissions are needed, and on iOS you get the real send status back. If you only need a basic `smsto:` link, that's simple enough; if you want capability detection and a consistent composer across Android and iOS, this plugin is designed for exactly that.

### Does the plugin send SMS messages automatically?

No, the plugin never sends SMS messages on its own. It opens the native SMS composer prefilled with the recipients and message body you provide, and the user always reviews the message and decides whether to send it. This makes the plugin privacy-friendly and avoids the need for any SMS permissions.

### Why is the status always `unknown` on Android?

On Android, the system does not report whether the message was sent after the composer is dismissed, so the `status` is always `unknown`. On iOS, the status reflects the actual outcome (`sent` or `canceled`). See [Result Status](#result-status) for details.

### How can I detect devices that cannot send SMS messages?

Call the `canComposeSms` method, which resolves with `canCompose` set to `false` on devices without SMS capability, such as Wi-Fi-only tablets or iPads. If you call `composeSms` on such a device anyway, the call rejects as unavailable.

### Why does prefilling multiple recipients not work in some Android messaging apps?

On Android, multiple recipients are joined with a semicolon (`;`) in the underlying `smsto:` URI, but some messaging apps expect a comma (`,`) instead. Multi-recipient prefilling may therefore not work reliably across all Android messaging apps. See [Multiple Recipients](#multiple-recipients) for details.

### Is any additional configuration required on Android?

No, the plugin works out of the box. It declares the necessary `<queries>` element for the `smsto` scheme in its own `AndroidManifest.xml`, so the SMS capability can be detected on Android 11 (API level 30) and higher without manual manifest changes.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Android SMS Retriever](https://capawesome.io/docs/sdks/capacitor/android-sms-retriever/): OTP autofill on Android via the SMS User Consent and Phone Number Hint APIs.
- [Contacts](https://capawesome.io/docs/sdks/capacitor/contacts/): Read, write, or select device contacts.
- [Mail Composer](https://capawesome.io/docs/sdks/capacitor/mail-composer/): Open the native email composer.
- [Phone Dialer](https://capawesome.io/docs/sdks/capacitor/phone-dialer/): Open the native phone dialer prefilled with a phone number.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sms-composer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sms-composer/LICENSE).
