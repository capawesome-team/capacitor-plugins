import { AppShortcuts } from '@capawesome/capacitor-app-shortcuts';

document.addEventListener('DOMContentLoaded', () => {
  const initialize = async () => {
    await AppShortcuts.removeAllListeners().then(() => {
      void AppShortcuts.addListener('onAppShortcut', event => {
        alert(`Shortcut: ${event.id}`);
      });
    });
  };

  initialize().then(() => {
    document.querySelector('#clear').addEventListener('click', async () => {
      await AppShortcuts.clear();
    });
    document.querySelector('#get').addEventListener('click', async () => {
      await AppShortcuts.get();
    });
    document.querySelector('#set').addEventListener('click', async () => {
      await AppShortcuts.set({
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
