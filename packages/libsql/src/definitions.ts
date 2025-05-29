export interface LibsqlPlugin {
  /**
   * Begin a transaction on the specified database connection.
   *
   * @since 0.1.0
   */
  beginTransaction(options: BeginTransactionOptions): Promise<void>;
  /**
   * Commit the current transaction on the specified database connection.
   *
   * @since 0.1.0
   */
  commitTransaction(options: CommitTransactionOptions): Promise<void>;
  /**
   * Connect to a database.
   *
   * This method must be called before any other methods that interact with the database.
   *
   * @since 0.1.0
   */
  connect(options: ConnectOptions): Promise<ConnectResult>;
  /**
   * Execute a single SQL statement on the specified database connection.
   *
   * This method can be used to execute any SQL statement, including `INSERT`, `UPDATE`, `DELETE`, and `CREATE TABLE`.
   *
   * @since 0.1.0
   */
  execute(options: ExecuteOptions): Promise<void>;
  /**
   * Execute a batch of SQL statements on the specified database connection.
   *
   * This method can be used to execute multiple SQL statements in a single call.
   *
   * @since 0.1.0
   */
  executeBatch(options: ExecuteBatchOptions): Promise<void>;
  /**
   * Query the database and return the result set.
   *
   * This method can be used to execute `SELECT` statements and retrieve the result set.
   *
   * @since 0.1.0
   */
  query(options: QueryOptions): Promise<QueryResult>;
  /**
   * Rollback the current transaction on the specified database connection.
   *
   * This method will undo all changes made in the current transaction.
   *
   * @since 0.1.0
   */
  rollbackTransaction(options: RollbackTransactionOptions): Promise<void>;
  /**
   * Synchronize the database with the remote server.
   *
   * @since 0.1.0
   */
  sync(options: SyncOptions): Promise<void>;
}

export interface BeginTransactionOptions {
  connectionId: string;
}

export interface CommitTransactionOptions {
  connectionId: string;
}

export interface ConnectOptions {
  authToken?: string;
  /**
   * The path to the database file.
   *
   * If no file exists at the specified path, a new file will be created.
   * If no path or URL is provided, the plugin will create a new in-memory database.
   *
   * @since 0.1.0
   * @example '/data/user/0/com.example.plugin/cache/data.db'
   */
  path?: string;
  /**
   * The URL of the database.
   *
   * If no path or URL is provided, the plugin will create a new in-memory database.
   *
   * @since 0.1.0
   */
  url?: string;
}

export interface ConnectResult {
  connectionId: string;
}

export interface ExecuteOptions {
  connectionId: string;
  statement: string;
  transactionId?: string;
  values?: Value[];
}

export interface ExecuteBatchOptions {
  connectionId: string;
  statement: string[];
  values?: Value[][];
}

export interface QueryOptions {
  connectionId: string;
  statement: string;
  transactionId?: string;
  values?: Value[];
}

export interface QueryResult {
  columns: string[];
  rows: Record<string, Value>[];
}

export interface RollbackTransactionOptions {
  connectionId: string;
}

export interface SyncOptions {
  connectionId: string;
}

export type Value = string | number | null;
