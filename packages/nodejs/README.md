# @capawesome/capacitor-nodejs

Capacitor plugin for running [Node.js](https://nodejs.org/) in mobile apps.[^1][^2]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for running Node.js in mobile apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🚀 **Node.js runtime**: Embeds a complete Node.js runtime based on [Node.js for Mobile Apps](https://github.com/nodejs-mobile/nodejs-mobile).
- 🧵 **Background thread**: Runs the Node.js engine on a dedicated background thread to avoid blocking the UI.
- 🔁 **Bidirectional communication**: Event-based message passing between the Capacitor app and the Node.js runtime.
- 📦 **npm ecosystem**: Use npm packages that are not browser-compatible and rely on Node.js core modules.
- 🛠️ **Configurable**: Start the Node.js runtime automatically or manually with custom arguments, environment variables and script.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.0.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-nodejs` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-nodejs
npx cap sync
```

**Attention**: This plugin embeds the [Node.js for Mobile Apps](https://github.com/nodejs-mobile/nodejs-mobile) runtime binaries. The binaries are not included in the npm package but downloaded on demand: the Android binaries are downloaded by the Gradle build and the iOS binaries are downloaded during `npm install` (only on macOS). Set the `CAPACITOR_NODEJS_SKIP_DOWNLOAD` environment variable to `1` to skip the iOS download.

### Android

#### Variables

If needed, you can define the following project variables in your app's `variables.gradle` file to change the default version of the runtime:

- `$nodejsMobileVersion` version of the Node.js for Mobile Apps runtime (default: `18.20.4-capawesome.1`, a [16 KB page size compatible build](https://github.com/capawesome-team/nodejs-mobile/releases))
- `$nodejsMobileAndroidUrl` download URL of the Android runtime binaries (default: GitHub release of `$nodejsMobileVersion`)
- `$nodejsMobileAndroidSha256` SHA-256 checksum of the Android runtime binaries download (default: checksum of `$nodejsMobileVersion`)

### iOS

This plugin currently only supports **Swift Package Manager**. CocoaPods is not supported.

If you install dependencies with disabled lifecycle scripts (e.g. `npm install --ignore-scripts`), the iOS runtime binaries are not downloaded automatically. In this case, run the download script manually before building your app:

```bash
node node_modules/@capawesome/capacitor-nodejs/scripts/postinstall.js
```

## Configuration

<docgen-config>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

| Prop            | Type                            | Description                                                                                                                                                                                                                                                    | Default               | Since |
| --------------- | ------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`nodeDir`**   | <code>string</code>             | The directory of the Node.js project, relative to the Capacitor `webDir`. Only available on Android and iOS.                                                                                                                                                   | <code>'nodejs'</code> | 0.0.1 |
| **`startMode`** | <code>'manual' \| 'auto'</code> | The start mode of the Node.js runtime. If set to `auto`, the Node.js runtime starts automatically when the app is launched. If set to `manual`, the Node.js runtime must be started manually using the `start(...)` method. Only available on Android and iOS. | <code>'auto'</code>   | 0.0.1 |

### Examples

In `capacitor.config.json`:

```json
{
  "plugins": {
    "Nodejs": {
      "nodeDir": "custom-nodejs",
      "startMode": "manual"
    }
  }
}
```

In `capacitor.config.ts`:

```ts
/// <reference types="@capawesome/capacitor-nodejs" />

import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  plugins: {
    Nodejs: {
      nodeDir: 'custom-nodejs',
      startMode: 'manual',
    },
  },
};

export default config;
```

</docgen-config>

## Demo

A working example can be found [here](https://github.com/capawesome-team/capacitor-plugins/tree/main/packages/nodejs/example).

## Usage

The plugin runs the Node.js project located in the `nodejs` directory (see the `nodeDir` configuration option) inside your Capacitor `webDir`. Make sure your web build outputs the Node.js project to this directory, for example by placing it in the `public` directory of your web project:

```
my-app
├── capacitor.config.json   // webDir: 'dist'
└── src
    └── public
        └── nodejs
            ├── package.json
            └── index.js
```

The `package.json` file of the Node.js project defines the script file to run in the `main` field:

```json
{
  "name": "nodejs-project",
  "version": "1.0.0",
  "main": "index.js"
}
```

Inside the script file (`index.js` in this example), the built-in `bridge` module provides the communication channel to the Capacitor app:

```js
const { app, channel } = require('bridge');

// Receive messages from the Capacitor app.
channel.on('my-event', (...args) => {
  // Send messages to the Capacitor app.
  channel.post('my-response', 'Hello from Node.js!');
});

// Get a writable directory for persistent file storage.
const dataDir = app.datadir();

// Listen for app lifecycle events.
app.on('pause', pauseLock => {
  pauseLock.release();
});
app.on('resume', () => {});
```

**Attention**: The Node.js project directory may be overwritten during app updates. Store persistent data in the directory returned by `app.datadir()`.

In your Capacitor app, you can then use the plugin to start the Node.js runtime and exchange messages with it:

```typescript
import { Nodejs } from '@capawesome/capacitor-nodejs';

const isReady = async () => {
  const { ready } = await Nodejs.isReady();
  return ready;
};

const send = async () => {
  await Nodejs.send({
    eventName: 'my-event',
    args: ['Hello from Capacitor!'],
  });
};

const start = async () => {
  // Only available if the `startMode` configuration option is set to `manual`.
  await Nodejs.start({
    args: ['--option', 'value'],
    env: { MY_ENV_VAR: 'value' },
    script: 'custom-main.js',
  });
};

const addReadyListener = async () => {
  await Nodejs.addListener('ready', () => {
    console.log('The Node.js runtime is ready.');
  });
};

const addMessageListener = async () => {
  await Nodejs.addListener('message', event => {
    console.log('Received message:', event.eventName, event.args);
  });
};
```

To use npm packages, run `npm install --omit=dev` inside the Node.js project directory before building your web project. It's recommended to bundle the Node.js project into a single file (e.g. with [esbuild](https://esbuild.github.io/)) to improve the startup time.

## API

<docgen-index>

- [`isReady()`](#isready)
- [`send(...)`](#send)
- [`start(...)`](#start)
- [`addListener('message', ...)`](#addlistenermessage-)
- [`addListener('ready', ...)`](#addlistenerready-)
- [`removeAllListeners()`](#removealllisteners)
- [Interfaces](#interfaces)
- [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isReady()

```typescript
isReady() => Promise<IsReadyResult>
```

Check if the Node.js runtime is ready to receive messages.

The Node.js runtime is considered ready as soon as the Node.js project
has required the `bridge` module.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isreadyresult">IsReadyResult</a>&gt;</code>

**Since:** 0.0.1

---

### send(...)

```typescript
send(options: SendOptions) => Promise<void>
```

Send a message to the Node.js runtime.

This method is only available when the Node.js runtime is ready.
Use the `isReady()` method or the `ready` event to check if the
Node.js runtime is ready.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#sendoptions">SendOptions</a></code> |

**Since:** 0.0.1

---

### start(...)

```typescript
start(options?: StartOptions | undefined) => Promise<void>
```

Start the Node.js runtime manually.

This method is only available if the `startMode` configuration option
is set to `manual`.

**Attention**: The Node.js runtime can only be started once per app
launch. Stopping and restarting the Node.js runtime is not supported.

Only available on Android and iOS.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#startoptions">StartOptions</a></code> |

**Since:** 0.0.1

---

### addListener('message', ...)

```typescript
addListener(eventName: 'message', listenerFunc: (event: MessageEvent) => void) => Promise<PluginListenerHandle>
```

Called when a message is received from the Node.js runtime.

Only available on Android and iOS.

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>'message'</code>                                                    |
| **`listenerFunc`** | <code>(event: <a href="#messageevent">MessageEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

---

### addListener('ready', ...)

```typescript
addListener(eventName: 'ready', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the Node.js runtime is ready to receive messages.

Only available on Android and iOS.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'ready'</code>       |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

---

### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

---

### Interfaces

#### IsReadyResult

| Prop        | Type                 | Description                                                      | Since |
| ----------- | -------------------- | ---------------------------------------------------------------- | ----- |
| **`ready`** | <code>boolean</code> | Whether or not the Node.js runtime is ready to receive messages. | 0.0.1 |

#### SendOptions

| Prop            | Type                      | Description                                           | Since |
| --------------- | ------------------------- | ----------------------------------------------------- | ----- |
| **`args`**      | <code>MessageArg[]</code> | The arguments to send to the Node.js runtime.         | 0.0.1 |
| **`eventName`** | <code>string</code>       | The name of the event to send to the Node.js runtime. | 0.0.1 |

#### StartOptions

| Prop         | Type                                    | Description                                                                    | Default                                                                          | Since |
| ------------ | --------------------------------------- | ------------------------------------------------------------------------------ | -------------------------------------------------------------------------------- | ----- |
| **`args`**   | <code>string[]</code>                   | The arguments to pass to the Node.js process.                                  |                                                                                  | 0.0.1 |
| **`env`**    | <code>{ [key: string]: string; }</code> | The environment variables to set for the Node.js process.                      |                                                                                  | 0.0.1 |
| **`script`** | <code>string</code>                     | The path of the script file to run, relative to the Node.js project directory. | <code>The `main` field of the `package.json` file of the Node.js project.</code> | 0.0.1 |

#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

#### MessageEvent

| Prop            | Type                      | Description                                              | Since |
| --------------- | ------------------------- | -------------------------------------------------------- | ----- |
| **`args`**      | <code>MessageArg[]</code> | The arguments received from the Node.js runtime.         | 0.0.1 |
| **`eventName`** | <code>string</code>       | The name of the event received from the Node.js runtime. | 0.0.1 |

### Type Aliases

#### MessageArg

A single argument of a message that is exchanged with the Node.js runtime.

<code>string | number | boolean</code>

</docgen-api>

## Limitations

The underlying [Node.js for Mobile Apps](https://github.com/nodejs-mobile/nodejs-mobile) runtime has some limitations that you should be aware of:

- **Single instance**: The Node.js runtime can only be started once per app launch. Stopping and restarting the runtime is not supported.
- **No child processes**: The `child_process` module is not supported on mobile platforms.
- **No JIT on iOS**: On iOS, the JavaScript engine runs in interpreter-only mode (no JIT compilation), which results in slower JavaScript execution compared to Android.
- **App size**: Embedding the Node.js runtime increases the app size by several tens of megabytes per CPU architecture.
- **Native addons**: Node.js native addons are only supported on Android if they are provided as prebuilds (see [`node-gyp-build`](https://github.com/prebuild/node-gyp-build)) for the target architectures.
- **`process.exit()`**: Calling `process.exit()` is not allowed by the Apple App Store guidelines.

## FAQ

### Which Node.js version is supported?

The plugin currently runs Node.js `18.20.4`, the latest version available from [Node.js for Mobile Apps](https://github.com/nodejs-mobile/nodejs-mobile). Support for newer Node.js versions requires self-built runtime binaries for mobile platforms, which we are evaluating.

### Why is there no `stop()` method?

The underlying runtime only supports a single Node.js instance per app launch and provides no API to stop or restart it. A `stop()` method would therefore leave the app in a state where Node.js could never be started again until the app is restarted. If you need to stop work in the Node.js runtime, send a message (e.g. a `shutdown` event) and let your Node.js code stop its servers and timers. The idle runtime consumes negligible resources.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nodejs/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/nodejs/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by the OpenJS Foundation or any of their affiliates or subsidiaries.
[^2]: `Node.js` is a registered trademark of the OpenJS Foundation.
