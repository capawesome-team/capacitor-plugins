import type { PluginListenerHandle } from '@capacitor/core';

export interface PdfViewerPlugin {
  /**
   * Close the currently open viewer.
   *
   * If no viewer is open, this method does nothing.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  close(): Promise<void>;
  /**
   * Open a PDF file in a fullscreen native viewer.
   *
   * If a viewer is already open, it is closed before the new one is
   * presented.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  open(options: OpenOptions): Promise<void>;
  /**
   * Called when the viewer is closed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'closed',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the current page of the viewer changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'pageChange',
    listenerFunc: (event: PageChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface OpenOptions {
  /**
   * The page (1-based) to display initially.
   *
   * @since 0.1.0
   * @default 1
   * @example 3
   */
  page?: number;
  /**
   * The password to unlock the PDF file if it is password-protected.
   *
   * @since 0.1.0
   * @example 'secret'
   */
  password?: string;
  /**
   * The path of the local PDF file to display.
   *
   * Remote URLs are not supported. Download the file first, for example
   * to the cache directory, and pass the local file path to this method.
   *
   * @since 0.1.0
   * @example 'file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/document.pdf'
   */
  path: string;
  /**
   * Whether to display a share button in the toolbar of the viewer.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.2
   * @default true
   * @example false
   */
  showShareButton?: boolean;
  /**
   * The title to display in the toolbar of the viewer.
   *
   * @since 0.1.0
   * @default The file name of the PDF file.
   * @example 'Invoice'
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface PageChangeEvent {
  /**
   * The page (1-based) that is currently displayed.
   *
   * @since 0.1.0
   * @example 3
   */
  page: number;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
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
   * The provided password is invalid.
   *
   * @since 0.1.0
   */
  PasswordInvalid = 'PASSWORD_INVALID',
  /**
   * The PDF document is password-protected and no password was provided.
   *
   * @since 0.1.0
   */
  PasswordRequired = 'PASSWORD_REQUIRED',
}
