import { Pixlive } from '@capawesome/capacitor-pixlive';

document.addEventListener('DOMContentLoaded', () => {
  const output = document.querySelector('#output');

  document
    .querySelector('#checkPermissions')
    .addEventListener('click', async () => {
      await Pixlive.checkPermissions();
    });

  document
    .querySelector('#requestPermissions')
    .addEventListener('click', async () => {
      await Pixlive.requestPermissions({
        permissions: ['location'],
      });
    });

  document.querySelector('#getContext').addEventListener('click', async () => {
    await Pixlive.getContext({ contextId: '12345' });
  });

  document.querySelector('#getVersion').addEventListener('click', async () => {
    const response = await Pixlive.getVersion();
    alert(response.version);
  });
});
