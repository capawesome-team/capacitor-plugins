import { Passkeys } from '@capawesome/capacitor-passkeys';

// The relying party ID must be a domain that your app is associated with.
// On Web, `localhost` works for local development.
const RP_ID = 'localhost';
const RP_NAME = 'Capawesome';
const USER_NAME = 'jane.doe@example.com';

const createChallenge = () => {
  const bytes = new Uint8Array(32);
  crypto.getRandomValues(bytes);
  return toBase64Url(bytes);
};

const toBase64Url = bytes => {
  let binary = '';
  for (const byte of bytes) {
    binary += String.fromCharCode(byte);
  }
  return btoa(binary)
    .replace(/\+/g, '-')
    .replace(/\//g, '_')
    .replace(/=+$/, '');
};

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#createPasskey')
    .addEventListener('click', async () => {
      // Note: In a real app, the options must be provided
      // by your WebAuthn server.
      const result = await Passkeys.createPasskey({
        attestation: 'none',
        authenticatorSelection: {
          residentKey: 'required',
          userVerification: 'required',
        },
        challenge: createChallenge(),
        pubKeyCredParams: [
          { alg: -7, type: 'public-key' },
          { alg: -257, type: 'public-key' },
        ],
        rp: { id: RP_ID, name: RP_NAME },
        user: {
          displayName: 'Jane Doe',
          id: toBase64Url(new TextEncoder().encode(USER_NAME)),
          name: USER_NAME,
        },
      });
      console.log('createPasskey result:', result);
    });
  document.querySelector('#getPasskey').addEventListener('click', async () => {
    // Note: In a real app, the options must be provided
    // by your WebAuthn server.
    const result = await Passkeys.getPasskey({
      challenge: createChallenge(),
      rpId: RP_ID,
      userVerification: 'required',
    });
    console.log('getPasskey result:', result);
  });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await Passkeys.isAvailable();
    console.log('available:', available);
  });
});
