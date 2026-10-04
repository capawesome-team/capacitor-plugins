import { _electron as electron } from '@playwright/test';
import electronPath from 'electron';
import { createHash } from 'node:crypto';
import { mkdtempSync, readFileSync, writeFileSync } from 'node:fs';
import { createServer } from 'node:http';
import { tmpdir } from 'node:os';
import { join } from 'node:path';

import { BUNDLE_ID, BUNDLE_ZIP_PATH, ELECTRON_APP_DIR } from './paths.mjs';

const GENERATED_CONFIG_PATH = join(
  ELECTRON_APP_DIR,
  'generated',
  'capacitor.config.json',
);

/**
 * Minimal Capawesome Cloud API mock that always offers the fixture bundle.
 */
export const startMockServer = () =>
  new Promise(resolve => {
    const zip = readFileSync(BUNDLE_ZIP_PATH);
    const checksum = createHash('sha256').update(zip).digest('hex');
    const server = createServer((req, res) => {
      const { port } = server.address();
      if (
        req.url.startsWith('/v1/apps/') &&
        req.url.includes('/bundles/latest')
      ) {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(
          JSON.stringify({
            artifactType: 'zip',
            bundleId: BUNDLE_ID,
            checksum,
            url: `http://localhost:${port}/bundle.zip`,
          }),
        );
        return;
      }
      if (req.url === '/bundle.zip') {
        res.writeHead(200, { 'Content-Type': 'application/zip' });
        res.end(zip);
        return;
      }
      res.writeHead(404);
      res.end();
    });
    server.listen(0, '127.0.0.1', () => {
      resolve({
        serverDomain: `localhost:${server.address().port}`,
        close: () => new Promise(done => server.close(done)),
      });
    });
  });

export const writeLiveUpdateConfig = liveUpdate => {
  const config = JSON.parse(readFileSync(GENERATED_CONFIG_PATH, 'utf8'));
  config.plugins = {
    ...config.plugins,
    LiveUpdate: {
      appId: '46d641f5-2703-4e99-b498-006192c70484',
      ...liveUpdate,
    },
  };
  writeFileSync(GENERATED_CONFIG_PATH, JSON.stringify(config, null, 2));
};

export const createUserDataDir = () =>
  mkdtempSync(join(tmpdir(), 'live-update-electron-e2e-'));

export const launch = async userDataDir => {
  const app = await electron.launch({
    executablePath: electronPath,
    args: [ELECTRON_APP_DIR, `--user-data-dir=${userDataDir}`],
  });
  // Skip the splash screen window.
  const isAppWindow = window => window.url().startsWith('capacitor-electron:');
  const page =
    app.windows().find(isAppWindow) ??
    (await app.waitForEvent('window', { predicate: isAppWindow }));
  await page.waitForFunction(() => !!window.Capacitor?.Plugins?.LiveUpdate);
  return { app, page };
};

export const callPlugin = (page, method, options) =>
  page.evaluate(
    ({ method, options }) =>
      window.Capacitor.Plugins.LiveUpdate[method](options),
    { method, options },
  );

export const activeBundleVersion = page =>
  page.evaluate(
    () =>
      document.querySelector('meta[name="bundle-version"]')?.content ?? null,
  );

export const reloadToBundle = async (page, bundleId) => {
  // Fire-and-forget: the window reloads before `reload()` resolves.
  await page.evaluate(() => {
    void window.Capacitor.Plugins.LiveUpdate.reload();
  });
  await page.waitForFunction(
    expected =>
      document.querySelector('meta[name="bundle-version"]')?.content ===
        expected && !!window.Capacitor?.Plugins?.LiveUpdate,
    bundleId,
  );
};
