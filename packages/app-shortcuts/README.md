# Capacitor App Shortcuts Plugin

Capacitor plugin to manage app shortcuts and quick actions.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The App Shortcuts plugin is typically used to give users faster access to key parts of your app right from the home screen icon, for example:

- **Quick actions**: Let users jump directly to frequently used screens such as search, compose, or feedback.
- **Dynamic shortcuts**: Create or update shortcuts at runtime based on user behavior, for example a shortcut to the most recently opened item.
- **Default shortcuts**: Define a set of default shortcuts in your Capacitor configuration without writing any code.
- **Deep linking**: React to shortcut clicks with the `click` listener and navigate the user to the corresponding screen.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-app-shortcuts` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-app-shortcuts
npx cap sync
```

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop            | Type                    | Description                                                                                 | Since |
| --------------- | ----------------------- | ------------------------------------------------------------------------------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | The list of app shortcuts that should be set by default. Only available on Android and iOS. | 7.2.0 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "AppShortcuts": {
      "shortcuts": [{ id: 'feedback', title: 'Feedback' }]
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-app-shortcuts" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    AppShortcuts: {
      shortcuts: [{ id: 'feedback', title: 'Feedback' }],
    },
  },
};

export default config;
```

</docgen-config>

### iOS

On iOS, you must add the following to your app's `AppDelegate.swift`:

```diff
+ import CapawesomeCapacitorAppShortcuts

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
+        if let shortcutItem = launchOptions?[.shortcutItem] as? UIApplicationShortcutItem {
+            NotificationCenter.default.post(name: NSNotification.Name(AppShortcutsPlugin.notificationName), object: nil, userInfo: [AppShortcutsPlugin.userInfoShortcutItemKey: shortcutItem])
+            return true
+        }
        return true
    }
    
