# Capacitor libSQL Plugin

Capacitor plugin for [libSQL](https://docs.turso.tech/libsql) databases.[^1]

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The libSQL plugin is typically used whenever an app needs a SQL database, for example:

- **Offline-first apps**: Store data in a local database file on the device and synchronize it with a remote server using the `sync(...)` method.
- **Remote databases**: Connect directly to a remote libSQL database, such as one hosted on Turso, using a URL and authentication token.
- **Structured local storage**: Create tables and insert, update, delete, and query data with SQL statements and bound values.
- **Atomic operations**: Group multiple statements into a transaction that can be committed or rolled back as a whole.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.2.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-libsql` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-libsql
npx cap sync
```

### Android

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$libsqlVersion` version of `tech.turso.libsql:libsql` (default: `0.1.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Usage

Import the plugin and call its methods:

```typescript
import { Libsql } from '@capawesome/capacitor-libsql';
```

### Connect to a local database

Connect to a local database file on the device. If no file exists at the specified path, a new file is created. If neither a path nor a URL is provided, the plugin creates a new in-memory database. This method must be called before any other methods that interact with the database:

```typescript
const connectToLocalDatabase = async () => {
  const { connectionId } = await Libsql.connect({
    path: 'database.db',
  });
  console.log('Connected to database with ID:', connectionId);
};
```

### Connect to a remote database

Connect to a remote libSQL database using its URL and an authentication token:

```typescript
const connectToRemoteDatabase = async () => {
  const { connectionId } = await Libsql.connect({
    url: 'libsql://your-database-url.turso.io',
    authToken: 'your-auth-token',
  });
  console.log('Connected to remote database with ID:', connectionId);
};
```

### Query data

Execute a `SELECT` statement and retrieve the result set:

```typescript
const query = async () => {
  const result = await Libsql.query({
    connectionId: 'my-connection-id',
    statement: 'SELECT * FROM my_table',
  });
  console.log('Query result:', result.rows);
};
```

### Insert, update, and delete data

Execute any SQL statement, including `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`, optionally with bound values:

```typescript
const execute = async () => {
  await Libsql.execute({
    connectionId: 'my-connection-id',
    statement: 'INSERT INTO my_table (column1, column2) VALUES (?, ?)',
    values: ['value1', 'value2'],
  });
  console.log('Insert executed successfully');
};
```

### Run multiple statements in a transaction

Begin a transaction, execute statements as part of it, and either commit or roll back all changes. Transactions are only available on Android:

```typescript
const performTransaction = async () => {
  const { transactionId } = await Libsql.beginTransaction({
    connectionId: 'my-connection-id',
  });
  try {
    await Libsql.execute({
      connectionId: 'my-connection-id',
      statement: 'UPDATE my_table SET column1 = ? WHERE column2 = ?',
      values: ['new_value', 'value2'],
      transactionId,
    });
    await Libsql.commitTransaction({
      connectionId: 'my-connection-id',
      transactionId,
    });
    console.log('Transaction committed successfully');
  } catch (error) {
    await Libsql.rollbackTransaction({
      connectionId: 'my-connection-id',
      transactionId,
    });
    console.error('Transaction rolled back due to error:', error);
  }
};
```

### Synchronize with a remote server

Synchronize the database with the remote server:

```typescript
const sync = async () => {
  await Libsql.sync({
    connectionId: 'my-connection-id',
  });
  console.log('Database synchronized successfully');
};
```

## API

<docgen-index>

* [`beginTransaction(...)`](#begintransaction)
* [`commitTransaction(...)`](#committransaction)
* [`connect(...)`](#connect)
* [`execute(...)`](#execute)
* [`executeBatch(...)`](#executebatch)
* [`query(...)`](#query)
* [`rollbackTransaction(...)`](#rollbacktransaction)
* [`sync(...)`](#sync)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### beginTransaction(...)

```typescript
beginTransaction(options: BeginTransactionOptions) => Promise<BeginTransactionResult>
```

Begin a transaction on the specified database connection.

Only available on Android.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#begintransactionoptions">BeginTransactionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#begintransactionresult">BeginTransactionResult</a>&gt;</code>

**Since:** 0.0.0

--------------------


### commitTransaction(...)

```typescript
commitTransaction(options: CommitTransactionOptions) => Promise<void>
```

Commit the current transaction on the specified database connection.

Only available on Android.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#committransactionoptions">CommitTransactionOptions</a></code> |

**Since:** 0.0.0

--------------------


### connect(...)

```typescript
connect(options: ConnectOptions) => Promise<ConnectResult>
```

Connect to a database.

This method must be called before any other methods that interact with the database.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#connectoptions">ConnectOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#connectresult">ConnectResult</a>&gt;</code>

**Since:** 0.0.0

--------------------


### execute(...)

```typescript
execute(options: ExecuteOptions) => Promise<void>
```

Execute a single SQL statement on the specified database connection.

This method can be used to execute any SQL statement, including
`INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#executeoptions">ExecuteOptions</a></code> |

**Since:** 0.0.0

--------------------


### executeBatch(...)

```typescript
executeBatch(options: ExecuteBatchOptions) => Promise<void>
```

Execute a batch of SQL statements on the specified database connection.

This method can be used to execute multiple SQL statements in a single call.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#executebatchoptions">ExecuteBatchOptions</a></code> |

**Since:** 0.0.0

--------------------


### query(...)

```typescript
query(options: QueryOptions) => Promise<QueryResult>
```

Query the database and return the result set.

This method can be used to execute `SELECT` statements
and retrieve the result set.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#queryoptions">QueryOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#queryresult">QueryResult</a>&gt;</code>

**Since:** 0.0.0

--------------------


### rollbackTransaction(...)

```typescript
rollbackTransaction(options: RollbackTransactionOptions) => Promise<void>
```

Rollback the current transaction on the specified database connection.

This method will undo all changes made in the current transaction.

Only available on Android.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#rollbacktransactionoptions">RollbackTransactionOptions</a></code> |

**Since:** 0.0.0

--------------------


### sync(...)

```typescript
sync(options: SyncOptions) => Promise<void>
```

Synchronize the database with the remote server.

Available on iOS and Android.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#syncoptions">SyncOptions</a></code> |

**Since:** 0.0.0

--------------------


### Interfaces


#### BeginTransactionResult

| Prop                | Type                | Description                                 | Since |
| ------------------- | ------------------- | ------------------------------------------- | ----- |
| **`transactionId`** | <code>string</code> | The ID of the transaction that was started. | 0.0.0 |


#### BeginTransactionOptions

| Prop               | Type                | Description                                           | Since |
| ------------------ | ------------------- | ----------------------------------------------------- | ----- |
| **`connectionId`** | <code>string</code> | The ID of the connection to begin the transaction on. | 0.0.0 |


#### CommitTransactionOptions

| Prop                | Type                | Description                                            | Since |
| ------------------- | ------------------- | ------------------------------------------------------ | ----- |
| **`connectionId`**  | <code>string</code> | The ID of the connection to commit the transaction on. | 0.0.0 |
| **`transactionId`** | <code>string</code> | The ID of the transaction to commit.                   | 0.0.0 |


#### ConnectResult

| Prop               | Type                | Description               | Since |
| ------------------ | ------------------- | ------------------------- | ----- |
| **`connectionId`** | <code>string</code> | The ID of the connection. | 0.0.0 |


#### ConnectOptions

| Prop            | Type                | Description                                                                                                                                                                                                               | Since |
| --------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`authToken`** | <code>string</code> | The authentication token for the database. This is required for connecting to a remote database. If the database is local (i.e., a file on the device), this can be omitted.                                              | 0.0.0 |
| **`path`**      | <code>string</code> | The path to the database file. If no path or URL is provided, the plugin will create a new in-memory database. If no file exists at the specified path, a new file will be created.                                       | 0.0.0 |
| **`url`**       | <code>string</code> | The URL of the database. This can be used to connect to a remote database. If the URL is provided, the `authToken` must also be provided. If no path or URL is provided, the plugin will create a new in-memory database. | 0.0.0 |


#### ExecuteOptions

| Prop                | Type                 | Description                                                                 | Since |
| ------------------- | -------------------- | --------------------------------------------------------------------------- | ----- |
| **`connectionId`**  | <code>string</code>  | The ID of the connection to execute the SQL statement on.                   | 0.0.0 |
| **`statement`**     | <code>string</code>  | The SQL statement to execute.                                               | 0.0.0 |
| **`transactionId`** | <code>string</code>  | The transaction ID to use for the SQL statement. Only available on Android. | 0.0.0 |
| **`values`**        | <code>Value[]</code> | The values to bind to the SQL statement.                                    | 0.0.0 |


#### ExecuteBatchOptions

| Prop               | Type                   | Description                                       | Since |
| ------------------ | ---------------------- | ------------------------------------------------- | ----- |
| **`connectionId`** | <code>string</code>    | The ID of the connection to execute the batch on. | 0.0.0 |
| **`statement`**    | <code>string[]</code>  | The SQL statements to execute in the batch.       | 0.0.0 |
| **`values`**       | <code>Value[][]</code> | The values to bind to the SQL statements.         | 0.0.0 |


#### QueryResult

| Prop       | Type                   | Description                       | Since |
| ---------- | ---------------------- | --------------------------------- | ----- |
| **`rows`** | <code>Value[][]</code> | The values returned by the query. | 0.0.0 |


#### QueryOptions

| Prop                | Type                 | Description                                                         | Since |
| ------------------- | -------------------- | ------------------------------------------------------------------- | ----- |
| **`connectionId`**  | <code>string</code>  | The ID of the connection to query.                                  | 0.0.0 |
| **`statement`**     | <code>string</code>  | The SQL statement to execute.                                       | 0.0.0 |
| **`transactionId`** | <code>string</code>  | The transaction ID to use for the query. Only available on Android. | 0.0.0 |
| **`values`**        | <code>Value[]</code> | The values to bind to the SQL statement.                            | 0.0.0 |


#### RollbackTransactionOptions

| Prop                | Type                | Description                                              | Since |
| ------------------- | ------------------- | -------------------------------------------------------- | ----- |
| **`connectionId`**  | <code>string</code> | The ID of the connection to rollback the transaction on. | 0.0.0 |
| **`transactionId`** | <code>string</code> | The ID of the transaction to rollback.                   | 0.0.0 |


#### SyncOptions

| Prop               | Type                | Description                       | Since |
| ------------------ | ------------------- | --------------------------------- | ----- |
| **`connectionId`** | <code>string</code> | The ID of the connection to sync. | 0.0.0 |


### Type Aliases


#### Value

<code>string | number | null</code>

</docgen-api>

## FAQ

### Can I use this plugin with Turso?

Yes, you can connect to a remote libSQL database hosted on [Turso](https://docs.turso.tech/libsql) by passing the database URL and an authentication token to the `connect(...)` method, as shown in the [usage example](#connect-to-a-remote-database) above.

### Do I need a remote database to use this plugin?

No, the plugin also works with purely local databases. If you pass a `path` to the `connect(...)` method, the plugin uses a database file on the device and creates it if it does not exist. If you provide neither a path nor a URL, the plugin creates a new in-memory database.

### Are transactions supported?

Yes, you can use the `beginTransaction(...)`, `commitTransaction(...)` and `rollbackTransaction(...)` methods to group multiple statements into a transaction. Note that transactions are only available on Android.

### What is the difference between the `query` and `execute` methods?

The `query(...)` method is used to execute `SELECT` statements and retrieve the result set. The `execute(...)` method is used for any other SQL statement, including `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`, and does not return a result set. Both methods support binding values to the statement.

### How do I keep a local database in sync with a remote server?

Call the `sync(...)` method with the ID of the connection you want to synchronize. This synchronizes the database with the remote server and is available on Android and iOS.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [SQLite](https://capawesome.io/docs/sdks/capacitor/sqlite/): Access SQLite databases with support for encryption, transactions, and schema migrations.
- [Secure Preferences](https://capawesome.io/docs/sdks/capacitor/secure-preferences/): Securely store key/value pairs such as passwords or tokens.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by CHISELSTRIKE INC. or any of their affiliates or subsidiaries.
