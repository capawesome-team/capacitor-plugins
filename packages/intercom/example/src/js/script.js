import { Intercom } from '@capawesome/capacitor-intercom';

let launcherVisible = true;

document.addEventListener('DOMContentLoaded', () => {
  Intercom.addListener('unreadConversationCountChange', event => {
    console.log('unreadConversationCountChange', event);
  });
  Intercom.addListener('messengerShown', () => {
    console.log('messengerShown');
  });
  Intercom.addListener('messengerHidden', () => {
    console.log('messengerHidden');
  });

  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await Intercom.initialize({
        appId: 'YOUR_APP_ID',
        androidApiKey: 'YOUR_ANDROID_API_KEY',
        iosApiKey: 'YOUR_IOS_API_KEY',
      });
    });
  document
    .querySelector('#login-user-button')
    .addEventListener('click', async () => {
      await Intercom.loginUser({
        userId: 'jane-doe',
        email: 'jane.doe@example.com',
      });
    });
  document
    .querySelector('#login-unidentified-user-button')
    .addEventListener('click', async () => {
      await Intercom.loginUnidentifiedUser();
    });
  document
    .querySelector('#update-user-button')
    .addEventListener('click', async () => {
      await Intercom.updateUser({
        name: 'Jane Doe',
        customAttributes: { plan: 'pro' },
        companies: [{ id: 'capawesome', name: 'Capawesome' }],
      });
    });
  document
    .querySelector('#present-button')
    .addEventListener('click', async () => {
      await Intercom.present({ space: 'home' });
    });
  document
    .querySelector('#present-message-composer-button')
    .addEventListener('click', async () => {
      await Intercom.presentMessageComposer({
        initialMessage: 'Hello, I need help with...',
      });
    });
  document
    .querySelector('#log-event-button')
    .addEventListener('click', async () => {
      await Intercom.logEvent({
        name: 'purchase',
        data: { plan: 'pro', price: 42 },
      });
    });
  document
    .querySelector('#get-unread-count-button')
    .addEventListener('click', async () => {
      const { count } = await Intercom.getUnreadConversationCount();
      console.log('Unread conversations:', count);
    });
  document
    .querySelector('#set-launcher-visible-button')
    .addEventListener('click', async () => {
      launcherVisible = !launcherVisible;
      await Intercom.setLauncherVisible({ visible: launcherVisible });
    });
  document
    .querySelector('#logout-button')
    .addEventListener('click', async () => {
      await Intercom.logout();
    });
});
