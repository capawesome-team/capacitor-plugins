import { WebPlugin } from '@capacitor/core';
import Vapi from '@vapi-ai/web';

import type {
  ConversationUpdateEvent,
  ErrorEvent,
  IsMutedResult,
  SayOptions,
  SetMutedOptions,
  SetupOptions,
  SpeechUpdateEvent,
  StartOptions,
  VapiPlugin,
} from './definitions';

export class VapiWeb extends WebPlugin implements VapiPlugin {
  private vapi: Vapi | undefined;

  async isMuted(): Promise<IsMutedResult> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    const muted = this.vapi.isMuted();
    return { muted };
  }

  async say(options: SayOptions): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.say(options.message, options.endCallAfterSpoken);
  }

  async setMuted(options: SetMutedOptions): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.setMuted(options.muted);
  }

  async setup(options: SetupOptions): Promise<void> {
    this.vapi?.removeAllListeners();
    this.vapi = new Vapi(options.apiKey);
    this.vapi.on('call-end', () => {
      this.notifyCallEndListener();
    });
    this.vapi.on('call-start', () => {
      this.notifyCallStartListener();
    });
    this.vapi.on('error', error => {
      this.notifyErrorListener(error);
    });
    this.vapi.on('message', message => {
      this.notifyConversationUpdateListener(message);
    });
    this.vapi.on('speech-end', () => {
      this.notifySpeechUpdateListener('stopped');
    });
    this.vapi.on('speech-start', () => {
      this.notifySpeechUpdateListener('started');
    });
  }

  async start(options: StartOptions): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.start(options.assistantId);
  }

  async stop(): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.stop();
  }

  private notifyCallEndListener() {
    this.notifyListeners('callEnd', {});
  }

  private notifyCallStartListener() {
    this.notifyListeners('callStart', {});
  }

  private notifyConversationUpdateListener(message: any) {
    const event: ConversationUpdateEvent = {
      messages: [message],
    };
    this.notifyListeners('conversationUpdate', event);
  }

  private notifyErrorListener(error: any) {
    const event: ErrorEvent = {
      message: error.message,
    };
    this.notifyListeners('error', event);
  }

  private notifySpeechUpdateListener(status: 'started' | 'stopped') {
    const event: SpeechUpdateEvent = {
      role: 'assistant',
      status,
    };
    this.notifyListeners('speechUpdate', event);
  }

  private throwUninitializedError(): never {
    throw new Error('Vapi is not initialized. Please call setup() first.');
  }
}
