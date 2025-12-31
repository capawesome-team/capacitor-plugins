import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

/**
 * @since 0.0.1
 */
export interface SquareMobilePaymentsPlugin {
  /**
   * Initialize the Square Mobile Payments SDK.
   *
   * This method must be called before any other method.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Authorize the SDK with a Square access token.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  authorize(options: AuthorizeOptions): Promise<void>;
  /**
   * Check if the SDK is currently authorized.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  isAuthorized(): Promise<IsAuthorizedResult>;
  /**
   * Deauthorize the SDK and clear the current authorization.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  deauthorize(): Promise<void>;
  /**
   * Show the Square settings screen.
   *
   * This displays a pre-built settings UI where merchants can view and manage
   * their paired readers.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  showSettings(): Promise<void>;
  /**
   * Get the current SDK settings.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getSettings(): Promise<GetSettingsResult>;
  /**
   * Start pairing with a Square reader.
   *
   * This initiates the reader pairing process. The SDK will search for nearby
   * readers and pair with the first one found.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startPairing(): Promise<void>;
  /**
   * Stop the reader pairing process.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  stopPairing(): Promise<void>;
  /**
   * Check if a pairing process is currently in progress.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  isPairingInProgress(): Promise<IsPairingInProgressResult>;
  /**
   * Get a list of all paired readers.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getReaders(): Promise<GetReadersResult>;
  /**
   * Forget a paired reader.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  forgetReader(options: ForgetReaderOptions): Promise<void>;
  /**
   * Retry connecting to a reader.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  retryConnection(options: RetryConnectionOptions): Promise<void>;
  /**
   * Start a payment flow.
   *
   * This presents the payment UI and processes the payment using the specified
   * parameters. Only one payment can be active at a time.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startPayment(options: StartPaymentOptions): Promise<void>;
  /**
   * Cancel an ongoing payment.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  cancelPayment(): Promise<void>;
  /**
   * Get the currently available card input methods.
   *
   * This returns the card entry methods that are available based on the
   * connected readers (e.g., TAP, DIP, SWIPE, KEYED).
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getAvailableCardInputMethods(): Promise<GetAvailableCardInputMethodsResult>;
  /**
   * Check the current permission status.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request the required permissions.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Listen for reader pairing events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerPairingDidBegin',
    listenerFunc: ReaderPairingDidBeginListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for successful reader pairing events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerPairingDidSucceed',
    listenerFunc: ReaderPairingDidSucceedListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for failed reader pairing events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerPairingDidFail',
    listenerFunc: ReaderPairingDidFailListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for reader added events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerWasAdded',
    listenerFunc: ReaderWasAddedListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for reader removed events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerWasRemoved',
    listenerFunc: ReaderWasRemovedListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for reader status and property changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'readerDidChange',
    listenerFunc: ReaderDidChangeListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for available card input method changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'availableCardInputMethodsDidChange',
    listenerFunc: AvailableCardInputMethodsDidChangeListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for successful payment completion events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paymentDidFinish',
    listenerFunc: PaymentDidFinishListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for failed payment events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paymentDidFail',
    listenerFunc: PaymentDidFailListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for cancelled payment events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paymentDidCancel',
    listenerFunc: PaymentDidCancelListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface InitializeOptions {
  /**
   * The Square location ID.
   *
   * @since 0.0.1
   * @example 'LOCATION_ID'
   */
  locationId: string;
}

/**
 * @since 0.0.1
 */
export interface AuthorizeOptions {
  /**
   * The Square access token.
   *
   * @since 0.0.1
   * @example 'EAAAEOuLQPDbLd...'
   */
  accessToken: string;
}

/**
 * @since 0.0.1
 */
export interface IsAuthorizedResult {
  /**
   * Whether the SDK is currently authorized.
   *
   * @since 0.0.1
   */
  authorized: boolean;
}

/**
 * @since 0.0.1
 */
