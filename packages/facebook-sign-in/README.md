# Capacitor Facebook Sign-In Plugin

Unofficial Capacitor plugin to sign-in with Facebook.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Facebook Sign-In plugin is a comprehensive Facebook authentication solution for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🔐 **Authentication**: Sign in users with their Facebook account.
- 🔑 **Access Token**: Retrieve the access token for the Facebook Graph API.
- 👤 **User Profile**: Retrieve the user's email, display name, profile picture, and more.
- 🕵️ **Limited Login**: Support for Limited Login on iOS to sign in users without tracking.
- 🛡️ **Nonce Support**: Prevent replay attacks with a custom nonce on iOS.
- 🪶 **Lightweight**: Just a single dependency and zero unnecessary bloat.
- 🚨 **Error Codes**: Provides detailed error codes for better error handling.
- 🤝 **Compatibility**: Works alongside the [Apple Sign-In](https://capawesome.io/docs/sdks/capacitor/apple-sign-in/) and [Google Sign-In](https://capawesome.io/docs/sdks/capacitor/google-sign-in/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Facebook Sign-In plugin is typically used wherever users should sign in with their existing Facebook account, for example:

- **Social login**: Let users sign in to your app with their Facebook account instead of creating a new password.
- **Backend authentication**: Send the authentication token (JWT) to your backend to verify the user's identity.
- **Graph API access**: Use the access token to request data from the Facebook Graph API on behalf of the user.
- **Privacy-friendly sign-in on iOS**: Use Limited Login to sign in users without tracking and without App Tracking Transparency consent.
- **Profile pre-filling**: Use the user's email, display name, and profile picture to pre-fill their profile in your app.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-facebook-sign-in` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-facebook-sign-in
npx cap sync
```

This plugin requires a Facebook app. If you don't have one yet, create it in the [Meta App Dashboard](https://developers.facebook.com/apps/) and add the **Facebook Login** product. You can find your **App ID** and **Client Token** in the app settings.

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$facebookLoginVersion` version of `com.facebook.android:facebook-login` (default: `18.3.0`)

#### Resources

Add the following string resources to the `android/app/src/main/res/values/strings.xml` file:

```xml
<string name="facebook_app_id">YOUR_APP_ID</string>
<string name="facebook_client_token">YOUR_CLIENT_TOKEN</string>
```

Replace `YOUR_APP_ID` with your Facebook App ID and `YOUR_CLIENT_TOKEN` with your Facebook Client Token.

#### Metadata

Add the following elements inside the `application` tag of the `android/app/src/main/AndroidManifest.xml` file:

```xml
<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id" />
<meta-data android:name="com.facebook.sdk.ClientToken" android:value="@string/facebook_client_token" />
```

### iOS

Add the following keys to the `ios/App/App/Info.plist` file:

```xml
<key>FacebookAppID</key>
<string>YOUR_APP_ID</string>
<key>FacebookClientToken</key>
<string>YOUR_CLIENT_TOKEN</string>
<key>FacebookDisplayName</key>
<string>YOUR_APP_NAME</string>
<key>LSApplicationQueriesSchemes</key>
<array>
  <string>fbapi</string>
  <string>fb-messenger-share-api</string>
</array>
```

Replace `YOUR_APP_ID` with your Facebook App ID, `YOUR_CLIENT_TOKEN` with your Facebook Client Token, and `YOUR_APP_NAME` with the display name of your Facebook app.

You also need to add the URL scheme for your Facebook App ID to the `ios/App/App/Info.plist` file:

```xml
<key>CFBundleURLTypes</key>
<array>
  <dict>
    <key>CFBundleURLSchemes</key>
    <array>
      <string>fbYOUR_APP_ID</string>
    </array>
  </dict>
</array>
```

Replace `YOUR_APP_ID` with your Facebook App ID, so that the URL scheme is your App ID prefixed with `fb` (e.g. `fb1234567890123456`).

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { FacebookSignIn } from '@capawesome/capacitor-facebook-sign-in';
```

### Initialize the plugin

Call `initialize(...)` once before all other methods. On Android and iOS, the App ID is usually read from the native configuration, so the `appId` option is only required on Web:

```typescript
const initialize = async () => {
  await FacebookSignIn.initialize({
    appId: '1234567890123456',
  });
};
```

### Sign in a user

Start the Facebook Sign-In flow and retrieve the access token and the user's profile:

```typescript
const signIn = async () => {
  const result = await FacebookSignIn.signIn();
  console.log(result.accessToken?.token);
  console.log(result.profile.id);
  console.log(result.profile.name);
  console.log(result.profile.email);
};
```

### Sign in with Limited Login

Use Limited Login to sign in users without tracking. Instead of an access token, an authentication token (JWT) is returned that can be verified on your backend. Provide a nonce to prevent replay attacks. Only available on iOS:

```typescript
const signInWithLimitedLogin = async () => {
  const result = await FacebookSignIn.signIn({
    limitedLogin: true,
    nonce: 'YOUR_NONCE',
  });
  console.log(result.authenticationToken);
};
```

### Get the current access token

Retrieve the current access token, for example to check whether a user is still signed in. The result is `null` if no user is signed in or the access token has expired:

```typescript
const getCurrentAccessToken = async () => {
  const { accessToken } = await FacebookSignIn.getCurrentAccessToken();
  console.log(accessToken?.token);
};
```

### Sign out a user

Sign out the current user:

```typescript
const signOut = async () => {
  await FacebookSignIn.signOut();
};
```

## API

<docgen-index>

* [`getCurrentAccessToken()`](#getcurrentaccesstoken)
* [`initialize(...)`](#initialize)
* [`signIn(...)`](#signin)
* [`signOut()`](#signout)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getCurrentAccessToken()

```typescript
getCurrentAccessToken() => Promise<GetCurrentAccessTokenResult>
```

Get the current access token.

**Returns:** <code>Promise&lt;<a href="#getcurrentaccesstokenresult">GetCurrentAccessTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initialize the Facebook Sign-In plugin.

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

Start the Facebook Sign-In flow.

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

**Since:** 0.1.0

--------------------


### Interfaces


#### GetCurrentAccessTokenResult

| Prop              | Type                                                        | Description                                                                                       | Since |
| ----------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------- | ----- |
| **`accessToken`** | <code><a href="#accesstoken">AccessToken</a> \| null</code> | The current access token. This is `null` if no user is signed in or the access token has expired. | 0.1.0 |


#### AccessToken

| Prop              | Type                  | Description                                                                         | Since |
| ----------------- | --------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`expiresAt`**   | <code>number</code>   | The timestamp (in milliseconds since the Unix epoch) when the access token expires. | 0.1.0 |
| **`permissions`** | <code>string[]</code> | The permissions granted to the access token.                                        | 0.1.0 |
| **`token`**       | <code>string</code>   | The access token string.                                                            | 0.1.0 |
| **`userId`**      | <code>string</code>   | The unique identifier of the user's Facebook account.                               | 0.1.0 |


#### InitializeOptions

| Prop              | Type                | Description                                                                                                                                                                                           | Since |
| ----------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`appId`**       | <code>string</code> | The Facebook App ID from the Meta App Dashboard. On Android and iOS, this overrides the value from the native configuration and is usually not needed. **Attention**: This option is required on Web. | 0.1.0 |
| **`clientToken`** | <code>string</code> | The Facebook Client Token from the Meta App Dashboard. This overrides the value from the native configuration and is usually not needed. Only available on Android and iOS.                           | 0.1.0 |


#### SignInResult

| Prop                      | Type                                                        | Description                                                                                                                                      | Since |
| ------------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`accessToken`**         | <code><a href="#accesstoken">AccessToken</a> \| null</code> | The access token for accessing the Facebook Graph API. This is `null` on iOS when Limited Login is used.                                         | 0.1.0 |
| **`authenticationToken`** | <code>string \| null</code>                                 | The authentication token (JWT) returned by Facebook. This token can be sent to your backend for verification. Only available on Android and iOS. | 0.1.0 |
| **`profile`**             | <code><a href="#profile">Profile</a></code>                 | The profile of the signed-in user.                                                                                                               | 0.1.0 |


#### Profile

| Prop           | Type                        | Description                                                                         | Since |
| -------------- | --------------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`email`**    | <code>string \| null</code> | The user's email address. This is `null` if the `email` permission was not granted. | 0.1.0 |
| **`id`**       | <code>string</code>         | The unique identifier of the user's Facebook account.                               | 0.1.0 |
| **`imageUrl`** | <code>string \| null</code> | The URL of the user's profile picture.                                              | 0.1.0 |
| **`name`**     | <code>string \| null</code> | The user's display name (full name).                                                | 0.1.0 |


#### SignInOptions

| Prop               | Type                  | Description                                                                                                                                                                                                                                                                                          | Default                                  | Since |
| ------------------ | --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`limitedLogin`** | <code>boolean</code>  | Whether or not to use [Limited Login](https://developers.facebook.com/docs/facebook-login/limited-login/) instead of classic login. With Limited Login, no access token is returned. Instead, an authentication token (JWT) is returned that can be verified on your backend. Only available on iOS. | <code>false</code>                       | 0.1.0 |
| **`nonce`**        | <code>string</code>   | A nonce to prevent replay attacks. The nonce is included in the authentication token and can be verified on your backend. Only available on iOS.                                                                                                                                                     |                                          | 0.1.0 |
| **`permissions`**  | <code>string[]</code> | The permissions to request from the user.                                                                                                                                                                                                                                                            | <code>["public_profile", "email"]</code> | 0.1.0 |

</docgen-api>

## Limited Login

On iOS, the Facebook SDK distinguishes between classic login and [Limited Login](https://developers.facebook.com/docs/facebook-login/limited-login/). With Limited Login, no data is shared with Meta that could be used for tracking, so no App Tracking Transparency consent is required. However, no access token for the Facebook Graph API is returned. Instead, an authentication token (JWT) is returned, which can be verified on your backend.

Keep the following in mind:

- Set the `limitedLogin` option to `true` to use Limited Login.
- If the user has not granted the App Tracking Transparency permission, the Facebook SDK may fall back to Limited Login even if classic login was requested. In this case, the `accessToken` property in the sign-in result is `null`.
- Provide a `nonce` option and verify it on your backend to prevent replay attacks.

## Security

This plugin handles the sign-in flow and returns tokens to your app. To keep your integration secure, be aware of the following:

- **Server-side token verification is required.** The `authenticationToken` (JWT) is **not** verified client-side. Your backend **must** verify the JWT signature using [Facebook's public keys](https://www.facebook.com/.well-known/oauth/openid/jwks/) before trusting any claims. Never use client-side token data for authorization decisions without server-side verification.
- **Validate the access token on your backend.** If you use the `accessToken`, your backend should validate it with the [Facebook Graph API](https://developers.facebook.com/docs/facebook-login/guides/advanced/manual-flow#checktoken) before trusting it.

## FAQ

### Do I need a Facebook app to use this plugin?

Yes, this plugin requires a Facebook app. You can create one in the [Meta App Dashboard](https://developers.facebook.com/apps/) and add the **Facebook Login** product to it. The **App ID** and **Client Token** from the app settings are needed for the platform-specific configuration, see the [Installation](#installation) section.

### What is Limited Login and when should I use it?

Limited Login is an alternative sign-in mode of the Facebook SDK on iOS. With Limited Login, no data is shared with Meta that could be used for tracking, so no App Tracking Transparency consent is required. However, no access token for the Facebook Graph API is returned. Instead, an authentication token (JWT) is returned, which can be verified on your backend. Set the `limitedLogin` option to `true` to use it.

### Why is the `accessToken` property `null` after signing in on iOS?

This happens when Limited Login is used. If the user has not granted the App Tracking Transparency permission, the Facebook SDK may fall back to Limited Login even if classic login was requested. In this case, use the `authenticationToken` (JWT) instead and verify it on your backend.

### How do I verify the sign-in on my backend?

Your backend must verify the JWT signature of the `authenticationToken` using [Facebook's public keys](https://www.facebook.com/.well-known/oauth/openid/jwks/) before trusting any claims. If you use the `accessToken`, your backend should validate it with the Facebook Graph API before trusting it. See the [Security](#security) section for more details.

### Can I use this plugin together with other sign-in plugins?

Yes, the plugin works alongside the [Apple Sign-In](https://capawesome.io/docs/sdks/capacitor/apple-sign-in/) and [Google Sign-In](https://capawesome.io/docs/sdks/capacitor/google-sign-in/) plugins, so you can offer multiple sign-in options in the same app.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Apple Sign-In](https://capawesome.io/docs/sdks/capacitor/apple-sign-in/): Sign in users with their Apple account.
- [Google Sign-In](https://capawesome.io/docs/sdks/capacitor/google-sign-in/): Sign in users with their Google account.
- [OAuth](https://capawesome.io/docs/sdks/capacitor/oauth/): Communicate with any OAuth 2.0 and OpenID Connect provider.
- [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/): Request biometric authentication such as face or fingerprint recognition.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/facebook-sign-in/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/facebook-sign-in/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Meta Platforms, Inc. or any of its affiliates or subsidiaries.
