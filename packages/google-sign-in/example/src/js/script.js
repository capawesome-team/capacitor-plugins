import { GoogleSignIn } from '@capawesome/capacitor-google-sign-in';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    GoogleSignIn.echo({ value: inputValue })
}
