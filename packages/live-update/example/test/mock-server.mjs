import { createReadStream, statSync } from 'node:fs';
import { createServer } from 'node:http';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const PORT = 4000;
const BUNDLE_PATH = join(
  dirname(fileURLToPath(import.meta.url)),
  'fixtures',
  'bundle.zip',
);

const server = createServer((req, res) => {
  if (req.method === 'GET' && req.url === '/bundle.zip') {
    const { size } = statSync(BUNDLE_PATH);
    res.writeHead(200, {
      'Content-Type': 'application/zip',
      'Content-Length': size,
    });
    createReadStream(BUNDLE_PATH).pipe(res);
    return;
  }
  res.writeHead(404);
  res.end();
});

server.listen(PORT, () => {
  console.log(`Mock server listening on http://0.0.0.0:${PORT}`);
});
