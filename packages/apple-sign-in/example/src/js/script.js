import { AppleSignIn } from '@capawesome/capacitor-apple-sign-in';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AppleSignIn.echo({ value: inputValue })
}
