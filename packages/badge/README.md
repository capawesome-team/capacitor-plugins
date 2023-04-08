# @capawesome/capacitor-badge

Capacitor plugin to access and update the badge number of the app icon.

## Installation

```bash
npm install @capawesome/capacitor-badge
npx cap sync
```

### Android Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$shortcutBadgerVersion` version of `me.leolin:ShortcutBadger` (default: `1.1.22`)

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

These configuration values are available:

| Prop            | Type                 | Description                                                                                                               | Default            |
| --------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------- | ------------------ |
| **`persist`**   | <code>boolean</code> | Configure whether the plugin should restore the counter after a reboot or app restart. Only available on Android and iOS. | <code>true</code>  |
| **`autoClear`** | <code>boolean</code> | Configure whether the plugin should reset the counter after resuming the application. Only available on Android and iOS.  | <code>false</code> |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "Badge": {
      "persist": true,
      "autoClear": false
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-badge" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    Badge: {
      persist: true,
      autoClear: false,
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { Badge } from '@capawesome/capacitor-badge';

const get = async () => {
  const result = await Badge.get();
  return result.count;
};

const set = async (count: number) => {
  await Badge.set({ count });
};

const increase = async () => {
  await Badge.increase();
};

const decrease = async () => {
  await Badge.decrease();
};

const clear = async () => {
  await Badge.clear();
};

const isSupported = async () => {
  const result = await Badge.isSupported();
  return result.isSupported;
};

const checkPermissions = async () => {
  const result = await Badge.checkPermissions();
};

const requestPermissions = async () => {
  const result = await Badge.requestPermissions();
};
```

## API

<docgen-index>

* [`get()`](#get)
* [`set(...)`](#set)
* [`increase()`](#increase)
* [`decrease()`](#decrease)
* [`clear()`](#clear)
* [`isSupported()`](#issupported)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### get()

```typescript
get() => Promise<GetBadgeResult>
```

Get the badge count.
The badge count won't be lost after a reboot or app restart.

Default: `0`.

**Returns:** <code>Promise&lt;<a href="#getbadgeresult">GetBadgeResult</a>&gt;</code>

--------------------


### set(...)

```typescript
set(options: SetBadgeOptions) => Promise<void>
```

Set the badge count.

| Param         | Type                                                        |
| ------------- | ----------------------------------------------------------- |
| **`options`** | <code><a href="#setbadgeoptions">SetBadgeOptions</a></code> |

--------------------


### increase()

```typescript
increase() => Promise<void>
```

Increase the badge count.

--------------------


### decrease()

```typescript
decrease() => Promise<void>
```

Decrease the badge count.

--------------------


### clear()

```typescript
clear() => Promise<void>
```

Clear the badge count.

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Check if the badge count is supported.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permission to display badge.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to display badge.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### Interfaces


#### GetBadgeResult

| Prop        | Type                |
| ----------- | ------------------- |
| **`count`** | <code>number</code> |


#### SetBadgeOptions

| Prop        | Type                |
| ----------- | ------------------- |
| **`count`** | <code>number</code> |


#### IsSupportedResult

| Prop              | Type                 |
| ----------------- | -------------------- |
| **`isSupported`** | <code>boolean</code> |


#### PermissionStatus

| Prop          | Type                                                        | Description                               |
| ------------- | ----------------------------------------------------------- | ----------------------------------------- |
| **`display`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state of displaying the badge. |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>

</docgen-api>

## Quirks

On **Android** not all launchers support badges. This plugin uses [ShortcutBadger](https://github.com/leolin310148/ShortcutBadger). All supported launchers are listed [there](https://github.com/leolin310148/ShortcutBadger#supported-launchers).

On **Web**, the app must run as an installed PWA (in the taskbar or dock).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/badge/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/badge/LICENSE).

## Credits

This plugin is based on the [Capacitor Badge](https://github.com/capawesome-team/capacitor-badge) plugin.
Thanks to everyone who contributed to the project!
