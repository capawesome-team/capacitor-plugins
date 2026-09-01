# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `startWatching(...)` method

The `sync` option has been removed. The upload is configured with `setConfig(...)` instead, so it no longer has to be set up again for every watch session and an expired authorization header can be replaced without stopping the session.

The `distanceFilter` option now defaults to `10` meters instead of `0`, and the `androidInterval` option to `5000` milliseconds instead of `1000`. A device that stands still therefore no longer records the same position over and over, which previously filled the queue with about `86400` duplicates per day on Android. Pass the previous values to keep the old behavior:

```typescript
await BackgroundGeolocation.startWatching({
  androidInterval: 1000,
  distanceFilter: 0,
  // ...
});
```

### `setConfig(...)` method

New method that configures the position queue and the upload. The configuration is persisted natively and **replaces** the previous one entirely, so always pass every property you want to keep:

```typescript
await BackgroundGeolocation.setConfig({
  batchSize: 100,
  headers: { Authorization: 'Bearer eyJhbGciOi...' },
  maxSize: 50000,
  url: 'https://api.example.com/positions',
});
```

Positions are stored if `maxSize` or `url` is provided, and uploaded if `url` is provided. Call `resetConfig()` to stop storing and uploading positions.

### `getConfig()` method

New method that returns the configuration that was last set. Only the properties that were provided are returned, so the result can be spread into `setConfig(...)` to change a single one:

```typescript
const config = await BackgroundGeolocation.getConfig();
await BackgroundGeolocation.setConfig({ ...config, maxSize: 5000 });
```

### `clearSyncQueue()` method

Renamed to `clearQueue()`.

### `getSyncStatus()` method

Renamed to `getQueueStatus()`. It returns `droppedCount`, `pendingCount` and `lastUploadedAt`, which was named `lastSyncedAt` before.

### `triggerSync()` method

Renamed to `triggerUpload()`. The promise now rejects if no `url` has been set via `setConfig(...)`. Previously it rejected if no watch session with a `sync` configuration was active.

### `syncFailed` event

Renamed to `uploadFailed`. The payload is unchanged. The plugin no longer uses the term "sync" anywhere, because the upload is a one-way delivery that ignores the response body:

```typescript
await BackgroundGeolocation.addListener('uploadFailed', event => {
  console.error('Upload failed: ', event.statusCode, event.message);
});
```

### Reading queued positions

Positions recorded during a watch session can now be read with `getQueuedPositions(...)` and acknowledged with `deleteQueuedPositions(...)`, so an app no longer needs a server to collect positions that were recorded while the web view was suspended.

### Upload response handling

A `401` response no longer drops the batch. It is retried, so an expired token can be replaced with `setConfig(...)` and the queued positions are uploaded afterwards.

### Queue database

The queue database has been renamed to `capawesome_capacitor_background_geolocation_queue.db`. Positions that were queued with a previous version are **not** carried over and can no longer be read. Upload or read all queued positions before upgrading.
