export interface AndroidSmsRetrieverPlugin {
  /**
   * Request the user's phone number via the Phone Number Hint API.
   *
   * A system bottom sheet is displayed that lets the user pick one of the
   * phone numbers associated with the device. The selected phone number is
   * returned so it can be used to prefill a phone number input field.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  requestPhoneNumber(): Promise<RequestPhoneNumberResult>;
  /**
   * Retrieve an incoming verification SMS via the SMS User Consent API.
   *
   * A system consent dialog is displayed when a matching SMS is received.
   * The promise resolves with the full message text once the user consents,
   * so the app can extract the one-time code itself.
   *
   * The underlying broadcast waits up to 5 minutes for a matching SMS. If no
   * SMS is received within this time, the promise rejects with the error
   * code `TIMEOUT`.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  retrieveSms(options?: RetrieveSmsOptions): Promise<RetrieveSmsResult>;
}

/**
 * @since 0.1.0
 */
export interface RequestPhoneNumberResult {
  /**
   * The phone number selected by the user.
   *
   * @example '+12025550123'
   * @since 0.1.0
   */
  phoneNumber: string;
}

/**
 * @since 0.1.0
 */
export interface RetrieveSmsOptions {
  /**
   * The phone number of the sender to filter incoming messages by.
   *
   * If not provided, the SMS from any sender is retrieved.
   *
   * @example '+12025550123'
   * @since 0.1.0
   */
  senderPhoneNumber?: string;
}

/**
 * @since 0.1.0
 */
export interface RetrieveSmsResult {
  /**
   * The full text of the retrieved SMS message.
   *
   * The app is responsible for extracting the one-time code from the message.
   *
   * @example 'Your verification code is 123456.'
   * @since 0.1.0
   */
  message: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user canceled the operation.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
  /**
   * The phone number could not be requested via the Phone Number Hint API.
   *
   * @since 0.1.0
   */
  PhoneNumberHintFailed = 'PHONE_NUMBER_HINT_FAILED',
  /**
   * The incoming SMS message could not be retrieved.
   *
   * @since 0.1.0
   */
  RetrieveFailed = 'RETRIEVE_FAILED',
  /**
   * No matching SMS message was received within the 5 minute time window.
   *
   * @since 0.1.0
   */
  Timeout = 'TIMEOUT',
  /**
   * The user denied consent to read the incoming SMS message.
   *
   * @since 0.1.0
   */
  UserDenied = 'USER_DENIED',
}
