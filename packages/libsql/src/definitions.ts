export interface LibsqlPlugin {
  beginTransaction(): Promise<void>;
  commitTransaction(): Promise<void>;
  connect(options: ConnectOptions): Promise<ConnectResult>;
  execute(options: ExecuteOptions): Promise<void>;
  executeBatch(options: ExecuteBatchOptions): Promise<void>;
  query(options: QueryOptions): Promise<QueryResult>;
  rollbackTransaction(): Promise<void>;
  sync(): Promise<void>;
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

export type Value = string | number | null;
