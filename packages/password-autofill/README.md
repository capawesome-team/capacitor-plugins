# @capawesome/capacitor-password-autofill

Capacitor plugin for saving passwords to the platform credential store.

This plugin lets you **save into** the platform autofill system (iCloud Keychain on iOS, Google Password Manager on Android) after a successful in-app login. It does **not** fill forms — it solves the well-known problem that WebView-based apps never trigger the native "Save password?" prompt.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for password autofill. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🔐 **Credential saving**: Save a username and password to the platform credential store.
- 🎯 **Deterministic**: Trigger the save prompt explicitly after a successful login, even for non-form flows.
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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-password-autofill` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-password-autofill
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxCredentials` version of `androidx.credentials:credentials` and `androidx.credentials:credentials-play-services-auth` (default: `1.5.0`)

No further setup is required. On Android, saving a credential presents the system "Save password?" prompt provided by the active credential provider (e.g. Google Password Manager).

### iOS

The Shared Web Credentials API requires the **Associated Domains** capability and a matching `apple-app-site-association` (AASA) file. Without this setup, `savePassword(...)` cannot save the credential.

#### Associated Domains

Add the Associated Domains entitlement to your app and include a `webcredentials:` entry for each domain you want to save credentials for:

```xml
<key>com.apple.developer.associated-domains</key>
<array>
  <string>webcredentials:example.com</string>
</array>
```

The `domain` you pass to `savePassword(...)` must match one of these entries (without the `webcredentials:` prefix).

#### Apple App Site Association

Host an `apple-app-site-association` file at `https://example.com/.well-known/apple-app-site-association` that lists your app under the `webcredentials` service:

```json
{
  "webcredentials": {
    "apps": ["TEAMID.com.example.app"]
  }
}
```

Replace `TEAMID` with your Apple Developer Team ID and `com.example.app` with your app's bundle identifier. See the [Apple documentation](https://developer.apple.com/documentation/xcode/supporting-associated-domains) for more information.

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { PasswordAutofill } from '@capawesome/capacitor-password-autofill';

const savePassword = async () => {
  await PasswordAutofill.savePassword({
    domain: 'example.com',
    username: 'jane.doe@example.com',
    password: 'super-secret',
  });
};
```

## API

<docgen-index>

* [`savePassword(...)`](#savepassword)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### savePassword(...)

```typescript
savePassword(options: SavePasswordOptions) => Promise<void>
```

Save a username and password to the platform credential store.

On iOS, the credential is saved to the iCloud Keychain via the
Shared Web Credentials API. This requires the Associated Domains
entitlement with a `webcredentials:` entry and a matching
`apple-app-site-association` file hosted on your domain.

On Android, the credential is saved to the Google Password Manager
(or another provider) via the Credential Manager API. This presents
the system "Save password?" prompt to the user.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#savepasswordoptions">SavePasswordOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### SavePasswordOptions

| Prop           | Type                | Description                                                                                                                                                                                            | Since |
| -------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`domain`**   | <code>string</code> | The domain to associate the credential with. This must match one of the domains in your app's Associated Domains entitlement (without the `webcredentials:` prefix). Only available on iOS (required). | 0.1.0 |
| **`password`** | <code>string</code> | The password to save.                                                                                                                                                                                  | 0.1.0 |
| **`username`** | <code>string</code> | The username to save.                                                                                                                                                                                  | 0.1.0 |

</docgen-api>

## When to use this plugin vs. the official guide

The [official Capacitor autofill guide](https://capacitorjs.com/docs/guides/autofill-credentials) saves credentials by relying on the operating system's form heuristics. This requires changing `server.hostname` to your real domain, which:

- changes the WebView origin and therefore wipes existing users' `localStorage`, `IndexedDB`, and cookies, and
- can break OAuth redirect allowlists.

It also depends on iOS detecting a form submission followed by a navigation — something typical single-page app logins (`fetch()` + `preventDefault()`) never trigger.

This plugin takes a different approach: **deterministic credential saving without changing the WebView origin**. Your app keeps `localhost` as its origin, and you call `savePassword(...)` explicitly after a successful login. This also covers non-form flows such as social signup with generated passwords.

What this plugin does **not** replace:

- The Associated Domains / `apple-app-site-association` setup is required in both approaches.
- Autofill (reading credentials back into a form) still benefits from the `autocomplete` attributes described in the official guide.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/password-autofill/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/password-autofill/LICENSE).
