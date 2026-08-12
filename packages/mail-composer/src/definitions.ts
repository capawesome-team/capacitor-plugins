export interface MailComposerPlugin {
  /**
   * Check whether the device is able to compose and send emails.
   *
   * On iOS, this returns `true` only if a mail account is configured. On
   * Android, this returns `true` if a mail app is installed. On the web, this
   * always returns `true` (best effort, not verifiable).
   *
   * @since 0.1.0
   */
  canComposeMail(): Promise<CanComposeMailResult>;
  /**
   * Open the native email composer prefilled with the provided data.
   *
   * The user reviews the email and decides whether to send it. The plugin
   * never sends the email itself.
   *
   * @since 0.1.0
   */
  composeMail(options: ComposeMailOptions): Promise<ComposeMailResult>;
}

/**
 * @since 0.1.0
 */
export interface CanComposeMailResult {
  /**
   * Whether the device is able to compose and send emails.
   *
   * @since 0.1.0
   */
  canCompose: boolean;
}

/**
 * @since 0.1.0
 */
export interface ComposeMailOptions {
  /**
   * The absolute file paths or `file://` URIs of the files to attach.
   *
   * Attachments are not supported on the web.
   *
   * @example ['/path/to/file.pdf']
   * @since 0.1.0
   */
  attachments?: string[];
  /**
   * The email addresses of the blind carbon copy (BCC) recipients.
   *
   * @example ['secret@example.com']
   * @since 0.1.0
   */
  bcc?: string[];
  /**
   * The body of the email.
   *
   * @example 'This is the body of the email.'
   * @since 0.1.0
   */
  body?: string;
  /**
   * The email addresses of the carbon copy (CC) recipients.
   *
   * @example ['john@example.com']
   * @since 0.1.0
   */
  cc?: string[];
  /**
   * Whether the body should be interpreted as HTML.
   *
   * **Note**: On Android, HTML is best-effort as many mail apps ignore it. On
   * the web, HTML is not supported and the body is always sent as plain text.
   *
   * @default false
   * @since 0.1.0
   */
  isHtml?: boolean;
  /**
   * The subject of the email.
   *
   * @example 'Hello World'
   * @since 0.1.0
   */
  subject?: string;
  /**
   * The email addresses of the primary recipients.
   *
   * @example ['jane@example.com']
   * @since 0.1.0
   */
  to?: string[];
}

/**
 * @since 0.1.0
 */
export interface ComposeMailResult {
  /**
   * The status of the email compose operation.
   *
   * **Note**: On Android, the status is always `unknown` because the mail
   * intent does not return a reliable result.
   *
   * @since 0.1.0
   */
  status: MailComposeStatus;
}

/**
 * The status of an email compose operation.
 *
 * - `sent`: The email was sent.
 * - `saved`: The email was saved as a draft.
 * - `canceled`: The user canceled the operation.
 * - `unknown`: The status is unknown.
 *
 * @since 0.1.0
 */
export type MailComposeStatus = 'sent' | 'saved' | 'canceled' | 'unknown';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * An attachment could not be found at the provided path.
   *
   * @since 0.1.0
   */
  AttachmentNotFound = 'ATTACHMENT_NOT_FOUND',
  /**
   * The email could not be composed.
   *
   * @since 0.1.0
   */
  ComposeFailed = 'COMPOSE_FAILED',
}
