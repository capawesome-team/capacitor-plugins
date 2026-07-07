import { ThermalState } from '@capawesome/capacitor-thermal-state';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getThermalState')
    .addEventListener('click', async () => {
      const { state } = await ThermalState.getThermalState();
      console.log('state:', state);
    });
  document
    .querySelector('#addListeners')
    .addEventListener('click', async () => {
      await ThermalState.addListener('thermalStateChange', event => {
        console.log('thermalStateChange:', event.state);
      });
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await ThermalState.removeAllListeners();
    });
});
