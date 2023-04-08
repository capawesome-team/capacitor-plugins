# @capawesome/capacitor-background-task

Capacitor plugin for running background tasks.

## Installation

```bash
npm install @capawesome/capacitor-background-task
npx cap sync
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

```typescript
import { App } from '@capacitor/app';
import { BackgroundTask } from '@capawesome/capacitor-background-task';

App.addListener('appStateChange', async ({ isActive }) => {
  if (isActive) {
    return;
  }
  // The app state has been changed to inactive.
  // Start the background task by calling `beforeExit`.
  const taskId = await BackgroundTask.beforeExit(async () => {
    // Run your code...
    // Finish the background task as soon as everything is done.
    BackgroundTask.finish({ taskId });
  });
});
```

## API

<docgen-index>

* [`beforeExit(...)`](#beforeexit)
* [`finish(...)`](#finish)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### beforeExit(...)

```typescript
beforeExit(cb: () => void) => Promise<CallbackID>
```

Call this method when the app moves to the background.
It allows the app to continue running a task in the background.

On **iOS** this method should be finished in less than 30 seconds.

Only available on Android and iOS.

| Param    | Type                       |
| -------- | -------------------------- |
| **`cb`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------


### finish(...)

```typescript
finish(options: FinishOptions) => void
```

Finish the current background task.
The OS will put the app to sleep.

Only available on Android and iOS.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#finishoptions">FinishOptions</a></code> |

--------------------


### Interfaces


#### FinishOptions

| Prop         | Type                                              |
| ------------ | ------------------------------------------------- |
| **`taskId`** | <code><a href="#callbackid">CallbackID</a></code> |


### Type Aliases


#### CallbackID

<code>string</code>

</docgen-api>

## Quirks

### iOS

On **iOS** the [UIKit framework](https://developer.apple.com/documentation/uikit) is used.
Read more about the implementation and any limitations [here](https://developer.apple.com/documentation/uikit/app_and_environment/scenes/preparing_your_ui_to_run_in_the_background/extending_your_app_s_background_execution_time).

### Android

There is currently no ready implementation on **Android**.
It's planned to add the support in the near future.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-task/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-task/LICENSE).

## Credits

This plugin is based on the [Capacitor Background Task](https://github.com/capawesome-team/capacitor-background-task) plugin.
Thanks to everyone who contributed to the project!
