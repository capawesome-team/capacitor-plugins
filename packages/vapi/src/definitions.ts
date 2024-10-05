import { PluginListenerHandle } from '@capacitor/core';

export interface VapiPlugin {
  isMuted(): Promise<IsMutedResult>;
  say(options: SayOptions): Promise<void>;
  setMuted(options: SetMutedOptions): Promise<void>;
  setup(options: SetupOptions): Promise<void>;
  start(options: StartOptions): Promise<void>;
  stop(): Promise<void>;
  addListener(
    eventName: 'callStart',
    listenerFunc: CallStartEventListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   */
  removeAllListeners(): Promise<void>;
}

export interface IsMutedResult {
  muted: boolean;
}

export interface SayOptions {
  message: string;
  /**
   * Only available on Web.
   */
  endCallAfterSpoken?: boolean;
}

export interface SetMutedOptions {
  muted: boolean;
}

export interface SetupOptions {
  apiKey: string;
}

export interface StartOptions {
  assistantId: string;
}

export type CallStartEventListener = () => void;

export type MessageEventListener = (event: MessageEvent) => void;

export interface MessageEvent {
  message: string;
}
