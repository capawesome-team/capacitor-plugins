export interface FormbricksPlugin {
  /**
   * Log out the current user and clear all attributes.
   *
   * @since 0.1.0
   */
  logout(): Promise<void>;
  /**
   * Set a single user attribute.
   *
   * @since 0.1.0
   */
  setAttribute(options: SetAttributeOptions): Promise<void>;
  /**
   * Set multiple user attributes at once.
   *
   * @since 0.1.0
   */
  setAttributes(options: SetAttributesOptions): Promise<void>;
  /**
   * Set the survey language.
   *
   * @since 0.1.0
   */
  setLanguage(options: SetLanguageOptions): Promise<void>;
  /**
   * Set the user ID of the current user.
   *
   * @since 0.1.0
   */
  setUserId(options: SetUserIdOptions): Promise<void>;
  /**
   * Setup the Formbricks SDK with the provided options.
   *
   * **Attention**: This method must be called before any other method.
   *
   * @since 0.1.0
   */
  setup(options: SetupOptions): Promise<void>;
  /**
   * Track an action that may trigger a survey.
   *
   * @since 0.1.0
   */
  track(options: TrackOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface SetAttributeOptions {
  /**
   * The name of the attribute.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * The value of the attribute.
   *
   * @since 0.1.0
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface SetAttributesOptions {
  /**
   * The attributes to set.
   *
   * @since 0.1.0
   */
  attributes: { [key: string]: string };
}

/**
 * @since 0.1.0
 */
export interface SetLanguageOptions {
  /**
   * The language code of the survey language (e.g. `de`).
   *
   * @since 0.1.0
   */
  language: string;
}

/**
 * @since 0.1.0
 */
export interface SetUserIdOptions {
  /**
   * The user ID of the current user.
   *
   * @since 0.1.0
   */
  userId: string;
}

/**
 * @since 0.1.0
 */
export interface SetupOptions {
  /**
   * The URL of your Formbricks instance.
   *
   * @since 0.1.0
   * @example 'https://app.formbricks.com'
   */
  appUrl: string;
  /**
   * The environment ID of your Formbricks project.
   *
   * @since 0.1.0
   */
  environmentId: string;
}

/**
 * @since 0.1.0
 */
export interface TrackOptions {
  /**
   * The name of the action to track.
   *
   * @since 0.1.0
   */
  action: string;
}
