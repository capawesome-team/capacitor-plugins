# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `prompt(...)` method

The `canceled` property has been removed from the result. If the user selects the cancel button or dismisses the dialog, the promise is now rejected with the `CANCELED` error code instead:

```typescript
// Before
const { value, canceled } = await Dialog.prompt({ message: 'What is your name?' });
if (canceled) {
  console.log('The user canceled the dialog.');
}

// After
try {
  const { value } = await Dialog.prompt({ message: 'What is your name?' });
} catch (error) {
  if (error.code === ErrorCode.Canceled) {
    console.log('The user canceled the dialog.');
  }
}
```
