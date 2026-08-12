import { Sim } from '@capawesome/capacitor-sim';

document.addEventListener('DOMContentLoaded', () => {
  document
    .querySelector('#checkPermissions')
    .addEventListener('click', async () => {
      const { readSimCards } = await Sim.checkPermissions();
      console.log('readSimCards:', readSimCards);
    });
  document
    .querySelector('#requestPermissions')
    .addEventListener('click', async () => {
      const { readSimCards } = await Sim.requestPermissions();
      console.log('readSimCards:', readSimCards);
    });
  document.querySelector('#getSimCards').addEventListener('click', async () => {
    const { simCards } = await Sim.getSimCards();
    console.log('simCards:', simCards);
  });
});
