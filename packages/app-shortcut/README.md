# @capawesome/capacitor-app-shortcut

Capacitor plugin to manage app shortcuts and quick actions.

## Install

```bash
npm install @capawesome/capacitor-app-shortcut
npx cap sync
```

### iOS

On iOS, you must add the following to your app's `AppDelegate.swift`:

```diff
+ import CapawesomeCapacitorAppShortcut

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

  func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
+     if let shortcutItem = launchOptions?[.shortcutItem] as? UIApplicationShortcutItem {
+         handleOnAppShortcut(shortcutItem)
+         return false
+     }
      return true
  }
  
+ func application(_ application: UIApplication, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
+     handleOnAppShortcut(shortcutItem)
+     completionHandler(true)
+ }
  
+  private func handleOnAppShortcut(_ shortcutItem: UIApplicationShortcutItem) {
+      NotificationCenter.default.post(
+        name: NSNotification.Name(AppShortcutPlugin.onAppShortcutEvent),
+        object: nil,
+        userInfo: [AppShortcutPlugin.userInfoShortcutItemKey: shortcutItem]
+      )
+  }
```

## API

<docgen-index>

* [`clear()`](#clear)
* [`get()`](#get)
* [`set(...)`](#set)
* [`addListener('onAppShortcut', ...)`](#addlisteneronappshortcut-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### clear()

```typescript
clear() => Promise<void>
```

Clear the list of app shortcuts.

Only available on Android and iOS.

**Since:** 6.0.0

--------------------


### get()

```typescript
get() => Promise<GetResult>
```

Get the list of app shortcuts.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getresult">GetResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### set(...)

```typescript
set(options: SetOptions) => Promise<void>
```

Create or update the list of app shortcuts.

Only available on Android and iOS.

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`options`** | <code><a href="#setoptions">SetOptions</a></code> |

**Since:** 6.0.0

--------------------


### addListener('onAppShortcut', ...)

```typescript
addListener(eventName: 'onAppShortcut', listenerFunc: (event: OnAppShortcutEvent) => void) => Promise<PluginListenerHandle>
```

Called when an app shortcut is clicked.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'onAppShortcut'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#onappshortcutevent">OnAppShortcutEvent</a>) =&gt; void</code> |

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

| Prop            | Type                    | Since |
| --------------- | ----------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | 6.0.0 |


#### Shortcut

| Prop              | Type                | Description               | Since |
| ----------------- | ------------------- | ------------------------- | ----- |
| **`description`** | <code>string</code> | Only supported on Android | 6.0.0 |
| **`id`**          | <code>string</code> |                           | 6.0.0 |
| **`title`**       | <code>string</code> |                           | 6.0.0 |


#### SetOptions

| Prop            | Type                    | Since |
| --------------- | ----------------------- | ----- |
| **`shortcuts`** | <code>Shortcut[]</code> | 6.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### OnAppShortcutEvent

| Prop     | Type                | Since |
| -------- | ------------------- | ----- |
| **`id`** | <code>string</code> | 6.0.0 |

</docgen-api>
