import { WebPlugin } from '@capacitor/core';
import posthog from 'posthog-js';

import type {
  AliasOptions,
  CaptureOptions,
  GroupOptions,
  IdentifyOptions,
  PosthogPlugin,
  RegisterOptions,
  ScreenOptions,
  SetupOptions,
  UnregisterOptions,
} from './definitions';

export class PosthogWeb extends WebPlugin implements PosthogPlugin {
  async alias(options: AliasOptions): Promise<void> {
    posthog.alias(options.alias);
  }

  async capture(options: CaptureOptions): Promise<void> {
    posthog.capture(options.event, options.properties);
  }

  async flush(): Promise<void> {
    this.throwUnimplementedError();
  }

  async group(options: GroupOptions): Promise<void> {
    posthog.group(options.type, options.key, options.groupProperties);
  }

  async identify(options: IdentifyOptions): Promise<void> {
    posthog.identify(options.distinctId, options.userProperties);
  }

  async register(options: RegisterOptions): Promise<void> {
    posthog.register({
      [options.key]: options.value,
    });
  }

  async reset(): Promise<void> {
    posthog.reset();
  }

  async screen(_options: ScreenOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  async setup(options: SetupOptions): Promise<void> {
    const host = options.host || 'https://us.i.posthog.com';
    posthog.init(options.apiKey, {
      api_host: host,
    });
  }

  async unregister(options: UnregisterOptions): Promise<void> {
    posthog.unregister(options.key);
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
