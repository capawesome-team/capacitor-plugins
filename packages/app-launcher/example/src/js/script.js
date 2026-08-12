import { AppLauncher } from '@capawesome/capacitor-app-launcher';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#canOpenUrl').addEventListener('click', async () => {
    const { value } = await AppLauncher.canOpenUrl({ url: 'mailto:' });
    console.log('value:', value);
  });
  document.querySelector('#openUrl').addEventListener('click', async () => {
    const { completed } = await AppLauncher.openUrl({ url: 'mailto:' });
    console.log('completed:', completed);
  });
});
