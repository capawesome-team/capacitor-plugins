import { Vapi } from '@capawesome/capacitor-vapi';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Vapi.echo({ value: inputValue })
}
