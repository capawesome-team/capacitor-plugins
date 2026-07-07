import { SmsComposer } from '@capawesome/capacitor-sms-composer';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#canComposeSms')
    .addEventListener('click', async () => {
      const { canCompose } = await SmsComposer.canComposeSms();
      console.log('canCompose:', canCompose);
    });
  document.querySelector('#composeSms').addEventListener('click', async () => {
    const { status } = await SmsComposer.composeSms({
      recipients: ['+41791234567'],
      body: 'Hello from Capacitor!',
    });
    console.log('status:', status);
  });
});
