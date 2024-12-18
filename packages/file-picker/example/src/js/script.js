import { FilePicker } from '@capawesome/capacitor-file-picker';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#pick-files').addEventListener('click', async () => {
    await FilePicker.pickFiles();
  });
  document.querySelector('#pick-images').addEventListener('click', async () => {
    await FilePicker.pickImages();
  });
});
