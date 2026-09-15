# Changelog

## 0.1.2

### Patch Changes

- [`199280c219b257841100e6d323d828c684ce35ef`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/199280c219b257841100e6d323d828c684ce35ef) ([#613](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/613)): fix(android): run a transfer that starts while the app is in the background without the foreground service instead of failing it

- [`42e8df2f26bfb79e16e222a81a30ade24f29028d`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/42e8df2f26bfb79e16e222a81a30ade24f29028d) ([#612](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/612)): fix(android): send an empty body for `POST` downloads instead of failing with `NETWORK_ERROR`

- [`199280c219b257841100e6d323d828c684ce35ef`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/199280c219b257841100e6d323d828c684ce35ef) ([#613](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/613)): fix(android): prevent `resumeTransferById(...)` from starting a second worker for a transfer whose resume is still queued

- [`199280c219b257841100e6d323d828c684ce35ef`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/199280c219b257841100e6d323d828c684ce35ef) ([#613](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/613)): fix(ios): mark a transfer whose task was canceled by the OS as `failed` instead of leaving it `running`

## 0.1.1

### Patch Changes

- [`d31e7c2b33eeabfaf0635b84b39d069c549bf50c`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d31e7c2b33eeabfaf0635b84b39d069c549bf50c) ([#555](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/555)): fix(ios): remove the SPM test target that made the published package unresolvable by SwiftPM

## 0.1.0

### Minor Changes

- [`d730357dccdf6363e58ec478e4ed98bcdbc2565a`](https://github.com/capawesome-team/capacitor-plugins-sponsorware/commit/d730357dccdf6363e58ec478e4ed98bcdbc2565a) ([#491](https://github.com/capawesome-team/capacitor-plugins-sponsorware/pull/491)): Initial release 🎉
