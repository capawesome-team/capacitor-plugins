# Breaking Changes

This is a comprehensive list of the breaking changes introduced in the major version releases.

## Versions

- [Version 8.x.x](#version-8xx)
- [Version 6.x.x](#version-6xx)

## Version 8.x.x

### Capacitor 8

This plugin now supports **Capacitor 8**. The minimum Android SDK version is **24** and the iOS deployment target is **15.0**. Ensure your project meets these requirements before upgrading.

### Variables

- On Android, the `androidxDocumentFileVersion` variable has been updated to `1.1.0`.

### `CompressImageResult`

On Android, the `path` property returned in the `CompressImageResult` object is now a URI string instead of an absolute file path. This change was made to ensure consistent behavior across different Capacitor plugins.

| Before                                            | After                                                    |
| ------------------------------------------------- | -------------------------------------------------------- |
| `/data/user/0/com.example.plugin/cache/test.jpeg` | `file:///data/user/0/com.example.plugin/cache/test.jpeg` |

## Version 6.x.x

### Capacitor 6

This plugin now only supports Capacitor 6.
