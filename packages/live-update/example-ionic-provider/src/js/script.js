import { registerPlugin } from '@capacitor/core';

// Custom Capacitor plugin defined in the example app's native code
// (`IonicProviderTestPlugin.java` on Android, `IonicProviderTestPlugin.swift`
// on iOS). It exposes the Ionic Live Update Provider SDK to JavaScript so we
// can test the integration end-to-end without a real Federated Capacitor or
// Portals host.
const IonicProviderTest = registerPlugin('IonicProviderTest');

const logOutput = () => document.querySelector('#log-output');

const log = (label, value) => {
  const out = logOutput();
  const stamp = new Date().toLocaleTimeString();
  const formatted =
    typeof value === 'string' ? value : JSON.stringify(value, null, 2);
  out.textContent = `[${stamp}] ${label}\n${formatted}\n\n` + out.textContent;
};

const readConfig = () => {
  const managerKey = document.querySelector('#manager-key-input').value?.trim();
  const appId = document.querySelector('#app-id-input').value?.trim();
  const channel = document.querySelector('#channel-input').value?.trim();
  return {
    managerKey: managerKey || 'example-shell',
    ...(appId ? { appId } : {}),
    ...(channel ? { channel } : {}),
  };
};

const safeCall = async (label, fn) => {
  try {
    const result = await fn();
    log(`${label} → ok`, result);
  } catch (error) {
    log(`${label} → error`, error?.message ?? String(error));
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#is-provider-registered-button')
    .addEventListener('click', () =>
      safeCall('isProviderRegistered', () =>
        IonicProviderTest.isProviderRegistered(),
      ),
    );

  document
    .querySelector('#get-latest-app-directory-button')
    .addEventListener('click', () => {
      const config = readConfig();
      log('getLatestAppDirectory →', config);
      void safeCall('getLatestAppDirectory', () =>
        IonicProviderTest.getLatestAppDirectory(config),
      );
    });

  document
    .querySelector('#sync-manager-button')
    .addEventListener('click', () => {
      const config = readConfig();
      log('syncManager →', config);
      void safeCall('syncManager', () => IonicProviderTest.syncManager(config));
    });

  document.querySelector('#clear-log-button').addEventListener('click', () => {
    logOutput().textContent = '';
  });
});
