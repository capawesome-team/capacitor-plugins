export interface AssetManagerPlugin {
  copy(options: CopyOptions): Promise<void>;
  list(options?: ListOptions): Promise<ListResult>;
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
