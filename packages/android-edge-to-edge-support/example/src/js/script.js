import { StatusBar, Style } from '@capacitor/status-bar';
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge-support';

document.addEventListener('DOMContentLoaded', () => {
  StatusBar.setStyle({ style: Style.Light });

  document
    .querySelector('#enable-button')
    .addEventListener('click', async () => {
      await EdgeToEdge.enable();
    }
  );
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
});
