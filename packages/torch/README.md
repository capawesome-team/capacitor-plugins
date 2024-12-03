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

* [`enable()`](#enable)
* [`disable()`](#disable)
* [`isAvailable()`](#isavailable)
* [`isEnabled()`](#isenabled)
* [`toggle()`](#toggle)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### enable()

```typescript
enable() => Promise<void>
```

Enable the torch.

Available on Android (SDK 23+), iOS and web.

**Since:** 6.0.0

--------------------


### disable()

```typescript
disable() => Promise<void>
```

Disable the torch.

Available on Android (SDK 23+), iOS and web.

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the torch is available.

Available on Android, iOS and web.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isEnabled()

```typescript
isEnabled() => Promise<IsEnabledResult>
```

Check if the torch is enabled.

Available on Android, iOS and web.

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### toggle()

```typescript
toggle() => Promise<void>
```

Toggle the torch.

Available on Android (SDK 23+), iOS and web.

**Since:** 6.0.0

--------------------


### Interfaces


#### IsAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the torch is available or not. | 6.0.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the torch is enabled or not. | 6.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/LICENSE).
