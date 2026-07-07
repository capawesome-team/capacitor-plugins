import { InAppBrowser } from '@capawesome/capacitor-in-app-browser';

const url = 'https://capawesome.io';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#openInExternalBrowser')
    .addEventListener('click', async () => {
      await InAppBrowser.openInExternalBrowser({ url });
    });
  document
    .querySelector('#openInSystemBrowser')
    .addEventListener('click', async () => {
      await InAppBrowser.openInSystemBrowser({
        url,
        android: {
          showTitle: true,
          toolbarColor: '#008080',
        },
        ios: {
          dismissButtonStyle: 'close',
          toolbarColor: '#008080',
        },
      });
    });
  document
    .querySelector('#openInWebView')
    .addEventListener('click', async () => {
      await InAppBrowser.openInWebView({
        url,
        toolbar: {
          backgroundColor: '#008080',
          color: '#FFFFFF',
          showNavigationButtons: true,
        },
      });
    });
  document.querySelector('#close').addEventListener('click', async () => {
    await InAppBrowser.close();
  });
  document
    .querySelector('#executeScript')
    .addEventListener('click', async () => {
      const { result } = await InAppBrowser.executeScript({
        script: 'document.title',
      });
      console.log('result:', result);
    });
  document.querySelector('#getCookies').addEventListener('click', async () => {
    const { cookies } = await InAppBrowser.getCookies({ url });
    console.log('cookies:', cookies);
  });
  document.querySelector('#postMessage').addEventListener('click', async () => {
    await InAppBrowser.postMessage({
      data: { name: 'Capawesome' },
    });
  });
  document.querySelector('#clearCache').addEventListener('click', async () => {
    await InAppBrowser.clearCache();
  });
  document
    .querySelector('#clearSessionData')
    .addEventListener('click', async () => {
      await InAppBrowser.clearSessionData();
    });

  void InAppBrowser.addListener('browserClosed', () => {
    console.log('browserClosed');
  });
  void InAppBrowser.addListener('browserPageLoaded', () => {
    console.log('browserPageLoaded');
  });
  void InAppBrowser.addListener('browserPageNavigationCompleted', event => {
    console.log('browserPageNavigationCompleted:', event.url);
  });
  void InAppBrowser.addListener('messageReceived', event => {
    console.log('messageReceived:', event.data);
  });
});
