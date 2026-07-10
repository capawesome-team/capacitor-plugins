import { AppIntegrity } from '@capawesome/capacitor-app-integrity';

// Replace with the Google Cloud project number that is linked
// to your Play Console developer account.
const cloudProjectNumber = 123456789012;

let keyId;

const createNonce = () => {
  const bytes = new Uint8Array(32);
  crypto.getRandomValues(bytes);
  return btoa(String.fromCharCode(...bytes))
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '');
};

const createChallenge = () => {
  const bytes = new Uint8Array(32);
  crypto.getRandomValues(bytes);
  return btoa(String.fromCharCode(...bytes));
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await AppIntegrity.isAvailable();
    console.log('available:', available);
  });
  document
    .querySelector('#prepareIntegrityToken')
    .addEventListener('click', async () => {
      await AppIntegrity.prepareIntegrityToken({ cloudProjectNumber });
      console.log('Integrity token provider prepared.');
    });
  document
    .querySelector('#requestIntegrityTokenStandard')
    .addEventListener('click', async () => {
      // In a real app, the request hash is derived from the request
      // that you want to protect.
      const { token } = await AppIntegrity.requestIntegrityToken({
        requestHash: 'exampleRequestHash',
      });
      console.log('token:', token);
    });
  document
    .querySelector('#requestIntegrityTokenClassic')
    .addEventListener('click', async () => {
      // In a real app, the nonce is received from your server.
      const { token } = await AppIntegrity.requestIntegrityToken({
        nonce: createNonce(),
      });
      console.log('token:', token);
    });
  document.querySelector('#generateKey').addEventListener('click', async () => {
    ({ keyId } = await AppIntegrity.generateKey());
    console.log('keyId:', keyId);
  });
  document.querySelector('#attestKey').addEventListener('click', async () => {
    // In a real app, the challenge is received from your server.
    const { attestationObject } = await AppIntegrity.attestKey({
      keyId,
      challenge: createChallenge(),
    });
    console.log('attestationObject:', attestationObject);
  });
  document
    .querySelector('#generateAssertion')
    .addEventListener('click', async () => {
      // In a real app, the client data includes a challenge received
      // from your server.
      const clientData = btoa(JSON.stringify({ challenge: createChallenge() }));
      const { assertion } = await AppIntegrity.generateAssertion({
        keyId,
        clientData,
      });
      console.log('assertion:', assertion);
    });
});
