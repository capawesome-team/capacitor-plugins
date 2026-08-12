export interface WalletPlugin {
  /**
   * Add one or more passes to Apple Wallet.
   *
   * The passes must be created and signed on your server. This method only
   * presents the system add-pass sheet with the provided passes.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  addPasses(options: AddPassesOptions): Promise<void>;
  /**
   * Check whether passes can be added to Apple Wallet.
   *
   * This may return `false` on some devices (e.g. certain iPads) or when adding
   * passes is restricted. Use this to gate the UI that triggers `addPasses`.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  canAddPasses(): Promise<CanAddPassesResult>;
  /**
   * Save a pass to Google Wallet.
   *
   * This opens the Google Wallet "Save to Wallet" flow using the provided JWT.
   * The JWT must be created and signed on your server.
   *
   * **Note**: With the URL-based flow there is no completion signal, so the
   * promise resolves as soon as the flow is launched, not when the pass is
   * actually saved.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  saveToGoogleWallet(options: SaveToGoogleWalletOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface AddPassesOptions {
  /**
   * The passes to add to Apple Wallet.
   *
   * Each entry must be a base64-encoded `.pkpass` file that was signed on your
   * server.
   *
   * @since 0.1.0
   */
  passes: string[];
}

/**
 * @since 0.1.0
 */
export interface CanAddPassesResult {
  /**
   * Whether passes can be added to Apple Wallet.
   *
   * @since 0.1.0
   */
  canAdd: boolean;
}

/**
 * @since 0.1.0
 */
export interface SaveToGoogleWalletOptions {
  /**
   * The signed Google Wallet JWT that was created on your server.
   *
   * @since 0.1.0
   */
  jwt: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The passes could not be added to Apple Wallet.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  AddFailed = 'ADD_FAILED',
  /**
   * A pass could not be read because its data is invalid or not properly signed.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  PassInvalid = 'PASS_INVALID',
}
