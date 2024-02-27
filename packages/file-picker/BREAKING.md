# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases of Capacitor File Picker plugin.

## Versions

- [Version 5.x.x](#version-5xx)

## Version 6.x.x

### Replace the `multiple` option

The `multiple` option has been replaced by a new `limit` option in the `pickFiles(...)`, `pickImages(...)`, `pickMedia(...)` and `pickVideos(...)` methods.

## Version 5.x.x

### Capacitor 5

This plugin now supports Capacitor 5 only. Please run `npx cap sync` after updating this package.

If you want to use this plugin with Capacitor 4, please install version `0.6.3`:

```
npm i @capawesome/capacitor-file-picker@0.6.3
```
