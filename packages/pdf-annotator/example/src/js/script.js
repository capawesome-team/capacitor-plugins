import { Directory, Filesystem } from '@capacitor/filesystem';
import { PdfAnnotator } from '@capawesome/capacitor-pdf-annotator';
import { FileOpener } from '@capawesome-team/capacitor-file-opener';

let annotatedFilePath;

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

const annotate = async path => {
  try {
    const result = await PdfAnnotator.open({ path });
    annotatedFilePath = result.path;
    setResult(`Annotated file: ${result.path}`);
  } catch (error) {
    setResult(`Error (${error.code}): ${error.message}`);
  }
};

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#is-available')
    .addEventListener('click', async () => {
      const { available } = await PdfAnnotator.isAvailable();
      setResult(`Available: ${available}`);
    });
  document.querySelector('#open').addEventListener('click', async () => {
    await annotate(await getSampleFilePath());
  });
  document
    .querySelector('#open-annotated')
    .addEventListener('click', async () => {
      if (!annotatedFilePath) {
        setResult('Annotate and save a file first');
        return;
      }
      await FileOpener.openFile({ path: annotatedFilePath });
    });
  document
    .querySelector('#annotate-annotated')
    .addEventListener('click', async () => {
      if (!annotatedFilePath) {
        setResult('Annotate and save a file first');
        return;
      }
      await annotate(annotatedFilePath);
    });
});
