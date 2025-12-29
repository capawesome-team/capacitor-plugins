import { Superwall } from '@capawesome/capacitor-superwall';

window.testEcho = () => {
  const inputValue = document.getElementById('echoInput').value;
  Superwall.echo({ value: inputValue });
};
