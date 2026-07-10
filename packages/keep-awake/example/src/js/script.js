import { KeepAwake } from '@capawesome/capacitor-keep-awake';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#keepAwake').addEventListener('click', async () => {
    await KeepAwake.keepAwake();
    console.log('Screen is kept awake.');
  });
  document.querySelector('#allowSleep').addEventListener('click', async () => {
    await KeepAwake.allowSleep();
    console.log('Screen is allowed to sleep.');
  });
  document.querySelector('#isKeptAwake').addEventListener('click', async () => {
    const { keptAwake } = await KeepAwake.isKeptAwake();
    console.log('keptAwake:', keptAwake);
  });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await KeepAwake.isAvailable();
    console.log('available:', available);
  });
});
