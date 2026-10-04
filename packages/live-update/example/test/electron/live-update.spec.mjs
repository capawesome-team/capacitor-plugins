import { expect, test } from '@playwright/test';

import {
  activeBundleVersion,
  callPlugin,
  createUserDataDir,
  launch,
  reloadToBundle,
  startMockServer,
  writeLiveUpdateConfig,
} from './support/harness.mjs';
import { BUNDLE_ID } from './support/paths.mjs';

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
