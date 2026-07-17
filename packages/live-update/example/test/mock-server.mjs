import { createHash } from 'node:crypto';
import { createReadStream, existsSync, readFileSync, statSync } from 'node:fs';
import { createServer } from 'node:http';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const HERE = dirname(fileURLToPath(import.meta.url));
const LEGACY_BUNDLE_PATH = join(HERE, 'fixtures', 'bundle.zip');

const sha256 = bytes => createHash('sha256').update(bytes).digest('hex');

const sendJson = (res, status, body) => {
  const payload = JSON.stringify(body);
  res.writeHead(status, {
    'Content-Type': 'application/json',
    'Content-Length': Buffer.byteLength(payload),
  });
  res.end(payload);
};

/**
 * Creates a mock Capawesome Cloud server that implements the endpoints the
 * live-update engine calls: `bundles/latest`, `channels` and zip downloads.
 * When `registry` is omitted only the legacy `GET /bundle.zip` route (used by
 * the mobile Maestro flows) is served.
 */
export const createMockServer = ({ registry, fixturesDir } = {}) => {
  const bundles = registry?.bundles ?? {};
  const fileToEntry = new Map();
  for (const entry of Object.values(bundles)) {
    if (entry.type === 'zip' && !fileToEntry.has(entry.file)) {
      fileToEntry.set(entry.file, entry);
    }
  }

  const state = {
    latestByChannel: {},
    channelsEnabled: true,
  };

  const resolveLatestBundleId = channelName => {
    const key = channelName ?? 'default';
    return state.latestByChannel[key] ?? state.latestByChannel.default ?? null;
  };

  const handleControl = (req, res) => {
    let body = '';
    req.on('data', chunk => (body += chunk));
    req.on('end', () => {
      let patch;
      try {
        patch = body ? JSON.parse(body) : {};
      } catch {
        return sendJson(res, 400, { message: 'Invalid JSON body.' });
      }
      if (patch.latestByChannel !== undefined) {
        state.latestByChannel = patch.latestByChannel;
      }
      if (patch.channelsEnabled !== undefined) {
        state.channelsEnabled = patch.channelsEnabled;
      }
      sendJson(res, 200, { ok: true, state });
    });
  };

  const handleChannels = (res, url) => {
    if (!state.channelsEnabled) {
      return sendJson(res, 401, {
        message:
          'Unauthorized. Channel Discovery may not be enabled for this app.',
      });
    }
    const query = url.searchParams.get('query');
    const limit = Number(url.searchParams.get('limit') ?? '50');
    const offset = Number(url.searchParams.get('offset') ?? '0');
    let channels = registry?.channels ?? [];
    if (query) {
      channels = channels.filter(channel => channel.name.includes(query));
    }
    sendJson(res, 200, channels.slice(offset, offset + limit));
  };

  const handleLatest = (res, url, requestOrigin) => {
    const channelName = url.searchParams.get('channelName');
    const bundleId = resolveLatestBundleId(channelName);
    const entry = bundleId ? bundles[bundleId] : null;
    if (!entry) {
      return sendJson(res, 404, { message: 'No bundle available.' });
    }
    sendJson(res, 200, {
      artifactType: 'zip',
      bundleId,
      channelName: channelName ?? null,
      checksum: entry.checksum,
      signature: entry.signature,
      url: `${requestOrigin}/download/${entry.file}`,
    });
  };

  const handleDownload = (res, file) => {
    const entry = fileToEntry.get(file);
    const zipPath = join(fixturesDir, 'zips', file);
    if (!entry || !existsSync(zipPath)) {
      res.writeHead(404);
      return res.end();
    }
    const bytes = readFileSync(zipPath);
    res.writeHead(200, {
      'Content-Type': 'application/zip',
      'Content-Length': bytes.length,
      'X-Checksum': sha256(bytes),
      ...(entry.signature ? { 'X-Signature': entry.signature } : {}),
    });
    res.end(bytes);
  };

  const handleLegacyBundle = res => {
    if (!existsSync(LEGACY_BUNDLE_PATH)) {
      res.writeHead(404);
      return res.end();
    }
    const { size } = statSync(LEGACY_BUNDLE_PATH);
    res.writeHead(200, {
      'Content-Type': 'application/zip',
      'Content-Length': size,
    });
    createReadStream(LEGACY_BUNDLE_PATH).pipe(res);
  };

  return createServer((req, res) => {
    const requestOrigin = `http://${req.headers.host ?? 'localhost'}`;
    const url = new URL(req.url, requestOrigin);
    const path = url.pathname;

    if (req.method === 'POST' && path === '/__control') {
      return handleControl(req, res);
    }
    if (req.method === 'GET' && path === '/bundle.zip') {
      return handleLegacyBundle(res);
    }
    if (req.method === 'GET' && /^\/v1\/apps\/[^/]+\/channels$/.test(path)) {
      return handleChannels(res, url);
    }
    if (
      req.method === 'GET' &&
      /^\/v1\/apps\/[^/]+\/bundles\/latest$/.test(path)
    ) {
      return handleLatest(res, url, requestOrigin);
    }
    if (req.method === 'GET' && path.startsWith('/download/')) {
      return handleDownload(
        res,
        decodeURIComponent(path.slice('/download/'.length)),
      );
    }
    res.writeHead(404);
    res.end();
  });
};

// Standalone entry point for the mobile Maestro flows: serve `bundle.zip` on
// port 4000, preserving the original behaviour.
if (process.argv[1] === fileURLToPath(import.meta.url)) {
  const PORT = 4000;
  const server = createMockServer();
  server.listen(PORT, () => {
    console.log(`Mock server listening on http://0.0.0.0:${PORT}`);
  });
}
