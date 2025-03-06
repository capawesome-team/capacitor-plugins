# @capawesome/capacitor-app-shortcuts

Capacitor plugin to manage app shortcuts and quick actions.

## Installation

```bash
npm install @capawesome/capacitor-app-shortcuts
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

| Prop              | Type                | Description                                                                                                                                                                                                                                                               | Since |
| ----------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`description`** | <code>string</code> | The description. On **Android**, the launcher shows this instead of the short title when it has enough space. **Attention**: On **iOS**, the icon and the description must be used together.                                                                              | 6.0.0 |
| **`id`**          | <code>string</code> | The unique identifier.                                                                                                                                                                                                                                                    | 6.0.0 |
| **`title`**       | <code>string</code> | The display name.                                                                                                                                                                                                                                                         | 6.0.0 |
| **`icon`**        | <code>number</code> | The icon to display. On **Android**, the icon is the constant value of the `R.drawable` enum. On **iOS**, the icon is the constant value of the `UIApplicationShortcutIcon.IconType` enum. **Attention**: On **iOS**, the icon and the description must be used together. | 6.1.0 |
| **`imageName`**   | <code>string</code> | Name of an asset from the assets catalogue. It overrides `icon` when used together. Only available on iOS.                                                                                                                                                                | 7.1.0 |


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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/app-shortcuts/LICENSE).
