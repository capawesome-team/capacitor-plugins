# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.3.x](#version-03x)
- [Version 0.2.x](#version-02x)

## Version 0.3.x

### Node.js 24

The Node.js runtime has been updated from `18.20.4` to `24.5.0`. Check your Node.js project for changes between Node.js 18 and 24 (see the [Node.js changelog](https://github.com/nodejs/node/blob/main/doc/changelogs/CHANGELOG_V24.md)). Native addons built for Node.js 18 must be rebuilt for Node.js 24.

### iOS Simulator on Intel Macs

The iOS runtime no longer includes an `x86_64` simulator slice. Running the app on the iOS Simulator requires an Apple Silicon Mac.

## Version 0.2.x

### `nodejsMobileVersion` variable

The `nodejsMobileVersion` Android variable has been removed. To use different runtime binaries, set both the `nodejsMobileAndroidUrl` and `nodejsMobileAndroidSha256` variables in your app's `variables.gradle` file instead:

```groovy
ext {
    nodejsMobileAndroidUrl = 'https://github.com/capawesome-team/nodejs-mobile/releases/download/v18.20.4-capawesome.1/nodejs-mobile-v18.20.4-capawesome.1-android.zip'
    nodejsMobileAndroidSha256 = '1b3c7979c81aec89a7f51b29af1f4875a5d637727ad6e2c392cdf2e127715da9'
}
```
