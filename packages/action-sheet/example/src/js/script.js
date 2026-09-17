import {
  ActionSheet,
  ActionSheetButtonStyle,
  ErrorCode,
} from '@capawesome/capacitor-action-sheet';

const setResult = value => {
  document.querySelector('#result').textContent = `Result: ${value}`;
};

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#showActions').addEventListener('click', async () => {
    try {
      const { index } = await ActionSheet.showActions({
        title: 'Photo Options',
        message: 'Select an option to perform.',
        options: [
          { title: 'Upload' },
          { title: 'Share' },
          { title: 'Delete', style: ActionSheetButtonStyle.Destructive },
          { title: 'Cancel', style: ActionSheetButtonStyle.Cancel },
        ],
      });
      setResult(`Index: ${index}`);
    } catch (error) {
      if (error.code === ErrorCode.Canceled) {
        setResult('Canceled');
      } else {
        setResult(`Error: ${error.message}`);
      }
    }
  });
});
