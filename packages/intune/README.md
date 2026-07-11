# Capacitor Intune Plugin

Unofficial Capacitor plugin for [Microsoft Intune](https://www.microsoft.com/en-us/security/business/microsoft-intune).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Intune plugin integrates the Microsoft Intune App SDK for Mobile Application Management (MAM) into Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🛡️ **App Protection Policies**: Automatic enforcement of PIN, encryption, copy/paste and screenshot restrictions after the native integration.
- 🔑 **MSAL**: Acquire tokens interactively or silently via the Microsoft Authentication Library.
- 🧾 **Enrollment**: Register and enroll accounts in Mobile Application Management (MAM) — without device enrollment.
- 📋 **Typed Policy Introspection**: Read the applied app protection policy as typed booleans to adapt your UI.
- ⚙️ **App Configuration**: Read the application configuration deployed via the MAM channel, including conflict information.
- 🧹 **Selective Wipe Events**: Get notified when the Intune service requests a wipe so you can purge the web layer storage (e.g. IndexedDB, Local Storage) that the SDK cannot wipe itself.
- 🩺 **Diagnostics**: Show the Intune diagnostic console from JavaScript.
- 📌 **Current SDK Pins**: Built against current Microsoft Intune App SDK and MSAL versions — Microsoft blocks apps that ship outdated SDKs.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Managed Configurations](https://capawesome.io/docs/sdks/capacitor/managed-configurations/) plugin, which covers the MDM channel (see [Choosing between the MAM and MDM channel](#choosing-between-the-mam-and-mdm-channel)).

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Intune plugin is typically used in line-of-business apps that are distributed to employees of organizations that manage corporate data with Microsoft Intune, for example:

- **App protection without device enrollment**: Protect corporate data in your app on personal (BYOD) devices via Mobile Application Management (MAM).
- **Conditional access**: Combine with Microsoft Entra conditional access policies that require an Intune-protected app.
- **Policy-aware UI**: Read the applied app protection policy and hide or disable features (e.g. local export) that the policy does not allow.
- **Per-tenant configuration**: Read the application configuration that the organization's IT administrator has deployed for the signed-in account.
- **Selective wipe**: Clean up the web layer storage of your app when the organization wipes its corporate data.
- **Ionic enterprise migration**: Migrate from the discontinued Ionic enterprise Intune integration to a maintained, free plugin.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-intune` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-intune
npx cap sync
```

This plugin requires a [Microsoft Intune](https://www.microsoft.com/en-us/security/business/microsoft-intune) tenant with Intune licenses and an app registration in [Microsoft Entra ID](https://entra.microsoft.com/). Create the app registration in the Microsoft Entra admin center, note its **Application (client) ID** and configure the platform-specific redirect URIs (see below). The `acquireToken(...)` scopes you request must be exposed or granted on this app registration.

> [!IMPORTANT]
> The Intune App SDKs are developed and licensed by Microsoft (see [Licensing](#licensing)). This plugin declares them as dependencies and downloads them from Microsoft's official repositories at build or install time. It does not bundle or modify them.

### Android

The Microsoft Intune App SDK for Android is downloaded automatically from [Microsoft's official GitHub repository](https://github.com/microsoftconnect/ms-intune-app-sdk-android) during the Gradle build. However, the Intune App SDK requires several changes to your app project that no plugin can make for you. Follow the steps below or use [Trapeze](#automated-setup-with-trapeze) to automate them.

#### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `$intuneMamSdkVersion` version of the [Microsoft Intune App SDK for Android](https://github.com/microsoftconnect/ms-intune-app-sdk-android) (default: `12.4.0`)
- `$msalVersion` version of `com.microsoft.identity.client:msal` (default: `8.4.0`)

#### MAM Build Plugin

The Intune App SDK relies on a Gradle build plugin that rewrites the Android base classes of your app **and all Capacitor plugins** to their MAM equivalents. Only the app module can apply this plugin — it cannot be applied by a library. Add the following to the `buildscript` block of your `android/build.gradle` file:

```diff
 buildscript {
     repositories {
         google()
         mavenCentral()
+        ivy {
+            url 'https://raw.githubusercontent.com/microsoftconnect/ms-intune-app-sdk-android'
+            patternLayout { artifact '[revision]/GradlePlugin/[artifact].[ext]' }
+            metadataSources { artifact() }
+            content { includeGroup 'com.microsoft.intune.mam.build' }
+        }
     }
     dependencies {
         classpath 'com.android.tools.build:gradle:8.13.0'
+        classpath 'org.javassist:javassist:3.29.2-GA'
+        classpath 'com.microsoft.intune.mam.build:com.microsoft.intune.mam.build:12.4.0@jar'
     }
 }
```

Then apply the plugin in your `android/app/build.gradle` file:

```diff
 apply plugin: 'com.android.application'
+apply plugin: 'com.microsoft.intune.mam'
```

#### Application Class

The Intune App SDK requires an `Application` class that registers the MAM components before any other code runs. Set the ready-made class provided by this plugin in the `application` tag of your `android/app/src/main/AndroidManifest.xml` file:

```xml
<application
    android:name="io.capawesome.capacitorjs.plugins.intune.IntuneApplication"
    ...>
```

If your app already uses a custom `Application` class, call the initializer yourself instead:

```java
import io.capawesome.capacitorjs.plugins.intune.Intune;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intune.initialize(this);
    }
}
```

It's also recommended to disable predictive back gestures in the `application` tag since the Intune App SDK does not support them yet:

```xml
<application
    android:enableOnBackInvokedCallback="false"
    ...>
```

#### MSAL Configuration

Create the file `android/app/src/main/res/raw/auth_config.json` with your MSAL configuration:

```json
{
  "client_id": "YOUR_CLIENT_ID",
  "authorization_user_agent": "DEFAULT",
  "redirect_uri": "msauth://YOUR_PACKAGE_NAME/YOUR_BASE64_URL_ENCODED_PACKAGE_SIGNATURE",
  "account_mode": "MULTIPLE",
  "broker_redirect_uri_registered": true,
  "authorities": [
    {
      "type": "AAD",
      "audience": {
        "type": "AzureADMultipleOrgs"
      }
    }
  ]
}
```

You can generate the base64-encoded signature hash of your signing key with:

```bash
keytool -exportcert -alias YOUR_KEY_ALIAS -keystore YOUR_KEYSTORE | openssl sha1 -binary | openssl base64
```

Make sure the same redirect URI (`msauth://YOUR_PACKAGE_NAME/YOUR_BASE64_URL_ENCODED_PACKAGE_SIGNATURE`) is registered as an **Android** platform redirect URI on your app registration in the Microsoft Entra admin center.

Next, add the following activity to the `application` tag of your `android/app/src/main/AndroidManifest.xml` file so that MSAL can receive the redirect from the Microsoft sign-in flow:

```xml
<activity android:name="com.microsoft.identity.client.BrowserTabActivity" android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="msauth" android:host="YOUR_PACKAGE_NAME" android:path="/YOUR_BASE64_ENCODED_PACKAGE_SIGNATURE" />
    </intent-filter>
</activity>
```

#### Gradle Properties

Add the following to your `android/gradle.properties` file. Without it, release builds may report `MAM Enabled: No` in the diagnostic console:

```properties
android.enableResourceOptimizations=false
```

#### Company Portal

App protection policies are only applied when the [Company Portal](https://play.google.com/store/apps/details?id=com.microsoft.windowsintune.companyportal) app is installed on the device. The user does **not** need to sign in to the Company Portal. Without it, your app behaves as unmanaged.

### iOS

The Microsoft Intune App SDK for iOS requires **iOS 17+** as deployment target and a current Xcode version. This deviates from the usual iOS 15 minimum of the Capawesome plugin collection. Make sure the deployment target of your app is set to iOS 17.0 or later (in `ios/App/App.xcodeproj` and, if you use Swift Package Manager, in `ios/App/CapApp-SPM/Package.swift`).

The SDK is consumed from [Microsoft's official GitHub repository](https://github.com/msintuneappsdk/ms-intune-app-sdk-ios): via Swift Package Manager it is resolved like any other package; via CocoaPods it is downloaded automatically during `pod install` (Microsoft does not publish a CocoaPods pod).

#### Info.plist

Add the `IntuneMAMSettings` dictionary to your `ios/App/App/Info.plist` file (the keys keep their legacy `ADAL` names for historical reasons):

```xml
<key>IntuneMAMSettings</key>
<dict>
    <key>ADALAuthority</key>
    <string>https://login.microsoftonline.com/YOUR_TENANT_ID</string>
    <key>ADALClientId</key>
    <string>YOUR_CLIENT_ID</string>
    <key>ADALRedirectUri</key>
    <string>msauth.YOUR_BUNDLE_ID://auth</string>
</dict>
```

This plugin also uses these values to configure MSAL, so no separate MSAL configuration is needed.

Next, register the MSAL redirect URL scheme and the query schemes in the same file:

```xml
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleURLName</key>
        <string>MSAL</string>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>msauth.YOUR_BUNDLE_ID</string>
        </array>
    </dict>
</array>
<key>LSApplicationQueriesSchemes</key>
<array>
    <string>msauthv2</string>
    <string>msauthv3</string>
    <string>http-intunemam</string>
    <string>https-intunemam</string>
</array>
```

Make sure the redirect URI (`msauth.YOUR_BUNDLE_ID://auth`) is registered as an **iOS/macOS** platform redirect URI on your app registration in the Microsoft Entra admin center. The plugin handles the MSAL redirect automatically — no `AppDelegate` changes are required.

Finally, add a Face ID usage description if your app does not have one yet, since app protection policies may require biometric unlock:

```xml
<key>NSFaceIDUsageDescription</key>
<string>This app uses Face ID to secure corporate data.</string>
```

#### Keychain Sharing

Enable the **Keychain Sharing** capability for your app target in Xcode and add the following keychain groups (in this order):

1. `com.example.app` (your bundle ID, usually already present)
2. `com.microsoft.intune.mam`
3. `com.microsoft.adalcache`

#### IntuneMAMConfigurator

Microsoft ships the `IntuneMAMConfigurator` tool with the [SDK repository](https://github.com/msintuneappsdk/ms-intune-app-sdk-ios). It applies the minimum required `Info.plist` changes for Intune management (including the `-intunemam` query scheme variants for every scheme your app queries) and is idempotent:

```bash
IntuneMAMConfigurator -i ios/App/App/Info.plist -e ios/App/App/App.entitlements
```

Running it is recommended before you ship, especially if your app passes additional URL schemes to `canOpenURL`.

### Automated Setup with Trapeze

Most of the host app changes above can be automated with [Trapeze](https://trapeze.dev/), which is also used by [Capawesome Cloud](https://capawesome.io/cloud/). Save the following configuration as `trapeze.yaml` and run `npx @trapezedev/configure run trapeze.yaml` with the `CLIENT_ID`, `TENANT_ID`, `PACKAGE_NAME`, `BUNDLE_ID` and `SIGNATURE_HASH` variables set:

```yaml
vars:
  CLIENT_ID:
  TENANT_ID:
  PACKAGE_NAME:
  BUNDLE_ID:
  SIGNATURE_HASH:

platforms:
  android:
    gradle:
      - file: build.gradle
        target:
          buildscript:
            dependencies:
        insert: |
          classpath 'org.javassist:javassist:3.29.2-GA'
          classpath 'com.microsoft.intune.mam.build:com.microsoft.intune.mam.build:12.4.0@jar'
      - file: build.gradle
        target:
          buildscript:
            repositories:
        insert: |
          ivy {
              url 'https://raw.githubusercontent.com/microsoftconnect/ms-intune-app-sdk-android'
              patternLayout { artifact '[revision]/GradlePlugin/[artifact].[ext]' }
              metadataSources { artifact() }
              content { includeGroup 'com.microsoft.intune.mam.build' }
          }
      - file: app/build.gradle
        target:
        insert: |
          apply plugin: 'com.microsoft.intune.mam'
    manifest:
      - file: AndroidManifest.xml
        target: manifest/application
        attrs:
          android:name: io.capawesome.capacitorjs.plugins.intune.IntuneApplication
          android:enableOnBackInvokedCallback: 'false'
      - file: AndroidManifest.xml
        target: manifest/application
        inject: |
          <activity android:name="com.microsoft.identity.client.BrowserTabActivity" android:exported="true">
            <intent-filter>
              <action android:name="android.intent.action.VIEW" />
              <category android:name="android.intent.category.DEFAULT" />
              <category android:name="android.intent.category.BROWSABLE" />
              <data android:scheme="msauth" android:host="$PACKAGE_NAME" android:path="/$SIGNATURE_HASH" />
            </intent-filter>
          </activity>
    res:
      - path: raw
        file: auth_config.json
        text: |
          {
            "client_id": "$CLIENT_ID",
            "authorization_user_agent": "DEFAULT",
            "redirect_uri": "msauth://$PACKAGE_NAME/$SIGNATURE_HASH",
            "account_mode": "MULTIPLE",
            "broker_redirect_uri_registered": true,
            "authorities": [
              {
                "type": "AAD",
                "audience": {
                  "type": "AzureADMultipleOrgs"
                }
              }
            ]
          }
  ios:
    targets:
      App:
        buildSettings:
          IPHONEOS_DEPLOYMENT_TARGET: '17.0'
        entitlements:
          - keychain-access-groups:
              [
                '$BUNDLE_ID',
                'com.microsoft.intune.mam',
                'com.microsoft.adalcache',
              ]
        plist:
          - entries:
              - IntuneMAMSettings:
                  ADALAuthority: https://login.microsoftonline.com/$TENANT_ID
                  ADALClientId: $CLIENT_ID
                  ADALRedirectUri: msauth.$BUNDLE_ID://auth
              - CFBundleURLTypes:
                  - CFBundleURLName: MSAL
                    CFBundleURLSchemes:
                      - msauth.$BUNDLE_ID
              - LSApplicationQueriesSchemes:
                  - msauthv2
                  - msauthv3
                  - http-intunemam
                  - https-intunemam
              - NSFaceIDUsageDescription: This app uses Face ID to secure corporate data.
```

> [!NOTE]
> Trapeze cannot edit `gradle.properties`, so the [Gradle Properties](#gradle-properties) step must still be done manually. Review the result after running Trapeze: some list-valued Info.plist entries may be merged rather than replaced, and the Gradle insertions are not idempotent — run them only once.

### Web

This plugin does not provide a web implementation. All methods reject with an `unimplemented` error on the web platform.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to use the plugin.

### Sign in and enroll an account

Acquire a token via MSAL and enroll the returned account in Mobile Application Management (MAM). The enrollment itself is asynchronous — listen for the `enrollmentChange` event to get the result:

```typescript
import { Intune } from '@capawesome/capacitor-intune';

const signInAndEnroll = async () => {
  await Intune.addListener('enrollmentChange', event => {
    console.log('Enrollment status:', event.status);
  });
  const { accountId } = await Intune.acquireToken({
    scopes: ['https://graph.microsoft.com/.default'],
  });
  await Intune.registerAndEnrollAccount({ accountId });
};
```

### Read the app protection policy

Adapt your UI to the applied app protection policy:

```typescript
import { Intune } from '@capawesome/capacitor-intune';

const applyPolicy = async () => {
  const { account } = await Intune.getEnrolledAccount();
  if (!account) {
    return;
  }
  const policy = await Intune.getPolicy({ accountId: account.accountId });
  if (!policy.saveToPersonalStorageAllowed) {
    // Hide your export/download buttons.
  }
};
```

### Read the app configuration

Read the configuration values that the organization's IT administrator has deployed:

```typescript
import { Intune } from '@capawesome/capacitor-intune';

const readAppConfig = async () => {
  const { account } = await Intune.getEnrolledAccount();
  if (!account) {
    return;
  }
  const { values } = await Intune.getAppConfig({ accountId: account.accountId });
  console.log('Server URL:', values['com.example.serverUrl']);
};
```

### Handle selective wipe

The Intune App SDK wipes the data it manages, but it does **not** wipe the web layer storage of your Capacitor app. Register the `wipeRequested` listener as early as possible and purge your web storage when it fires:

```typescript
import { Intune } from '@capawesome/capacitor-intune';

const registerWipeListener = async () => {
  await Intune.addListener('wipeRequested', async () => {
    localStorage.clear();
    sessionStorage.clear();
    const databases = await indexedDB.databases();
    for (const database of databases) {
      if (database.name) {
        indexedDB.deleteDatabase(database.name);
      }
    }
  });
};
```

## API

<docgen-index>

* [`acquireToken(...)`](#acquiretoken)
* [`acquireTokenSilent(...)`](#acquiretokensilent)
* [`getAppConfig(...)`](#getappconfig)
* [`getEnrolledAccount()`](#getenrolledaccount)
* [`getPolicy(...)`](#getpolicy)
* [`getSdkVersion()`](#getsdkversion)
* [`loginAndEnrollAccount()`](#loginandenrollaccount)
* [`registerAndEnrollAccount(...)`](#registerandenrollaccount)
* [`showDiagnosticConsole()`](#showdiagnosticconsole)
* [`unenrollAccount(...)`](#unenrollaccount)
* [`addListener('appConfigChange', ...)`](#addlistenerappconfigchange-)
* [`addListener('enrollmentChange', ...)`](#addlistenerenrollmentchange-)
* [`addListener('policyChange', ...)`](#addlistenerpolicychange-)
* [`addListener('wipeRequested', ...)`](#addlistenerwiperequested-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### acquireToken(...)

```typescript
acquireToken(options: AcquireTokenOptions) => Promise<AcquireTokenResult>
```

Acquire an access token interactively via the Microsoft Authentication
Library (MSAL).

This presents the Microsoft sign-in UI if necessary. Use the returned
`accountId` to enroll the account via `registerAndEnrollAccount(...)`.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#acquiretokenoptions">AcquireTokenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#acquiretokenresult">AcquireTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### acquireTokenSilent(...)

```typescript
acquireTokenSilent(options: AcquireTokenSilentOptions) => Promise<AcquireTokenResult>
```

Acquire an access token silently via the Microsoft Authentication
Library (MSAL) for an already signed-in account.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#acquiretokensilentoptions">AcquireTokenSilentOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#acquiretokenresult">AcquireTokenResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getAppConfig(...)

```typescript
getAppConfig(options: GetAppConfigOptions) => Promise<GetAppConfigResult>
```

Get the application configuration values that the organization's IT
administrator has deployed for the given account via the MAM channel.

For configuration deployed via the MDM channel (device enrollment), use
the Managed Configurations plugin instead.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#getappconfigoptions">GetAppConfigOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getappconfigresult">GetAppConfigResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getEnrolledAccount()

```typescript
getEnrolledAccount() => Promise<GetEnrolledAccountResult>
```

Get the account that is currently enrolled in Mobile Application
Management (MAM).

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getenrolledaccountresult">GetEnrolledAccountResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getPolicy(...)

```typescript
getPolicy(options: GetPolicyOptions) => Promise<GetPolicyResult>
```

Get the app protection policy that is currently applied for the given
account.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#getpolicyoptions">GetPolicyOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getpolicyresult">GetPolicyResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getSdkVersion()

```typescript
getSdkVersion() => Promise<GetSdkVersionResult>
```

Get the versions of the Intune App SDK and the Microsoft Authentication
Library (MSAL) that the plugin was built with.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getsdkversionresult">GetSdkVersionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### loginAndEnrollAccount()

```typescript
loginAndEnrollAccount() => Promise<void>
```

Sign in and enroll an account using the login UI provided by the Intune
App SDK.

On Android, use `acquireToken(...)` followed by
`registerAndEnrollAccount(...)` instead.

Only available on iOS.

**Since:** 0.1.0

--------------------


### registerAndEnrollAccount(...)

```typescript
registerAndEnrollAccount(options: RegisterAndEnrollAccountOptions) => Promise<void>
```

Register an account for Mobile Application Management (MAM) and enroll
it in the Intune service.

Call this after a successful `acquireToken(...)` call. The enrollment
itself is asynchronous; listen for the `enrollmentChange` event to get
the enrollment result.

Only available on Android and iOS.

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#registerandenrollaccountoptions">RegisterAndEnrollAccountOptions</a></code> |

**Since:** 0.1.0

--------------------


### showDiagnosticConsole()

```typescript
showDiagnosticConsole() => Promise<void>
```

Show the diagnostic console of the Intune App SDK.

The console allows the user to inspect the SDK state and collect logs
for support requests.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### unenrollAccount(...)

```typescript
unenrollAccount(options: UnenrollAccountOptions) => Promise<void>
```

Unenroll an account from Mobile Application Management (MAM) and
unregister it from the Intune service.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#unenrollaccountoptions">UnenrollAccountOptions</a></code> |

**Since:** 0.1.0

--------------------


### addListener('appConfigChange', ...)

```typescript
addListener(eventName: 'appConfigChange', listenerFunc: (event: AppConfigChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the application configuration changes.

Use `getAppConfig(...)` to read the new configuration values.

Only available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'appConfigChange'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#appconfigchangeevent">AppConfigChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('enrollmentChange', ...)

```typescript
addListener(eventName: 'enrollmentChange', listenerFunc: (event: EnrollmentChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the enrollment state of an account changes, for example
when an enrollment attempt succeeds or fails.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'enrollmentChange'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#enrollmentchangeevent">EnrollmentChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('policyChange', ...)

```typescript
addListener(eventName: 'policyChange', listenerFunc: (event: PolicyChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when the app protection policy changes.

Use `getPolicy(...)` to read the new policy values.

Only available on Android and iOS.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'policyChange'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#policychangeevent">PolicyChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('wipeRequested', ...)

```typescript
addListener(eventName: 'wipeRequested', listenerFunc: (event: WipeRequestedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Intune service requests a selective wipe of the
account's data.

The Intune App SDK wipes the data it manages, but it does **not** wipe
the web layer storage of your Capacitor app (e.g. IndexedDB, Local
Storage). Use this event to clean up any data your web code has
persisted.

The event is delivered even if the wipe was requested while your app
was not running (see the documentation for details).

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'wipeRequested'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#wiperequestedevent">WipeRequestedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### AcquireTokenResult

| Prop              | Type                        | Description                                                                                                             | Since |
| ----------------- | --------------------------- | ----------------------------------------------------------------------------------------------------------------------- | ----- |
| **`accessToken`** | <code>string</code>         | The acquired access token.                                                                                              | 0.1.0 |
| **`accountId`**   | <code>string</code>         | The Microsoft Entra object ID (OID) of the signed-in account. Use this identifier for all other methods of this plugin. | 0.1.0 |
| **`idToken`**     | <code>string \| null</code> | The raw ID token of the signed-in account, if available.                                                                | 0.1.0 |
| **`tenantId`**    | <code>string \| null</code> | The Microsoft Entra tenant ID of the signed-in account, if available.                                                   | 0.1.0 |
| **`username`**    | <code>string \| null</code> | The username (usually the UPN) of the signed-in account, if available.                                                  | 0.1.0 |


#### AcquireTokenOptions

| Prop              | Type                  | Description                                                                                            | Default            | Since |
| ----------------- | --------------------- | ------------------------------------------------------------------------------------------------------ | ------------------ | ----- |
| **`forcePrompt`** | <code>boolean</code>  | Whether or not to force the account selection prompt to be shown, even if a user is already signed in. | <code>false</code> | 0.1.0 |
| **`loginHint`**   | <code>string</code>   | The username to pre-fill in the sign-in UI.                                                            |                    | 0.1.0 |
| **`scopes`**      | <code>string[]</code> | The scopes to request the access token for.                                                            |                    | 0.1.0 |


#### AcquireTokenSilentOptions

| Prop               | Type                  | Description                                                                  | Default            | Since |
| ------------------ | --------------------- | ---------------------------------------------------------------------------- | ------------------ | ----- |
| **`accountId`**    | <code>string</code>   | The Microsoft Entra object ID (OID) of the account to acquire the token for. |                    | 0.1.0 |
| **`forceRefresh`** | <code>boolean</code>  | Whether or not to ignore any cached token and force a token refresh.         | <code>false</code> | 0.1.0 |
| **`scopes`**       | <code>string[]</code> | The scopes to request the access token for.                                  |                    | 0.1.0 |


#### GetAppConfigResult

| Prop            | Type                                      | Description                                                                                                                             | Since |
| --------------- | ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`conflicts`** | <code>AppConfigConflict[]</code>          | The configuration keys for which multiple conflicting values have been deployed.                                                        | 0.1.0 |
| **`values`**    | <code>Record&lt;string, string&gt;</code> | The merged application configuration values. For keys with conflicting values, the value that the Intune App SDK returns first is used. | 0.1.0 |


#### AppConfigConflict

| Prop         | Type                  | Description                                                   | Since |
| ------------ | --------------------- | ------------------------------------------------------------- | ----- |
| **`key`**    | <code>string</code>   | The configuration key for which conflicting values exist.     | 0.1.0 |
| **`values`** | <code>string[]</code> | All values that have been deployed for the configuration key. | 0.1.0 |


#### GetAppConfigOptions

| Prop            | Type                | Description                                                                                  | Since |
| --------------- | ------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string</code> | The Microsoft Entra object ID (OID) of the account to get the application configuration for. | 0.1.0 |


#### GetEnrolledAccountResult

| Prop          | Type                                                                | Description                                               | Since |
| ------------- | ------------------------------------------------------------------- | --------------------------------------------------------- | ----- |
| **`account`** | <code><a href="#enrolledaccount">EnrolledAccount</a> \| null</code> | The enrolled account or `null` if no account is enrolled. | 0.1.0 |


#### EnrolledAccount

| Prop            | Type                        | Description                                                           | Since |
| --------------- | --------------------------- | --------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string</code>         | The Microsoft Entra object ID (OID) of the enrolled account.          | 0.1.0 |
| **`username`**  | <code>string \| null</code> | The username (usually the UPN) of the enrolled account, if available. | 0.1.0 |


#### GetPolicyResult

| Prop                               | Type                 | Description                                                                                                                      | Since |
| ---------------------------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`contactSyncAllowed`**           | <code>boolean</code> | Whether or not the policy allows syncing contacts to the device.                                                                 | 0.1.0 |
| **`fileEncryptionRequired`**       | <code>boolean</code> | Whether or not the policy requires files to be encrypted. On Android, this reflects whether file encryption is currently in use. | 0.1.0 |
| **`managedBrowserRequired`**       | <code>boolean</code> | Whether or not the policy requires links to be opened in a managed browser (e.g. Microsoft Edge).                                | 0.1.0 |
| **`pinRequired`**                  | <code>boolean</code> | Whether or not the policy requires a PIN to access the app.                                                                      | 0.1.0 |
| **`saveToPersonalStorageAllowed`** | <code>boolean</code> | Whether or not the policy allows saving files to personal (local) storage.                                                       | 0.1.0 |
| **`screenCaptureAllowed`**         | <code>boolean</code> | Whether or not the policy allows taking screenshots.                                                                             | 0.1.0 |


#### GetPolicyOptions

| Prop            | Type                | Description                                                                              | Since |
| --------------- | ------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string</code> | The Microsoft Entra object ID (OID) of the account to get the app protection policy for. | 0.1.0 |


#### GetSdkVersionResult

| Prop                   | Type                        | Description                                                               | Since |
| ---------------------- | --------------------------- | ------------------------------------------------------------------------- | ----- |
| **`intuneSdkVersion`** | <code>string</code>         | The version of the Intune App SDK.                                        | 0.1.0 |
| **`msalVersion`**      | <code>string \| null</code> | The version of the Microsoft Authentication Library (MSAL), if available. | 0.1.0 |


#### RegisterAndEnrollAccountOptions

| Prop            | Type                | Description                                                                                                    | Since |
| --------------- | ------------------- | -------------------------------------------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string</code> | The Microsoft Entra object ID (OID) of the account to register and enroll, as returned by `acquireToken(...)`. | 0.1.0 |


#### UnenrollAccountOptions

| Prop            | Type                 | Description                                                                                           | Default            | Since |
| --------------- | -------------------- | ----------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`accountId`** | <code>string</code>  | The Microsoft Entra object ID (OID) of the account to unenroll.                                       |                    | 0.1.0 |
| **`wipe`**      | <code>boolean</code> | Whether or not the account's data should be wiped as part of the unenrollment. Only available on iOS. | <code>false</code> | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### AppConfigChangeEvent

| Prop            | Type                        | Description                                                                | Since |
| --------------- | --------------------------- | -------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string \| null</code> | The Microsoft Entra object ID (OID) of the affected account, if available. | 0.1.0 |


#### EnrollmentChangeEvent

| Prop            | Type                                                          | Description                                                                | Since |
| --------------- | ------------------------------------------------------------- | -------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string \| null</code>                                   | The Microsoft Entra object ID (OID) of the affected account, if available. | 0.1.0 |
| **`status`**    | <code><a href="#enrollmentstatus">EnrollmentStatus</a></code> | The new enrollment status of the account.                                  | 0.1.0 |


#### PolicyChangeEvent

| Prop            | Type                        | Description                                                                | Since |
| --------------- | --------------------------- | -------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string \| null</code> | The Microsoft Entra object ID (OID) of the affected account, if available. | 0.1.0 |


#### WipeRequestedEvent

| Prop            | Type                        | Description                                                                | Since |
| --------------- | --------------------------- | -------------------------------------------------------------------------- | ----- |
| **`accountId`** | <code>string \| null</code> | The Microsoft Entra object ID (OID) of the affected account, if available. | 0.1.0 |


### Type Aliases


#### EnrollmentStatus

The enrollment status of an account.

<code>'enrolled' | 'failed' | 'pending' | 'unenrolled'</code>

</docgen-api>

## Platform Support

Not every feature is available on all platforms. The following table lists the notable per-platform differences of the plugin's API:

| Method / Option                       | Android | iOS | Web |
| ------------------------------------- | :-----: | :-: | :-: |
| `loginAndEnrollAccount()`              |   ❌    | ✅  | ❌  |
| `unenrollAccount(...)` (`wipe` option) |   ❌    | ✅  | ❌  |

Additional notes:

- On Android, use `acquireToken(...)` followed by `registerAndEnrollAccount(...)` instead of `loginAndEnrollAccount()`. The Intune App SDK for Android does not provide its own login UI.
- On Android, `fileEncryptionRequired` reflects whether file encryption is currently **in use** by the Intune App SDK, which is the closest equivalent the SDK exposes.
- The `wipeRequested` event is persisted and replayed on the next app launch if no listener was registered when the wipe arrived. In rare cases the event may be delivered more than once, so make sure your wipe handler is idempotent.
- Policy **enforcement** (PIN, encryption, copy/paste and screenshot restrictions, etc.) is performed automatically by the Intune App SDK once the native integration is in place. The JavaScript API exists for the parts that enforcement cannot do: enrolling accounts, reading configuration, adapting your UI to the policy, and cleaning up web storage on selective wipe.

## Choosing between the MAM and MDM channel

Organizations can deliver app configuration through two different channels, and administrators frequently mix them up. This plugin covers the **MAM channel**; the [Managed Configurations](https://capawesome.io/docs/sdks/capacitor/managed-configurations/) plugin covers the **MDM channel**:

|                            | MAM channel (this plugin)                       | MDM channel ([Managed Configurations](https://capawesome.io/docs/sdks/capacitor/managed-configurations/)) |
| -------------------------- | ----------------------------------------------- | --------------------------------------------------------- |
| Device enrollment required | No                                              | Yes                                                        |
| Delivered via              | Intune App SDK (Intune service)                 | `RestrictionsManager` / `com.apple.configuration.managed`  |
| Targeted at                | The signed-in account (identity)                | The device                                                 |
| EMM vendor                 | Microsoft Intune only                           | Any EMM/MDM vendor                                         |
| Typical scenario           | BYOD / app protection without device management | Corporate-owned, fully managed devices                     |

If your organization deploys the app configuration policy with **"Managed apps"** as the delivery channel in the Intune admin center, use this plugin. If it is deployed with **"Managed devices"**, use the Managed Configurations plugin. Apps that must support both scenarios should read both channels.

## Testing

Exercising Mobile Application Management (MAM) requires infrastructure that cannot be simulated locally:

- A **Microsoft Intune tenant** with **Intune licenses** and a **licensed test user**.
- An **app protection policy** and (optionally) an **app configuration policy** targeted at the test user and your app.
- On Android, the **Company Portal** app installed on the test device.

Without a tenant, the plugin compiles and loads, but enrollment fails with `AccountNotLicensed`-style errors and no policy is applied. Building this plugin (e.g. via `npm run verify`) only proves that the code compiles — it does not exercise any MAM functionality.

## Licensing

The Microsoft Intune App SDKs for Android and iOS are proprietary software, licensed by Microsoft under the [Microsoft License Terms Intune App SDK](https://github.com/msintuneappsdk/ms-intune-app-sdk-ios/blob/master/Microsoft%20License%20Terms%20Intune%20App%20SDK%20for%20iOS.pdf). This plugin does **not** bundle or modify the Intune App SDKs — it declares them as dependencies and downloads them from Microsoft's official repositories at build or install time. By building an app with this plugin, you accept Microsoft's license terms for the Intune App SDK. The Microsoft Authentication Library (MSAL) is licensed under the MIT license. Using the plugin requires a Microsoft Intune tenant with appropriate licenses. The MIT license of this plugin covers the wrapper code only, not the Microsoft SDKs.

Note that Microsoft's license terms also place obligations on **your app** as the distributor of the Intune App SDK binaries — among other things regarding your app's own functionality and copyright notice, license compatibility, and indemnification. Review the license terms linked above before shipping your app.

## FAQ

### How is this plugin different from other similar plugins?

It integrates the Microsoft Intune App SDK for Mobile Application Management on Android and iOS through a fully typed API, enforcing app protection policies and exposing them as typed booleans so you can adapt your UI, acquiring tokens via MSAL, and enrolling accounts without device enrollment. It emits selective-wipe events so you can purge the web-layer storage (such as IndexedDB and Local Storage) that the SDK cannot reach itself, and it is built against current Intune and MSAL versions, which matters because Microsoft blocks apps that ship outdated SDKs. It is a free, actively maintained plugin that stays current with the latest Capacitor version.

### Do I need a Microsoft Intune tenant to use this plugin?

Yes. This plugin wraps the Microsoft Intune App SDK, which requires a Microsoft Intune tenant, Intune licenses, and an app registration in Microsoft Entra ID (see [Testing](#testing)).

### Why does the plugin require iOS 17?

The current Microsoft Intune App SDK for iOS requires iOS 17 or later. Microsoft actively blocks apps built with outdated Intune App SDK versions, so this plugin always tracks Microsoft's current SDK line instead of pinning an older one.

### Does this plugin enforce the app protection policies?

Yes, automatically. Once the native integration (MAM build plugin on Android, SDK integration on iOS) is in place, the Intune App SDK enforces PIN, encryption, data transfer and screenshot restrictions itself. The JavaScript API of this plugin is for the parts enforcement cannot do: enrollment, introspection, and cleaning up web storage on selective wipe.

### What is the difference between this plugin and the Managed Configurations plugin?

This plugin reads the MAM channel (Intune App SDK, no device enrollment required). The [Managed Configurations](https://capawesome.io/docs/sdks/capacitor/managed-configurations/) plugin reads the MDM channel (device enrollment required, works with any EMM vendor). See [Choosing between the MAM and MDM channel](#choosing-between-the-mam-and-mdm-channel).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/intune/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/intune/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Microsoft Corporation or any of its affiliates or subsidiaries. "Microsoft" and "Microsoft Intune" are trademarks of the Microsoft group of companies.
