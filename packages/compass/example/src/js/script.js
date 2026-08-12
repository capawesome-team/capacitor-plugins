import { Compass } from '@capawesome/capacitor-compass';

document.addEventListener('DOMContentLoaded', () => {
  Compass.addListener('headingChange', event => {
    console.log('headingChange:', event);
  });

  document.querySelector('#getHeading').addEventListener('click', async () => {
    const heading = await Compass.getHeading();
    console.log('heading:', heading);
  });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await Compass.isAvailable();
    console.log('available:', available);
  });
  document
    .querySelector('#startHeadingUpdates')
    .addEventListener('click', async () => {
      await Compass.startHeadingUpdates();
      console.log('startHeadingUpdates: started');
    });
  document
    .querySelector('#stopHeadingUpdates')
    .addEventListener('click', async () => {
      await Compass.stopHeadingUpdates();
      console.log('stopHeadingUpdates: stopped');
    });
});
