# E2E test helpers

## `fixtures/bundle.zip`

A minimal valid zip used as a live-update bundle in e2e flows.

- Real SHA-256: `f78569c2b774f49b3abeb58e275b646f31f8c3988312d6d3caea8b10e69a23e1`

The Maestro flow `.maestro/download-checksum-mismatch.yaml` passes a different checksum on purpose to assert that the plugin fails with `Checksum mismatch.`.

## `mock-server.js`

Tiny zero-dependency HTTP server that serves `fixtures/bundle.zip` on `GET /bundle.zip`. Listens on port `4000`.

Both emulator and simulator reach it at `http://localhost:4000`. The Android emulator relies on `adb reverse tcp:4000 tcp:4000` (run automatically by `npm run e2e:android`); the iOS simulator uses the host's network directly.
