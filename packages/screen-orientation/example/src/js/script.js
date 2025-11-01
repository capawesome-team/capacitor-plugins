import { ScreenOrientation } from '@capawesome/capacitor-screen-orientation';

document.addEventListener('DOMContentLoaded', () => {
  ScreenOrientation.addListener('screenOrientationChange', event => {
    console.log('Screen Orientation Change event', event);
  });

  // Buttons
  document.querySelector('#lock-button').addEventListener('click', async () => {
    const type = document.querySelector('#type').value;
    let result;
    if (type === '--') {
      result = await ScreenOrientation.lock();
    } else {
      result = await ScreenOrientation.lock({ type });
    }
    console.log(result);
  });
  document
    .querySelector('#unlock-button')
    .addEventListener('click', async () => {
      await ScreenOrientation.unlock();
    });
  document
    .querySelector('#get-current-orientation-button')
    .addEventListener('click', async () => {
      const result = await ScreenOrientation.getCurrentOrientation();
      console.log(result);
    });
});
