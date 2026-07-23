# Capacitor OAuth Plugin

Capacitor plugin for communicating with OAuth 2.0 and OpenID Connect providers.[^1][^2]

<div class="capawesome-z29o10a">
  <a href="https://capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor OAuth plugin is one of the most complete authentication solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌐 **Providers**: Works with any OAuth 2.0 / OpenID Connect provider, including Auth0, Azure AD, Amazon Cognito, Okta and OneLogin.
- 🔐 **PKCE**: Implements the Authorization Code flow with Proof Key for Code Exchange (PKCE).
- 🔍 **Auto-discovery**: Automatically fetches endpoints via OpenID Connect discovery.
- 🔄 **Token Refresh**: Refresh access tokens using a refresh token.
- 🪪 **JWT Decoding**: Decode JWT ID tokens without verification.
- 🪶 **Lightweight**: Just a single dependency and zero unnecessary bloat.
- 🤝 **Compatibility**: Compatible with the [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/) plugin to securely store tokens.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The OAuth plugin is typically used whenever an app needs to authenticate users against an OAuth 2.0 or OpenID Connect provider, for example:

- **Enterprise sign-in**: Authenticate users against identity providers such as Auth0, Azure AD, Amazon Cognito, Okta or OneLogin using the Authorization Code flow with PKCE.
- **Social login**: Sign in users with providers like Google that support OpenID Connect discovery.
- **Session management**: Keep users signed in by refreshing access tokens with a refresh token and checking token expiration.
- **User profiles**: Decode the JWT ID token to access the user's claims.
- **Auth Connect migration**: Replace the discontinued Ionic Auth Connect plugin with an actively maintained alternative.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Demo

| Android                                                                                                                      | iOS                                                                                                                      | Web                                                                                                                      |
| ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------ |
| <img src="https://github.com/user-attachments/assets/95ec6fe8-ba1d-4be0-898d-6b63a9170347" width="266" alt="Android Demo" /> | <img src="https://github.com/user-attachments/assets/0f06193f-15c5-4c72-a3dd-ada5163ce3eb" width="266" alt="iOS Demo" /> | <img src="https://github.com/user-attachments/assets/267c8536-2c83-455a-ab8f-76ed99011ba1" width="266" alt="Web Demo" /> |

## Guides

