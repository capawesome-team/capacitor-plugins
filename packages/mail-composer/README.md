# Capacitor Mail Composer Plugin

Capacitor plugin to open the native email composer.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✉️ **Compose**: Open the native email composer prefilled with recipients, subject and body.
- 📎 **Attachments**: Attach one or more files to the email.
- ✅ **Availability**: Check whether the device is able to compose and send emails.
- 🌐 **Cross-platform**: Supports Android, iOS and the web (via `mailto:`).
- 🤝 **Compatibility**: Works alongside the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/), [Phone Dialer](https://capawesome.io/docs/sdks/capacitor/phone-dialer/) and [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Mail Composer plugin is typically used whenever an app wants the user to send an email, for example:

- **Support and feedback**: Open a prefilled email to your support address with a subject and body so users can reach out with one tap.
- **Bug reports**: Attach log files or screenshots to a bug report email using the `attachments` option.
- **Sharing content**: Let users share content from your app via email with prefilled recipients, subject, and body.
- **Invitations**: Prefill invitation emails with CC and BCC recipients that the user only needs to review and send.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-mail-composer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-mail-composer
npx cap sync
```

### Android

#### Attachments

To attach files, the plugin uses the [`FileProvider`](https://developer.android.com/reference/androidx/core/content/FileProvider) that Capacitor already registers for your app (authority `${applicationId}.fileprovider`). Make sure that the directories your attachments are stored in are covered by the `res/xml/file_paths.xml` resource of your app. Otherwise, the `composeMail(...)` method will reject with an error.

### Web

On the web, the plugin builds a `mailto:` URL and opens it. This has the following limitations:

- Attachments are **not** supported and cause the `composeMail(...)` method to reject.
- HTML bodies are **not** supported. The body is always sent as plain text.
- The overall URL length is limited, so long bodies may be truncated.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether the device can send emails and how to open the native email composer.

### Check whether the device can send emails

Before opening the composer, check whether the device is able to compose and send emails. On iOS, this returns `true` only if a mail account is configured. On Android, it returns `true` if a mail app is installed. On the web, it always returns `true`:

```typescript
import { MailComposer } from '@capawesome/capacitor-mail-composer';

const canComposeMail = async () => {
  const { canCompose } = await MailComposer.canComposeMail();
  return canCompose;
};
```

### Compose an email

Open the native email composer prefilled with recipients, subject, and body. The user reviews the email and decides whether to send it. Note that on Android, the returned `status` is always `unknown`:

```typescript
import { MailComposer } from '@capawesome/capacitor-mail-composer';

const composeMail = async () => {
  const { status } = await MailComposer.composeMail({
    to: ['jane@example.com'],
    cc: ['john@example.com'],
    subject: 'Hello World',
    body: 'This is the body of the email.',
  });
  return status;
};
```

## API

<docgen-index>

* [`canComposeMail()`](#cancomposemail)
* [`composeMail(...)`](#composemail)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### canComposeMail()

```typescript
canComposeMail() => Promise<CanComposeMailResult>
```

Check whether the device is able to compose and send emails.

On iOS, this returns `true` only if a mail account is configured. On
Android, this returns `true` if a mail app is installed. On the web, this
always returns `true` (best effort, not verifiable).

**Returns:** <code>Promise&lt;<a href="#cancomposemailresult">CanComposeMailResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### composeMail(...)

```typescript
composeMail(options: ComposeMailOptions) => Promise<ComposeMailResult>
```

Open the native email composer prefilled with the provided data.

The user reviews the email and decides whether to send it. The plugin
never sends the email itself.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#composemailoptions">ComposeMailOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#composemailresult">ComposeMailResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CanComposeMailResult

| Prop             | Type                 | Description                                            | Since |
| ---------------- | -------------------- | ------------------------------------------------------ | ----- |
| **`canCompose`** | <code>boolean</code> | Whether the device is able to compose and send emails. | 0.1.0 |


#### ComposeMailResult

| Prop         | Type                                                            | Description                                                                                                                                                | Since |
| ------------ | --------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`status`** | <code><a href="#mailcomposestatus">MailComposeStatus</a></code> | The status of the email compose operation. **Note**: On Android, the status is always `unknown` because the mail intent does not return a reliable result. | 0.1.0 |


#### ComposeMailOptions

| Prop              | Type                  | Description                                                                                                                                                                                         | Default            | Since |
| ----------------- | --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`attachments`** | <code>string[]</code> | The absolute file paths or `file://` URIs of the files to attach. Attachments are not supported on the web.                                                                                         |                    | 0.1.0 |
| **`bcc`**         | <code>string[]</code> | The email addresses of the blind carbon copy (BCC) recipients.                                                                                                                                      |                    | 0.1.0 |
| **`body`**        | <code>string</code>   | The body of the email.                                                                                                                                                                              |                    | 0.1.0 |
| **`cc`**          | <code>string[]</code> | The email addresses of the carbon copy (CC) recipients.                                                                                                                                             |                    | 0.1.0 |
| **`isHtml`**      | <code>boolean</code>  | Whether the body should be interpreted as HTML. **Note**: On Android, HTML is best-effort as many mail apps ignore it. On the web, HTML is not supported and the body is always sent as plain text. | <code>false</code> | 0.1.0 |
| **`subject`**     | <code>string</code>   | The subject of the email.                                                                                                                                                                           |                    | 0.1.0 |
| **`to`**          | <code>string[]</code> | The email addresses of the primary recipients.                                                                                                                                                      |                    | 0.1.0 |


### Type Aliases


#### MailComposeStatus

The status of an email compose operation.

- `sent`: The email was sent.
- `saved`: The email was saved as a draft.
- `canceled`: The user canceled the operation.
- `unknown`: The status is unknown.

<code>'sent' | 'saved' | 'canceled' | 'unknown'</code>

</docgen-api>

## Platform Support

Keep the following platform-specific behavior in mind:

- **Status**: On Android, the `status` returned by `composeMail(...)` is always `unknown` because the mail intent does not return a reliable result. On iOS, the real status is returned via the delegate. On the web, the status is always `unknown`.
- **HTML body**: On Android, the `isHtml` option is best-effort because many mail apps ignore HTML content. On the web, HTML is not supported at all.
- **No mail account**: On iOS, `composeMail(...)` rejects as unavailable if no mail account is configured. Use `canComposeMail(...)` to check upfront.

## FAQ

### How is this plugin different from other similar plugins?

It opens the native email composer prefilled with recipients, subject, body, and CC and BCC, and supports file attachments and HTML bodies through a fully typed API on Android, iOS, and the Web. A `canComposeMail()` check lets you confirm the device can actually send mail before you show the feature, and the user always stays in control of sending. If you only need a plain `mailto:` link, a simpler approach is fine; if you need attachments, rich bodies, and reliable capability detection, this plugin is built for exactly that.

### Does the plugin send the email itself?

No, the plugin only opens the native email composer prefilled with the provided data. The user reviews the email and decides whether to send it. The email is always sent through the user's own mail app and account.

### Why is the status always `unknown` on Android?

On Android, the mail intent does not return a reliable result, so the `status` returned by `composeMail` is always `unknown`. On iOS, the real status (`sent`, `saved`, or `canceled`) is returned. On the web, the status is always `unknown` as well.

### Why does `composeMail` reject on iOS?

On iOS, `composeMail` rejects as unavailable if no mail account is configured on the device. Use `canComposeMail` upfront to check whether the device is able to compose and send emails, and only show your email feature if it returns `true`.

### Why does attaching a file fail on Android?

On Android, the plugin shares attachments through the [`FileProvider`](https://developer.android.com/reference/androidx/core/content/FileProvider) that Capacitor already registers for your app. If the directory a file is stored in is not covered by the `res/xml/file_paths.xml` resource of your app, `composeMail` rejects with an error. See the [Installation](#installation) section for details.

### Are attachments supported on the web?

No, on the web the plugin builds a `mailto:` URL, which cannot carry attachments, so `composeMail` rejects if attachments are provided. Also keep in mind that HTML bodies are not supported on the web and long bodies may be truncated due to the URL length limit.

### Can I send HTML emails?

Yes, set the `isHtml` option to `true` to have the body interpreted as HTML. On iOS, this is fully supported. On Android, it is best-effort because many mail apps ignore HTML content. On the web, HTML is not supported and the body is always sent as plain text.

## Related Plugins

- [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/): Let the user select files to attach to an email.
- [Phone Dialer](https://capawesome.io/docs/sdks/capacitor/phone-dialer/): Open the native phone dialer prefilled with a phone number.
- [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/): Open the native SMS composer prefilled with recipients and a message body.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/mail-composer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/mail-composer/LICENSE).
