# Capacitor App Integrity Plugin

Capacitor plugin to verify app and device integrity using the Play Integrity API (Android) and App Attest (iOS).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🤖 **Play Integrity**: Request standard and classic integrity tokens from the Play Integrity API on Android.
- 🍏 **App Attest**: Generate hardware-backed keys, attest them and generate assertions with App Attest on iOS.
- 🛡️ **Server-verifiable**: Tokens and assertions are verified on your backend via Google and Apple, so they cannot be spoofed on the device.
- 🤝 **Compatibility**: Works alongside the [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/) plugin for additional client-side checks.
- 📦 **SPM**: Supports Swift Package Manager and CocoaPods for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The App Integrity plugin is typically used to protect your backend from requests that do not originate from your genuine, unmodified app, for example:

- **API protection**: Attach integrity tokens or assertions to requests to sensitive endpoints so your server can reject tampered clients.
- **Fraud prevention**: Verify high-value actions such as payments or account changes with a classic integrity request or an App Attest assertion.
- **Account security**: Protect login and registration flows against bots and automated abuse.
- **Gated content**: Grant access to protected resources only after your server has verified the integrity verdict.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-integrity` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-app-integrity
npx cap sync
```

### Android

The Play Integrity API must be set up in the Google Play Console before you can request integrity tokens. Follow the [official setup instructions](https://developer.android.com/google/play/integrity/setup) to link a Google Cloud project to your app. You need the **Google Cloud project number** of the linked project for the standard request flow.

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$playIntegrityVersion` version of `com.google.android.play:integrity` (default: `1.6.0`)

### iOS

App Attest requires the **App Attest capability**. Add it to your app in Xcode under **Signing & Capabilities** or add the following entitlement to your `.entitlements` file:

```xml
<key>com.apple.developer.devicecheck.appattest-environment</key>
<string>development</string>
```

Use the value `development` to attest against Apple's sandbox servers during development and `production` for App Store builds. Note that App Attest is not supported on simulators, so you need a real device for testing.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { AppIntegrity } from '@capawesome/capacitor-app-integrity';
import { Capacitor } from '@capacitor/core';
```

### Check if integrity attestation is available

Use `isAvailable()` before requesting tokens or attestations. On Android, this checks whether Google Play Services is available; on iOS, it checks whether the App Attest service is supported (it is not supported on simulators):

```typescript
const isAvailable = async () => {
  const { available } = await AppIntegrity.isAvailable();
  return available;
};
```

### Request an integrity token on Android

For the recommended standard request flow, prepare the integrity token provider once (for example at app start) with your Google Cloud project number, since the preparation can take several seconds. Then request tokens with a `requestHash`. For infrequent, high-value actions you can use the classic request flow with a `nonce` instead. Only available on Android:

```typescript
const prepareIntegrityToken = async () => {
  // Only available on Android
  await AppIntegrity.prepareIntegrityToken({
    cloudProjectNumber: 123456789012,
  });
};

const requestIntegrityToken = async () => {
  // Only available on Android
  if (Capacitor.getPlatform() === 'android') {
    // Standard request (recommended, requires a prior call to `prepareIntegrityToken(...)`)
    const { token } = await AppIntegrity.requestIntegrityToken({
      requestHash: '2cp24z...',
    });
    // Classic request
    const { token: classicToken } = await AppIntegrity.requestIntegrityToken({
      nonce: 'R2Rra24fVm5xa2Mg',
    });
    // Send the token to your server for verification
  }
};
```

### Attest a key on iOS

Generate a hardware-backed key pair and attest it with a one-time challenge received from your server. The attestation object must be sent to your server for verification with Apple. Only available on iOS:

```typescript
const attestKey = async () => {
  // Only available on iOS
  if (Capacitor.getPlatform() === 'ios') {
    // 1. Generate a new key pair and store the key identifier
    const { keyId } = await AppIntegrity.generateKey();
    // 2. Attest the key with a one-time challenge received from your server
    const { attestationObject } = await AppIntegrity.attestKey({
      keyId,
      challenge: 'dGhpc2lzYWNoYWxsZW5nZQ==',
    });
    // 3. Send the attestation object to your server for verification
  }
};
```

### Generate an assertion on iOS

Once a key is attested, sign client data (usually a JSON payload that includes a one-time challenge from your server) to prove that a request comes from your genuine app. Only available on iOS:

```typescript
const generateAssertion = async () => {
  // Only available on iOS
  if (Capacitor.getPlatform() === 'ios') {
    const { assertion } = await AppIntegrity.generateAssertion({
      keyId: 'Kh0DIEwVJTDJUyIRZ4M9BvJn/i4RSSGDkFvUZOaSm5g=',
      clientData: 'eyJjaGFsbGVuZ2UiOiJkR2hwYzJsellXTm9ZV3hzWlc1blpRPT0ifQ==',
    });
    // Send the assertion to your server for verification
  }
};
```

## API

<docgen-index>

* [`attestKey(...)`](#attestkey)
* [`generateAssertion(...)`](#generateassertion)
* [`generateKey()`](#generatekey)
* [`isAvailable()`](#isavailable)
* [`prepareIntegrityToken(...)`](#prepareintegritytoken)
* [`requestIntegrityToken(...)`](#requestintegritytoken)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### attestKey(...)

```typescript
attestKey(options: AttestKeyOptions) => Promise<AttestKeyResult>
```

Attest a key generated with `generateKey()` using Apple's App Attest service.

The returned attestation object must be sent to your server, which verifies
it with Apple and stores the key identifier for future assertions.

Only available on iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#attestkeyoptions">AttestKeyOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#attestkeyresult">AttestKeyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### generateAssertion(...)

```typescript
generateAssertion(options: GenerateAssertionOptions) => Promise<GenerateAssertionResult>
```

Generate an assertion for the given client data using an attested key.

The returned assertion must be sent to your server along with the client
data, where it is verified using the previously stored key identifier.

Only available on iOS.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#generateassertionoptions">GenerateAssertionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#generateassertionresult">GenerateAssertionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### generateKey()

```typescript
generateKey() => Promise<GenerateKeyResult>
```

Generate a new App Attest key pair.

The private key is stored in the Secure Enclave. Store the returned key
identifier in your app to attest the key and generate assertions later.

Only available on iOS.

**Returns:** <code>Promise&lt;<a href="#generatekeyresult">GenerateKeyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check whether app integrity attestation is available on this device.

On Android, this checks whether Google Play Services is available.
On iOS, this checks whether the App Attest service is supported.
On iOS, the App Attest service is not supported on simulators.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### prepareIntegrityToken(...)

```typescript
prepareIntegrityToken(options: PrepareIntegrityTokenOptions) => Promise<void>
```

Prepare the integrity token provider for standard integrity token requests.

Call this method once, for example at app start, before calling
`requestIntegrityToken(...)` with a request hash. The preparation can take
several seconds, so it should be done well ahead of the first request.

Only available on Android.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#prepareintegritytokenoptions">PrepareIntegrityTokenOptions</a></code> |

**Since:** 0.1.0

--------------------


### requestIntegrityToken(...)

```typescript
requestIntegrityToken(options: RequestIntegrityTokenOptions) => Promise<RequestIntegrityTokenResult>
```

Request an integrity token from the Play Integrity API.

Provide a `requestHash` for a standard request (recommended, requires a
prior call to `prepareIntegrityToken(...)`) or a `nonce` for a classic request.

The returned token must be sent to your server, which decrypts and
verifies it via Google's servers.

Only available on Android.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestintegritytokenoptions">RequestIntegrityTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#requestintegritytokenresult">RequestIntegrityTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### AttestKeyResult

| Prop                    | Type                | Description                                                                                               | Since |
| ----------------------- | ------------------- | --------------------------------------------------------------------------------------------------------- | ----- |
| **`attestationObject`** | <code>string</code> | The attestation object, encoded as a base64 string. Send this to your server for verification with Apple. | 0.1.0 |


#### AttestKeyOptions

| Prop            | Type                | Description                                                                                                                                                                     | Since |
| --------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`challenge`** | <code>string</code> | The one-time challenge received from your server, encoded as a base64 string. The challenge is hashed with SHA-256 on the device before it is passed to the App Attest service. | 0.1.0 |
| **`keyId`**     | <code>string</code> | The identifier of the key to attest, as returned by `generateKey()`.                                                                                                            | 0.1.0 |


#### GenerateAssertionResult

| Prop            | Type                | Description                                                                                  | Since |
| --------------- | ------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`assertion`** | <code>string</code> | The assertion object, encoded as a base64 string. Send this to your server for verification. | 0.1.0 |


#### GenerateAssertionOptions

| Prop             | Type                | Description                                                                                                                                                                                                                                           | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`clientData`** | <code>string</code> | The client data to sign, encoded as a base64 string. This is usually a JSON payload that includes a one-time challenge received from your server. The client data is hashed with SHA-256 on the device before it is passed to the App Attest service. | 0.1.0 |
| **`keyId`**      | <code>string</code> | The identifier of an attested key, as returned by `generateKey()`.                                                                                                                                                                                    | 0.1.0 |


#### GenerateKeyResult

| Prop        | Type                | Description                               | Since |
| ----------- | ------------------- | ----------------------------------------- | ----- |
| **`keyId`** | <code>string</code> | The identifier of the generated key pair. | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                    | Since |
| --------------- | -------------------- | -------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether app integrity attestation is available on this device. | 0.1.0 |


#### PrepareIntegrityTokenOptions

| Prop                     | Type                | Description                                                                                           | Since |
| ------------------------ | ------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`cloudProjectNumber`** | <code>number</code> | The Google Cloud project number of the project that is linked to your Play Console developer account. | 0.1.0 |


#### RequestIntegrityTokenResult

| Prop        | Type                | Description                                                                                         | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`token`** | <code>string</code> | The integrity token. Send this to your server, which decrypts and verifies it via Google's servers. | 0.1.0 |


#### RequestIntegrityTokenOptions

| Prop                     | Type                | Description                                                                                                                                                                                             | Since |
| ------------------------ | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`cloudProjectNumber`** | <code>number</code> | The Google Cloud project number of the project that is linked to your Play Console developer account. Only used for classic requests. It is required if your app is distributed outside of Google Play. | 0.1.0 |
| **`nonce`**              | <code>string</code> | The one-time nonce for a classic request, encoded as a base64 web-safe no-wrap string. Provide either `nonce` or `requestHash`.                                                                         | 0.1.0 |
| **`requestHash`**        | <code>string</code> | The request hash for a standard request. Requires a prior call to `prepareIntegrityToken(...)`. Provide either `nonce` or `requestHash`.                                                                | 0.1.0 |

</docgen-api>

## How Verification Works

This plugin only provides the **client-side** part of the attestation flow. The returned tokens, attestation objects, and assertions are opaque to your app and **must** be verified on your own server. Never make security decisions based on the client-side result alone.

The typical flow looks like this:

1. Your app requests a one-time challenge (or nonce) from your server.
2. Your app calls this plugin to obtain an integrity token (Android) or an attestation object/assertion (iOS) that is bound to the challenge.
3. Your app sends the result to your server.
4. Your server verifies the result:
   - **Android**: Decrypt and verify the integrity token via the [Play Integrity API](https://developer.android.com/google/play/integrity/verdicts) and evaluate the verdicts.
   - **iOS**: Validate the attestation object or assertion as described in [Validating Apps That Connect to Your Server](https://developer.apple.com/documentation/devicecheck/validating-apps-that-connect-to-your-server).
5. Your server grants or denies access based on the verification result.

On Android, Google recommends the **standard request** flow (`prepareIntegrityToken(...)` + `requestIntegrityToken({ requestHash })`) for frequent integrity checks. Use the **classic request** flow (`requestIntegrityToken({ nonce })`) only for infrequent, high-value actions.

## FAQ

### Do I need my own server to use this plugin?

Yes. This plugin only provides the client-side part of the attestation flow. The returned tokens, attestation objects, and assertions are opaque to your app and must be decrypted and verified on your own server via Google (Android) or Apple (iOS). Never make security decisions based on the client-side result alone. See the [How Verification Works](#how-verification-works) section for the typical flow.

### What is the difference between a standard and a classic request on Android?

A standard request is the flow recommended by Google for frequent integrity checks: it requires a prior call to `prepareIntegrityToken(...)` and passes a `requestHash` to `requestIntegrityToken(...)`. A classic request passes a `nonce` instead, requires no preparation, and should only be used for infrequent, high-value actions.

### Why does `isAvailable` return `false` on the iOS Simulator?

The App Attest service is not supported on simulators, so you need a real device for testing. On Android, `isAvailable()` checks whether Google Play Services is available.

### What do I need to set up before requesting integrity tokens on Android?

The Play Integrity API must be set up in the Google Play Console by linking a Google Cloud project to your app. You need the Google Cloud project number of the linked project for the standard request flow. See the [Installation](#installation) section for more information.

### How is this plugin different from the Root Detection plugin?

The [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/) plugin performs client-side checks for rooted and jailbroken devices, which can be helpful but can also be bypassed on a compromised device. The App Integrity plugin provides server-verifiable tokens and assertions that are verified via Google and Apple, so they cannot be spoofed on the device. The two plugins work well together as complementary layers.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Root Detection](https://capawesome.io/docs/sdks/capacitor/root-detection/): Detect rooted and jailbroken devices with client-side checks.
- [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/): Request biometric authentication, such as face or fingerprint recognition.
- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Securely store key/value pairs such as passwords, tokens or other sensitive information.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-integrity/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-integrity/LICENSE).
