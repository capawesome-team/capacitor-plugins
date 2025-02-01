export interface AssetManagerPlugin {
  /**
   * Copy a file or directory from the app bundle to the app's data directory.
   *
   * Only available on Android and iOS.
   *
   * @since 7.0.0
   */
  copy(options: CopyOptions): Promise<void>;
  /**
   * List files in a directory.
   *
   * Only available on Android and iOS.
   *
   * @since 7.0.0
   */
  list(options?: ListOptions): Promise<ListResult>;
  /**
   * Read a file from the app bundle.
   *
   * **Attention**: Reading large files can cause out of memory (OOM) issues.
   * It is therefore recommended to copy files to the app's data directory
   * using the `copy` method and read them from there using the [fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch).
   *
   * Only available on Android and iOS.
   *
   * @since 7.0.0
   */
  read(options: ReadOptions): Promise<ReadResult>;
}

/**
 * @since 7.0.0
 */
export interface CopyOptions {
  /**
   * The source path to copy from.
   *
   * @since 7.0.0
   * @example 'public/index.html'
   * @example 'capacitor.config.json'
   */
  from: string;
  /**
   * The destination path to copy to.
   *
   * @since 7.0.0
   */
  to: string;
}

/**
 * @since 7.0.0
 */
export interface ListOptions {
  /**
   * The path to list files from. If not specified, the root path will be used.
   *
   * @since 7.0.0
   * @example 'public'
   */
  path?: string;
}

/**
 * @since 7.0.0
 */
export interface ListResult {
  /**
   * The list of files in the directory.
   *
   * @since 7.0.0
   * @example ['/private/var/containers/Bundle/Application/D83E2C08-BBA1-4963-8ED8-806FD92E15B3/App.app/public/index.html']
   */
  files: string[];
}

/**
 * @since 7.0.0
 */
export interface ReadOptions {
  /**
   * The encoding to use when reading the file.
   *
   * @since 7.0.0
   * @default 'base64'
   * @example 'utf8'
   */
  encoding?: 'base64' | 'utf8';
  /**
   * The path to read the file from.
   *
   * @since 7.0.0
   * @example 'public/index.html'
   * @example 'capacitor.config.json'
   */
  path: string;
}

/**
 * @since 7.0.0
 */
export interface ReadResult {
  /**
   * The content of the file.
   *
   * @since 7.0.0
   */
  data: string;
}
