import { AppTrackingTransparency } from '@capawesome/capacitor-app-tracking-transparency';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getStatus').addEventListener('click', async () => {
    const { status } = await AppTrackingTransparency.getStatus();
    console.log('status:', status);
  });
  document
    .querySelector('#requestPermission')
    .addEventListener('click', async () => {
      const { status } = await AppTrackingTransparency.requestPermission();
      console.log('status:', status);
    });
  document
    .querySelector('#getAdvertisingIdentifier')
    .addEventListener('click', async () => {
      const { advertisingIdentifier } =
        await AppTrackingTransparency.getAdvertisingIdentifier();
      console.log('advertisingIdentifier:', advertisingIdentifier);
    });
});
