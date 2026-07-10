import { Network } from '@capawesome/capacitor-network';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getStatus').addEventListener('click', async () => {
    const status = await Network.getStatus();
    console.log('status:', status);
  });
  document
    .querySelector('#isAirplaneModeEnabled')
    .addEventListener('click', async () => {
      const { enabled } = await Network.isAirplaneModeEnabled();
      console.log('enabled:', enabled);
    });
  document
    .querySelector('#addListeners')
    .addEventListener('click', async () => {
      await Network.addListener('networkStatusChange', status => {
        console.log('networkStatusChange:', status);
      });
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await Network.removeAllListeners();
    });
});
