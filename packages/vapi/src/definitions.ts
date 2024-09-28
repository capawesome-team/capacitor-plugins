export interface VapiPlugin {
  isMuted(): Promise<IsMutedResult>;
  /**
   * Only available on Web.
   */
  say(options: SayOptions): Promise<void>;
  send(options: SendOptions): Promise<void>;
  setMuted(options: SetMutedOptions): Promise<void>;
  setup(options: SetupOptions): Promise<void>;
  start(options: StartOptions): Promise<void>;
  stop(): Promise<void>;
}

export interface IsMutedResult {
  muted: boolean;
}

export interface SayOptions {
  message: string;
  endCallAfterSpoken?: boolean;
}

export interface SendOptions {
  content: string;
  role: 'function' | 'system' | 'user' | 'assistant' | 'tool';
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
