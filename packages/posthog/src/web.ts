import { WebPlugin } from '@capacitor/core';
import posthog from 'posthog-js';
import type { PostHogConfig } from 'posthog-js';

import type {
  AliasOptions,
  CaptureExceptionOptions,
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
  StackFrame,
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
    const error = new Error(options.message);
    if (options.name) {
      error.name = options.name;
    }
    if (options.stacktrace?.length) {
      error.stack = this.createStackString(
        error.name,
        options.message,
        options.stacktrace,
      );
    }
    posthog.captureException(error, options.properties);
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
    const value = posthog.getFeatureFlagPayload(options.key) ?? null;
    return { value: value as GetFeatureFlagPayloadResult['value'] };
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
    void _options;
    this.throwUnimplementedError();
  }

  async setup(options: SetupOptions): Promise<void> {
    const apiHost = this.getApiHost(options.apiHost, options.host);
    const config: Partial<PostHogConfig> & {
      cookieless_mode?: 'always' | 'on_reject';
    } = {
      api_host: apiHost,
    };

    if (options.uiHost) {
      config.ui_host = options.uiHost;
    }

    if (options.optOut) {
      config.opt_out_capturing_by_default = true;
    }
    if (options.cookielessMode) {
      config.cookieless_mode = options.cookielessMode;
    }
    if (options.autoCaptureExceptions) {
      config.capture_exceptions = true;
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

  private createStackString(
    name: string,
    message: string,
    stacktrace: StackFrame[],
  ): string {
    const lines = stacktrace.map(frame => {
      const location = [frame.fileName, frame.lineNumber, frame.columnNumber]
        .filter(value => value !== undefined)
        .join(':');
      return `    at ${frame.functionName ?? '?'} (${location})`;
    });
    return [`${name}: ${message}`, ...lines].join('\n');
  }

  private getApiHost(apiHost?: string, host?: string): string {
    if (apiHost) {
      if (host && host !== apiHost) {
        console.warn('[Posthog] Both apiHost and host are set. Using apiHost.');
      }
      return apiHost;
    }

    if (host) {
      console.warn('[Posthog] host is deprecated. Use apiHost instead.');
      return host;
    }

    return 'https://us.i.posthog.com';
  }
}
