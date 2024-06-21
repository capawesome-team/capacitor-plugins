export interface FileOpenerPlugin {
  /**
   * Open a file with the default application.
   *
   * @since 0.0.1
   */
  openFile(options: OpenFileOptions): Promise<void>;
}

export interface OpenFileOptions {
  /**
   * The blob instance of the file to open.
   *
   * Only available on Web.
   *
   * @since 6.1.0
   */
  blob?: Blob;
  /**
   * The path of the file.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   * @example 'content://com.android.providers.downloads.documents/document/msf%3A1000000073'
   */
  path?: string;
  /**
   * The mime type of the file.
   * If not specified, the mime type will be determined.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   * @example 'image/jpeg'
   */
  mimeType?: string;
}
