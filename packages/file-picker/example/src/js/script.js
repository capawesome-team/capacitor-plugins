import { Capacitor } from '@capacitor/core';
import { Directory, Filesystem } from '@capacitor/filesystem';
import { FilePicker } from '@capawesome/capacitor-file-picker';

const displayFile = file => {
  document.querySelector('#name-input').value = file.name ?? '';
  document.querySelector('#mime-type-input').value = file.mimeType ?? '';
  document.querySelector('#size-input').value = file.size ?? '';
  document.querySelector('#path-input').value = file.path ?? '';
  document.querySelector('#modified-at-input').value = file.modifiedAt ?? '';
  document.querySelector('#duration-input').value = file.duration ?? '';
  document.querySelector('#width-input').value = file.width ?? '';
  document.querySelector('#height-input').value = file.height ?? '';

  const image = document.querySelector('#image');
  if (!file.mimeType?.startsWith('image/')) {
    image.src = '';
  } else if (Capacitor.getPlatform() === 'web' && file.blob) {
    image.src = URL.createObjectURL(file.blob);
  } else if (file.path) {
    image.src = Capacitor.convertFileSrc(file.path);
  } else {
    image.src = '';
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#copy-file').addEventListener('click', async () => {
    const file = (await FilePicker.pickFiles()).files[0];
    displayFile(file);
    const from = file.path;
    const { uri } = await Filesystem.getUri({
      path: file.name,
      directory: Directory.Documents,
    });
    await FilePicker.copyFile({ from, to: uri });
  });
  document.querySelector('#pick-files').addEventListener('click', async () => {
    const { files } = await FilePicker.pickFiles();
    displayFile(files[0]);
  });
  document.querySelector('#pick-images').addEventListener('click', async () => {
    const { files } = await FilePicker.pickImages();
    displayFile(files[0]);
  });
  document.querySelector('#pick-media').addEventListener('click', async () => {
    const { files } = await FilePicker.pickMedia();
    displayFile(files[0]);
  });
  document.querySelector('#pick-videos').addEventListener('click', async () => {
    const { files } = await FilePicker.pickVideos();
    displayFile(files[0]);
  });
});
