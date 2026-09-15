# Changelog

## 0.3.0

### Minor Changes

- [`c62a0866be52f16e4badac0c35297ce837439b63`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/c62a0866be52f16e4badac0c35297ce837439b63) ([#629](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/629)): feat!: remove the `androidExpirationDuration` option; geofences no longer expire automatically (see `BREAKING.md`)

### Patch Changes

- [`f92b2a280362c0b802531818f10f03edecf8b642`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/f92b2a280362c0b802531818f10f03edecf8b642) ([#611](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/611)): fix(ios): reject `addGeofences(...)` if a geofence could not be registered, settle the call on every failure and keep previously monitored regions

- [`18cfa11e390b4a822a271d056fa65f7080f44fb1`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/18cfa11e390b4a822a271d056fa65f7080f44fb1) ([#610](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/610)): fix(android): make `removeAllGeofences()` remove every registered geofence even when the store is unreadable

- [`18cfa11e390b4a822a271d056fa65f7080f44fb1`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/18cfa11e390b4a822a271d056fa65f7080f44fb1) ([#610](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/610)): fix(android): skip unreadable geofences instead of failing every read of the store

## 0.2.1

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.2.0

### Minor Changes

- [`49627f959ebc811974cfec03fbbf5b01bcb12974`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/49627f959ebc811974cfec03fbbf5b01bcb12974) ([#542](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/542)): feat!: add a durable transition queue that survives app termination (see `BREAKING.md`)

## 0.1.0

### Minor Changes

- [`d730357dccdf6363e58ec478e4ed98bcdbc2565a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d730357dccdf6363e58ec478e4ed98bcdbc2565a) ([#491](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/491)): Initial release 🎉
