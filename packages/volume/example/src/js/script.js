import { Volume } from '@capawesome/capacitor-volume';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getVolume').addEventListener('click', async () => {
    const { volume } = await Volume.getVolume();
    console.log('volume:', volume);
  });
  document.querySelector('#setVolume').addEventListener('click', async () => {
    await Volume.setVolume({ volume: 0.5 });
  });
  document
    .querySelector('#startWatching')
    .addEventListener('click', async () => {
      await Volume.startWatching();
    });
  document
    .querySelector('#startWatchingSuppressed')
    .addEventListener('click', async () => {
      await Volume.startWatching({ suppressVolumeChange: true });
    });
  document
    .querySelector('#stopWatching')
    .addEventListener('click', async () => {
      await Volume.stopWatching();
    });
  document.querySelector('#isWatching').addEventListener('click', async () => {
    const { watching } = await Volume.isWatching();
    console.log('watching:', watching);
  });
  document
    .querySelector('#addListeners')
    .addEventListener('click', async () => {
      await Volume.addListener('volumeButtonPressed', event => {
        console.log('volumeButtonPressed:', event.direction);
      });
      await Volume.addListener('volumeChange', event => {
        console.log('volumeChange:', event.volume);
      });
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await Volume.removeAllListeners();
    });
});
