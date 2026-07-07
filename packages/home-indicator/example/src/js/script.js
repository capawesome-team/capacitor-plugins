import { HomeIndicator } from '@capawesome/capacitor-home-indicator';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#hide').addEventListener('click', async () => {
    await HomeIndicator.hide();
    console.log('Home indicator hidden.');
  });
  document.querySelector('#show').addEventListener('click', async () => {
    await HomeIndicator.show();
    console.log('Home indicator shown.');
  });
  document.querySelector('#isHidden').addEventListener('click', async () => {
    const { hidden } = await HomeIndicator.isHidden();
    console.log('hidden:', hidden);
  });
});