- [Announcing the Capacitor OAuth Plugin](https://capawesome.io/blog/announcing-the-capacitor-oauth-plugin/)
- [How to Use Better Auth in Capacitor Apps](https://capawesome.io/blog/how-to-use-better-auth-in-capacitor-apps/)
- [How to Sign in with Okta using Capacitor](https://capawesome.io/blog/how-to-sign-in-with-okta-using-capacitor/)
- [How to Sign in with Auth0 using Capacitor](https://capawesome.io/blog/how-to-sign-in-with-auth0-using-capacitor/)
- [How to Sign in with Azure Entra ID using Capacitor](https://capawesome.io/blog/how-to-sign-in-with-azure-entra-id-using-capacitor/)
- [Alternatives to Ionic Enterprise Plugins](https://capawesome.io/blog/alternatives-to-ionic-enterprise-plugins/)
- [Alternative to the Ionic Auth Connect Plugin](https://capawesome.io/blog/alternative-to-ionic-auth-connect-plugin/)

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-oauth` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-oauth
npx cap sync
```

### Android

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$appAuthVersion` version of `net.openid:appauth` (default: `0.11.1`)

#### Redirect Scheme

Add the following to your app's `build.gradle` file to configure the redirect scheme used by AppAuth:

```groovy
android {
    defaultConfig {
        manifestPlaceholders = [appAuthRedirectScheme: "com.example.app"]
    }
}
```

Replace `com.example.app` with the scheme of your redirect URI.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

## Usage

The following examples show how to sign in a user, handle the redirect callback on the Web, refresh the access token, sign out a user, and decode an ID token, using the [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/) plugin to securely store the tokens.

### Sign in a user

Start an OAuth 2.0 authorization code flow with PKCE using the `login(...)` method. When you provide the `issuerUrl`, the plugin automatically fetches the authorization and token endpoints via OpenID Connect discovery:

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';

const login = async () => {
  // Sign in the user
  const result = await Oauth.login({
    issuerUrl: 'https://accounts.google.com',
    clientId: 'YOUR_CLIENT_ID',
    redirectUrl: 'com.example.app://oauth/callback',
    scopes: ['openid', 'profile', 'email', 'offline_access'],
  });
  console.log('Access token:', result.accessToken);
  console.log('ID token:', result.idToken);
  console.log('Refresh token:', result.refreshToken);
  // Store the tokens securely
  await SecurePreferences.set({
    key: 'tokens',
    value: JSON.stringify(result),
  });
};
```

### Handle the redirect callback on the Web

On the Web, call the `handleRedirectCallback()` method on page load when the URL contains authorization response parameters. This method is only available on Web:

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';
import { Capacitor } from '@capacitor/core';

const handleRedirectCallback = async () => {
  if (Capacitor.getPlatform() !== 'web') {
    return;
  }
  // Handle the redirect callback on web
  const result = await Oauth.handleRedirectCallback();
  console.log('Access token:', result.accessToken);
};
```

### Refresh the access token

Use the `refreshToken(...)` method to obtain a new access token using the refresh token from the login:

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';

const refreshToken = async () => {
  const result = await Oauth.refreshToken({
    issuerUrl: 'https://accounts.google.com',
    clientId: 'YOUR_CLIENT_ID',
    refreshToken: 'YOUR_REFRESH_TOKEN',
  });
  console.log('New access token:', result.accessToken);
};
```

### Sign out a user

End the OAuth session by calling the provider's end-session endpoint using the `logout(...)` method:

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';

const logout = async () => {
  await Oauth.logout({
    issuerUrl: 'https://accounts.google.com',
    idToken: 'YOUR_ID_TOKEN',
    postLogoutRedirectUrl: 'com.example.app://oauth/logout',
  });
};
```

### Decode an ID token

Use the `decodeIdToken(...)` method to decode a JWT ID token without verification and access its claims:

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';

const decodeIdToken = async () => {
  const result = await Oauth.decodeIdToken({
    token: 'YOUR_ID_TOKEN',
  });
  console.log('Payload:', result.payload);
};
```

## API

<docgen-index>

* [`decodeIdToken(...)`](#decodeidtoken)
* [`getAccessTokenExpirationDate(...)`](#getaccesstokenexpirationdate)
* [`isAccessTokenAvailable(...)`](#isaccesstokenavailable)
* [`isAccessTokenExpired(...)`](#isaccesstokenexpired)
* [`isRefreshTokenAvailable(...)`](#isrefreshtokenavailable)
* [`handleRedirectCallback()`](#handleredirectcallback)
* [`login(...)`](#login)
* [`logout(...)`](#logout)
* [`refreshToken(...)`](#refreshtoken)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### decodeIdToken(...)

```typescript
decodeIdToken(options: DecodeIdTokenOptions) => Promise<DecodeIdTokenResult>
```

Decode a JWT ID token without verification.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#decodeidtokenoptions">DecodeIdTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#decodeidtokenresult">DecodeIdTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getAccessTokenExpirationDate(...)

```typescript
getAccessTokenExpirationDate(options: GetAccessTokenExpirationDateOptions) => Promise<GetAccessTokenExpirationDateResult>
```

Get the access token expiration date as an ISO 8601 string.

| Param         | Type                                                                                                |
| ------------- | --------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getaccesstokenexpirationdateoptions">GetAccessTokenExpirationDateOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getaccesstokenexpirationdateresult">GetAccessTokenExpirationDateResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAccessTokenAvailable(...)

```typescript
isAccessTokenAvailable(options: IsAccessTokenAvailableOptions) => Promise<IsAccessTokenAvailableResult>
```

Check if an access token is available (non-null and non-empty).

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isaccesstokenavailableoptions">IsAccessTokenAvailableOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isaccesstokenavailableresult">IsAccessTokenAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAccessTokenExpired(...)

```typescript
isAccessTokenExpired(options: IsAccessTokenExpiredOptions) => Promise<IsAccessTokenExpiredResult>
```

Check if the access token has expired.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isaccesstokenexpiredoptions">IsAccessTokenExpiredOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isaccesstokenexpiredresult">IsAccessTokenExpiredResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isRefreshTokenAvailable(...)

```typescript
isRefreshTokenAvailable(options: IsRefreshTokenAvailableOptions) => Promise<IsRefreshTokenAvailableResult>
```

Check if a refresh token is available (non-null and non-empty).

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isrefreshtokenavailableoptions">IsRefreshTokenAvailableOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isrefreshtokenavailableresult">IsRefreshTokenAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### handleRedirectCallback()

```typescript
handleRedirectCallback() => Promise<HandleRedirectCallbackResult>
```

Handle the redirect callback after a login or logout redirect on the web.

Call this method on page load when the URL contains authorization response parameters.

Only available on Web.

**Returns:** <code>Promise&lt;<a href="#loginresult">LoginResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### login(...)

```typescript
login(options: LoginOptions) => Promise<LoginResult>
```

Start an OAuth 2.0 authorization code flow with PKCE.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#loginoptions">LoginOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loginresult">LoginResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### logout(...)

```typescript
logout(options: LogoutOptions) => Promise<void>
```

End the OAuth session by calling the end-session endpoint.

Note that some providers (e.g. Microsoft Entra ID) may not redirect back to the app
after logout and instead show a "You have signed out" page. In this case, the user
has to close the browser manually which results in a `USER_CANCELED` error even though
the logout was successful.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#logoutoptions">LogoutOptions</a></code> |

**Since:** 0.1.0

--------------------


### refreshToken(...)

```typescript
refreshToken(options: RefreshTokenOptions) => Promise<RefreshTokenResult>
```

Refresh the access token using a refresh token.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#refreshtokenoptions">RefreshTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#loginresult">LoginResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### DecodeIdTokenResult

| Prop          | Type                                       | Description                                    | Since |
| ------------- | ------------------------------------------ | ---------------------------------------------- | ----- |
| **`header`**  | <code>Record&lt;string, unknown&gt;</code> | The decoded JWT header.                        | 0.1.0 |
| **`payload`** | <code>Record&lt;string, unknown&gt;</code> | The decoded JWT payload containing the claims. | 0.1.0 |


#### DecodeIdTokenOptions

| Prop        | Type                | Description                        | Since |
| ----------- | ------------------- | ---------------------------------- | ----- |
| **`token`** | <code>string</code> | The JWT ID token string to decode. | 0.1.0 |


#### GetAccessTokenExpirationDateResult

| Prop       | Type                | Description                                             | Since |
| ---------- | ------------------- | ------------------------------------------------------- | ----- |
| **`date`** | <code>string</code> | The access token expiration date as an ISO 8601 string. | 0.1.0 |


#### GetAccessTokenExpirationDateOptions

| Prop                            | Type                | Description                                             | Since |
| ------------------------------- | ------------------- | ------------------------------------------------------- | ----- |
| **`accessTokenExpirationDate`** | <code>number</code> | The access token expiration date in epoch milliseconds. | 0.1.0 |


#### IsAccessTokenAvailableResult

| Prop              | Type                 | Description                                         | Since |
| ----------------- | -------------------- | --------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether the access token is non-null and non-empty. | 0.1.0 |


#### IsAccessTokenAvailableOptions

| Prop              | Type                | Description                | Since |
| ----------------- | ------------------- | -------------------------- | ----- |
| **`accessToken`** | <code>string</code> | The access token to check. | 0.1.0 |


#### IsAccessTokenExpiredResult

| Prop            | Type                 | Description                           | Since |
| --------------- | -------------------- | ------------------------------------- | ----- |
| **`isExpired`** | <code>boolean</code> | Whether the access token has expired. | 0.1.0 |


#### IsAccessTokenExpiredOptions

| Prop                            | Type                | Description                                             | Since |
| ------------------------------- | ------------------- | ------------------------------------------------------- | ----- |
| **`accessTokenExpirationDate`** | <code>number</code> | The access token expiration date in epoch milliseconds. | 0.1.0 |


#### IsRefreshTokenAvailableResult

| Prop              | Type                 | Description                                          | Since |
| ----------------- | -------------------- | ---------------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether the refresh token is non-null and non-empty. | 0.1.0 |


#### IsRefreshTokenAvailableOptions

| Prop               | Type                | Description                 | Since |
| ------------------ | ------------------- | --------------------------- | ----- |
| **`refreshToken`** | <code>string</code> | The refresh token to check. | 0.1.0 |


#### LoginResult

| Prop                            | Type                                      | Description                                                                                            | Since |
| ------------------------------- | ----------------------------------------- | ------------------------------------------------------------------------------------------------------ | ----- |
| **`accessToken`**               | <code>string</code>                       | The access token.                                                                                      | 0.1.0 |
| **`accessTokenExpirationDate`** | <code>number</code>                       | The access token expiration date in epoch milliseconds.                                                | 0.1.0 |
| **`additionalParameters`**      | <code>Record&lt;string, string&gt;</code> | Additional non-standard parameters returned by the token endpoint. All values are returned as strings. | 0.1.8 |
| **`idToken`**                   | <code>string</code>                       | The JWT ID token (OpenID Connect).                                                                     | 0.1.0 |
| **`refreshToken`**              | <code>string</code>                       | The refresh token.                                                                                     | 0.1.0 |
| **`scope`**                     | <code>string</code>                       | The granted scopes as a space-delimited string.                                                        | 0.1.0 |
| **`tokenType`**                 | <code>string</code>                       | The token type.                                                                                        | 0.1.0 |


#### LoginOptions

| Prop                                    | Type                                      | Description                                                                                                                                                                                                                                                                                                                                                           | Default            | Since |
| --------------------------------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`additionalParameters`**              | <code>Record&lt;string, string&gt;</code> | Additional parameters to include in the authorization request.                                                                                                                                                                                                                                                                                                        |                    | 0.1.0 |
| **`authorizationEndpoint`**             | <code>string</code>                       | The authorization endpoint URL. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided.                                                                                                                                                                                                                                              |                    | 0.1.0 |
| **`clientId`**                          | <code>string</code>                       | The OAuth client ID.                                                                                                                                                                                                                                                                                                                                                  |                    | 0.1.0 |
| **`issuerUrl`**                         | <code>string</code>                       | The OpenID Connect issuer URL for auto-discovery. The plugin will fetch the OpenID Connect discovery document from `{issuerUrl}/.well-known/openid-configuration` to obtain the authorization and token endpoint URLs. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided.                                                       |                    | 0.1.0 |
| **`loginHint`**                         | <code>string</code>                       | A hint to the authorization server about the user's identifier to pre-fill the login form.                                                                                                                                                                                                                                                                            |                    | 0.1.0 |
| **`prefersEphemeralWebBrowserSession`** | <code>boolean</code>                      | Whether the authentication session should use an ephemeral web browser session. If `true`, the session will not share cookies or other browsing data with the user's regular browser session. As a side effect, the system consent dialog (e.g. "...wants to use 'example.com' to Sign In") is not shown. Only available on iOS.                                      | <code>false</code> | 0.1.5 |
| **`prompt`**                            | <code>string</code>                       | The prompt parameter to control the authorization server UI behavior.                                                                                                                                                                                                                                                                                                 |                    | 0.1.0 |
| **`redirectUrl`**                       | <code>string</code>                       | The redirect URI to use after authentication. **Attention**: On iOS, the redirect URI returned by the provider must exactly match this value (including any trailing slash), otherwise `login(...)` will never resolve. See the [Troubleshooting](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/oauth#troubleshooting) section for details. |                    | 0.1.0 |
| **`scopes`**                            | <code>string[]</code>                     | The OAuth scopes to request.                                                                                                                                                                                                                                                                                                                                          |                    | 0.1.0 |
| **`tokenEndpoint`**                     | <code>string</code>                       | The token endpoint URL. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided.                                                                                                                                                                                                                                                      |                    | 0.1.0 |
| **`uiLocales`**                         | <code>string[]</code>                     | The end-user's preferred languages for the authorization server UI, as an ordered list of BCP47 language tags.                                                                                                                                                                                                                                                        |                    | 0.1.4 |


#### LogoutOptions

| Prop                                    | Type                                      | Description                                                                                                                                                                                                                                                                                                                       | Default            | Since |
| --------------------------------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`additionalParameters`**              | <code>Record&lt;string, string&gt;</code> | Additional parameters to include in the end-session request.                                                                                                                                                                                                                                                                      |                    | 0.1.0 |
| **`endSessionEndpoint`**                | <code>string</code>                       | The end-session endpoint URL. Either `issuerUrl` or `endSessionEndpoint` must be provided.                                                                                                                                                                                                                                        |                    | 0.1.0 |
| **`idToken`**                           | <code>string</code>                       | The ID token hint for session identification.                                                                                                                                                                                                                                                                                     |                    | 0.1.0 |
| **`issuerUrl`**                         | <code>string</code>                       | The OpenID Connect issuer URL used to fetch the discovery document at `{issuerUrl}/.well-known/openid-configuration`. Either `issuerUrl` or `endSessionEndpoint` must be provided.                                                                                                                                                |                    | 0.1.0 |
| **`postLogoutRedirectUrl`**             | <code>string</code>                       | The redirect URI to use after logout.                                                                                                                                                                                                                                                                                             |                    | 0.1.0 |
| **`prefersEphemeralWebBrowserSession`** | <code>boolean</code>                      | Whether the authentication session should use an ephemeral web browser session. If `true`, the session will not share cookies or other browsing data with the user's regular browser session. As a side effect, the system consent dialog (e.g. "...wants to use 'example.com' to Sign Out") is not shown. Only available on iOS. | <code>false</code> | 0.1.5 |


#### RefreshTokenOptions

| Prop                       | Type                                      | Description                                                                                                                                                                   | Since |
| -------------------------- | ----------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`issuerUrl`**            | <code>string</code>                       | The OpenID Connect issuer URL used to fetch the discovery document at `{issuerUrl}/.well-known/openid-configuration`. Either `issuerUrl` or `tokenEndpoint` must be provided. | 0.1.0 |
| **`tokenEndpoint`**        | <code>string</code>                       | The token endpoint URL. Either `issuerUrl` or `tokenEndpoint` must be provided.                                                                                               | 0.1.0 |
| **`clientId`**             | <code>string</code>                       | The OAuth client ID.                                                                                                                                                          | 0.1.0 |
| **`refreshToken`**         | <code>string</code>                       | The refresh token obtained from login.                                                                                                                                        | 0.1.0 |
| **`additionalParameters`** | <code>Record&lt;string, string&gt;</code> | Additional parameters to include in the token refresh request.                                                                                                                | 0.1.0 |


### Type Aliases


#### HandleRedirectCallbackResult

<code><a href="#loginresult">LoginResult</a></code>


#### RefreshTokenResult

<code><a href="#loginresult">LoginResult</a></code>

</docgen-api>

## Troubleshooting

##### `login(...)` never resolves on iOS

On iOS, `login(...)` may hang forever (while working on Android and Web) if the redirect URI returned by your provider does not exactly match the `redirectUrl` you passed. The plugin compares scheme, host and path and silently ignores any mismatch, so the promise never settles. The usual culprit is a **trailing slash** added by the provider or its infrastructure (`com.example.app://callback` vs `com.example.app://callback/`).

Compare the returned redirect URI (e.g. via a network proxy such as [Proxyman](https://proxyman.io/)) byte-for-byte with your `redirectUrl` and make them match exactly.

##### In-app browser closes when the app is reopened on Android

With the Capacitor default `android:launchMode="singleTask"`, Android destroys all activities on top of the main activity, including the in-app browser, when the app is reopened via the launcher icon, for example after switching to a mail app to look up a one-time password. The plugin detects this and automatically re-opens the in-app browser so the user can complete the flow. Since the identity provider's session cookies are preserved, the user can usually continue where they left off.

If you prefer to keep the in-app browser (including any entered form data) alive while the app is in the background, you can optionally apply one of the following workarounds in your app:

**Launcher activity (recommended)**: Add a launcher activity that receives the launcher intent instead of your main activity. This way, reopening the app via the launcher icon no longer targets the `singleTask` main activity, so Android brings the task to the foreground without destroying the in-app browser, while your main activity keeps its `singleTask` behavior. Create the following activity next to your `MainActivity` (adjust the package name):

```java
package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            // The app is already running, so just bring the existing task
            // (including any open in-app browser) to the foreground.
            finish();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.replaceExtras(getIntent());
        startActivity(intent);
        finish();
    }
}
```

Then, in your `AndroidManifest.xml` file, move the `MAIN`/`LAUNCHER` intent filter from your main activity to the new launcher activity and set `android:exported` of your main activity to `false`:

```xml
<activity
    android:name=".LauncherActivity"
    android:theme="@style/AppTheme.NoActionBarLaunch"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

Your main activity keeps `android:launchMode="singleTask"` and all other intent filters (e.g. for deep links).

**`singleTop` launch mode**: Alternatively, set `android:launchMode="singleTop"` on your main activity in the `AndroidManifest.xml` file. Be aware that this may change how Android delivers intents (e.g. deep links) to your app, so make sure to test this change carefully.

## FAQ

### Is this plugin an alternative to Ionic Auth Connect?

Yes. This plugin was built as an actively maintained alternative to [Ionic Auth Connect](https://ionic.io/products/auth-connect), which has been discontinued and reaches end of life on December 31, 2027. It offers a similar feature set:

- OAuth 2.0 and OpenID Connect support for any provider, including Auth0, Azure AD, Amazon Cognito, Okta and OneLogin
- Authorization Code flow with Proof Key for Code Exchange (PKCE)
- Automatic endpoint discovery via OpenID Connect discovery
- Access token refresh using a refresh token
- Logout via the provider's end-session endpoint

### How do I migrate from Ionic Auth Connect?

For an AI-assisted migration of your code, add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool and instruct it to use the `ionic-enterprise-sdk-migration` skill to migrate your project from Ionic Auth Connect to `@capawesome-team/capacitor-oauth`. Alternatively, if you want to perform the migration manually, you can follow the instructions in this blog post: [Alternative to the Ionic Auth Connect plugin](https://capawesome.io/blog/alternative-to-ionic-auth-connect-plugin/).

### Why does `login(...)` never resolve on iOS?

This usually happens when the redirect URI returned by your provider does not exactly match the `redirectUrl` you passed, for example because of a trailing slash added by the provider. The plugin compares scheme, host and path and silently ignores any mismatch. See the [Troubleshooting](#troubleshooting) section for details on how to diagnose and fix this.

### Where should I store the tokens?

Tokens are sensitive data and should not be stored in plain text. The plugin is compatible with the [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/) plugin, which securely stores key/value pairs such as tokens, as shown in the [usage examples](#usage) above.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

### How does OAuth compare to Apple, Google, or Facebook Sign-In?

This plugin talks to any OAuth 2.0 / OpenID Connect provider directly, which is the right fit for a custom identity provider or one without a dedicated Capacitor plugin. If you're specifically integrating Apple, Google, or Facebook, their dedicated [Apple Sign-In](https://capawesome.io/docs/sdks/capacitor/apple-sign-in/), [Google Sign-In](https://capawesome.io/docs/sdks/capacitor/google-sign-in/), and [Facebook Sign-In](https://capawesome.io/docs/sdks/capacitor/facebook-sign-in/) plugins use each provider's native SDK and are usually simpler to set up than configuring them through generic OAuth.

## Related Plugins

- [Biometrics](https://capawesome.io/docs/sdks/capacitor/biometrics/): Request biometric authentication, such as face recognition or fingerprint recognition.
- [In-App Browser](https://capawesome.io/docs/sdks/capacitor/in-app-browser/): Open the OAuth authorization page in an embedded browser instead of the system browser.
- [Passkeys](https://capawesome.io/docs/sdks/capacitor/passkeys/): Create and authenticate with passkeys based on the WebAuthn standard.
- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Securely store key/value pairs such as passwords, tokens or other sensitive information.

## Next steps

Here are a few resources to help you continue:

- Read [Alternative to the Ionic Auth Connect plugin](https://capawesome.io/blog/alternative-to-ionic-auth-connect-plugin/) if you are migrating from Ionic Auth Connect.
- Store tokens and other sensitive data with the [Capacitor Secure Preferences plugin](https://capawesome.io/docs/sdks/capacitor/secure-preferences/).
- Check out [Getting Started with Insiders](https://capawesome.io/docs/insiders/getting-started/) to learn how to install the plugin.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by the OpenID Foundation or any of their affiliates or subsidiaries.
[^2]: `OpenID` is a registered trademark of the OpenID Foundation.
