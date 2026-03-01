# @capawesome/capacitor-apple-sign-in

Unofficial Capacitor plugin to sign-in with Apple.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for Apple Sign-In. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🍎 **Native Sign-In**: Uses native AuthenticationServices on iOS.
- 🌐 **WebView OAuth**: Implements Apple OAuth flow on Android without external dependencies.
- 📧 **Scope support**: Request email and full name on all platforms.
- 🔐 **Nonce & state**: Supports nonce for replay protection and state for CSRF protection.
- 🪶 **Lightweight**: Just a single dependency and zero unnecessary bloat.
- 🤝 **Compatibility**: Compatible with the [Google Sign-In](https://capawesome.io/plugins/google-sign-in) and [OAuth](https://capawesome.io/plugins/oauth) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Guides 

- [How to Sign In with Apple using Capacitor](https://capawesome.io/blog/how-to-sign-in-with-apple-using-capacitor/) 

## Installation

```bash
npm install @capawesome/capacitor-apple-sign-in
npx cap sync
```

### iOS

Add the **Sign in with Apple** capability to your app in Xcode:

1. Open your app target in Xcode.
1. Go to the **Signing & Capabilities** tab.
1. Click **+ Capability** and add **Sign in with Apple**.

### Web

The plugin loads the [Apple JS SDK](https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_js) automatically.
Make sure you have configured your Apple Service ID with the correct redirect URL and web domain in the [Apple Developer Portal](https://developer.apple.com/account/resources/identifiers/list/serviceId).

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { AppleSignIn, SignInScope } from '@capawesome/capacitor-apple-sign-in';

const initialize = async () => {
  await AppleSignIn.initialize({
    clientId: 'com.example.app.signin',
  });
};

const signIn = async () => {
  const result = await AppleSignIn.signIn({
    scopes: [SignInScope.Email, SignInScope.FullName],
    redirectUrl: 'https://example.com/callback',
    nonce: 'random-nonce',
    state: 'random-state',
  });
  return result;
};
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`signIn(...)`](#signin)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the plugin.

This method must be called before `signIn()` on **Android** and **Web**.

Only available on Android and Web.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 8.0.0

--------------------


### signIn(...)

```typescript
signIn(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Sign in with Apple.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### Interfaces


#### InitializeOptions

| Prop           | Type                | Description                              | Since |
| -------------- | ------------------- | ---------------------------------------- | ----- |
| **`clientId`** | <code>string</code> | The Apple Service ID to use for sign-in. | 8.0.0 |


#### SignInResult

| Prop                    | Type                                                      | Description                                                                                             | Since |
| ----------------------- | --------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ----- |
| **`authorizationCode`** | <code>string</code>                                       | The authorization code.                                                                                 | 8.0.0 |
| **`idToken`**           | <code>string</code>                                       | The ID token (JWT).                                                                                     | 8.0.0 |
| **`user`**              | <code>string</code>                                       | The stable Apple user identifier. On **Android** and **Web**, this is decoded from the JWT `sub` claim. | 8.0.0 |
| **`email`**             | <code>string \| null</code>                               | The user's email address. On **iOS**, this is only provided on the first sign-in.                       | 8.0.0 |
| **`givenName`**         | <code>string \| null</code>                               | The user's given name. On **iOS**, this is only provided on the first sign-in.                          | 8.0.0 |
| **`familyName`**        | <code>string \| null</code>                               | The user's family name. On **iOS**, this is only provided on the first sign-in.                         | 8.0.0 |
| **`state`**             | <code>string</code>                                       | The state value from the sign-in request. Only available on Android and Web.                            | 8.0.0 |
| **`realUserStatus`**    | <code><a href="#realuserstatus">RealUserStatus</a></code> | The real user status. Only available on iOS.                                                            | 8.0.0 |


#### SignInOptions

| Prop              | Type                       | Description                                                                   | Since |
| ----------------- | -------------------------- | ----------------------------------------------------------------------------- | ----- |
| **`redirectUrl`** | <code>string</code>        | The OAuth redirect URL to use for sign-in. Only available on Android and Web. | 8.0.0 |
| **`scopes`**      | <code>SignInScope[]</code> | The scopes to request during sign-in.                                         | 8.0.0 |
| **`nonce`**       | <code>string</code>        | A nonce for replay protection.                                                | 8.0.0 |
| **`state`**       | <code>string</code>        | A state value for CSRF protection. Only available on Android and Web.         | 8.0.0 |


### Enums


#### RealUserStatus

| Members           | Value                      | Description                                                   | Since |
| ----------------- | -------------------------- | ------------------------------------------------------------- | ----- |
| **`LikelyReal`**  | <code>'LIKELY_REAL'</code> | The user appears to be a real person.                         | 8.0.0 |
| **`Unknown`**     | <code>'UNKNOWN'</code>     | The system can't determine whether the user is a real person. | 8.0.0 |
| **`Unsupported`** | <code>'UNSUPPORTED'</code> | The real user status is not supported on this platform.       | 8.0.0 |


#### SignInScope

| Members        | Value                    | Description                       | Since |
| -------------- | ------------------------ | --------------------------------- | ----- |
| **`Email`**    | <code>'EMAIL'</code>     | Request the user's email address. | 8.0.0 |
| **`FullName`** | <code>'FULL_NAME'</code> | Request the user's full name.     | 8.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/apple-sign-in/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/apple-sign-in/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Apple Inc. or any of their affiliates or subsidiaries.
