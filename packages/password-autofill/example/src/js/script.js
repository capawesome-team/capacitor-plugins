import { PasswordAutofill } from '@capawesome/capacitor-password-autofill';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#savePassword')
    .addEventListener('click', async () => {
      const domain = document.querySelector('#domain').value;
      const username = document.querySelector('#username').value;
      const password = document.querySelector('#password').value;
      try {
        await PasswordAutofill.savePassword({ domain, username, password });
        console.log('Password saved.');
      } catch (error) {
        console.error('Failed to save password:', error);
      }
    });
});
