import { Screenshot } from '@capawesome/capacitor-screenshot';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#take').addEventListener('click', async () => {
    await Screenshot.take();
  });
});