export interface GetSettingsResult {
  /**
   * The SDK version.
   *
   * @since 0.0.1
   * @example '2.0.0'
   */
  version: string;
  /**
   * The current environment.
   *
   * @since 0.0.1
   * @example 'production'
   */
  environment: Environment;
}

/**
 * @since 0.0.1
 */
export interface IsPairingInProgressResult {
  /**
   * Whether a pairing process is currently in progress.
   *
   * @since 0.0.1
   */
  inProgress: boolean;
}

/**
 * @since 0.0.1
 */
export interface GetReadersResult {
  /**
   * The list of paired readers.
   *
   * @since 0.0.1
   */
  readers: ReaderInfo[];
}

/**
 * @since 0.0.1
 */
export interface ForgetReaderOptions {
  /**
   * The serial number of the reader to forget.
   *
   * @since 0.0.1
   * @example 'CRR123456789'
   */
  serialNumber: string;
}

/**
 * @since 0.0.1
 */
export interface RetryConnectionOptions {
  /**
   * The serial number of the reader to retry connection.
   *
   * @since 0.0.1
   * @example 'CRR123456789'
   */
  serialNumber: string;
}

/**
 * @since 0.0.1
 */
export interface StartPaymentOptions {
  /**
   * The payment parameters.
   *
   * @since 0.0.1
   */
  paymentParameters: PaymentParameters;
  /**
   * The prompt parameters.
   *
   * @since 0.0.1
   */
  promptParameters: PromptParameters;
}

/**
 * @since 0.0.1
 */
export interface PaymentParameters {
  /**
   * The amount of money to charge.
   *
   * @since 0.0.1
   */
  amountMoney: Money;
  /**
   * A unique identifier for this payment attempt.
   *
   * This is used for idempotent payment requests.
   *
   * @since 0.0.1
   * @example 'a1b2c3d4-e5f6-4a5b-8c9d-1e2f3a4b5c6d'
   */
  paymentAttemptId: string;
  /**
   * The processing mode for this payment.
   *
   * @since 0.0.1
   * @default ProcessingMode.AutoDetect
   */
  processingMode?: ProcessingMode;
  /**
   * A user-defined reference ID to associate with the payment.
   *
   * @since 0.0.1
   * @example 'ORDER-12345'
   */
  referenceId?: string;
  /**
   * An optional note to add to the payment.
   *
   * @since 0.0.1
   * @example 'Birthday gift'
   */
  note?: string;
  /**
   * The Square order ID to associate with this payment.
   *
   * @since 0.0.1
   * @example 'CAISENgvlJ6jLWAzERDzjyHVybY'
   */
  orderId?: string;
  /**
   * The tip amount.
   *
   * @since 0.0.1
   */
  tipMoney?: Money;
  /**
   * The application fee.
   *
   * @since 0.0.1
   */
  applicationFee?: Money;
  /**
   * Whether to automatically complete the payment.
   *
   * If false, the payment will be authorized but not captured, requiring
   * manual completion via the Payments API.
   *
   * @since 0.0.1
   * @default true
   */
  autocomplete?: boolean;
  /**
   * The duration to delay before automatically completing or canceling the payment.
   *
   * Only applicable when autocomplete is false.
   *
   * @since 0.0.1
   * @example 'PT1H'
   */
  delayDuration?: string;
  /**
   * The action to take when the delay duration expires.
   *
   * Only applicable when autocomplete is false.
   *
   * @since 0.0.1
   */
  delayAction?: DelayAction;
}

/**
 * @since 0.0.1
 */
export interface PromptParameters {
  /**
   * The prompt mode.
   *
   * @since 0.0.1
   * @default PromptMode.Default
   */
  mode?: PromptMode;
  /**
   * Additional payment methods to allow.
   *
   * @since 0.0.1
   * @default []
   */
  additionalMethods?: AdditionalPaymentMethod[];
}

/**
 * @since 0.0.1
 */
