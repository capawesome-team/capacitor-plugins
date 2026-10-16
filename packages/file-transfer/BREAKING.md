# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `androidNotification` option

All running transfers now share one notification that lists them by their `title` and shows their combined progress. The `androidNotification` option has been removed from `startDownload(...)` and `startUpload(...)`. The title and channel name of the notification are set once with the new `setConfig(...)` method, and the line of a transfer is its new `title` option:

```typescript
// Before
await FileTransfer.startDownload({
  url,
  path,
  androidNotification: {
    title: 'Downloading',
    text: 'file.zip',
    progress: true,
  },
});

// After
await FileTransfer.setConfig({
  androidNotification: { title: 'Downloads' },
});
await FileTransfer.startDownload({
  url,
  path,
  title: 'file.zip',
});
```

The `progress` property has no replacement, because the progress bar is now always part of the notification.

### `interrupted` state

Transfers that are stopped by the system are no longer `failed`. They are now `interrupted`, carry an `interruptionReason` and are reported through the new `transferInterrupted` event instead of `transferFailed`. On iOS, a transfer canceled by the system after a force quit is no longer retried automatically. Resume interrupted transfers with `resumeTransferById(...)`.
