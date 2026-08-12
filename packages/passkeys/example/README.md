## Created with Capacitor Create App

This app was created using [`@capacitor/create-app`](https://github.com/ionic-team/create-capacitor-app),
and comes with a very minimal shell for building an app.

### Running this example

To run the provided example, you can use `npm start` command.

```bash
npm start
```

On **Web**, the default relying party ID `localhost` works out of the box for local development.

To test passkeys on **Android** or **iOS**, you need a domain that is associated with the app:

1. Replace the `RP_ID` constant in `src/js/script.js` with your domain.
2. On **Android**, host a Digital Asset Links file at `https://<your-domain>/.well-known/assetlinks.json` that delegates `common.get_login_creds` to the app.
3. On **iOS**, add the Associated Domains capability with the `webcredentials:<your-domain>` entry to the app and host an `apple-app-site-association` file at `https://<your-domain>/.well-known/apple-app-site-association`.

See the [plugin documentation](../README.md) for more details.
