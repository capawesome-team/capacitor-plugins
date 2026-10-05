import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const HERE = dirname(fileURLToPath(import.meta.url));

export const BUNDLE_ID = '2.0.0';
export const EXAMPLE_DIR = join(HERE, '..', '..', '..');
export const ELECTRON_APP_DIR = join(EXAMPLE_DIR, 'electron');
export const FIXTURES_DIR = join(HERE, '..', '.fixtures');
export const BUNDLE_ZIP_PATH = join(FIXTURES_DIR, 'bundle.zip');
export const BROKEN_BUNDLE_ID = '3.0.0';
export const BROKEN_BUNDLE_MARKER_ID = 'broken-bundle';
export const BROKEN_BUNDLE_ZIP_PATH = join(FIXTURES_DIR, 'broken-bundle.zip');
