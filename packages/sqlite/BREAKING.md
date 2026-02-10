# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 0.3.x](#version-03x)
- [Version 0.2.x](#version-02x)

## Version 0.3.x

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
