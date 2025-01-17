import { AppUpdate } from '@capawesome/capacitor-app-update';

window.customElements.define(
  'capacitor-welcome',
  class extends HTMLElement {
    constructor() {
      super();

      const root = this.attachShadow({ mode: 'open' });
      root.innerHTML = `
    <main>
      <h1>Capacitor App</h1>
      <p>
        This project is used to test the plugin.
      </p>
      <button id="open-app-store">Open App Store</button>
    </main>
    `;
    }

    connectedCallback() {
      const self = this;

      self.shadowRoot
        .querySelector('#open-app-store')
        .addEventListener('click', async function () {
          await AppUpdate.openAppStore({
            appId: '1203299771',
          });
        });
    }
  },
);
