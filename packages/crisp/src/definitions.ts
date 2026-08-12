import type { PluginListenerHandle } from '@capacitor/core';

export interface CrispPlugin {
  /**
   * Configure the plugin with your Crisp website ID.
   *
   * This method must be called before any other method.
   *
   * @since 0.1.0
   */
  configure(options: ConfigureOptions): Promise<void>;
  /**
   * Handle an incoming push notification that belongs to Crisp.
   *
   * Use `isCrispPushNotification(...)` to check whether the notification
   * belongs to Crisp before calling this method.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  handlePushNotification(options: HandlePushNotificationOptions): Promise<void>;
  /**
   * Check whether an incoming push notification belongs to Crisp.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isCrispPushNotification(
    options: IsCrispPushNotificationOptions,
  ): Promise<IsCrispPushNotificationResult>;
  /**
   * Open the chat.
   *
   * @since 0.1.0
   */
  openChat(): Promise<void>;
  /**
   * Open a specific helpdesk article.
   *
   * @since 0.1.0
   */
  openHelpdeskArticle(options: OpenHelpdeskArticleOptions): Promise<void>;
  /**
   * Push a session event to the current session.
   *
   * @since 0.1.0
   */
  pushSessionEvent(options: PushSessionEventOptions): Promise<void>;
  /**
   * Reset the current session.
   *
   * This clears the current session and starts a new one on the next
   * interaction.
   *
   * @since 0.1.0
   */
  resetSession(): Promise<void>;
  /**
   * Open the helpdesk with the search view.
   *
   * @since 0.1.0
   */
  searchHelpdesk(): Promise<void>;
  /**
   * Set the company of the current user.
   *
   * @since 0.1.0
   */
  setCompany(options: SetCompanyOptions): Promise<void>;
  /**
   * Enable or disable push notifications.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  setNotificationsEnabled(
    options: SetNotificationsEnabledOptions,
  ): Promise<void>;
  /**
   * Set a boolean value for the given key in the current session.
   *
   * @since 0.1.0
   */
  setSessionBool(options: SetSessionBoolOptions): Promise<void>;
  /**
   * Set an integer value for the given key in the current session.
   *
   * @since 0.1.0
   */
  setSessionInt(options: SetSessionIntOptions): Promise<void>;
  /**
   * Set the segment of the current session.
   *
   * @since 0.1.0
   */
  setSessionSegment(options: SetSessionSegmentOptions): Promise<void>;
  /**
   * Set a string value for the given key in the current session.
   *
   * @since 0.1.0
   */
  setSessionString(options: SetSessionStringOptions): Promise<void>;
  /**
   * Set whether the user should be prompted for the notification permission.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  setShouldPromptForNotificationPermission(
    options: SetShouldPromptForNotificationPermissionOptions,
  ): Promise<void>;
  /**
   * Set the token ID of the current user.
   *
   * The token ID is used to restore a session across devices and logins.
   * You may also set it directly via `configure(...)`.
   *
   * @since 0.1.0
   */
  setTokenId(options: SetTokenIdOptions): Promise<void>;
  /**
   * Set the information of the current user.
   *
   * @since 0.1.0
   */
  setUser(options: SetUserOptions): Promise<void>;
  /**
   * Called when the chat is closed.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'chatClosed',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the chat is opened.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'chatOpened',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a message is received from the operator.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'messageReceived',
    listenerFunc: (event: MessageReceivedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a message is sent by the user.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'messageSent',
    listenerFunc: (event: MessageSentEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the session is loaded.
   *
   * This is also the recommended way to get the session ID.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'sessionLoaded',
    listenerFunc: (event: SessionLoadedEvent) => void,
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
export interface ConfigureOptions {
  /**
   * The token ID of the current user.
   *
   * @since 0.1.0
   */
  tokenId?: string;
  /**
   * The website ID of your Crisp website.
   *
   * @since 0.1.0
   * @example '5c4b5c4b-5c4b-5c4b-5c4b-5c4b5c4b5c4b'
   */
  websiteId: string;
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
export interface IsCrispPushNotificationOptions {
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
export interface IsCrispPushNotificationResult {
  /**
   * Whether the push notification belongs to Crisp.
   *
   * @since 0.1.0
   */
  crisp: boolean;
}

/**
 * @since 0.1.0
 */
export interface MessageReceivedEvent {
  /**
   * The text content of the message.
   *
   * This is `null` if the message has no text content (e.g. a file or audio
   * message).
   *
   * @since 0.1.0
   */
  content: string | null;
}

/**
 * @since 0.1.0
 */
export interface MessageSentEvent {
  /**
   * The text content of the message.
   *
   * This is `null` if the message has no text content (e.g. a file or audio
   * message).
   *
   * @since 0.1.0
   */
  content: string | null;
}

/**
 * @since 0.1.0
 */
export interface OpenHelpdeskArticleOptions {
  /**
   * The category of the article.
   *
   * @since 0.1.0
   */
  category?: string;
  /**
   * The ID (slug) of the article.
   *
   * @since 0.1.0
   * @example 'how-to-use-crisp-1a2b3c'
   */
  id: string;
  /**
   * The locale of the article.
   *
   * @since 0.1.0
   * @example 'en'
   */
  locale: string;
  /**
   * The title of the article.
   *
   * @since 0.1.0
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface PushSessionEventOptions {
  /**
   * The color of the event.
   *
   * @default SessionEventColor.Blue
   * @since 0.1.0
   */
  color?: SessionEventColor;
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
export interface SetCompanyOptions {
  /**
   * The description of the company.
   *
   * @since 0.1.0
   */
  description?: string;
  /**
   * The employment information of the user at the company.
   *
   * @since 0.1.0
   */
  employment?: SetCompanyEmployment;
  /**
   * The geolocation of the company.
   *
   * @since 0.1.0
   */
  geolocation?: SetCompanyGeolocation;
  /**
   * The name of the company.
   *
   * @since 0.1.0
   */
  name: string;
  /**
   * The URL of the company.
   *
   * @since 0.1.0
   */
  url?: string;
}

/**
 * @since 0.1.0
 */
export interface SetCompanyEmployment {
  /**
   * The role of the user at the company.
   *
   * @since 0.1.0
   */
  role: string;
  /**
   * The job title of the user at the company.
   *
   * @since 0.1.0
   */
  title: string;
}

/**
 * @since 0.1.0
 */
export interface SetCompanyGeolocation {
  /**
   * The city of the company.
   *
   * @since 0.1.0
   */
  city: string;
  /**
   * The country of the company.
   *
   * @since 0.1.0
   */
  country: string;
}

/**
 * @since 0.1.0
 */
export interface SetNotificationsEnabledOptions {
  /**
   * Whether push notifications should be enabled.
   *
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetSessionBoolOptions {
  /**
   * The key of the session data.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * The value of the session data.
   *
   * @since 0.1.0
   */
  value: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetSessionIntOptions {
  /**
   * The key of the session data.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * The value of the session data.
   *
   * @since 0.1.0
   */
  value: number;
}

/**
 * @since 0.1.0
 */
export interface SetSessionSegmentOptions {
  /**
   * The segment of the session.
   *
   * @since 0.1.0
   */
  segment: string;
}

/**
 * @since 0.1.0
 */
export interface SetSessionStringOptions {
  /**
   * The key of the session data.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * The value of the session data.
   *
   * @since 0.1.0
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface SetShouldPromptForNotificationPermissionOptions {
  /**
   * Whether the user should be prompted for the notification permission.
   *
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetTokenIdOptions {
  /**
   * The token ID of the current user.
   *
   * @since 0.1.0
   */
  tokenId: string;
}

/**
 * @since 0.1.0
 */
export interface SetUserOptions {
  /**
   * The URL of the user's avatar.
   *
   * @since 0.1.0
   */
  avatarUrl?: string;
  /**
   * The email address of the user.
   *
   * @since 0.1.0
   */
  email?: string;
  /**
   * The HMAC-SHA256 signature of the user's email address for identity
   * verification.
   *
   * The signature must be generated on your backend using your Crisp secret
   * key. Never expose the secret key in your app.
   *
   * @since 0.1.0
   */
  emailSignature?: string;
  /**
   * The nickname of the user.
   *
   * @since 0.1.0
   */
  nickname?: string;
  /**
   * The phone number of the user.
   *
   * @since 0.1.0
   */
  phone?: string;
}

/**
 * @since 0.1.0
 */
export interface SessionLoadedEvent {
  /**
   * The ID of the loaded session.
   *
   * @since 0.1.0
   */
  sessionId: string;
}

/**
 * The color of a session event.
 *
 * @since 0.1.0
 */
export enum SessionEventColor {
  /**
   * @since 0.1.0
   */
  Black = 'BLACK',
  /**
   * @since 0.1.0
   */
  Blue = 'BLUE',
  /**
   * @since 0.1.0
   */
  Brown = 'BROWN',
  /**
   * @since 0.1.0
   */
  Green = 'GREEN',
  /**
   * @since 0.1.0
   */
  Grey = 'GREY',
  /**
   * @since 0.1.0
   */
  Orange = 'ORANGE',
  /**
   * @since 0.1.0
   */
  Pink = 'PINK',
  /**
   * @since 0.1.0
   */
  Purple = 'PURPLE',
  /**
   * @since 0.1.0
   */
  Red = 'RED',
  /**
   * @since 0.1.0
   */
  Yellow = 'YELLOW',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The plugin has not been configured yet.
   *
   * Call `configure(...)` before calling any other method.
   *
   * @since 0.1.0
   */
  NotConfigured = 'NOT_CONFIGURED',
}
