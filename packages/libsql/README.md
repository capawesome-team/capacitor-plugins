# @capawesome/capacitor-libsql

Capacitor plugin for libSQL databases.

## Installation

```bash
npm install @capawesome/capacitor-libsql
npx cap sync
```

## Usage

```typescript
import { Libsql } from '@capawesome/capacitor-libsql';

const echo = async () => {
  await Libsql.echo();
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

This method can be used to execute any SQL statement, including `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`.

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

This method can be used to execute `SELECT` statements and retrieve the result set.

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

Only available on iOS.

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

| Prop               | Type                   | Description                                                         | Since |
| ------------------ | ---------------------- | ------------------------------------------------------------------- | ----- |
| **`connectionId`** | <code>string</code>    | The ID of the connection to execute the batch on.                   | 0.0.0 |
| **`statement`**    | <code>string[]</code>  | The SQL statements to execute in the batch.                         | 0.0.0 |
| **`values`**       | <code>Value[][]</code> | The transaction ID to use for the batch. Only available on Android. | 0.0.0 |


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
