# E2E test helpers

## `fixtures/bundle.zip`

A minimal valid zip used as a live-update bundle in e2e flows.

- Real SHA-256: `f78569c2b774f49b3abeb58e275b646f31f8c3988312d6d3caea8b10e69a23e1`

The Maestro flow `.maestro/download-checksum-mismatch.yaml` passes a different checksum on purpose to assert that the plugin fails with `Checksum mismatch.`.

## `mock-server.mjs`

Zero-dependency HTTP server exposing `createMockServer(...)`.

- **Standalone (mobile):** `node test/mock-server.mjs` serves `fixtures/bundle.zip` on `GET /bundle.zip`, port `4000` — the behaviour the Maestro flows rely on. Both emulator and simulator reach it at `http://localhost:4000` (the Android emulator relies on `adb reverse tcp:4000 tcp:4000`, run automatically by `npm run e2e:android`; the iOS simulator uses the host's network directly).
- **As a module (Electron e2e):** `createMockServer({ registry, fixturesDir })` additionally implements the Capawesome Cloud endpoints the live-update engine calls: `GET /v1/apps/:appId/bundles/latest`, `GET /v1/apps/:appId/channels`, zip downloads (`GET /download/:file`) and `manifest` delta downloads (`GET /manifest/:id?href=`). `POST /__control` mutates the served bundle per channel and toggles channel discovery at runtime.

## Electron e2e (`test/electron/`)

`npm run e2e:electron` builds the web app, syncs the `@capawesome/capacitor-electron` platform, compiles the Electron main process, builds signed bundle fixtures (`support/build-fixtures.mjs`), and runs the Playwright spec (`live-update.spec.mjs`) which drives the built Electron app via `electron.launch`. Each scenario rewrites `electron/generated/capacitor.config.json` (server domain, public key, `readyTimeout`) and launches with an isolated `--user-data-dir`, so a single build covers happy path, checksum/signature verification, kill-safe rollback, channel switching, `fetchChannels`, and manifest delta updates.
