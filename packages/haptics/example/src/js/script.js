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
  document
    .querySelector('#playPrimitivePattern')
    .addEventListener('click', async () => {
      await Haptics.playPattern({
        events: [
          { time: 0, intensity: 0.5, primitive: 'TICK' },
          { time: 0.1, intensity: 1, primitive: 'CLICK' },
          { time: 0.5, intensity: 1, primitive: 'SLOW_RISE' },
          { time: 1, intensity: 1, primitive: 'THUD' },
        ],
      });
    });
  document
    .querySelector('#playCurvePattern')
    .addEventListener('click', async () => {
      await Haptics.playPattern({
        events: [
          { time: 0, intensity: 1, sharpness: 0.3, duration: 1 },
          { time: 1, intensity: 1, sharpness: 0.9 },
        ],
        parameterCurves: [
          {
            parameter: 'INTENSITY',
            controlPoints: [
              { time: 0, value: 0.1 },
              { time: 1, value: 1 },
            ],
          },
        ],
      });
    });
});
