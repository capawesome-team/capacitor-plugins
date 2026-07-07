import { Toast } from '@capawesome/capacitor-toast';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#show').addEventListener('click', async () => {
    const text = document.querySelector('#text').value;
    const duration = document.querySelector('#duration').value;
    const position = document.querySelector('#position').value;
    await Toast.show({ text, duration, position });
  });
});
