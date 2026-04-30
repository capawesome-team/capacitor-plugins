import { Formbricks } from '@capawesome/capacitor-formbricks';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Formbricks.echo({ value: inputValue })
}
