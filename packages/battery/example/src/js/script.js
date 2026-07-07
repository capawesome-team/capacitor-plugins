import { Battery } from '@capawesome/capacitor-battery';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#getBatteryLevel')
    .addEventListener('click', async () => {
      const { level } = await Battery.getBatteryLevel();
      console.log('level:', level);
    });
  document
    .querySelector('#getBatteryState')
    .addEventListener('click', async () => {
      const { state } = await Battery.getBatteryState();
      console.log('state:', state);
    });
  document
    .querySelector('#isLowPowerModeEnabled')
    .addEventListener('click', async () => {
      const { enabled } = await Battery.isLowPowerModeEnabled();
      console.log('enabled:', enabled);
    });
  document
    .querySelector('#addListeners')
    .addEventListener('click', async () => {
      await Battery.addListener('batteryLevelChange', event => {
        console.log('batteryLevelChange:', event.level);
      });
      await Battery.addListener('batteryStateChange', event => {
        console.log('batteryStateChange:', event.state);
      });
      await Battery.addListener('lowPowerModeChange', event => {
        console.log('lowPowerModeChange:', event.enabled);
      });
    });
  document
    .querySelector('#removeAllListeners')
    .addEventListener('click', async () => {
      await Battery.removeAllListeners();
    });
});