+    func application(_ application: UIApplication, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
+        NotificationCenter.default.post(name: NSNotification.Name(AppShortcutsPlugin.notificationName), object: nil, userInfo: [AppShortcutsPlugin.userInfoShortcutItemKey: shortcutItem])
+        completionHandler(true)
+    }
```

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/app-shortcuts/example).

| Android                                                                                                     | iOS                                                                                                         |
| ----------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| <image src="https://github.com/user-attachments/assets/58ac7272-de12-457f-a047-8f1f1e08ef52" width="324" /> | <image src="https://github.com/user-attachments/assets/6de8e629-8c31-4383-ba1a-faa921117128" width="324" /> |

## Usage

Import the plugin and call its methods:

```ts
import { AppShortcuts } from '@capawesome/capacitor-app-shortcuts';
```

### Create or update app shortcuts

Use the `set(...)` method to create or update the app shortcuts. Each shortcut needs a unique identifier and a title. Only available on Android and iOS:

```ts
const set = async () => {
  await AppShortcuts.set({
    shortcuts: [
      {
        id: 'feedback',
        title: 'Feedback',
        description: 'Send us your feedback',
      },
      {
        id: 'rate',
        title: 'Rate',
        description: 'Rate our app',
      }
    ],
  });
};
```

### Get all app shortcuts

Retrieve the list of app shortcuts that are currently set. Only available on Android and iOS:

```ts
const get = async () => {
  const result = await AppShortcuts.get();
  return result.shortcuts;
};
```

### Remove all app shortcuts

Clear all app shortcuts, for example when the user signs out. Only available on Android and iOS:

```ts
const clear = async () => {
  await AppShortcuts.clear();
};
```

### Listen for shortcut clicks

Add a listener for the `click` event to react when the user taps an app shortcut, for example to navigate to the corresponding screen. On iOS, this requires the changes to your `AppDelegate.swift` described in the [iOS configuration](#ios) instructions:

```ts
const addListener = async () => {
  AppShortcuts.addListener('click', (event) => {
    console.log('Shortcut clicked:', event.id);
  });
};
```

## API

<docgen-index>

* [`clear()`](#clear)
* [`get()`](#get)
* [`set(...)`](#set)
* [`addListener('click', ...)`](#addlistenerclick-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clear()

```typescript
clear() => Promise<void>
```

Remove all app shortcuts.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### get()

```typescript
get() => Promise<GetResult>
```

Get all app shortcuts.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### set(...)

```typescript
set(options: SetOptions) => Promise<void>
```

Create or update app shortcuts.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#setoptions">SetOptions</a></code> |

**Since:** 6.0.0

--------------------


### addListener('click', ...)

```typescript
addListener(eventName: 'click', listenerFunc: (event: ClickEvent) => void) => Promise<PluginListenerHandle>
```

Called when an app shortcut is clicked.

Only available on Android and iOS.

| Param              | Type                                                                  |
| ------------------ | --------------------------------------------------------------------- |
| **`eventName`**    | <code>'click'</code>                                                  |
| **`listenerFunc`** | <code>(event: <a href="#clickevent">ClickEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 6.0.0

--------------------


### Interfaces


#### GetResult

| Prop            | Type                    | Description                | Since |
| --------------- | ----------------------- | -------------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | The list of app shortcuts. | 6.0.0 |


#### Shortcut

| Prop              | Type                          | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          | Since |
| ----------------- | ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`description`** | <code>string</code>           | The description. On **Android**, the launcher shows this instead of the short title when it has enough space. **Attention**: On **iOS**, the icon and the description must be used together.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | 6.0.0 |
| **`id`**          | <code>string</code>           | The unique identifier.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               | 6.0.0 |
| **`title`**       | <code>string</code>           | The display name.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | 6.0.0 |
| **`icon`**        | <code>string \| number</code> | The icon to display. On **Android**, the icon can be one of the following: - An integer value of the [R.drawable](https://developer.android.com/reference/android/R.drawable) enum (e.g. `17301547`). - A string that represents the name of the drawable resource (e.g. `"alert_dark_frame"`). - A base64 encoded image string (e.g. `"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAASABIAAD/4QC..."`). On **iOS**, the icon can be one of the following: - The constant integer value of the [UIApplicationShortcutIcon.IconType](https://developer.apple.com/documentation/uikit/uiapplicationshortcuticon/icontype) enum (e.g. `6`). - A system symbol name (e.g. `star.fill`). - Name of the image asset from the asset catalogue. | 6.1.0 |
| **`androidIcon`** | <code>string \| number</code> | The icon to display on Android. The icon can be one of the following: - An integer value of the [R.drawable](https://developer.android.com/reference/android/R.drawable) enum (e.g. `17301547`). - A string that represents the name of the drawable resource (e.g. `"alert_dark_frame"`). - A base64 encoded image string (e.g. `"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAASABIAAD/4QC..."`).                                                                                                                                                                                                                                                                                                                                     | 7.2.0 |
| **`iosIcon`**     | <code>string \| number</code> | The icon to display on iOS. The icon can be one of the following: - The constant integer value of the [UIApplicationShortcutIcon.IconType](https://developer.apple.com/documentation/uikit/uiapplicationshortcuticon/icontype) enum (e.g. `6`). - A system symbol name (e.g. `star.fill`). - Name of the image asset from the asset catalogue.                                                                                                                                                                                                                                                                                                                                                                                       | 7.2.0 |


#### SetOptions

| Prop            | Type                    | Description                | Since |
| --------------- | ----------------------- | -------------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | The list of app shortcuts. | 6.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ClickEvent

| Prop             | Type                | Description                                                 | Since |
| ---------------- | ------------------- | ----------------------------------------------------------- | ----- |
| **`shortcutId`** | <code>string</code> | The unique identifier of the app shortcut that was clicked. | 6.0.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

App shortcuts are a native concept of the Android launcher and the iOS home screen, so the plugin is only available on Android and iOS. All plugin methods and the `click` event are marked as only available on Android and iOS in the [API](#api) section.

### Why is the click listener not called on iOS?

On iOS, the plugin relies on changes to your app's `AppDelegate.swift` to forward shortcut items to the plugin. Make sure you have applied the code changes described in the [iOS configuration](#ios) instructions. Without them, the `click` event is never delivered.

### Can I define app shortcuts without writing any code?

Yes, you can use the `shortcuts` configuration option to define a list of app shortcuts that are set by default. See the [Configuration](#configuration) section for examples in `capacitor.config.json` and `capacitor.config.ts`.

### Can I use different icons on Android and iOS?

Yes, you can use the `androidIcon` and `iosIcon` properties to provide platform-specific icons, or the `icon` property for both platforms. On Android, drawable resources and base64 encoded images are supported, while on iOS you can use system symbol names, asset catalogue images, or `UIApplicationShortcutIcon` types. Note that on iOS, the icon and the description must be used together.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [App Icon](https://capawesome.io/docs/sdks/capacitor/app-icon/): Change the app icon at runtime.
- [Badge](https://capawesome.io/docs/sdks/capacitor/badge/): Access and update the badge number of the app icon.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/LICENSE).