export interface Money {
  /**
   * The amount in the smallest currency unit (e.g., cents for USD).
   *
   * @since 0.0.1
   * @example 100
   */
  amount: number;
  /**
   * The ISO 4217 currency code.
   *
   * @since 0.0.1
   * @example 'USD'
   */
  currency: CurrencyCode;
}

/**
 * @since 0.0.1
 */
export interface GetAvailableCardInputMethodsResult {
  /**
   * The available card input methods.
   *
   * @since 0.0.1
   */
  cardInputMethods: CardInputMethod[];
}

/**
 * @since 0.0.1
 */
export interface ReaderInfo {
  /**
   * The reader's serial number.
   *
   * @since 0.0.1
   * @example 'CRR123456789'
   */
  serialNumber: string;
  /**
   * The reader's model.
   *
   * @since 0.0.1
   */
  model: ReaderModel;
  /**
   * The reader's current status.
   *
   * @since 0.0.1
   */
  status: ReaderStatus;
  /**
   * The reader's firmware version.
   *
   * @since 0.0.1
   * @example '1.2.3'
   */
  firmwareVersion?: string;
  /**
   * The reader's battery level (0-100).
   *
   * @since 0.0.1
   * @example 85
   */
  batteryLevel?: number;
  /**
   * Whether the reader is currently charging.
   *
   * @since 0.0.1
   */
  isCharging?: boolean;
  /**
   * The card input methods supported by this reader.
   *
   * @since 0.0.1
   */
  supportedCardInputMethods: CardInputMethod[];
  /**
   * Additional information about why the reader is unavailable.
   *
   * Only present when status is ReaderUnavailable.
   *
   * @since 0.0.1
   */
  unavailableReasonInfo?: UnavailableReasonInfo;
}

/**
 * @since 0.0.1
 */
export interface UnavailableReasonInfo {
  /**
   * The reason code why the reader is unavailable.
   *
   * @since 0.0.1
   */
  reason: UnavailableReason;
  /**
   * A human-readable message describing why the reader is unavailable.
   *
   * @since 0.0.1
   */
  message?: string;
}

/**
 * @since 0.0.1
 */
export interface Payment {
  /**
   * The unique identifier for this payment.
   *
   * For offline payments, this may be null until synced.
   *
   * @since 0.0.1
   * @example 'AbCdEfGhIjKlMnOpQrStUvWxYz'
   */
  id: string | null;
  /**
   * The payment type.
   *
   * @since 0.0.1
   */
  type: PaymentType;
  /**
   * The payment status.
   *
   * @since 0.0.1
   */
  status: PaymentStatus;
  /**
   * The amount paid.
   *
   * @since 0.0.1
   */
  amountMoney: Money;
  /**
   * The tip amount.
   *
   * @since 0.0.1
   */
  tipMoney?: Money;
  /**
   * The application fee.
   *
   * @since 0.0.1
   */
  applicationFee?: Money;
  /**
   * The reference ID provided in the payment parameters.
   *
   * @since 0.0.1
   */
  referenceId?: string;
  /**
   * The order ID associated with this payment.
   *
   * @since 0.0.1
   */
  orderId?: string;
  /**
   * Card payment details.
   *
   * Only present for card payments.
   *
   * @since 0.0.1
   */
  cardDetails?: CardPaymentDetails;
  /**
   * The time the payment was created (ISO 8601 format).
   *
   * @since 0.0.1
   * @example '2024-01-15T10:30:00Z'
   */
  createdAt?: string;
  /**
   * The time the payment was updated (ISO 8601 format).
   *
   * @since 0.0.1
   * @example '2024-01-15T10:30:00Z'
   */
  updatedAt?: string;
}

/**
 * @since 0.0.1
 */
export interface CardPaymentDetails {
  /**
   * The card information.
   *
   * @since 0.0.1
   */
  card: Card;
  /**
   * The card entry method used.
   *
   * @since 0.0.1
   */
  entryMethod: CardInputMethod;
  /**
   * The authorization code.
   *
   * @since 0.0.1
   * @example '123456'
   */
  authorizationCode?: string;
  /**
   * The EMV application name.
   *
   * @since 0.0.1
   * @example 'VISA CREDIT'
   */
  applicationName?: string;
  /**
   * The EMV application identifier.
   *
   * @since 0.0.1
   * @example 'A0000000031010'
   */
  applicationId?: string;
}

