import { Flic } from '@capawesome/capacitor-flic';

document.addEventListener('DOMContentLoaded', () => {
  const eventNames = [
    'buttonConnected',
    'buttonConnectionFailed',
    'buttonDisconnected',
    'buttonDoubleClick',
    'buttonDown',
    'buttonHold',
    'buttonReady',
    'buttonSingleClick',
    'buttonUnpaired',
    'buttonUp',
    'scanStatusChanged',
  ];
  for (const eventName of eventNames) {
    Flic.addListener(eventName, event => {
      console.log(eventName, event);
    });
  }

  document.querySelector('#initialize').addEventListener('click', async () => {
    await Flic.initialize({ iosBackground: true });
    window.alert('Initialized');
  });
  document
    .querySelector('#checkPermissions')
    .addEventListener('click', async () => {
      const permissionStatus = await Flic.checkPermissions();
      window.alert(JSON.stringify(permissionStatus, null, 2));
    });
  document
    .querySelector('#requestPermissions')
    .addEventListener('click', async () => {
      const permissionStatus = await Flic.requestPermissions();
      window.alert(JSON.stringify(permissionStatus, null, 2));
    });
  document.querySelector('#startScan').addEventListener('click', async () => {
    const { button } = await Flic.startScan();
    window.alert(`Paired button: ${JSON.stringify(button, null, 2)}`);
  });
  document.querySelector('#stopScan').addEventListener('click', async () => {
    await Flic.stopScan();
  });
  document.querySelector('#getButtons').addEventListener('click', async () => {
    const { buttons } = await Flic.getButtons();
    window.alert(JSON.stringify(buttons, null, 2));
  });
  document
    .querySelector('#connectButtonById')
    .addEventListener('click', async () => {
      const id = document.querySelector('#buttonId').value;
      await Flic.connectButtonById({ id });
    });
  document
    .querySelector('#disconnectButtonById')
    .addEventListener('click', async () => {
      const id = document.querySelector('#buttonId').value;
      await Flic.disconnectButtonById({ id });
    });
  document
    .querySelector('#forgetButtonById')
    .addEventListener('click', async () => {
      const id = document.querySelector('#buttonId').value;
      await Flic.forgetButtonById({ id });
    });
});
