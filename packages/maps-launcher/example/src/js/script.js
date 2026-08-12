import { MapsLauncher } from '@capawesome/capacitor-maps-launcher';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getAvailableApps')
    .addEventListener('click', async () => {
      const { apps } = await MapsLauncher.getAvailableApps();
      console.log('apps:', apps);
    });
  document
    .querySelector('#getDefaultApp')
    .addEventListener('click', async () => {
      const { app } = await MapsLauncher.getDefaultApp();
      console.log('app:', app);
    });
  document.querySelector('#navigate').addEventListener('click', async () => {
    await MapsLauncher.navigate({
      destination: {
        latitude: 37.3349,
        longitude: -122.009,
      },
      travelMode: 'driving',
    });
  });
});
