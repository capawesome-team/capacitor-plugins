/**
 * Builds the live update bundle served by the mock Cloud API: a copy of the
 * built web app (`dist/`) with a `<meta name="bundle-version">` tag so the
 * spec can detect which bundle is active, zipped to `.fixtures/bundle.zip`.
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
  BUNDLE_ID,
  BUNDLE_ZIP_PATH,
  EXAMPLE_DIR,
  FIXTURES_DIR,
} from './paths.mjs';

const bundleDir = join(FIXTURES_DIR, 'bundle');

rmSync(FIXTURES_DIR, { recursive: true, force: true });
mkdirSync(FIXTURES_DIR, { recursive: true });
cpSync(join(EXAMPLE_DIR, 'dist'), bundleDir, { recursive: true });

const indexPath = join(bundleDir, 'index.html');
const meta = `<meta name="bundle-version" content="${BUNDLE_ID}" />`;
writeFileSync(
  indexPath,
  readFileSync(indexPath, 'utf8').replace('<head>', `<head>\n    ${meta}`),
);

execFileSync('zip', ['-r', '-X', '-q', BUNDLE_ZIP_PATH, '.'], {
  cwd: bundleDir,
});
