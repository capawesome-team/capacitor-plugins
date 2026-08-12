import { SilentMode } from '@capawesome/capacitor-silent-mode';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isSilent').addEventListener('click', async () => {
    const { silent } = await SilentMode.isSilent();
    console.log('silent:', silent);
  });
  document
    .querySelector('#getRingerMode')
    .addEventListener('click', async () => {
      const { mode } = await SilentMode.getRingerMode();
      console.log('mode:', mode);
    });
  document.querySelector('#addListener').addEventListener('click', async () => {
    await SilentMode.addListener('silentModeChange', event => {
      console.log('silentModeChange:', event.silent);
    });
  });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await SilentMode.removeAllListeners();
    });
});
