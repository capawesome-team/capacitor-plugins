# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `showActions(...)` method

The `canceled` property has been removed from the result. If the user selects a button with the `ActionSheetButtonStyle.Cancel` style or dismisses the action sheet, the promise is now rejected with the `CANCELED` error code instead:

```typescript
// Before
const { index, canceled } = await ActionSheet.showActions({ options });
if (canceled) {
  console.log('The user canceled the action sheet.');
}

// After
try {
  const { index } = await ActionSheet.showActions({ options });
} catch (error) {
  if (error.code === ErrorCode.Canceled) {
    console.log('The user canceled the action sheet.');
  }
}
```
