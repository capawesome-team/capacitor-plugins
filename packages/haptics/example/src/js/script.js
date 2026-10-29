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
    .querySelector('#performAndroidHapticFeedback')
    .addEventListener('click', async () => {
      const type = document.querySelector('#androidHapticFeedbackType').value;
      await Haptics.performAndroidHapticFeedback({ type });
    });
  document.querySelector('#playPattern').addEventListener('click', async () => {
    await Haptics.playPattern({
      events: [
        { time: 0, intensity: 1, sharpness: 0.8 },
        { time: 200, intensity: 0.6, sharpness: 0.4 },
        { time: 400, intensity: 0.8, duration: 500 },
      ],
    });
  });
  document
    .querySelector('#playAndroidComposition')
    .addEventListener('click', async () => {
      await Haptics.playAndroidComposition({
        primitives: [
          { type: 'TICK', scale: 0.5 },
          { type: 'CLICK', delay: 50 },
          { type: 'SLOW_RISE', delay: 300 },
          { type: 'THUD' },
        ],
      });
    });
  document
    .querySelector('#playCurvePattern')
    .addEventListener('click', async () => {
      await Haptics.playPattern({
        events: [
          { time: 0, intensity: 1, sharpness: 0.3, duration: 1000 },
          { time: 1000, intensity: 1, sharpness: 0.9 },
        ],
        parameterCurves: [
          {
            parameter: 'INTENSITY',
            controlPoints: [
              { time: 0, value: 0.1 },
              { time: 1000, value: 1 },
            ],
          },
        ],
      });
    });
});
