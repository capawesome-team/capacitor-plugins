import { Formbricks } from '@capawesome/capacitor-formbricks';

window.testSetup = async () => {
  await Formbricks.setup({
    appUrl: 'https://app.formbricks.com',
    environmentId: document.getElementById('environmentIdInput').value,
  });
};

window.testTrack = async () => {
  await Formbricks.track({
    action: document.getElementById('actionInput').value,
  });
};
