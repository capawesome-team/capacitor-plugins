# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.4.x](#version-04x)
- [Version 0.3.x](#version-03x)
- [Version 0.2.x](#version-02x)

## Version 0.4.x

### SQLite.swift

The minimum required version of `SQLite.swift` on iOS has been updated from `0.15.4` to `0.15.5`.

### `SQLiteDBConnection.getVersion()`

The method now returns `{ version }` with the schema version of the database (`PRAGMA user_version`) instead of the version string of the SQLite library.

### `SQLiteDBConnection.execute(...)`

Every statement of the script is executed. Previously only the first statement ran on Android and iOS. A script with more than one statement runs in a transaction unless `transaction: false` is passed, so a script that is executed inside an explicit transaction, or that contains its own `BEGIN` and `COMMIT`, must pass `transaction: false`. `run(...)` rejects on a read-only connection, and the transaction methods reject on a closed connection.

### Read-only connections with a `version` (Android)

`open({ readOnly: true, version })` and a read-only `SQLiteDBConnection` now reject when the stored `user_version` of the database is lower than `version`, because the upgrade cannot be written. Previously Android ignored `readOnly` in this case, ran the upgrade and returned a writable connection. Open the database writable once to apply the upgrade, then open it read-only. `SQLiteDBConnection.open()` always sends its `version` (default `1`), so a read-only connection to a database that has never been opened with a version must be opened writable once.

## Version 0.3.x

### Electron Native Support

The plugin now uses native SQLite for Electron via the `node:sqlite` module. This change improves performance and reliability when using the plugin in Electron applications. Please note that this change may require you to migrate your existing databases if you were previously using the web implementation which relied on SQLite WASM and OPFS (Origin Private File System).

### `@sqlite.org/sqlite-wasm`

The minimum required version of `@sqlite.org/sqlite-wasm` is now `3.51.2-build2`. This version includes a new distribution structure that requires the following changes:

- The asset input path has changed from `node_modules/@sqlite.org/sqlite-wasm/sqlite-wasm/jswasm/` to `node_modules/@sqlite.org/sqlite-wasm/dist/`.
- The worker file has been renamed from `sqlite3-worker1-bundler-friendly.mjs` to `sqlite3-worker1.mjs`.

## Version 0.2.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### SQLite WASM Dependency

The `@sqlite.org/sqlite-wasm` dependency version is now restricted to `3.50.3-build1` or lower. Versions higher than `3.50.3-build1` are not supported due to an open bug report (see [sqlite/sqlite-wasm#123](https://github.com/sqlite/sqlite-wasm/issues/123)).

### Variables

- On Android, the `androidxSqliteVersion` variable has been updated to `2.6.2`.
- On Android, the `androidxSqliteFrameworkAndroidVersion` variable has been updated to `2.6.2`.
- On Android, the `netZeteticSqlcipherVersion` variable has been updated to `4.12.0`.

### Database Version Management

The default behavior for setting the database version has changed. If neither `version` nor `upgradeStatements` are provided when opening a database, no version will be set. This allows for greater flexibility in managing database versions manually. Previously, if you did not specify a `version` when opening a database, the default version was set to `1`.
