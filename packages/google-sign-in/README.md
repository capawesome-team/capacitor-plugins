# @capawesome/capacitor-google-sign-in

Unofficial Capacitor plugin to sign-in with Google.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer a comprehensive Capacitor plugin for Google Sign-In. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🔐 **Authentication**: Sign in users with their Google account and receive an ID token (JWT).
- 🔑 **Authorization**: Optionally request OAuth scopes to get an access token and server auth code.
- 👤 **User Profile**: Retrieve the user's email, display name, profile picture, and more.
- 🛡️ **Nonce Support**: Prevent replay attacks with a custom nonce on Android and Web.
- 🪶 **Lightweight**: Just a single dependency and zero unnecessary bloat.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- 🤝 **Compatibility**: Compatible with the [OAuth](https://capawesome.io/plugins/oauth) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Guides 

- [How to Sign In with Google using Capacitor](https://capawesome.io/blog/how-to-sign-in-with-google-using-capacitor/) 

## Installation 

```bash 
npm install @capawesome/capacitor-google-sign-in
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$androidxCredentialsVersion` version of `androidx.credentials:credentials` (default: `1.5.0`)
- `$googleIdVersion` version of `com.google.android.libraries.identity.googleid:googleid` (default: `1.1.1`)
- `$playServicesAuthVersion` version of `com.google.android.gms:play-services-auth` (default: `21.5.0`)

### iOS

Add the `GIDClientID` key to the `ios/App/App/Info.plist` file with your iOS client ID from the Google Cloud Console:

```xml
<key>GIDClientID</key>
<string>YOUR_IOS_CLIENT_ID</string>
```

You also need to add the URL scheme for your iOS client ID to the `ios/App/App/Info.plist` file:

```xml
<key>CFBundleURLTypes</key>
<array>
  <dict>
    <key>CFBundleURLSchemes</key>
    <array>
      <string>com.googleusercontent.apps.YOUR_IOS_CLIENT_ID</string>
    </array>
  </dict>
</array>
```

Replace `YOUR_IOS_CLIENT_ID` with the reversed client ID from the Google Cloud Console (e.g. `com.googleusercontent.apps.123456789-abc`).

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { GoogleSignIn } from '@capawesome/capacitor-google-sign-in';
import { Capacitor } from '@capacitor/core';

const initialize = async () => {
  await GoogleSignIn.initialize({
    clientId: '123456789-abc.apps.googleusercontent.com',
    scopes: ['https://www.googleapis.com/auth/userinfo.profile'],
  });
};

const signIn = async () => {
  const result = await GoogleSignIn.signIn();
  console.log(result.idToken);
  console.log(result.userId);
  console.log(result.email);
  console.log(result.displayName);
  console.log(result.accessToken);
  console.log(result.serverAuthCode);
};

const handleRedirectCallback = async () => {
  if (Capacitor.getPlatform() !== 'web') {
    return;
  }
  const result = await GoogleSignIn.handleRedirectCallback();
  console.log(result.idToken);
  console.log(result.userId);
  console.log(result.email);
  console.log(result.displayName);
  console.log(result.accessToken);
  console.log(result.serverAuthCode);
};

const signOut = async () => {
  await GoogleSignIn.signOut();
};
```

## API

<docgen-index>

* [`handleRedirectCallback()`](#handleredirectcallback)
* [`initialize(...)`](#initialize)
* [`signIn(...)`](#signin)
* [`signOut()`](#signout)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### handleRedirectCallback()

```typescript
handleRedirectCallback() => Promise<SignInResult>
```

Handle the redirect callback from the OAuth provider.

This method must be called when the app is redirected back from the OAuth provider.
It exchanges the authorization code for tokens and returns the sign-in result.

Only available on Web.

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the Google Sign-In plugin.

This method must be called once before all other methods.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### signIn(...)

```typescript
signIn(options?: SignInOptions | undefined) => Promise<SignInResult>
```

Start the Google Sign-In flow.

On Web, this redirects to the Google OAuth authorization page.
The promise will never resolve on Web. After the user signs in,
the app will be redirected back to the `redirectUrl`.
Use `handleRedirectCallback()` to complete the sign-in flow.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#signinoptions">SignInOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#signinresult">SignInResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### signOut()

```typescript
signOut() => Promise<void>
```

Sign out the current user.

On Android, this clears the credential state.
On iOS, this signs out from the Google Sign-In SDK.
On Web, this is a no-op.

**Since:** 0.1.0

--------------------


### Interfaces


#### SignInResult

| Prop                 | Type                        | Description                                                                                                                                                                 | Since |
| -------------------- | --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`idToken`**        | <code>string</code>         | The ID token (JWT) returned by Google. This token can be sent to your backend for verification.                                                                             | 0.1.0 |
| **`userId`**         | <code>string</code>         | The unique identifier of the user's Google Account.                                                                                                                         | 0.1.0 |
| **`email`**          | <code>string \| null</code> | The user's email address.                                                                                                                                                   | 0.1.0 |
| **`displayName`**    | <code>string \| null</code> | The user's display name (full name).                                                                                                                                        | 0.1.0 |
| **`givenName`**      | <code>string \| null</code> | The user's given name (first name).                                                                                                                                         | 0.1.0 |
| **`familyName`**     | <code>string \| null</code> | The user's family name (last name).                                                                                                                                         | 0.1.0 |
| **`imageUrl`**       | <code>string \| null</code> | The URL of the user's profile picture.                                                                                                                                      | 0.1.0 |
| **`accessToken`**    | <code>string \| null</code> | The access token for accessing Google APIs. Only available when `scopes` are configured in `initialize()`.                                                                  | 0.1.0 |
| **`serverAuthCode`** | <code>string \| null</code> | The server auth code that can be exchanged on your backend for access and refresh tokens. Only available on Android and iOS when `scopes` are configured in `initialize()`. | 0.1.0 |


#### InitializeOptions

| Prop              | Type                  | Description                                                                                                                                                                                                                                                                                                                                                                                          | Since |
| ----------------- | --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`clientId`**    | <code>string</code>   | The web client ID from Google Cloud Console. On Android, this is passed as the server client ID to the Credential Manager API and the AuthorizationClient API. On iOS, this is used as the server client ID for the Google Sign-In SDK. On Web, this is used to initialize the Google Sign-In JavaScript API. **Attention**: This must be a web client ID on all platforms, even on Android and iOS. | 0.1.0 |
| **`redirectUrl`** | <code>string</code>   | The URL to redirect to after the OAuth flow. Only available on Web.                                                                                                                                                                                                                                                                                                                                  | 0.1.0 |
| **`scopes`**      | <code>string[]</code> | The OAuth scopes to request. If provided, the plugin will request authorization in addition to authentication. This enables `accessToken` and `serverAuthCode` in the sign-in result.                                                                                                                                                                                                                | 0.1.0 |


#### SignInOptions

| Prop        | Type                | Description                                                           | Since |
| ----------- | ------------------- | --------------------------------------------------------------------- | ----- |
| **`nonce`** | <code>string</code> | A nonce to prevent replay attacks. Only available on Android and Web. | 0.1.0 |

</docgen-api>

## FAQ

### What's the difference between this plugin and other Google Sign-In plugins?

This plugin is purpose-built for Google Sign-In and focuses on providing a clean and modern API with the latest platform features. Here are some of the key differences:

- **Cross-platform**: Supports Android, iOS, and Web.
- **Lightweight**: No unnecessary dependencies. Just Google Sign-In, nothing else.
- **No deprecated APIs**: Uses the latest platform APIs (Credential Manager on Android, Google Sign-In SDK on iOS).
- **Authentication + Authorization**: Supports both authentication (ID tokens) and authorization (access tokens, server auth codes) in a single flow.
- **Error codes**: Provides typed error codes for proper error handling.
- **Redirect flow on Web**: Uses a redirect-based OAuth flow instead of popups, resulting in a more reliable and user-friendly experience.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/google-sign-in/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/google-sign-in/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google Inc. or any of their affiliates or subsidiaries.
