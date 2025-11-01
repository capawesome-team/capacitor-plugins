import type { PluginListenerHandle } from '@capacitor/core';

export interface ScreenOrientationPlugin {
  /**
   * Locks the device orientation.
   */
  lock(options?: LockOptions): Promise<void>;
  /**
   * Unlocks the device orientation.
   */
  unlock(): Promise<void>;
  /**
   * Gets the current device orientation type.
   */
  getCurrentOrientation(): Promise<GetCurrentOrientationResult>;
  /**
   * Listen for screen orientation changes.
   */
  addListener(
    eventName: 'screenOrientationChange',
    listenerFunc: ScreenOrientationChangeListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   */
  removeAllListeners(): Promise<void>;
}

export interface LockOptions {
  /**
   * The orientation lock type.
   */
  type?: OrientationType;
}

export interface GetCurrentOrientationResult {
  /**
   * The current orientation type.
   */
  type: OrientationType;
}

export enum OrientationType {
  /**
   * The orientation is either landscape-primary or landscape-secondary.
   */
  LANDSCAPE = 'landscape',
  /**
   * The orientation is in the primary landscape mode.
   */
  LANDSCAPE_PRIMARY = 'landscape-primary',
  /**
   * The orientation is in the secondary landscape mode.
   */
  LANDSCAPE_SECONDARY = 'landscape-secondary',
  /**
   * The orientation is either portrait-primary or portrait-secondary.
   */
  PORTRAIT = 'portrait',
  /**
   * The orientation is in the primary portrait mode.
   */
  PORTRAIT_PRIMARY = 'portrait-primary',
  /**
   * The orientation is in the secondary portrait mode.
   */
  PORTRAIT_SECONDARY = 'portrait-secondary',
}

/**
 * Callback to receive the screen orientation change notifications.
 */
export type ScreenOrientationChangeListener = (
  change: ScreenOrientationChange,
) => void;

export interface ScreenOrientationChange {
  /**
   * The current orientation type.
   */
  type: OrientationType;
}
