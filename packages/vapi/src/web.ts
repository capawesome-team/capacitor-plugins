import { WebPlugin } from '@capacitor/core';
import Vapi from '@vapi-ai/web';

import type {
  IsMutedResult,
  SayOptions,
  SendOptions,
  SetMutedOptions,
  SetupOptions,
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

  async send(options: SendOptions): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.send({
      type: 'add-message',
      message: {
        role: options.role,
        content: options.content,
      },
    });
  }

  async setMuted(options: SetMutedOptions): Promise<void> {
    if (!this.vapi) {
      this.throwUninitializedError();
    }

    this.vapi.setMuted(options.muted);
  }

  async setup(options: SetupOptions): Promise<void> {
    this.vapi = new Vapi(options.apiKey);
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

  private throwUninitializedError(): never {
    throw new Error('Vapi is not initialized. Please call setup() first.');
  }
}
