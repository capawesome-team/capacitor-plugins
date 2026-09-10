# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the different releases.

## Versions

- [Version 0.2.x](#version-02x)

## Version 0.2.x

### `maplibre-gl` dependency

`maplibre-gl` is now an optional peer dependency instead of a dependency. If you use the plugin on the Web platform, you must install it yourself:

```bash
npm install @capawesome/capacitor-maplibre maplibre-gl
```
