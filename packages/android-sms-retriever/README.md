# Capacitor Android SMS Retriever Plugin

Capacitor plugin for OTP autofill on Android via the SMS User Consent and Phone Number Hint APIs.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 💬 **SMS User Consent**: Read an incoming verification SMS after a one-tap system consent dialog.
- 📞 **Phone Number Hint**: Prefill the user's phone number via the system bottom sheet.
- 🔒 **No SMS permissions**: Uses Play-policy-safe APIs that require no SMS permissions.
- 🤝 **Compatibility**: Works alongside the [Password Autofill](https://capawesome.io/docs/sdks/capacitor/password-autofill/), [SIM](https://capawesome.io/docs/sdks/capacitor/sim/) and [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/) plugins.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Android SMS Retriever plugin is typically used in phone number verification flows, for example:

- **OTP autofill**: Retrieve an incoming verification SMS after a one-tap system consent dialog and extract the one-time code, so the user doesn't have to type it manually.
- **Phone number prefill**: Prefill the phone number input field of your sign-up or login form via the system bottom sheet.
- **Play-policy-safe SMS verification**: Implement SMS-based verification without requesting any SMS permissions, using the SMS User Consent and Phone Number Hint APIs.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-android-sms-retriever` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-android-sms-retriever
npx cap sync
```

This plugin is only available on **Android**. On iOS and Web, all methods reject as unimplemented (see [iOS](#ios) below).

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$playServicesAuthVersion` version of `com.google.android.gms:play-services-auth` (default: `21.5.0`)
- `$playServicesAuthApiPhoneVersion` version of `com.google.android.gms:play-services-auth-api-phone` (default: `18.3.0`)

### iOS

This plugin has **no iOS implementation** and you don't need one. iOS already autofills one-time codes from incoming SMS messages in `WKWebView` when the input field uses `autocomplete="one-time-code"`:

```html
<input autocomplete="one-time-code" />
```

All methods reject as unimplemented on iOS.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to prefill the user's phone number and retrieve a verification SMS.

### Prefill the user's phone number

Request the user's phone number via the Phone Number Hint API. A system bottom sheet is displayed that lets the user pick one of the phone numbers associated with the device, for example to prefill a phone number input field. Only available on Android:

```typescript
import { AndroidSmsRetriever } from '@capawesome/capacitor-android-sms-retriever';

const requestPhoneNumber = async () => {
  const { phoneNumber } = await AndroidSmsRetriever.requestPhoneNumber();
  return phoneNumber;
};
```

### Retrieve a verification SMS

Retrieve an incoming verification SMS via the SMS User Consent API. A system consent dialog is displayed when a matching SMS is received, and the promise resolves with the full message text once the user consents, so your app can extract the one-time code itself (see [Extracting the One-Time Code](#extracting-the-one-time-code)). Only available on Android:

```typescript
import { AndroidSmsRetriever } from '@capawesome/capacitor-android-sms-retriever';

const retrieveSms = async () => {
  const { message } = await AndroidSmsRetriever.retrieveSms();
  // Extract the one-time code from the message.
  const code = message.match(/\d{6}/)?.[0];
  return code;
};
```

## API

<docgen-index>

* [`requestPhoneNumber()`](#requestphonenumber)
* [`retrieveSms(...)`](#retrievesms)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### requestPhoneNumber()

```typescript
requestPhoneNumber() => Promise<RequestPhoneNumberResult>
```

Request the user's phone number via the Phone Number Hint API.

A system bottom sheet is displayed that lets the user pick one of the
phone numbers associated with the device. The selected phone number is
returned so it can be used to prefill a phone number input field.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#requestphonenumberresult">RequestPhoneNumberResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### retrieveSms(...)

```typescript
retrieveSms(options?: RetrieveSmsOptions | undefined) => Promise<RetrieveSmsResult>
```

Retrieve an incoming verification SMS via the SMS User Consent API.

A system consent dialog is displayed when a matching SMS is received.
The promise resolves with the full message text once the user consents,
so the app can extract the one-time code itself.

The underlying broadcast waits up to 5 minutes for a matching SMS. If no
SMS is received within this time, the promise rejects with the error
code `TIMEOUT`.

Only available on Android.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#retrievesmsoptions">RetrieveSmsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#retrievesmsresult">RetrieveSmsResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### RequestPhoneNumberResult

| Prop              | Type                | Description                            | Since |
| ----------------- | ------------------- | -------------------------------------- | ----- |
| **`phoneNumber`** | <code>string</code> | The phone number selected by the user. | 0.1.0 |


#### RetrieveSmsResult

| Prop          | Type                | Description                                                                                                           | Since |
| ------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`message`** | <code>string</code> | The full text of the retrieved SMS message. The app is responsible for extracting the one-time code from the message. | 0.1.0 |


#### RetrieveSmsOptions

| Prop                    | Type                | Description                                                                                                           | Since |
| ----------------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`senderPhoneNumber`** | <code>string</code> | The phone number of the sender to filter incoming messages by. If not provided, the SMS from any sender is retrieved. | 0.1.0 |

</docgen-api>

## Extracting the One-Time Code

The `retrieveSms(...)` method resolves with the full text of the incoming SMS message. Your app is responsible for extracting the one-time code from the message, for example with a regular expression:

```typescript
const { message } = await AndroidSmsRetriever.retrieveSms();
const code = message.match(/\d{6}/)?.[0];
```

For the SMS User Consent API to detect a message, the SMS must:

- contain a one-time code that the user sends back to your server to complete the verification,
- be no longer than 140 bytes,
- not originate from a phone number in the user's contacts.

The underlying broadcast waits up to 5 minutes for a matching SMS. If no message is received within this time, the promise rejects with the error code `TIMEOUT`.

## FAQ

### How is this plugin different from other similar plugins?

It implements OTP autofill on Android through the SMS User Consent and Phone Number Hint APIs, so you can read a verification SMS and prefill the user's phone number without requesting any SMS permissions and while staying within Google Play policy. It's a focused, fully typed API that handles the system consent dialog and bottom sheet for you, and it's actively maintained against the latest Capacitor and Android versions. On iOS you don't need it at all — the WebView already autofills one-time codes — and the README explains exactly how.

### Does this plugin require any SMS permissions?

No, the plugin uses the SMS User Consent and Phone Number Hint APIs, which are Play-policy-safe and require no SMS permissions. Instead of reading SMS messages silently, the user explicitly consents via a one-tap system dialog before your app receives the message.

### Does this plugin work on iOS or Web?

No, this plugin only provides an Android implementation and you don't need one on iOS. iOS already autofills one-time codes from incoming SMS messages in `WKWebView` when the input field uses `autocomplete="one-time-code"`. On iOS and Web, all methods reject as unimplemented.

### Why does `retrieveSms` reject with the error code `TIMEOUT`?

The underlying broadcast waits up to 5 minutes for a matching SMS. If no matching message is received within this time, the promise rejects with the error code `TIMEOUT`. Make sure the SMS is sent while the broadcast is active and meets the requirements of the SMS User Consent API.

### Why is my SMS message not detected?

For the SMS User Consent API to detect a message, the SMS must contain a one-time code that the user sends back to your server to complete the verification, be no longer than 140 bytes, and not originate from a phone number in the user's contacts. See [Extracting the One-Time Code](#extracting-the-one-time-code) for more information.

### Can I filter incoming messages by sender?

Yes, you can pass the `senderPhoneNumber` option to the `retrieveSms(...)` method to only retrieve messages from a specific sender. If not provided, the SMS from any sender is retrieved.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Password Autofill](https://capawesome.io/docs/sdks/capacitor/password-autofill/): Save passwords to the platform credential store.
- [SIM](https://capawesome.io/docs/sdks/capacitor/sim/): Read SIM card and carrier information.
- [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/): Open the native SMS composer prefilled with recipients and a message body.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-sms-retriever/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-sms-retriever/LICENSE).
