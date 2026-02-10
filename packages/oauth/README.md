# @capawesome-team/capacitor-oauth

Capacitor plugin for communicating with OAuth 2.0 and OpenID Connect providers.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for OAuth. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🌐 **Providers**: Works with any OAuth 2.0 / OpenID Connect provider, including Auth0, Azure AD, Amazon Cognito, Okta and OneLogin.
- 🔐 **PKCE**: Implements the Authorization Code flow with Proof Key for Code Exchange (PKCE).
- 🔍 **Auto-discovery**: Automatically fetches endpoints via OpenID Connect discovery.
- 🔄 **Token Refresh**: Refresh access tokens using a refresh token.
- 🪪 **JWT Decoding**: Decode JWT ID tokens without verification.
- 🤝 **Compatibility**: Compatible with the [Secure Preferences](https://capawesome.io/plugins/secure-preferences/) plugin to securely store tokens.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.1.x          | >=8.x.x           | Active support |

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/).
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
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

```typescript
import { Oauth } from '@capawesome-team/capacitor-oauth';
import { SecurePreferences } from '@capawesome-team/capacitor-secure-preferences';
import { Capacitor } from '@capacitor/core';

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

const handleRedirectCallback = async () => {
  if (Capacitor.getPlatform() !== 'web') {
    return;
  }
  // Handle the redirect callback on web
  const result = await Oauth.handleRedirectCallback();
  console.log('Access token:', result.accessToken);
};

const refreshToken = async () => {
  const result = await Oauth.refreshToken({
    issuerUrl: 'https://accounts.google.com',
    clientId: 'YOUR_CLIENT_ID',
    refreshToken: 'YOUR_REFRESH_TOKEN',
  });
  console.log('New access token:', result.accessToken);
};

const logout = async () => {
  await Oauth.logout({
    issuerUrl: 'https://accounts.google.com',
    idToken: 'YOUR_ID_TOKEN',
    postLogoutRedirectUrl: 'com.example.app://oauth/logout',
  });
};

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

| Prop                            | Type                | Description                                             | Since |
| ------------------------------- | ------------------- | ------------------------------------------------------- | ----- |
| **`accessToken`**               | <code>string</code> | The access token.                                       | 0.1.0 |
| **`accessTokenExpirationDate`** | <code>number</code> | The access token expiration date in epoch milliseconds. | 0.1.0 |
| **`idToken`**                   | <code>string</code> | The JWT ID token (OpenID Connect).                      | 0.1.0 |
| **`refreshToken`**              | <code>string</code> | The refresh token.                                      | 0.1.0 |
| **`scope`**                     | <code>string</code> | The granted scopes as a space-delimited string.         | 0.1.0 |
| **`tokenType`**                 | <code>string</code> | The token type.                                         | 0.1.0 |


#### LoginOptions

| Prop                        | Type                                      | Description                                                                                                                                                                                                                                                                                                     | Since |
| --------------------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`issuerUrl`**             | <code>string</code>                       | The OpenID Connect issuer URL for auto-discovery. The plugin will fetch the OpenID Connect discovery document from `{issuerUrl}/.well-known/openid-configuration` to obtain the authorization and token endpoint URLs. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided. | 0.1.0 |
| **`authorizationEndpoint`** | <code>string</code>                       | The authorization endpoint URL. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided.                                                                                                                                                                                        | 0.1.0 |
| **`tokenEndpoint`**         | <code>string</code>                       | The token endpoint URL. Either `issuerUrl` or both `authorizationEndpoint` and `tokenEndpoint` must be provided.                                                                                                                                                                                                | 0.1.0 |
| **`clientId`**              | <code>string</code>                       | The OAuth client ID.                                                                                                                                                                                                                                                                                            | 0.1.0 |
| **`redirectUrl`**           | <code>string</code>                       | The redirect URI to use after authentication.                                                                                                                                                                                                                                                                   | 0.1.0 |
| **`scopes`**                | <code>string[]</code>                     | The OAuth scopes to request.                                                                                                                                                                                                                                                                                    | 0.1.0 |
| **`loginHint`**             | <code>string</code>                       | A hint to the authorization server about the user's identifier to pre-fill the login form.                                                                                                                                                                                                                      | 0.1.0 |
| **`prompt`**                | <code>string</code>                       | The prompt parameter to control the authorization server UI behavior.                                                                                                                                                                                                                                           | 0.1.0 |
| **`additionalParameters`**  | <code>Record&lt;string, string&gt;</code> | Additional parameters to include in the authorization request.                                                                                                                                                                                                                                                  | 0.1.0 |


#### LogoutOptions

| Prop                        | Type                                      | Description                                                                                                                                                                        | Since |
| --------------------------- | ----------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`issuerUrl`**             | <code>string</code>                       | The OpenID Connect issuer URL used to fetch the discovery document at `{issuerUrl}/.well-known/openid-configuration`. Either `issuerUrl` or `endSessionEndpoint` must be provided. | 0.1.0 |
| **`endSessionEndpoint`**    | <code>string</code>                       | The end-session endpoint URL. Either `issuerUrl` or `endSessionEndpoint` must be provided.                                                                                         | 0.1.0 |
| **`idToken`**               | <code>string</code>                       | The ID token hint for session identification.                                                                                                                                      | 0.1.0 |
| **`postLogoutRedirectUrl`** | <code>string</code>                       | The redirect URI to use after logout.                                                                                                                                              | 0.1.0 |
| **`additionalParameters`**  | <code>Record&lt;string, string&gt;</code> | Additional parameters to include in the end-session request.                                                                                                                       | 0.1.0 |


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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/oauth/LICENSE).
