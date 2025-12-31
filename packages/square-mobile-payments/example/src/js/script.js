import { SquareMobilePayments } from '@capawesome/capacitor-square-mobile-payments';

window.testEcho = () => {
  const inputValue = document.getElementById('echoInput').value;
  SquareMobilePayments.echo({ value: inputValue });
};
