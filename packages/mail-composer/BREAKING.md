# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `attachments` option

The `attachments` option of the `composeMail(...)` method is now an array of objects instead of an array of strings. Use the `path` property to attach a file from the file system:

```typescript
// Before
await MailComposer.composeMail({ attachments: ['/path/to/file.pdf'] });

// After
await MailComposer.composeMail({ attachments: [{ path: '/path/to/file.pdf' }] });
```

Alternatively, use the new `data` and `name` properties to attach a file from a base64 string without writing it to the file system first:

```typescript
await MailComposer.composeMail({
  attachments: [{ data: 'SGVsbG8gV29ybGQ=', name: 'log.txt' }],
});
```
