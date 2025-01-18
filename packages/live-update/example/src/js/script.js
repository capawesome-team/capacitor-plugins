import { LiveUpdate } from '@capawesome/capacitor-live-update';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#present-select-bundle-alert-button').addEventListener('click', async () => {
    const result = await LiveUpdate.getBundles();
    const alertInputs = [];
    for (const bundleId of result.bundleIds) {
      alertInputs.push({
        type: 'radio',
        label: bundleId,
        value: bundleId,
      });
    }
    const alertElement = document.createElement('ion-alert');
    alertElement.header = 'Select Bundle';
    alertElement.message = 'Please select a bundle.';
    alertElement.inputs = alertInputs;
    alertElement.buttons = [
      {
        text: 'Cancel',
        role: 'cancel',
      },
      {
        text: 'Select',
      },
    ];
    document.body.appendChild(alertElement);
    await alertElement.present();
    alertElement.onDidDismiss().then(async (data) => {
      if (data.role === 'cancel') {
        return;
      }
      if (!data.data) {
        return;
      }
      const bundleId = data.data.values;
      document.querySelector('#bundle-id-input').value = bundleId;
    });
  });
  document.querySelector('#delete-bundle-button').addEventListener('click', async () => {
    const bundleId = document.querySelector('#bundle-id-input').value;
    if (!bundleId) {
      return;
    }
    await LiveUpdate.deleteBundle({ bundleId });
  });
  document.querySelector('#download-bundle-button').addEventListener('click', async () => {
    const artifactType = document.querySelector('#artifact-type-select').value;
    const bundleId = document.querySelector('#bundle-id-input').value;
    const downloadUrl = document.querySelector('#download-url-input').value;
    if (!bundleId || !downloadUrl) {
      return;
    }
    await LiveUpdate.downloadBundle({ artifactType, bundleId, url: downloadUrl });
  });
  document.querySelector('#fetch-latest-bundle-button').addEventListener('click', async () => {
    const channel = document.querySelector('#channel-input').value;
    const result = await LiveUpdate.fetchLatestBundle({
      channel,
    });
    document.querySelector('#artifact-type-select').value = result.artifactType || '';
    document.querySelector('#bundle-id-input').value = result.bundleId || '';
    document.querySelector('#download-url-input').value = result.downloadUrl || '';
  });
  document.querySelector('#get-bundles-button').addEventListener('click', async () => {
    const result = await LiveUpdate.getBundles();
    console.log(result);
  });
  document.querySelector('#get-channel-button').addEventListener('click', async () => {
    const result = await LiveUpdate.getChannel();
    console.log(result);
  });
  document.querySelector('#ready-button').addEventListener('click', async () => {
    await LiveUpdate.ready();
  });
  document.querySelector('#reload-button').addEventListener('click', async () => {
    await LiveUpdate.reload();
  });
  document.querySelector('#reset-button').addEventListener('click', async () => {
    await LiveUpdate.reset();
  });
  document.querySelector('#set-channel-button').addEventListener('click', async () => {
    const channel = document.querySelector('#channel-input').value || null;
    await LiveUpdate.setChannel({ channel });
  });
  document.querySelector('#set-custom-id-button').addEventListener('click', async () => {
    const customId = document.querySelector('#custom-id-input').value || null;
    await LiveUpdate.setCustomId({ customId });
  });
  document.querySelector('#set-next-bundle-button').addEventListener('click', async () => {
    const bundleId = document.querySelector('#bundle-id-input').value || null;
    await LiveUpdate.setNextBundle({ bundleId });
  });
  document.querySelector('#sync-button').addEventListener('click', async () => {
    const channel = document.querySelector('#channel-input').value || undefined;
    await LiveUpdate.sync({
      channel,
    });
  });
});
