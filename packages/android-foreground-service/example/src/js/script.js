import { ForegroundService } from '@capawesome-team/capacitor-android-foreground-service';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#request-permissions')
    .addEventListener('click', async () => {
      await ForegroundService.requestPermissions();
    });
  document
    .querySelector('#start-foreground-service')
    .addEventListener('click', async () => {
      await ForegroundService.startForegroundService({
        id: 1,
        body: 'Foreground Service',
        title: 'Foreground Service',
        smallIcon: 'push_icon',
      });
    });
  document
    .querySelector('#stop-foreground-service')
    .addEventListener('click', async () => {
      await ForegroundService.stopForegroundService();
    });
});
