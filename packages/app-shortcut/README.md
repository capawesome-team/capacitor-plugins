# @capawesome/capacitor-app-shortcut

Capacitor plugin to manage app shortcuts and quick actions.

## Install

```bash
npm install @capawesome/capacitor-app-shortcut
npx cap sync
```

## API

<docgen-index>

* [`clear()`](#clear)
* [`get()`](#get)
* [`set(...)`](#set)
* [`addListener('onAppShortcut', ...)`](#addlisteneronappshortcut)
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

| Prop            | Type                    |
| --------------- | ----------------------- |
| **`shortcuts`** | <code>Shortcut[]</code> |


#### Shortcut

| Prop              | Type                |
| ----------------- | ------------------- |
| **`description`** | <code>string</code> |
| **`id`**          | <code>string</code> |
| **`title`**       | <code>string</code> |
| **`iosIcon`**     | <code>number</code> |


#### SetOptions

| Prop            | Type                    |
| --------------- | ----------------------- |
| **`shortcuts`** | <code>Shortcut[]</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### OnAppShortcutEvent

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |

</docgen-api>
