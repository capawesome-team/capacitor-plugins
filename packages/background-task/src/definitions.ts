export type CallbackID = string;

export interface BackgroundTaskPlugin {
  /**
   * Call this method when the app moves to the background.
   * It allows the app to continue running a task in the background.
   *
   * On **iOS** this method should be finished in less than 30 seconds.
   *
   * Only available for Android and iOS.
   */
  beforeExit(cb: () => void): Promise<CallbackID>;
  /**
   * Finish the current background task.
   * The OS will put the app to sleep.
   *
   * Only available for Android and iOS.
   */
  finish(options: FinishOptions): void;
}

export interface FinishOptions {
  taskId: CallbackID;
}
