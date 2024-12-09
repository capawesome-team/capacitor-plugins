# @capawesome/capacitor-torch

Capacitor plugin for switching the flashlight on and off.

## Installation

```bash
npm install @capawesome/capacitor-torch
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<!-- To get access to the flashlight. -->
<uses-permission android:name="android.permission.FLASHLIGHT"/>
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCameraCoreVersion` version of `androidx.camera:camera-core` (default: `1.1.0`)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Torch } from '@capawesome/capacitor-torch';

const enable = async () => {
  await Torch.enable();
};

const disable = async () => {
  await Torch.disable();
};

const isAvailable = async () => {
    const result = await Torch.isAvailable();
    return result.available;
};

const isEnabled = async () => {
    const result = await Torch.isEnabled();
    return result.enabled;
};

const toggle = async () => {
  await Torch.toggle();
};
```

## API

<docgen-index>

* [`enable(...)`](#enable)
* [`disable(...)`](#disable)
* [`isAvailable()`](#isavailable)
* [`isEnabled(...)`](#isenabled)
* [`toggle(...)`](#toggle)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### enable(...)

```typescript
enable(options?: EnableOptions | undefined) => Promise<void>
```

Enable the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#enableoptions">EnableOptions</a></code> |

**Since:** 6.0.0

--------------------


### disable(...)

```typescript
disable(options?: DisableOptions | undefined) => Promise<void>
```

Disable the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#disableoptions">DisableOptions</a></code> |

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the torch is available.

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isEnabled(...)

```typescript
isEnabled(options?: IsEnabledOptions | undefined) => Promise<IsEnabledResult>
```

Check if the torch is enabled.

Only available on Android, iOS and Web.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#isenabledoptions">IsEnabledOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### toggle(...)

```typescript
toggle(options: ToggleOptions) => Promise<void>
```

Toggle the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#toggleoptions">ToggleOptions</a></code> |

**Since:** 6.0.0

--------------------


### Interfaces


#### EnableOptions

| Prop         | Type                     | Description                                                                                                                                                                                             | Since |
| ------------ | ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to enable the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### DisableOptions

| Prop         | Type                     | Description                                                                                                                                                                                              | Since |
| ------------ | ------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to disable the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the torch is available or not. | 6.0.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the torch is enabled or not. | 6.0.0 |


#### IsEnabledOptions

| Prop         | Type                     | Description                                                                                                                                                                                                          | Since |
| ------------ | ------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to check if the torch is enabled on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### ToggleOptions

| Prop         | Type                     | Description                                                                                                                                                                                             | Since |
| ------------ | ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to toggle the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/LICENSE).
