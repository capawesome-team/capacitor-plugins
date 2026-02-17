import { WebPlugin } from '@capacitor/core';
import posthog from 'posthog-js';
import type { PostHogConfig } from 'posthog-js';

import type {
  AliasOptions,
  CaptureOptions,
  GetDistinctIdResult,
  GetFeatureFlagOptions,
  GetFeatureFlagPayloadOptions,
  GetFeatureFlagPayloadResult,
  GetFeatureFlagResult,
  GroupOptions,
  IdentifyOptions,
  IsFeatureEnabledOptions,
  IsFeatureEnabledResult,
  IsOptOutResult,
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

  async getDistinctId(): Promise<GetDistinctIdResult> {
    return { distinctId: posthog.get_distinct_id() };
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
    return { value: posthog.getFeatureFlagPayload(options.key) };
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

  async isOptOut(): Promise<IsOptOutResult> {
    return { optedOut: posthog.has_opted_out_capturing() };
  }

  async optIn(): Promise<void> {
    posthog.opt_in_capturing();
  }

  async optOut(): Promise<void> {
    posthog.opt_out_capturing();
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
    const config: Partial<PostHogConfig> & {
      cookieless_mode?: 'always' | 'on_reject';
    } = {
      api_host: host,
    };

    if (options.optOut) {
      config.opt_out_capturing_by_default = true;
    }
    if (options.cookielessMode) {
      config.cookieless_mode = options.cookielessMode;
    }

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
