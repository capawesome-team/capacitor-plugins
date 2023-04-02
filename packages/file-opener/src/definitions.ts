export interface FileOpenerPlugin {
  /**
   * Open a file with the default application.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  openFile(options: OpenFileOptions): Promise<void>;
}

export interface OpenFileOptions {
  /**
   * The path of the file.
   *
   * @since 0.0.1
   * @example '/storage/emulated/0/DCIM/Camera/IMG_20220808_1234.jpg'
   */
  path: string;
  /**
   * The mime type of the file.
   * If not specified, the mime type will be determined.
   *
   * @since 0.0.1
   * @example 'image/jpeg'
   */
  mimeType?: string;
}
