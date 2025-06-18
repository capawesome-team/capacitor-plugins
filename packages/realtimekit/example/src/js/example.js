import { RealtimeKit } from '@capawesome/capacitor-realtimekit';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    RealtimeKit.echo({ value: inputValue })
}
