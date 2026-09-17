import { Singular } from '@capawesome/capacitor-singular';

document.addEventListener('DOMContentLoaded', () => {
  Singular.addListener('deviceAttributionInfoReceived', event => {
    console.log('deviceAttributionInfoReceived', event);
  });
  Singular.addListener('sdidReceived', event => {
    console.log('sdidReceived', event);
  });
  Singular.addListener('sdidSet', event => {
    console.log('sdidSet', event);
  });
  Singular.addListener('singularLinkResolved', event => {
    console.log('singularLinkResolved', event);
  });
  Singular.addListener('skanConversionValueUpdated', event => {
    console.log('skanConversionValueUpdated', event);
  });

  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await Singular.initialize({
        apiKey: 'YOUR_SDK_KEY',
        secret: 'YOUR_SDK_SECRET',
        loggingEnabled: true,
      });
    });
  document
    .querySelector('#track-event-button')
    .addEventListener('click', async () => {
      await Singular.trackEvent({
        name: 'level_completed',
        attributes: { level: 3, character: 'warrior' },
      });
    });
  document
    .querySelector('#track-revenue-button')
    .addEventListener('click', async () => {
      await Singular.trackRevenue({
        amount: 9.99,
        currency: 'USD',
        eventName: 'subscription_purchase',
      });
    });
  document
    .querySelector('#track-ad-revenue-button')
    .addEventListener('click', async () => {
      await Singular.trackAdRevenue({
        adPlatform: 'AdMob',
        adType: 'Rewarded',
        currency: 'USD',
        revenue: 0.05,
      });
    });
  document
    .querySelector('#set-custom-user-id-button')
    .addEventListener('click', async () => {
      await Singular.setCustomUserId({ customUserId: 'user-123' });
    });
  document
    .querySelector('#unset-custom-user-id-button')
    .addEventListener('click', async () => {
      await Singular.unsetCustomUserId();
    });
  document
    .querySelector('#set-global-property-button')
    .addEventListener('click', async () => {
      await Singular.setGlobalProperty({ key: 'plan', value: 'premium' });
    });
  document
    .querySelector('#get-global-properties-button')
    .addEventListener('click', async () => {
      const result = await Singular.getGlobalProperties();
      console.log('getGlobalProperties', result);
    });
  document
    .querySelector('#clear-global-properties-button')
    .addEventListener('click', async () => {
      await Singular.clearGlobalProperties();
    });
  document
    .querySelector('#stop-all-tracking-button')
    .addEventListener('click', async () => {
      await Singular.stopAllTracking();
    });
  document
    .querySelector('#resume-all-tracking-button')
    .addEventListener('click', async () => {
      await Singular.resumeAllTracking();
    });
  document
    .querySelector('#is-all-tracking-stopped-button')
    .addEventListener('click', async () => {
      const result = await Singular.isAllTrackingStopped();
      console.log('isAllTrackingStopped', result);
    });
  document
    .querySelector('#create-referrer-short-link-button')
    .addEventListener('click', async () => {
      const result = await Singular.createReferrerShortLink({
        baseLink: 'https://myapp.sng.link/A1b2c/d3e4',
        passthroughParameters: { campaign: 'friend-invite' },
        referrerId: 'user-123',
        referrerName: 'Jane Doe',
      });
      console.log('createReferrerShortLink', result);
    });
  document
    .querySelector('#skan-get-conversion-value-button')
    .addEventListener('click', async () => {
      const result = await Singular.skanGetConversionValue();
      console.log('skanGetConversionValue', result);
    });
});
