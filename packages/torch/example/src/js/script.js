import { Torch } from '@capawesome/capacitor-torch';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#enable').addEventListener('click', async () => {
    await Torch.enable();
  });
  document.querySelector('#disable').addEventListener('click', async () => {
    await Torch.disable();
  });
  document.querySelector('#toggle').addEventListener('click', async () => {
    await Torch.toggle();
  });
});
