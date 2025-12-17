import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#enable-button')
    .addEventListener('click', async () => {
      await EdgeToEdge.enable();
    });
  document
    .querySelector('#disable-button')
    .addEventListener('click', async () => {
      await EdgeToEdge.disable();
    });
  document
    .querySelector('#get-insets-button')
    .addEventListener('click', async () => {
      const result = await EdgeToEdge.getInsets();
      console.log(result);
    });
  document
    .querySelector('#set-background-color-button')
    .addEventListener('click', async () => {
      const color = document.querySelector('#color-input').value;
      if (!color) {
        return;
      }
      await EdgeToEdge.setBackgroundColor({
        color: color.includes('#') ? color : `#${color}`,
      });
    });
  document
    .querySelector('#set-status-bar-color-button')
    .addEventListener('click', async () => {
      await EdgeToEdge.setStatusBarColor({
        color: '#ff0000',
      });
    });
  document
    .querySelector('#set-navigation-bar-color-button')
    .addEventListener('click', async () => {
      await EdgeToEdge.setNavigationBarColor({
        color: '#0000ff',
      });
    });
});
