/**
 * Builds the live update bundles served by the mock Cloud API, each a copy of
 * the built web app (`dist/`) zipped to `.fixtures/`:
 *
 * - `bundle.zip` adds a `<meta name="bundle-version">` tag so the spec can
 *   detect which bundle is active.
 * - `broken-bundle.zip` keeps every file name of the default bundle but makes
 *   the entry script render a marker. Since it never calls `ready()`, it must
 *   be rolled back. Reusing the file names ensures that a cached script cannot
 *   mask the bundle switch.
 */
import { execFileSync } from 'node:child_process';
import {
  cpSync,
  mkdirSync,
  readFileSync,
  rmSync,
  writeFileSync,
} from 'node:fs';
import { join } from 'node:path';

import {
  BROKEN_BUNDLE_MARKER_ID,
  BROKEN_BUNDLE_ZIP_PATH,
  BUNDLE_ID,
  BUNDLE_ZIP_PATH,
  EXAMPLE_DIR,
  FIXTURES_DIR,
} from './paths.mjs';

const buildBundle = (name, zipPath, patch) => {
  const bundleDir = join(FIXTURES_DIR, name);
  cpSync(join(EXAMPLE_DIR, 'dist'), bundleDir, { recursive: true });
  patch(bundleDir);
  execFileSync('zip', ['-r', '-X', '-q', zipPath, '.'], { cwd: bundleDir });
};

const patchFile = (path, patch) =>
  writeFileSync(path, patch(readFileSync(path, 'utf8')));

rmSync(FIXTURES_DIR, { recursive: true, force: true });
mkdirSync(FIXTURES_DIR, { recursive: true });

buildBundle('bundle', BUNDLE_ZIP_PATH, bundleDir => {
  const meta = `<meta name="bundle-version" content="${BUNDLE_ID}" />`;
  patchFile(join(bundleDir, 'index.html'), html =>
    html.replace('<head>', `<head>\n    ${meta}`),
  );
});

buildBundle('broken-bundle', BROKEN_BUNDLE_ZIP_PATH, bundleDir => {
  const html = readFileSync(join(bundleDir, 'index.html'), 'utf8');
  const [, entryScript] = html.match(
    /<script type="module"[^>]* src="\/([^"]+)"/,
  );
  const marker = `<p id="${BROKEN_BUNDLE_MARKER_ID}">Broken bundle</p>`;
  patchFile(
    join(bundleDir, entryScript),
    script =>
      `document.body.insertAdjacentHTML('afterbegin', '${marker}');\n${script}`,
  );
});
