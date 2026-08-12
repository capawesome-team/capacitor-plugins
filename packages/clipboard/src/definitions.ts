export interface ClipboardPlugin {
  /**
   * Read the current content of the system clipboard.
   *
   * On Android, reading the clipboard is only possible while the app is in the
   * foreground.
   *
   * On iOS, reading the clipboard displays a system paste notification. This is
   * expected behavior and cannot be suppressed.
   *
   * @since 0.1.0
   */
  read(): Promise<ReadResult>;
  /**
   * Write content to the system clipboard.
   *
   * Exactly one of `text`, `html`, `image` or `url` must be provided. The
   * `html` property may additionally be combined with `text` to provide a
   * plain-text fallback.
   *
   * @since 0.1.0
   */
  write(options: WriteOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface ReadResult {
  /**
   * The type of the content that was read from the clipboard.
   *
   * @since 0.1.0
   * @example 'TEXT'
   */
  type: ClipboardContentType;
  /**
   * The content that was read from the clipboard.
   *
   * Images are returned as a Base64-encoded data URL.
   *
   * @since 0.1.0
   * @example 'Hello World'
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface WriteOptions {
  /**
   * The HTML content to write to the clipboard.
   *
   * Combine this with `text` to provide a plain-text fallback for apps that
   * cannot handle HTML content.
   *
   * @since 0.1.0
   * @example '<b>Hello World</b>'
   */
  html?: string;
  /**
   * The image to write to the clipboard as a Base64-encoded data URL.
   *
   * @since 0.1.0
   * @example 'data:image/png;base64,iVBORw0KGgo...'
   */
  image?: string;
  /**
   * The label used to describe the clipboard content.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   * @example 'My label'
   */
  label?: string;
  /**
   * The plain text to write to the clipboard.
   *
   * @since 0.1.0
   * @example 'Hello World'
   */
  text?: string;
  /**
   * The URL to write to the clipboard.
   *
   * @since 0.1.0
   * @example 'https://capawesome.io'
   */
  url?: string;
}

/**
 * The type of content stored on the clipboard.
 *
 * @since 0.1.0
 */
export enum ClipboardContentType {
  /**
   * The content is HTML.
   *
   * @since 0.1.0
   */
  Html = 'HTML',
  /**
   * The content is an image, returned as a Base64-encoded data URL.
   *
   * @since 0.1.0
   */
  Image = 'IMAGE',
  /**
   * The content is plain text.
   *
   * @since 0.1.0
   */
  Text = 'TEXT',
  /**
   * The content is a URL.
   *
   * @since 0.1.0
   */
  Url = 'URL',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The clipboard is empty.
   *
   * @since 0.1.0
   */
  EmptyClipboard = 'EMPTY_CLIPBOARD',
  /**
   * The clipboard content could not be read.
   *
   * @since 0.1.0
   */
  ReadFailed = 'READ_FAILED',
  /**
   * The content could not be written to the clipboard.
   *
   * @since 0.1.0
   */
  WriteFailed = 'WRITE_FAILED',
}
