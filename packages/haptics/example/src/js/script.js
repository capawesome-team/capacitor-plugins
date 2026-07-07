import { Haptics } from '@capawesome/capacitor-haptics';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await Haptics.isAvailable();
    window.alert(`Haptics available: ${available}`);
  });
  document.querySelector('#impact').addEventListener('click', async () => {
    const style = document.querySelector('#impactStyle').value;
    await Haptics.impact({ style });
  });
  document
    .querySelector('#notification')
    .addEventListener('click', async () => {
      const type = document.querySelector('#notificationType').value;
      await Haptics.notification({ type });
    });
  document.querySelector('#vibrate').addEventListener('click', async () => {
    const duration = parseInt(
      document.querySelector('#vibrateDuration').value,
      10,
    );
    await Haptics.vibrate({ duration });
  });
  document
    .querySelector('#selectionStart')
    .addEventListener('click', async () => {
      await Haptics.selectionStart();
    });
  document
    .querySelector('#selectionChanged')
    .addEventListener('click', async () => {
      await Haptics.selectionChanged();
    });
  document
    .querySelector('#selectionEnd')
    .addEventListener('click', async () => {
      await Haptics.selectionEnd();
    });
  document
    .querySelector('#performAndroidHaptic')
    .addEventListener('click', async () => {
      const type = document.querySelector('#androidHapticType').value;
      await Haptics.performAndroidHaptic({ type });
    });
  document.querySelector('#playPattern').addEventListener('click', async () => {
    await Haptics.playPattern({
      events: [
        { time: 0, intensity: 1, sharpness: 0.8 },
        { time: 0.2, intensity: 0.6, sharpness: 0.4 },
        { time: 0.4, intensity: 0.8, duration: 0.5 },
      ],
    });
  });
});
