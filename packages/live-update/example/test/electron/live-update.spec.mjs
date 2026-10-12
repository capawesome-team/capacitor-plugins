import { expect, test } from '@playwright/test';

import {
  activeBundleVersion,
  callPlugin,
  createUserDataDir,
  launch,
  reloadToBundle,
  startMockServer,
  triggerReload,
  writeLiveUpdateConfig,
} from './support/harness.mjs';
import {
  BROKEN_BUNDLE_ID,
  BROKEN_BUNDLE_MARKER_ID,
  BROKEN_BUNDLE_ZIP_PATH,
  BUNDLE_ID,
} from './support/paths.mjs';

let mockServer;

test.beforeAll(async () => {
  mockServer = await startMockServer();
});

test.afterAll(async () => {
  await mockServer.close();
});

test('sync, reload and ready apply the new bundle', async () => {
  writeLiveUpdateConfig({ serverDomain: mockServer.serverDomain });
  const { app, page } = await launch(createUserDataDir());

  expect(await activeBundleVersion(page)).toBeNull();
  expect((await callPlugin(page, 'sync')).nextBundleId).toBe(BUNDLE_ID);
  await reloadToBundle(page, BUNDLE_ID);
  const ready = await callPlugin(page, 'ready');
  expect(ready.currentBundleId).toBe(BUNDLE_ID);
  expect(ready.rollback).toBe(false);

  await app.close();
});

test('rolls back on the next start if the app was closed before ready', async () => {
  writeLiveUpdateConfig({
    readyTimeout: 60000,
    serverDomain: mockServer.serverDomain,
  });
  const userDataDir = createUserDataDir();

  const firstRun = await launch(userDataDir);
  await callPlugin(firstRun.page, 'sync');
  await reloadToBundle(firstRun.page, BUNDLE_ID);
  await firstRun.app.close();

  const secondRun = await launch(userDataDir);
  expect(await activeBundleVersion(secondRun.page)).toBeNull();
  const ready = await callPlugin(secondRun.page, 'ready');
  expect(ready.previousBundleId).toBe(BUNDLE_ID);
  expect(ready.rollback).toBe(true);

  await secondRun.app.close();
});

test('rolls back to the default bundle when the app does not signal readiness', async () => {
  const readyTimeout = 3000;
  const brokenBundleServer = await startMockServer({
    bundleId: BROKEN_BUNDLE_ID,
    zipPath: BROKEN_BUNDLE_ZIP_PATH,
  });
  writeLiveUpdateConfig({
    autoBlockRolledBackBundles: true,
    readyTimeout,
    serverDomain: brokenBundleServer.serverDomain,
  });
  const { app, page } = await launch(createUserDataDir());
  const marker = page.locator(`#${BROKEN_BUNDLE_MARKER_ID}`);

  await callPlugin(page, 'sync');
  await triggerReload(page);
  await expect(marker).toBeVisible();

  await expect(marker).toBeHidden({ timeout: readyTimeout + 5000 });
  // Wait for the rolled back page's scripts to run before checking again.
  await page.waitForLoadState();
  await expect(marker).toBeHidden();
  const ready = await callPlugin(page, 'ready');
  expect(ready.currentBundleId).toBeNull();
  expect(ready.rollback).toBe(true);
  const blocked = await callPlugin(page, 'getBlockedBundles');
  expect(blocked.bundleIds).toContain(BROKEN_BUNDLE_ID);
  expect((await callPlugin(page, 'sync')).nextBundleId).toBeNull();

  await app.close();
  await brokenBundleServer.close();
});
