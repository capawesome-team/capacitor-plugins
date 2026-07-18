#!/usr/bin/env node
/**
 * Builds the Electron e2e fixtures: an RSA key pair and a set of live-update
 * bundles derived from the built web app (`../dist`).
 *
 * Each bundle is a copy of the web app with a `<meta name="bundle-version">`
 * tag injected into its `<head>` so the e2e spec can detect which bundle is
 * active after a reload. Bundles are emitted as signed zip artifacts.
 *
 * Output (git-ignored): `test/electron/.fixtures/`.
 */
import { execFileSync } from 'node:child_process';
import {
  createHash,
  createSign,
  generateKeyPairSync,
} from 'node:crypto';
import {
  cpSync,
  mkdirSync,
  readFileSync,
  rmSync,
  writeFileSync,
} from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const HERE = dirname(fileURLToPath(import.meta.url));
const EXAMPLE_DIR = join(HERE, '..', '..', '..');
const WEB_DIST = join(EXAMPLE_DIR, 'dist');
const FIXTURES_DIR = join(HERE, '..', '.fixtures');
const BUNDLES_DIR = join(FIXTURES_DIR, 'bundles');
const ZIPS_DIR = join(FIXTURES_DIR, 'zips');

const sha256 = bytes => createHash('sha256').update(bytes).digest('hex');
const sign = (bytes, privateKeyPem) =>
  createSign('RSA-SHA256').update(bytes).sign(privateKeyPem).toString('base64');

const generateKeyPair = () => {
  const { privateKey, publicKey } = generateKeyPairSync('rsa', {
    modulusLength: 2048,
  });
  return {
    privateKeyPem: privateKey.export({ type: 'pkcs8', format: 'pem' }).toString(),
    publicKeyPem: publicKey.export({ type: 'spki', format: 'pem' }).toString(),
  };
};

const injectBundleVersion = (bundleDir, version) => {
  const indexPath = join(bundleDir, 'index.html');
  const html = readFileSync(indexPath, 'utf8');
  const meta = `<meta name="bundle-version" content="${version}" />`;
  writeFileSync(indexPath, html.replace('<head>', `<head>\n    ${meta}`));
};

const createBundleDir = (id, version) => {
  const bundleDir = join(BUNDLES_DIR, id);
  rmSync(bundleDir, { recursive: true, force: true });
  cpSync(WEB_DIST, bundleDir, { recursive: true });
  injectBundleVersion(bundleDir, version);
  return bundleDir;
};

const zipBundle = (id, bundleDir) => {
  const zipPath = join(ZIPS_DIR, `${id}.zip`);
  rmSync(zipPath, { force: true });
  // `-X` strips extra file attributes; archive entries live at the root.
  execFileSync('zip', ['-r', '-X', '-q', zipPath, '.'], { cwd: bundleDir });
  return zipPath;
};

const main = () => {
  rmSync(FIXTURES_DIR, { recursive: true, force: true });
  mkdirSync(BUNDLES_DIR, { recursive: true });
  mkdirSync(ZIPS_DIR, { recursive: true });

  const signer = generateKeyPair();
  const attacker = generateKeyPair();
  const keysDir = join(FIXTURES_DIR, 'keys');
  mkdirSync(keysDir, { recursive: true });
  writeFileSync(join(keysDir, 'private.pem'), signer.privateKeyPem);
  writeFileSync(join(keysDir, 'public.pem'), signer.publicKeyPem);

  const bundles = {};

  const addZipBundle = (id, version, { privateKeyPem }) => {
    const bundleDir = createBundleDir(id, version);
    const zipPath = zipBundle(id, bundleDir);
    const bytes = readFileSync(zipPath);
    bundles[id] = {
      type: 'zip',
      file: `${id}.zip`,
      checksum: sha256(bytes),
      signature: sign(bytes, privateKeyPem),
    };
  };

  // Happy path / correct-signature bundle.
  addZipBundle('2.0.0', '2.0.0', { privateKeyPem: signer.privateKeyPem });
  // Checksum-mismatch reuses the 2.0.0 zip but advertises a bogus checksum.
  bundles['2.0.0-badsum'] = {
    type: 'zip',
    file: '2.0.0.zip',
    checksum: '0'.repeat(64),
    signature: bundles['2.0.0'].signature,
  };
  // Signature-mismatch: signed with an attacker key the app does not trust.
  addZipBundle('sig-2.1.0', '2.1.0', { privateKeyPem: attacker.privateKeyPem });
  // Rollback bundle (kept intentionally un-ready by the spec).
  addZipBundle('3.0.0', '3.0.0', { privateKeyPem: signer.privateKeyPem });
  // Channel-specific bundle.
  addZipBundle('beta-1.0.0', 'beta-1.0.0', {
    privateKeyPem: signer.privateKeyPem,
  });

  const registry = {
    keys: {
      publicKey: signer.publicKeyPem,
    },
    channels: [
      { id: 'a1b2c3d4-0000-0000-0000-000000000001', name: 'production' },
      { id: 'a1b2c3d4-0000-0000-0000-000000000002', name: 'beta' },
    ],
    bundles,
  };
  writeFileSync(
    join(FIXTURES_DIR, 'registry.json'),
    JSON.stringify(registry, null, 2),
  );
  console.log(
    `Built ${Object.keys(bundles).length} fixture bundles in ${FIXTURES_DIR}`,
  );
};

main();
