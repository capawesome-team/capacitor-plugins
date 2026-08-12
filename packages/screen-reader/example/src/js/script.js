import { ScreenReader } from '@capawesome/capacitor-screen-reader';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#announce').addEventListener('click', async () => {
    const value = document.querySelector('#announceValue').value;
    await ScreenReader.announce({ value });
  });
  document.querySelector('#isEnabled').addEventListener('click', async () => {
    const { enabled } = await ScreenReader.isEnabled();
    window.alert(`Screen reader enabled: ${enabled}`);
  });

  ScreenReader.addListener('stateChange', event => {
    document.querySelector('#state').textContent = `State: ${
      event.enabled ? 'enabled' : 'disabled'
    }`;
  });
});
