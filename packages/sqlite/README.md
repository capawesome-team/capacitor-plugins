# @capawesome-team/capacitor-sqlite

Capacitor plugin to access SQLite databases with support for encryption, transactions, and schema migrations.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins to access SQLite databases. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS and Web.
- 🔒 **Encryption**: Supports 256 bit AES encryption with custom keys.
- 📖 **Read-only mode**: Open databases in read-only mode to prevent modifications.
- 📂 **File-based**: Open existing databases or create new ones with a file path.
- 📦 **In-memory databases**: Create temporary in-memory databases for quick operations or testing.
- 📈 **Schema migrations**: Automatically apply schema migrations when opening a database.
- 🔄 **Transactions**: Supports transactions with `beginTransaction(...)`, `commitTransaction(...)`, and `rollbackTransaction(...)`.
- 🔍 **Querying**: Execute SQL queries with `query(...)` and `execute(...)`.
- 🔢 **Data Types**: Supports all SQLite data types: `NULL`, `INTEGER`, `REAL`, `TEXT`, and `BLOB`.
- 🛡️ **Prepared Statements**: Uses prepared statements to prevent SQL injection attacks.
- 🕸️ **SQLite WASM**: Uses SQLite WebAssembly for web platform support.
- 📝 **Full Text Search**: Supports full text search with [FTS5](https://www.sqlite.org/fts5.html).
- 🗃️ **ORM Support**: Works with popular ORMs like TypeORM, Drizzle, and Kysely.
- 🤝 **Compatibility**: Compatible with the [Secure Preferences](https://capawesome.io/plugins/secure-preferences/) plugin.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll add it for you!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.3.x          | >=8.x.x           | Active support |
| 0.2.x          | >=8.x.x           | Deprecated     |
| 0.1.x          | 7.x.x             | Deprecated     |

## Guides

- [Alternative to the Capacitor Community SQLite plugin](https://capawesome.io/blog/alternative-to-capacitor-community-sqlite-plugin/)
- [Alternative to the Ionic Secure Storage plugin](https://capawesome.io/blog/alternative-to-ionic-secure-storage-plugin/)
- [Announcing the SQLite Plugin for Capacitor](https://capawesome.io/blog/announcing-the-capacitor-sqlite-plugin/)
- [Encrypting SQLite databases in Capacitor](https://capawesome.io/blog/encrypting-capacitor-sqlite-database/)
- [Exploring the Capacitor SQLite API](https://capawesome.io/blog/exploring-the-capacitor-sqlite-api/)

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-sqlite @sqlite.org/sqlite-wasm
npx cap sync
```

### Android

#### Encryption

If you want to use encryption, you must include the SQLCipher dependency in your app's `variables.gradle` file by setting the `capawesomeCapacitorSqliteIncludeSqlcipher` variable to `true`:

```diff
ext {
+  capawesomeCapacitorSqliteIncludeSqlcipher = true // Default: false
}
```

**Attention**: When using SQLCipher you are responsible for compliance with all export, re-export and import restrictions and regulations in all applicable countries. You can find more information about this in this [blog post](https://discuss.zetetic.net/t/export-requirements-for-applications-using-sqlcipher/47).

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxSqliteVersion` version of `androidx.sqlite:sqlite` (default: `2.6.2`)
- `$androidxSqliteFrameworkAndroidVersion` version of `androidx.sqlite:sqlite-framework-android` (default: `2.6.2`)
- `$netZeteticSqlcipherVersion` version of `net.zetetic:sqlcipher-android` (default: `4.12.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Encryption

If you want to use encryption, you must include the `SQLCipher` dependency in your app's `Podfile` by adding the following line:

```diff
target 'App' do
capacitor_pods
# Add your Pods here
+  pod 'CapawesomeTeamCapacitorSqlite/SQLCipher', :path => '../../node_modules/@capawesome-team/capacitor-sqlite'
end
```

**Attention**: Encryption is only supported with CocoaPods and not with Swift Package Manager (SPM). If you need database encryption, you must use CocoaPods for the Capacitor iOS platform. See the [Limitations](#limitations) section for more details.

**Attention**: When using SQLCipher you are responsible for compliance with all export, re-export and import restrictions and regulations in all applicable countries. You can find more information about this in this [blog post](https://discuss.zetetic.net/t/export-requirements-for-applications-using-sqlcipher/47).

If you do NOT want to use encryption, you need to add the `Plain` pod to your app's `Podfile` by adding the following line:

```diff
target 'App' do
capacitor_pods
# Add your Pods here
+  pod 'CapawesomeTeamCapacitorSqlite/Plain', :path => '../../node_modules/@capawesome-team/capacitor-sqlite'
end
```

**Attention**: In both cases, do not add the pod in the section `def capacitor_pods`, but under the comment `# Add your Pods here`.

### Web

This plugin uses the [@sqlite.org/sqlite-wasm](https://www.npmjs.com/package/@sqlite.org/sqlite-wasm) package to provide SQLite support on the web platform. It will automatically load the SQLite WASM module when needed.

#### Usage with Angular

If you are using Angular, you need to add the following configuration to your `angular.json` file to ensure the SQLite WASM module is copied to the assets folder during the build process and to set the necessary headers for the web worker:

```diff
{
  "projects": {
    "your-app-name": {
      "architect": {
        "build": {
          "options": {
            "assets": [
+              {
+                "glob": "**/*",
+                "input": "node_modules/@sqlite.org/sqlite-wasm/dist/",
+                "output": "/assets/sqlite-wasm/"
+              }
            ]
          }
        },
        "serve": {
+          "options": {
+            "headers": {
+              "Cross-Origin-Embedder-Policy": "require-corp",
+              "Cross-Origin-Opener-Policy": "same-origin"
+            }
+          }
        }
      }
    }
  }
}
```

Finally, you need to initialize the SQLite WASM module before using the plugin. You can do this in your `main.ts` file or in a service that is loaded at the start of your application:

```typescript
import { Capacitor } from '@capacitor/core';
import { Sqlite } from '@capawesome-team/capacitor-sqlite';

const initialize = async () => {
  const isWeb = Capacitor.getPlatform() === 'web';
  if (isWeb) {
    // Initialize the SQLite WASM module
    await Sqlite.initialize({
      worker: new Worker('/assets/sqlite-wasm/sqlite3-worker1.mjs', { type: 'module' })
    });
  }
};
```

#### Usage with Vite

If you are using Vite, you need to add the following configuration to your `vite.config.ts` file to ensure the SQLite WASM module is loaded correctly:

```typescript
import { defineConfig } from 'vite';

export default defineConfig({
  optimizeDeps: {
    exclude: ['@sqlite.org/sqlite-wasm'],
  },
  server: {
    headers: {
      'Cross-Origin-Embedder-Policy': 'require-corp',
      'Cross-Origin-Opener-Policy': 'same-origin',
    },
  },
});
```

## Configuration

No configuration required for this plugin.

## Usage

```typescript
import { Capacitor } from '@capacitor/core';
import { Directory, Filesystem } from '@capacitor/filesystem';
import { Sqlite } from '@capawesome-team/capacitor-sqlite';

const open = async () => {
  const { databaseId } = await Sqlite.open({
    encryptionKey: 'secret', // Tip: Use the Secure Preferences plugin to store the key securely
    readOnly: false,
    path: 'mydb.sqlite3',
    upgradeStatements: [
      {
        version: 1,
        statements: [
          'CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)',
        ],
      },
      {
        version: 2,
        statements: ['ALTER TABLE users ADD COLUMN email TEXT'],
      },
    ],
    version: 2,
  });
};

const execute = async () => {
  const { databaseId } = await Sqlite.open();
  await Sqlite.execute({
    databaseId,
    statement: 'INSERT INTO users (name, age) VALUES (?, ?)',
    values: ['Alice', 30],
  });
};

const query = async () => {
  const { databaseId } = await Sqlite.open();
  const result = await Sqlite.query({
    databaseId,
    statement: 'SELECT * FROM users WHERE age > ?',
    values: [25],
  });
  console.log(result.columns); // The column names in the result set
  console.log(result.rows); // The rows returned by the query
};

const performTransaction = async () => {
  const { databaseId } = await Sqlite.open();
  await Sqlite.beginTransaction({ databaseId });
  await Sqlite.execute({
    databaseId,
    statement: 'INSERT INTO users (name, age) VALUES (?, ?)',
    values: ['Alice', 30],
  });
  await Sqlite.execute({
    databaseId,
    statement: 'INSERT INTO users (name, age) VALUES (?, ?)',
    values: ['Bob', 25],
  });
  await Sqlite.commitTransaction({ databaseId });
};

const close = async () => {
  const { databaseId } = await Sqlite.open();
  await Sqlite.close({ databaseId });
};

const changeEncryptionKey = async () => {
  // Open the database with the old encryption key
  const { databaseId } = await Sqlite.open({
    encryptionKey: 'old-secret',
  });
  // Change the encryption key to a new one
  await Sqlite.changeEncryptionKey({
    databaseId,
    encryptionKey: 'new-secret',
  });
};

const getVersion = async () => {
  const result = await Sqlite.getVersion();
  console.log(result.version); // The version of the SQLite library used by the plugin
};

const vacuum = async () => {
  const { databaseId } = await Sqlite.open();
  await Sqlite.vacuum({ databaseId });
};
```

## API

<docgen-index>

* [`beginTransaction(...)`](#begintransaction)
* [`changeEncryptionKey(...)`](#changeencryptionkey)
* [`close(...)`](#close)
* [`commitTransaction(...)`](#committransaction)
* [`execute(...)`](#execute)
* [`getVersion()`](#getversion)
* [`initialize(...)`](#initialize)
* [`open(...)`](#open)
* [`query(...)`](#query)
* [`rollbackTransaction(...)`](#rollbacktransaction)
* [`vacuum(...)`](#vacuum)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### beginTransaction(...)

```typescript
beginTransaction(options: BeginTransactionOptions) => Promise<void>
```

Begin a transaction on the specified database.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#begintransactionoptions">BeginTransactionOptions</a></code> |

**Since:** 0.1.0

--------------------


### changeEncryptionKey(...)

```typescript
changeEncryptionKey(options: ChangeEncryptionKeyOptions) => Promise<void>
```

Change the encryption key of the database.

**Attention**: This must be called after opening the database with the current encryption key.

Only available on Android and iOS.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#changeencryptionkeyoptions">ChangeEncryptionKeyOptions</a></code> |

**Since:** 0.1.0

--------------------


### close(...)

```typescript
close(options: CloseOptions) => Promise<void>
```

Close the specified database.

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#closeoptions">CloseOptions</a></code> |

**Since:** 0.1.0

--------------------


### commitTransaction(...)

```typescript
commitTransaction(options: CommitTransactionOptions) => Promise<void>
```

Commit the current transaction on the specified database.

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#committransactionoptions">CommitTransactionOptions</a></code> |

**Since:** 0.1.0

--------------------


### execute(...)

```typescript
execute(options: ExecuteOptions) => Promise<ExecuteResult>
```

Execute a single SQL statement on the specified database.

This method can be used to execute any SQL statement, including `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#executeoptions">ExecuteOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#executeresult">ExecuteResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getVersion()

```typescript
getVersion() => Promise<GetVersionResult>
```

Get the version of the SQLite library used by the plugin.

To get the version of the database schema, simply run the `PRAGMA user_version;` command.

**Returns:** <code>Promise&lt;<a href="#getversionresult">GetVersionResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### initialize(...)

```typescript
initialize(options?: InitializeOptions | undefined) => Promise<void>
```

Initialize the plugin before any other method is called.

Use this method to customize the plugin's behavior.
If this method is not called, the plugin will be automatically
initialized with default settings on the first call to any other method.

On **Android** and **iOS**, this method is a no-op.

On **Web**, this method allows you to pass a `Worker` instance
that will be used for the SQLite WebAssembly initialization.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#initializeoptions">InitializeOptions</a></code> |

**Since:** 0.1.3

--------------------


### open(...)

```typescript
open(options?: OpenOptions | undefined) => Promise<OpenResult>
```

Open a database with the specified options.

This method can be used to open an existing database or create a new one.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#openoptions">OpenOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#openresult">OpenResult</a>&gt;</code>

**Since:** 0.1.0

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

**Since:** 0.1.0

--------------------


### rollbackTransaction(...)

```typescript
rollbackTransaction(options: RollbackTransactionOptions) => Promise<void>
```

Rollback the current transaction on the specified database.

This method will undo all changes made in the current transaction.

Only available on Android.

| Param         | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#rollbacktransactionoptions">RollbackTransactionOptions</a></code> |

**Since:** 0.1.0

--------------------


### vacuum(...)

```typescript
vacuum(options: VacuumOptions) => Promise<void>
```

Runs the [VACUUM](https://www.sqlite.org/lang_vacuum.html) command to rebuild the database file.

This command can be used to reclaim unused space and optimize the database file.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#vacuumoptions">VacuumOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### BeginTransactionOptions

| Prop             | Type                | Description                                                       | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`databaseId`** | <code>string</code> | The unique identifier for the database to begin a transaction on. | 0.1.0 |


#### ChangeEncryptionKeyOptions

| Prop                | Type                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                     | Since |
| ------------------- | ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`databaseId`**    | <code>string</code> | The unique identifier for the database to change the encryption key for.                                                                                                                                                                                                                                                                                                                                                                        | 0.1.0 |
| **`encryptionKey`** | <code>string</code> | The new encryption key to set for the database. **Attention:** It's recommended to use a strong encryption key to protect sensitive data. This key should be kept secret and not hard-coded in your application. If you lose the encryption key, you will not be able to access the data in the database. **Tip:** Use the [Secure Preferences](https://capawesome.io/plugins/secure-preferences/) plugin to securely store the encryption key. | 0.1.0 |


#### CloseOptions

| Prop             | Type                | Description                                      | Since |
| ---------------- | ------------------- | ------------------------------------------------ | ----- |
| **`databaseId`** | <code>string</code> | The unique identifier for the database to close. | 0.1.0 |


#### CommitTransactionOptions

| Prop             | Type                | Description                                                        | Since |
| ---------------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`databaseId`** | <code>string</code> | The unique identifier for the database to commit a transaction on. | 0.1.0 |


#### ExecuteResult

| Prop          | Type                | Description                                                                                                         | Since |
| ------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------- | ----- |
| **`changes`** | <code>number</code> | The number of rows modified by the statement. This property is set for `INSERT`, `UPDATE`, and `DELETE` statements. | 0.1.1 |
| **`rowId`**   | <code>number</code> | The row ID of the last inserted row. This property is only set when executing an `INSERT` statement.                | 0.1.1 |


#### ExecuteOptions

| Prop                | Type                 | Description                                                                                                                                                                   | Default           | Since |
| ------------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`databaseId`**    | <code>string</code>  | The unique identifier for the database to execute the statement on.                                                                                                           |                   | 0.1.0 |
| **`returnChanges`** | <code>boolean</code> | Whether to return the number of rows modified by the statement. Disabling this option can improve performance for statements that do not require the number of modified rows. | <code>true</code> | 0.1.2 |
| **`returnRowId`**   | <code>boolean</code> | Whether to return the row ID of the last inserted row. Disabling this option can improve performance for statements that do not require the row ID of the last inserted row.  | <code>true</code> | 0.1.2 |
| **`statement`**     | <code>string</code>  | The SQL statement to execute. This can be any valid SQL statement, such as `INSERT`, `UPDATE`, `DELETE`, or `CREATE TABLE`.                                                   |                   | 0.1.0 |
| **`values`**        | <code>Value[]</code> | Only available on Android.                                                                                                                                                    |                   |       |


#### GetVersionResult

| Prop          | Type                | Description                                           | Since |
| ------------- | ------------------- | ----------------------------------------------------- | ----- |
| **`version`** | <code>string</code> | The version of the SQLite library used by the plugin. | 0.1.0 |


#### InitializeOptions

| Prop         | Type                | Description                                                                                                                                                                                                                                | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`worker`** | <code>Worker</code> | The Worker to use for the SQLite WebAssembly initialization. If provided, this worker will be passed to the sqlite3Worker1Promiser method for initializing the SQLite WebAssembly module in the web implementation. Only available on Web. | 0.1.3 |


#### OpenResult

| Prop             | Type                | Description                                  | Since |
| ---------------- | ------------------- | -------------------------------------------- | ----- |
| **`databaseId`** | <code>string</code> | A unique identifier for the opened database. | 0.1.0 |


#### OpenOptions

| Prop                    | Type                            | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | Default            | Since |
| ----------------------- | ------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`encryptionKey`**     | <code>string</code>             | The encryption key to use for the database. If provided, the database will be opened as an encrypted database using the specified key. If not provided, the database will be opened as an unencrypted database. **Attention:** It's recommended to use a strong encryption key to protect sensitive data. This key should be kept secret and not hard-coded in your application. If you lose the encryption key, you will not be able to access the data in the database. **Tip:** Use the [Secure Preferences](https://capawesome.io/plugins/secure-preferences/) plugin to securely store the encryption key. Only available on Android and iOS.                                                                                                                                                                                                                                 |                    | 0.1.0 |
| **`readOnly`**          | <code>boolean</code>            | Whether the database should be opened in read-only mode. Only available on Android and iOS.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | <code>false</code> | 0.1.0 |
| **`path`**              | <code>string</code>             | The path to the database file. If no file exists at the specified path, a new file will be created. If no path or URL is provided, the plugin will create a new in-memory database. On **Android**, the path can either be a simple filename or a file URI. If a simple filename is provided, the plugin will create the database in the default database directory (see [getDatabasePath](https://developer.android.com/reference/android/content/Context#getDatabasePath(java.lang.String))). On **iOS**, the path can either be a simple filename or a file URL. If a simple filename is provided, the plugin will create the database in the default documents directory (see [documentsDirectory](https://developer.apple.com/documentation/foundation/url/documentsdirectory)). On **Web**, the path should be a simple filename without a directory (e.g., `mydb.sqlite3`). |                    | 0.1.0 |
| **`upgradeStatements`** | <code>UpgradeStatement[]</code> | An array of upgrade statements to apply when opening the database. Each statement should specify the version of the database schema it applies to and the SQL statements to execute for the upgrade. The current version of the database schema can be checked using the `PRAGMA user_version;` command.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |                    | 0.1.0 |
| **`version`**           | <code>number</code>             | The version of the database schema. If provided, the plugin will check the schema version and apply migrations if necessary. If not provided, the latest version of the upgrade statements will be used, if any. If neither `version` nor `upgradeStatements` are provided, the database version will not be managed by the plugin. **Attention:** The version must be 1 or higher.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |                    | 0.1.0 |


#### UpgradeStatement

| Prop             | Type                  | Description                                                                                                                            | Since |
| ---------------- | --------------------- | -------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`version`**    | <code>number</code>   | The version of the database schema that this statement applies to.                                                                     | 0.1.0 |
| **`statements`** | <code>string[]</code> | The SQL statement to execute for the upgrade. This can be any valid SQL statement, such as `ALTER TABLE`, `CREATE TABLE`, or `INSERT`. | 0.1.0 |


#### QueryResult

| Prop          | Type                   | Description                                                                                                                                                   | Default         | Since |
| ------------- | ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------- | ----- |
| **`columns`** | <code>string[]</code>  | The column names in the result set.                                                                                                                           | <code>[]</code> | 0.1.0 |
| **`rows`**    | <code>Value[][]</code> | The rows returned by the query. Each row is represented as an object where the keys are column names and the values are the corresponding values in that row. | <code>[]</code> | 0.1.0 |


#### QueryOptions

| Prop             | Type                 | Description                                                                                                    | Default         | Since |
| ---------------- | -------------------- | -------------------------------------------------------------------------------------------------------------- | --------------- | ----- |
| **`databaseId`** | <code>string</code>  | The unique identifier for the database to query.                                                               |                 | 0.1.0 |
| **`statement`**  | <code>string</code>  | The SQL statement to execute for the query. This should be a `SELECT` statement.                               |                 | 0.1.0 |
| **`values`**     | <code>Value[]</code> | An array of values to bind to the SQL statement. Each value corresponds to a placeholder in the SQL statement. | <code>[]</code> | 0.1.0 |


#### RollbackTransactionOptions

| Prop             | Type                | Description                                                          | Since |
| ---------------- | ------------------- | -------------------------------------------------------------------- | ----- |
| **`databaseId`** | <code>string</code> | The unique identifier for the database to rollback a transaction on. | 0.1.0 |


#### VacuumOptions

| Prop             | Type                | Description                                                          | Since |
| ---------------- | ------------------- | -------------------------------------------------------------------- | ----- |
| **`databaseId`** | <code>string</code> | The unique identifier for the database to run the VACUUM command on. | 0.1.0 |


### Type Aliases


#### Value

Represents a value that can be used in SQL statements.

This can include strings, numbers, arrays of numbers (for BLOBs), or `null`.

**Attention:** On Web, arrays of numbers (BLOBs) are not supported.

<code>string | number | number[] | null</code>

</docgen-api>

## ORMs

### TypeORM

This plugin is compatible with [TypeORM](https://typeorm.io/), a popular ORM for TypeScript and JavaScript. 

```typescript
import { Sqlite, SQLiteConnection } from '@capawesome-team/capacitor-sqlite';
import { DataSource, DataSourceOptions } from 'typeorm';

const createDataSource = async () => {
  return new DataSource({
    database: 'db',
    driver: new SQLiteConnection(Sqlite),
    entities: [
      // Your TypeORM entities here
    ],
    migrationsRun: false, // Required with capacitor type
    type: 'capacitor'
  });
};
```

## Limitations

This plugin has some limitations on certain platforms.

### iOS

The iOS implementation of this plugin has the following limitations:

- **Encryption**: Encryption is only supported with CocoaPods and not with Swift Package Manager (SPM). SQLCipher does not officially support SPM due to technical limitations in its build system (see [sqlcipher/sqlcipher#371](https://github.com/sqlcipher/sqlcipher/issues/371)). If you need database encryption, you must use CocoaPods for the Capacitor iOS platform.

### Web

The web implementation of this plugin has the following limitations:

- **BLOBs**: Arrays of numbers (BLOBs) are not supported. You can only use strings, numbers, and `null` as values in SQL statements.

## Troubleshooting

##### `SQLITE_ERROR: sqlite3 result code 1: no such vfs: opfs`

This error occurs when OPFS (Origin Private File System) cannot be instantiated. This is likely due to the server not sending the required headers for the web worker to be able to access the OPFS. To fix this, you need to add the following headers to your server configuration:

```
'Cross-Origin-Embedder-Policy': 'require-corp'
'Cross-Origin-Opener-Policy': 'same-origin'
```

##### `Sqlite.open()` never resolves in production

If `open()` hangs or never resolves while working in dev mode, this is typically caused by missing COOP/COEP headers in your production deployment. You need to configure your production web server (Netlify, Vercel, Nginx, Apache, etc.) to send these headers:

```
'Cross-Origin-Embedder-Policy': 'require-corp'
'Cross-Origin-Opener-Policy': 'same-origin'
```

To verify this is the issue, open Chrome DevTools → Network tab and check if these headers are present in the response for your HTML document.

##### `No such module 'SQLite'`

This error occurs when the `SQLite` module is not found in your iOS project. To fix this, make sure you have added the `CapawesomeTeamCapacitorSqlite/Plain` or `CapawesomeTeamCapacitorSqlite/SQLCipher` pod to your `Podfile` as described in the [Installation](#ios) section:

```diff
def capacitor_pods
  pod 'CapawesomeTeamCapacitorSqlite', :path => '../../node_modules/@capawesome-team/capacitor-sqlite'
end

target 'App' do
  capacitor_pods
  # Add your Pods here
+  pod 'CapawesomeTeamCapacitorSqlite/Plain', :path => '../../node_modules/@capawesome-team/capacitor-sqlite'
end
```

**Attention**: Both `CapawesomeTeamCapacitorSqlite` and `CapawesomeTeamCapacitorSqlite/Plain` or `CapawesomeTeamCapacitorSqlite/SQLCipher` must be included in the `Podfile`. The first one is required for the plugin to work, while the second one is required for the specific implementation (Plain or SQLCipher).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sqlite/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sqlite/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/sqlite/LICENSE).
