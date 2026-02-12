import { Pixlive } from '@capawesome/capacitor-pixlive';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Pixlive.echo({ value: inputValue })
}
