import { AppleSignIn, SignInScope } from '@capawesome/capacitor-apple-sign-in';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#initialize').addEventListener('click', async () => {
    await AppleSignIn.initialize({
      clientId: 'dev.ionstarter.angularfirebase.demo.service',
    });
  });
  document.querySelector('#sign-in').addEventListener('click', async () => {
    const result = await AppleSignIn.signIn({
      scopes: [SignInScope.Email, SignInScope.FullName],
      redirectUrl:
        'https://tony-firewire-surprise-automobiles.trycloudflare.com/callback',
      nonce: 'dummy-nonce',
      state: 'dummy-state',
    });
    console.log('Sign in result:', result);
  });
});
