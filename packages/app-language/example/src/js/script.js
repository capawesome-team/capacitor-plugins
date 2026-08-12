import { AppLanguage } from '@capawesome/capacitor-app-language';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getLanguage').addEventListener('click', async () => {
    const { languageTag } = await AppLanguage.getLanguage();
    console.log('languageTag:', languageTag);
  });
  document.querySelector('#setLanguage').addEventListener('click', async () => {
    await AppLanguage.setLanguage({ languageTag: 'de-DE' });
    console.log('Language set to de-DE.');
  });
  document
    .querySelector('#resetLanguage')
    .addEventListener('click', async () => {
      await AppLanguage.resetLanguage();
      console.log('Language reset.');
    });
  document
    .querySelector('#openSettings')
    .addEventListener('click', async () => {
      await AppLanguage.openSettings();
      console.log('Settings opened.');
    });
});
