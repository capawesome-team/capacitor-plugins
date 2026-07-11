import { Crisp, SessionEventColor } from '@capawesome/capacitor-crisp';

document.addEventListener('DOMContentLoaded', () => {
  Crisp.addListener('sessionLoaded', event => {
    console.log('sessionLoaded', event);
  });
  Crisp.addListener('chatOpened', () => {
    console.log('chatOpened');
  });
  Crisp.addListener('chatClosed', () => {
    console.log('chatClosed');
  });
  Crisp.addListener('messageSent', event => {
    console.log('messageSent', event);
  });
  Crisp.addListener('messageReceived', event => {
    console.log('messageReceived', event);
  });

  document
    .querySelector('#configure-button')
    .addEventListener('click', async () => {
      await Crisp.configure({
        websiteId: 'YOUR_WEBSITE_ID',
      });
    });
  document
    .querySelector('#open-chat-button')
    .addEventListener('click', async () => {
      await Crisp.openChat();
    });
  document
    .querySelector('#set-user-button')
    .addEventListener('click', async () => {
      await Crisp.setUser({
        email: 'jane.doe@example.com',
        nickname: 'Jane Doe',
      });
    });
  document
    .querySelector('#set-company-button')
    .addEventListener('click', async () => {
      await Crisp.setCompany({
        name: 'Capawesome',
        url: 'https://capawesome.io',
      });
    });
  document
    .querySelector('#set-session-data-button')
    .addEventListener('click', async () => {
      await Crisp.setSessionString({ key: 'plan', value: 'pro' });
      await Crisp.setSessionInt({ key: 'age', value: 30 });
      await Crisp.setSessionSegment({ segment: 'checkout' });
    });
  document
    .querySelector('#push-session-event-button')
    .addEventListener('click', async () => {
      await Crisp.pushSessionEvent({
        name: 'purchase',
        color: SessionEventColor.Green,
      });
    });
  document
    .querySelector('#search-helpdesk-button')
    .addEventListener('click', async () => {
      await Crisp.searchHelpdesk();
    });
  document
    .querySelector('#reset-session-button')
    .addEventListener('click', async () => {
      await Crisp.resetSession();
    });
});