/**
 * @since 0.0.1
 */
export interface Card {
  /**
   * The card brand.
   *
   * @since 0.0.1
   */
  brand: CardBrand;
  /**
   * The last four digits of the card number.
   *
   * @since 0.0.1
   * @example '1234'
   */
  lastFourDigits: string;
  /**
   * The cardholder name.
   *
   * @since 0.0.1
   * @example 'John Doe'
   */
  cardholderName?: string;
  /**
   * The card expiration month (1-12).
   *
   * @since 0.0.1
   * @example 12
   */
  expirationMonth?: number;
  /**
   * The card expiration year.
   *
   * @since 0.0.1
   * @example 2025
   */
  expirationYear?: number;
}

/**
 * Callback to receive reader pairing start notifications.
 *
 * @since 0.0.1
 */
export type ReaderPairingDidBeginListener = () => void;

/**
 * Callback to receive reader pairing success notifications.
 *
 * @since 0.0.1
 */
export type ReaderPairingDidSucceedListener = () => void;

/**
 * Callback to receive reader pairing failure notifications.
 *
 * @since 0.0.1
 */
export type ReaderPairingDidFailListener = (
  event: ReaderPairingDidFailEvent,
) => void;

/**
 * @since 0.0.1
 */
export interface ReaderPairingDidFailEvent {
  /**
   * The error code.
   *
   * @since 0.0.1
   */
  code?: string;
  /**
   * The error message.
   *
   * @since 0.0.1
   */
  message: string;
}

/**
 * Callback to receive reader added notifications.
 *
 * @since 0.0.1
 */
export type ReaderWasAddedListener = (event: ReaderWasAddedEvent) => void;

/**
 * @since 0.0.1
 */
export interface ReaderWasAddedEvent {
  /**
   * The reader that was added.
   *
   * @since 0.0.1
   */
  reader: ReaderInfo;
}

/**
 * Callback to receive reader removed notifications.
 *
 * @since 0.0.1
 */
export type ReaderWasRemovedListener = (event: ReaderWasRemovedEvent) => void;

/**
 * @since 0.0.1
 */
export interface ReaderWasRemovedEvent {
  /**
   * The reader that was removed.
   *
   * @since 0.0.1
   */
  reader: ReaderInfo;
}

/**
 * Callback to receive reader change notifications.
 *
 * @since 0.0.1
 */
export type ReaderDidChangeListener = (event: ReaderDidChangeEvent) => void;

/**
 * @since 0.0.1
 */
export interface ReaderDidChangeEvent {
  /**
   * The reader that changed.
   *
   * @since 0.0.1
   */
  reader: ReaderInfo;
  /**
   * The type of change that occurred.
   *
   * @since 0.0.1
   */
  change: ReaderChange;
}

/**
 * Callback to receive available card input method change notifications.
 *
 * @since 0.0.1
 */
export type AvailableCardInputMethodsDidChangeListener = (
  event: AvailableCardInputMethodsDidChangeEvent,
) => void;

/**
 * @since 0.0.1
 */
export interface AvailableCardInputMethodsDidChangeEvent {
  /**
   * The available card input methods.
   *
   * @since 0.0.1
   */
  cardInputMethods: CardInputMethod[];
}

/**
 * Callback to receive payment completion notifications.
 *
 * @since 0.0.1
 */
export type PaymentDidFinishListener = (event: PaymentDidFinishEvent) => void;

/**
 * @since 0.0.1
 */
export interface PaymentDidFinishEvent {
  /**
   * The completed payment.
   *
   * @since 0.0.1
   */
  payment: Payment;
}

