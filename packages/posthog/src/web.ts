import { WebPlugin } from '@capacitor/core';
import posthog from 'posthog-js';
import type { PostHogConfig } from 'posthog-js';

import type {
  AliasOptions,
  CaptureOptions,
  GetFeatureFlagOptions,
  GetFeatureFlagPayloadOptions,
  GetFeatureFlagPayloadResult,
  GetFeatureFlagResult,
  GroupOptions,
  IdentifyOptions,
  IsFeatureEnabledOptions,
  IsFeatureEnabledResult,
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

  async getFeatureFlag(
    options: GetFeatureFlagOptions,
  ): Promise<GetFeatureFlagResult> {
    const value = posthog.getFeatureFlag(options.key);
    return value === undefined ? { value: null } : { value };
  }

  async getFeatureFlagPayload(
    options: GetFeatureFlagPayloadOptions,
  ): Promise<GetFeatureFlagPayloadResult> {
    const value = posthog.getFeatureFlagPayload(options.key);
    return { value: value || null };
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

  async isFeatureEnabled(
    options: IsFeatureEnabledOptions,
  ): Promise<IsFeatureEnabledResult> {
    const enabled = posthog.isFeatureEnabled(options.key);
    return { enabled: enabled || false };
  }

  async register(options: RegisterOptions): Promise<void> {
    posthog.register({
      [options.key]: options.value,
    });
  }

  async reloadFeatureFlags(): Promise<void> {
    posthog.reloadFeatureFlags();
  }

  async reset(): Promise<void> {
    posthog.reset();
  }

  async screen(_options: ScreenOptions): Promise<void> {
    this.throwUnimplementedError();
  }

  async setup(options: SetupOptions): Promise<void> {
    const host = options.host || 'https://us.i.posthog.com';
    const config: Partial<PostHogConfig> = {
      api_host: host,
    };

    // Configure session recording if enabled
    if (options.enableSessionReplay) {
      config.session_recording = {
        recordCrossOriginIframes: true,
      };

      if (options.sessionReplayConfig) {
        if (options.sessionReplayConfig.maskAllTextInputs !== undefined) {
          config.session_recording.maskAllInputs =
            options.sessionReplayConfig.maskAllTextInputs;
        }
      }
    }

    // Configure error tracking if enabled
    if (options.enableErrorTracking) {
      config.capture_exceptions = true;
    }

    posthog.init(options.apiKey, config);
  }

  async startSessionRecording(): Promise<void> {
    posthog.startSessionRecording();
  }

  async stopSessionRecording(): Promise<void> {
    posthog.stopSessionRecording();
  }

  async unregister(options: UnregisterOptions): Promise<void> {
    posthog.unregister(options.key);
  }

  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}
