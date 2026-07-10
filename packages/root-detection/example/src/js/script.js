import { RootDetection } from '@capawesome/capacitor-root-detection';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isRooted').addEventListener('click', async () => {
    const { rooted } = await RootDetection.isRooted();
    console.log('rooted:', rooted);
  });
  document.querySelector('#isEmulator').addEventListener('click', async () => {
    const { emulator } = await RootDetection.isEmulator();
    console.log('emulator:', emulator);
  });
  document
    .querySelector('#isDeveloperModeEnabled')
    .addEventListener('click', async () => {
      const { enabled } = await RootDetection.isDeveloperModeEnabled();
      console.log('enabled:', enabled);
    });
});
