import { AppShortcuts } from '@capawesome/capacitor-app-shortcuts';
import { Capacitor } from '@capacitor/core';

document.addEventListener('DOMContentLoaded', () => {
  const initialize = async () => {
    await AppShortcuts.removeAllListeners().then(() => {
      void AppShortcuts.addListener('click', event => {
        alert(`Shortcut clicked: ${event.shortcutId}`);
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
            icon: Capacitor.getPlatform() === 'ios' ? 6 : 17301547,
          },
        ],
      });
    });
  });
});
