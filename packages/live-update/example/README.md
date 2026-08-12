## Created with Capacitor Create App

This app was created using [`@capacitor/create-app`](https://github.com/ionic-team/create-capacitor-app),
and comes with a very minimal shell for building an app.

### Running this example

To run the provided example, you can use `npm start` command.

```bash
npm start
```

### Running Maestro tests

The example app includes [Maestro](https://maestro.dev) flows under `.maestro/` for verifying basic UI behavior.

Prerequisite: an Android emulator or iOS simulator running the example app (`npx cap run android` or `npx cap run ios`).

Run all flows:

```bash
maestro test .maestro
```

Run a single flow:

```bash
maestro test .maestro/smoke-test.yaml
```

Some flows rely on the local mock server in `test/mock-server.mjs` (e.g. `download-checksum-mismatch.yaml`). The flows always hit `http://localhost:4000`; on Android the npm script sets up `adb reverse` so the emulator's `localhost` maps to the host. To run all flows together with the mock server:

```bash
npm run e2e:android   # or
npm run e2e:ios
```
