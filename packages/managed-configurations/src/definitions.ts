export interface ManagedConfigurationsPlugin {
  /**
   * Fetches the value associated with the given key, or `null` if no mapping exists for the given key.
   *
   * Only available on Android and iOS.
   */
  getString(options: GetOptions): Promise<GetResult<string>>;
  /**
   * Fetches the value associated with the given key, or `null` if no mapping exists for the given key.
   *
   * Only available on Android and iOS.
   */
  getNumber(options: GetOptions): Promise<GetResult<number>>;
  /**
   * Fetches the value associated with the given key, or `null` if no mapping exists for the given key.
   *
   * Only available on Android and iOS.
   */
  getBoolean(options: GetOptions): Promise<GetResult<boolean>>;
}

export interface GetOptions {
  /**
   * Unique key for the configuration entry.
   */
  key: string;
}

export interface GetResult<T> {
  /**
   * The value of the configuration entry, or `null` if no mapping exists for the given key.
   */
  value: T | null;
}
