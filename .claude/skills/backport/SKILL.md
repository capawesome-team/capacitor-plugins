---
name: backport
description: Backport a change that is merged into main to a release branch such as v6.x.x, v7.x.x or v8.x.x. Cherry-picks the squash commit in a worktree, resolves branch differences, verifies, opens the PR and prepares the manual LTS release.
argument-hint: <pr-number> <target-branch> [more target branches]
allowed-tools: Read, Glob, Grep, Bash, AskUserQuestion
---

# Backport Skill

Bring a merged `main` PR to one or more release branches, one PR per branch, and cut the LTS release afterwards.

---

## Prerequisites

- The change is merged into `main` (squash merge, so one commit). Backport from the squash commit, never from the PR's individual commits.
- `gh` authenticated. Labels and PR conventions follow the `create-gh-pr` skill.

---

## Workflow

### 1. Prepare a worktree per branch

```
git fetch origin
SQ=$(git log origin/main --grep "#<pr>" --format=%H -1)
git worktree add -b <type>/<slug>-v<N> ../<repo>-worktrees/<package>-v<N> origin/v<N>.x.x
[ -f CLAUDE.md ] && cp CLAUDE.md ../<repo>-worktrees/<package>-v<N>/   # developer-local and gitignored on main; not ignored on every release branch, so stage files explicitly there
cd ../<repo>-worktrees/<package>-v<N> && npm ci && npx patch-package
```

### 2. Cherry-pick and resolve

`git cherry-pick $SQ`. Expect conflicts where the branch's surrounding code differs; see `references/branch-differences.md` for the known ones. Rules for resolving:

- Keep the branch's structure and APIs. Never pull newer `main` refactors along.
- Place new calls at the equivalent position, for example before a conditional block that `main` no longer has.
- Finish with `GIT_EDITOR=true git cherry-pick --continue` so the commit message keeps the `(#<pr>)` reference.
- Afterwards grep the package for identifiers the change removed or renamed; none may remain.

### 3. Verify

In the package directory: `npx prettier --check` on the changed Java files, `npx node-swiftlint lint ios/Plugin` (only new violation types matter), `npm run verify:android`, `npm run verify:ios`. For `live-update`, run the core runtime flows of the `test-live-update` skill on both devices as well; the worktree's example app needs its own `npm install` and its config pointed at the test app before `npx cap run`.

### 4. Open the PR

Push the branch and open the PR against `v<N>.x.x` with the original title, the original labels, and this body:

```
Backport of #<pr> to `v<N>.x.x`.

<the original PR description>
```

Release-branch CI may pin toolchains that no longer exist on the runners. If a job fails during setup rather than in the build, fix the pin in the same PR.

### 5. Release (release branches only)

Always ask with `AskUserQuestion` before starting this phase, even when the backport PR is already merged. More changes may be waiting to be backported first, or the release may need to wait for another reason.

The release workflow runs on `main` only and nothing consumes changeset files on release branches, so releases there are manual. After the backport PR is merged and the user confirmed the release:

1. On the branch, in the package directory: `npx changeset version` then `npm run version`. The second command syncs the native version constants that are sent to the API.
2. Check every generated CHANGELOG line against the branch history. Manual releases leave stale changeset files behind that describe already shipped fixes, and merged changes may lack one. Drop stale entries, hand-write missing ones in the generator's format, keep the stale files deleted.
3. Follow the branch's precedent for the lockfiles. Open the release PR as `chore(<package>): release <version>`.
4. After merge, from the package directory: `npm ci && npm run build`, then `npm publish --tag v<N>-lts --access public`. The tag is mandatory; a plain publish moves `latest`. Verify with `npm view <package> dist-tags`.
5. `git tag <package>@<version>`, push the tag, and `gh release create` with the CHANGELOG section as notes.

---

## Constraints

- Never dispatch the release workflow on a release branch.
- Never amend or force-push. Remove the worktree and its local branches when the release is done.
