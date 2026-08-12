import { Localization } from '@capawesome/capacitor-localization';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getLocales').addEventListener('click', async () => {
    const { locales } = await Localization.getLocales();
    console.log('locales:', locales);
  });
  document.querySelector('#getSettings').addEventListener('click', async () => {
    const settings = await Localization.getSettings();
    console.log('settings:', settings);
  });
});
