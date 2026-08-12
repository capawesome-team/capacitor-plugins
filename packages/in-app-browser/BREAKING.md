# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `clearSessionData()` method

The `clearSessionData()` method has been replaced by the `clearCookies()` method. The previous implementation also deleted the web storage of the app's own origin, which could corrupt the state of the app. The new method only clears cookies and accepts an optional `url` parameter to clear only the cookies of a specific URL:

```typescript
// Before
await InAppBrowser.clearSessionData();

// After
await InAppBrowser.clearCookies();
// or
await InAppBrowser.clearCookies({ url: 'https://capawesome.io' });
```
