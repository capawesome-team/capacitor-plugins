import { SystemWebview } from '@capawesome/capacitor-system-webview';

const MIN_MAJOR_VERSION = 105;

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getInfo').addEventListener('click', async () => {
    const result = await SystemWebview.getInfo();
    console.log('info:', result);
  });
  document
    .querySelector('#isUpdateRequired')
    .addEventListener('click', async () => {
      const { required } = await SystemWebview.isUpdateRequired({
        minMajorVersion: MIN_MAJOR_VERSION,
      });
      console.log('required:', required);
    });
  document
    .querySelector('#openAppStore')
    .addEventListener('click', async () => {
      await SystemWebview.openAppStore();
    });
});
