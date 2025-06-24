import { FilePicker } from '@capawesome/capacitor-file-picker';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#copy-file').addEventListener('click', async () => {
    const from = (await FilePicker.pickFiles()).files[0].path;
    const to = (await FilePicker.pickDirectory()).path;
    await FilePicker.copyFile({ from, to });
  });
  document.querySelector('#pick-files').addEventListener('click', async () => {
    await FilePicker.pickFiles();
  });
  document.querySelector('#pick-images').addEventListener('click', async () => {
    await FilePicker.pickImages();
  });
});
