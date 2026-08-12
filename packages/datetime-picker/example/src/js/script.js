import { DatetimePicker } from '@capawesome-team/capacitor-datetime-picker';

const getValue = id => {
  const el = document.querySelector(`#${id}`);
  const value = el?.value;
  return value === '' || value == null ? undefined : value;
};

const showResult = data => {
  const card = document.querySelector('#result-card');
  const pre = document.querySelector('#result');
  pre.textContent = JSON.stringify(data, null, 2);
  card.style.display = 'block';
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#present').addEventListener('click', async () => {
    const minuteIntervalRaw = getValue('minuteInterval');
    const options = {
      mode: getValue('mode'),
      theme: getValue('theme'),
      locale: getValue('locale'),
      format: getValue('format'),
      value: getValue('value'),
      min: getValue('min'),
      max: getValue('max'),
      cancelButtonText: getValue('cancelButtonText'),
      doneButtonText: getValue('doneButtonText'),
      minuteInterval: minuteIntervalRaw ? Number(minuteIntervalRaw) : undefined,
      androidDatePickerMode: getValue('androidDatePickerMode'),
      androidTimePickerMode: getValue('androidTimePickerMode'),
    };
    Object.keys(options).forEach(
      key => options[key] === undefined && delete options[key],
    );
    try {
      const result = await DatetimePicker.present(options);
      console.log('present result:', result);
      showResult(result);
    } catch (error) {
      console.error('present error:', error);
      showResult({ error: error?.message ?? String(error) });
    }
  });

  document.querySelector('#cancel').addEventListener('click', async () => {
    await DatetimePicker.cancel();
    console.log('cancel called');
  });
});
