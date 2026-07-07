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
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

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

```typescript
import { MailComposer } from '@capawesome/capacitor-mail-composer';

const canComposeMail = async () => {
  const { canCompose } = await MailComposer.canComposeMail();
  return canCompose;
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/mail-composer/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/mail-composer/LICENSE).
