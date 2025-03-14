import { Permissions } from '@capawesome/capacitor-permissions';

window.testEcho = () => {
  const inputValue = document.getElementById('echoInput').value;
  Permissions.echo({ value: inputValue });
};