/**
 * Callback to receive payment failure notifications.
 *
 * @since 0.0.1
 */
export type PaymentDidFailListener = (event: PaymentDidFailEvent) => void;

/**
 * @since 0.0.1
 */
export interface PaymentDidFailEvent {
  /**
   * The failed payment.
   *
   * @since 0.0.1
   */
  payment?: Payment;
  /**
   * The error code.
   *
   * @since 0.0.1
   */
  code?: string;
  /**
   * The error message.
   *
   * @since 0.0.1
   */
  message: string;
}

/**
 * Callback to receive payment cancellation notifications.
 *
 * @since 0.0.1
 */
export type PaymentDidCancelListener = (event: PaymentDidCancelEvent) => void;

/**
 * @since 0.0.1
 */
export interface PaymentDidCancelEvent {
  /**
   * The cancelled payment.
   *
   * @since 0.0.1
   */
  payment?: Payment;
}

/**
 * @since 0.0.1
 */
export enum Environment {
  /**
   * Production environment.
   *
   * @since 0.0.1
   */
  Production = 'production',
  /**
   * Sandbox environment for testing.
   *
   * @since 0.0.1
   */
  Sandbox = 'sandbox',
}

/**
 * @since 0.0.1
 */
export enum CardInputMethod {
  /**
   * Contactless card tap (NFC).
   *
   * @since 0.0.1
   */
  Tap = 'TAP',
  /**
   * EMV chip card insertion.
   *
   * @since 0.0.1
   */
  Dip = 'DIP',
  /**
   * Magnetic stripe swipe.
   *
   * @since 0.0.1
   */
  Swipe = 'SWIPE',
  /**
   * Manually keyed card entry.
   *
   * @since 0.0.1
   */
  Keyed = 'KEYED',
}

/**
 * @since 0.0.1
 */
export enum ProcessingMode {
  /**
   * Automatically detect the best processing mode (online or offline).
   *
   * @since 0.0.1
   */
  AutoDetect = 'AUTO_DETECT',
  /**
   * Process the payment online only.
   *
   * @since 0.0.1
   */
  OnlineOnly = 'ONLINE_ONLY',
  /**
   * Allow offline payment processing.
   *
   * @since 0.0.1
   */
  OfflineOnly = 'OFFLINE_ONLY',
}

/**
 * @since 0.0.1
 */
export enum PromptMode {
  /**
   * Use the default Square payment UI.
   *
   * @since 0.0.1
   */
  Default = 'DEFAULT',
  /**
   * Use a custom payment UI.
   *
   * @since 0.0.1
   */
  Custom = 'CUSTOM',
}

/**
 * @since 0.0.1
 */
export enum AdditionalPaymentMethod {
  /**
   * Allow manually keyed card entry.
   *
   * @since 0.0.1
   */
  Keyed = 'KEYED',
  /**
   * Allow cash payments.
   *
   * @since 0.0.1
   */
  Cash = 'CASH',
}

/**
 * @since 0.0.1
 */
export enum DelayAction {
  /**
   * Automatically complete the payment when the delay expires.
   *
   * @since 0.0.1
   */
  Complete = 'COMPLETE',
  /**
   * Automatically cancel the payment when the delay expires.
   *
   * @since 0.0.1
   */
  Cancel = 'CANCEL',
}

/**
 * @since 0.0.1
 */
export enum ReaderModel {
  /**
   * Square Reader for contactless and chip.
   *
   * @since 0.0.1
   */
  ContactlessAndChip = 'CONTACTLESS_AND_CHIP',
  /**
   * Square Reader for magstripe.
   *
   * @since 0.0.1
   */
  Magstripe = 'MAGSTRIPE',
  /**
   * Square Stand.
   *
   * @since 0.0.1
   */
  Stand = 'STAND',
  /**
   * Unknown reader model.
   *
   * @since 0.0.1
   */
  Unknown = 'UNKNOWN',
}

/**
 * @since 0.0.1
 */
