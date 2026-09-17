import { OptionPicker } from '@capawesome/capacitor-option-picker';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const getTheme = () => document.querySelector('#theme').value;

const run = async action => {
  try {
    const { value } = await action();
    setResult(value);
  } catch (error) {
    setResult(`Error (${error.code}): ${error.message}`);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#present').addEventListener('click', () =>
    run(() =>
      OptionPicker.present({
        title: 'Select a country',
        theme: getTheme(),
        options: [
          { label: 'Germany', value: 'de' },
          { label: 'France', value: 'fr' },
          { label: 'Spain', value: 'es' },
        ],
        value: 'fr',
      }),
    ),
  );
  document.querySelector('#presentLongList').addEventListener('click', () =>
    run(() =>
      OptionPicker.present({
        title: 'Select a year',
        theme: getTheme(),
        doneButtonText: 'Done',
        options: Array.from({ length: 100 }, (_, index) => {
          const year = String(2026 - index);
          return { label: year, value: year };
        }),
      }),
    ),
  );
});
