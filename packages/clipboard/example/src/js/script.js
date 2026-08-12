import { Clipboard } from '@capawesome/capacitor-clipboard';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

const createImageDataUrl = () => {
  const canvas = document.createElement('canvas');
  canvas.width = 100;
  canvas.height = 100;
  const context = canvas.getContext('2d');
  context.fillStyle = '#119EFF';
  context.fillRect(0, 0, 100, 100);
  return canvas.toDataURL('image/png');
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#write-text').addEventListener('click', async () => {
    await Clipboard.write({ text: 'Hello World' });
    setResult('Text written');
  });
  document.querySelector('#write-html').addEventListener('click', async () => {
    await Clipboard.write({ html: '<b>Hello World</b>', text: 'Hello World' });
    setResult('HTML written');
  });
  document.querySelector('#write-image').addEventListener('click', async () => {
    await Clipboard.write({ image: createImageDataUrl() });
    setResult('Image written');
  });
  document.querySelector('#write-url').addEventListener('click', async () => {
    await Clipboard.write({ url: 'https://capawesome.io' });
    setResult('URL written');
  });
  document.querySelector('#read').addEventListener('click', async () => {
    const { type, value } = await Clipboard.read();
    const image = document.querySelector('#image');
    image.src = type === 'IMAGE' ? value : '';
    setResult(`Type: ${type}, Value: ${value}`);
  });
});
