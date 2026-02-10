export interface LibsqlPlugin {
  /**
   * Begin a transaction on the specified database connection.
   *
   * Only available on Android.
   *
   * @since 0.0.0
   */
  beginTransaction(
    options: BeginTransactionOptions,
  ): Promise<BeginTransactionResult>;
  /**
   * Commit the current transaction on the specified database connection.
   *
   * Only available on Android.
   *
   * @since 0.0.0
   */
  commitTransaction(options: CommitTransactionOptions): Promise<void>;
  /**
   * Connect to a database.
   *
   * This method must be called before any other methods that interact with the database.
   *
   * @since 0.0.0
   */
  connect(options: ConnectOptions): Promise<ConnectResult>;
  /**
   * Execute a single SQL statement on the specified database connection.
   *
   * This method can be used to execute any SQL statement, including
   * `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`.
   *
   * @since 0.0.0
   */
  execute(options: ExecuteOptions): Promise<void>;
  /**
   * Execute a batch of SQL statements on the specified database connection.
   *
   * This method can be used to execute multiple SQL statements in a single call.
   *
   * @since 0.0.0
   */
  executeBatch(options: ExecuteBatchOptions): Promise<void>;
  /**
   * Query the database and return the result set.
   *
   * This method can be used to execute `SELECT` statements
   * and retrieve the result set.
   *
   * @since 0.0.0
   */
  query(options: QueryOptions): Promise<QueryResult>;
  /**
   * Rollback the current transaction on the specified database connection.
   *
   * This method will undo all changes made in the current transaction.
   *
   * Only available on Android.
   *
   * @since 0.0.0
   */
  rollbackTransaction(options: RollbackTransactionOptions): Promise<void>;
  /**
   * Synchronize the database with the remote server.
   *
   * Available on iOS and Android.
   *
   * @since 0.0.0
   */
  sync(options: SyncOptions): Promise<void>;
}

/**
 * @since 0.0.0
 */
export interface BeginTransactionOptions {
  /**
   * The ID of the connection to begin the transaction on.
   *
   * @since 0.0.0
   */
  connectionId: string;
}

/**
 * @since 0.0.0
 */
export interface BeginTransactionResult {
  /**
   * The ID of the transaction that was started.
   *
   * @since 0.0.0
   */
  transactionId: string;
}

/**
 * @since 0.0.0
 */
export interface CommitTransactionOptions {
  /**
   * The ID of the connection to commit the transaction on.
   *
   * @since 0.0.0
   */
  connectionId: string;
  /**
   * The ID of the transaction to commit.
   *
   * @since 0.0.0
   */
  transactionId: string;
}

export interface ConnectOptions {
  /**
   * The authentication token for the database.
   *
   * This is required for connecting to a remote database.
   * If the database is local (i.e., a file on the device),
   * this can be omitted.
   *
   * @since 0.0.0
   */
  authToken?: string;
  /**
   * The path to the database file.
   *
   * If no path or URL is provided, the plugin will create
   * a new in-memory database.
   *
   * If no file exists at the specified path,
   * a new file will be created.
   *
   * @since 0.0.0
   * @example '/data/user/0/com.example.plugin/cache/data.db'
   */
  path?: string;
  /**
   * The URL of the database.
   *
   * This can be used to connect to a remote database.
   * If the URL is provided, the `authToken` must also be provided.
   *
   * If no path or URL is provided, the plugin will create
   * a new in-memory database.
   *
   * @since 0.0.0
   */
  url?: string;
}

/**
 * @since 0.0.0
 */
export interface ConnectResult {
  /**
   * The ID of the connection.
   *
   * @since 0.0.0
   */
  connectionId: string;
}

/**
 * @since 0.0.0
 */
export interface ExecuteOptions {
  /**
   * The ID of the connection to execute the SQL statement on.
   *
   * @since 0.0.0
   */
  connectionId: string;
  /**
   * The SQL statement to execute.
   *
   * @since 0.0.0
   * @example 'INSERT INTO users (name, age) VALUES (?, ?)'
   */
  statement: string;
  /**
   * The transaction ID to use for the SQL statement.
   *
   * Only available on Android.
   *
   * @since 0.0.0
   */
  transactionId?: string;
  /**
   * The values to bind to the SQL statement.
   *
   * @since 0.0.0
   * @example ['Alice', 30]
   */
  values?: Value[];
}

/**
 * @since 0.0.0
 */
export interface ExecuteBatchOptions {
  /**
   * The ID of the connection to execute the batch on.
   *
   * @since 0.0.0
   */
  connectionId: string;
  /**
   * The SQL statements to execute in the batch.
   *
   * @since 0.0.0
   */
  statement: string[];
  /**
   * The values to bind to the SQL statements.
   *
   * @since 0.0.0
   */
  values?: Value[][];
}

/**
 * @since 0.0.0
 */
export interface QueryOptions {
  /**
   * The ID of the connection to query.
   *
   * @since 0.0.0
   */
  connectionId: string;
  /**
   * The SQL statement to execute.
   *
   * @since 0.0.0
   * @example 'SELECT name, age FROM users WHERE age > ?'
   */
  statement: string;
  /**
   * The transaction ID to use for the query.
   *
   * Only available on Android.
   *
   * @since 0.0.0
   */
  transactionId?: string;
  /**
   * The values to bind to the SQL statement.
   *
   * @since 0.0.0
   */
  values?: Value[];
}

/**
 * @since 0.0.0
 */
export interface QueryResult {
  /**
   * The values returned by the query.
   *
   * @since 0.0.0
   */
  rows: Value[][];
}

/**
 * @since 0.0.0
 */
export interface RollbackTransactionOptions {
  /**
   * The ID of the connection to rollback the transaction on.
   *
   * @since 0.0.0
   */
  connectionId: string;
  /**
   * The ID of the transaction to rollback.
   *
   * @since 0.0.0
   */
  transactionId: string;
}

/**
 * @since 0.0.0
 */
export interface SyncOptions {
  /**
   * The ID of the connection to sync.
   *
   * @since 0.0.0
   */
  connectionId: string;
}

/**
 * @since 0.0.0
 */
export type Value = string | number | null;
