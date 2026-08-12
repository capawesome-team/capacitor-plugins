import { AndroidIntentLauncher } from '@capawesome/capacitor-android-intent-launcher';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#openWebsite').addEventListener('click', async () => {
    const result = await AndroidIntentLauncher.startActivity({
      action: 'android.intent.action.VIEW',
      dataUri: 'https://capawesome.io',
    });
    console.log('resultCode:', result.resultCode);
  });
  document.querySelector('#openDialer').addEventListener('click', async () => {
    await AndroidIntentLauncher.startActivity({
      action: 'android.intent.action.DIAL',
      dataUri: 'tel:+12025550123',
    });
  });
  document
    .querySelector('#composeEmail')
    .addEventListener('click', async () => {
      await AndroidIntentLauncher.startActivity({
        action: 'android.intent.action.SENDTO',
        dataUri: 'mailto:hello@example.com',
        extras: {
          'android.intent.extra.SUBJECT': 'Hello from Capacitor',
          'android.intent.extra.TEXT':
            'Sent via the Android Intent Launcher plugin.',
        },
      });
    });
  document
    .querySelector('#canResolveWebsite')
    .addEventListener('click', async () => {
      const { canResolve } = await AndroidIntentLauncher.canResolveActivity({
        action: 'android.intent.action.VIEW',
        dataUri: 'https://capawesome.io',
      });
      console.log('canResolve:', canResolve);
    });
});
