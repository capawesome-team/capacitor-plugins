import { Libsql } from '@capawesome/capacitor-libsql';

document.addEventListener('DOMContentLoaded', () => {
  let connectionId;
  let transactionId;

  document
    .querySelector('#begin-transaction-button')
    .addEventListener('click', async () => {
      const result = await Libsql.beginTransaction({ connectionId });
      transactionId = result.transactionId;
      console.log('Begin transaction result:', result);
    });
  document
    .querySelector('#commit-transaction-button')
    .addEventListener('click', async () => {
      await Libsql.commitTransaction({ connectionId, transactionId });
    });
  document
    .querySelector('#connect-button')
    .addEventListener('click', async () => {
      const result = await Libsql.connect({ path: 'mylocal.db' });
      connectionId = result.connectionId;
      console.log('Connect result:', result);
    });
  document
    .querySelector('#execute-button')
    .addEventListener('click', async () => {
      await Libsql.execute({
        connectionId,
        statement:
          'CREATE TABLE IF NOT EXISTS test (key TEXT PRIMARY KEY, value TEXT)',
        transactionId,
      });
      await Libsql.execute({
        connectionId,
        statement: 'INSERT OR REPLACE INTO test (key, value) VALUES (?, ?)',
        transactionId,
        values: ['key1', 'value1'],
      });
    });
  document
    .querySelector('#execute-batch-button')
    .addEventListener('click', async () => {
      await Libsql.executeBatch({
        connectionId,
        statement: [
          'CREATE TABLE IF NOT EXISTS test (key TEXT PRIMARY KEY, value TEXT)',
          'INSERT OR REPLACE INTO test (key, value) VALUES (?, ?)'
        ],
        values: [[], ['key1', 'value1']],
      });
    });
  document
    .querySelector('#rollback-transaction-button')
    .addEventListener('click', async () => {
      await Libsql.rollbackTransaction({ connectionId, transactionId });
    });
  document
    .querySelector('#query-button')
    .addEventListener('click', async () => {
      const result = await Libsql.query({
        connectionId,
        statement: 'SELECT * FROM test',
        transactionId,
      });
      console.log('Query result:', result);
    });
  document
    .querySelector('#sync-button')
    .addEventListener('click', async () => {
      await Libsql.sync({ connectionId })
    })
});
