import type { PluginListenerHandle } from '@capacitor/core';

export interface IntercomPlugin {
  /**
   * Get the number of unread conversations for the current user.
   *
   * @since 0.1.0
   */
  getUnreadConversationCount(): Promise<GetUnreadConversationCountResult>;
  /**
   * Handle an incoming push notification that belongs to Intercom.
   *
   * Use `isIntercomPushNotification(...)` to check whether the notification
   * belongs to Intercom before calling this method.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  handlePushNotification(options: HandlePushNotificationOptions): Promise<void>;
  /**
   * Hide any currently presented Intercom UI (e.g. the Messenger).
   *
   * @since 0.1.0
   */
  hide(): Promise<void>;
  /**
   * Initialize the Intercom SDK with your app ID and API key.
   *
   * This method must be called before any other method.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Check whether an incoming push notification belongs to Intercom.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isIntercomPushNotification(
    options: IsIntercomPushNotificationOptions,
  ): Promise<IsIntercomPushNotificationResult>;
  /**
   * Log an event with an optional set of metadata.
   *
   * @since 0.1.0
   */
  logEvent(options: LogEventOptions): Promise<void>;
  /**
   * Log in an unidentified (anonymous) user.
   *
   * @since 0.1.0
   */
  loginUnidentifiedUser(): Promise<void>;
  /**
   * Log in an identified user with a user ID and/or an email address.
   *
   * At least one of `userId` or `email` must be provided.
   *
   * @since 0.1.0
   */
  loginUser(options: LoginUserOptions): Promise<void>;
  /**
   * Log out the current user and clear the local Intercom data.
   *
   * @since 0.1.0
   */
  logout(): Promise<void>;
  /**
   * Present the Intercom Messenger with the given space.
   *
   * @since 0.1.0
   */
  present(options?: PresentOptions): Promise<void>;
  /**
   * Present a specific piece of Intercom content (e.g. an article, carousel,
   * survey, or conversation).
   *
   * @since 0.1.0
   */
  presentContent(options: PresentContentOptions): Promise<void>;
  /**
   * Present the Intercom message composer, optionally pre-filled with an
   * initial message.
   *
   * @since 0.1.0
   */
  presentMessageComposer(
    options?: PresentMessageComposerOptions,
  ): Promise<void>;
  /**
   * Forward a push notification token to Intercom.
   *
   * On Android, pass the Firebase Cloud Messaging (FCM) token. On iOS, pass
   * the hexadecimal APNs device token.
   *
   * @since 0.1.0
   */
  sendPushTokenToIntercom(
    options: SendPushTokenToIntercomOptions,
  ): Promise<void>;
  /**
   * Set the bottom padding of the Intercom UI (in-app messages and launcher).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setBottomPadding(options: SetBottomPaddingOptions): Promise<void>;
  /**
   * Set whether in-app messages are visible.
   *
   * @since 0.1.0
   */
  setInAppMessagesVisible(
    options: SetInAppMessagesVisibleOptions,
  ): Promise<void>;
  /**
   * Set whether the Intercom launcher is visible.
   *
   * @since 0.1.0
   */
  setLauncherVisible(options: SetLauncherVisibleOptions): Promise<void>;
  /**
   * Set the user hash (HMAC) for identity verification.
   *
   * This must be called before logging in the user.
   *
   * @since 0.1.0
   */
  setUserHash(options: SetUserHashOptions): Promise<void>;
  /**
   * Set the JSON Web Token (JWT) for identity verification.
   *
   * This must be called before logging in the user.
   *
   * @since 0.1.0
   */
  setUserJwt(options: SetUserJwtOptions): Promise<void>;
  /**
   * Update the attributes of the current user.
   *
   * @since 0.1.0
   */
  updateUser(options: UpdateUserOptions): Promise<void>;
  /**
   * Called when the Intercom Messenger is hidden.
   *
   * Only available on iOS and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'messengerHidden',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the Intercom Messenger is shown.
   *
   * Only available on iOS and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'messengerShown',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the number of unread conversations changes.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'unreadConversationCountChange',
    listenerFunc: (event: UnreadConversationCountChangeEvent) => void,
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
export interface GetUnreadConversationCountResult {
  /**
   * The number of unread conversations.
   *
   * @since 0.1.0
   */
  count: number;
}

/**
 * @since 0.1.0
 */
export interface HandlePushNotificationOptions {
  /**
   * The data of the push notification.
   *
   * @since 0.1.0
   */
  data: Record<string, unknown>;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * The Android API key of your Intercom app.
   *
   * Required to use the plugin on Android.
   *
   * @since 0.1.0
   */
  androidApiKey?: string;
  /**
   * The app ID of your Intercom app.
   *
   * @since 0.1.0
   */
  appId: string;
  /**
   * The iOS API key of your Intercom app.
   *
   * Required to use the plugin on iOS.
   *
   * @since 0.1.0
   */
  iosApiKey?: string;
}

/**
 * @since 0.1.0
 */
export interface IsIntercomPushNotificationOptions {
  /**
   * The data of the push notification.
   *
   * @since 0.1.0
   */
  data: Record<string, unknown>;
}

/**
 * @since 0.1.0
 */
export interface IsIntercomPushNotificationResult {
  /**
   * Whether the push notification belongs to Intercom.
   *
   * @since 0.1.0
   */
  intercom: boolean;
}

/**
 * @since 0.1.0
 */
export interface LogEventOptions {
  /**
   * The metadata of the event.
   *
   * @since 0.1.0
   */
  data?: Record<string, string | number | boolean>;
  /**
   * The name of the event.
   *
   * @since 0.1.0
   */
  name: string;
}

