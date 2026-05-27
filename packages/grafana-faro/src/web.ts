import { WebPlugin } from '@capacitor/core';
import {
  type BrowserConfig,
  type Faro,
  type Instrumentation,
  type LogLevel as FaroLogLevel,
  getWebInstrumentations,
  initializeFaro,
} from '@grafana/faro-web-sdk';

import type {
  GetSessionResult,
  GetViewResult,
  GrafanaFaroPlugin,
  InitializeOptions,
  PushErrorOptions,
  PushEventOptions,
  PushLogOptions,
  PushMeasurementOptions,
  SetSessionOptions,
  SetUserOptions,
  SetViewOptions,
} from './definitions';
import { ERROR_ALREADY_INITIALIZED, ERROR_NOT_INITIALIZED } from './errors';

export class GrafanaFaroWeb extends WebPlugin implements GrafanaFaroPlugin {
  private faro?: Faro;

  public async getSession(): Promise<GetSessionResult> {
    const faro = this.requireFaro();
    const session = faro.api.getSession();
    if (!session) {
      return {};
    }
    return {
      attributes: session.attributes,
      id: session.id,
    };
  }

  public async getView(): Promise<GetViewResult> {
    const faro = this.requireFaro();
    const view = faro.api.getView();
    return { name: view?.name };
  }

  public async initialize(options: InitializeOptions): Promise<void> {
    if (this.faro) {
      throw new Error(ERROR_ALREADY_INITIALIZED);
    }
    const config: BrowserConfig = {
      apiKey: options.apiKey,
      app: {
        environment: options.app.environment,
        name: options.app.name,
        namespace: options.app.namespace,
        version: options.app.version,
      },
      ignoreErrors: options.ignoreErrors,
      ignoreUrls: options.ignoreUrls,
      instrumentations: this.buildInstrumentations(options),
      paused: options.paused,
      sessionTracking: {
        samplingRate: options.sessionSamplingRate,
      },
      url: options.url,
      user: options.user,
      view: options.view,
    };
    this.faro = initializeFaro(config);
    if (options.sessionAttributes) {
      this.faro.api.setSession({
        attributes: options.sessionAttributes,
      });
    }
  }

  public async pause(): Promise<void> {
    const faro = this.requireFaro();
    faro.pause();
  }

  public async pushError(options: PushErrorOptions): Promise<void> {
    const faro = this.requireFaro();
    const error = this.buildSyntheticError(options);
    faro.api.pushError(error, {
      context: options.context,
      stackFrames: this.mapStackFrames(options.stackFrames),
      type: options.type,
    });
  }

  public async pushEvent(options: PushEventOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.pushEvent(
      options.name,
      options.attributes,
      options.domain ?? 'web',
    );
  }

  public async pushLog(options: PushLogOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.pushLog([options.message], {
      context: options.context,
      level: options.level as FaroLogLevel | undefined,
    });
  }

  public async pushMeasurement(options: PushMeasurementOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.pushMeasurement({
      context: options.context,
      type: options.type,
      values: options.values,
    });
  }

  public async resetSession(): Promise<void> {
    const faro = this.requireFaro();
    faro.api.resetSession();
  }

  public async resetUser(): Promise<void> {
    const faro = this.requireFaro();
    faro.api.resetUser();
  }

  public async setSession(options: SetSessionOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.setSession({
      attributes: options.attributes,
      id: options.id,
    });
  }

  public async setUser(options: SetUserOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.setUser({
      attributes: options.attributes,
      email: options.email,
      fullName: options.fullName,
      id: options.id,
      username: options.username,
    });
  }

  public async setView(options: SetViewOptions): Promise<void> {
    const faro = this.requireFaro();
    faro.api.setView({ name: options.name });
  }

  public async unpause(): Promise<void> {
    const faro = this.requireFaro();
    faro.unpause();
  }

  private buildInstrumentations(options: InitializeOptions): Instrumentation[] {
    const toggles = options.instrumentations ?? {};
    const all = getWebInstrumentations({
      captureConsole: toggles.console !== false,
    });
    return all.filter(instrumentation => {
      switch (instrumentation.name) {
        case '@grafana/faro-web-sdk:instrumentation-errors':
          return toggles.errors !== false;
        case '@grafana/faro-web-sdk:instrumentation-console':
          return toggles.console !== false;
        case '@grafana/faro-web-sdk:instrumentation-web-vitals':
          return toggles.webVitals !== false;
        case '@grafana/faro-web-sdk:instrumentation-view':
          return toggles.view !== false;
        case '@grafana/faro-web-sdk:instrumentation-performance':
          return toggles.performance !== false;
        default:
          return true;
      }
    });
  }

  private buildSyntheticError(options: PushErrorOptions): Error {
    const error = new Error(options.value);
    error.name = options.type;
    return error;
  }

  private mapStackFrames(frames: PushErrorOptions['stackFrames']):
    | {
        colno?: number;
        filename: string;
        function: string;
        lineno?: number;
      }[]
    | undefined {
    if (!frames || frames.length === 0) {
      return undefined;
    }
    return frames.map(frame => ({
      colno: frame.columnNumber,
      filename: frame.fileName ?? '',
      function: frame.functionName ?? '',
      lineno: frame.lineNumber,
    }));
  }

  private requireFaro(): Faro {
    if (!this.faro) {
      throw new Error(ERROR_NOT_INITIALIZED);
    }
    return this.faro;
  }
}
