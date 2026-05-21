import { GrafanaFaro } from '@capawesome/capacitor-grafana-faro';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    GrafanaFaro.echo({ value: inputValue })
}
