import { PrivacyScreen } from '@capawesome/capacitor-privacy-screen';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#enable').addEventListener('click', async () => {
    await PrivacyScreen.enable();
    console.log('Privacy screen enabled.');
  });
  document
    .querySelector('#enablePreventScreenshots')
    .addEventListener('click', async () => {
      await PrivacyScreen.enable({ ios: { preventScreenshots: true } });
      console.log('Privacy screen enabled with screenshot prevention.');
    });
  document.querySelector('#disable').addEventListener('click', async () => {
    await PrivacyScreen.disable();
    console.log('Privacy screen disabled.');
  });
  document.querySelector('#isEnabled').addEventListener('click', async () => {
    const { enabled } = await PrivacyScreen.isEnabled();
    console.log('enabled:', enabled);
  });
  document.querySelector('#addListener').addEventListener('click', async () => {
    await PrivacyScreen.addListener('screenshotTaken', () => {
      console.log('screenshotTaken');
    });
  });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await PrivacyScreen.removeAllListeners();
    });
});
