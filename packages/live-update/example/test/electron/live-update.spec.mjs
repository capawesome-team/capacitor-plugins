import { expect, test } from '@playwright/test';

import {
  activeBundleVersion,
  callPlugin,
  callPluginExpectError,
  freshUserDataDir,
  launch,
  loadRegistry,
  startMockServer,
  triggerReload,
  waitForBundle,
  writeLiveUpdateConfig,
} from './support/harness.mjs';

const APP_ID = '46d641f5-2703-4e99-b498-006192c70484';

let registry;
let mock;
const openApps = [];

test.beforeAll(async () => {
  registry = loadRegistry();
  mock = await startMockServer(registry);
});

test.afterAll(async () => {
  await mock.close();
});

test.afterEach(async () => {
  for (const app of openApps.splice(0)) {
    await app.close().catch(() => undefined);
  }
});

const boot = async ({ liveUpdate = {}, userDataDir }) => {
  writeLiveUpdateConfig({ serverDomain: mock.serverDomain, ...liveUpdate });
  const { app, page } = await launch(userDataDir);
  openApps.push(app);
  return { app, page };
};

test('happy path: sync -> reload -> new bundle active -> ready(rollback:false)', async () => {
  await mock.control({ latestByChannel: { default: '2.0.0' } });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0 },
    userDataDir: freshUserDataDir(),
  });

  // Default (packaged) bundle is active on first boot.
  expect(await activeBundleVersion(page)).toBeNull();
  expect((await callPlugin(page, 'getCurrentBundle')).bundleId).toBeNull();

  // Version + config APIs.
  expect((await callPlugin(page, 'getVersionName')).versionName).toBe('0.0.0');
  expect((await callPlugin(page, 'getVersionCode')).versionCode).toBe('0.0.0');
  const config = await callPlugin(page, 'getConfig');
  expect(config.appId).toBe(APP_ID);
  expect(config.autoUpdateStrategy).toBe('none');

  // setConfig / resetConfig are unimplemented on Electron.
  const setConfig = await callPluginExpectError(page, 'setConfig', {
    appId: null,
  });
  expect(setConfig.ok).toBe(false);
  expect(setConfig.code).toBe('UNIMPLEMENTED');
  const resetConfig = await callPluginExpectError(page, 'resetConfig');
  expect(resetConfig.ok).toBe(false);
  expect(resetConfig.code).toBe('UNIMPLEMENTED');

  // Sync downloads and stages the next bundle.
  const sync = await callPlugin(page, 'sync');
  expect(sync.nextBundleId).toBe('2.0.0');
  expect((await callPlugin(page, 'getNextBundle')).bundleId).toBe('2.0.0');
  expect((await callPlugin(page, 'getDownloadedBundles')).bundleIds).toContain(
    '2.0.0',
  );

  // Reload activates the new bundle.
  await triggerReload(page);
  await waitForBundle(page, '2.0.0');
  expect(await activeBundleVersion(page)).toBe('2.0.0');
  expect((await callPlugin(page, 'getCurrentBundle')).bundleId).toBe('2.0.0');

  // ready() confirms the update with no rollback.
  const ready = await callPlugin(page, 'ready');
  expect(ready.rollback).toBe(false);
  expect(ready.currentBundleId).toBe('2.0.0');
});

test('checksum mismatch is rejected', async () => {
  await mock.control({ latestByChannel: { default: '2.0.0-badsum' } });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0 },
    userDataDir: freshUserDataDir(),
  });

  const result = await callPluginExpectError(page, 'sync');
  expect(result.ok).toBe(false);
  expect(result.message).toContain('Checksum mismatch');
  expect((await callPlugin(page, 'getCurrentBundle')).bundleId).toBeNull();
});

test('signature verification: correct public key is accepted', async () => {
  await mock.control({ latestByChannel: { default: '2.0.0' } });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0, publicKey: registry.keys.publicKey },
    userDataDir: freshUserDataDir(),
  });

  const sync = await callPlugin(page, 'sync');
  expect(sync.nextBundleId).toBe('2.0.0');
});

test('signature verification: wrong public key is rejected', async () => {
  await mock.control({ latestByChannel: { default: 'sig-2.1.0' } });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0, publicKey: registry.keys.publicKey },
    userDataDir: freshUserDataDir(),
  });

  const result = await callPluginExpectError(page, 'sync');
  expect(result.ok).toBe(false);
  expect(result.message).toContain('Signature verification failed');
});

test('broken bundle: kill-safe rollback at next boot reports rollback:true and blocks the bundle', async () => {
  const userDataDir = freshUserDataDir();
  await mock.control({ latestByChannel: { default: '3.0.0' } });

  // Boot 1: activate 3.0.0 but never call ready(), then quit (simulated crash).
  {
    const { app, page } = await boot({
      liveUpdate: { readyTimeout: 60000, autoBlockRolledBackBundles: true },
      userDataDir,
    });
    const sync = await callPlugin(page, 'sync');
    expect(sync.nextBundleId).toBe('3.0.0');
    await triggerReload(page);
    await waitForBundle(page, '3.0.0');
    expect((await callPlugin(page, 'getCurrentBundle')).bundleId).toBe('3.0.0');
    await app.close();
    openApps.splice(openApps.indexOf(app), 1);
  }

  // Boot 2: same user data -> engine rolls back to the default bundle.
  {
    const { page } = await boot({
      liveUpdate: { readyTimeout: 60000, autoBlockRolledBackBundles: true },
      userDataDir,
    });
    expect(await activeBundleVersion(page)).toBeNull();
    expect((await callPlugin(page, 'getCurrentBundle')).bundleId).toBeNull();

    const ready = await callPlugin(page, 'ready');
    expect(ready.rollback).toBe(true);
    expect(ready.previousBundleId).toBe('3.0.0');

    const blocked = await callPlugin(page, 'getBlockedBundles');
    expect(blocked.bundleIds).toContain('3.0.0');

    // The blocked bundle is no longer offered by sync.
    const sync = await callPlugin(page, 'sync');
    expect(sync.nextBundleId).toBeNull();
  }
});

test('channel switching serves a channel-specific bundle and fetchChannels lists channels', async () => {
  await mock.control({
    latestByChannel: { default: '2.0.0', beta: 'beta-1.0.0' },
    channelsEnabled: true,
  });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0 },
    userDataDir: freshUserDataDir(),
  });

  const channels = await callPlugin(page, 'fetchChannels');
  expect(channels.channels.map(channel => channel.name)).toContain('beta');

  await callPlugin(page, 'setChannel', { channel: 'beta' });
  expect((await callPlugin(page, 'getChannel')).channel).toBe('beta');

  const sync = await callPlugin(page, 'sync');
  expect(sync.nextBundleId).toBe('beta-1.0.0');

  await triggerReload(page);
  await waitForBundle(page, 'beta-1.0.0');
  expect(await activeBundleVersion(page)).toBe('beta-1.0.0');
});

test('fetchChannels rejects when channel discovery is disabled', async () => {
  await mock.control({ channelsEnabled: false });
  const { page } = await boot({
    liveUpdate: { readyTimeout: 0 },
    userDataDir: freshUserDataDir(),
  });

  const result = await callPluginExpectError(page, 'fetchChannels');
  expect(result.ok).toBe(false);
  expect(result.message).toContain('Channel Discovery');

  await mock.control({ channelsEnabled: true });
});
