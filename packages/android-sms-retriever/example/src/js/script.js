import { AndroidSmsRetriever } from '@capawesome/capacitor-android-sms-retriever';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#requestPhoneNumber')
    .addEventListener('click', async () => {
      const { phoneNumber } = await AndroidSmsRetriever.requestPhoneNumber();
      console.log('phoneNumber:', phoneNumber);
    });
  document.querySelector('#retrieveSms').addEventListener('click', async () => {
    const { message } = await AndroidSmsRetriever.retrieveSms();
    console.log('message:', message);
  });
});
