import { ProximitySensor } from '@capawesome/capacitor-proximity-sensor';

document.addEventListener('DOMContentLoaded', () => {
  ProximitySensor.addListener('measurement', event => {
    console.log('measurement:', event);
  });

  document
    .querySelector('#getMeasurement')
    .addEventListener('click', async () => {
      const measurement = await ProximitySensor.getMeasurement();
      console.log('measurement:', measurement);
    });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await ProximitySensor.isAvailable();
    console.log('available:', available);
  });
  document
    .querySelector('#startMeasurementUpdates')
    .addEventListener('click', async () => {
      await ProximitySensor.startMeasurementUpdates();
    });
  document
    .querySelector('#stopMeasurementUpdates')
    .addEventListener('click', async () => {
      await ProximitySensor.stopMeasurementUpdates();
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await ProximitySensor.removeAllListeners();
    });
});
