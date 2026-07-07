import {
  ActionSheet,
  ActionSheetButtonStyle,
} from '@capawesome/capacitor-action-sheet';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#showActions').addEventListener('click', async () => {
    const { index, canceled } = await ActionSheet.showActions({
      title: 'Photo Options',
      message: 'Select an option to perform.',
      options: [
        { title: 'Upload' },
        { title: 'Share' },
        { title: 'Delete', style: ActionSheetButtonStyle.Destructive },
        { title: 'Cancel', style: ActionSheetButtonStyle.Cancel },
      ],
    });
    setResult(`Index: ${index}, Canceled: ${canceled}`);
  });
});
