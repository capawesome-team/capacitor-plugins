# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### Queued transitions instead of replayed transitions

Transitions that occurred while the app was in the background or terminated are no longer replayed via the `geofenceTransition` event once the first listener is registered. The event is now a **live feed** that only fires while your app is running.

Enable the queue and read it when your app becomes active instead:

```typescript
await Geofences.setConfig({ maxSize: 1000 });

let hasMore = true;
while (hasMore) {
  const result = await Geofences.getQueuedTransitions({ limit: 1000 });
  if (!result.transitions.length) break;
  await persist(result.transitions);
  await Geofences.deleteQueuedTransitions({
    upToId: result.transitions[result.transitions.length - 1].id,
  });
  hasMore = result.hasMore;
}
```

An app that only registers a listener keeps compiling and receives nothing after a cold start until it enables the queue and drains it.

### `GeofenceTransitionEvent` interface

The event payload is now nested under a `transition` property, the `id` property has been renamed to `geofenceId` and the `transitionType` property has been renamed to `type`.

### `configureSync(...)` and `disableSync()` methods

Both have been replaced by `setConfig(...)`, which configures the queue and the upload in one persisted, flat object that **replaces** the previous configuration entirely:

```typescript
await Geofences.setConfig({
  headers: { Authorization: 'Bearer eyJhbGciOi...' },
  maxSize: 1000,
  url: 'https://api.example.com/transitions',
});
```

Transitions are stored if `maxSize` or `url` is provided, and uploaded if `url` is provided. Call `resetConfig()` to stop storing and uploading transitions.

### `getConfig()` method

New method that returns the configuration that was last set. Only the properties that were provided are returned, so the result can be spread into `setConfig(...)` to change a single one:

```typescript
const config = await Geofences.getConfig();
await Geofences.setConfig({ ...config, maxSize: 5000 });
```

### `clearSyncQueue()` method

Renamed to `clearQueue()`.

### `getSyncStatus()` method

Renamed to `getQueueStatus()`. It returns `droppedCount`, `pendingCount` and `lastUploadedAt`, which was named `lastSyncedAt` before.

### `triggerSync()` method

Renamed to `triggerUpload()`.

### `syncFailed` event

Renamed to `uploadFailed`. The payload is unchanged. The plugin no longer uses the term "sync" anywhere, because the upload is a one-way delivery that ignores the response body:

```typescript
await Geofences.addListener('uploadFailed', event => {
  console.error('Upload failed: ', event.statusCode, event.message);
});
```

### Upload request body

Each uploaded transition now carries `id` instead of `uuid` as its deduplication key, and the geofence identifier is `geofenceId` instead of `id`. Existing servers need to be updated.

### Upload response handling

A `401` response no longer drops the transitions. They are retried, so an expired token can be replaced with `setConfig(...)` and the queued transitions are uploaded afterwards.

### Stored transitions

Transitions that were buffered by an earlier version are **not** carried over and can no longer be read.
