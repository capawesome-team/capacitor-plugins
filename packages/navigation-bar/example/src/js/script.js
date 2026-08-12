import { NavigationBar } from '@capawesome/capacitor-navigation-bar';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#set-color-button')
    .addEventListener('click', async () => {
      const color = document.querySelector('#color-input').value;
      const dividerColor = document.querySelector('#divider-color-input').value;
      await NavigationBar.setColor({
        color,
        dividerColor: dividerColor || undefined,
      });
    });
  document
    .querySelector('#set-style-button')
    .addEventListener('click', async () => {
      const style = document.querySelector('#style-select').value;
      await NavigationBar.setStyle({ style });
    });
  document
    .querySelector('#get-color-button')
    .addEventListener('click', async () => {
      const result = await NavigationBar.getColor();
      console.log(result);
      document.querySelector('#current-color-input').value = result.color;
    });
  document
    .querySelector('#get-style-button')
    .addEventListener('click', async () => {
      const result = await NavigationBar.getStyle();
      console.log(result);
      document.querySelector('#current-style-input').value = result.style;
    });
  document.querySelector('#hide-button').addEventListener('click', async () => {
    await NavigationBar.hide();
  });
  document.querySelector('#show-button').addEventListener('click', async () => {
    await NavigationBar.show();
  });
});
