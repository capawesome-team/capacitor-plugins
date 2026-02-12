import { GoogleSignIn } from '@capawesome/capacitor-google-sign-in';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await GoogleSignIn.initialize({
        clientId: 'YOUR_CLIENT_ID.apps.googleusercontent.com',
        scopes: ['https://www.googleapis.com/auth/userinfo.email'],
      });
    });
  document
    .querySelector('#sign-in-button')
    .addEventListener('click', async () => {
      const result = await GoogleSignIn.signIn();
      console.log(result);
      document.querySelector('#id-token-input').value = result.idToken;
      document.querySelector('#user-id-input').value = result.userId;
      document.querySelector('#email-input').value = result.email ?? '';
    });
  document
    .querySelector('#sign-out-button')
    .addEventListener('click', async () => {
      await GoogleSignIn.signOut();
    });
});
