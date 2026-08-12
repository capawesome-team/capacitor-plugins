## Created with Capacitor Create App

This app was created using [`@capacitor/create-app`](https://github.com/ionic-team/create-capacitor-app),
and comes with a very minimal shell for building an app.

### Running this example

To run the provided example, you can use `npm start` command.

```bash
npm start
```

### App Attest

The iOS app already includes the App Attest entitlement (`ios/App/App/App.entitlements`) with the `development` environment. App Attest is not supported on simulators, so you need a real device to test the `generateKey()`, `attestKey(...)` and `generateAssertion(...)` methods.
