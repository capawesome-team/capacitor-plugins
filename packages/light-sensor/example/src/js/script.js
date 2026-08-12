import { LightSensor } from '@capawesome/capacitor-light-sensor';

document.addEventListener('DOMContentLoaded', () => {
  LightSensor.addListener('measurement', event => {
    console.log('measurement:', event);
  });

  document
    .querySelector('#getMeasurement')
    .addEventListener('click', async () => {
      const measurement = await LightSensor.getMeasurement();
      console.log('measurement:', measurement);
    });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await LightSensor.isAvailable();
    console.log('available:', available);
  });
  document
    .querySelector('#startMeasurementUpdates')
    .addEventListener('click', async () => {
      await LightSensor.startMeasurementUpdates();
    });
  document
    .querySelector('#stopMeasurementUpdates')
    .addEventListener('click', async () => {
      await LightSensor.stopMeasurementUpdates();
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await LightSensor.removeAllListeners();
    });
});
