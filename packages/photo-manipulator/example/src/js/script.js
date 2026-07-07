import { Capacitor } from '@capacitor/core';
import { Directory, Filesystem } from '@capacitor/filesystem';
import { PhotoManipulator } from '@capawesome/capacitor-photo-manipulator';

const blobToBase64 = blob =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result.split(',')[1]);
    reader.onerror = reject;
    reader.readAsDataURL(blob);
  });

// Stage the bundled test image at a real file path on native platforms
const getPathForAsset = async fileName => {
  if (!Capacitor.isNativePlatform()) {
    return `assets/${fileName}`;
  }
  const response = await fetch(`assets/${fileName}`);
  const blob = await response.blob();
  await Filesystem.writeFile({
    path: fileName,
    directory: Directory.Cache,
    data: await blobToBase64(blob),
  });
  const { uri } = await Filesystem.getUri({
    path: fileName,
    directory: Directory.Cache,
  });
  return uri;
};

document.addEventListener('DOMContentLoaded', () => {
  const presentResult = result => {
    document.querySelector('#result').textContent = JSON.stringify(result);
  };
  const presentError = error => {
    document.querySelector('#result').textContent = `${error}`;
  };
  document.querySelector('#getInfo').addEventListener('click', async () => {
    try {
      const path = await getPathForAsset(
        document.querySelector('#source').value,
      );
      const result = await PhotoManipulator.getInfo({ path });
      presentResult(result);
    } catch (error) {
      presentError(error);
    }
  });
  document.querySelector('#transform').addEventListener('click', async () => {
    try {
      const path = await getPathForAsset(
        document.querySelector('#source').value,
      );
      const options = {
        path,
        format: document.querySelector('#format').value,
        flipHorizontal: document.querySelector('#flipHorizontal').checked,
        flipVertical: document.querySelector('#flipVertical').checked,
        rotate: parseInt(document.querySelector('#rotate').value, 10),
      };
      const resizeWidth = document.querySelector('#resizeWidth').value;
      if (resizeWidth) {
        options.resize = { width: parseInt(resizeWidth, 10) };
      }
      if (document.querySelector('#crop').checked) {
        const info = await PhotoManipulator.getInfo({ path });
        const size = Math.min(info.width, info.height);
        options.crop = {
          x: Math.floor((info.width - size) / 2),
          y: Math.floor((info.height - size) / 2),
          width: size,
          height: size,
        };
      }
      const result = await PhotoManipulator.transform(options);
      presentResult(result);
      document.querySelector('#preview').src = Capacitor.isNativePlatform()
        ? Capacitor.convertFileSrc(result.path)
        : result.path;
    } catch (error) {
      presentError(error);
    }
  });
});
