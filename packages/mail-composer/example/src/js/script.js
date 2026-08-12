import { MailComposer } from '@capawesome/capacitor-mail-composer';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#canComposeMail')
    .addEventListener('click', async () => {
      const { canCompose } = await MailComposer.canComposeMail();
      console.log('canCompose:', canCompose);
    });
  document.querySelector('#composeMail').addEventListener('click', async () => {
    const { status } = await MailComposer.composeMail({
      to: ['jane@example.com'],
      cc: ['john@example.com'],
      subject: 'Hello World',
      body: 'This is the body of the email.',
    });
    console.log('status:', status);
  });
});
