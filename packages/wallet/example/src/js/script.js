import { Wallet } from '@capawesome/capacitor-wallet';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#canAddPasses')
    .addEventListener('click', async () => {
      const { canAdd } = await Wallet.canAddPasses();
      console.log('canAdd:', canAdd);
    });
  document.querySelector('#addPasses').addEventListener('click', async () => {
    // Replace with base64-encoded `.pkpass` files that were signed on your server.
    const passes = [];
    await Wallet.addPasses({ passes });
    console.log('Passes added.');
  });
  document
    .querySelector('#saveToGoogleWallet')
    .addEventListener('click', async () => {
      // Replace with a signed Google Wallet JWT that was created on your server.
      const jwt = 'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9';
      await Wallet.saveToGoogleWallet({ jwt });
      console.log('Save to Google Wallet flow launched.');
    });
});
