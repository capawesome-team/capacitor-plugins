# Capacitor Mail Composer Plugin

Capacitor plugin to open the native email composer.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ✉️ **Compose**: Open the native email composer prefilled with recipients, subject and body.
- 📎 **Attachments**: Attach one or more files from the file system or from base64 strings.
- ✅ **Availability**: Check whether the device is able to compose and send emails.
- 🌐 **Cross-platform**: Supports Android, iOS and the web (via `mailto:`).
- 🤝 **Compatibility**: Works alongside the [File Picker](https://capawesome.io/docs/sdks/capacitor/file-picker/), [Phone Dialer](https://capawesome.io/docs/sdks/capacitor/phone-dialer/) and [SMS Composer](https://capawesome.io/docs/sdks/capacitor/sms-composer/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Mail Composer plugin is typically used whenever an app wants the user to send an email, for example:

- **Support and feedback**: Open a prefilled email to your support address with a subject and body so users can reach out with one tap.
- **Bug reports**: Attach log files or screenshots to a bug report email using the `attachments` option, either from the file system or directly from a base64 string.
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

Attachments that are provided as base64 strings are written to the cache directory of your app, which the default `res/xml/file_paths.xml` resource of a Capacitor app already covers with a `cache-path` entry.

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

### Compose an email with attachments

Attach a file from the file system using the `path` property, or attach a file that only exists in memory using the `data` and `name` properties. Attachments are not supported on the web:

```typescript
import { MailComposer } from '@capawesome/capacitor-mail-composer';

const composeMailWithAttachments = async () => {
  await MailComposer.composeMail({
    to: ['support@example.com'],
    subject: 'Bug report',
    attachments: [
      { path: '/path/to/screenshot.png' },
      { data: 'SGVsbG8gV29ybGQ=', name: 'log.txt' },
    ],
  });
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

| Prop              | Type                          | Description                                                                                                                                                                                         | Default            | Since |
| ----------------- | ----------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`attachments`** | <code>MailAttachment[]</code> | The files to attach to the email. Attachments are not supported on the web.                                                                                                                         |                    | 0.1.0 |
| **`bcc`**         | <code>string[]</code>         | The email addresses of the blind carbon copy (BCC) recipients.                                                                                                                                      |                    | 0.1.0 |
| **`body`**        | <code>string</code>           | The body of the email.                                                                                                                                                                              |                    | 0.1.0 |
| **`cc`**          | <code>string[]</code>         | The email addresses of the carbon copy (CC) recipients.                                                                                                                                             |                    | 0.1.0 |
| **`isHtml`**      | <code>boolean</code>          | Whether the body should be interpreted as HTML. **Note**: On Android, HTML is best-effort as many mail apps ignore it. On the web, HTML is not supported and the body is always sent as plain text. | <code>false</code> | 0.1.0 |
| **`subject`**     | <code>string</code>           | The subject of the email.                                                                                                                                                                           |                    | 0.1.0 |
| **`to`**          | <code>string[]</code>         | The email addresses of the primary recipients.                                                                                                                                                      |                    | 0.1.0 |


#### MailAttachment

| Prop       | Type                | Description                                                                                                                                                                                | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`data`** | <code>string</code> | The content of the file to attach, encoded as a base64 string. Either `data` or `path` must be provided. If both are provided, `path` is used.                                             | 0.2.0 |
| **`name`** | <code>string</code> | The file name of the attachment, including the file extension. Must be provided if `data` is provided. Ignored if `path` is provided, because the file name is then derived from the path. | 0.2.0 |
| **`path`** | <code>string</code> | The absolute file path or `file://` URI of the file to attach. Either `data` or `path` must be provided.                                                                                   | 0.2.0 |


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

### How do I attach a file that is not stored on the file system?

Provide the content of the file as a base64 string using the `data` property of an attachment and set the `name` property to the file name including the file extension. This is useful for files that only exist in memory, for example a log file that you keep in IndexedDB. The plugin takes care of handing the content over to the mail app, so you do not need to write the file to the file system yourself.

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
