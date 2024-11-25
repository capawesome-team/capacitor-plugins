import { AppReview } from '@capawesome/capacitor-app-review';

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
      <button id="request-review">Request Review</button>
    </main>
    `;
    }

    connectedCallback() {
      const self = this;

      self.shadowRoot
        .querySelector('#open-app-store')
        .addEventListener('click', async function () {
          await AppReview.openAppStore({
            appId: '1203299771',
          });
        });
      self.shadowRoot
        .querySelector('#request-review')
        .addEventListener('click', async function () {
          await AppReview.requestReview();
        });
    }
  },
);
