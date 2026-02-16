import { GoogleSignIn } from '@capawesome/capacitor-google-sign-in';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await GoogleSignIn.initialize({
        clientId:
          '85678314176-1e4f8ur01p711jf9t1mu1g68opjt3oh0.apps.googleusercontent.com',
        redirectUrl:
          'https://want-convertible-awards-machinery.trycloudflare.com',
        scopes: ['https://www.googleapis.com/auth/userinfo.email'],
      });
    });
  document
    .querySelector('#handle-redirect-callback-button')
    .addEventListener('click', async () => {
      const result = await GoogleSignIn.handleRedirectCallback();
      console.log(result);
      document.querySelector('#id-token-input').value = result.idToken;
      document.querySelector('#user-id-input').value = result.userId;
      document.querySelector('#email-input').value = result.email ?? '';
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
