import { Libsql } from '@capawesome/capacitor-libsql';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Libsql.echo({ value: inputValue })
}
