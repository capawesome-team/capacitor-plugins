import { Directory, Filesystem } from '@capacitor/filesystem';
import { PdfViewer } from '@capawesome/capacitor-pdf-viewer';

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

const getSampleFilePath = async () => {
  const response = await fetch('assets/sample.pdf');
  const blob = await response.blob();
  const { uri } = await Filesystem.writeFile({
    path: 'sample.pdf',
    data: await blobToBase64(blob),
    directory: Directory.Cache,
  });
  return uri;
};

document.addEventListener('DOMContentLoaded', () => {
  PdfViewer.addListener('closed', () => {
    setResult('Viewer closed');
  });
  PdfViewer.addListener('pageChange', event => {
    setResult(`Page changed: ${event.page}`);
  });
  document.querySelector('#open').addEventListener('click', async () => {
    await PdfViewer.open({
      path: await getSampleFilePath(),
      title: 'Sample',
    });
  });
  document.querySelector('#open-page-2').addEventListener('click', async () => {
    await PdfViewer.open({
      path: await getSampleFilePath(),
      page: 2,
    });
  });
  document
    .querySelector('#open-and-close')
    .addEventListener('click', async () => {
      await PdfViewer.open({
        path: await getSampleFilePath(),
      });
      setTimeout(() => PdfViewer.close(), 3000);
    });
});
