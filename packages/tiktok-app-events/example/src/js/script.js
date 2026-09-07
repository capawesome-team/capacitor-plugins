import { TiktokAppEvents } from '@capawesome/capacitor-tiktok-app-events';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await TiktokAppEvents.initialize({
        accessToken: 'YOUR_ACCESS_TOKEN',
        tiktokAppId: 'YOUR_TIKTOK_APP_ID',
        iosAppId: 'YOUR_APP_STORE_ID',
        debugMode: true,
      });
    });
  document
    .querySelector('#track-event-button')
    .addEventListener('click', async () => {
      await TiktokAppEvents.trackEvent({
        name: 'Purchase',
        properties: {
          currency: 'USD',
          value: 9.99,
          contents: [
            {
              content_id: 'sku-123',
              content_type: 'product',
              quantity: 1,
              price: 9.99,
            },
          ],
        },
      });
    });
  document
    .querySelector('#identify-button')
    .addEventListener('click', async () => {
      await TiktokAppEvents.identify({
        externalId: 'user-123',
        email: 'jane.doe@example.com',
      });
    });
  document
    .querySelector('#logout-button')
    .addEventListener('click', async () => {
      await TiktokAppEvents.logout();
    });
  document
    .querySelector('#flush-button')
    .addEventListener('click', async () => {
      await TiktokAppEvents.flush();
    });
});
