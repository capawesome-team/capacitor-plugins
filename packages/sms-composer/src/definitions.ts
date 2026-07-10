export interface SmsComposerPlugin {
  /**
   * Check whether the device is able to compose and send SMS messages.
   *
   * On devices without SMS capability (e.g. Wi-Fi-only tablets or iPads),
   * this resolves with `canCompose` set to `false`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  canComposeSms(): Promise<CanComposeSmsResult>;
  /**
   * Open the native SMS composer prefilled with the given recipients and body.
   *
   * The user reviews the message and decides whether to send it. The plugin
   * never sends the message itself.
   *
   * The call resolves once the composer is dismissed. If the device is not
   * able to compose SMS messages, the call rejects as unavailable.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  composeSms(options: ComposeSmsOptions): Promise<ComposeSmsResult>;
}

/**
 * @since 0.1.0
 */
export interface CanComposeSmsResult {
  /**
   * Whether the device is able to compose and send SMS messages.
   *
   * @since 0.1.0
   */
  canCompose: boolean;
}

/**
 * @since 0.1.0
 */
export interface ComposeSmsOptions {
  /**
   * The message body to prefill in the composer.
   *
   * @example 'Hello from Capacitor!'
   * @since 0.1.0
   */
  body?: string;
  /**
   * The recipients (phone numbers) to prefill in the composer.
   *
   * @example ['+41791234567', '+41797654321']
   * @since 0.1.0
   */
  recipients?: string[];
}

/**
 * @since 0.1.0
 */
export interface ComposeSmsResult {
  /**
   * The status of the SMS composer after it was dismissed.
   *
   * On Android, the status is always `unknown` because the system does not
   * report whether the message was sent.
   *
   * @since 0.1.0
   */
  status: SmsComposeStatus;
}

/**
 * The status of the SMS composer after it was dismissed.
 *
 * - `sent`: The user sent the message.
 * - `canceled`: The user canceled the composer without sending the message.
 * - `unknown`: The result is unknown. This is always the case on Android.
 *
 * @since 0.1.0
 */
export type SmsComposeStatus = 'canceled' | 'sent' | 'unknown';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The SMS composer failed to compose or present the message.
   *
   * @since 0.1.0
   */
  ComposeFailed = 'COMPOSE_FAILED',
}
