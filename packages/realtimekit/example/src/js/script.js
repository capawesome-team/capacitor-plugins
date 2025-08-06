import { RealtimeKit } from '@capawesome/capacitor-realtimekit';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await RealtimeKit.initialize();
    });
  document
    .querySelector('#start-meeting-button')
    .addEventListener('click', async () => {
      await RealtimeKit.startMeeting({ authToken: 'authToken' });
    });
});
