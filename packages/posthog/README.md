# Capacitor PostHog Plugin

Unofficial Capacitor plugin for [PostHog](https://posthog.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The PostHog plugin is typically used to understand how users interact with an app, for example:

- **Product analytics**: Capture custom events and screen views to analyze how users move through your app.
- **User identification**: Identify users with a distinct ID, assign aliases, and associate their events with groups.
- **Feature flags**: Roll out features gradually by checking feature flags and their payloads at runtime.
- **Session replay**: Record user sessions to understand usage patterns and debug issues.
- **Error tracking**: Capture exceptions manually or automatically to monitor the stability of your app.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-posthog` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-posthog posthog-js
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxCoreKtxVersion` version of `androidx.core:core-ktx` (default: `1.13.1`)
- `$posthogVersion` version of `com.posthog:posthog-android` (default: `3.27.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop                                    | Type                                                                  | Description                                                                               | Default                                 | Since |
| --------------------------------------- | --------------------------------------------------------------------- | ----------------------------------------------------------------------------------------- | --------------------------------------- | ----- |
| **`apiKey`**                            | <code>string</code>                                                   | The API key of your PostHog project.                                                      |                                         | 7.1.0 |
| **`apiHost`**                           | <code>string</code>                                                   | The API host of your PostHog instance or reverse proxy.                                   | <code>'https://us.i.posthog.com'</code> | 8.3.0 |
| **`host`**                              | <code>string</code>                                                   | The API host of your PostHog instance. Deprecated alias for `apiHost`.                    | <code>'https://us.i.posthog.com'</code> | 7.1.0 |
| **`uiHost`**                            | <code>string</code>                                                   | The PostHog UI host used when `apiHost` points to a reverse proxy. Only available on Web. |                                         | 8.3.0 |
| **`enableSessionReplay`**               | <code>boolean</code>                                                  | Whether to enable session recording automatically.                                        | <code>false</code>                      | 7.3.0 |
| **`sessionReplayConfig`**               | <code><a href="#sessionreplayoptions">SessionReplayOptions</a></code> | Session recording configuration options.                                                  |                                         | 7.3.0 |
| **`captureApplicationLifecycleEvents`** | <code>boolean</code>                                                  | Whether to capture application lifecycle events.                                          | <code>true</code>                       | 8.3.0 |
| **`autoCaptureExceptions`**             | <code>boolean</code>                                                  | Whether to automatically capture unhandled exceptions.                                    | <code>false</code>                      | 8.5.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "Posthog": {
      "apiKey": 'phc_g8wMenebiIQ1pYd5v9Vy7oakn6MczVKIsNG5ZHCspdy',
      "apiHost": 'https://eu.i.posthog.com',
      "host": 'https://eu.i.posthog.com',
      "uiHost": 'https://eu.posthog.com',
      "enableSessionReplay": undefined,
      "sessionReplayConfig": undefined,
      "captureApplicationLifecycleEvents": undefined,
      "autoCaptureExceptions": undefined
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-posthog" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    Posthog: {
      apiKey: 'phc_g8wMenebiIQ1pYd5v9Vy7oakn6MczVKIsNG5ZHCspdy',
      apiHost: 'https://eu.i.posthog.com',
      host: 'https://eu.i.posthog.com',
      uiHost: 'https://eu.posthog.com',
      enableSessionReplay: undefined,
      sessionReplayConfig: undefined,
      captureApplicationLifecycleEvents: undefined,
      autoCaptureExceptions: undefined,
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

The following examples show how to set up the SDK, capture events, track screen views, identify users, associate users with a group, manage super properties, reset the user, and flush queued events.

### Set up the SDK

Call `setup(...)` before any other method. Alternatively, on Android and iOS, you can configure the plugin in your Capacitor configuration file (see [Configuration](#configuration)); in that case, you must not call this method:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const setup = async () => {
  await Posthog.setup({
    apiKey: 'YOUR_API_KEY',
    apiHost: 'https://eu.i.posthog.com',
  });
};
```

### Capture events

Capture a custom event with optional properties:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const capture = async () => {
  await Posthog.capture({
    event: 'event',
    properties: {
      key: 'value',
    },
  });
};
```

### Track screen views

Send a screen event with the title of the screen. Only available on Android and iOS:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const screen = async () => {
  await Posthog.screen({
    screenTitle: 'screen',
    properties: {
      key: 'value',
    },
  });
};
```

### Identify users

Identify the current user with a distinct ID and set person properties. You can also assign another distinct ID to the current user using `alias(...)`:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const identify = async () => {
  await Posthog.identify({
    distinctId: 'distinct-id',
    userProperties: {
      key: 'value',
    },
  });
};

const alias = async () => {
  await Posthog.alias({
    alias: 'new-distinct-id',
  });
};
```

### Associate users with a group

Associate the events of the current user with a group, for example a company or a team:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const group = async () => {
  await Posthog.group({
    type: 'group',
    key: 'key',
    groupProperties: {
      key: 'value',
    },
  });
};
```

### Manage super properties

Register a super property that is sent with every event, and unregister it when it is no longer needed:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const register = async () => {
  await Posthog.register({
    key: 'super-property',
    value: 'super-value',
  });
};

const unregister = async () => {
  await Posthog.unregister({
    key: 'super-property',
  });
};
```

### Reset the user

Reset the current user's ID and anonymous ID, for example after a logout:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const reset = async () => {
  await Posthog.reset();
};
```

### Flush queued events

Manually flush all events in the queue. Only available on Android and iOS:

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const flush = async () => {
  await Posthog.flush();
};
```

## API

<docgen-index>

* [`alias(...)`](#alias)
* [`capture(...)`](#capture)
* [`captureException(...)`](#captureexception)
* [`flush()`](#flush)
* [`getDistinctId()`](#getdistinctid)
* [`getFeatureFlag(...)`](#getfeatureflag)
* [`getFeatureFlagPayload(...)`](#getfeatureflagpayload)
* [`group(...)`](#group)
* [`identify(...)`](#identify)
* [`isFeatureEnabled(...)`](#isfeatureenabled)
* [`isOptOut()`](#isoptout)
* [`optIn()`](#optin)
* [`optOut()`](#optout)
* [`register(...)`](#register)
* [`reloadFeatureFlags()`](#reloadfeatureflags)
* [`reset()`](#reset)
* [`screen(...)`](#screen)
* [`setup(...)`](#setup)
* [`startSessionRecording()`](#startsessionrecording)
* [`stopSessionRecording()`](#stopsessionrecording)
* [`unregister(...)`](#unregister)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### alias(...)

```typescript
alias(options: AliasOptions) => Promise<void>
```

Assign another distinct ID to the current user.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#aliasoptions">AliasOptions</a></code> |

**Since:** 6.0.0

--------------------


### capture(...)

```typescript
capture(options: CaptureOptions) => Promise<void>
```

Capture an event.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#captureoptions">CaptureOptions</a></code> |

**Since:** 6.0.0

--------------------


### captureException(...)

```typescript
captureException(options: CaptureExceptionOptions) => Promise<void>
```

Capture an exception.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#captureexceptionoptions">CaptureExceptionOptions</a></code> |

**Since:** 8.5.0

--------------------


### flush()

```typescript
flush() => Promise<void>
```

Flush all events in the queue.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### getDistinctId()

```typescript
getDistinctId() => Promise<GetDistinctIdResult>
```

Get the current distinct ID.

**Returns:** <code>Promise&lt;<a href="#getdistinctidresult">GetDistinctIdResult</a>&gt;</code>

**Since:** 8.2.0

--------------------


### getFeatureFlag(...)

```typescript
getFeatureFlag(options: GetFeatureFlagOptions) => Promise<GetFeatureFlagResult>
```

Get the value of a feature flag.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getfeatureflagoptions">GetFeatureFlagOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getfeatureflagresult">GetFeatureFlagResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### getFeatureFlagPayload(...)

```typescript
getFeatureFlagPayload(options: GetFeatureFlagPayloadOptions) => Promise<GetFeatureFlagPayloadResult>
```

Get the payload of a feature flag.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getfeatureflagpayloadoptions">GetFeatureFlagPayloadOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getfeatureflagpayloadresult">GetFeatureFlagPayloadResult</a>&gt;</code>

**Since:** 7.1.0

--------------------


### group(...)

```typescript
group(options: GroupOptions) => Promise<void>
```

Associate the events for that user with a group.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#groupoptions">GroupOptions</a></code> |

**Since:** 6.0.0

--------------------


### identify(...)

```typescript
identify(options: IdentifyOptions) => Promise<void>
```

Identify the current user.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#identifyoptions">IdentifyOptions</a></code> |

**Since:** 6.0.0

--------------------


### isFeatureEnabled(...)

```typescript
isFeatureEnabled(options: IsFeatureEnabledOptions) => Promise<IsFeatureEnabledResult>
```

Check if a feature flag is enabled.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#isfeatureenabledoptions">IsFeatureEnabledOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isfeatureenabledresult">IsFeatureEnabledResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### isOptOut()

```typescript
isOptOut() => Promise<IsOptOutResult>
```

Check if the user has opted out of capturing.

**Returns:** <code>Promise&lt;<a href="#isoptoutresult">IsOptOutResult</a>&gt;</code>

**Since:** 8.1.0

--------------------


### optIn()

```typescript
optIn() => Promise<void>
```

Opt in to event capturing.

**Since:** 8.1.0

--------------------


### optOut()

```typescript
optOut() => Promise<void>
```

Opt out of event capturing.

On Web with `cookielessMode: 'on_reject'`: switches to cookieless anonymous tracking.
On iOS/Android: stops all event capturing entirely.

**Since:** 8.1.0

--------------------


### register(...)

```typescript
register(options: RegisterOptions) => Promise<void>
```

Register a new super property. This property will be sent with every event.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#registeroptions">RegisterOptions</a></code> |

**Since:** 6.0.0

--------------------


### reloadFeatureFlags()

```typescript
reloadFeatureFlags() => Promise<void>
```

Reload the feature flags.

**Since:** 7.0.0

--------------------


### reset()

```typescript
reset() => Promise<void>
```

Reset the current user's ID and anonymous ID.

**Since:** 6.0.0

--------------------


### screen(...)

```typescript
screen(options: ScreenOptions) => Promise<void>
```

Send a screen event.

Only available on Android and iOS.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#screenoptions">ScreenOptions</a></code> |

**Since:** 6.0.0

--------------------


### setup(...)

```typescript
setup(options: SetupOptions) => Promise<void>
```

Setup the PostHog SDK with the provided options.

**Attention**: This method should be called before any other method.
Alternatively, on Android and iOS, you can configure this plugin in
your Capacitor Configuration file. In this case, you must not call this method.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#setupoptions">SetupOptions</a></code> |

**Since:** 6.0.0

--------------------


### startSessionRecording()

```typescript
startSessionRecording() => Promise<void>
```

Start session recording.

**Since:** 7.3.0

--------------------


### stopSessionRecording()

```typescript
stopSessionRecording() => Promise<void>
```

Stop session recording.

**Since:** 7.3.0

--------------------


### unregister(...)

```typescript
unregister(options: UnregisterOptions) => Promise<void>
```

Remove a super property.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#unregisteroptions">UnregisterOptions</a></code> |

**Since:** 6.0.0

--------------------


### Interfaces


#### AliasOptions

| Prop        | Type                | Description                                        | Since |
| ----------- | ------------------- | -------------------------------------------------- | ----- |
| **`alias`** | <code>string</code> | The new distinct ID to assign to the current user. | 6.0.0 |


#### CaptureOptions

| Prop             | Type                                   | Description                            | Since |
| ---------------- | -------------------------------------- | -------------------------------------- | ----- |
| **`event`**      | <code>string</code>                    | The name of the event to capture.      | 6.0.0 |
| **`properties`** | <code>Record&lt;string, any&gt;</code> | The properties to send with the event. | 6.0.0 |


#### CaptureExceptionOptions

| Prop             | Type                                   | Description                                                                                                                | Default              | Since |
| ---------------- | -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | -------------------- | ----- |
| **`message`**    | <code>string</code>                    | The message of the exception.                                                                                              |                      | 8.5.0 |
| **`name`**       | <code>string</code>                    | The name of the exception. Used as the exception type for grouping.                                                        | <code>'Error'</code> | 8.5.0 |
| **`stacktrace`** | <code>StackFrame[]</code>              | The stack trace of the exception. Can be generated from an `Error` using [`stacktrace.js`](https://www.stacktracejs.com/). |                      | 8.5.0 |
| **`properties`** | <code>Record&lt;string, any&gt;</code> | The properties to send with the exception.                                                                                 |                      | 8.5.0 |


#### StackFrame

| Prop               | Type                | Description                        | Since |
| ------------------ | ------------------- | ---------------------------------- | ----- |
| **`functionName`** | <code>string</code> | The name of the function.          | 8.5.0 |
| **`fileName`**     | <code>string</code> | The name of the file.              | 8.5.0 |
| **`lineNumber`**   | <code>number</code> | The line number within the file.   | 8.5.0 |
| **`columnNumber`** | <code>number</code> | The column number within the file. | 8.5.0 |


#### GetDistinctIdResult

| Prop             | Type                | Description              | Since |
| ---------------- | ------------------- | ------------------------ | ----- |
| **`distinctId`** | <code>string</code> | The current distinct ID. | 8.2.0 |


#### GetFeatureFlagResult

| Prop        | Type                                   | Description                                                                                  | Since |
| ----------- | -------------------------------------- | -------------------------------------------------------------------------------------------- | ----- |
| **`value`** | <code>string \| boolean \| null</code> | The value of the feature flag. If the feature flag does not exist, the value will be `null`. | 7.0.0 |


#### GetFeatureFlagOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the feature flag. | 7.0.0 |


#### GetFeatureFlagPayloadResult

| Prop        | Type                                          | Description                            | Since |
| ----------- | --------------------------------------------- | -------------------------------------- | ----- |
| **`value`** | <code><a href="#jsontype">JsonType</a></code> | The value of the feature flag payload. | 7.1.0 |


#### GetFeatureFlagPayloadOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the feature flag. | 7.1.0 |


#### GroupOptions

| Prop                  | Type                                   | Description                                  | Since |
| --------------------- | -------------------------------------- | -------------------------------------------- | ----- |
| **`type`**            | <code>string</code>                    | The group type.                              | 6.0.0 |
| **`key`**             | <code>string</code>                    | The group key.                               | 6.0.0 |
| **`groupProperties`** | <code>Record&lt;string, any&gt;</code> | The properties to send with the group event. | 6.0.0 |


#### IdentifyOptions

| Prop                 | Type                                   | Description                   | Since |
| -------------------- | -------------------------------------- | ----------------------------- | ----- |
| **`distinctId`**     | <code>string</code>                    | The distinct ID of the user.  | 6.0.0 |
| **`userProperties`** | <code>Record&lt;string, any&gt;</code> | The person properties to set. | 6.0.0 |


#### IsFeatureEnabledResult

| Prop          | Type                 | Description                                                                                         | Since |
| ------------- | -------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether the feature flag is enabled. If the feature flag does not exist, the value will be `false`. | 7.0.0 |


#### IsFeatureEnabledOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the feature flag. | 7.0.0 |


#### IsOptOutResult

| Prop           | Type                 | Description                                  | Since |
| -------------- | -------------------- | -------------------------------------------- | ----- |
| **`optedOut`** | <code>boolean</code> | Whether the user has opted out of capturing. | 8.1.0 |


#### RegisterOptions

| Prop        | Type                | Description                      | Since |
| ----------- | ------------------- | -------------------------------- | ----- |
| **`key`**   | <code>string</code> | The name of the super property.  | 6.0.0 |
| **`value`** | <code>any</code>    | The value of the super property. | 6.0.0 |


#### ScreenOptions

| Prop              | Type                                   | Description                                   | Since |
| ----------------- | -------------------------------------- | --------------------------------------------- | ----- |
| **`screenTitle`** | <code>string</code>                    | The name of the screen.                       | 6.0.0 |
| **`properties`**  | <code>Record&lt;string, any&gt;</code> | The properties to send with the screen event. | 6.0.0 |


#### SetupOptions

| Prop                                    | Type                                                                  | Description                                                                                                                                                                                                                                                                                  | Default                                 | Since |
| --------------------------------------- | --------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------- | ----- |
| **`apiKey`**                            | <code>string</code>                                                   | The API key of your PostHog project.                                                                                                                                                                                                                                                         |                                         | 6.0.0 |
| **`apiHost`**                           | <code>string</code>                                                   | The API host of your PostHog instance or reverse proxy. If both `apiHost` and `host` are provided, `apiHost` takes precedence.                                                                                                                                                               | <code>'https://us.i.posthog.com'</code> | 8.3.0 |
| **`host`**                              | <code>string</code>                                                   | The API host of your PostHog instance. Deprecated alias for `apiHost`.                                                                                                                                                                                                                       | <code>'https://us.i.posthog.com'</code> | 6.0.0 |
| **`uiHost`**                            | <code>string</code>                                                   | The PostHog UI host used when `apiHost` points to a reverse proxy. Only available on Web.                                                                                                                                                                                                    |                                         | 8.3.0 |
| **`cookielessMode`**                    | <code>'always' \| 'on_reject'</code>                                  | Cookieless tracking mode. - `'always'`: Always use cookieless tracking with server-side anonymous hash. - `'on_reject'`: Normal tracking until `optOut()` is called, then switches to cookieless. Only available on Web. Requires cookieless mode to be enabled in PostHog project settings. |                                         | 8.1.0 |
| **`enableSessionReplay`**               | <code>boolean</code>                                                  | Whether to enable session recording automatically.                                                                                                                                                                                                                                           | <code>false</code>                      | 7.3.0 |
| **`optOut`**                            | <code>boolean</code>                                                  | Whether to opt out of capturing by default. User must call `optIn()` to enable capturing.                                                                                                                                                                                                    | <code>false</code>                      | 8.1.0 |
| **`sessionReplayConfig`**               | <code><a href="#sessionreplayoptions">SessionReplayOptions</a></code> | Session replay configuration options.                                                                                                                                                                                                                                                        |                                         | 7.3.0 |
| **`captureApplicationLifecycleEvents`** | <code>boolean</code>                                                  | Whether to capture application lifecycle events. Only available on iOS and Android.                                                                                                                                                                                                          | <code>true</code>                       | 8.3.0 |
| **`autoCaptureExceptions`**             | <code>boolean</code>                                                  | Whether to automatically capture unhandled exceptions.                                                                                                                                                                                                                                       | <code>false</code>                      | 8.5.0 |


#### SessionReplayOptions

| Prop                          | Type                 | Description                                                                                     | Default            | Since |
| ----------------------------- | -------------------- | ----------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`screenshotMode`**          | <code>boolean</code> | Enable screenshot mode for session recordings. WARNING: This may capture sensitive information. | <code>false</code> | 7.3.0 |
| **`maskAllTextInputs`**       | <code>boolean</code> | Mask all text input fields in session recordings.                                               | <code>true</code>  | 7.3.0 |
| **`maskAllImages`**           | <code>boolean</code> | Mask all images in session recordings.                                                          | <code>true</code>  | 7.3.0 |
| **`maskAllSandboxedViews`**   | <code>boolean</code> | Mask all sandboxed system views (iOS-specific).                                                 | <code>true</code>  | 7.3.0 |
| **`captureNetworkTelemetry`** | <code>boolean</code> | Capture network telemetry in session recordings.                                                | <code>false</code> | 7.3.0 |
| **`debouncerDelay`**          | <code>number</code>  | Debounce delay for session recording snapshots (in seconds).                                    | <code>1.0</code>   | 7.3.0 |


#### UnregisterOptions

| Prop      | Type                | Description                               | Since |
| --------- | ------------------- | ----------------------------------------- | ----- |
| **`key`** | <code>string</code> | The name of the super property to remove. | 6.0.0 |


### Type Aliases


#### JsonType

<code>string | number | boolean | null | { [key: string]: <a href="#jsontype">JsonType</a>; } | JsonType[]</code>

</docgen-api>

## Advanced

### Reverse Proxy

For PostHog managed reverse proxy, set `apiHost` to your proxy URL and `uiHost` to your PostHog app host (`https://us.posthog.com` or `https://eu.posthog.com`). `host` remains supported as a deprecated alias for `apiHost`. `uiHost` is only available on Web.

## FAQ

### Do I have to call the `setup` method before using the plugin?

Yes, the `setup(...)` method should be called before any other method. Alternatively, on Android and iOS, you can configure the plugin in your Capacitor configuration file (see [Configuration](#configuration)). In that case, you must not call the `setup(...)` method.

### What is the difference between the `apiHost` and `host` options?

The `host` option is a deprecated alias for `apiHost`. Both point to the API host of your PostHog instance or reverse proxy. If both options are provided, `apiHost` takes precedence, so new projects should only use `apiHost`.

### How do I use the plugin with a reverse proxy?

Set the `apiHost` option to your proxy URL and the `uiHost` option to your PostHog app host, such as `https://us.posthog.com` or `https://eu.posthog.com`. Note that `uiHost` is only available on Web. See the [Reverse Proxy](#reverse-proxy) section for more details.

### How do I enable session replay?

Set the `enableSessionReplay` option to `true` in the `setup(...)` method or in your Capacitor configuration file, and fine-tune the recording behavior with the `sessionReplayConfig` option. You can also start and stop recording manually using the `startSessionRecording()` and `stopSessionRecording()` methods.

### How can users opt out of tracking?

Call the `optOut()` method to stop event capturing and the `optIn()` method to re-enable it. You can check the current state with `isOptOut()` or opt users out by default with the `optOut` setup option. On Web with `cookielessMode: 'on_reject'`, opting out switches to cookieless anonymous tracking instead of stopping capturing entirely.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Analytics](https://capawesome.io/docs/sdks/capacitor/firebase/analytics/): Unofficial Capacitor plugin for Firebase Analytics.
- [Grafana Faro](https://capawesome.io/docs/sdks/capacitor/grafana-faro/): Unofficial Capacitor plugin for Grafana Faro to monitor your app.
- [Formbricks](https://capawesome.io/docs/sdks/capacitor/formbricks/): Unofficial Capacitor plugin for Formbricks to run in-app surveys.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/posthog/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/posthog/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by PostHog, Inc. or any of their affiliates or subsidiaries.
