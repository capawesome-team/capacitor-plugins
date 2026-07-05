/**
 * Downloads the Node.js for Mobile Apps iOS artifacts (NodeMobile.xcframework
 * and the Node.js headers) into `ios/libnode`. The Android artifacts are
 * downloaded by the Gradle build (see `android/build.gradle`).
 *
 * Environment variables:
 * - `CAPACITOR_NODEJS_SKIP_DOWNLOAD`: Set to `1` to skip the download.
 * - `CAPACITOR_NODEJS_IOS_URL`: Overwrite the download URL.
 * - `CAPACITOR_NODEJS_IOS_SHA256`: Overwrite the checksum of the download.
 */
const { execFileSync } = require('child_process');
const crypto = require('crypto');
const fs = require('fs');
const https = require('https');
const os = require('os');
const path = require('path');

const version = '18.20.4-capawesome.1';
const url =
  process.env.CAPACITOR_NODEJS_IOS_URL ||
  `https://github.com/capawesome-team/nodejs-mobile/releases/download/v${version}/nodejs-mobile-v${version}-ios.zip`;
const sha256 =
  process.env.CAPACITOR_NODEJS_IOS_SHA256 ||
  '8c5ca3a0d1e38de7f182a5642593e82593b820efd375a14b3ecafc4bcfee620e';

const libnodeDir = path.join(__dirname, '..', 'ios', 'libnode');
const cacheDir = path.join(
  os.homedir(),
  '.cache',
  'capawesome-capacitor-nodejs',
);
const zipFile = path.join(cacheDir, `nodejs-mobile-v${version}-ios.zip`);

function download(downloadUrl, targetFile, redirectCount = 0) {
  return new Promise((resolve, reject) => {
    if (redirectCount > 5) {
      reject(new Error('Too many redirects.'));
      return;
    }
    https
      .get(downloadUrl, response => {
        if (
          response.statusCode >= 300 &&
          response.statusCode < 400 &&
          response.headers.location
        ) {
          response.resume();
          resolve(
            download(response.headers.location, targetFile, redirectCount + 1),
          );
          return;
        }
        if (response.statusCode !== 200) {
          reject(
            new Error(
              `Download failed with status code ${response.statusCode}.`,
            ),
          );
          return;
        }
        const fileStream = fs.createWriteStream(targetFile);
        response.pipe(fileStream);
        fileStream.on('finish', () => fileStream.close(resolve));
        fileStream.on('error', reject);
      })
      .on('error', reject);
  });
}

function calculateSha256(file) {
  const hash = crypto.createHash('sha256');
  hash.update(fs.readFileSync(file));
  return hash.digest('hex');
}

async function run() {
  if (process.env.CAPACITOR_NODEJS_SKIP_DOWNLOAD === '1') {
    return;
  }
  if (process.platform !== 'darwin') {
    return;
  }
  if (
    fs.existsSync(path.join(libnodeDir, 'NodeMobile.xcframework')) &&
    fs.existsSync(path.join(libnodeDir, 'include'))
  ) {
    return;
  }
  fs.mkdirSync(cacheDir, { recursive: true });
  if (!fs.existsSync(zipFile) || calculateSha256(zipFile) !== sha256) {
    console.log(`Downloading Node.js for Mobile Apps from ${url}...`);
    await download(url, zipFile);
  }
  const actualSha256 = calculateSha256(zipFile);
  if (actualSha256 !== sha256) {
    fs.rmSync(zipFile);
    throw new Error(
      `Checksum verification failed for ${zipFile}. Expected ${sha256} but got ${actualSha256}.`,
    );
  }
  fs.rmSync(libnodeDir, { recursive: true, force: true });
  fs.mkdirSync(libnodeDir, { recursive: true });
  execFileSync('unzip', ['-q', zipFile, '-d', libnodeDir]);
}

run().catch(error => {
  console.error(error.message || error);
  process.exit(1);
});
