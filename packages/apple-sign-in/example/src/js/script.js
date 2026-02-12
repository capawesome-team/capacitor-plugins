import { AppleSignIn, SignInScope } from '@capawesome/capacitor-apple-sign-in';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#initialize').addEventListener('click', async () => {
    await AppleSignIn.initialize({
      clientId: 'com.example.app.signin',
    });
  });
  document.querySelector('#sign-in').addEventListener('click', async () => {
    const result = await AppleSignIn.signIn({
      scopes: [SignInScope.Email, SignInScope.FullName],
      redirectUrl: 'https://example.com/callback',
      nonce: 'dummy-nonce',
      state: 'dummy-state',
    });
    alert(JSON.stringify(result, null, 2));
  });
});
