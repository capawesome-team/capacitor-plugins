import { InstallReferrer } from '@capawesome/capacitor-install-referrer';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getInstallReferrer')
    .addEventListener('click', async () => {
      const result = await InstallReferrer.getInstallReferrer();
      console.log('installReferrer:', result);
    });
  document
    .querySelector('#getAttributionToken')
    .addEventListener('click', async () => {
      const { token } = await InstallReferrer.getAttributionToken();
      console.log('token:', token);
    });
});
