import { DeviceInfo } from '@capawesome/capacitor-device-info';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#getId').addEventListener('click', async () => {
    const { identifier } = await DeviceInfo.getId();
    console.log('identifier:', identifier);
  });
  document.querySelector('#getInfo').addEventListener('click', async () => {
    const info = await DeviceInfo.getInfo();
    console.log('info:', info);
  });
  document.querySelector('#getUptime').addEventListener('click', async () => {
    const { uptime } = await DeviceInfo.getUptime();
    console.log('uptime:', uptime);
  });
});
