# @capawesome/capacitor-grafana-faro

Unofficial Capacitor plugin for [Grafana Faro](https://grafana.com/oss/faro/).[^1]

> ⚠️ **Experimental:** This plugin is in early development. APIs may change between minor versions. Feedback and bug reports are very welcome — please [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for Grafana Faro. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 📝 **Logs**: Capture log messages with severity levels and structured context.
- 📡 **Events**: Track custom events with attributes and a platform domain.
- 💥 **Errors**: Capture errors with optional stack frames (compatible with [`stacktrace.js`](https://www.stacktracejs.com/)).
- 📊 **Measurements**: Record numeric metrics for performance and business KPIs.
- 👤 **User, Session and View Metadata**: Attach user, session and view information to every signal.
- 🛡️ **Native Crash Reporting**: Capture native crashes via [PLCrashReporter](https://github.com/microsoft/plcrashreporter) on iOS and [`ApplicationExitInfo`](https://developer.android.com/reference/android/app/ApplicationExitInfo) on Android.
- 🐌 **ANR Detection**: Detect Application Not Responding events on the Android main thread.
- 🌐 **Web Auto-Instrumentation**: Automatically capture uncaught errors, console output, Core Web Vitals, route changes and network performance on Web.
- ⏯️ **Pause and Resume**: Pause and resume telemetry on demand.
- 🎯 **Session Sampling**: Sample sessions with a configurable rate to control data volume.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-grafana-faro` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-grafana-faro @grafana/faro-web-sdk
npx cap sync
```

### iOS

#### Minimum Deployment Target

This plugin depends on [PLCrashReporter](https://github.com/microsoft/plcrashreporter) for native crash reporting. Make sure that your iOS deployment target is set to at least `15.0` in your Xcode project settings (usually in `ios/App/App.xcodeproj`):

```diff
-IPHONEOS_DEPLOYMENT_TARGET = 14.0
+IPHONEOS_DEPLOYMENT_TARGET = 15.0
```

If you are using **CocoaPods**, make sure that your iOS deployment target is set to at least `15.0` in your `Podfile`:

```ruby
platform :ios, '15.0'
```

#### Conflicts with other crash reporters

PLCrashReporter installs global crash handlers. Running this plugin alongside another crash reporter that also installs handlers (e.g. Firebase Crashlytics, Sentry) can lead to one or both reporters losing crash reports. Enable `instrumentations.nativeCrashReporting` only when no other crash reporter is active.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Build-time configuration for the Grafana Faro plugin.

When `url` and `appName` are both provided, the native plugin
auto-initializes during app startup. This allows native crash and ANR
handlers to be installed before any JavaScript code runs. Calling
`GrafanaFaro.initialize(...)` from JavaScript afterwards is not required
and will fail with an `already initialized` error.

Only applies to Android and iOS.

| Prop                   | Type                                                                    | Description                                                                                                                | Since |
| ---------------------- | ----------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`apiKey`**           | <code>string</code>                                                     | The API key sent as the `x-api-key` header.                                                                                | 0.1.0 |
| **`appEnvironment`**   | <code>string</code>                                                     | The environment of the application (e.g. `production`, `staging`).                                                         | 0.1.0 |
| **`appName`**          | <code>string</code>                                                     | The name of the application.                                                                                               | 0.1.0 |
| **`appNamespace`**     | <code>string</code>                                                     | The namespace of the application.                                                                                          | 0.1.0 |
| **`appVersion`**       | <code>string</code>                                                     | The version of the application.                                                                                            | 0.1.0 |
| **`instrumentations`** | <code>{ anrTracking?: boolean; nativeCrashReporting?: boolean; }</code> | Toggles for built-in automatic instrumentations. Only the toggles that apply at build time on Android and iOS are honored. | 0.1.0 |
| **`url`**              | <code>string</code>                                                     | The Faro collector endpoint URL.                                                                                           | 0.1.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "GrafanaFaro": {
      "apiKey": undefined,
      "appEnvironment": undefined,
      "appName": undefined,
      "appNamespace": undefined,
      "appVersion": undefined,
      "instrumentations": undefined,
      "url": 'https://faro-collector-prod.grafana.net/collect/<token>'
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-grafana-faro" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    GrafanaFaro: {
      apiKey: undefined,
      appEnvironment: undefined,
      appName: undefined,
      appNamespace: undefined,
      appVersion: undefined,
      instrumentations: undefined,
      url: 'https://faro-collector-prod.grafana.net/collect/<token>',
    },
  },
};

export default config;
```

</docgen-config>

## Usage

```typescript
import { GrafanaFaro } from '@capawesome/capacitor-grafana-faro';

const initialize = async () => {
  await GrafanaFaro.initialize({
    app: {
      environment: 'production',
      name: 'my-app',
      version: '1.0.0',
    },
    instrumentations: {
      anrTracking: true,
      console: true,
      errors: true,
      nativeCrashReporting: true,
      performance: true,
      view: true,
      webVitals: true,
    },
    url: 'https://faro-collector-prod-us-central-0.grafana.net/collect/REPLACE_ME',
  });
};

const pushLog = async () => {
  await GrafanaFaro.pushLog({
    level: 'info',
    message: 'User pressed sign-in button',
  });
};

const pushEvent = async () => {
  await GrafanaFaro.pushEvent({
    attributes: { provider: 'google' },
    name: 'sign_in_started',
  });
};

const pushError = async (error: Error) => {
  await GrafanaFaro.pushError({
    type: error.name,
    value: error.message,
  });
};

const pushErrorWithStacktrace = async (error: Error) => {
  // Use `stacktrace-js` (or any equivalent) to parse the stack into
  // structured frames.
  const StackTrace = await import('stacktrace-js');
  const stackFrames = await StackTrace.fromError(error);
  await GrafanaFaro.pushError({
    type: error.name,
    value: error.message,
    stackFrames: stackFrames.map(frame => ({
      columnNumber: frame.columnNumber,
      fileName: frame.fileName,
      functionName: frame.functionName,
      lineNumber: frame.lineNumber,
    })),
  });
};

const pushMeasurement = async () => {
  await GrafanaFaro.pushMeasurement({
    type: 'sign_in_duration',
    values: { duration_ms: 320 },
  });
};

const setUser = async () => {
  await GrafanaFaro.setUser({
    email: 'jane@example.com',
    id: 'user-123',
    username: 'jane',
  });
};

const setView = async () => {
  await GrafanaFaro.setView({ name: 'SignInView' });
};
```

## API

<docgen-index>

* [`getSession()`](#getsession)
* [`getView()`](#getview)
* [`initialize(...)`](#initialize)
* [`pause()`](#pause)
* [`pushError(...)`](#pusherror)
* [`pushEvent(...)`](#pushevent)
* [`pushLog(...)`](#pushlog)
* [`pushMeasurement(...)`](#pushmeasurement)
* [`resetSession()`](#resetsession)
* [`resetUser()`](#resetuser)
* [`setSession(...)`](#setsession)
* [`setUser(...)`](#setuser)
* [`setView(...)`](#setview)
* [`unpause()`](#unpause)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getSession()

```typescript
getSession() => Promise<GetSessionResult>
```

Get the current session.

**Returns:** <code>Promise&lt;<a href="#getsessionresult">GetSessionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getView()

```typescript
getView() => Promise<GetViewResult>
```

Get the current view.

**Returns:** <code>Promise&lt;<a href="#getviewresult">GetViewResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options: InitializeOptions) => Promise<void>
```

Initialize the Faro SDK.

**Attention**: This method must be called before any other method.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.0

--------------------


### pause()

```typescript
pause() => Promise<void>
```

Pause sending telemetry. New signals are still buffered locally.

**Since:** 0.1.0

--------------------


### pushError(...)

```typescript
pushError(options: PushErrorOptions) => Promise<void>
```

Push an error/exception.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pusherroroptions">PushErrorOptions</a></code> |

**Since:** 0.1.0

--------------------


### pushEvent(...)

```typescript
pushEvent(options: PushEventOptions) => Promise<void>
```

Push a custom event.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#pusheventoptions">PushEventOptions</a></code> |

**Since:** 0.1.0

--------------------


### pushLog(...)

```typescript
pushLog(options: PushLogOptions) => Promise<void>
```

Push a log message.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#pushlogoptions">PushLogOptions</a></code> |

**Since:** 0.1.0

--------------------


### pushMeasurement(...)

```typescript
pushMeasurement(options: PushMeasurementOptions) => Promise<void>
```

Push a measurement (numeric metric).

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#pushmeasurementoptions">PushMeasurementOptions</a></code> |

**Since:** 0.1.0

--------------------


### resetSession()

```typescript
resetSession() => Promise<void>
```

Clear the current session and start a new one.

**Since:** 0.1.0

--------------------


### resetUser()

```typescript
resetUser() => Promise<void>
```

Clear the current user.

**Since:** 0.1.0

--------------------


### setSession(...)

```typescript
setSession(options: SetSessionOptions) => Promise<void>
```

Set the current session.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#setsessionoptions">SetSessionOptions</a></code> |

**Since:** 0.1.0

--------------------


### setUser(...)

```typescript
setUser(options: SetUserOptions) => Promise<void>
```

Set the current user.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#usermetadata">UserMetadata</a></code> |

**Since:** 0.1.0

--------------------


### setView(...)

```typescript
setView(options: SetViewOptions) => Promise<void>
```

Set the current view (e.g. screen / route name).

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#viewmetadata">ViewMetadata</a></code> |

**Since:** 0.1.0

--------------------


### unpause()

```typescript
unpause() => Promise<void>
```

Resume sending telemetry.

**Since:** 0.1.0

--------------------


### Interfaces


#### GetSessionResult

| Prop             | Type                                    | Description                     | Since |
| ---------------- | --------------------------------------- | ------------------------------- | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | The current session attributes. | 0.1.0 |
| **`id`**         | <code>string</code>                     | The current session ID.         | 0.1.0 |


#### GetViewResult

| Prop       | Type                | Description            | Since |
| ---------- | ------------------- | ---------------------- | ----- |
| **`name`** | <code>string</code> | The current view name. | 0.1.0 |


#### InitializeOptions

| Prop                      | Type                                                                        | Description                                                         | Default            | Since |
| ------------------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------- | ------------------ | ----- |
| **`apiKey`**              | <code>string</code>                                                         | API key sent as the `x-api-key` header to the collector.            |                    | 0.1.0 |
| **`app`**                 | <code><a href="#appmetadata">AppMetadata</a></code>                         | Application metadata attached to every signal.                      |                    | 0.1.0 |
| **`ignoreErrors`**        | <code>string[]</code>                                                       | Error message patterns to ignore.                                   |                    | 0.1.0 |
| **`ignoreUrls`**          | <code>string[]</code>                                                       | URL patterns to ignore when instrumenting network calls.            |                    | 0.1.0 |
| **`instrumentations`**    | <code><a href="#instrumentationsoptions">InstrumentationsOptions</a></code> | Toggles for built-in automatic instrumentations.                    |                    | 0.1.0 |
| **`paused`**              | <code>boolean</code>                                                        | Start in a paused state. Telemetry must be resumed via `unpause()`. | <code>false</code> | 0.1.0 |
| **`sessionAttributes`**   | <code>{ [key: string]: string; }</code>                                     | Initial session attributes.                                         |                    | 0.1.0 |
| **`sessionSamplingRate`** | <code>number</code>                                                         | Session sampling rate in `[0, 1]`. `1` = all sessions, `0` = none.  | <code>1</code>     | 0.1.0 |
| **`url`**                 | <code>string</code>                                                         | The Faro collector endpoint URL.                                    |                    | 0.1.0 |
| **`user`**                | <code><a href="#usermetadata">UserMetadata</a></code>                       | Initial user metadata.                                              |                    | 0.1.0 |
| **`view`**                | <code><a href="#viewmetadata">ViewMetadata</a></code>                       | Initial view metadata.                                              |                    | 0.1.0 |


#### AppMetadata

| Prop              | Type                | Description                                                        | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`environment`** | <code>string</code> | The environment of the application (e.g. `production`, `staging`). | 0.1.0 |
| **`name`**        | <code>string</code> | The name of the application.                                       | 0.1.0 |
| **`namespace`**   | <code>string</code> | The namespace of the application.                                  | 0.1.0 |
| **`version`**     | <code>string</code> | The version of the application.                                    | 0.1.0 |


#### InstrumentationsOptions

Toggles for built-in automatic instrumentations.

Note: some toggles only apply on specific platforms.
The plugin silently ignores toggles that do not apply on the current platform.

| Prop                       | Type                 | Description                                                                                | Default            | Since |
| -------------------------- | -------------------- | ------------------------------------------------------------------------------------------ | ------------------ | ----- |
| **`anrTracking`**          | <code>boolean</code> | Detect Application Not Responding events on the main thread. Only available for Android.   | <code>false</code> | 0.1.0 |
| **`console`**              | <code>boolean</code> | Capture `console.warn` and `console.error` calls. Only available for Web.                  | <code>true</code>  | 0.1.0 |
| **`errors`**               | <code>boolean</code> | Capture uncaught errors and unhandled promise rejections. Only available for Web.          | <code>true</code>  | 0.1.0 |
| **`nativeCrashReporting`** | <code>boolean</code> | Capture native crashes (reported on the next session). Only available for Android and iOS. | <code>false</code> | 0.1.0 |
| **`performance`**          | <code>boolean</code> | Capture `fetch` and `XHR` performance entries. Only available for Web.                     | <code>true</code>  | 0.1.0 |
| **`view`**                 | <code>boolean</code> | Capture History API navigation as view changes. Only available for Web.                    | <code>true</code>  | 0.1.0 |
| **`webVitals`**            | <code>boolean</code> | Capture Core Web Vitals (LCP, FID, CLS, INP, TTFB). Only available for Web.                | <code>true</code>  | 0.1.0 |


#### UserMetadata

| Prop             | Type                                    | Description                                   | Since |
| ---------------- | --------------------------------------- | --------------------------------------------- | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | Additional key-value attributes for the user. | 0.1.0 |
| **`email`**      | <code>string</code>                     | The user's email address.                     | 0.1.0 |
| **`fullName`**   | <code>string</code>                     | The user's full name.                         | 0.1.0 |
| **`id`**         | <code>string</code>                     | The user's ID.                                | 0.1.0 |
| **`username`**   | <code>string</code>                     | The user's username.                          | 0.1.0 |


#### ViewMetadata

| Prop       | Type                | Description           | Since |
| ---------- | ------------------- | --------------------- | ----- |
| **`name`** | <code>string</code> | The name of the view. | 0.1.0 |


#### PushErrorOptions

| Prop              | Type                                    | Description                                           | Default            | Since |
| ----------------- | --------------------------------------- | ----------------------------------------------------- | ------------------ | ----- |
| **`context`**     | <code>{ [key: string]: string; }</code> | Additional context attached to the error.             |                    | 0.1.0 |
| **`fatal`**       | <code>boolean</code>                    | Mark the error as fatal (e.g. caused a crash).        | <code>false</code> | 0.1.0 |
| **`stackFrames`** | <code>StackFrame[]</code>               | Pre-parsed stack frames generated by `stacktrace.js`. |                    | 0.1.0 |
| **`type`**        | <code>string</code>                     | The error type (e.g. `'TypeError'`).                  |                    | 0.1.0 |
| **`value`**       | <code>string</code>                     | The error message.                                    |                    | 0.1.0 |


#### StackFrame

Subset of the stacktrace generated by `stacktrace.js`.

| Prop               | Type                | Since |
| ------------------ | ------------------- | ----- |
| **`columnNumber`** | <code>number</code> | 0.1.0 |
| **`fileName`**     | <code>string</code> | 0.1.0 |
| **`functionName`** | <code>string</code> | 0.1.0 |
| **`lineNumber`**   | <code>number</code> | 0.1.0 |


#### PushEventOptions

| Prop             | Type                                    | Description                                                                                      | Since |
| ---------------- | --------------------------------------- | ------------------------------------------------------------------------------------------------ | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | Key-value attributes attached to the event.                                                      | 0.1.0 |
| **`domain`**     | <code>string</code>                     | Logical grouping for the event. Defaults to the platform domain (`'web'`, `'android'`, `'ios'`). | 0.1.0 |
| **`name`**       | <code>string</code>                     | The name of the event.                                                                           | 0.1.0 |


#### PushLogOptions

| Prop          | Type                                          | Description                             | Default             | Since |
| ------------- | --------------------------------------------- | --------------------------------------- | ------------------- | ----- |
| **`context`** | <code>{ [key: string]: string; }</code>       | Additional context attached to the log. |                     | 0.1.0 |
| **`level`**   | <code><a href="#loglevel">LogLevel</a></code> | The log level.                          | <code>'info'</code> | 0.1.0 |
| **`message`** | <code>string</code>                           | The log message.                        |                     | 0.1.0 |


#### PushMeasurementOptions

| Prop          | Type                                    | Description                                     | Since |
| ------------- | --------------------------------------- | ----------------------------------------------- | ----- |
| **`context`** | <code>{ [key: string]: string; }</code> | Additional context attached to the measurement. | 0.1.0 |
| **`type`**    | <code>string</code>                     | The measurement type (e.g. `'custom_latency'`). | 0.1.0 |
| **`values`**  | <code>{ [key: string]: number; }</code> | Key-value numeric measurements.                 | 0.1.0 |


#### SetSessionOptions

| Prop             | Type                                    | Description                                               | Since |
| ---------------- | --------------------------------------- | --------------------------------------------------------- | ----- |
| **`attributes`** | <code>{ [key: string]: string; }</code> | The session attributes.                                   | 0.1.0 |
| **`id`**         | <code>string</code>                     | Optional session ID. A UUID v4 is generated when omitted. | 0.1.0 |


### Type Aliases


#### LogLevel

<code>'debug' | 'error' | 'info' | 'log' | 'trace' | 'warn'</code>


#### SetUserOptions

<code><a href="#usermetadata">UserMetadata</a></code>


#### SetViewOptions

<code><a href="#viewmetadata">ViewMetadata</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/grafana-faro/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/grafana-faro/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Grafana Labs or any of their affiliates or subsidiaries.
