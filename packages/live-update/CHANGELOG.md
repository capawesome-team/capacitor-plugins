# Changelog

## 7.2.1

### Patch Changes

- [`66f4cb8a2f76582c8d8bda8df356800232d7c018`](https://github.com/capawesome-team/capacitor-plugins/commit/66f4cb8a2f76582c8d8bda8df356800232d7c018) ([#557](https://github.com/capawesome-team/capacitor-plugins/pull/557)): fix: missing bundle files in the asset folder are now downloaded

## 7.2.0

### Minor Changes

- [`2c5d6ab30e8f95b4cc0a69814539aff8ee530805`](https://github.com/capawesome-team/capacitor-plugins/commit/2c5d6ab30e8f95b4cc0a69814539aff8ee530805): feat: add `removeAllListeners()` method

## 7.1.1

### Patch Changes

- [`1bce8e114fdeffc0160c49e0e4da591fdc61d7d1`](https://github.com/capawesome-team/capacitor-plugins/commit/1bce8e114fdeffc0160c49e0e4da591fdc61d7d1) ([#454](https://github.com/capawesome-team/capacitor-plugins/pull/454)): chore(ios): allow all versions of Alamofire up to the next major release

## 7.1.0

### Minor Changes

- [`083233300c173475340874f00ba19afd0f72eb8a`](https://github.com/capawesome-team/capacitor-plugins/commit/083233300c173475340874f00ba19afd0f72eb8a) ([#435](https://github.com/capawesome-team/capacitor-plugins/pull/435)): feat: support code signing for self-hosted bundles

## 7.0.0

### Major Changes

- [`a8a9e276492df2176b327c6eedae5325ea0442ea`](https://github.com/capawesome-team/capacitor-plugins/commit/a8a9e276492df2176b327c6eedae5325ea0442ea) ([#402](https://github.com/capawesome-team/capacitor-plugins/pull/402)): feat: update to Capacitor 7

- [`48f35a31bdcddd8a861fe1c28c991d67ce4cf986`](https://github.com/capawesome-team/capacitor-plugins/commit/48f35a31bdcddd8a861fe1c28c991d67ce4cf986): refactor: disable automatic rollbacks by default (see `BREAKING.md`)

- [`56367d5e0deec242e0502bdeb9ea0fdae5947cc7`](https://github.com/capawesome-team/capacitor-plugins/commit/56367d5e0deec242e0502bdeb9ea0fdae5947cc7) ([#242](https://github.com/capawesome-team/capacitor-plugins/pull/242)): feat: implement return type `ReadyResult`

- [`80487793c0f452339289ac11f310e26d63e3abc6`](https://github.com/capawesome-team/capacitor-plugins/commit/80487793c0f452339289ac11f310e26d63e3abc6) ([#390](https://github.com/capawesome-team/capacitor-plugins/pull/390)): refactor: remove deprecated code (see `BREAKING.md`)

- [`f90c1b92e6d371801b36fb36e0c0a9123343afd7`](https://github.com/capawesome-team/capacitor-plugins/commit/f90c1b92e6d371801b36fb36e0c0a9123343afd7) ([#404](https://github.com/capawesome-team/capacitor-plugins/pull/404)): refactor: remove the `enabled` and `resetOnUpdate` configuration options

### Minor Changes

- [`0dc14929935941bf71e1871d8ad7af027cff3fee`](https://github.com/capawesome-team/capacitor-plugins/commit/0dc14929935941bf71e1871d8ad7af027cff3fee): feat: add `serverDomain` configuration option

- [`fdbcd817f6ab89525149685045f6ea7b78606b45`](https://github.com/capawesome-team/capacitor-plugins/commit/fdbcd817f6ab89525149685045f6ea7b78606b45) ([#382](https://github.com/capawesome-team/capacitor-plugins/pull/382)): feat: add SPM support

- [`e5258d03572e5c732de5eadc788a17b9a1b6ec3e`](https://github.com/capawesome-team/capacitor-plugins/commit/e5258d03572e5c732de5eadc788a17b9a1b6ec3e) ([#383](https://github.com/capawesome-team/capacitor-plugins/pull/383)): feat: add `customProperties` property to `FetchLatestBundleResult`

- [`c9156f7a3331a4c4db4152d7c9abe4311d4a8d05`](https://github.com/capawesome-team/capacitor-plugins/commit/c9156f7a3331a4c4db4152d7c9abe4311d4a8d05) ([#399](https://github.com/capawesome-team/capacitor-plugins/pull/399)): feat: add `downloadBundleProgress` listener

## 6.7.1

### Patch Changes

- [`fac67aa`](https://github.com/capawesome-team/capacitor-plugins/commit/fac67aa8c0d16c36716428b28853cded892453d2): fix: set correct plugin version

## 6.7.0

### Minor Changes

- [`a23b782`](https://github.com/capawesome-team/capacitor-plugins/commit/a23b782d5163fb205bb53f8ebad91c792dfa15ce) ([#368](https://github.com/capawesome-team/capacitor-plugins/pull/368)): feat: add `artifactType` property to `FetchLatestBundleResult`

* [`233889d`](https://github.com/capawesome-team/capacitor-plugins/commit/233889da605192870a0110ce60a402445e5dbc54) ([#364](https://github.com/capawesome-team/capacitor-plugins/pull/364)): feat: add `getCurrentBundle()` and `getNextBundle()` methods

- [`cdc1fdb`](https://github.com/capawesome-team/capacitor-plugins/commit/cdc1fdb00b10c2d3ff263d9de7922e0d387dc9df) ([#361](https://github.com/capawesome-team/capacitor-plugins/pull/361)): feat: add `channel` option to `sync()` and `fetchLatestBundle()` methods

* [`f7b45ac`](https://github.com/capawesome-team/capacitor-plugins/commit/f7b45ac8a8449acd9d3fac9c8853137317708144) ([#363](https://github.com/capawesome-team/capacitor-plugins/pull/363)): feat: add `downloadUrl` property to `FetchLatestBundleResult`

- [`685e0c6`](https://github.com/capawesome-team/capacitor-plugins/commit/685e0c61ae064ab5953c30e6f7281d28865e1884) ([#367](https://github.com/capawesome-team/capacitor-plugins/pull/367)): feat: add `setNextBundle()` method

### Patch Changes

- [`e58efa3`](https://github.com/capawesome-team/capacitor-plugins/commit/e58efa3be0e3fa6fec787208c4c18cdf2cd989ad): fix(ios): some plugin calls were resolved too early

## 6.6.0

### Minor Changes

- [`0f4f2fa`](https://github.com/capawesome-team/capacitor-plugins/commit/0f4f2fa40318ad82e14b5bf14f7dd70eec7bbe93) ([#311](https://github.com/capawesome-team/capacitor-plugins/pull/311)): feat: add `fetchLatestBundle()` method

* [`626a01d`](https://github.com/capawesome-team/capacitor-plugins/commit/626a01db4d6c6698c3069a9c2f65465545ae8400) ([#315](https://github.com/capawesome-team/capacitor-plugins/pull/315)): feat: add `artifactType` option to `downloadBundle(...)` method

## 6.5.0

### Minor Changes

- [`afc536c`](https://github.com/capawesome-team/capacitor-plugins/commit/afc536cd4f3b829aa4dc8f68b0b3b5a30abcbbbe) ([#305](https://github.com/capawesome-team/capacitor-plugins/pull/305)): feat: support delta updates

## 6.4.2

### Patch Changes

- [`de9144e`](https://github.com/capawesome-team/capacitor-plugins/commit/de9144eee298e9e80535ebd30086391aad47fc68): fix(android): return `null` if no channel is set

## 6.4.1

### Patch Changes

- [`f2c5a79`](https://github.com/capawesome-team/capacitor-plugins/commit/f2c5a79c41baded4bb5be3939bebde9a72e37ce7) ([#283](https://github.com/capawesome-team/capacitor-plugins/pull/283)): fix: perform a reset when the current bundle is deleted

## 6.4.0

### Minor Changes

- [`4e4fcc0`](https://github.com/capawesome-team/capacitor-plugins/commit/4e4fcc0515f5b55280755766056c86eb2a7442a1) ([#277](https://github.com/capawesome-team/capacitor-plugins/pull/277)): feat: add configuration option `httpTimeout`

### Patch Changes

- [`f4df3ff`](https://github.com/capawesome-team/capacitor-plugins/commit/f4df3ffec7b9a18271be8e0596b8f864de127eff) ([#275](https://github.com/capawesome-team/capacitor-plugins/pull/275)): fix: throw an error if the sync is already in progress

* [`375c857`](https://github.com/capawesome-team/capacitor-plugins/commit/375c8574cf09a7053e35408177eb8b340a648e42) ([#279](https://github.com/capawesome-team/capacitor-plugins/pull/279)): fix: throw error if a http timeout occurs

## 6.3.0

### Minor Changes

- [`826955b`](https://github.com/capawesome-team/capacitor-plugins/commit/826955b5bd40bb17154b4182689ef1216402e2a2) ([#248](https://github.com/capawesome-team/capacitor-plugins/pull/248)): feat: add `defaultChannel` configuration option

### Patch Changes

- [`83f3f7f`](https://github.com/capawesome-team/capacitor-plugins/commit/83f3f7f3ff314a1c69f5280848262bfdb5afbd2d) ([#253](https://github.com/capawesome-team/capacitor-plugins/pull/253)): fix(ios): update possibly failed if more than one `index.html` file exists

## 6.2.0

### Minor Changes

- [`370095c`](https://github.com/capawesome-team/capacitor-plugins/commit/370095c63a28a2901efad611a74bb880d9c6bdc0) ([#232](https://github.com/capawesome-team/capacitor-plugins/pull/232)): feat: add `location` configuration option

## 6.1.0

### Minor Changes

- [`a4fe04d`](https://github.com/capawesome-team/capacitor-plugins/commit/a4fe04d8f225f892fc8da88816acf9cd41ff4acc) ([#229](https://github.com/capawesome-team/capacitor-plugins/pull/229)): feat: add support for checksum verification

* [`a4b1a06`](https://github.com/capawesome-team/capacitor-plugins/commit/a4b1a060eebe446427ed9ea5a600a271fdc6acfa) ([#231](https://github.com/capawesome-team/capacitor-plugins/pull/231)): feat: add support for signature verification

## 6.0.8

### Patch Changes

- [`bb4ba6d`](https://github.com/capawesome-team/capacitor-plugins/commit/bb4ba6df4631c2f903a6d9c65ec78c4477cc14ab): chore(ios): print sync response error message

* [`175ff86`](https://github.com/capawesome-team/capacitor-plugins/commit/175ff8620ff20f4cf04b291e11c826ad486c4b10) ([#216](https://github.com/capawesome-team/capacitor-plugins/pull/216)): fix: `reset()` followed by `reload()` did not load the built-in bundle

- [`6a92e93`](https://github.com/capawesome-team/capacitor-plugins/commit/6a92e93d98a23168ffd0a394b776c30aa42e7dbc) ([#218](https://github.com/capawesome-team/capacitor-plugins/pull/218)): chore: print debug url on sync

## 6.0.7

### Patch Changes

- [`6d44185`](https://github.com/capawesome-team/capacitor-plugins/commit/6d441858266e1dfc5e3e2606a0e71b30540a9742): fix(ios): bundle was reset after restarting the app

## 6.0.6

### Patch Changes

- [`b0a6473`](https://github.com/capawesome-team/capacitor-plugins/commit/b0a647325380973351512a9dae00db96f2fe4c16) ([#188](https://github.com/capawesome-team/capacitor-plugins/pull/188)): fix(ios): improve unzip performance

* [`843900b`](https://github.com/capawesome-team/capacitor-plugins/commit/843900bfbab3ec89f2289d0399e7bef1cba4a632) ([#189](https://github.com/capawesome-team/capacitor-plugins/pull/189)): fix: `setBundle` is not resolved

## 6.0.5

### Patch Changes

- [`a215553`](https://github.com/capawesome-team/capacitor-plugins/commit/a215553180d3c96b6d58cc3cecd537be4d0c6349) ([#183](https://github.com/capawesome-team/capacitor-plugins/pull/183)): fix(android): create bundles directory if it does not exist

## 6.0.4

### Patch Changes

- [`b4eaec8`](https://github.com/capawesome-team/capacitor-plugins/commit/b4eaec8b244b2df54f0b8a48eb6c7179f64c19dc): fix: convert the device id to lower case

* [`c0e84c2`](https://github.com/capawesome-team/capacitor-plugins/commit/c0e84c2461f6857907797b071c579dccfdd332ce) ([#172](https://github.com/capawesome-team/capacitor-plugins/pull/172)): fix(ios): use `identifierForVendor` to get a device id
