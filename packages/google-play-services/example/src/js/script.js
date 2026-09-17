import { GooglePlayServices } from '@capawesome/capacitor-google-play-services';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const run = async action => {
  try {
    setResult(await action());
  } catch (error) {
    setResult(`Error (${error.code}): ${error.message}`);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#is-available').addEventListener('click', () =>
    run(async () => {
      const { available } = await GooglePlayServices.isAvailable();
      return `Available: ${available}`;
    }),
  );
  document.querySelector('#get-status').addEventListener('click', () =>
    run(async () => {
      const { status } = await GooglePlayServices.getStatus();
      return `Status: ${status}`;
    }),
  );
  document.querySelector('#get-version').addEventListener('click', () =>
    run(async () => {
      const { version } = await GooglePlayServices.getVersion();
      return `Version: ${version}`;
    }),
  );
  document.querySelector('#make-available').addEventListener('click', () =>
    run(async () => {
      await GooglePlayServices.makeAvailable();
      return 'Google Play Services is available';
    }),
  );
});
