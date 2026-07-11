import { AppIcon } from '@capawesome/capacitor-app-icon';

document.addEventListener('DOMContentLoaded', () => {
  document.querySelector('#isAvailable').addEventListener('click', async () => {
    const { available } = await AppIcon.isAvailable();
    console.log('available:', available);
  });
  document
    .querySelector('#getCurrentIcon')
    .addEventListener('click', async () => {
      const { icon } = await AppIcon.getCurrentIcon();
      console.log('icon:', icon);
    });
  document.querySelector('#setIcon').addEventListener('click', async () => {
    await AppIcon.setIcon({ icon: 'Alternate' });
    console.log('Icon changed.');
  });
  document.querySelector('#resetIcon').addEventListener('click', async () => {
    await AppIcon.resetIcon();
    console.log('Icon reset.');
  });
});
