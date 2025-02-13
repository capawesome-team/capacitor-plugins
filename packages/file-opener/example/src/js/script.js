import { FileOpener } from '@capawesome-team/capacitor-file-opener';
import { FilePicker } from '@capawesome/capacitor-file-picker';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#open-file').addEventListener('click', async () => {
    const result = await FilePicker.pickFiles({
      limit: 1,
    });
    await FileOpener.openFile({
      blob: result.files[0].blob,
    });
  });
});
