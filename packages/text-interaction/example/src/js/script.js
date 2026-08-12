import { TextInteraction } from '@capawesome/capacitor-text-interaction';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#enable').addEventListener('click', async () => {
    await TextInteraction.enable();
    console.log('Text interaction enabled.');
  });
  document.querySelector('#disable').addEventListener('click', async () => {
    await TextInteraction.disable();
    console.log('Text interaction disabled.');
  });
  document.querySelector('#isEnabled').addEventListener('click', async () => {
    const { enabled } = await TextInteraction.isEnabled();
    console.log('enabled:', enabled);
  });
});
