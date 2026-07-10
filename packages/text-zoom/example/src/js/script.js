import { TextZoom } from '@capawesome/capacitor-text-zoom';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getZoom').addEventListener('click', async () => {
    const { zoom } = await TextZoom.getZoom();
    console.log('zoom:', zoom);
  });
  document
    .querySelector('#getPreferredZoom')
    .addEventListener('click', async () => {
      const { zoom } = await TextZoom.getPreferredZoom();
      console.log('preferred zoom:', zoom);
    });
  document.querySelector('#setZoom').addEventListener('click', async () => {
    await TextZoom.setZoom({ zoom: 1.5 });
    console.log('Zoom set to 1.5.');
  });
});
