import { Capacitor } from '@capacitor/core';
import { Screenshot } from '@capawesome/capacitor-screenshot';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#take').addEventListener('click', async () => {
    const { uri } = await Screenshot.take();
    if (Capacitor.getPlatform() === 'web') {
      document.querySelector('#image').src = uri;
    } else {
      document.querySelector('#image').src = Capacitor.convertFileSrc(uri);
    }
  });
});
