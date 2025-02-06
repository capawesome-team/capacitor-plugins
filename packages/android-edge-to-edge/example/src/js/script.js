import { StatusBar, Style } from '@capacitor/status-bar';
import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge';

document.addEventListener('DOMContentLoaded', () => {
  StatusBar.setStyle({ style: Style.Light });

  document
    .querySelector('#set-background-color')
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
