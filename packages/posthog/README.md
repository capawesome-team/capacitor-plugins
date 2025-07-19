# @capawesome/capacitor-posthog

Unofficial Capacitor plugin for [PostHog](https://posthog.com/).[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-posthog posthog-js
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxCoreKtxVersion` version of `androidx.core:core-ktx` (default: `1.13.1`)
- `$posthogVersion` version of `com.posthog:posthog-android` (default: `3.10.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Posthog } from '@capawesome/capacitor-posthog';

const alias = async () => {
  await Posthog.alias({
    alias: 'new-distinct-id',
  });
};

const capture = async () => {
  await Posthog.capture({
    event: 'event',
    properties: {
      key: 'value',
    },
  });
};

const flush = async () => {
  await Posthog.flush();
};

const group = async () => {
  await Posthog.group({
    type: 'group',
    key: 'key',
    groupProperties: {
      key: 'value',
    },
  });
};

const identify = async () => {
  await Posthog.identify({
    distinctId: 'distinct-id',
    userProperties: {
      key: 'value',
    },
  });
};

const register = async () => {
  await Posthog.register({
    key: 'super-property',
    value: 'super-value',
  });
};

const reset = async () => {
  await Posthog.reset();
};

const screen = async () => {
  await Posthog.screen({
    screenTitle: 'screen',
    properties: {
      key: 'value',
    },
  });
};

const setup = async () => {
  await Posthog.setup({
    apiKey: 'YOUR_API_KEY',
    host: 'https://eu.i.posthog.com',
    });
};

const unregister = async () => {
  await Posthog.unregister({
    key: 'super-property',
  });
};
```

## API

<docgen-index>

* [`alias(...)`](#alias)
* [`capture(...)`](#capture)
* [`flush()`](#flush)
* [`getFeatureFlag(...)`](#getfeatureflag)
* [`getFeatureFlagPayload(...)`](#getfeatureflagpayload)
* [`group(...)`](#group)
* [`identify(...)`](#identify)
* [`isFeatureEnabled(...)`](#isfeatureenabled)
* [`register(...)`](#register)
* [`reloadFeatureFlags()`](#reloadfeatureflags)
* [`reset()`](#reset)
* [`screen(...)`](#screen)
* [`setup(...)`](#setup)
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


### flush()

```typescript
flush() => Promise<void>
```

Flush all events in the queue.

Only available on Android and iOS.

**Since:** 6.0.0

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

| Prop             | Type                                                         | Description                            | Since |
| ---------------- | ------------------------------------------------------------ | -------------------------------------- | ----- |
| **`event`**      | <code>string</code>                                          | The name of the event to capture.      | 6.0.0 |
| **`properties`** | <code><a href="#record">Record</a>&lt;string, any&gt;</code> | The properties to send with the event. | 6.0.0 |


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

| Prop                  | Type                                                         | Description                                  | Since |
| --------------------- | ------------------------------------------------------------ | -------------------------------------------- | ----- |
| **`type`**            | <code>string</code>                                          | The group type.                              | 6.0.0 |
| **`key`**             | <code>string</code>                                          | The group key.                               | 6.0.0 |
| **`groupProperties`** | <code><a href="#record">Record</a>&lt;string, any&gt;</code> | The properties to send with the group event. | 6.0.0 |


#### IdentifyOptions

| Prop                 | Type                                                         | Description                   | Since |
| -------------------- | ------------------------------------------------------------ | ----------------------------- | ----- |
| **`distinctId`**     | <code>string</code>                                          | The distinct ID of the user.  | 6.0.0 |
| **`userProperties`** | <code><a href="#record">Record</a>&lt;string, any&gt;</code> | The person properties to set. | 6.0.0 |


#### IsFeatureEnabledResult

| Prop          | Type                 | Description                                                                                         | Since |
| ------------- | -------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether the feature flag is enabled. If the feature flag does not exist, the value will be `false`. | 7.0.0 |


#### IsFeatureEnabledOptions

| Prop      | Type                | Description                  | Since |
| --------- | ------------------- | ---------------------------- | ----- |
| **`key`** | <code>string</code> | The key of the feature flag. | 7.0.0 |


#### RegisterOptions

| Prop        | Type                | Description                      | Since |
| ----------- | ------------------- | -------------------------------- | ----- |
| **`key`**   | <code>string</code> | The name of the super property.  | 6.0.0 |
| **`value`** | <code>any</code>    | The value of the super property. | 6.0.0 |


#### ScreenOptions

| Prop              | Type                                                         | Description                                   | Since |
| ----------------- | ------------------------------------------------------------ | --------------------------------------------- | ----- |
| **`screenTitle`** | <code>string</code>                                          | The name of the screen.                       | 6.0.0 |
| **`properties`**  | <code><a href="#record">Record</a>&lt;string, any&gt;</code> | The properties to send with the screen event. | 6.0.0 |


#### SetupOptions

| Prop         | Type                | Description                          | Default                                 | Since |
| ------------ | ------------------- | ------------------------------------ | --------------------------------------- | ----- |
| **`apiKey`** | <code>string</code> | The API key of your PostHog project. |                                         | 6.0.0 |
| **`host`**   | <code>string</code> | The host of your PostHog instance.   | <code>'https://us.i.posthog.com'</code> | 6.0.0 |


#### UnregisterOptions

| Prop      | Type                | Description                               | Since |
| --------- | ------------------- | ----------------------------------------- | ----- |
| **`key`** | <code>string</code> | The name of the super property to remove. | 6.0.0 |


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### JsonType

<code>string | number | boolean | null | { [key: string]: <a href="#jsontype">JsonType</a>; } | JsonType[]</code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/posthog/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/posthog/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by PostHog, Inc. or any of their affiliates or subsidiaries.
