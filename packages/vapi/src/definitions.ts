import type { PluginListenerHandle } from '@capacitor/core';

export interface VapiPlugin {
  isMuted(): Promise<IsMutedResult>;
  say(options: SayOptions): Promise<void>;
  setMuted(options: SetMutedOptions): Promise<void>;
  setup(options: SetupOptions): Promise<void>;
  start(options: StartOptions): Promise<void>;
  stop(): Promise<void>;
  addListener(
    eventName: 'callEnd',
    listenerFunc: CallEndEventListener,
  ): Promise<PluginListenerHandle>;
  addListener(
    eventName: 'callStart',
    listenerFunc: CallStartEventListener,
  ): Promise<PluginListenerHandle>;
  addListener(
    eventName: 'conversationUpdate',
    listenerFunc: ConversationUpdateEventListener,
  ): Promise<PluginListenerHandle>;
  addListener(
    eventName: 'error',
    listenerFunc: ErrorEventListener,
  ): Promise<PluginListenerHandle>;
  addListener(
    eventName: 'speechUpdate',
    listenerFunc: SpeechUpdateEventListener,
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

export type CallEndEventListener = () => void;

export type CallStartEventListener = () => void;

export type ConversationUpdateEventListener = (
  event: ConversationUpdateEvent,
) => void;

export interface ConversationUpdateEvent {
  messages: {
    content: string;
    role: 'assistant' | 'system' | 'user';
  }[];
}

export type ErrorEventListener = (error: ErrorEvent) => void;

export interface ErrorEvent {
  message: string;
}

export type SpeechUpdateEventListener = (event: SpeechUpdateEvent) => void;

export interface SpeechUpdateEvent {
  /**
   * Only available on Android and iOS.
   */
  role?: 'assistant' | 'user';
  status: 'started' | 'stopped';
}

export type MessageEventListener = (event: MessageEvent) => void;

export interface MessageEvent {
  message: string;
}
