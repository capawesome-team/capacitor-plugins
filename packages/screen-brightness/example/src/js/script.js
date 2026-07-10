import { ScreenBrightness } from '@capawesome/capacitor-screen-brightness';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getBrightness')
    .addEventListener('click', async () => {
      const { brightness } = await ScreenBrightness.getBrightness();
      console.log('brightness:', brightness);
    });
  document
    .querySelector('#setBrightness')
    .addEventListener('click', async () => {
      await ScreenBrightness.setBrightness({ brightness: 1 });
      console.log('Brightness set to 1.0.');
    });
  document
    .querySelector('#resetBrightness')
    .addEventListener('click', async () => {
      await ScreenBrightness.resetBrightness();
      console.log('Brightness reset.');
    });
});
