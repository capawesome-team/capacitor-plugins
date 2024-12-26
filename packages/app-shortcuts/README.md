# @capawesome/capacitor-app-shortcuts

Capacitor plugin to manage app shortcuts and quick actions.

## Installation

To use npm

```bash
npm install @capawesome/capacitor-app-shortcuts
```

To use yarn

```bash
yarn add @capawesome/capacitor-app-shortcuts
```

Sync native files

```bash
npx cap sync
```

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

```ts
import { AppShortcuts } from '@capawesome/capacitor-app-shortcuts';

const clear = async () => {
  await AppShortcuts.clear();
};

const get = async () => {
  const result = await AppShortcuts.get();
  return result.shortcuts;
};

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

Removes all existing app shortcuts from the device.

This method ensures no app shortcuts remain on the user's home screen.

Available only on Android and iOS platforms.

**Since:** 6.0.0

--------------------


### get()

```typescript
get() => Promise<GetResult>
```

Retrieves the list of all app shortcuts currently set on the device.

This method returns a structured result containing the shortcuts information.

Available only on Android and iOS platforms.

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### set(...)

```typescript
set(options: SetOptions) => Promise<void>
```

Creates new app shortcuts or updates existing ones based on the provided options.

Use this method to define or modify the app shortcuts displayed on the user's device.

Available only on Android and iOS platforms.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#setoptions">SetOptions</a></code> |

**Since:** 6.0.0

--------------------


### addListener('click', ...)

```typescript
addListener(eventName: 'click', listenerFunc: (event: ClickEvent) => void) => Promise<PluginListenerHandle>
```

Registers a listener that triggers when an app shortcut is clicked by the user.

The listener receives an event object containing information about the clicked shortcut.

Available only on Android and iOS platforms.

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

Removes all event listeners associated with this plugin.

Use this to clean up resources or stop listening for app shortcut events.

**Since:** 6.0.0

--------------------


### Interfaces


#### GetResult

Represents the result returned by the `get` method, containing the current app shortcuts.

| Prop            | Type                    | Description                                                                                                                                            | Since |
| --------------- | ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | An array of app shortcuts currently configured for the application. Each shortcut provides metadata such as title, ID, and description (if available). | 6.0.0 |


#### Shortcut

Represents the metadata for an individual app shortcut.

App shortcuts are quick actions that can be accessed directly from the app's icon.

| Prop              | Type                | Description                                                                                                                                            | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`description`** | <code>string</code> | A brief description of the shortcut's functionality. Available only on Android platforms.                                                              | 6.0.0 |
| **`id`**          | <code>string</code> | A unique identifier for the shortcut. This `id` is used to differentiate between shortcuts and is required for actions like updates or event handling. | 6.0.0 |
| **`title`**       | <code>string</code> | The display name of the shortcut. This `title` is shown to the user as the shortcut's label on the home screen or app menu.                            | 6.0.0 |


#### SetOptions

Represents the options required to create or update app shortcuts using the `set` method.

| Prop            | Type                    | Description                                                                                                                  | Since |
| --------------- | ----------------------- | ---------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | An array of shortcuts to be created or updated. Each shortcut must include a unique identifier (`id`) and a title (`title`). | 6.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ClickEvent

Represents the event triggered when an app shortcut is clicked by the user.

The event provides the `shortcutId` of the clicked shortcut.

| Prop             | Type                | Description                                                                                                                                                                  | Since |
| ---------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`shortcutId`** | <code>string</code> | The unique identifier (`id`) of the app shortcut that was clicked. This `shortcutId` can be used to determine which action to perform in response to the user's interaction. | 6.0.0 |

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/LICENSE).
