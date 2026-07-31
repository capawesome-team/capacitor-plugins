## Created with Capacitor Create App

This app was created using [`@capacitor/create-app`](https://github.com/ionic-team/create-capacitor-app),
and comes with a very minimal shell for building an app.

### Running this example

To run the provided example, you can use `npm start` command.

```bash
npm start
```

### Testing the privacy screen on iOS

The **Open System Dialog** button opens a `tel:` URL, which makes iOS present the
system call confirmation sheet. This requires a real device, as the iOS Simulator
has no Phone app.

Tap **Enable**, then verify the following:

| Action                            | Expected                  |
| --------------------------------- | ------------------------- |
| Open the app switcher             | App content is blurred    |
| Tap **Open System Dialog**        | App content stays visible |
| Pull down the Notification Center | App content stays visible |
| Swipe down the Control Center     | App content stays visible |
