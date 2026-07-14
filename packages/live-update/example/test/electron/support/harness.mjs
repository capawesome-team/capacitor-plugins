import { _electron as electron } from 'playwright';
import electronPath from 'electron';
import { mkdtempSync, readFileSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

import { createMockServer } from '../../mock-server.mjs';

const HERE = dirname(fileURLToPath(import.meta.url));
export const EXAMPLE_DIR = join(HERE, '..', '..', '..');
export const ELECTRON_APP_DIR = join(EXAMPLE_DIR, 'electron');
export const GENERATED_CONFIG_PATH = join(
  ELECTRON_APP_DIR,
  'generated',
  'capacitor.config.json',
);
export const FIXTURES_DIR = join(HERE, '..', '.fixtures');
export const REGISTRY_PATH = join(FIXTURES_DIR, 'registry.json');

export const loadRegistry = () =>
  JSON.parse(readFileSync(REGISTRY_PATH, 'utf8'));

export const startMockServer = registry =>
  new Promise(resolve => {
    const server = createMockServer({ registry, fixturesDir: FIXTURES_DIR });
    server.listen(0, '127.0.0.1', () => {
      const { port } = server.address();
      const origin = `http://localhost:${port}`;
      const control = async patch => {
        const response = await fetch(`${origin}/__control`, {
          method: 'POST',
          body: JSON.stringify(patch),
        });
        if (!response.ok) {
          throw new Error(`Mock control failed: ${response.status}`);
        }
      };
      resolve({
        origin,
        serverDomain: `localhost:${port}`,
        control,
        close: () => new Promise(done => server.close(done)),
      });
    });
  });

export const writeLiveUpdateConfig = liveUpdate => {
  const config = JSON.parse(readFileSync(GENERATED_CONFIG_PATH, 'utf8'));
  config.plugins = config.plugins ?? {};
  config.plugins.LiveUpdate = {
    appId: '46d641f5-2703-4e99-b498-006192c70484',
    ...liveUpdate,
  };
  writeFileSync(GENERATED_CONFIG_PATH, JSON.stringify(config, null, 2));
};

export const freshUserDataDir = () =>
  mkdtempSync(join(tmpdir(), 'lu-electron-e2e-'));

export const launch = async userDataDir => {
  const app = await electron.launch({
    executablePath: electronPath,
    args: [ELECTRON_APP_DIR, `--user-data-dir=${userDataDir}`],
  });
  const page = await app.firstWindow();
  await page.waitForFunction(
    () => !!window.Capacitor?.Plugins?.LiveUpdate,
    null,
    { timeout: 30000 },
  );
  return { app, page };
};

export const callPlugin = (page, method, options) =>
  page.evaluate(
    ({ method, options }) => window.Capacitor.Plugins.LiveUpdate[method](options),
    { method, options },
  );

export const callPluginExpectError = (page, method, options) =>
  page.evaluate(
    ({ method, options }) =>
      window.Capacitor.Plugins.LiveUpdate[method](options).then(
        () => ({ ok: true }),
        error => ({ ok: false, message: error.message, code: error.code }),
      ),
    { method, options },
  );

export const activeBundleVersion = page =>
  page.evaluate(
    () =>
      document.querySelector('meta[name="bundle-version"]')?.content ?? null,
  );

export const triggerReload = page =>
  page.evaluate(() => {
    // Fire-and-forget: the window reloads before `reload()` resolves.
    void window.Capacitor.Plugins.LiveUpdate.reload();
  });

export const waitForBundle = (page, version) =>
  page.waitForFunction(
    expected =>
      document.querySelector('meta[name="bundle-version"]')?.content ===
        expected && !!window.Capacitor?.Plugins?.LiveUpdate,
    version,
    { timeout: 30000 },
  );
