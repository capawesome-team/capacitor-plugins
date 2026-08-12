import { Alarm } from '@capawesome/capacitor-alarm';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await Alarm.isAvailable();
    window.alert(`Available: ${available}`);
  });
  document
    .querySelector('#checkPermissions')
    .addEventListener('click', async () => {
      const { alarms } = await Alarm.checkPermissions();
      window.alert(`Permission state: ${alarms}`);
    });
  document
    .querySelector('#requestPermissions')
    .addEventListener('click', async () => {
      const { alarms } = await Alarm.requestPermissions();
      window.alert(`Permission state: ${alarms}`);
    });
  document.querySelector('#createAlarm').addEventListener('click', async () => {
    const hour = parseInt(document.querySelector('#hour').value, 10);
    const minute = parseInt(document.querySelector('#minute').value, 10);
    const label = document.querySelector('#label').value || undefined;
    const days = document.querySelector('#days').value;
    const { id } = await Alarm.createAlarm({
      hour,
      minute,
      label,
      days: days && days.length > 0 ? days : undefined,
    });
    window.alert(`Created alarm with ID: ${id}`);
  });
  document.querySelector('#createTimer').addEventListener('click', async () => {
    const duration = parseInt(document.querySelector('#duration').value, 10);
    const label = document.querySelector('#label').value || undefined;
    await Alarm.createTimer({ duration, label });
  });
  document.querySelector('#getAlarms').addEventListener('click', async () => {
    const { alarms } = await Alarm.getAlarms();
    window.alert(`Alarms: ${JSON.stringify(alarms, null, 2)}`);
  });
  document.querySelector('#cancelAlarm').addEventListener('click', async () => {
    const id = document.querySelector('#alarmId').value;
    await Alarm.cancelAlarm({ id });
  });
  document.querySelector('#openAlarms').addEventListener('click', async () => {
    await Alarm.openAlarms();
  });
});
