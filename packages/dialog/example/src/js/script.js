import { Dialog } from '@capawesome/capacitor-dialog';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#alert').addEventListener('click', async () => {
    await Dialog.alert({
      title: 'Success',
      message: 'Your changes have been saved.',
    });
    setResult('Alert dismissed');
  });
  document.querySelector('#confirm').addEventListener('click', async () => {
    const { value } = await Dialog.confirm({
      title: 'Confirm',
      message: 'Do you want to delete this item?',
    });
    setResult(`Confirmed: ${value}`);
  });
  document.querySelector('#prompt').addEventListener('click', async () => {
    const { value, canceled } = await Dialog.prompt({
      title: 'Name',
      message: 'What is your name?',
      inputPlaceholder: 'Enter your name',
    });
    setResult(`Value: ${value}, Canceled: ${canceled}`);
  });
});
