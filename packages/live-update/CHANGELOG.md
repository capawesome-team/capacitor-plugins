# Changelog

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
