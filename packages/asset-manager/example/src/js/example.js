import { AssetManager } from '@capawesome/capacitor-asset-manager';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AssetManager.echo({ value: inputValue })
}