export enum ReaderStatus {
  /**
   * Reader is paired, connected, and ready to accept payments.
   *
   * @since 0.0.1
   */
  Ready = 'READY',
  /**
   * Reader is connecting to Square servers.
   *
   * @since 0.0.1
   */
  ConnectingToSquare = 'CONNECTING_TO_SQUARE',
  /**
   * Reader is connecting to the mobile device.
   *
   * @since 0.0.1
   */
  ConnectingToDevice = 'CONNECTING_TO_DEVICE',
  /**
   * Reader has a hardware or connection error in an unrecoverable state.
   *
   * @since 0.0.1
   */
  Faulty = 'FAULTY',
  /**
   * Reader is connected but unable to process payments.
   *
   * @since 0.0.1
   */
  ReaderUnavailable = 'READER_UNAVAILABLE',
}

/**
 * @since 0.0.1
 */
export enum ReaderChange {
  /**
   * Reader battery started charging.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  BatteryDidBeginCharging = 'BATTERY_DID_BEGIN_CHARGING',
  /**
   * Reader battery stopped charging.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  BatteryDidEndCharging = 'BATTERY_DID_END_CHARGING',
  /**
   * Reader battery level changed.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  BatteryLevelDidChange = 'BATTERY_LEVEL_DID_CHANGE',
  /**
   * Reader battery charging status changed.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  BatteryCharging = 'BATTERY_CHARGING',
  /**
   * Reader was added.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  Added = 'ADDED',
  /**
   * Reader was removed.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  Removed = 'REMOVED',
  /**
   * Reader status changed.
   *
   * @since 0.0.1
   */
  StatusDidChange = 'STATUS_DID_CHANGE',
}

/**
 * @since 0.0.1
 */
export enum UnavailableReason {
  /**
   * Bluetooth connection issue.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  BluetoothError = 'BLUETOOTH_ERROR',
  /**
   * Bluetooth failure.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  BluetoothFailure = 'BLUETOOTH_FAILURE',
  /**
   * Bluetooth is disabled.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  BluetoothDisabled = 'BLUETOOTH_DISABLED',
  /**
   * Reader firmware is updating.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  FirmwareUpdate = 'FIRMWARE_UPDATE',
  /**
   * Blocking firmware update.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  BlockingUpdate = 'BLOCKING_UPDATE',
  /**
   * Reader is unavailable offline.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  ReaderUnavailableOffline = 'READER_UNAVAILABLE_OFFLINE',
  /**
   * Device is in developer mode.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  DeviceDeveloperMode = 'DEVICE_DEVELOPER_MODE',
  /**
   * Unknown reason.
   *
   * @since 0.0.1
   */
  Unknown = 'UNKNOWN',
}

/**
 * @since 0.0.1
 */
export enum PaymentType {
  /**
   * Payment processed online.
   *
   * @since 0.0.1
   */
  Online = 'ONLINE',
  /**
   * Payment processed offline.
   *
   * @since 0.0.1
   */
  Offline = 'OFFLINE',
}

/**
 * @since 0.0.1
 */
export enum PaymentStatus {
  /**
   * Payment was completed successfully.
   *
   * @since 0.0.1
   */
  Completed = 'COMPLETED',
  /**
   * Payment was approved but not yet completed.
   *
   * @since 0.0.1
   */
  Approved = 'APPROVED',
  /**
   * Payment was canceled.
   *
   * @since 0.0.1
   */
  Canceled = 'CANCELED',
  /**
   * Payment failed.
   *
   * @since 0.0.1
   */
  Failed = 'FAILED',
  /**
   * Payment is pending.
   *
   * @since 0.0.1
   */
  Pending = 'PENDING',
}

/**
 * @since 0.0.1
 */
