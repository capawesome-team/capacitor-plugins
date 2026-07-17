# Capacitor Passkeys Plugin

Capacitor plugin to create and authenticate with [passkeys](https://fidoalliance.org/passkeys/) based on the [WebAuthn](https://www.w3.org/TR/webauthn-2/) standard.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Passkeys plugin is one of the most complete passkey authentication solutions for Capacitor apps. Here are some of the key features:

- 🔐 **Passkey creation**: Register new passkeys with the platform authenticator.
- 🔑 **Passkey authentication**: Authenticate users with their existing passkeys.
- 🌐 **WebAuthn standard**: Uses the WebAuthn JSON serialization so any WebAuthn server library works unchanged.
- 📱 **Availability check**: Check if passkeys are available on the device.
- 🖥️ **Web support**: Full web support via the native WebAuthn browser API.
- 🤝 **Compatibility**: Works alongside the [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/) and [OAuth](https://capawesome.io/docs/sdks/capacitor/oauth/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Passkeys plugin is typically used to modernize the authentication of an app, for example:

- **Passwordless sign-in**: Let users authenticate with a passkey instead of typing a password.
- **Passkey enrollment**: Let existing users register a passkey for their account after they signed in with their current credentials.
- **Progressive enhancement**: Check with `isAvailable()` whether passkeys are supported on the device and offer them as an alternative to your existing login.
- **Server integration**: Pass the options and results of any WebAuthn server library through unchanged, thanks to the WebAuthn JSON serialization.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-passkeys` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-passkeys
npx cap sync
```

### Android

On Android, passkeys are supported on Android 9 (API level 28) and higher with an available credential provider (e.g. Google Password Manager).

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCredentialsVersion` version of `androidx.credentials:credentials` and `androidx.credentials:credentials-play-services-auth` (default: `1.5.0`)

#### Digital Asset Links

Your app must be associated with the domain of the relying party (`rp.id` / `rpId`) using [Digital Asset Links](https://developers.google.com/digital-asset-links). For this, host a JSON file at `https://<your-domain>/.well-known/assetlinks.json` that delegates the `common.get_login_creds` permission to your app:

```json
[
  {
    "relation": [
      "delegate_permission/common.handle_all_urls",
      "delegate_permission/common.get_login_creds"
    ],
    "target": {
      "namespace": "android_app",
      "package_name": "com.example.app",
      "sha256_cert_fingerprints": [
        "01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF:01:23:45:67:89:AB:CD:EF"
      ]
    }
  }
]
```

Replace `com.example.app` with the application ID of your app and the fingerprint with the SHA-256 fingerprint of your app's signing certificate. Otherwise, the plugin methods will reject with the `DOMAIN_NOT_ASSOCIATED` error code.

### iOS

On iOS, passkeys are supported on iOS 15 and higher.

#### Associated Domains

Your app must be associated with the domain of the relying party (`rp.id` / `rpId`). For this, add the [Associated Domains](https://developer.apple.com/documentation/xcode/supporting-associated-domains) capability with the `webcredentials` service type to your app:

```xml
<key>com.apple.developer.associated-domains</key>
<array>
  <string>webcredentials:example.com</string>
</array>
```

Additionally, host an [`apple-app-site-association`](https://developer.apple.com/documentation/xcode/supporting-associated-domains) file at `https://<your-domain>/.well-known/apple-app-site-association`:

```json
{
  "webcredentials": {
    "apps": ["TEAMID.com.example.app"]
  }
}
```

Replace `TEAMID` with your Apple Developer Team ID and `com.example.app` with the bundle identifier of your app. Otherwise, the plugin methods will reject with the `DOMAIN_NOT_ASSOCIATED` error code.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to create a new passkey, authenticate with an existing passkey, and check whether passkeys are available on the device.

### Create a new passkey

Register a new passkey for a user account. In a real app, the options must be provided by your WebAuthn server, and the result must be passed back to it for verification:

```typescript
import { Passkeys } from '@capawesome/capacitor-passkeys';

const createPasskey = async () => {
  // In a real app, the options must be provided by your WebAuthn server,
  // e.g. via `generateRegistrationOptions()` from SimpleWebAuthn.
  const result = await Passkeys.createPasskey({
    attestation: 'none',
    authenticatorSelection: {
      residentKey: 'required',
      userVerification: 'required',
    },
    challenge: 'dGhpc2lzYWNoYWxsZW5nZQ',
    pubKeyCredParams: [
      { alg: -7, type: 'public-key' },
      { alg: -257, type: 'public-key' },
    ],
    rp: { id: 'example.com', name: 'Example Inc.' },
    user: {
      displayName: 'Jane Doe',
      id: 'anVzdGFyYW5kb21pZA',
      name: 'jane.doe@example.com',
    },
  });
  // Pass the result to your WebAuthn server for verification,
  // e.g. via `verifyRegistrationResponse()` from SimpleWebAuthn.
  return result;
};
```

### Authenticate with an existing passkey

Sign a user in with a passkey that was previously created for your relying party. Again, the options come from your WebAuthn server and the result is verified by it:

```typescript
import { Passkeys } from '@capawesome/capacitor-passkeys';

const getPasskey = async () => {
  // In a real app, the options must be provided by your WebAuthn server,
  // e.g. via `generateAuthenticationOptions()` from SimpleWebAuthn.
  const result = await Passkeys.getPasskey({
    challenge: 'dGhpc2lzYWNoYWxsZW5nZQ',
    rpId: 'example.com',
    userVerification: 'required',
  });
  // Pass the result to your WebAuthn server for verification,
  // e.g. via `verifyAuthenticationResponse()` from SimpleWebAuthn.
  return result;
};
```

### Check if passkeys are available

Check whether passkeys are available on the device before offering them in your login UI. On Android, this returns `true` if the device runs Android 9 (API level 28) or higher. On iOS, this always returns `true`. On Web, this returns `true` if the browser supports WebAuthn and a user-verifying platform authenticator is available:

```typescript
import { Passkeys } from '@capawesome/capacitor-passkeys';

const isAvailable = async () => {
  const { available } = await Passkeys.isAvailable();
  return available;
};
```

## API

<docgen-index>

* [`createPasskey(...)`](#createpasskey)
* [`getPasskey(...)`](#getpasskey)
* [`isAvailable()`](#isavailable)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### createPasskey(...)

```typescript
createPasskey(options: CreatePasskeyOptions) => Promise<CreatePasskeyResult>
```

Create (register) a new passkey.

The options mirror the WebAuthn `PublicKeyCredentialCreationOptions` JSON
serialization so that the values provided by any WebAuthn server library
can be passed through unchanged.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#createpasskeyoptions">CreatePasskeyOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createpasskeyresult">CreatePasskeyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getPasskey(...)

```typescript
getPasskey(options: GetPasskeyOptions) => Promise<GetPasskeyResult>
```

Get (authenticate with) an existing passkey.

The options mirror the WebAuthn `PublicKeyCredentialRequestOptions` JSON
serialization so that the values provided by any WebAuthn server library
can be passed through unchanged.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#getpasskeyoptions">GetPasskeyOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getpasskeyresult">GetPasskeyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if passkeys are available on this device.

On **Android**, this returns `true` if the device runs Android 9 (API level 28) or higher.
On **iOS**, this always returns `true`.
On **Web**, this returns `true` if the browser supports WebAuthn and
a user-verifying platform authenticator is available.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### CreatePasskeyResult

The result of the passkey creation.

This mirrors the WebAuthn `RegistrationResponseJSON` so it can be
passed to any WebAuthn server library for verification.

| Prop                          | Type                                                                                      | Description                                                   | Since |
| ----------------------------- | ----------------------------------------------------------------------------------------- | ------------------------------------------------------------- | ----- |
| **`authenticatorAttachment`** | <code><a href="#passkeyauthenticatorattachment">PasskeyAuthenticatorAttachment</a></code> | The attachment of the authenticator that created the passkey. | 0.1.0 |
| **`id`**                      | <code>string</code>                                                                       | The credential identifier as a base64url-encoded string.      | 0.1.0 |
| **`rawId`**                   | <code>string</code>                                                                       | The raw credential identifier as a base64url-encoded string.  | 0.1.0 |
| **`response`**                | <code><a href="#createpasskeyresponse">CreatePasskeyResponse</a></code>                   | The response of the authenticator.                            | 0.1.0 |
| **`type`**                    | <code>'public-key'</code>                                                                 | The credential type.                                          | 0.1.0 |


#### CreatePasskeyResponse

The response of the authenticator for the registration of a new passkey.

This mirrors the WebAuthn `AuthenticatorAttestationResponse` JSON
serialization.

| Prop                     | Type                            | Description                                                                                  | Since |
| ------------------------ | ------------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`attestationObject`**  | <code>string</code>             | The attestation object as a base64url-encoded string.                                        | 0.1.0 |
| **`authenticatorData`**  | <code>string</code>             | The authenticator data as a base64url-encoded string. Only available on Android and Web.     | 0.1.0 |
| **`clientDataJSON`**     | <code>string</code>             | The client data as a base64url-encoded string.                                               | 0.1.0 |
| **`publicKey`**          | <code>string</code>             | The DER-encoded public key as a base64url-encoded string. Only available on Android and Web. | 0.1.0 |
| **`publicKeyAlgorithm`** | <code>number</code>             | The COSE algorithm identifier of the public key. Only available on Android and Web.          | 0.1.0 |
| **`transports`**         | <code>PasskeyTransport[]</code> | The transports that the authenticator supports. Only available on Android and Web.           | 0.1.0 |


#### CreatePasskeyOptions

| Prop                         | Type                                                                                    | Description                                                                                                                                                                                  | Default             | Since |
| ---------------------------- | --------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------- | ----- |
| **`attestation`**            | <code><a href="#passkeyattestation">PasskeyAttestation</a></code>                       | The attestation conveyance preference.                                                                                                                                                       | <code>'none'</code> | 0.1.0 |
| **`authenticatorSelection`** | <code><a href="#passkeyauthenticatorselection">PasskeyAuthenticatorSelection</a></code> | Criteria that the authenticator must meet.                                                                                                                                                   |                     | 0.1.0 |
| **`challenge`**              | <code>string</code>                                                                     | The challenge provided by the relying party server as a base64url-encoded string.                                                                                                            |                     | 0.1.0 |
| **`excludeCredentials`**     | <code>PasskeyCredentialDescriptor[]</code>                                              | Credentials that already exist for the user, so that the authenticator does not create a second passkey for the same account. On **iOS**, this option is only applied on iOS 17.4 and later. |                     | 0.1.0 |
| **`pubKeyCredParams`**       | <code>PasskeyCredentialParameter[]</code>                                               | The public key credential types and algorithms that the relying party server supports, ordered from most to least preferred. Only available on Android and Web.                              |                     | 0.1.0 |
| **`rp`**                     | <code><a href="#passkeyrelyingparty">PasskeyRelyingParty</a></code>                     | The relying party for which the passkey is created.                                                                                                                                          |                     | 0.1.0 |
| **`timeout`**                | <code>number</code>                                                                     | The time in milliseconds that the caller is willing to wait for the operation to complete. Only available on Android and Web.                                                                |                     | 0.1.0 |
| **`user`**                   | <code><a href="#passkeyuser">PasskeyUser</a></code>                                     | The user account for which the passkey is created.                                                                                                                                           |                     | 0.1.0 |


#### PasskeyAuthenticatorSelection

Criteria that the authenticator must meet to create a passkey.

| Prop                          | Type                                                                                      | Description                                                                                                                                                                                           | Default                  | Since |
| ----------------------------- | ----------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ | ----- |
| **`authenticatorAttachment`** | <code><a href="#passkeyauthenticatorattachment">PasskeyAuthenticatorAttachment</a></code> | The authenticator attachment modality. Only available on Android and Web.                                                                                                                             |                          | 0.1.0 |
| **`requireResidentKey`**      | <code>boolean</code>                                                                      | Whether or not a discoverable credential (passkey) is required. This property is retained for backwards compatibility with WebAuthn Level 1. Prefer `residentKey`. Only available on Android and Web. |                          | 0.1.0 |
| **`residentKey`**             | <code><a href="#passkeyresidentkey">PasskeyResidentKey</a></code>                         | The extent to which the relying party desires to create a discoverable credential (passkey). Only available on Android and Web.                                                                       |                          | 0.1.0 |
| **`userVerification`**        | <code><a href="#passkeyuserverification">PasskeyUserVerification</a></code>               | The user verification requirement.                                                                                                                                                                    | <code>'preferred'</code> | 0.1.0 |


#### PasskeyCredentialDescriptor

A descriptor that identifies a specific credential.

| Prop             | Type                            | Description                                                                                          | Since |
| ---------------- | ------------------------------- | ---------------------------------------------------------------------------------------------------- | ----- |
| **`id`**         | <code>string</code>             | The credential identifier as a base64url-encoded string.                                             | 0.1.0 |
| **`transports`** | <code>PasskeyTransport[]</code> | The transports that the authenticator of the credential supports. Only available on Android and Web. | 0.1.0 |
| **`type`**       | <code>'public-key'</code>       | The credential type.                                                                                 | 0.1.0 |


#### PasskeyCredentialParameter

A public key credential type and algorithm that the relying party
server supports.

| Prop       | Type                      | Description                                                             | Since |
| ---------- | ------------------------- | ----------------------------------------------------------------------- | ----- |
| **`alg`**  | <code>number</code>       | The COSE algorithm identifier, e.g. `-7` for ES256 or `-257` for RS256. | 0.1.0 |
| **`type`** | <code>'public-key'</code> | The credential type.                                                    | 0.1.0 |


#### PasskeyRelyingParty

The relying party for which a passkey is created.

| Prop       | Type                | Description                                                                                                                                | Since |
| ---------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`id`**   | <code>string</code> | The identifier of the relying party. This must be a registrable domain suffix of (or equal to) the domain that the app is associated with. | 0.1.0 |
| **`name`** | <code>string</code> | The human-readable name of the relying party.                                                                                              | 0.1.0 |


#### PasskeyUser

The user account for which a passkey is created.

| Prop              | Type                | Description                                                                    | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------ | ----- |
| **`displayName`** | <code>string</code> | The human-readable display name of the user account.                           | 0.1.0 |
| **`id`**          | <code>string</code> | The user handle of the user account as a base64url-encoded string.             | 0.1.0 |
| **`name`**        | <code>string</code> | The human-readable name of the user account, e.g. a username or email address. | 0.1.0 |


#### GetPasskeyResult

The result of the passkey authentication.

This mirrors the WebAuthn `AuthenticationResponseJSON` so it can be
passed to any WebAuthn server library for verification.

| Prop                          | Type                                                                                      | Description                                                    | Since |
| ----------------------------- | ----------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`authenticatorAttachment`** | <code><a href="#passkeyauthenticatorattachment">PasskeyAuthenticatorAttachment</a></code> | The attachment of the authenticator that provided the passkey. | 0.1.0 |
| **`id`**                      | <code>string</code>                                                                       | The credential identifier as a base64url-encoded string.       | 0.1.0 |
| **`rawId`**                   | <code>string</code>                                                                       | The raw credential identifier as a base64url-encoded string.   | 0.1.0 |
| **`response`**                | <code><a href="#getpasskeyresponse">GetPasskeyResponse</a></code>                         | The response of the authenticator.                             | 0.1.0 |
| **`type`**                    | <code>'public-key'</code>                                                                 | The credential type.                                           | 0.1.0 |


#### GetPasskeyResponse

The response of the authenticator for the authentication with an
existing passkey.

This mirrors the WebAuthn `AuthenticatorAssertionResponse` JSON
serialization.

| Prop                    | Type                | Description                                                                             | Since |
| ----------------------- | ------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`authenticatorData`** | <code>string</code> | The authenticator data as a base64url-encoded string.                                   | 0.1.0 |
| **`clientDataJSON`**    | <code>string</code> | The client data as a base64url-encoded string.                                          | 0.1.0 |
| **`signature`**         | <code>string</code> | The signature as a base64url-encoded string.                                            | 0.1.0 |
| **`userHandle`**        | <code>string</code> | The user handle (the `user.id` provided during creation) as a base64url-encoded string. | 0.1.0 |


#### GetPasskeyOptions

| Prop                   | Type                                                                        | Description                                                                                                                                                            | Default                  | Since |
| ---------------------- | --------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ | ----- |
| **`allowCredentials`** | <code>PasskeyCredentialDescriptor[]</code>                                  | The credentials that are acceptable to the relying party server. If not provided, the user can select from any discoverable credential (passkey) of the relying party. |                          | 0.1.0 |
| **`challenge`**        | <code>string</code>                                                         | The challenge provided by the relying party server as a base64url-encoded string.                                                                                      |                          | 0.1.0 |
| **`rpId`**             | <code>string</code>                                                         | The identifier of the relying party.                                                                                                                                   |                          | 0.1.0 |
| **`timeout`**          | <code>number</code>                                                         | The time in milliseconds that the caller is willing to wait for the operation to complete. Only available on Android and Web.                                          |                          | 0.1.0 |
| **`userVerification`** | <code><a href="#passkeyuserverification">PasskeyUserVerification</a></code> | The user verification requirement.                                                                                                                                     | <code>'preferred'</code> | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                           | Since |
| --------------- | -------------------- | ----------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not passkeys are available on this device. | 0.1.0 |


### Type Aliases


#### PasskeyAuthenticatorAttachment

The authenticator attachment modality.

<code>'cross-platform' | 'platform'</code>


#### PasskeyTransport

A transport that an authenticator supports.

<code>'ble' | 'hybrid' | 'internal' | 'nfc' | 'smart-card' | 'usb'</code>


#### PasskeyAttestation

The attestation conveyance preference.

<code>'direct' | 'enterprise' | 'indirect' | 'none'</code>


#### PasskeyResidentKey

The extent to which the relying party desires to create a
discoverable credential (passkey).

<code>'discouraged' | 'preferred' | 'required'</code>


#### PasskeyUserVerification

The user verification requirement.

- `discouraged`: The relying party prefers no user verification.
- `preferred`: The relying party prefers user verification but will
  not fail the operation without it.
- `required`: The relying party requires user verification.

<code>'discouraged' | 'preferred' | 'required'</code>

</docgen-api>

## Testing

Keep the following in mind when testing your passkey integration:

- **Real device recommended**: Test on a real device with a screen lock set up. On iOS, the Simulator supports passkeys since iOS 16. On Android, the Emulator requires Google Play services and a Google account with Google Password Manager set up.
- **WebAuthn server**: The challenges in this plugin's examples are only placeholders. In production, the options must be generated and the results must be verified by a WebAuthn server library such as [SimpleWebAuthn](https://simplewebauthn.dev/) (Node.js) or [java-webauthn-server](https://github.com/Yubico/java-webauthn-server) (Java).
- **Domain association**: The relying party ID must be associated with your app (see the Installation section). Domain association changes can take some time to propagate, as the files are cached by Apple and Google.

## Limitations

Conditional UI (passkey autofill) is not supported by this plugin. The operating system autofill integrations target native text fields, not HTML inputs in a web view.

## FAQ

### How is this plugin different from other similar plugins?

It implements the full WebAuthn standard using the JSON serialization, so the options and results from any WebAuthn server library pass through unchanged on Android, iOS, and the Web. Passkey creation, authentication, and an `isAvailable()` capability check are all covered through a fully typed, actively maintained API. If you only need a local face or fingerprint check, a simpler biometric setup is enough; if you want to replace passwords with server-verified passkeys across every platform, this plugin is designed for exactly that.

### Why do the plugin methods reject with the `DOMAIN_NOT_ASSOCIATED` error code?

This error means that your app is not associated with the domain of the relying party (`rp.id` / `rpId`). On Android, you must host a Digital Asset Links file at `https://<your-domain>/.well-known/assetlinks.json` that delegates the `common.get_login_creds` permission to your app. On iOS, you must add the Associated Domains capability with the `webcredentials` service type and host a matching `apple-app-site-association` file. See the [Installation](#installation) section for the details. Also keep in mind that changes to these files can take some time to propagate, as they are cached by Apple and Google.

### Do I need my own WebAuthn server to use this plugin?

Yes. The challenges shown in the examples are only placeholders. In production, the options for `createPasskey(...)` and `getPasskey(...)` must be generated by a WebAuthn server, and the results must be verified by it, for example with a library such as [SimpleWebAuthn](https://simplewebauthn.dev/). Since the plugin uses the WebAuthn JSON serialization, any WebAuthn server library works unchanged.

### Which devices support passkeys?

On Android, passkeys are supported on Android 9 (API level 28) and higher with an available credential provider such as Google Password Manager. On iOS, passkeys are supported on iOS 15 and higher. On the web, passkeys are supported in browsers that implement WebAuthn with a user-verifying platform authenticator. Use the `isAvailable()` method to check the availability at runtime.

### Does this plugin support passkey autofill (Conditional UI)?

No, Conditional UI is not supported. The operating system autofill integrations target native text fields, not HTML inputs in a web view. See the [Limitations](#limitations) section for more details.

### What is the difference between this plugin and the Biometrics plugin?

The [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/) plugin performs a local biometric check (e.g. face or fingerprint recognition) on the device, for example to protect a screen inside your app. The Passkeys plugin implements the WebAuthn standard, where the authentication is cryptographically verified by your server, making it suitable to replace password-based sign-in entirely.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/): Request biometric authentication, such as face or fingerprint recognition.
- [OAuth](https://capawesome.io/docs/sdks/capacitor/oauth/): Communicate with OAuth 2.0 and OpenID Connect providers.
- [Password Autofill](https://capawesome.io/docs/sdks/capacitor/password-autofill/): Save passwords to the platform credential store.
- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Securely store key/value pairs such as passwords or tokens.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/passkeys/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/passkeys/LICENSE).
