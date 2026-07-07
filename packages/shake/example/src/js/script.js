import { Shake } from '@capawesome/capacitor-shake';

document.addEventListener('DOMContentLoaded', () => {
  Shake.addListener('shake', () => {
    console.log('shake detected');
  });
  document
    .querySelector('#startWatching')
    .addEventListener('click', async () => {
      await Shake.startWatching({ sensitivity: 'medium' });
      console.log('watching started');
    });
  document
    .querySelector('#stopWatching')
    .addEventListener('click', async () => {
      await Shake.stopWatching();
      console.log('watching stopped');
    });
});
