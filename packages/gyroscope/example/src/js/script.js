import { Gyroscope } from '@capawesome/capacitor-gyroscope';

document.addEventListener('DOMContentLoaded', () => {
  Gyroscope.addListener('measurement', event => {
    console.log('measurement:', event);
  });

  document
    .querySelector('#getMeasurement')
    .addEventListener('click', async () => {
      const measurement = await Gyroscope.getMeasurement();
      console.log('measurement:', measurement);
    });
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { isAvailable } = await Gyroscope.isAvailable();
    console.log('isAvailable:', isAvailable);
  });
  document
    .querySelector('#startMeasurementUpdates')
    .addEventListener('click', async () => {
      await Gyroscope.startMeasurementUpdates();
    });
  document
    .querySelector('#stopMeasurementUpdates')
    .addEventListener('click', async () => {
      await Gyroscope.stopMeasurementUpdates();
    });
  document
    .querySelector('#checkPermissions')
    .addEventListener('click', async () => {
      const result = await Gyroscope.checkPermissions();
      console.log('checkPermissions:', result);
    });
  document
    .querySelector('#requestPermissions')
    .addEventListener('click', async () => {
      const result = await Gyroscope.requestPermissions();
      console.log('requestPermissions:', result);
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await Gyroscope.removeAllListeners();
    });
});
