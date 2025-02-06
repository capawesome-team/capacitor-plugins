import { EdgeToEdge } from '@capawesome/capacitor-android-edge-to-edge';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    EdgeToEdge.echo({ value: inputValue })
}
