# Changelog

## 0.1.2

### Patch Changes

- [`f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74) ([#564](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/564)): fix(android): register operation ids atomically

- [`f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74) ([#564](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/564)): fix(android): clamp negative `limit` and `offset` in `ReadDirectoryOptions` to match the iOS behavior

- [`6e8977e21b0485e7e874c417bae6436c2048bcc8`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/6e8977e21b0485e7e874c417bae6436c2048bcc8) ([#561](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/561)): fix(android): reject invalid document provider entry names in `copyDirectory(...)` and `moveDirectory(...)`

- [`ed8b4d52a2d141ff51b68f7af650ae385f58ebcc`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/ed8b4d52a2d141ff51b68f7af650ae385f58ebcc) ([#565](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/565)): fix(android): release a persisted directory grant only when the document no longer exists

- [`f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74) ([#564](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/564)): fix: reject `copyFile(...)` when `toUri` is the same as `uri` instead of truncating the file

- [`f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74) ([#564](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/564)): fix(android): settle the call with `OPERATION_FAILED` when an `Error` escapes an operation

- [`f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f5bde2a4fbedf1e8f0983d8a560fa66ff4a0cb74) ([#564](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/564)): fix(android): skip document provider rows without a display name in `listChildren(...)`

## 0.1.1

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.1.0

### Minor Changes

- [`d730357dccdf6363e58ec478e4ed98bcdbc2565a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d730357dccdf6363e58ec478e4ed98bcdbc2565a) ([#491](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/491)): Initial release 🎉
