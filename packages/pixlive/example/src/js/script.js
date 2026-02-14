import { Pixlive } from '@capawesome/capacitor-pixlive';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getContext').addEventListener('click', async () => {
    try {
      const result = await Pixlive.getContext({ contextId: '12345' });
      document.querySelector('#output').textContent = JSON.stringify(
        result,
        null,
        2,
      );
    } catch (error) {
      document.querySelector('#output').textContent = `Error: ${error.message}`;
    }
  });
});
