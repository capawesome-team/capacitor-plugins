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
      await Pixlive.requestPermissions();
    });

  document.querySelector('#getContext').addEventListener('click', async () => {
    await Pixlive.getContext({ contextId: '12345' });
  });
});
