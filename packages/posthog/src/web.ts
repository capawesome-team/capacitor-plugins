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
  StartSessionRecordingOptions,
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
    return { value: posthog.getFeatureFlagPayload(options.key) };
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
      if (options.sessionReplaySampling !== undefined) {
        config.session_recording.sampleRate = options.sessionReplaySampling;
      }
      if (options.sessionReplayLinkedFlag) {
        config.session_recording.linked_flag = options.sessionReplayLinkedFlag;
      }
    }

    // Configure error tracking if enabled
    if (options.enableErrorTracking) {
      config.captureExceptions = true;
    }

    posthog.init(options.apiKey, config);

    // Start session recording if configured
    if (options.enableSessionReplay) {
      const sessionOptions: any = {};
      if (options.sessionReplaySampling !== undefined) {
        sessionOptions.sampling = options.sessionReplaySampling;
      }
      if (options.sessionReplayLinkedFlag) {
        sessionOptions.linked_flag = options.sessionReplayLinkedFlag;
      }
      this.startSessionRecording(sessionOptions);
    }
  }

  async startSessionRecording(
    options?: StartSessionRecordingOptions,
  ): Promise<void> {
    if (options?.sampling || options?.linkedFlag) {
      const sessionOptions: any = {};
      if (options.sampling !== undefined) {
        sessionOptions.sampling = options.sampling;
      }
      if (options.linkedFlag !== undefined) {
        sessionOptions.linked_flag = options.linkedFlag;
      }
      posthog.startSessionRecording(sessionOptions);
    } else {
      posthog.startSessionRecording();
    }
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
