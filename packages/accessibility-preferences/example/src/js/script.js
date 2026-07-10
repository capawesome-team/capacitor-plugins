import { AccessibilityPreferences } from '@capawesome/capacitor-accessibility-preferences';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getPreferences')
    .addEventListener('click', async () => {
      const preferences = await AccessibilityPreferences.getPreferences();
      console.log('preferences:', preferences);
    });
});
