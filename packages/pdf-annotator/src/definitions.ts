export interface PdfAnnotatorPlugin {
  /**
   * Check whether PDF annotation is available on this device.
   *
   * On **Android**, this resolves to `true` if the device runs Android 11 (API level 30)
   * or higher and its PDF system module supports annotations (SDK extension level 18 or higher).
   * On **iOS**, this always resolves to `true`.
   * On **Web**, this always resolves to `false`.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Open a PDF file in a fullscreen native viewer that lets the user annotate it.
   *
   * The promise resolves with the path of the annotated copy when the user
   * saves the annotations and closes the viewer. If the user closes the viewer
   * without saving, the promise is rejected with the `CANCELED` error code.
   * The original file is never modified.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  open(options: OpenOptions): Promise<OpenResult>;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not PDF annotation is available on this device.
   *
   * @since 0.1.0
   * @example true
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface OpenOptions {
  /**
   * The path of the local PDF file to annotate.
   *
   * Remote URLs are not supported. Download the file first, for example
   * to the cache directory, and pass the local file path to this method.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/document.pdf'
   */
  path: string;
}

/**
 * @since 0.1.0
 */
export interface OpenResult {
  /**
   * The path of the annotated copy of the PDF file.
   *
   * The file is stored in the cache directory and deleted on the next app
   * launch. Move it to a permanent location if you want to keep it.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/capawesome_capacitor_pdf_annotator_documents/3f1b2c8e-6a5d-4f0e-9c2b-1d7e8a9b0c3d.pdf'
   */
  path: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user closed the viewer without saving any annotations.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
  /**
   * The file was not found at the given path.
   *
   * @since 0.1.0
   */
  FileNotFound = 'FILE_NOT_FOUND',
  /**
   * The PDF document could not be loaded.
   *
   * @since 0.1.0
   */
  LoadFailed = 'LOAD_FAILED',
  /**
   * PDF annotation is not supported on this device.
   *
   * @since 0.1.0
   */
  NotSupported = 'NOT_SUPPORTED',
  /**
   * The annotated PDF document could not be saved.
   *
   * @since 0.1.0
   */
  SaveFailed = 'SAVE_FAILED',
}