export enum CardBrand {
  /**
   * Visa card.
   *
   * @since 0.0.1
   */
  Visa = 'VISA',
  /**
   * Mastercard.
   *
   * @since 0.0.1
   */
  Mastercard = 'MASTERCARD',
  /**
   * American Express card.
   *
   * @since 0.0.1
   */
  AmericanExpress = 'AMERICAN_EXPRESS',
  /**
   * Discover card.
   *
   * @since 0.0.1
   */
  Discover = 'DISCOVER',
  /**
   * Discover Diners card.
   *
   * @since 0.0.1
   */
  DiscoverDiners = 'DISCOVER_DINERS',
  /**
   * JCB card.
   *
   * @since 0.0.1
   */
  Jcb = 'JCB',
  /**
   * UnionPay card.
   *
   * @since 0.0.1
   */
  UnionPay = 'UNION_PAY',
  /**
   * Interac card.
   *
   * @since 0.0.1
   */
  Interac = 'INTERAC',
  /**
   * Eftpos card.
   *
   * @since 0.0.1
   */
  Eftpos = 'EFTPOS',
  /**
   * Other or unknown card brand.
   *
   * @since 0.0.1
   */
  Other = 'OTHER',
}

/**
 * @since 0.0.1
 */
export interface PermissionStatus {
  /**
   * Permission state for accessing location.
   *
   * Required to confirm that payments are occurring in a supported Square location.
   *
   * @since 0.0.1
   */
  location: PermissionState;
  /**
   * Permission state for recording audio.
   *
   * Required to receive data from magstripe readers.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  recordAudio?: PermissionState;
  /**
   * Permission state for Bluetooth connect.
   *
   * Required to receive data from contactless and chip readers.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  bluetoothConnect?: PermissionState;
  /**
   * Permission state for Bluetooth scan.
   *
   * Required to store information during checkout.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  bluetoothScan?: PermissionState;
  /**
   * Permission state for reading phone state.
   *
   * Required to identify the device sending information to Square servers.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  readPhoneState?: PermissionState;
  /**
   * Permission state for using Bluetooth.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  bluetooth?: PermissionState;
}

/**
 * @since 0.0.1
 */
export type CurrencyCode = string;

/**
 * @since 0.0.1
 */
export enum ErrorCode {
  /**
   * The location ID is missing.
   *
   * @since 0.0.1
   */
  LocationIdMissing = 'LOCATION_ID_MISSING',
  /**
   * The access token is missing.
   *
   * @since 0.0.1
   */
  AccessTokenMissing = 'ACCESS_TOKEN_MISSING',
  /**
   * The serial number is missing.
   *
   * @since 0.0.1
   */
  SerialNumberMissing = 'SERIAL_NUMBER_MISSING',
  /**
   * The payment parameters are missing.
   *
   * @since 0.0.1
   */
  PaymentParametersMissing = 'PAYMENT_PARAMETERS_MISSING',
  /**
   * The prompt parameters are missing.
   *
   * @since 0.0.1
   */
  PromptParametersMissing = 'PROMPT_PARAMETERS_MISSING',
  /**
   * The amount money is missing.
   *
   * @since 0.0.1
   */
  AmountMoneyMissing = 'AMOUNT_MONEY_MISSING',
  /**
   * The payment attempt ID is missing.
   *
   * @since 0.0.1
   */
  PaymentAttemptIdMissing = 'PAYMENT_ATTEMPT_ID_MISSING',
  /**
   * The SDK is not initialized.
   *
   * @since 0.0.1
   */
  NotInitialized = 'NOT_INITIALIZED',
  /**
   * The SDK is not authorized.
   *
   * @since 0.0.1
   */
  NotAuthorized = 'NOT_AUTHORIZED',
  /**
   * A pairing process is already in progress.
   *
   * @since 0.0.1
   */
  PairingAlreadyInProgress = 'PAIRING_ALREADY_IN_PROGRESS',
  /**
   * No payment is in progress.
   *
   * @since 0.0.1
   */
  NoPaymentInProgress = 'NO_PAYMENT_IN_PROGRESS',
  /**
   * Reader not found.
   *
   * @since 0.0.1
   */
  ReaderNotFound = 'READER_NOT_FOUND',
}
