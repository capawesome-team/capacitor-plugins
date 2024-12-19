import { AppShortcut } from '@capawesome/capacitor-app-shortcut';

document.addEventListener('DOMContentLoaded', () => {
  const initialize = async () => {
    await AppShortcut.removeAllListeners().then(() => {
      void AppShortcut.addListener('onAppShortcut', event => {
        alert(`Shortcut: ${event.id}`);
      });
    });
  };

  initialize().then(() => {
    document.querySelector('#clear').addEventListener('click', async () => {
      await AppShortcut.clear();
    });
    document.querySelector('#get').addEventListener('click', async () => {
      await AppShortcut.get();
    });
    document.querySelector('#set').addEventListener('click', async () => {
      await AppShortcut.set({
        shortcuts: [
          {
            description: 'Let us know how we can improve',
            id: 'feedback',
            title: 'Feedback',
          },
        ],
      });
    });
  });
});
