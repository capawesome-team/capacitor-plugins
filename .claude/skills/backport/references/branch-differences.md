# Known differences between main and the release branches

Observed while backporting `live-update` changes. Extend this list with every backport; remove entries once the difference itself is backported.

## v6.x.x

- Init on both platforms wraps the rollback timer in `if config.enabled { ... }` together with the `wasUpdated` and `resetOnUpdate` handling. New startup calls go before that block.
- `CLAUDE.md` is not gitignored: never `git add -A`.
- The example config is `capacitor.config.json` and ships with a `publicKey`; remove it for unsigned test bundles.
- Release commits update the root lockfile's workspace version line.
- `readyTimeout` defaults to 10000 ms (`main`: 0), so tests must call `ready()` after a reload.

## v7.x.x

- The root lockfile's workspace entry is stale and release commits leave it that way.

## All release branches

- `release.yml` triggers only on `main`; publishing is manual with the `v<N>-lts` dist-tag.
- Stale changeset files from long-shipped fixes can be present; check every `changeset version` line against the branch history.
- The example app needs its own `npm install` in the worktree, and every `npx cap run` bakes the config.
- CI may pin toolchains that left the runner image; fix the pin in the backport PR.
