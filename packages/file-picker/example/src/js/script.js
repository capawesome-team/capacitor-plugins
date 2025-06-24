import { Directory, Filesystem } from '@capacitor/filesystem';
import { FilePicker } from '@capawesome/capacitor-file-picker';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#copy-file').addEventListener('click', async () => {
    const file = (await FilePicker.pickFiles()).files[0];
    const from = file.path;
    const { uri } = await Filesystem.getUri({
      path: file.name,
      directory: Directory.Documents,
    });
    await FilePicker.copyFile({ from, to: uri });
  });
  document.querySelector('#pick-files').addEventListener('click', async () => {
    await FilePicker.pickFiles();
  });
  document.querySelector('#pick-images').addEventListener('click', async () => {
    await FilePicker.pickImages();
  });
});
