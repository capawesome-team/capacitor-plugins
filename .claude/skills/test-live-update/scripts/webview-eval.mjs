#!/usr/bin/env node
// Evaluate a JavaScript expression in the WebView of the example app on Android and print the result as JSON.
// Usage: webview-eval.mjs '<expression that returns a value or a promise>'
// Set ANDROID_SERIAL for another device and APP for another app, for example the provider example.
import { execFileSync } from 'node:child_process';

const serial = process.env.ANDROID_SERIAL ?? 'emulator-5554';
const app = process.env.APP ?? 'com.example.plugin';
const port = 9222;
const attempts = 10;
const expression = process.argv[2];

if (!expression) {
  console.error("usage: webview-eval.mjs '<expression>'");
  process.exit(2);
}

const adb = (...args) => execFileSync('adb', ['-s', serial, ...args], { encoding: 'utf8' });

const connect = async () => {
  const pid = adb('shell', 'pidof', app).trim();
  if (!pid) {
    throw new Error(`${app} is not running on ${serial}`);
  }
  adb('forward', `tcp:${port}`, `localabstract:webview_devtools_remote_${pid}`);
  const pages = await (await fetch(`http://localhost:${port}/json`)).json();
  const page = pages.find(candidate => candidate.type === 'page') ?? pages[0];
  const socket = new WebSocket(page.webSocketDebuggerUrl);
  await new Promise((resolve, reject) => {
    socket.onopen = resolve;
    socket.onerror = reject;
  });
  return socket;
};

const evaluate = (socket, source) =>
  new Promise(resolve => {
    socket.onmessage = event => {
      const message = JSON.parse(event.data);
      if (message.id === 1) {
        resolve(message.result);
      }
    };
    socket.send(
      JSON.stringify({
        id: 1,
        method: 'Runtime.evaluate',
        params: { expression: source, awaitPromise: true, returnByValue: true },
      }),
    );
  });

const wrapped = `(async () => { try { const value = await (${expression}); return { ok: true, value }; } catch (error) { return { ok: false, code: error.code ?? null, message: error.message ?? String(error) }; } })()`;

for (let attempt = 1; attempt <= attempts; attempt++) {
  try {
    const socket = await connect();
    // Right after a start or a reload the page has no Capacitor object yet.
    const ready = await evaluate(socket, 'typeof Capacitor !== "undefined" && !!Capacitor.Plugins');
    if (!ready?.result?.value) {
      socket.close();
      throw new Error('Capacitor is not defined yet');
    }
    const result = await evaluate(socket, wrapped);
    socket.close();
    console.log(JSON.stringify(result.result?.value ?? result));
    process.exit(0);
  } catch (error) {
    if (attempt === attempts) {
      console.error(`failed: ${error.message}`);
      process.exit(1);
    }
    await new Promise(resolve => setTimeout(resolve, 1500));
  }
}
