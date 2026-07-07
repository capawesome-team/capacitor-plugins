import { PhoneDialer } from '@capawesome/capacitor-phone-dialer';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#canDial').addEventListener('click', async () => {
    const { canDial } = await PhoneDialer.canDial();
    console.log('canDial:', canDial);
  });
  document.querySelector('#dial').addEventListener('click', async () => {
    await PhoneDialer.dial({ number: '+41791234567' });
  });
});
