import { Badge } from '@capawesome/capacitor-badge';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#get-button').addEventListener('click', async () => {
    const result = await Badge.get();
    console.log(result);
  });
  document.querySelector('#set-button').addEventListener('click', async () => {
    await Badge.set({ count: 7 });
  });
  document
    .querySelector('#increase-button')
    .addEventListener('click', async () => {
      await Badge.increase();
    });
  document
    .querySelector('#decrease-button')
    .addEventListener('click', async () => {
      await Badge.decrease();
    });
  document
    .querySelector('#clear-button')
    .addEventListener('click', async () => {
      await Badge.clear();
    });
  document
    .querySelector('#is-supported-button')
    .addEventListener('click', async () => {
      const result = await Badge.isSupported();
      console.log(result);
    });
  document
    .querySelector('#check-permissions-button')
    .addEventListener('click', async () => {
      const result = await Badge.checkPermissions();
      console.log(result);
    });
  document
    .querySelector('#request-permissions-button')
    .addEventListener('click', async () => {
      await Badge.requestPermissions();
    });
});
