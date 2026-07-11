import { Intune } from '@capawesome/capacitor-intune';

let accountId;

document.addEventListener('DOMContentLoaded', () => {
  Intune.addListener('appConfigChange', event => {
    console.log('appConfigChange', event);
  });
  Intune.addListener('enrollmentChange', event => {
    console.log('enrollmentChange', event);
  });
  Intune.addListener('policyChange', event => {
    console.log('policyChange', event);
  });
  Intune.addListener('wipeRequested', event => {
    console.log('wipeRequested', event);
    // Clean up the web layer storage of your app here,
    // e.g. IndexedDB and Local Storage.
  });

  document
    .querySelector('#acquire-token-button')
    .addEventListener('click', async () => {
      const result = await Intune.acquireToken({
        scopes: ['https://graph.microsoft.com/.default'],
      });
      accountId = result.accountId;
      console.log('acquireToken', result);
    });
  document
    .querySelector('#acquire-token-silent-button')
    .addEventListener('click', async () => {
      const result = await Intune.acquireTokenSilent({
        accountId,
        scopes: ['https://graph.microsoft.com/.default'],
      });
      console.log('acquireTokenSilent', result);
    });
  document
    .querySelector('#register-and-enroll-account-button')
    .addEventListener('click', async () => {
      await Intune.registerAndEnrollAccount({ accountId });
    });
  document
    .querySelector('#login-and-enroll-account-button')
    .addEventListener('click', async () => {
      await Intune.loginAndEnrollAccount();
    });
  document
    .querySelector('#get-enrolled-account-button')
    .addEventListener('click', async () => {
      const result = await Intune.getEnrolledAccount();
      accountId = result.account?.accountId ?? accountId;
      console.log('getEnrolledAccount', result);
    });
  document
    .querySelector('#get-app-config-button')
    .addEventListener('click', async () => {
      const result = await Intune.getAppConfig({ accountId });
      console.log('getAppConfig', result);
    });
  document
    .querySelector('#get-policy-button')
    .addEventListener('click', async () => {
      const result = await Intune.getPolicy({ accountId });
      console.log('getPolicy', result);
    });
  document
    .querySelector('#get-sdk-version-button')
    .addEventListener('click', async () => {
      const result = await Intune.getSdkVersion();
      console.log('getSdkVersion', result);
    });
  document
    .querySelector('#show-diagnostic-console-button')
    .addEventListener('click', async () => {
      await Intune.showDiagnosticConsole();
    });
  document
    .querySelector('#unenroll-account-button')
    .addEventListener('click', async () => {
      await Intune.unenrollAccount({ accountId });
    });
});
