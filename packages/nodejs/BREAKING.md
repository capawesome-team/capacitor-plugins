# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `nodejsMobileVersion` variable

The `nodejsMobileVersion` Android variable has been removed. To use different runtime binaries, set both the `nodejsMobileAndroidUrl` and `nodejsMobileAndroidSha256` variables in your app's `variables.gradle` file instead:

```groovy
ext {
    nodejsMobileAndroidUrl = 'https://github.com/capawesome-team/nodejs-mobile/releases/download/v18.20.4-capawesome.1/nodejs-mobile-v18.20.4-capawesome.1-android.zip'
    nodejsMobileAndroidSha256 = '1b3c7979c81aec89a7f51b29af1f4875a5d637727ad6e2c392cdf2e127715da9'
}
```
