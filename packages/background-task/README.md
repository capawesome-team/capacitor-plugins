# Capacitor Background Task Plugin

Capacitor plugin for running background tasks.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Background Task plugin is typically used to finish short-running work when the app moves to the background, for example:

- **Finish network requests**: Complete an upload or API request that was started while the app was in the foreground.
- **Save application state**: Persist unsaved user input or app state before the OS puts the app to sleep.
- **Sync data**: Finish writing pending changes to your backend or local storage.
- **Clean up resources**: Close connections or release resources gracefully before the app is suspended.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-background-task` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

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

## FAQ

### How much time does my task have to run in the background?

On iOS, the task started with `beforeExit(...)` should be finished in less than 30 seconds. As soon as your work is done, call `finish(...)` so the OS can put the app to sleep. Read more about the underlying background execution mechanism in the [Apple documentation](https://developer.apple.com/documentation/uikit/app_and_environment/scenes/preparing_your_ui_to_run_in_the_background/extending_your_app_s_background_execution_time).

### Is this plugin supported on Android?

There is currently no ready implementation on Android, so the task is not extended there. Support for Android is planned for the future, see the [Quirks](#quirks) section.

### Can I run periodic or scheduled tasks with this plugin?

No, this plugin is designed to extend the execution time of your app when it moves to the background, so you can finish a short-running task. It does not schedule tasks that run while the app is closed.

### How do I know when the app moves to the background?

Use the `appStateChange` listener of the official `@capacitor/app` plugin, as shown in the [Usage](#usage) section. When `isActive` becomes `false`, call `beforeExit(...)` to start the background task.

### When do I have to call the `finish` method?

Call `finish(...)` with the task ID returned by `beforeExit(...)` as soon as everything is done. This tells the OS that the task is complete so it can put the app to sleep.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Alarm](https://capawesome.io/docs/sdks/capacitor/alarm/): Create system alarms and timers.
- [Android Foreground Service](https://capawesome.io/docs/sdks/capacitor/android-foreground-service/): Run a foreground service on Android for long-running work.
- [Keep Awake](https://capawesome.io/docs/sdks/capacitor/keep-awake/): Keep the screen awake while your app is in the foreground.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-task/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-task/LICENSE).

## Credits

This plugin is based on the [Capacitor Background Task](https://github.com/capawesome-team/capacitor-background-task) plugin.
Thanks to everyone who contributed to the project!