/**
 * @since 0.1.0
 */
export interface LoginUserOptions {
  /**
   * The email address of the user.
   *
   * @since 0.1.0
   */
  email?: string;
  /**
   * The unique identifier of the user.
   *
   * @since 0.1.0
   */
  userId?: string;
}

/**
 * @since 0.1.0
 */
export interface PresentContentOptions {
  /**
   * The identifier of the content to present.
   *
   * Required for the `article`, `carousel`, `survey`, and `conversation`
   * content types.
   *
   * @since 0.1.0
   */
  id?: string;
  /**
   * The identifiers of the content to present.
   *
   * Required for the `help-center-collections` content type.
   *
   * @since 0.1.0
   */
  ids?: string[];
  /**
   * The type of content to present.
   *
   * @since 0.1.0
   */
  type: IntercomContentType;
}

/**
 * @since 0.1.0
 */
export interface PresentMessageComposerOptions {
  /**
   * The message to pre-fill the composer with.
   *
   * @since 0.1.0
   */
  initialMessage?: string;
}

/**
 * @since 0.1.0
 */
export interface PresentOptions {
  /**
   * The space to present.
   *
   * @default 'home'
   * @since 0.1.0
   */
  space?: IntercomSpace;
}

/**
 * @since 0.1.0
 */
export interface SendPushTokenToIntercomOptions {
  /**
   * The push notification token.
   *
   * On Android, this is the Firebase Cloud Messaging (FCM) token. On iOS,
   * this is the hexadecimal APNs device token.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.1.0
 */
export interface SetBottomPaddingOptions {
  /**
   * The bottom padding to set.
   *
   * @since 0.1.0
   */
  padding: number;
}

/**
 * @since 0.1.0
 */
export interface SetInAppMessagesVisibleOptions {
  /**
   * Whether in-app messages should be visible.
   *
   * @since 0.1.0
   */
  visible: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetLauncherVisibleOptions {
  /**
   * Whether the launcher should be visible.
   *
   * @since 0.1.0
   */
  visible: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetUserHashOptions {
  /**
   * The user hash (HMAC) for identity verification.
   *
   * The hash must be generated on your backend using your Intercom secret
   * key. Never expose the secret key in your app.
   *
   * @since 0.1.0
   */
  userHash: string;
}

/**
 * @since 0.1.0
 */
export interface SetUserJwtOptions {
  /**
   * The JSON Web Token (JWT) for identity verification.
   *
   * The token must be generated on your backend using your Intercom secret
   * key. Never expose the secret key in your app.
   *
   * @since 0.1.0
   */
  jwt: string;
}

/**
 * @since 0.1.0
 */
export interface UnreadConversationCountChangeEvent {
  /**
   * The number of unread conversations.
   *
   * @since 0.1.0
   */
  count: number;
}

/**
 * @since 0.1.0
 */
export interface UpdateUserCompany {
  /**
   * The date the company was created as a Unix timestamp in seconds.
   *
   * @since 0.1.0
   * @example 1704067200
   */
  createdAt?: number;
  /**
   * The custom attributes of the company.
   *
   * @since 0.1.0
   */
  customAttributes?: Record<string, string | number | boolean>;
  /**
   * The unique identifier of the company.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The monthly spend of the company.
   *
   * @since 0.1.0
   */
  monthlySpend?: number;
  /**
   * The name of the company.
   *
   * @since 0.1.0
   */
  name?: string;
  /**
   * The plan of the company.
   *
   * @since 0.1.0
   */
  plan?: string;
}

/**
 * @since 0.1.0
 */
export interface UpdateUserOptions {
  /**
   * The companies the user belongs to.
   *
   * @since 0.1.0
   */
  companies?: UpdateUserCompany[];
  /**
   * The custom attributes of the user.
   *
   * @since 0.1.0
   */
  customAttributes?: Record<string, string | number | boolean>;
  /**
   * The email address of the user.
   *
   * @since 0.1.0
   */
  email?: string;
  /**
   * The preferred language of the user as an ISO 639-1 code.
   *
   * @since 0.1.0
   * @example 'en'
   */
  languageOverride?: string;
  /**
   * The name of the user.
   *
   * @since 0.1.0
   */
  name?: string;
  /**
   * The phone number of the user.
   *
   * @since 0.1.0
   */
  phone?: string;
  /**
   * The date the user signed up as a Unix timestamp in seconds.
   *
   * @since 0.1.0
   * @example 1704067200
   */
  signedUpAt?: number;
  /**
   * Whether the user is unsubscribed from emails.
   *
   * @since 0.1.0
   */
  unsubscribedFromEmails?: boolean;
  /**
   * The unique identifier of the user.
   *
   * @since 0.1.0
   */
  userId?: string;
}

/**
 * A type of Intercom content that can be presented.
 *
 * @since 0.1.0
 */
export type IntercomContentType =
  | 'article'
  | 'carousel'
  | 'conversation'
  | 'help-center-collections'
  | 'survey';

/**
 * A space of the Intercom Messenger.
 *
 * @since 0.1.0
 */
export type IntercomSpace = 'home' | 'messages' | 'help-center' | 'tickets';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The login of the user failed.
   *
   * @since 0.1.0
   */
  LoginFailed = 'LOGIN_FAILED',
  /**
   * The plugin has not been initialized yet.
   *
   * Call `initialize(...)` before calling any other method.
   *
   * @since 0.1.0
   */
  NotInitialized = 'NOT_INITIALIZED',
  /**
   * The update of the user failed.
   *
   * @since 0.1.0
   */
  UpdateFailed = 'UPDATE_FAILED',
}
