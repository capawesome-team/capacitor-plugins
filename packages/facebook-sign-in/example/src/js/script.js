import { FacebookSignIn } from '@capawesome/capacitor-facebook-sign-in';

document.addEventListener('DOMContentLoaded', () => {
  const handleSignInResult = result => {
    console.log(result);
    document.querySelector('#access-token-input').value =
      result.accessToken?.token ?? '';
    document.querySelector('#user-id-input').value = result.profile.id;
    document.querySelector('#name-input').value = result.profile.name ?? '';
    document.querySelector('#email-input').value = result.profile.email ?? '';
  };

  document
    .querySelector('#initialize-button')
    .addEventListener('click', async () => {
      await FacebookSignIn.initialize({
        appId: 'YOUR_APP_ID',
      });
    });
  document
    .querySelector('#sign-in-button')
    .addEventListener('click', async () => {
      const result = await FacebookSignIn.signIn({
        permissions: ['public_profile', 'email'],
      });
      handleSignInResult(result);
    });
  document
    .querySelector('#sign-in-limited-button')
    .addEventListener('click', async () => {
      const result = await FacebookSignIn.signIn({
        limitedLogin: true,
      });
      handleSignInResult(result);
    });
  document
    .querySelector('#get-current-access-token-button')
    .addEventListener('click', async () => {
      const { accessToken } = await FacebookSignIn.getCurrentAccessToken();
      console.log(accessToken);
      document.querySelector('#access-token-input').value =
        accessToken?.token ?? '';
    });
  document
    .querySelector('#sign-out-button')
    .addEventListener('click', async () => {
      await FacebookSignIn.signOut();
    });
});
