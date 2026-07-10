# Capacitor Screen Orientation Plugin

Capacitor plugin to lock/unlock the screen orientation.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Screen Orientation plugin is one of the most complete orientation control solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🔒 **Orientation locking**: Lock screen to specific orientations.
- 🔓 **Orientation unlocking**: Unlock and restore automatic orientation.
- 📱 **Multiple orientations**: Support for portrait, landscape, and specific orientations.
- 🔄 **Orientation detection**: Get current screen orientation.
- 📢 **Event listeners**: Listen to orientation change events.
- 📐 **Fine-grained control**: Primary and secondary orientation modes.
- 🍎 **iPad support**: Special configuration for iPad orientation locking.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Screen Orientation plugin is typically used whenever certain screens of an app only work well in a specific orientation, for example:

- **Video players**: Lock the screen to landscape for fullscreen video playback.
- **Games**: Keep the game in a fixed orientation regardless of how the device is held.
- **Camera and scanner screens**: Lock the screen to portrait while capturing photos or scanning codes.
- **Forms and reading views**: Prevent accidental rotation while the user is typing or reading.
- **Responsive layouts**: React to orientation changes with the `screenOrientationChange` event to adapt your UI.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |
| 6.x.x          | 6.x.x             | Deprecated     |
| 5.x.x          | 5.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-screen-orientation` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-screen-orientation
npx cap sync
```

### iOS

#### General

On iOS you must add the following to your app's `AppDelegate.swift`:

```diff
+ import ScreenOrientationPlugin

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

+ func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
+   return ScreenOrientation.getSupportedInterfaceOrientations()
+ }
```

If your project still uses CocoaPods instead of Swift Package Manager (SPM), import `CapawesomeCapacitorScreenOrientation` rather than `ScreenOrientationPlugin`:

```diff
+ import CapawesomeCapacitorScreenOrientation
```

#### iPad Orientation Lock

On iPad, you must add the following to your app's `Info.plist`:

```xml
<key>UIRequiresFullScreen</key>
<true/>
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

The following examples show how to lock, unlock, and read the current screen orientation.

### Lock the screen orientation

Lock the device to a specific orientation, for example landscape. Besides `LANDSCAPE` and `PORTRAIT`, you can also lock to a primary or secondary mode such as `LANDSCAPE_PRIMARY` for fine-grained control:

```typescript
import { ScreenOrientation, OrientationType } from '@capawesome/capacitor-screen-orientation';

const lock = async () => {
  await ScreenOrientation.lock({ type: OrientationType.LANDSCAPE });
};
```

### Unlock the screen orientation

Remove the orientation lock and restore automatic rotation:

```typescript
import { ScreenOrientation } from '@capawesome/capacitor-screen-orientation';

const unlock = async () => {
  await ScreenOrientation.unlock();
};
```

### Get the current screen orientation

Read the current orientation type of the device:

```typescript
import { ScreenOrientation } from '@capawesome/capacitor-screen-orientation';

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
lock(options?: LockOptions | undefined) => Promise<void>
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

## FAQ

### Why does the orientation lock not work on iPad?

For the orientation lock to work on iPad, you must add the `UIRequiresFullScreen` key with the value `true` to your app's `Info.plist` file, as described in the [Installation](#installation) section. Also make sure that you have applied the required changes to your `AppDelegate.swift`.

### Do I need to modify my AppDelegate on iOS?

Yes. You must implement the `supportedInterfaceOrientationsFor` method in your app's `AppDelegate.swift` and return `ScreenOrientation.getSupportedInterfaceOrientations()`, as shown in the [Installation](#installation) section. Without this change, the orientation lock has no effect on iOS.

### What is the difference between LANDSCAPE and LANDSCAPE_PRIMARY?

The `LANDSCAPE` type covers both landscape modes, so the device can be rotated between landscape-primary and landscape-secondary while locked. The `LANDSCAPE_PRIMARY` and `LANDSCAPE_SECONDARY` types lock the screen to exactly one of the two landscape modes. The same applies to `PORTRAIT`, `PORTRAIT_PRIMARY` and `PORTRAIT_SECONDARY`.

### How do I restore automatic rotation after locking the orientation?

Simply call the `unlock()` method. It removes the orientation lock so that the device rotates automatically again based on the user's device settings.

### How can I react to orientation changes?

Add a listener for the `screenOrientationChange` event using the `addListener(...)` method. The listener receives the new orientation type every time the screen orientation changes. Use `removeAllListeners()` to remove all listeners when you no longer need them.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Keep Awake](https://capawesome.io/docs/sdks/capacitor/keep-awake/): Keep the screen awake, for example during video playback.
- [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/): Read and control the screen brightness.
- [Home Indicator](https://capawesome.io/docs/sdks/capacitor/home-indicator/): Hide and show the iOS home indicator.
- [Navigation Bar](https://capawesome.io/docs/sdks/capacitor/navigation-bar/): Set the background color and button style of the Android navigation bar.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-orientation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-orientation/LICENSE).

## Credits

This plugin is based on the [Capacitor Screen Orientation](https://github.com/capawesome-team/capacitor-screen-orientation) plugin.
Thanks to everyone who contributed to the project!
