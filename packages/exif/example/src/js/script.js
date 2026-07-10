import { Directory, Filesystem } from '@capacitor/filesystem';
import { Exif } from '@capawesome/capacitor-exif';

const fileNames = ['exif-sample.jpg', 'exif-sample.heic', 'exif-sample.png'];

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const blobToBase64 = blob =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result.split(',')[1]);
    reader.onerror = reject;
    reader.readAsDataURL(blob);
  });

const stageFile = async fileName => {
  const response = await fetch(`assets/${fileName}`);
  const blob = await response.blob();
  const { uri } = await Filesystem.writeFile({
    path: fileName,
    data: await blobToBase64(blob),
    directory: Directory.Cache,
  });
  return uri;
};

const getFilePath = async fileName => {
  try {
    const { uri } = await Filesystem.stat({
      path: fileName,
      directory: Directory.Cache,
    });
    return uri;
  } catch {
    return stageFile(fileName);
  }
};

const getSelectedFileName = () => document.querySelector('#image').value;

const runWithResult = async callback => {
  try {
    await callback();
  } catch (error) {
    setResult(error.message || error);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#read-exif').addEventListener('click', () =>
    runWithResult(async () => {
      const path = await getFilePath(getSelectedFileName());
      const { tags } = await Exif.readExif({ path });
      setResult(JSON.stringify(tags, null, 2));
    }),
  );
  document.querySelector('#write-exif').addEventListener('click', () =>
    runWithResult(async () => {
      const path = await getFilePath(getSelectedFileName());
      await Exif.writeExif({
        path,
        tags: {
          gpsLatitude: 48.137154,
          gpsLongitude: 11.576124,
          software: 'Capawesome Exif Example',
        },
      });
      setResult('EXIF written');
    }),
  );
  document.querySelector('#remove-exif').addEventListener('click', () =>
    runWithResult(async () => {
      const path = await getFilePath(getSelectedFileName());
      await Exif.removeExif({ path });
      setResult('EXIF removed');
    }),
  );
  document
    .querySelector('#remove-exif-and-orientation')
    .addEventListener('click', () =>
      runWithResult(async () => {
        const path = await getFilePath(getSelectedFileName());
        await Exif.removeExif({ path, keepOrientation: false });
        setResult('EXIF removed (incl. orientation)');
      }),
    );
  document.querySelector('#reset-files').addEventListener('click', () =>
    runWithResult(async () => {
      for (const fileName of fileNames) {
        await stageFile(fileName);
      }
      setResult('Sample files reset');
    }),
  );
});
