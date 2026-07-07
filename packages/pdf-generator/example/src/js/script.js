import { PdfGenerator } from '@capawesome/capacitor-pdf-generator';

const html = `
  <html>
    <body>
      <h1>Invoice</h1>
      <p>Thank you for your purchase!</p>
      <table border="1" cellpadding="8" cellspacing="0" width="100%">
        <tr><th align="left">Item</th><th align="right">Price</th></tr>
        <tr><td>Capacitor Plugin</td><td align="right">$ 49.00</td></tr>
        <tr><td>Support</td><td align="right">$ 99.00</td></tr>
      </table>
    </body>
  </html>
`;

document.addEventListener('DOMContentLoaded', () => {
  const presentResult = result => {
    document.querySelector('#path').textContent = result.path;
  };
  document
    .querySelector('#generateFromHtml')
    .addEventListener('click', async () => {
      const result = await PdfGenerator.generateFromHtml({
        html,
        fileName: 'invoice.pdf',
        pageSize: document.querySelector('#pageSize').value,
        orientation: document.querySelector('#orientation').value,
      });
      presentResult(result);
    });
  document
    .querySelector('#generateFromUrl')
    .addEventListener('click', async () => {
      const result = await PdfGenerator.generateFromUrl({
        url: document.querySelector('#url').value,
        pageSize: document.querySelector('#pageSize').value,
        orientation: document.querySelector('#orientation').value,
      });
      presentResult(result);
    });
});
