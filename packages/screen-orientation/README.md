# @capawesome/capacitor-screen-orientation

Capacitor plugin to lock/unlock the screen orientation.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-deploy-real-time-app-updates.png?t=1" />
  </a>
</div>

## Installation

```bash
npm install @capawesome/capacitor-screen-orientation
npx cap sync
```

### iOS

On iOS you must add the following to your app's `AppDelegate.swift`:

```diff
+ import CapawesomeCapacitorScreenOrientation

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

+ func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
+   return ScreenOrientation.getSupportedInterfaceOrientations()
+ }
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { ScreenOrientation, OrientationType } from '@capawesome/capacitor-screen-orientation';

const lock = async () => {
  await ScreenOrientation.lock({ type: OrientationType.LANDSCAPE });
};

const unlock = async () => {
  await ScreenOrientation.unlock();
};

const getCurrentOrientation = async () => {
  const result = await ScreenOrientation.getCurrentOrientation();
  return result.type;
};
```

## API

<docgen-index>

* [`lock(...)`](#lock)
* [`unlock()`](#unlock)
* [`getCurrentOrientation()`](#getcurrentorientation)
* [`addListener('screenOrientationChange', ...)`](#addlistenerscreenorientationchange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### lock(...)

```typescript
lock(options: LockOptions) => Promise<void>
```

Locks the device orientation.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#lockoptions">LockOptions</a></code> |

--------------------


### unlock()

```typescript
unlock() => Promise<void>
```

Unlocks the device orientation.

--------------------


### getCurrentOrientation()

```typescript
getCurrentOrientation() => Promise<GetCurrentOrientationResult>
```

Gets the current device orientation type.

**Returns:** <code>Promise&lt;<a href="#getcurrentorientationresult">GetCurrentOrientationResult</a>&gt;</code>

--------------------


### addListener('screenOrientationChange', ...)

```typescript
addListener(eventName: 'screenOrientationChange', listenerFunc: ScreenOrientationChangeListener) => Promise<PluginListenerHandle>
```

Listen for screen orientation changes.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'screenOrientationChange'</code>                                                      |
| **`listenerFunc`** | <code><a href="#screenorientationchangelistener">ScreenOrientationChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

--------------------


### Interfaces


#### LockOptions

| Prop       | Type                                                        | Description                |
| ---------- | ----------------------------------------------------------- | -------------------------- |
| **`type`** | <code><a href="#orientationtype">OrientationType</a></code> | The orientation lock type. |


#### GetCurrentOrientationResult

| Prop       | Type                                                        | Description                   |
| ---------- | ----------------------------------------------------------- | ----------------------------- |
| **`type`** | <code><a href="#orientationtype">OrientationType</a></code> | The current orientation type. |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ScreenOrientationChange

| Prop       | Type                                                        | Description                   |
| ---------- | ----------------------------------------------------------- | ----------------------------- |
| **`type`** | <code><a href="#orientationtype">OrientationType</a></code> | The current orientation type. |


### Type Aliases


#### ScreenOrientationChangeListener

Callback to receive the screen orientation change notifications.

<code>(change: <a href="#screenorientationchange">ScreenOrientationChange</a>): void</code>


### Enums


#### OrientationType

| Members                   | Value                              | Description                                                         |
| ------------------------- | ---------------------------------- | ------------------------------------------------------------------- |
| **`LANDSCAPE`**           | <code>'landscape'</code>           | The orientation is either landscape-primary or landscape-secondary. |
| **`LANDSCAPE_PRIMARY`**   | <code>'landscape-primary'</code>   | The orientation is in the primary landscape mode.                   |
| **`LANDSCAPE_SECONDARY`** | <code>'landscape-secondary'</code> | The orientation is in the secondary landscape mode.                 |
| **`PORTRAIT`**            | <code>'portrait'</code>            | The orientation is either portrait-primary or portrait-secondary.   |
| **`PORTRAIT_PRIMARY`**    | <code>'portrait-primary'</code>    | The orientation is in the primary portrait mode.                    |
| **`PORTRAIT_SECONDARY`**  | <code>'portrait-secondary'</code>  | The orientation is in the secondary portrait mode.                  |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-orientation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-orientation/LICENSE).

## Credits

This plugin is based on the [Capacitor Screen Orientation](https://github.com/capawesome-team/capacitor-screen-orientation) plugin.
Thanks to everyone who contributed to the project!
