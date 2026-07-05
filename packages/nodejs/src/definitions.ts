/// <reference types="@capacitor/cli" />

import type { PluginListenerHandle } from '@capacitor/core';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    Nodejs?: {
      /**
       * The directory of the Node.js project, relative to the Capacitor `webDir`.
       *
       * Only available on Android and iOS.
       *
       * @since 0.0.1
       * @default 'nodejs'
       * @example 'custom-nodejs'
       */
      nodeDir?: string;
      /**
       * The start mode of the Node.js runtime.
       *
       * If set to `auto`, the Node.js runtime starts automatically when the app is launched.
       * If set to `manual`, the Node.js runtime must be started manually using the `start(...)` method.
       *
       * Only available on Android and iOS.
       *
       * @since 0.0.1
       * @default 'auto'
       * @example 'manual'
       */
      startMode?: 'auto' | 'manual';
    };
  }
}

/**
 * @since 0.0.1
 */
export interface NodejsPlugin {
  /**
   * Check if the Node.js runtime is ready to receive messages.
   *
   * The Node.js runtime is considered ready as soon as the Node.js project
   * has required the `bridge` module.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  isReady(): Promise<IsReadyResult>;
  /**
   * Send a message to the Node.js runtime.
   *
   * This method is only available when the Node.js runtime is ready.
   * Use the `isReady()` method or the `ready` event to check if the
   * Node.js runtime is ready.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  send(options: SendOptions): Promise<void>;
  /**
   * Start the Node.js runtime manually.
   *
   * This method is only available if the `startMode` configuration option
   * is set to `manual`.
   *
   * **Attention**: The Node.js runtime can only be started once per app
   * launch. Stopping and restarting the Node.js runtime is not supported.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  start(options?: StartOptions): Promise<void>;
  /**
   * Called when a message is received from the Node.js runtime.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'message',
    listenerFunc: (event: MessageEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the Node.js runtime is ready to receive messages.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'ready',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.0.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface IsReadyResult {
  /**
   * Whether or not the Node.js runtime is ready to receive messages.
   *
   * @since 0.0.1
   * @example true
   */
  ready: boolean;
}

/**
 * @since 0.0.1
 */
export interface SendOptions {
  /**
   * The arguments to send to the Node.js runtime.
   *
   * @since 0.0.1
   * @example ['Hello from Capacitor!']
   */
  args?: MessageArg[];
  /**
   * The name of the event to send to the Node.js runtime.
   *
   * @since 0.0.1
   * @example 'my-event'
   */
  eventName: string;
}

/**
 * @since 0.0.1
 */
export interface StartOptions {
  /**
   * The arguments to pass to the Node.js process.
   *
   * @since 0.0.1
   * @example ['--option', 'value']
   */
  args?: string[];
  /**
   * The environment variables to set for the Node.js process.
   *
   * @since 0.0.1
   * @example { MY_ENV_VAR: 'value' }
   */
  env?: { [key: string]: string };
  /**
   * The path of the script file to run, relative to the Node.js project directory.
   *
   * @since 0.0.1
   * @default The `main` field of the `package.json` file of the Node.js project.
   * @example 'custom-main.js'
   */
  script?: string;
}

/**
 * @since 0.0.1
 */
export interface MessageEvent {
  /**
   * The arguments received from the Node.js runtime.
   *
   * @since 0.0.1
   * @example ['Hello from Node.js!']
   */
  args: MessageArg[];
  /**
   * The name of the event received from the Node.js runtime.
   *
   * @since 0.0.1
   * @example 'my-event'
   */
  eventName: string;
}

/**
 * A single argument of a message that is exchanged with the Node.js runtime.
 *
 * @since 0.0.1
 */
export type MessageArg = string | number | boolean;

/**
 * @since 0.0.1
 */
export enum ErrorCode {
  /**
   * The event name is missing.
   *
   * @since 0.0.1
   */
  EventNameMissing = 'EVENT_NAME_MISSING',
  /**
   * The Node.js runtime is already running.
   *
   * @since 0.0.1
   */
  NodeAlreadyRunning = 'NODE_ALREADY_RUNNING',
  /**
   * The Node.js runtime is not ready to receive messages.
   *
   * @since 0.0.1
   */
  NodeNotReady = 'NODE_NOT_READY',
  /**
   * The Node.js project directory was not found.
   *
   * @since 0.0.1
   */
  ProjectNotFound = 'PROJECT_NOT_FOUND',
  /**
   * The `startMode` configuration option is not set to `manual`.
   *
   * @since 0.0.1
   */
  StartModeNotManual = 'START_MODE_NOT_MANUAL',
}
