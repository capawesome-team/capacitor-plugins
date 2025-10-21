import { WebPlugin } from '@capacitor/core';
import posthog from 'posthog-js';

import type {
  AliasOptions,
  CaptureExceptionOptions,
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

  async captureException(options: CaptureExceptionOptions): Promise<void> {
    posthog.captureException(options.exception, options.properties);
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
    const config: any = {
      api_host: host,
    };

    // Configure session recording if enabled
    if (options.enableSessionReplay) {
      config.session_recording = {
        recordCrossOriginIframes: true,
      };

      // Use new sessionReplayConfig if provided, otherwise fall back to deprecated options
      if (options.sessionReplayConfig) {
        if (options.sessionReplayConfig.debouncerDelay !== undefined) {
          config.session_recording.sampleRate =
            options.sessionReplayConfig.debouncerDelay;
        }
        if (options.sessionReplayConfig.captureNetworkTelemetry !== undefined) {
          config.session_recording.captureNetworkTelemetry =
            options.sessionReplayConfig.captureNetworkTelemetry;
        }
        if (options.sessionReplayConfig.screenshotMode !== undefined) {
          config.session_recording.screenshotMode =
            options.sessionReplayConfig.screenshotMode;
        }
        if (options.sessionReplayConfig.maskAllTextInputs !== undefined) {
          config.session_recording.maskAllTextInputs =
            options.sessionReplayConfig.maskAllTextInputs;
        }
        if (options.sessionReplayConfig.maskAllImages !== undefined) {
          config.session_recording.maskAllImages =
            options.sessionReplayConfig.maskAllImages;
        }
      }
    }

    // Configure error tracking if enabled
    if (options.enableErrorTracking) {
      config.captureExceptions = true;
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
