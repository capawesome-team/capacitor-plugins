import { SettingsLauncher } from '@capawesome/capacitor-settings-launcher';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#openAppSettings')
    .addEventListener('click', async () => {
      await SettingsLauncher.openAppSettings();
    });
  document
    .querySelector('#openNotificationSettings')
    .addEventListener('click', async () => {
      await SettingsLauncher.openNotificationSettings();
    });
  document
    .querySelector('#openAndroidSettings')
    .addEventListener('click', async () => {
      const page = document.querySelector('#androidSettingsPage').value;
      await SettingsLauncher.openAndroidSettings({ page });
    });
});
